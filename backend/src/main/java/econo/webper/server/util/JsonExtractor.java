package econo.webper.server.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Component;

@Component
public class JsonExtractor {

    private static JSONParser jsonParser = new JSONParser();

    public static Object getValueByKey(String body, String key) {
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) jsonParser.parse(body);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.get(key);
    }
}
