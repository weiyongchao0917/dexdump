package main;
/**
 * DexClassDef 表示 DEX 文件中单个类定义项。
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
