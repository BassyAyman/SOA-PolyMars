package com.marsy.teamb.commandservice.modele;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
@JsonDeserialize(using = MarsyLogDeserializer.class)
public class MarsyLog implements Serializable {
    private String service;
    private String message;

    public MarsyLog(){}

    public MarsyLog(String service, String message){
        this.service = service;
        this.message = message;
    }

    @Override
    public String toString() {
        return "["+service+"]: "+message+".";
    }
}
