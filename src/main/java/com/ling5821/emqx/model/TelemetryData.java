package com.ling5821.emqx.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author lsj
 * @date 2021/2/2 18:33
 */
@NoArgsConstructor
@Data
public class TelemetryData {

    /**
     * uuid : 649AD1B0-6540-11EB-A638-A988928AEDF5
     * up_time : 208812
     * otp_version : 22
     * os_version : 3.11.6
     * os_name : Alpine Linux
     * num_clients : 0
     * nodes_uuid : ["53CB08EA-6541-11EB-85CD-931A09B88EE6"]
     * messages_sent : 0
     * messages_received : 0
     * license : {"edition":"community"}
     * emqx_version : 4.2.2
     * active_plugins : ["emqx_telemetry","emqx_rule_engine","emqx_retainer","emqx_recon","emqx_management","emqx_exproto","emqx_dashboard","emqx_auth_mysql"]
     * active_modules : ["emqx_mod_presence","emqx_mod_acl_internal","emqx_mod_topic_metrics","emqx_mod_delayed"]
     */

    private String uuid;
    private Integer upTime;
    private String otpVersion;
    private String osVersion;
    private String osName;
    private Integer numClients;
    private List<String> nodesUuid;
    private Integer messagesSent;
    private Integer messagesReceived;
    private LicenseBean license;
    private String emqxVersion;
    private List<String> activePlugins;
    private List<String> activeModules;

    @NoArgsConstructor
    @Data
    public static class LicenseBean {
        /**
         * edition : community
         */
        private String edition;
    }
}
