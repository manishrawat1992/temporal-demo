package org.tdemo.wfEngine.activities.Activity1.domain;

import lombok.Data;

@Data
public class ProcessingOutput {
    private int id;

    private String type;

    public ProcessingOutput() {}

    public ProcessingOutput(int id) {
        this.id = id;
    }
}
