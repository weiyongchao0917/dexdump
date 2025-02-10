package main.second;

/**
 * DexMethodId 表示 DEX 文件中的一个方法 ID 项。
 *
 * 每个方法 ID 项占用 8 字节，具体构成如下：
 * - class_idx (2 字节)：指向 DexTypeIds 表中，表示该方法所属的类
 * - proto_idx (2 字节)：指向 DexProtoIds 表中，表示该方法的原型（返回值和参数列表）
 * - name_idx (4 字节)：指向 DexStringIds 表中，表示方法名称的索引
 */
public class DexMethodId {
    private int classIdx;
    private int protoIdx;
    private int nameIdx;

    public DexMethodId(int classIdx, int protoIdx, int nameIdx) {
        this.classIdx = classIdx;
        this.protoIdx = protoIdx;
        this.nameIdx = nameIdx;
    }

    public int getClassIdx() {
        return classIdx;
    }

    public int getProtoIdx() {
        return protoIdx;
    }

    public int getNameIdx() {
        return nameIdx;
    }

    @Override
    public String toString() {
        return "DexMethodId{" +
                "classIdx=" + classIdx +
                ", protoIdx=" + protoIdx +
                ", nameIdx=" + nameIdx +
                '}';
    }
}