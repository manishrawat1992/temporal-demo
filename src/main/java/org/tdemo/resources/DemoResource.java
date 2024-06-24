package org.tdemo.resources;

import com.google.inject.Inject;
import jakarta.annotation.Resource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.tdemo.domain.StartWorkflowRequest;
import org.tdemo.domain.WorkflowStatusRequest;
import org.tdemo.domain.WorkflowVerifyRequest;
import org.tdemo.services.DemoService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class DemoResource {

    @Inject
    private DemoService demoService;

    @Path("start")
    @POST
    public String start(StartWorkflowRequest startRequest) {
        return demoService.startWorkflow(startRequest);

    }

    @Path("/status")
    @GET
    public String getCurrentStage(WorkflowStatusRequest wfId) {
        return demoService.getStage(wfId);
    }

    @Path("/verify")
    @POST
    public String verify(WorkflowVerifyRequest wfId) {
        demoService.verify(wfId);
        return "done";
    }

}
