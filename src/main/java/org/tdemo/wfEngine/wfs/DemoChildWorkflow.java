package org.tdemo.wfEngine.wfs;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
import org.tdemo.wfEngine.wfs.domain.ChildWorkflowRequest;
import org.tdemo.wfEngine.wfs.domain.ChildWorkflowResponse;

@WorkflowInterface
public interface DemoChildWorkflow {

    @WorkflowMethod
    ChildWorkflowResponse execute(ChildWorkflowRequest request) ;

}
