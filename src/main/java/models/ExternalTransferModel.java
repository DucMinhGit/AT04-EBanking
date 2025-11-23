package models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExternalTransferModel {
    private String fromAccountValue;
    private String receiverAccount;
    private String receiverName;
    private String bankValue;
    private String branchValue;
    private String content;
    private double amount;
}
