package org.idea.irpc.framework.core.common;

import java.util.Arrays;

import static org.idea.irpc.framework.core.common.constants.RpcConstants.MAGIC_NUMBER;

/**
 * @Author linhao
 * @Date created in 9:48 上午 2021/12/4
 */
public class RpcProtocol {

    private short magicNumber = MAGIC_NUMBER;

    private int contentLength;

    private byte[] content;

    public RpcProtocol(int contentLength, byte[] content) {
        this.contentLength = contentLength;
        this.content = content;
    }

    public short getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(short magicNumber) {
        this.magicNumber = magicNumber;
    }

    public int getContentLength() {
        return contentLength;
    }

    public void setContentLength(int contentLength) {
        this.contentLength = contentLength;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "RpcProtocol{" +
                " contentLength=" + contentLength +
                ", content=" + Arrays.toString(content) +
                '}';
    }
}
