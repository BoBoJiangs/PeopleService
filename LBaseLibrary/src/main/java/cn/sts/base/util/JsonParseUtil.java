package cn.sts.base.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析json
 *
 * @author weilin
 */
public class JsonParseUtil {

    // private static Gson gson = new Gson();
    private static Gson dateGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
            .create();

    /**
     * 对象转成json字符串
     *
     * @param obj 对象
     */
    public static String objToJson(Object obj) {
        return dateGson.toJson(obj);
    }

    /**
     * json字符串转换成List对象
     *
     * @param json  json字符串{"mv":"2015-10-10 22:22","success":true,"data":[{},{},...]}
     * @param clazz 对象类
     */
    public static <T> List<T> jsonToList(String json, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            boolean success = (Boolean) jsonObject.get("success");
            if (success) {
                JSONArray array = (JSONArray) jsonObject.get("data");
                if (array != null) {
                    int len = array.length();
                    for (int i = 0; i < len; i++) {
                        list.add(jsonStrToObject((array.get(i)).toString(), clazz));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * json字符串转换成List对象
     *
     * @param jsonArray JSONArray字符串[{},{},...]
     * @param clazz     对象类
     */
    public static <T> List<T> jsonStrToList(String jsonArray, Class<T> clazz) {
        List<T> list = new ArrayList<>();
        try {
            JSONArray array = new JSONArray(jsonArray);
            int len = array.length();
            for (int i = 0; i < len; i++) {
                list.add(jsonToObject((array.get(i)).toString(), clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * json字符串转换成对象
     *
     * @param json  json字符串{"mv":"2015-10-10 22:22","success":true,"data":{"a":"","b":"",...}
     * @param clazz 对象类
     */
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(json);
            boolean success = (Boolean) jsonObject.get("success");
            if (success) {
                return jsonStrToObject(jsonObject.getString("data"), clazz);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转成对象
     *
     * @param json  JSONObject字符串{"a":"","b":"",...}
     * @param clazz 对象类
     */
    public static <T> T jsonStrToObject(String json, Class<T> clazz) {
        return dateGson.fromJson(json, clazz);
    }

    /**
     * json校正，是否字符串值中有英文引号，有就替换成中文引号
     *
     * @param json json字符串
     * @return 检查后的json字符串
     */
    public static String jsonCorrect(String json) {
        char[] temp = json.toCharArray();
        int n = temp.length;
        for (int i = 0; i < n; i++) {
            if (temp[i] == ':' && temp[i + 1] == '"') {
                for (int j = i + 2; j < n; j++) {
                    if (temp[j] == '"') {
                        if (temp[j + 1] != ',' && temp[j + 1] != '}') {
                            temp[j] = '”';
                        } else if (temp[j + 1] == ',' || temp[j + 1] == '}') {
                            break;
                        }
                    }
                }
            }
        }
        return new String(temp);
    }


}
