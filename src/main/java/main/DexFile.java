package main;

import main.second.DexProtoId;
import main.second.DexString;
import main.second.DexTypeId;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.List;

/**
 * DexFile 类用于解析整个 DEX 文件。
 * 它包含一个 DexFileHeader 字段，同时可以解析其他部分（例如 String IDs、Type IDs、Method IDs、Class Definitions 等）。
 */
public class DexFile {
    private DexFileHeader header; // DEX 文件头部信息
    private DexStringIds stringIds; // 字符串 ID 区域
    private DexTypeIds typeIds;     // 类型 ID 区域
    private DexMethodIds methodIds; // 方法 ID 区域
    private DexClassDefs classDefs; // 类定义区域

    private DexProtoIds protoIds; // 类定义区域

    /**
     * 构造方法，通过文件路径读取并解析 DEX 文件。
     *
     * @param filePath DEX 文件路径
     * @throws IOException 读取文件或解析错误
     */
    public DexFile(String filePath) throws IOException {
        try (FileInputStream fis = new FileInputStream(filePath);
             FileChannel channel = fis.getChannel()) {
            // 将整个文件映射到内存中
            ByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
            buffer.order(ByteOrder.LITTLE_ENDIAN);

            // 解析头部信息
            header = new DexFileHeader();
            header.parse(buffer);

            // 解析其他区域（这里仅作占位处理，实际实现需要根据 header 中的偏移量和大小进行解析）
            stringIds = new DexStringIds();
            stringIds.parse(buffer, header);
            List<DexString> strings = stringIds.getStrings();


            typeIds = new DexTypeIds();
            typeIds.parse(buffer, header);


            protoIds  =new  DexProtoIds ();
            protoIds.parse(buffer,header);
            for (DexProtoId allProtoId : protoIds.getAllProtoIds()) {
                String methodSignature = allProtoId.getMethodSignature(stringIds, typeIds);
                System.out.println(methodSignature);
            }


            methodIds = new DexMethodIds();
            methodIds.parse(buffer, header);

            classDefs = new DexClassDefs();
            classDefs.parse(buffer, header);


        }
    }

    public DexFileHeader getHeader() {
        return header;
    }

    public DexStringIds getStringIds() {
        return stringIds;
    }

    public DexTypeIds getTypeIds() {
        return typeIds;
    }

    public DexMethodIds getMethodIds() {
        return methodIds;
    }

    public DexClassDefs getClassDefs() {
        return classDefs;
    }

    /**
     * 打印 DEX 文件的基本信息
     */
    public void printDexInfo() {
        System.out.println("=== DEX Header Info ===");
        header.printHeader();
        System.out.println("\n=== Additional Sections ===");
        System.out.println("String IDs Count: " + stringIds.getStringCount());
        System.out.println("Type IDs Count: " + typeIds.getTypeCount());
        System.out.println("Method IDs Count: " + methodIds.getMethodCount());
        System.out.println("Class Defs Count: " + classDefs.getClassDefCount());
    }

    public static void main(String[] args) {
        try {
            DexFile dexFile = new DexFile("C:\\Users\\Administrator\\Desktop\\吧中吧各个环境包\\classes.dex");
            dexFile.printDexInfo();
        } catch(IOException e) {
            System.err.println("Error reading DEX file: " + e.getMessage());
        }
    }
}

