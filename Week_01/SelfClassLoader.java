import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SelfClassLoader extends ClassLoader{

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class object = new SelfClassLoader().findClass("Hello");
        Method hello = object.getDeclaredMethod("hello");
        hello.invoke(object.newInstance());
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("E:\\Hello.xlass\\Hello.xlass");
        ByteArrayOutputStream outputStream = null;
        byte[] bytes = null;
        try {
            FileInputStream inputStream = new FileInputStream(file);
            outputStream = new ByteArrayOutputStream();
            byte[] data = new byte[2048];
            int len;
            while ((len = inputStream.read(data)) != -1) {
                outputStream.write(data, 0, len);
            }
            bytes = outputStream.toByteArray();
            for (int i = 0; i < bytes.length; i++) {
                bytes[i] = (byte) (255 - bytes[i]);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass(name, bytes, 0, bytes.length);
    }
}
