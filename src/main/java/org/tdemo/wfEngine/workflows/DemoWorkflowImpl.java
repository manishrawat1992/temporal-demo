package org.tdemo.wfEngine.workflows;

import io.temporal.activity.ActivityOptions;
import io.temporal.workflow.Workflow;
import org.tdemo.wfEngine.activities.Activity1.Activity1;
import org.tdemo.wfEngine.activities.Activity1.domain.ProcessRequest;
import org.tdemo.wfEngine.activities.Activity1.domain.ProcessingOutput;
import org.tdemo.wfEngine.activities.Activity2.Activity2;
import org.tdemo.wfEngine.activities.Activity2.ConfirmRequest;

import java.time.Duration;
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

        ProcessRequest processRequest = new ProcessRequest();
        ProcessingOutput out = activity1.startVerification(processRequest);

        currentStage = "verifyAckPending";

        boolean timeout = Workflow.await(Duration.ofHours(1), () -> acked);

        currentStage = "verified";

        ConfirmRequest confirmRequest = new ConfirmRequest();
        activity2.execute(confirmRequest);

        currentStage = "completed";
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
