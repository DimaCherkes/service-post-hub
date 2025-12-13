package com.posthub.iam_service.model.dto.role;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO implements Serializable {
    private Integer id;
    private String name;
}