package com.project.dtos.operater;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperaterRequestDto {
    private String email;
    private String password;
}
