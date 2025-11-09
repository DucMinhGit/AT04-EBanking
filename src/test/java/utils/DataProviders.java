package utils;

import com.google.gson.reflect.TypeToken;
import org.testng.annotations.DataProvider;
import data.ExternalTransferData;
import java.util.List;

public class DataProviders {
    private static final String TRANSFER_FILE = "src/test/resources/data/ext01_data.json";
    private static final String TRANSFER_ERROR_FILE = "src/test/resources/data/ext01_error_data.json";

    @DataProvider(name = "externalTransferDataFromJson")
    public static Object[][] getExternalTransferData() {
        return JsonUtils.getDataAsObjectArray(
                TRANSFER_FILE,
                new TypeToken<List<ExternalTransferData>>() {}
        );
    }

    @DataProvider(name = "externalTransferErrorDataFromJson")
    public static Object[][] getExternalTransferErrorData() {
        return JsonUtils.getDataAsObjectArray(
                TRANSFER_ERROR_FILE,
                new TypeToken<List<ExternalTransferData>>() {} // Vẫn dùng cùng Data Class
        );
    }
}