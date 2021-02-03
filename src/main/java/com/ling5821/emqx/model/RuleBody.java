package com.ling5821.emqx.model;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author lsj
 * @date 2021/2/2 16:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RuleBody {
    private String id;
    private String rawsql;
    private List<RuleActionBody> actions;
    private String description;


    @NoArgsConstructor
    @Data
    public static class RuleActionBody {

        @SerializedName("params")
        private ActionParams params;
        @SerializedName("name")
        private String name;
        @SerializedName("id")
        private String id;
        @SerializedName("fallbacks")
        private List<RuleActionBody> fallbacks = new ArrayList<>();
    }
}
