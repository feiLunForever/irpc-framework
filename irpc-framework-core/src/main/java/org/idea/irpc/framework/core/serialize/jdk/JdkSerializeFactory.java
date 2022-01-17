package org.idea.irpc.framework.core.serialize.jdk;

import org.idea.irpc.framework.core.serialize.SerializeFactory;
import org.idea.irpc.framework.core.serialize.Student;

import java.io.*;

/**
 * @Author linhao
 * @Date created in 7:04 下午 2022/1/17
 */
public class JdkSerializeFactory implements SerializeFactory {


    @Override
    public <T> byte[] serialize(T t) {
        byte[] data = null;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(os);
            output.writeObject(t);
            output.flush();
            output.close();
            data = os.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return data;
    }

    @Override
    public <T> T deserialize(byte[] data, Class<T> clazz) {
        ByteArrayInputStream is = new ByteArrayInputStream(data);
        try {
            ObjectInputStream input = new ObjectInputStream(is);
            Object result = input.readObject();
            return ((T) result);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Student student = new Student(1,"idea","1");
        JdkSerializeFactory jdkSerializeFactory = new JdkSerializeFactory();
        byte[] result = jdkSerializeFactory.serialize(student);
        Student stu = jdkSerializeFactory.deserialize(result,Student.class);
        System.out.println(stu);
    }
}
