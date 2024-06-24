package org.tdemo.wfEngine.activities.Activity1;

import org.tdemo.wfEngine.activities.Activity1.domain.ProcessRequest;
import org.tdemo.wfEngine.activities.Activity1.domain.ProcessingOutput;

import java.util.Random;

public class Activity1Impl implements Activity1{

    @Override
    public ProcessingOutput startVerification(ProcessRequest request) {

        try {
            Thread.sleep(5000 + new Random(System.currentTimeMillis()).nextInt(5000) );
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return new ProcessingOutput(request.getId());
    }
}
