package org.idea.irpc.framework.core.spi;

import org.idea.irpc.framework.core.common.utils.CommonUtils;
import org.idea.irpc.framework.core.filter.IClientFilter;
import org.idea.irpc.framework.core.filter.IServerFilter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author linhao
 * @Date created in 10:08 下午 2022/2/2
 */
public class ExtensionLoader {

    public static String EXTENSION_LOADER_DIR_PREFIX = "META-INF/irpc/";

    public static Map<String, LinkedHashMap<String, Object>> EXTENSION_LOADER_CACHE = new ConcurrentHashMap<>();

    public void loadExtension(Class clazz) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (clazz == null) {
            throw new IllegalArgumentException("class is null!");
        }
        String spiFilePath = EXTENSION_LOADER_DIR_PREFIX + clazz.getName();
        ClassLoader classLoader = this.getClass().getClassLoader();
        Enumeration<URL> enumeration = null;
        enumeration = classLoader.getResources(spiFilePath);
        while (enumeration.hasMoreElements()) {
            URL url = enumeration.nextElement();
            InputStreamReader inputStreamReader = null;
            inputStreamReader = new InputStreamReader(url.openStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            LinkedHashMap<String, Object> objectMap = new LinkedHashMap<>();
            while ((line = bufferedReader.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue;
                }
                String[] lineArr = line.split("=");
                String implClassName = lineArr[0];
                String interfaceName = lineArr[1];
                Object object = newInstanceFromExtensionLoaderCache(interfaceName);
                objectMap.put(implClassName, object);
            }
            EXTENSION_LOADER_CACHE.put(clazz.getName(), objectMap);
        }
    }

    public <T> Object newInstanceFromExtensionLoaderCache(String implClassName) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        if (CommonUtils.isEmpty(implClassName)) {
            throw new IllegalArgumentException("className can not be null");
        }
        return (T) Class.forName(implClassName).newInstance();
    }

    public static void main(String[] args) throws IOException, IllegalAccessException, InstantiationException, ClassNotFoundException {
        ExtensionLoader extensionLoader = new ExtensionLoader();
        extensionLoader.loadExtension(IClientFilter.class);
        extensionLoader.loadExtension(IServerFilter.class);

    }

}
