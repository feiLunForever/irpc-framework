package org.idea.irpc.framework.core.common.cache;

import io.netty.util.internal.ConcurrentSet;
import org.idea.irpc.framework.core.common.config.ServerConfig;
import org.idea.irpc.framework.core.filter.server.ServerFilterChain;
import org.idea.irpc.framework.core.registy.RegistryService;
import org.idea.irpc.framework.core.registy.URL;
import org.idea.irpc.framework.core.serialize.SerializeFactory;
import org.idea.irpc.framework.core.server.ServiceWrapper;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author linhao
 * @Date created in 8:45 下午 2021/12/1
 */
public class CommonServerCache {

    public static final Map<String,Object> PROVIDER_CLASS_MAP = new ConcurrentHashMap<>();
    public static final Set<URL> PROVIDER_URL_SET = new ConcurrentSet<>();
    public static RegistryService REGISTRY_SERVICE;
    public static SerializeFactory SERVER_SERIALIZE_FACTORY;
    public static ServerConfig SERVER_CONFIG;
    public static ServerFilterChain SERVER_FILTER_CHAIN;
    public static final Map<String, ServiceWrapper> PROVIDER_SERVICE_WRAPPER_MAP = new ConcurrentHashMap<>();
}
