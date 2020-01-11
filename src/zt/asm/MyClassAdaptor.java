package zt.asm;

import org.objectweb.asm.*;

import java.util.HashMap;
import java.util.List;

public class MyClassAdaptor extends ClassVisitor {

    public HashMap<String, List<RawLocalInfo>> methodVariables;

    public MyClassAdaptor(final ClassVisitor cv, HashMap<String, List<RawLocalInfo>> methodVariables) {
        super(Opcodes.ASM4, cv);
        this.methodVariables = new HashMap<>(methodVariables);
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        //    this.cci.setInterface((access & Opcodes.ACC_INTERFACE) != 0);
        //    this.owningClassName = ClassName.fromString(name);
        //    System.out.println(version + "\t" + access + "\t" +name + "\t" + signature);
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        MethodVisitor adapted = new MyMethodAdapter(mv, methodVariables.get(name + "_" + desc));
        return adapted;
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }

}
