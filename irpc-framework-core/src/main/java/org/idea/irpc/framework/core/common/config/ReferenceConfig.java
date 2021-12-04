package org.idea.irpc.framework.core.common.config;

/**
 * @Author linhao
 * @Date created in 9:58 上午 2021/11/30
 */
public class ReferenceConfig {

    /**
     * 需要远程调用的文本字节数组
     */
    private byte[] body;

    public byte[] getBody() {
        return body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }
}
