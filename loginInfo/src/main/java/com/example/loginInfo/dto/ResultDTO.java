package com.example.loginInfo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultDTO {

    private boolean success;
    private String message;
    private Object data;

    public ResultDTO(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
