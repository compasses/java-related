package classloader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by i311352 on 08/04/2017.
 */
public class JarClassLoader extends ClassLoader {
    private String jarFile = "jar/test.jar";
    private Hashtable classes = new Hashtable();

    public JarClassLoader() {
        super(JarClassLoader.class.getClassLoader());
    }

    public Class loadClass(String className) throws ClassNotFoundException {
        return findClass(className);
    }


    public Class findClass(String className) {
        byte classByte[];
        Class result = null;
        result = (Class) classes.get(className);
        if (result != null) {
            return result;
        }
        try {
            findSystemClass(className);
        } catch (Exception e) {

        }

        try {
            JarFile jar = new JarFile(jarFile);
            JarEntry entry = jar.getJarEntry(className + ".class");
            InputStream inputStream = jar.getInputStream(entry);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int nextValue = inputStream.read();

            while (-1 != nextValue) {
                byteArrayOutputStream.write(nextValue);
                nextValue = inputStream.read();
            }

            classByte = byteArrayOutputStream.toByteArray();
            result = defineClass(className, classByte, 0, classByte.length, null);
            classes.put(className, result);
            return result;

        } catch (Exception e) {
            return null;
        }
    }

}
