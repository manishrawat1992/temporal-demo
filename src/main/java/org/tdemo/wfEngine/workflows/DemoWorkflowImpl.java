package org.tdemo.wfEngine.workflows;

import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.*;
import org.apache.commons.compress.utils.Lists;
import org.tdemo.wfEngine.activities.Activity1.Activity1;
import org.tdemo.wfEngine.activities.Activity1.domain.ProcessRequest;
import org.tdemo.wfEngine.activities.Activity1.domain.ProcessingOutput;
import org.tdemo.wfEngine.activities.Activity2.Activity2;
import org.tdemo.wfEngine.activities.Activity2.ConfirmRequest;
import org.tdemo.wfEngine.activities.Activity2.ConfirmResponse;
import org.tdemo.wfEngine.wfs.DemoChildWorkflow;
import org.tdemo.wfEngine.wfs.domain.ChildWorkflowRequest;
import org.tdemo.wfEngine.wfs.domain.ChildWorkflowResponse;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

public class DemoWorkflowImpl implements DemoWorkflow {

    String currentStage = "init";

    boolean acked = false;

    Activity1 activity1;

    Activity2 activity2;

    private final DemoChildWorkflow wf1;

    public DemoWorkflowImpl() {
        ActivityOptions opts = ActivityOptions.newBuilder()
                .setScheduleToCloseTimeout(Duration.ofHours(1))
                .setStartToCloseTimeout(Duration.ofHours(1))
                .build();

        activity1 = Workflow.newActivityStub(Activity1.class, opts);
        activity2 = Workflow.newActivityStub(Activity2.class, opts);

        RetryOptions retryOptions = RetryOptions.newBuilder()
                .setMaximumAttempts(1)
                .build();

        wf1 = Workflow.newChildWorkflowStub(DemoChildWorkflow.class,
                ChildWorkflowOptions.newBuilder()
                        .setRetryOptions(retryOptions)
                        .build()
        );
    }

    @Override
    public void execute() {

        Workflow.sleep(5000);

        currentStage = "started";

        List<Promise<ProcessingOutput>> output = Lists.newArrayList();

        for(int i =0 ; i < 10; i++) {
            ProcessRequest processRequest = new ProcessRequest();
            processRequest.setId(i);
            Promise<ProcessingOutput> future = Async.function(activity1::startVerification, processRequest);

            output.add(future);
        }

        List<ProcessingOutput> outputs = Lists.newArrayList();
        for(Promise<ProcessingOutput> future: output) {
            outputs.add(future.get());
        }

        currentStage = "childWf";

        try {

            Promise<ChildWorkflowResponse> resp = Async.function(wf1::execute, new ChildWorkflowRequest("32"));

            ChildWorkflowResponse re = resp.get();

        } catch (Exception e) {
            System.out.println("Reverting workflow");
        }

        currentStage = "verifyAckPending";

        boolean timeout = Workflow.await(Duration.ofHours(1), () -> acked);

        currentStage = "verified";

        ConfirmRequest confirmRequest = new ConfirmRequest();
        confirmRequest.setOutputs(outputs);
        ConfirmResponse resp1 = activity2.execute(confirmRequest);

        currentStage = "completed :" + resp1.getResult();
    }

    @Override
    public String getCurrentStage() {
        return currentStage;
    }

    @Override
    public void signalVerified() {
        acked = true;
    }

}
