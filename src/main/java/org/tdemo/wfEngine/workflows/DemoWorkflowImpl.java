package org.tdemo.wfEngine.workflows;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;
import org.apache.commons.compress.utils.Lists;
import org.tdemo.wfEngine.activities.Activity1.Activity1;
import org.tdemo.wfEngine.activities.Activity1.domain.ProcessRequest;
import org.tdemo.wfEngine.activities.Activity1.domain.ProcessingOutput;
import org.tdemo.wfEngine.activities.Activity2.Activity2;
import org.tdemo.wfEngine.activities.Activity2.ConfirmRequest;
import org.tdemo.wfEngine.activities.Activity2.ConfirmResponse;

import java.time.Duration;
import java.util.List;
import java.util.function.Supplier;

public class DemoWorkflowImpl implements DemoWorkflow {

    String currentStage = "init";

    boolean acked = false;


    Activity1 activity1;

    Activity2 activity2;

    public DemoWorkflowImpl() {
        ActivityOptions opts = ActivityOptions.newBuilder()
                .setScheduleToCloseTimeout(Duration.ofHours(1))
                .setStartToCloseTimeout(Duration.ofHours(1))
                .build();

        activity1 = Workflow.newActivityStub(Activity1.class, opts);
        activity2 = Workflow.newActivityStub(Activity2.class, opts);
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
