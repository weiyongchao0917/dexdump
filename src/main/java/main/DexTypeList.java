package main;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * DexTypeList 解析 DEX 中的类型列表（参数列表）
 */
public class DexTypeList {
    private List<Integer> typeIndexes = new ArrayList<>();

    /**
     * 解析 DexTypeList
     *
     * @param buffer DEX 文件的 ByteBuffer
     * @param offset 参数列表的偏移地址（parametersOff）
     */
    public void parse(ByteBuffer buffer, int offset) {
        if (offset == 0) {
            return; // 没有参数
        }
        // 设置字节序为 little-endian
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        buffer.position(offset);
        int size = buffer.getInt(); // 读取参数个数
        for (int i = 0; i < size; i++) {
            if (buffer.remaining() < 2) {
                break;
            }
            typeIndexes.add(buffer.getShort() & 0xFFFF);
        }
    }


    /**
     * 获取参数类型索引列表
     */
    public List<Integer> getTypeIndexes() {
        return typeIndexes;
    }
}