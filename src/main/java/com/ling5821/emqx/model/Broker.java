package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/1/30 1:33
 */
@NoArgsConstructor
@Data
public class Broker {

    /**
     * version : 4.2.2
     * uptime : 11 hours, 21 minutes, 9 seconds
     * sysdescr : EMQ X Broker
     * otp_release : R22/10.7.2.1
     * node_status : Running
     * node : emqx@node2.emqx.iot
     * datetime : 2021-01-29 17:24:15
     */

    private String version;
    private String uptime;
    private String sysdescr;
    private String otpRelease;
    private String nodeStatus;
    private String node;
    private String datetime;
}
