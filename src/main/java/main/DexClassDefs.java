package main;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * DexClassDefs 用于解析 DEX 文件中的类定义部分。
 * 解析结果存储为 DexClassDef 对象的列表，每个对象包含了一个类的详细信息。
 */
public class DexClassDefs {
    private int classDefCount;     // 类定义数量
    private int classDefsOffset;   // 类定义区域的偏移量
    private List<DexClassDef> classDefList;  // 存储解析得到的类定义项

    /**
     * 从 ByteBuffer 中解析类定义区域。
     * 使用 header 中的 classDefsSize 和 classDefsOff 字段确定区域位置和数量。
     *
     * @param buffer ByteBuffer（整个文件映射）
     * @param header 已解析的 DEX 头部信息
     */
    public void parse(ByteBuffer buffer, DexFileHeader header) {
        classDefCount = header.getClassDefsSize();
        classDefsOffset = header.getClassDefsOff();
        classDefList = new ArrayList<>();

        // 每个类定义项占用 8*4=32 字节
        final int CLASS_DEF_ITEM_SIZE = 32;

        // 定位到类定义区域的起始位置
        buffer.position(classDefsOffset);
        for (int i = 0; i < classDefCount; i++) {
            // 为每个类定义项依次读取 8 个整数
            int classIdx = buffer.getInt();
            int accessFlags = buffer.getInt();
            int superclassIdx = buffer.getInt();
            int interfacesOff = buffer.getInt();
            int sourceFileIdx = buffer.getInt();
            int annotationsOff = buffer.getInt();
            int classDataOff = buffer.getInt();
            int staticValuesOff = buffer.getInt();

            DexClassDef classDef = new DexClassDef(
                    classIdx, accessFlags, superclassIdx, interfacesOff,
                    sourceFileIdx, annotationsOff, classDataOff, staticValuesOff);
            classDefList.add(classDef);
        }
    }

    public int getClassDefCount() {
        return classDefCount;
    }

    public List<DexClassDef> getClassDefList() {
        return classDefList;
    }

    /**
     * 打印所有类定义项的信息
     */
    public void printClassDefs() {
        System.out.println("Total ClassDefs: " + classDefCount);
        for (int i = 0; i < classDefList.size(); i++) {
            System.out.println("ClassDef[" + i + "]: " + classDefList.get(i));
        }
    }
}

