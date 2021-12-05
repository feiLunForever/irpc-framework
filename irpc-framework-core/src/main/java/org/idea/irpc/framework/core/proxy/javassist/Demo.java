package org.idea.irpc.framework.core.proxy.javassist;

/**
 * @Author linhao
 * @Date created in 9:28 上午 2021/12/5
 */
public class Demo {

    public void doTest(){
        System.out.println("this is demo");
    }

    public static void main(String[] args) {
        Demo demo = new Demo();
        demo.doTest();
    }
}
