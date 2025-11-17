package testdata;

import lombok.Data;
import models.ExternalTransferModel;

@Data
public class ValidationTestCase {
    private String caseId;
    private ExternalTransferModel data;
    private String expectedErrorMessage;
}