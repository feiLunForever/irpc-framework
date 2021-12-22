package org.idea.irpc.framework.core.common.cache;

import org.idea.irpc.framework.core.registy.URL;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @Author linhao
 * @Date created in 8:45 下午 2021/12/1
 */
public class CommonServerCache {

    public static final Map<String,Object> PROVIDER_CLASS_MAP = new HashMap<>();
    public static final Set<URL> PROVIDER_URL_SET = new HashSet<>();
}
