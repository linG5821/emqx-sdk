package com.ling5821.emqx.common;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ling5821.emqx.model.Listener;

/**
 * @author lsj
 * @date 2021/2/3 16:05
 */
public class GsonUtils {
    public static Gson createGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(Listener.class, new Listener.ListenerDeserializer())
            .create();
    }
}
