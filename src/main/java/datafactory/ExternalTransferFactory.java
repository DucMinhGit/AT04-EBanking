package datafactory;

import models.ExternalTransfer;

public class ExternalTransferFactory {
    public static ExternalTransfer initData() {
        ExternalTransfer data = new ExternalTransfer();
        data.setReceiverAccount("10001111");
        data.setReceiverName("Nguyen Van A");
        data.setBankValue("Ngân hàng Đông Á");
        data.setBranchValue("Chi nhánh Đà Nẵng");
        return data;
    }
}
