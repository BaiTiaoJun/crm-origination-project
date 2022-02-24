package name.btjproject.crm.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PrintJsonUtil {
    public static void printBoolJson(HttpServletResponse response, Boolean bool) {
        Map<String, Boolean> map = new HashMap<>();
        ObjectMapper mapper = new ObjectMapper();
        map.put("success", bool);
        try {
            String jsonStr = mapper.writeValueAsString(map);
            response.getWriter().print(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printObjectJson(HttpServletResponse response, Object object) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonStr = mapper.writeValueAsString(object);
            response.getWriter().print(jsonStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
