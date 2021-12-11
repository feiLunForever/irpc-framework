package org.idea.irpc.framework.interfaces;

import java.util.List;

/**
 * @Author linhao
 * @Date created in 10:19 上午 2021/11/30
 */
public interface DataService {

    /**
     * 发送数据
     *
     * @param body
     */
    String sendData(String body);

    /**
     * 获取数据
     *
     * @return
     */
    List<String> getList();
}
