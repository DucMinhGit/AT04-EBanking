package utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;

public class JsonUtils {
    private static final Gson gson = new Gson();

    public static <T> List<T> readJsonFileToList(String jsonFilePath, TypeToken<List<T>> typeToken) {
        try (Reader reader = new FileReader(jsonFilePath)) {
            return gson.fromJson(reader, typeToken.getType());
        } catch (Exception e) {
            throw new RuntimeException("Can not read parse file JSON: " + jsonFilePath, e);
        }
    }

    public static <T> Object[][] getDataAsObjectArray(String jsonFilePath, TypeToken<List<T>> typeToken) {
        List<T> dataList = readJsonFileToList(jsonFilePath, typeToken);

        Object[][] data = new Object[dataList.size()][1];
        for (int i = 0; i < dataList.size(); i++) {
            data[i][0] = dataList.get(i);
        }
        return data;
    }
}