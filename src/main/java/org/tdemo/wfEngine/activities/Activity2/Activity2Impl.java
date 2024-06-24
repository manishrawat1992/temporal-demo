package org.tdemo.wfEngine.activities.Activity2;

import io.temporal.activity.ActivityExecutionContext;

public class Activity2Impl implements Activity2{

    @Override
    public void execute(ConfirmRequest confirmRequest) {

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
