package com.example.firstDbProject.DtoObjects;

import lombok.Data;

@Data
public class MsgDto {
    private String msg;


    public MsgDto(String mensaje) {
        this.msg = mensaje;
    }
}
