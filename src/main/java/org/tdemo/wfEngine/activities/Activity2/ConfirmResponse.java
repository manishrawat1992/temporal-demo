package org.tdemo.wfEngine.activities.Activity2;

import lombok.Data;

@Data
public class ConfirmResponse {
    private String result;

    public ConfirmResponse() {

    }
    public ConfirmResponse(String result) {
        this.result = result;
    }
}
