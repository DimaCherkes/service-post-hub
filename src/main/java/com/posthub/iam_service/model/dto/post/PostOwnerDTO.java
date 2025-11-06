package com.posthub.iam_service.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostOwnerDTO {

    private Integer id;
    private String username;
    private String email;
}
