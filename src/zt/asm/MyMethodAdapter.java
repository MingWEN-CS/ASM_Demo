package zt.asm;


import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyMethodAdapter extends MethodVisitor {

    private String name;
    private boolean trace;
    private List<RawLocalInfo> variables;
    private HashMap<Integer, String> indexVariable;
    private int currentLineNumber;

    protected MyMethodAdapter(MethodVisitor mv, List<RawLocalInfo> variables) {
        super(Opcodes.ASM5, mv);
        this.variables = new ArrayList<>(variables);
        indexVariable = new HashMap<>();
        for (RawLocalInfo variable : variables) {
            indexVariable.put(variable.index, variable.name + "_" + variable.desc);
        }
        currentLineNumber = 0;
    }

    @Override
    public void visitVarInsn(int opcode, int var) {
        super.visitVarInsn(opcode, var);

        String varaibleName = indexVariable.get(var);
        if (opcode == Opcodes.ISTORE && varaibleName.startsWith("j_")) {
            System.out.println("visit assignment of j\t" + currentLineNumber);
            mv.visitIincInsn(var, 2);
            visitTrace(var);
        }
    }

    @Override
    public void visitIincInsn(int var, int increment) {
        super.visitIincInsn(var, increment);
        visitTrace(var);
        mv.visitIincInsn(1, 2);
    }

    @Override
    public void visitLdcInsn(Object cst) {
        //if("Hello World".equals(cst)) cst = "Multiply Of x*y is: ";
        System.out.println("visiting LDC instruction\t" + cst + "\t" + currentLineNumber);
        cst = "Multiply Of x*y is: ";
        super.visitLdcInsn(cst);
    }

    @Override
    public void visitLineNumber(int line, Label start) {
 //     System.out.println("visit line number\t" + line + "\t" + start.toString());
        currentLineNumber = line;
        super.visitLineNumber(line, start);
    }

    private void visitTrace(int var) {
        mv.visitCode();
        mv.visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System", "out", "Ljava/io/PrintStream;");
        mv.visitVarInsn(Opcodes.ILOAD, var);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/PrintStream", "println", "(I)V", false);
        mv.visitEnd();
    }

    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}
