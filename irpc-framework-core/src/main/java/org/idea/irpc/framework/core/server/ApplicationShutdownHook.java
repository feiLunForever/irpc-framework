package org.idea.irpc.framework.core.server;


import java.util.IdentityHashMap;

/**
 * 监听java进程被关闭
 *
 * @Author linhao
 * @Date created in 9:11 下午 2021/12/19
 */
public class ApplicationShutdownHook {

    private static IdentityHashMap<Thread,Thread> hooks;

}
