package zt.asm;

import org.objectweb.asm.Label;

public class RawLocalInfo {
    final String name;
    final String desc;
    final Label start;
    final Label end;
    final int index;

    RawLocalInfo(String name, String desc, Label start, Label end, int index) {
        this.name = name;
        this.desc = desc;
        this.start = start;
        this.end = end;
        this.index = index;
    }

    @Override
    public String toString() {
        return name + "\t" + desc + "\t" + index;
    }
}
