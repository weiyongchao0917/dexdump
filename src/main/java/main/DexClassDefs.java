package main;

import main.second.DexClassDef;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * DexClassDefs 用于解析 DEX 文件中的类定义区域（class_defs）。
 *
 * 根据 DexFileHeader 中的 classDefsSize 与 classDefsOff，依次解析每个类定义项（32 字节）。
 */
public class DexClassDefs {
    private List<DexClassDef> classDefList = new ArrayList<>();

    /**
     * 解析类定义区域
     *
     * @param buffer DEX 文件数据的 ByteBuffer
     * @param header 已解析的 DexFileHeader，其中包含 classDefsSize 和 classDefsOff 信息
     */
    public void parse(ByteBuffer buffer, DexFileHeader header) {
        int classDefsSize = header.getClassDefsSize();
        int classDefsOff = header.getClassDefsOff();

        // DEX 文件采用小端字节序
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(classDefsOff);

        // 每个类定义项占用 32 字节
        for (int i = 0; i < classDefsSize; i++) {
            int classIdx = buffer.getInt();
            int accessFlags = buffer.getInt();
            int superclassIdx = buffer.getInt();
            int interfacesOff = buffer.getInt();
            int sourceFileIdx = buffer.getInt();
            int annotationsOff = buffer.getInt();
            int classDataOff = buffer.getInt();
            int staticValuesOff = buffer.getInt();
            classDefList.add(new DexClassDef(classIdx, accessFlags, superclassIdx, interfacesOff,
                    sourceFileIdx, annotationsOff, classDataOff, staticValuesOff));
        }
    }

    public List<DexClassDef> getClassDefList() {
        return classDefList;
    }

    /**
     * 输出整个类的字符串信息
     *
     * 这里依赖于 DexTypeIds 和 DexStringIds：
     * - 通过 DexTypeIds.getTypeName() 解析 classIdx 与 superclassIdx 得到类名和父类名；
     * - 通过 DexStringIds.getStringByIndex() 解析 sourceFileIdx 得到源文件名。
     *
     * @param dexStringIds 已解析的 DexStringIds（包含所有字符串）
     * @param dexTypeIds 已解析的 DexTypeIds（包含所有类型信息）
     */
    public void printClassDefs(DexStringIds dexStringIds, DexTypeIds dexTypeIds) {
        for (int i = 0; i < classDefList.size(); i++) {
            DexClassDef def = classDefList.get(i);
            // 获取类名（描述符），注意：classIdx 是指向 DexTypeIds 的索引
            String classDescriptor = dexTypeIds.getTypeName(def.getClassIdx(), dexStringIds);

            // 获取父类名（如果没有父类，则通常为 0xFFFFFFFF 或 -1，根据实际解析情况判断）
            String superclassDescriptor = (def.getSuperclassIdx() < 0 || def.getSuperclassIdx() >= dexTypeIds.getTypeCount())
                    ? "null" : dexTypeIds.getTypeName(def.getSuperclassIdx(), dexStringIds);

            // 获取源文件名，通过 sourceFileIdx 从 DexStringIds 获取（注意无效索引的情况）
            String sourceFile = (def.getSourceFileIdx() < 0 || def.getSourceFileIdx() >= dexStringIds.getStringCount())
                    ? "null" : dexStringIds.getStringByIndex(def.getSourceFileIdx());

            System.out.println("Class " + i + ": " + classDescriptor);
            System.out.println("  Superclass: " + superclassDescriptor);
            System.out.println("  Source File: " + sourceFile);
            System.out.println("  Access Flags: 0x" + Integer.toHexString(def.getAccessFlags()));
            System.out.println();
        }
    }


    public void printFullClassInfo(DexStringIds dexStringIds, DexTypeIds dexTypeIds, ByteBuffer buffer) {
        for (int i = 0; i < classDefList.size(); i++) {
            DexClassDef def = classDefList.get(i);
            // 解析类名和父类名
            String classDesc = dexTypeIds.getTypeName(def.getClassIdx(), dexStringIds);
            String superDesc = (def.getSuperclassIdx() < 0 || def.getSuperclassIdx() >= dexTypeIds.getTypeCount())
                    ? "Ljava/lang/Object;" : dexTypeIds.getTypeName(def.getSuperclassIdx(), dexStringIds);
            // 转换为 Java 风格的名称（例如 "Lcom/example/MyClass;" -> "com.example.MyClass"）
            String className = convertDescriptorToJavaName(classDesc);
            String superName = convertDescriptorToJavaName(superDesc);
            // 获取源文件
            String sourceFile = (def.getSourceFileIdx() < 0 || def.getSourceFileIdx() >= dexStringIds.getStringCount())
                    ? "Unknown" : dexStringIds.getStringByIndex(def.getSourceFileIdx());

            // 输出类头
            System.out.println("--------------------------------------------------");
            System.out.println("Class " + i + ": " + className);
            System.out.println("  Superclass: " + superName);
            System.out.println("  Source File: " + sourceFile);
            System.out.println("  Access Flags: 0x" + Integer.toHexString(def.getAccessFlags()));

            // 解析 class data（字段和方法）
            if (def.getClassDataOff() != 0) {
                DexClassData classData = DexClassData.parse(buffer.duplicate(), def.getClassDataOff());
                if (classData != null) {
                    System.out.println("  [Class Data]");
                    System.out.println(classData);
                }
            } else {
                System.out.println("  [No Class Data]");
            }
            System.out.println("--------------------------------------------------\n");
        }
    }


    private String convertDescriptorToJavaName(String descriptor) {
        if (descriptor == null || descriptor.isEmpty()) {
            return descriptor;
        }
        if (descriptor.charAt(0) == 'L' && descriptor.charAt(descriptor.length()-1) == ';') {
            return descriptor.substring(1, descriptor.length()-1).replace('/', '.');
        }
        return descriptor;
    }
}
