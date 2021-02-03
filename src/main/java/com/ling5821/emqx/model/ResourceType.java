package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author lsj
 * @date 2021/2/2 17:05
 */
@NoArgsConstructor
@Data
public class ResourceType {

    private CommonTitle title;
    private String provider;
    private Map<String, Param> params;
    private String name;
    private CommonTitle description;
}
