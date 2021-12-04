package org.idea.irpc.framework.core.proxy.javassist;

public interface HelloService {

    void say(String msg);

    String echo(String msg);

    String[] getHobbies();

}