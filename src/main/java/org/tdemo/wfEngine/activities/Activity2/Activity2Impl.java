package org.tdemo.wfEngine.activities.Activity2;

import io.temporal.activity.ActivityExecutionContext;
import org.tdemo.wfEngine.activities.Activity1.domain.ProcessingOutput;

public class Activity2Impl implements Activity2{

    @Override
    public ConfirmResponse execute(ConfirmRequest confirmRequest) {

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        StringBuilder result = new StringBuilder();

        for (ProcessingOutput o : confirmRequest.getOutputs()) {
            result.append(" ").append(o.getId());
        }

        return new ConfirmResponse(result.toString());
    }
}
