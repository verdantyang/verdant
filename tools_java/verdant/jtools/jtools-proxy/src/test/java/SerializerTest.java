import com.verdant.jtools.proxy.codec.serialization.Serializer;
import org.junit.Assert;
import org.junit.Test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public class SerializerTest {
    private class User implements Serializable {
        private String userName;
        private String password;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    @Test
    public static void main(String[] args) {
        String userName = "abc";
        String password = "123456";
        SerializerTest test = new SerializerTest();
        SerializerTest.User user = test.new User();
        user.setUserName(userName);
        user.setPassword(password);
        byte[] ser = Serializer.serialize(user, true);
        User deser = Serializer.deserialize(ser, true);
        Assert.assertEquals(deser.getUserName(), userName);
        Assert.assertEquals(deser.getPassword(), password);

        System.out.println("序列化测试2：字符串列表");
        List<String> list = new ArrayList<>();
        list.add(userName);
        list.add(password);
        byte[] ser1 = Serializer.serialize(list);
        List<String> deser1 = Serializer.deserialize(ser1);
        for (String str : deser1) {
            System.out.println(str);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~");
        System.out.println("序列化测试3：对象列表");
        List<User> listUser = new ArrayList<>();
        listUser.add(user);
        listUser.add(user);
        byte[] ser2 = Serializer.serialize(listUser);
        List<User> deser2 = Serializer.deserialize(ser2);
        for (User elem : deser2) {
            System.out.println(elem.getUserName() + "  " + elem.getPassword());
        }
    }
}
