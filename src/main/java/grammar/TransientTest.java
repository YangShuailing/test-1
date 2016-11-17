package grammar;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TransientTest {
    public static void main(String[] args) {

        User user = new User();
        user.setUsername("Alexia");
        user.setPassword("123456");
        
        /*
         * @description ʹ��transient�ؼ��ֲ����л�ĳ������
	 		ע���ȡ��ʱ�򣬶�ȡ���ݵ�˳��һ��Ҫ�ʹ�����ݵ�˳�򱣳�һ��
         */
        System.out.println("read before Serializable: ");
        System.out.println("username: " + user.getUsername());
        System.err.println("password: " + user.getPassword());

        try {
            ObjectOutputStream os = new ObjectOutputStream(
                    new FileOutputStream("C:/Users/jwang21/Documents/Test/user.txt"));//������\б��
            os.writeObject(user); // ��User����д���ļ�
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(
                    "C:/Users/jwang21/Documents/Test/user.txt"));//  C:/user.txt?  Ȩ�������д��������
            user = (User) is.readObject(); // �����ж�ȡUser������
            is.close();
            System.out.println("\nread after Serializable: ");
            System.out.println("username: " + user.getUsername());
            System.err.println("password: " + user.getPassword());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}

class User implements Serializable {
    private static final long serialVersionUID = 8294180014912103005L;

    private String username;
    private transient String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    String getPassword() {
        return password;
    }

    void setPassword(String password) {
        this.password = password;
    }
}