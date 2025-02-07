package main;

import main.second.DexTypeId;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * DexTypeIds 用于解析 DEX 文件中的类型 ID 区域。
 * 类型 ID 部分保存了对字符串 ID（描述符）的索引，实际解析时需要根据 header 中的 typeIdsSize 和 typeIdsOff 字段。
 */
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * DexTypeIds 负责解析 DEX 文件中的 type_ids 表
 * 该表存储类型信息，每个类型 ID 指向 DexStringIds 中的字符串索引
 */
public class DexTypeIds {
    private List<DexTypeId> typeIds = new ArrayList<>();

    /**
     * 解析 Type IDs 表
     *
     * @param buffer DEX 文件的 ByteBuffer
     * @param header DEX 文件头信息
     */
    public void parse(ByteBuffer buffer, DexFileHeader header) {
        int typeIdsSize = header.getTypeIdsSize(); // 读取 type_ids 数量
        int typeIdsOff = header.getTypeIdsOff();   // 读取 type_ids 起始偏移

        // 定位到 type_ids 表的位置
        buffer.position(typeIdsOff);

        for (int i = 0; i < typeIdsSize; i++) {
            int descriptorIdx = buffer.getInt(); // 读取索引（指向 DexStringIds 表）
            typeIds.add(new DexTypeId(descriptorIdx));
        }
    }

    /**
     * 获取解析得到的类型 ID 列表
     */
    public List<DexTypeId> getTypeIds() {
        return typeIds;
    }

    /**
     * 获取类型 ID 数量
     */
    public int getTypeCount() {
        return typeIds.size();
    }

    public String getTypeName(int typeIdx, DexStringIds dexStringIds) {
        if (typeIdx < 0 || typeIdx >= typeIds.size()) {
            return "UNKNOWN";
        }
        int stringIdx = typeIds.get(typeIdx).getDescriptorIdx(); // 获取 DexStringIds 的索引
        return dexStringIds.getStringByIndex(stringIdx); // 从 DexStringIds 获取字符串
    }


    public String getParameterTypeName(Buffer buffer,int typeIdx, DexStringIds dexStringIds) {

        int stringIdx = typeIds.get(typeIdx).getDescriptorIdx(); // 获取 DexStringIds 的索引
        return dexStringIds.getStringByIndex(stringIdx); // 从 DexStringIds 获取字符串
    }
}
