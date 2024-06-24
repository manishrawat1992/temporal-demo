package org.tdemo.wfEngine.activities.Activity2;

import lombok.Data;
import org.tdemo.wfEngine.activities.Activity1.domain.ProcessingOutput;

import java.util.List;

@Data
public class ConfirmRequest {

    private String dummyId;

    private List<ProcessingOutput> outputs;

}
