package com.marsy.teamb.rocketservice.modele;

import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;

@Builder
@Getter
public class MarsyLog implements Serializable {
    private String service;
    private String message;

    @Override
    public String toString() {
        return "["+service+"]: "+message+".";
    }
}
