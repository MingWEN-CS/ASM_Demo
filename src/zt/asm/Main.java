package zt.asm;


import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws Exception {
        MyClassLoader cl = new MyClassLoader();
        Class c = cl.findClass("zt.asm.model.Algorithm");
        Method m = c.getDeclaredMethod("run");
        Object algorithm = c.newInstance();
        m.invoke(algorithm);
    }
}

