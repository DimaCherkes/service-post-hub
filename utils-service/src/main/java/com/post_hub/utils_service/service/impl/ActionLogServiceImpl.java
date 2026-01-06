package com.post_hub.utils_service.service.impl;

import com.post_hub.utils_service.entity.ActionLog;
import com.post_hub.utils_service.kafka.model.UtilMessage;
import com.post_hub.utils_service.mapper.ActionLogMapper;
import com.post_hub.utils_service.model.constant.ApiErrorMessage;
import com.post_hub.utils_service.model.dto.ActionLogDTO;
import com.post_hub.utils_service.model.exception.NotFoundException;
import com.post_hub.utils_service.model.request.ActionLogIsReadRequest;
import com.post_hub.utils_service.model.response.ActionLogIsReadResponse;
import com.post_hub.utils_service.model.response.PaginationResponse;
import com.post_hub.utils_service.repository.ActionLogRepository;
import com.post_hub.utils_service.service.ActionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActionLogServiceImpl implements ActionLogService {

    private final ActionLogRepository repository;
    private final ActionLogMapper mapper;

    @Override
    public ActionLogDTO getActionLog(Integer logId, Integer userId) {
        ActionLog actionLog;

        if (userId == null) {
            actionLog = repository.findById(logId)
                    .orElseThrow(() -> new NotFoundException(ApiErrorMessage.NOT_FOUND_ACTION_LOG.getMessage(logId)));
        } else {
            actionLog = repository.findByIdAndUserId(logId, userId)
                    .orElseThrow(() -> new NotFoundException(ApiErrorMessage.NOT_FOUND_ACTION_LOG_FOR_USER.getMessage(logId, userId)));
        }

        return mapper.toDto(actionLog);
    }

    @Override
    public PaginationResponse<ActionLogDTO> findAll(Pageable pageable) {

        Page<ActionLogDTO> logs = repository.findAll(pageable)
                .map(mapper::toDto);

        return new PaginationResponse<>(
                logs.stream().toList(),
                new PaginationResponse.Pagination(
                        logs.getTotalElements(),
                        pageable.getPageSize(),
                        pageable.getPageNumber() + 1,
                        logs.getTotalPages()
                )
        );
    }

    @Override
    @Transactional
    public ActionLogIsReadResponse setIsReadEqualsTrue(ActionLogIsReadRequest request) {
        Integer currentUserId = request.getUserId();

        List<ActionLog> logs = repository.findAllById(request.getIds());

        Map<Boolean, List<Integer>> partitioned = logs.stream()
                .collect(Collectors.partitioningBy(
                        log -> log.getUserId().equals(currentUserId),
                        Collectors.mapping(ActionLog::getId, Collectors.toList())
                ));

        List<Integer> allowedIds = partitioned.get(true);
        List<Integer> skippedIds = partitioned.get(false);

        int updatedCount = allowedIds.isEmpty() ? 0 : repository.setIsReadEqualsTrue(allowedIds);

        return ActionLogIsReadResponse.builder()
                .updatedCount(updatedCount)
                .updatedIds(allowedIds)
                .skippedIds(skippedIds)
                .build();
    }

    @Override
    @Transactional
    public ActionLog saveLogFromKafkaMessage(UtilMessage message) {
        ActionLog actionLog = mapper.mapKafkaMessageToEntity(message);
        return repository.save(actionLog);
    }

}
