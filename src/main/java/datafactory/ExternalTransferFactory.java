package datafactory;

import models.ExternalTransfer;

public class ExternalTransferFactory {
    public static ExternalTransfer initData() {
        return ExternalTransfer.builder()
                .fromAccountValue("100001915")
                .receiverAccount("10001111")
                .content("valid external")
                .amount(5000.0)
                .receiverName("Nguyen Van A")
                .bankValue("Ngân hàng Đông Á")
                .branchValue("Chi nhánh Đà Nẵng")
                .build();
    }
}
