package models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class InternalTransfer {
    private String fromAccountValue;
    private String receiverAccount;
    private String content;
    private Double amount;
}
