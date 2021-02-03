package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lsj
 * @date 2021/1/30 2:18
 */
@NoArgsConstructor
@Data
public class Node {

    /**
     * version : 4.2.2
     * uptime : 12 hours, 18 minutes, 34 seconds
     * process_used : 467
     * process_available : 2097152
     * otp_release : R22/10.7.2.1
     * node_status : Running
     * node : emqx@node2.emqx.iot
     * memory_used : 120608720
     * memory_total : 175726592
     * max_fds : 1048576
     * load5 : 0.60
     * load15 : 0.56
     * load1 : 0.42
     * connections : 0
     */

    private String version;
    private String uptime;
    private Integer processUsed;
    private Integer processAvailable;
    private String otpRelease;
    private String nodeStatus;
    private String node;
    private String memoryUsed;
    private String memoryTotal;
    private Integer maxFds;
    private String load5;
    private String load15;
    private String load1;
    private Integer connections;
}
