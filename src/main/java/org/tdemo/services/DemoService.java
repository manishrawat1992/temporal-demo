package org.tdemo.services;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.api.enums.v1.WorkflowIdReusePolicy;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import org.tdemo.domain.StartWorkflowRequest;
import org.tdemo.domain.WorkflowStatusRequest;
import org.tdemo.domain.WorkflowVerifyRequest;
import org.tdemo.wfEngine.DemoWorker;
import org.tdemo.wfEngine.workflows.DemoWorkflow;

import java.time.Duration;

@Singleton
public class DemoService {

    @Inject
    private WorkflowClient workflowClient;


    public String startWorkflow(StartWorkflowRequest request) {
        DemoWorkflow wfStub = workflowClient.newWorkflowStub(DemoWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setWorkflowId(request.getId())
                        .setTaskQueue(DemoWorker.taskQueueName)
                        .setWorkflowExecutionTimeout(Duration.ofHours(10))
                        .setWorkflowIdReusePolicy(WorkflowIdReusePolicy.WORKFLOW_ID_REUSE_POLICY_ALLOW_DUPLICATE)
                        .build()
        );

        WorkflowExecution wf = WorkflowClient.start(wfStub::execute);

        return wf.getWorkflowId();
    }

    public String getStage(WorkflowStatusRequest request) {
        DemoWorkflow wfStub = workflowClient.newWorkflowStub(DemoWorkflow.class, request.getWfId());

        return wfStub.getCurrentStage();
    }

    public void verify(WorkflowVerifyRequest request) {
        DemoWorkflow wfStub = workflowClient.newWorkflowStub(DemoWorkflow.class, request.getWfId());

        wfStub.signalVerified();
    }
}
