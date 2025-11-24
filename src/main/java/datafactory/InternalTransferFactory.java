package datafactory;

import models.InternalTransfer;
import utils.Constants;

public class InternalTransferFactory {
    public static InternalTransfer initData() {
        return InternalTransfer.builder()
                .fromAccountValue("100001440")
                .receiverAccount("100001457")
                .content("valid transfer")
                .amount(50000.0)
                .build();
    }
}
