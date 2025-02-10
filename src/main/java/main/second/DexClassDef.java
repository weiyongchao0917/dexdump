package main.second;

/**
 * DexClassDef 表示 DEX 文件中的一个类定义项。
 *
 * 每个类定义项占 32 字节，字段如下：
 * - class_idx (4 字节)：指向 DexTypeIds 表中，表示该类的类型描述符索引
 * - access_flags (4 字节)：类的访问标志
 * - superclass_idx (4 字节)：指向 DexTypeIds 表中，表示父类的类型描述符索引
 * - interfaces_off (4 字节)：接口列表偏移量（如果有的话）
 * - source_file_idx (4 字节)：指向 DexStringIds 表中，表示源文件名称的索引
 * - annotations_off (4 字节)：注解区域的偏移量
 * - class_data_off (4 字节)：类数据的偏移量（包含字段、方法列表等）
 * - static_values_off (4 字节)：静态字段初始值的偏移量
 */
public class DexClassDef {
    private int classIdx;
    private int accessFlags;
    private int superclassIdx;
    private int interfacesOff;
    private int sourceFileIdx;
    private int annotationsOff;
    private int classDataOff;
    private int staticValuesOff;

    public DexClassDef(int classIdx, int accessFlags, int superclassIdx, int interfacesOff,
                       int sourceFileIdx, int annotationsOff, int classDataOff, int staticValuesOff) {
        this.classIdx = classIdx;
        this.accessFlags = accessFlags;
        this.superclassIdx = superclassIdx;
        this.interfacesOff = interfacesOff;
        this.sourceFileIdx = sourceFileIdx;
        this.annotationsOff = annotationsOff;
        this.classDataOff = classDataOff;
        this.staticValuesOff = staticValuesOff;
    }

    public int getClassIdx() {
        return classIdx;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public int getSuperclassIdx() {
        return superclassIdx;
    }

    public int getInterfacesOff() {
        return interfacesOff;
    }

    public int getSourceFileIdx() {
        return sourceFileIdx;
    }

    public int getAnnotationsOff() {
        return annotationsOff;
    }

    public int getClassDataOff() {
        return classDataOff;
    }

    public int getStaticValuesOff() {
        return staticValuesOff;
    }

    @Override
    public String toString() {
        return "DexClassDef{" +
                "classIdx=" + classIdx +
                ", accessFlags=0x" + Integer.toHexString(accessFlags) +
                ", superclassIdx=" + superclassIdx +
                ", interfacesOff=" + interfacesOff +
                ", sourceFileIdx=" + sourceFileIdx +
                ", annotationsOff=" + annotationsOff +
                ", classDataOff=" + classDataOff +
                ", staticValuesOff=" + staticValuesOff +
                '}';
    }
}

