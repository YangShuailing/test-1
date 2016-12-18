package grammar.reflection.reflectasm;

import com.esotericsoftware.reflectasm.MethodAccess;

import java.lang.reflect.Method;

/**
 * Author: Johnny
 * Date: 2016/12/18
 * Time: 21:54
 */
public class ReflectasmClient {
    public static void main(String[] args) throws Exception {
        testJdkReflect();
//        testReflectAsm();
    }

    public static void testJdkReflect() throws Exception {
        SomeClass someObject = new SomeClass();
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {
                Method method = SomeClass.class.getMethod("foo", String.class);
                method.invoke(someObject, "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin +" ");
        }
    }

    public static void testReflectAsm() {
        SomeClass someObject = new SomeClass();
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {
                MethodAccess access = MethodAccess.get(SomeClass.class);
                access.invoke(someObject, "foo", "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
    }

    public static void testJdkReflect1() throws Exception {
        SomeClass someObject = new SomeClass();
        Method method = SomeClass.class.getMethod("foo", String.class);
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {
                method.invoke(someObject, "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin +" ");
        }
    }

    public static void testReflectAsm1() {
        SomeClass someObject = new SomeClass();
        MethodAccess access = MethodAccess.get(SomeClass.class);
        for (int i = 0; i < 5; i++) {
            long begin = System.currentTimeMillis();
            for (int j = 0; j < 100000000; j++) {
                access.invoke(someObject, "foo", "Unmi");
            }
            System.out.print(System.currentTimeMillis() - begin + " ");
        }
    }
}
