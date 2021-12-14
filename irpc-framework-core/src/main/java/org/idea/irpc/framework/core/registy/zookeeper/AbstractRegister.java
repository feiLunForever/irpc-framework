package org.idea.irpc.framework.core.registy.zookeeper;

import io.netty.util.internal.ConcurrentSet;
import org.idea.irpc.framework.core.common.utils.CommonUtils;
import org.idea.irpc.framework.core.registy.RegistryService;
import org.idea.irpc.framework.core.registy.URL;

import java.util.List;
import java.util.Set;

import static org.idea.irpc.framework.core.common.cache.CommonClientCache.SUBSCRIBE_SERVICE_LIST;

/**
 * @Author linhao
 * @Date created in 3:57 下午 2021/12/11
 */
public abstract class AbstractRegister implements RegistryService {

    private Set<URL> providerUrls = new ConcurrentSet<>();


    @Override
    public void register(URL url) {
        providerUrls.add(url);
    }

    @Override
    public void unRegister(URL url) {
        providerUrls.remove(url);
    }

    @Override
    public void subscribe(URL url) {
        providerUrls.add(url);
        SUBSCRIBE_SERVICE_LIST.add(url.getServiceName());
//        List<URL> urls = URL_MAP.get(url.getServiceName());
//        if (CommonUtils.isEmptyList(urls)) {
//            urls = new ArrayList<>();
//        }
//        urls.add(url);
//        URL_MAP.put(url.getServiceName(), urls);
    }

    public abstract List<String> getProviderIps(String serviceName);


    @Override
    public void doUnSubscribe(URL url) {

    }
}
