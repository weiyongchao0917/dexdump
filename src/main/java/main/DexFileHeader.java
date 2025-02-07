package main;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * DexFileHeader 用于解析 DEX 文件的头部信息。
 * DEX 文件头部固定大小为 112 字节，包含了 magic、checksum、signature、
 * fileSize、headerSize、endianTag、linkSize、linkOff、mapOff、
 * stringIdsSize、stringIdsOff、typeIdsSize、typeIdsOff、protoIdsSize、protoIdsOff、
 * fieldIdsSize、fieldIdsOff、methodIdsSize、methodIdsOff、classDefsSize、classDefsOff、
 * dataSize、dataOff 等字段。
 */
public class DexFileHeader {
    public static final int HEADER_SIZE = 112;      // 头部固定大小
    public static final int DEX_MAGIC_SIZE = 8;       // magic 字段大小
    public static final int CHECKSUM_SIZE = 4;        // checksum 大小
    public static final int SIGNATURE_SIZE = 20;      // signature 大小

    private byte[] magic;       // 文件标识符
    private int checksum;       // 校验和
    private byte[] signature;   // SHA-1 签名
    private int fileSize;       // 整个文件大小
    private int headerSize;     // 头部大小（通常为 112）
    private int endianTag;      // 字节序标志
    private int linkSize;       // 链接表大小
    private int linkOff;        // 链接表偏移量
    private int mapOff;         // map 区域偏移量
    private int stringIdsSize;  // 字符串 ID 数量
    private int stringIdsOff;   // 字符串 ID 数组偏移量
    private int typeIdsSize;    // 类型 ID 数量
    private int typeIdsOff;     // 类型 ID 数组偏移量
    private int protoIdsSize;   // 方法原型 ID 数量
    private int protoIdsOff;    // 方法原型 ID 数组偏移量
    private int fieldIdsSize;   // 字段 ID 数量
    private int fieldIdsOff;    // 字段 ID 数组偏移量
    private int methodIdsSize;  // 方法 ID 数量
    private int methodIdsOff;   // 方法 ID 数组偏移量
    private int classDefsSize;  // 类定义数量
    private int classDefsOff;   // 类定义数组偏移量
    private int dataSize;       // data 区域大小
    private int dataOff;        // data 区域偏移量

    public DexFileHeader() {}

    /**
     * 从 ByteBuffer 中解析 DEX 头部信息。
     * 注意：传入的 ByteBuffer 的 position 应该在文件开头。
     *
     * @param buffer 包含 DEX 文件数据的 ByteBuffer
     * @throws IOException 如果数据不足，则抛出异常
     */
    public void parse(ByteBuffer buffer) throws IOException {
        // 保证使用小端字节序
        buffer.order(ByteOrder.LITTLE_ENDIAN);

        // 读取头部固定大小的数据
        if (buffer.remaining() < HEADER_SIZE) {
            throw new IOException("Invalid DEX file: insufficient header size");
        }
        byte[] headerBytes = new byte[HEADER_SIZE];
        buffer.get(headerBytes);
        ByteBuffer headerBuffer = ByteBuffer.wrap(headerBytes);
        headerBuffer.order(ByteOrder.LITTLE_ENDIAN);

        // 依次解析各字段
        magic = new byte[DEX_MAGIC_SIZE];
        headerBuffer.get(magic);

        checksum = headerBuffer.getInt();

        signature = new byte[SIGNATURE_SIZE];
        headerBuffer.get(signature);

        fileSize = headerBuffer.getInt();
        headerSize = headerBuffer.getInt();
        endianTag = headerBuffer.getInt();
        linkSize = headerBuffer.getInt();
        linkOff = headerBuffer.getInt();
        mapOff = headerBuffer.getInt();
        stringIdsSize = headerBuffer.getInt();
        stringIdsOff = headerBuffer.getInt();
        typeIdsSize = headerBuffer.getInt();
        typeIdsOff = headerBuffer.getInt();
        protoIdsSize = headerBuffer.getInt();
        protoIdsOff = headerBuffer.getInt();
        fieldIdsSize = headerBuffer.getInt();
        fieldIdsOff = headerBuffer.getInt();
        methodIdsSize = headerBuffer.getInt();
        methodIdsOff = headerBuffer.getInt();
        classDefsSize = headerBuffer.getInt();
        classDefsOff = headerBuffer.getInt();
        dataSize = headerBuffer.getInt();
        dataOff = headerBuffer.getInt();
    }

    public String getMagic() {
        return new String(magic);
    }

    public int getChecksum() {
        return checksum;
    }

    public byte[] getSignature() {
        return signature;
    }

    public int getFileSize() {
        return fileSize;
    }

    public int getHeaderSize() {
        return headerSize;
    }

    public int getEndianTag() {
        return endianTag;
    }

    public int getLinkSize() {
        return linkSize;
    }

    public int getLinkOff() {
        return linkOff;
    }

    public int getMapOff() {
        return mapOff;
    }

    public int getStringIdsSize() {
        return stringIdsSize;
    }

    public int getStringIdsOff() {
        return stringIdsOff;
    }

    public int getTypeIdsSize() {
        return typeIdsSize;
    }

    public int getTypeIdsOff() {
        return typeIdsOff;
    }

    public int getProtoIdsSize() {
        return protoIdsSize;
    }

    public int getProtoIdsOff() {
        return protoIdsOff;
    }

    public int getFieldIdsSize() {
        return fieldIdsSize;
    }

    public int getFieldIdsOff() {
        return fieldIdsOff;
    }

    public int getMethodIdsSize() {
        return methodIdsSize;
    }

    public int getMethodIdsOff() {
        return methodIdsOff;
    }

    public int getClassDefsSize() {
        return classDefsSize;
    }

    public int getClassDefsOff() {
        return classDefsOff;
    }

    public int getDataSize() {
        return dataSize;
    }

    public int getDataOff() {
        return dataOff;
    }

    /**
     * 打印解析的头部信息
     */
    public void printHeader() {
        System.out.println("DEX Magic: " + getMagic());
        System.out.println("Checksum: " + Integer.toHexString(getChecksum()));
        System.out.println("File Size: " + getFileSize());
        System.out.println("Header Size: " + getHeaderSize());
        System.out.println("Endian Tag: " + Integer.toHexString(getEndianTag()));
        System.out.println("Link Size: " + getLinkSize() + ", Link Off: " + getLinkOff());
        System.out.println("Map Off: " + getMapOff());
        System.out.println("String IDs: count=" + getStringIdsSize() + ", off=" + getStringIdsOff());
        System.out.println("Type IDs: count=" + getTypeIdsSize() + ", off=" + getTypeIdsOff());
        System.out.println("Proto IDs: count=" + getProtoIdsSize() + ", off=" + getProtoIdsOff());
        System.out.println("Field IDs: count=" + getFieldIdsSize() + ", off=" + getFieldIdsOff());
        System.out.println("Method IDs: count=" + getMethodIdsSize() + ", off=" + getMethodIdsOff());
        System.out.println("Class Defs: count=" + getClassDefsSize() + ", off=" + getClassDefsOff());
        System.out.println("Data: size=" + getDataSize() + ", off=" + getDataOff());
    }
}


