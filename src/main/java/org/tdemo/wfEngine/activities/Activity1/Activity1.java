package org.tdemo.wfEngine.activities.Activity1;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;
import org.tdemo.wfEngine.activities.Activity1.domain.ProcessRequest;
import org.tdemo.wfEngine.activities.Activity1.domain.ProcessingOutput;

@ActivityInterface
public interface Activity1 {

    @ActivityMethod
    ProcessingOutput startVerification(ProcessRequest request);
}
