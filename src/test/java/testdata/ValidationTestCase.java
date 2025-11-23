package testdata;

import lombok.Data;
import models.ExternalTransfer;

@Data
public class ValidationTestCase {
    private String caseId;
    private ExternalTransfer data;
    private String expectedErrorMessage;
}