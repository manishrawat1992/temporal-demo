package org.tdemo.wfEngine.wfs;

import io.temporal.api.failure.v1.ApplicationFailureInfoOrBuilder;
import io.temporal.failure.ApplicationFailure;
import io.temporal.failure.CanceledFailure;
import io.temporal.workflow.Workflow;
import org.tdemo.wfEngine.wfs.domain.ChildWorkflowRequest;
import org.tdemo.wfEngine.wfs.domain.ChildWorkflowResponse;

import java.util.Random;

public class DemoChildWorkflowImpl implements DemoChildWorkflow {
    @Override
    public ChildWorkflowResponse execute(ChildWorkflowRequest request)  {

        Workflow.sleep(10000);

        if (new Random(System.currentTimeMillis()).nextInt(10) < 10) {
            throw ApplicationFailure.newNonRetryableFailure("exit", "graceful");
        }

        return new ChildWorkflowResponse("yo");

    }
}
