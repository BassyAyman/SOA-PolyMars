package com.marsy.teamb.stagingservice.models;

import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarsyLog implements Serializable {
    private String service;
    private String message;

    @Override
    public String toString() {
        return "["+service+"]: "+message+".";
    }
}
