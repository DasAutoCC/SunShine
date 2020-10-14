package com.lkz.blog.web.utils;

import java.io.*;
import java.util.ArrayList;

/**
 * 这个工具类中的对象序列化工具用来序列化SpringSecurity中的认证对象
 * AuthenticationContext对象，当然也可以序列化和反序列化其他对象
 * @author 盛世繁华
 */
public class SerializableUtil {

    /**
     * 将对象序列化为byte数组，并转化为StringBuilder对象，以便将其保存在其它存储介质中
     *
     * @author 盛世繁华
     * @param Object 需要序列化的对象
     * @return StringBuilder byte数组
     * @throws IOException
     */
    public static StringBuilder writeObjectToStringBuilder(Object o) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(os);
        oo.writeObject(o);

        //获取到对象转化的byte数组
        byte[] bytes = os.toByteArray();
        //String类型的byte数组
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(b + " ");
        }
        //删除最后一位的空格
        //sb.deleteCharAt(sb.lastIndexOf(" "));
        return sb;
    }

    /**
     * @author 盛世繁华
     * @param orgain 这应该传入一个byte数组所转化为的String，如果格式不对会发生异常
     * @return 返回一个object对象，可以将其强转为预期的类型
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Object readStringToObject(String orgain) throws IOException ,ClassNotFoundException{
        StringBuilder sb = new StringBuilder(orgain);
        ArrayList<Byte> al = new ArrayList<>();

        while(true){
            int i = sb.indexOf(" ");
            if (i == -1){
                break;
            }
            String substring = sb.substring(0, i);
            al.add(Byte.parseByte(substring));
            sb.delete(0,i+1);
        }

        //将list转换为数组
        byte[] b2 = new byte[al.size()];

        Byte[] bytes1 = al.toArray(new Byte[al.size()]);

        for (int i = 0 ; i<al.size() ; i++){
            b2[i] = bytes1[i];
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(b2);
        ObjectInputStream oi = new ObjectInputStream(bi);
        Object o = oi.readObject();

        return o;
    }
}
