package models;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ExternalTransfer extends InternalTransfer {
    private String receiverName;
    private String bankValue;
    private String branchValue;
}
