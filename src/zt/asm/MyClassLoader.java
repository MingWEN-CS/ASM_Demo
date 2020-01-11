package zt.asm;



import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class MyClassLoader extends ClassLoader {

    @Override
    protected Class findClass(String name) throws ClassNotFoundException {
        try {

            ClassReader cr1 = new ClassReader(name);
            ClassInfoCollector ccc = new ClassInfoCollector();
            cr1.accept(ccc, 0);
            HashMap<String, List<RawLocalInfo>> classMethods = ccc.getMethodLocals();

            System.out.println("====== Collected Information");
            for (String methodName : classMethods.keySet()) {
                System.out.println(name);
                for (RawLocalInfo variable : classMethods.get(methodName)) {
                    System.out.println(variable.toString());
                }
            }
            System.out.println("====== Starting Mutating Information");

            ClassReader cr = new ClassReader(name);
            ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
            ClassVisitor adaptor = new MyClassAdaptor(cw, classMethods);
            cr.accept(adaptor, 0);

            byte b[] = cw.toByteArray();
            FileOutputStream out = new FileOutputStream("Algorithm.class");
            out.write(cw.toByteArray());
            out.close();
            return defineClass(name, b, 0, b.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return loadClass(name);
    }

}
