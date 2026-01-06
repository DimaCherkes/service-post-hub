package com.post_hub.utils_service.mapper;

import com.post_hub.utils_service.entity.ActionLog;
import com.post_hub.utils_service.kafka.model.UtilMessage;
import com.post_hub.utils_service.model.dto.ActionLogDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.time.LocalDateTime;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {LocalDateTime.class}
)
public interface ActionLogMapper {

    @Mapping(target = "createdAt", expression = "java(LocalDateTime.now())")
    @Mapping(target = "isRead", expression = "java(false)")
    @Mapping(target = "id", ignore = true)
    ActionLog mapKafkaMessageToEntity(UtilMessage message);

    ActionLogDTO toDto(ActionLog log);
}
