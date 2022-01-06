package org.idea.irpc.framework.core.router;

/**
 * @Author linhao
 * @Date created in 8:13 下午 2022/1/5
 */
public class Selector {

    /**
     * 筛选策略
     */
    private String selectStrategy;

    /**
     * 服务命名
     * eg: com.sise.test.DataService
     */
    private String providerServiceName;


    public String getSelectStrategy() {
        return selectStrategy;
    }

    public void setSelectStrategy(String selectStrategy) {
        this.selectStrategy = selectStrategy;
    }

    public String getProviderServiceName() {
        return providerServiceName;
    }

    public void setProviderServiceName(String providerServiceName) {
        this.providerServiceName = providerServiceName;
    }
}
