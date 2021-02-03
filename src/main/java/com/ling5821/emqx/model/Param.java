package com.ling5821.emqx.model;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lsj
 * @date 2021/2/2 19:17
 */
@NoArgsConstructor
@Data
public class Param {
    private String type;
    private CommonTitle title;
    private Boolean required;
    private Integer order;
    private String input;
    @SerializedName("enum")
    private List<String> enums;
    private CommonTitle description;
    @SerializedName("default")
    private Object defaultValue;
}
