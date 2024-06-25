package org.tdemo.wfEngine;

import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;
import org.tdemo.wfEngine.activities.Activity1.Activity1Impl;
import org.tdemo.wfEngine.activities.Activity2.Activity2Impl;
import org.tdemo.wfEngine.wfs.DemoChildWorkflowImpl;
import org.tdemo.wfEngine.workflows.DemoWorkflow;
import org.tdemo.wfEngine.workflows.DemoWorkflowImpl;

public class DemoWorker {

    public static String taskQueueName = "default-queue";

    public static void main(String[] args) {

        // TODO While starting up clean the local directory to prevent file leakage

        // WorkflowServiceStubs is a gRPC stubs wrapper that talks to the local Docker instance of the Temporal server.
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        // Worker factory is used to create Workers that poll specific Task Queues.
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(taskQueueName);
        // This Worker hosts both Workflow and Activity implementations.

        // Workflows are stateful so a type is needed to create instances.
        worker.registerWorkflowImplementationTypes(DemoWorkflowImpl.class);
        worker.registerWorkflowImplementationTypes(DemoChildWorkflowImpl.class);

        // Activities are stateless and thread safe so a shared instance is used.
        worker.registerActivitiesImplementations(new Activity1Impl());
        worker.registerActivitiesImplementations(new Activity2Impl());

        // Start listening to the Task Queue.
        factory.start();
    }
}
