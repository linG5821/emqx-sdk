package com.ling5821.emqx.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author lsj
 * @date 2021/2/2 15:33
 */
@Data
@AllArgsConstructor
public class UploadFileBody {
    private String filename;
    private String file;
}
