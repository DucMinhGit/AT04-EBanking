package data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ExternalTransferData {
    private final String caseId;
    private final String fromAccountValue;
    private final String receiverAccount;
    private final String receiverName;
    private final String bankValue;
    private final String branchValue;
    private final String content;
    private final String amount;
    private final String expectedErrorMessage;
}
