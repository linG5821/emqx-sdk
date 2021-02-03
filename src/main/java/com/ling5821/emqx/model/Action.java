package com.ling5821.emqx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author lsj
 * @date 2021/2/2 16:18
 */

@NoArgsConstructor
@Data
public class Action {
    private List<String> types;
    private CommonTitle title;
    private Map<String, Param> params;
    private String name;
    @SerializedName("for")
    private String forX;
    private CommonTitle description;
    private String category;
    private String app;
}
