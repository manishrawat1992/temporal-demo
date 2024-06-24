package org.tdemo.wfEngine.workflows;

import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface DemoWorkflow {

    @WorkflowMethod
    void execute();

    @QueryMethod
    String getCurrentStage();

    @SignalMethod
    void signalVerified();

}
