package org.idea.irpc.framework.core.serialize.fastjson;

import com.alibaba.fastjson.JSON;
import org.idea.irpc.framework.core.serialize.SerializeFactory;
import org.idea.irpc.framework.core.serialize.Student;

/**
 * @Author linhao
 * @Date created in 9:51 下午 2022/1/17
 */
public class FastJsonSerializeFactory implements SerializeFactory {

    @Override
    public <T> byte[] serialize(T t) {
        String jsonStr = JSON.toJSONString(t);
        return jsonStr.getBytes();
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        return JSON.parseObject(new String(data),clazz);
    }

    public static void main(String[] args) {
        Student student = new Student(0,"idea","1");
        SerializeFactory serializeFactory = new FastJsonSerializeFactory();
        byte[] b = serializeFactory.serialize(student);
        Student student1 = serializeFactory.deserialize(b,Student.class);
        System.out.println(student1);
    }
}
