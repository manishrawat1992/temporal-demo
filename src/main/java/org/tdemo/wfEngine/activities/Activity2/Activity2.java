package org.tdemo.wfEngine.activities.Activity2;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface Activity2 {

    @ActivityMethod
    ConfirmResponse execute(ConfirmRequest confirmRequest);

}
