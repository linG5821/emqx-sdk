package com.ling5821.emqx.model;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author lsj
 * @date 2021/2/2 10:28
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Listener {

    /**
     * shutdown_count : []
     * protocol : mqtt:ssl
     * max_conns : 102400
     * listen_on : 8883
     * current_conns : 0
     * acceptors : 32
     */

    private String protocol;
    @SerializedName("max_conns")
    private Integer maxConns;
    @SerializedName("listen_on")
    private String listenOn;
    @SerializedName("current_conns")
    private Integer currentConns;
    private Integer acceptors;
    @Expose(deserialize = false)
    private List<ShutdownCount> shutdownCount;

    @Data
    public static class ShutdownCount {
        private Integer normal;
        private Integer kicked;
        private Integer discarded;
        private Integer takeovered;
    }

    public static class ListenerDeserializer implements JsonDeserializer<Listener> {

        @Override
        public Listener deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
            Gson gson = new Gson();
            Listener listener = gson.fromJson(json, Listener.class);
            JsonObject jsonObject = json.getAsJsonObject();

            if (jsonObject.has("shutdown_count")) {
                JsonElement elem = jsonObject.get("shutdown_count");
                if (elem != null && !elem.isJsonNull()) {
                    if (elem.isJsonArray()) {
                        listener.setShutdownCount(gson.fromJson(elem, new TypeToken<List<ShutdownCount>>() {
                        }.getType()));
                    } else if (elem.isJsonObject()) {
                        listener.setShutdownCount(Collections.singletonList(gson.fromJson(elem, ShutdownCount.class)));
                    }
                }
            }
            return listener;
        }
    }
}
