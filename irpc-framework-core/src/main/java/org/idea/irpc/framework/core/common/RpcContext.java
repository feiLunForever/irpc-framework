package org.idea.irpc.framework.core.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author linhao
 * @Date created in 2:34 下午 2022/1/29
 */
public class RpcContext {

    private static InheritableThreadLocalMap<Map<Object, Object>> attachments = new InheritableThreadLocalMap<>();

    public static void addAttachment(String key, Object value) {
        attachments.get().put(key, value);
    }

    public static Object getAttachment(String key) {
        return attachments.get().get(key);
    }

    public static Map<Object, Object> getAttachments() {
        return attachments.get();
    }

    public static void setAttachments(Map<String, Object> attachments) {
        RpcContext.attachments.get().putAll(attachments);
    }

    private static final class InheritableThreadLocalMap<T extends Map<Object, Object>> extends InheritableThreadLocal<Map<Object, Object>> {

        @Override
        protected Map<Object, Object> initialValue() {
            return new HashMap<Object, Object>();
        }

        @Override
        protected Map<Object, Object> childValue(Map<Object, Object> parentValue) {
            if (parentValue != null) {
                return (Map<Object, Object>) ((HashMap<Object, Object>) parentValue).clone();
            } else {
                return null;
            }
        }
    }

    public static void main(String[] args) {
        ThreadLocal<Map<Object, Object>> local = new InheritableThreadLocalMap<Map<Object, Object>>();
        local.get().put("key","test");

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("curr->>" + local.get());
            }
        });
        thread.start();
    }

}
