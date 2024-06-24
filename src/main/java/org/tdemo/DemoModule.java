package org.tdemo;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.serviceclient.WorkflowServiceStubsOptions;

public class DemoModule extends AbstractModule {

    private static String TASK_QUEUE = "demo-queue";

    @Provides
    public WorkflowClient provideLocalTemporalClient() {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();

        // WorkflowClient can be used to start, signal, query, cancel, and terminate Workflows.
        return WorkflowClient.newInstance(service);
    }

}
