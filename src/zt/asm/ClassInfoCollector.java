package zt.asm;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.HashMap;
import java.util.List;

public class ClassInfoCollector extends ClassVisitor {

    private HashMap<String, List<RawLocalInfo>> methodLocals;

    public ClassInfoCollector() {
        super(Opcodes.ASM4);
        methodLocals = new HashMap<>();
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc,
                                     String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
        System.out.println(access + "\t" + name + "\t" + desc);
        MethodInfoCollector mc = new MethodInfoCollector(mv);
        methodLocals.put(name + "_" + desc, mc.getLocals());
        return mc;
    }

    public HashMap<String, List<RawLocalInfo>> getMethodLocals() {
        return methodLocals;
    }


    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
