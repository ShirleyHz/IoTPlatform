package com.example.iotplatform.DeviceManager.Service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @Author: hyh
 * @Description:
 * @Date: 2021/4/15 12:39 上午
 */
public class Test {
    public static void main(String[] args) {
        String s="{\"State\":{\"reported\":{\"status\":1,\"temperature\":24,\"mode\":0},\"desired\":{}},\"timestamp\":0,\"version\":0}";
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(s);
        System.out.println(element.isJsonObject());
        JsonObject object = element.getAsJsonObject();
        JsonObject state = object.getAsJsonObject("State");
        System.out.println(state.toString());

        String desired = state.get("desired").toString();
        System.out.println(desired);
    }
}
