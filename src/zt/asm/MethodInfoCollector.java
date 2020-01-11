package zt.asm;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MethodInfoCollector extends MethodVisitor {

    private final List<RawLocalInfo> locals;



    public MethodInfoCollector(MethodVisitor mv) {
        super(Opcodes.ASM5, mv);
        this.locals = new ArrayList<>();
    }

    @Override
    public void visitLabel(Label label) {
        System.out.println("visit label\t" + label.toString() + "\t");
        super.visitLabel(label);
    }

    @Override
    public void visitLineNumber(int line, Label start) {
//      System.out.println("visit line number\t" + line + "\t" + start.toString());
        super.visitLineNumber(line, start);
    }

    @Override
    public void visitLocalVariable(String name,
                                   String desc,
                                   String signature,
                                   Label start,
                                   Label end,
                                   int index) {
        this.locals.add(new RawLocalInfo(name, desc, start, end, index));
        System.out.println("visit local variable\t" + name + "_" + desc + "\t" + start.toString());
        super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    public List<RawLocalInfo> getLocals() {
        return locals;
    }

}
