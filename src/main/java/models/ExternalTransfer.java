package models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExternalTransfer extends InternalTransfer {
    private String receiverName;
    private String bankValue;
    private String branchValue;
}