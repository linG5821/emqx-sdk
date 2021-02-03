package com.ling5821.emqx;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.URLUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.Method;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.ling5821.emqx.common.GsonUtils;
import com.ling5821.emqx.model.*;

import java.io.File;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author lsj
 * @date 2021/1/29 20:06
 */
public class EmqxHttpClient {

    private EmqxConfig config;
    private Gson gson;

    public EmqxHttpClient(EmqxConfig config) {
        this.config = config;
        this.gson = GsonUtils.createGson();
    }

    private String request(Method method, String path, Object body) {
        if (method == Method.GET && body != null) {
            path = path + "?" + URLUtil.buildQuery(BeanUtil.beanToMap(body, true, true), StandardCharsets.UTF_8);
        }

        UrlBuilder urlBuilder = getUrlBuilder(path);
        System.out.println(urlBuilder.toString());
        HttpRequest request =
            new HttpRequest(urlBuilder).method(method).basicAuth(config.getUsername(), config.getPassword());
        if ((method == Method.POST || method == Method.PUT) && body != null) {
            request.body(gson.toJson(body));
        }
        HttpResponse response = request.execute();
        if (!response.isOk()) {
            throw new RuntimeException("request error: " + response.getStatus());
        }
        System.out.println(response.body());
        return response.body();
    }

    private <T> T request(Method method, String path, Object body, Type type) {
        return gson.fromJson(request(method, path, body), type);
    }

    private <T> T request(Method method, String path, Object body, Class<T> clazz) {
        return gson.fromJson(request(method, path, body), clazz);
    }

    private <T> T request(Method method, String path, Object body, TypeToken<T> typeToken) {
        return gson.fromJson(request(method, path, body), typeToken.getType());
    }

    private <T> T request(Method method, String path, TypeToken<T> typeToken) {
        return gson.fromJson(request(method, path, (Object)null), typeToken.getType());
    }

    private String request(Method method, String path) {
        return request(method, path, (Object)null);
    }

    private UrlBuilder getUrlBuilder(String path) {
        return UrlBuilder.ofHttpWithoutEncode(config.getAddress().replaceAll("\\/$", "") + path);
    }

    private void checkResponse(EmqxResponse response) {
        if (response.getCode() != EmqxResponseStatus.SUCCESS) {
            throw new RuntimeException("request error: " + response.getCode() + " body: " + response);
        }
    }

    private void checkList(EmqxResponse response) {
        if (response.getData() == null) {
            response.setData(Collections.emptyList());
        }
    }

    private void checkList(EmqxPageResponse response) {
        checkList((EmqxResponse)response);
    }

    /**
     * 获取所有Brokers
     *
     * @return
     */
    public List<Broker> getBrokers() {
        EmqxResponse<List<Broker>> response =
            request(Method.GET, "/api/v4/brokers", new TypeToken<EmqxResponse<List<Broker>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回集群下所有节点的基本信息
     *
     * @param node
     * @return
     */
    public Broker getBroker(String node) {
        EmqxResponse<Broker> response =
            request(Method.GET, "/api/v4/brokers/" + node, new TypeToken<EmqxResponse<Broker>>() {
            });
        checkResponse(response);
        return response.getData();
    }

    /**
     * 返回节点的状态
     *
     * @return
     */
    public List<Node> getNodes() {
        EmqxResponse<List<Node>> response =
            request(Method.GET, "/api/v4/nodes", new TypeToken<EmqxResponse<List<Node>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回节点的状态
     *
     * @param node
     * @return
     */
    public Node getNode(String node) {
        EmqxResponse<Node> response = request(Method.GET, "/api/v4/nodes/" + node, new TypeToken<EmqxResponse<Node>>() {
        });
        checkResponse(response);
        return response.getData();
    }

    /**
     * 返回集群下所有客户端的信息，支持分页
     *
     * @param query
     * @return
     */
    public EmqxPageResponse<List<Client>> getClients(ClientQuery query) {
        EmqxPageResponse<List<Client>> response =
            request(Method.GET, "/api/v4/clients", query, new TypeToken<EmqxPageResponse<List<Client>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response;
    }

    /**
     * 返回指定节点下所有客户端的信息，支持分页
     *
     * @param query
     * @param node
     * @return
     */
    public EmqxPageResponse<List<Client>> getClientsByNode(PageQuery query, String node) {
        EmqxPageResponse<List<Client>> response = request(Method.GET, "/api/v4/nodes/" + node + "/clients", query,
            new TypeToken<EmqxPageResponse<List<Client>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response;
    }

    /**
     * 返回指定节点下指定客户端的信息
     *
     * @param node
     * @param clientId
     * @return
     */
    public Optional<Client> getClientByNode(String node, String clientId) {
        EmqxResponse<List<Client>> response = request(Method.GET, "/api/v4/nodes/" + node + "/clients/" + clientId,
            new TypeToken<EmqxResponse<List<Client>>>() {
            });
        checkResponse(response);
        return response.getData().stream().findFirst();
    }

    /**
     * 返回指定客户端的信息
     *
     * @param clientId
     * @return
     */
    public Optional<Client> getClient(String clientId) {
        EmqxResponse<List<Client>> response =
            request(Method.GET, "/api/v4/clients/" + clientId, new TypeToken<EmqxResponse<List<Client>>>() {
            });
        checkResponse(response);
        return response.getData().stream().findFirst();
    }

    /**
     * 踢除指定客户端。注意踢除客户端操作会将连接与会话一并终结
     *
     * @param clientId
     * @return
     */
    public boolean kickedClient(String clientId) {
        EmqxResponse<Object> response =
            request(Method.DELETE, "/api/v4/clients/" + clientId, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 剔除指定节点下的指定客户端
     * 文档上存在接口,实际调用不存在
     *
     * @param node
     * @param clientId
     * @return
     */
    public boolean kickedClientByNode(String node, String clientId) {
        EmqxResponse<Object> response = request(Method.DELETE, "/api/v4/nodes/" + node + "/clients/" + clientId,
            new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 通过 Username 查询客户端的信息。由于可能存在多个客户端使用相同的用户名的情况，所以可能同时返回多个客户端信息
     *
     * @param username
     * @return
     */
    public List<Client> getClientsByUsername(String username) {
        EmqxResponse<List<Client>> response =
            request(Method.GET, "/api/v4/clients/username/" + username, new TypeToken<EmqxResponse<List<Client>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 在指定节点下，通过 Username 查询指定客户端的信息
     *
     * @param node
     * @param username
     * @return
     */
    public List<Client> getClientsByNodeAndUsername(String node, String username) {
        EmqxResponse<List<Client>> response =
            request(Method.GET, "/api/v4/nodes/" + node + "/clients/username/" + username,
                new TypeToken<EmqxResponse<List<Client>>>() {
                });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 查询指定客户端的 ACL 缓存
     *
     * @param clientId
     * @return
     */
    public List<AclCache> getAclCaches(String clientId) {
        EmqxResponse<List<AclCache>> response = request(Method.GET, "/api/v4/clients/" + clientId + "/acl_cache",
            new TypeToken<EmqxResponse<List<AclCache>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 清除指定客户端的 ACL 缓存
     *
     * @param clientId
     * @return
     */
    public boolean deleteAclCaches(String clientId) {
        EmqxResponse<Object> response =
            request(Method.DELETE, "/api/v4/clients/" + clientId + "/acl_cache", new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 返回集群下所有订阅信息，支持分页机制
     *
     * @param query
     * @return
     */
    public EmqxPageResponse<List<Subscription>> getSubscriptions(SubscriptionQuery query) {
        EmqxPageResponse<List<Subscription>> response =
            request(Method.GET, "/api/v4/subscriptions", query, new TypeToken<EmqxPageResponse<List<Subscription>>>() {
            }.getType());
        checkResponse(response);
        checkList(response);
        return response;
    }

    /**
     * 返回集群下指定客户端的订阅信息
     *
     * @param clientId
     * @return
     */
    public List<Subscription> getSubscriptions(String clientId) {
        EmqxResponse<List<Subscription>> response =
            request(Method.GET, "/api/v4/subscriptions/" + clientId, new TypeToken<EmqxResponse<List<Subscription>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回指定节点下的所有订阅信息，支持分页机制
     *
     * @param node
     * @param query
     * @return
     */
    public EmqxPageResponse<List<Subscription>> getSubscriptionsByNode(String node, SubscriptionQuery query) {
        EmqxPageResponse<List<Subscription>> response =
            request(Method.GET, "/api/v4/nodes/" + node + "/subscriptions", query,
                new TypeToken<EmqxPageResponse<List<Subscription>>>() {
                });
        checkResponse(response);
        checkList(response);
        return response;
    }

    /**
     * 在指定节点下，查询某 clientid 的所有订阅信息，支持分页机制
     *
     * @param node
     * @param clientId
     * @return
     */
    public EmqxPageResponse<List<Subscription>> getSubscriptionsByNodeAndClientId(String node, String clientId) {
        EmqxPageResponse<List<Subscription>> response =
            request(Method.GET, "/api/v4/nodes/" + node + "/subscriptions/" + clientId,
                new TypeToken<EmqxPageResponse<List<Subscription>>>() {
                });
        checkResponse(response);
        checkList(response);
        return response;
    }

    /**
     * 返回集群下的所有路由信息，支持分页机制
     *
     * @param query
     * @return
     */
    public EmqxPageResponse<List<Route>> getRoutes(PageQuery query) {
        EmqxPageResponse<List<Route>> response =
            request(Method.GET, "/api/v4/routes", query, new TypeToken<EmqxPageResponse<List<Route>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response;
    }

    /**
     * 返回集群下指定主题的路由信息
     *
     * @param topic
     * @return
     */
    public Optional<Route> getRoute(String topic) {
        EmqxPageResponse<List<Route>> response = request(Method.GET, "/api/v4/routes/" + URLUtil.encodeAll(topic),
            new TypeToken<EmqxPageResponse<List<Route>>>() {
            });
        checkResponse(response);
        return response.getData().stream().findFirst();
    }

    /**
     * 发布 MQTT 消息
     *
     * @param message
     * @return
     */
    public boolean publish(MqttMessageBody message) {
        EmqxResponse<Object> response =
            request(Method.POST, "/api/v4/mqtt/publish", message, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 订阅 MQTT 主题
     *
     * @param body
     * @return
     */
    public boolean subscribe(SubscribeBody body) {
        EmqxResponse<Object> response =
            request(Method.POST, "/api/v4/mqtt/subscribe", body, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 取消订阅
     *
     * @param body
     * @return
     */
    public boolean unsubscribe(UnSubscribeBody body) {
        EmqxResponse<Object> response =
            request(Method.POST, "/api/v4/mqtt/unsubscribe", body, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 批量发布 MQTT 消息
     *
     * @param messages
     * @return
     */
    public boolean publishBatch(List<MqttMessageBody> messages) {
        EmqxResponse<Object> response =
            request(Method.POST, "/api/v4/mqtt/publish_batch", messages, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 批量订阅 MQTT 主题
     *
     * @param bodyList
     * @return
     */
    public boolean subscribeBatch(List<SubscribeBody> bodyList) {
        EmqxResponse<Object> response =
            request(Method.POST, "/api/v4/mqtt/subscribe_batch", bodyList, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 批量取消订阅
     *
     * @param bodyList
     * @return
     */
    public List<UnSubscribeResponse> unsubscribeBatch(List<UnSubscribeBody> bodyList) {
        EmqxResponse<List<UnSubscribeResponse>> response =
            request(Method.POST, "/api/v4/mqtt/unsubscribe_batch", bodyList,
                new TypeToken<EmqxResponse<List<UnSubscribeResponse>>>() {
                });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回集群下的所有插件信息
     *
     * @return
     */
    public List<NodePlugin> getPlugins() {
        EmqxResponse<List<NodePlugin>> response =
            request(Method.GET, "/api/v4/plugins", new TypeToken<EmqxResponse<List<NodePlugin>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回指定节点下的插件信息
     *
     * @param node
     * @return
     */
    public List<Plugin> getPluginsByNode(String node) {
        EmqxResponse<List<Plugin>> response =
            request(Method.GET, "/api/v4/nodes/" + node + "/plugins", new TypeToken<EmqxResponse<List<Plugin>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 加载指定节点下的指定插件
     *
     * @param node
     * @param plugin
     * @return
     */
    public boolean loadPlugin(String node, String plugin) {
        EmqxResponse<Object> response = request(Method.PUT, "/api/v4/nodes/" + node + "/plugins/" + plugin + "/load",
            new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 卸载指定节点下的指定插件
     *
     * @param node
     * @param plugin
     * @return
     */
    public boolean unloadPlugin(String node, String plugin) {
        EmqxResponse<Object> response = request(Method.PUT, "/api/v4/nodes/" + node + "/plugins/" + plugin + "/unload",
            new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 返回集群下的所有监听器信息
     *
     * @return
     */
    public List<NodeListener> getListeners() {
        EmqxResponse<List<NodeListener>> response =
            request(Method.GET, "/api/v4/listeners", new TypeToken<EmqxResponse<List<NodeListener>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回指定节点的监听器信息
     *
     * @param node
     * @return
     */
    public List<Listener> getListenersByNode(String node) {
        EmqxResponse<List<Listener>> response =
            request(Method.GET, "/api/v4/nodes/" + node + "/listeners", new TypeToken<EmqxResponse<List<Listener>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回集群下所有内置模块信息
     *
     * @return
     */
    public List<NodeModule> getModules() {
        EmqxResponse<List<NodeModule>> response =
            request(Method.GET, "/api/v4/modules", new TypeToken<EmqxResponse<List<NodeModule>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回指定节点下所有内置模块信息
     *
     * @param node
     * @return
     */
    public List<Module> getModulesByNode(String node) {
        EmqxResponse<List<Module>> response =
            request(Method.GET, "/api/v4/nodes/" + node + "/modules", new TypeToken<EmqxResponse<List<Module>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 加载集群下所有节点的指定内置模块
     *
     * @param module
     * @return
     */
    public boolean loadModule(String module) {
        EmqxResponse<Object> response =
            request(Method.PUT, "/api/v4/modules/" + module + "/load", new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 加载指定节点下的指定内置模块
     *
     * @param module
     * @return
     */
    public boolean loadModuleByNode(String node, String module) {
        EmqxResponse<Object> response = request(Method.PUT, "/api/v4/nodes/" + node + "/modules/" + module + "/load",
            new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 卸载集群下所有节点的指定内置模块
     *
     * @param module
     * @return
     */
    public boolean unloadModule(String module) {
        EmqxResponse<Object> response =
            request(Method.PUT, "/api/v4/modules/" + module + "/unload", new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 卸载指定节点下的指定内置模块
     *
     * @param module
     * @return
     */
    public boolean unloadModuleByNode(String node, String module) {
        EmqxResponse<Object> response = request(Method.PUT, "/api/v4/nodes/" + node + "/modules/" + module + "/unload",
            new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 重新加载集群下所有节点的指定内置模块
     * 仅支持 emqx_mod_acl_internal
     *
     * @param module
     * @return
     */
    public boolean reloadModule(String module) {
        EmqxResponse<Object> response =
            request(Method.PUT, "/api/v4/modules/" + module + "/reload", new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 重新加载指定节点下的指定内置模块
     * 仅支持 emqx_mod_acl_internal
     *
     * @param module
     * @return
     */
    public boolean reloadModuleByNode(String node, String module) {
        EmqxResponse<Object> response = request(Method.PUT, "/api/v4/nodes/" + node + "/modules/" + module + "/reload",
            new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 返回集群下所有统计指标数据
     *
     * @return
     */
    public List<NodeMetrics> getMetrics() {
        EmqxResponse<List<NodeMetrics>> response =
            request(Method.GET, "/api/v4/metrics", new TypeToken<EmqxResponse<List<NodeMetrics>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回指定节点下所有监控指标数据
     *
     * @param node
     * @return
     */
    public Map<String, Object> getMetricsByNode(String node) {
        EmqxResponse<Map<String, Object>> response = request(Method.GET, "/api/v4/nodes/" + node + "/metrics",
            new TypeToken<EmqxResponse<Map<String, Object>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回所有主题统计指标数据
     *
     * @return
     */
    public List<TopicMetrics> getTopicMetrics() {
        EmqxResponse<List<TopicMetrics>> response =
            request(Method.GET, "/api/v4/topic-metrics", new TypeToken<EmqxResponse<List<TopicMetrics>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回指定主题的统计指标数据
     *
     * @return
     */
    public Map<String, Object> getTopicMetricsByTopic(String topic) {
        EmqxResponse<Map<String, Object>> response =
            request(Method.GET, "/api/v4/topic-metrics/" + URLUtil.encodeAll(topic),
                new TypeToken<EmqxResponse<Map<String, Object>>>() {
                });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 开启/关闭对指定主题的指标统计
     *
     * @param body
     * @return
     */
    public boolean setTopicMetric(String topic, boolean enable) {
        EmqxResponse<Object> response;
        if (enable) {
            response = request(Method.POST, "/api/v4/topic-metrics", new OpenTopicMetricBody(topic),
                new TypeToken<EmqxResponse<Object>>() {
                });
        } else {
            response = request(Method.DELETE, "/api/v4/topic-metrics/" + URLUtil.encodeAll(topic),
                new TypeToken<EmqxResponse<Object>>() {
                });
        }
        checkResponse(response);
        return true;
    }

    /**
     * 返回集群下所有状态数据
     *
     * @return
     */
    public List<NodeStats> getStats() {
        EmqxResponse<List<NodeStats>> response =
            request(Method.GET, "/api/v4/stats", new TypeToken<EmqxResponse<List<NodeStats>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回指定节点上的状态数据
     *
     * @param node
     * @return
     */
    public Map<String, Object> getStatsByNode(String node) {
        EmqxResponse<Map<String, Object>> response =
            request(Method.GET, "/api/v4/nodes/" + node + "/stats", new TypeToken<EmqxResponse<Map<String, Object>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /* 告警 */

    /**
     * 返回集群下当前告警信息
     *
     * @return
     */
    public List<NodeAlarm> getAlarms() {
        EmqxResponse<List<NodeAlarm>> response =
            request(Method.GET, "/api/v4/alarms", new TypeToken<EmqxResponse<List<NodeAlarm>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回指定节点下的告警信息
     *
     * @param node
     * @return
     */
    public List<Alarm> getAlarmsByNode(String node) {
        EmqxResponse<List<Alarm>> response =
            request(Method.GET, "/api/v4/nodes/" + node + "/alarms", new TypeToken<EmqxResponse<List<Alarm>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回集群下激活中的告警
     *
     * @return
     */
    public List<NodeAlarm> getActivatedAlarms() {
        EmqxResponse<List<NodeAlarm>> response =
            request(Method.GET, "/api/v4/alarms/activated", new TypeToken<EmqxResponse<List<NodeAlarm>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回指定节点下激活中的告警
     *
     * @param node
     * @return
     */
    public List<Alarm> getActivatedAlarmsByNode(String node) {
        EmqxResponse<List<Alarm>> response = request(Method.GET, "/api/v4/nodes/" + node + "/alarms/activated",
            new TypeToken<EmqxResponse<List<Alarm>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回集群下已经取消的告警
     *
     * @return
     */
    public List<NodeAlarm> getDeactivatedAlarms() {
        EmqxResponse<List<NodeAlarm>> response =
            request(Method.GET, "/api/v4/alarms/deactivated", new TypeToken<EmqxResponse<List<NodeAlarm>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 返回指定节点下已经取消的告警
     *
     * @param node
     * @return
     */
    public List<Alarm> getDeactivatedAlarmsByNode(String node) {
        EmqxResponse<List<Alarm>> response = request(Method.GET, "/api/v4/nodes/" + node + "/alarms/deactivated",
            new TypeToken<EmqxResponse<List<Alarm>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 取消指定告警
     *
     * @param body
     * @return
     */
    public boolean cancelDeactivatedAlarm(CancelDeactivatedAlarmBody body) {
        EmqxResponse<Object> response =
            request(Method.POST, "/api/v4/alarms/deactivated", body, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 删除所有已经取消的告警
     *
     * @return
     */
    public boolean deleteDeactivatedAlarm() {
        EmqxResponse<Object> response =
            request(Method.DELETE, "/api/v4/alarms/deactivated", new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 清除指定节点下所有已经取消的告警
     *
     * @param node
     * @return
     */
    public boolean deleteDeactivatedAlarmByNode(String node) {
        EmqxResponse<Object> response = request(Method.DELETE, "/api/v4/nodes/" + node + "/alarms/deactivated",
            new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /* 黑名单 */

    /**
     * 获取黑名单
     *
     * @param query
     * @return
     */
    public EmqxPageResponse<List<Banned>> getBanned(PageQuery query) {
        EmqxPageResponse<List<Banned>> response =
            request(Method.GET, "/api/v4/banned", query, new TypeToken<EmqxPageResponse<List<Banned>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response;
    }

    /**
     * 将对象添加至黑名单
     *
     * @param body
     * @return
     */
    public boolean addBanned(BannedBody body) {
        EmqxResponse<Object> response =
            request(Method.POST, "/api/v4/banned", body, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 将对象从黑名单中删除
     *
     * @param who
     * @param as
     * @return
     */
    public boolean deleteBanned(String who, String as) {
        EmqxResponse<Object> response =
            request(Method.DELETE, "/api/v4/banned/" + as + "/" + who, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /* 数据导入导出 */

    /**
     * 获取当前的导出文件信息列表，包括文件名、大小和创建时间
     *
     * @return
     */
    public List<DataFile> getExportFiles() {
        EmqxResponse<List<DataFile>> response =
            request(Method.GET, "/api/v4/data/export", new TypeToken<EmqxResponse<List<DataFile>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 下载数据文件
     *
     * @param filename
     * @param destFilePath
     * @return
     */
    public File downloadExportFile(String filename, String destFilePath) {
        String json = request(Method.GET, "/api/v4/data/file/" + filename);
        return FileUtil.writeBytes(json.getBytes(StandardCharsets.UTF_8), destFilePath);
    }

    /**
     * 导出当前数据到文件
     *
     * @return
     */
    public DataFile exportFile() {
        EmqxResponse<DataFile> response =
            request(Method.POST, "/api/v4/data/export", new TypeToken<EmqxResponse<DataFile>>() {
            });
        checkResponse(response);
        return response.getData();
    }

    /**
     * 从指定文件导入数据
     *
     * @param filename
     * @return
     */
    public boolean importFile(String filename) {
        EmqxResponse<Object> response = request(Method.POST, "/api/v4/data/import", new ImportBody(filename),
            new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 上传数据文件
     *
     * @param body 通过对象形式
     * @return
     */
    public boolean uploadFile(UploadFileBody body) {
        EmqxResponse<Object> response =
            request(Method.POST, "/api/v4/data/file", body, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 上传数据文件
     *
     * @param filePath 指定导出文件的路径
     * @return
     */
    public boolean uploadFile(String filePath) {
        EmqxResponse<Object> response =
            request(Method.POST, "/api/v4/data/file", FileUtil.readString(filePath, StandardCharsets.UTF_8),
                new TypeToken<EmqxResponse<Object>>() {
                });
        checkResponse(response);
        return true;
    }

    /**
     * 删除指定的数据文件
     *
     * @param filename
     * @return
     */
    public boolean deleteFile(String filename) {
        EmqxResponse<Object> response =
            request(Method.DELETE, "/api/v4/data/file/" + filename, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /* 规则 */

    public List<Rule> getRules() {
        EmqxResponse<List<Rule>> response =
            request(Method.GET, "/api/v4/rules", new TypeToken<EmqxResponse<List<Rule>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 获取某个规则的详情，包括规则的 SQL、Topics 列表、动作列表等。还会返回当前规则和动作的统计指标的值
     *
     * @param ruleId
     * @return
     */
    public Rule getRule(String ruleId) {
        EmqxResponse<Rule> response =
            request(Method.GET, "/api/v4/rules/" + ruleId, new TypeToken<EmqxResponse<Rule>>() {
            });
        checkResponse(response);
        return response.getData();
    }

    /**
     * 创建规则，返回规则 ID
     *
     * @param body
     * @return
     */
    public Rule addRule(RuleBody body) {
        EmqxResponse<Rule> response = request(Method.POST, "/api/v4/rules", body, new TypeToken<EmqxResponse<Rule>>() {
        });
        checkResponse(response);
        return response.getData();
    }

    /**
     * 更新规则
     *
     * @param body
     * @return
     */
    public Rule updateRule(RuleBody body) {
        EmqxResponse<Rule> response =
            request(Method.PUT, "/api/v4/rules/" + body.getId(), body, new TypeToken<EmqxResponse<Rule>>() {
            });
        checkResponse(response);
        return response.getData();
    }

    /**
     * 删除规则
     *
     * @param ruleId
     * @return
     */
    public boolean deleteRule(String ruleId) {
        EmqxResponse<Object> response =
            request(Method.DELETE, "/api/v4/rules/" + ruleId, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /* 动作 */

    /**
     * 动作列表
     *
     * @return
     */
    public List<Action> getActions() {
        EmqxResponse<List<Action>> response =
            request(Method.GET, "/api/v4/actions", new TypeToken<EmqxResponse<List<Action>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 获取某个动作的详情，包括动作名字、参数列表等
     *
     * @param actionName
     * @return
     */
    public Action getAction(String actionName) {
        EmqxResponse<Action> response =
            request(Method.GET, "/api/v4/actions/" + actionName, new TypeToken<EmqxResponse<Action>>() {
            });
        checkResponse(response);
        return response.getData();
    }

    /* 资源类型 */

    /**
     * 资源类型列表
     *
     * @return
     */
    public List<ResourceType> getResourceTypes() {
        EmqxResponse<List<ResourceType>> response =
            request(Method.GET, "/api/v4/resource_types", new TypeToken<EmqxResponse<List<ResourceType>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 获取某个资源的详情，包括资源描述、参数列表等
     *
     * @param resourceTypeName
     * @return
     */
    public ResourceType getResourceType(String resourceTypeName) {
        EmqxResponse<ResourceType> response = request(Method.GET, "/api/v4/resource_types/" + resourceTypeName,
            new TypeToken<EmqxResponse<ResourceType>>() {
            });
        checkResponse(response);
        return response.getData();
    }

    /* 资源 */

    /**
     * 资源列表
     *
     * @return
     */
    public List<Resource> getResources() {
        EmqxResponse<List<Resource>> response =
            request(Method.GET, "/api/v4/resources", new TypeToken<EmqxResponse<List<Resource>>>() {
            });
        checkResponse(response);
        checkList(response);
        return response.getData();
    }

    /**
     * 获取指定的资源的详细信息
     *
     * @param resourceId
     * @return
     */
    public Resource getResource(String resourceId) {
        EmqxResponse<Resource> response =
            request(Method.GET, "/api/v4/resources/" + resourceId, new TypeToken<EmqxResponse<Resource>>() {
            });
        checkResponse(response);
        return response.getData();
    }

    /**
     * 创建规则，返回资源 对象
     * 经过测试指定ID存在时会更新原有资源
     * @param body
     * @return
     */
    public Resource addResource(ResourceBody body) {
        EmqxResponse<Resource> response =
            request(Method.POST, "/api/v4/resources", body, new TypeToken<EmqxResponse<Resource>>() {
            });
        checkResponse(response);
        return response.getData();
    }

    /**
     * 删除资源
     *
     * @param resourceId
     * @return
     */
    public boolean deleteResource(String resourceId) {
        EmqxResponse<Object> response =
            request(Method.DELETE, "/api/v4/resources/" + resourceId, new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /* 数据遥测 */

    /**
     * 启用或关闭数据遥测功能
     *
     * @param enable
     * @return
     */
    public boolean setTelemetryStatus(boolean enable) {
        EmqxResponse<Object> response = request(Method.PUT, "/api/v4/telemetry/status", new TelemetryStatusBody(enable),
            new TypeToken<EmqxResponse<Object>>() {
            });
        checkResponse(response);
        return true;
    }

    /**
     * 查询数据遥测功能是否启用
     *
     * @return
     */
    public TelemetryStatus getTelemetryStatus() {
        EmqxResponse<TelemetryStatus> response =
            request(Method.GET, "/api/v4/telemetry/status", new TypeToken<EmqxResponse<TelemetryStatus>>() {
            });
        checkResponse(response);
        return response.getData();
    }

    /**
     * 获取数据遥测功能上报的数据内容
     *
     * @return
     */
    public TelemetryData getTelemetryData() {
        EmqxResponse<TelemetryData> response =
            request(Method.GET, "/api/v4/telemetry/data", new TypeToken<EmqxResponse<TelemetryData>>() {
            });
        checkResponse(response);
        return response.getData();
    }

}
