package main;

import main.second.DexFieldId;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class DexFieldIds {
    private List<DexFieldId> fieldIds = new ArrayList<>();

    /**
     * 解析字段 ID 表
     *
     * @param buffer DEX 文件数据的 ByteBuffer
     * @param header 已解析的 DexFileHeader 对象，其中包含 field_ids_size 和 field_ids_off 信息
     */
    public void parse(ByteBuffer buffer, DexFileHeader header) {
        int fieldIdsSize = header.getFieldIdsSize();
        int fieldIdsOff = header.getFieldIdsOff();

        // 确保使用 little-endian（DEX 文件采用小端字节序）
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        // 定位到字段 ID 表的起始位置
        buffer.position(fieldIdsOff);

        // 每个字段 ID 项占 8 字节
        for (int i = 0; i < fieldIdsSize; i++) {
            // 读取 class_idx（2 字节，无符号）
            int classIdx = buffer.getShort() & 0xFFFF;
            // 读取 type_idx（2 字节，无符号）
            int typeIdx = buffer.getShort() & 0xFFFF;
            // 读取 name_idx（4 字节，无符号）
            int nameIdx = buffer.getInt();
            fieldIds.add(new DexFieldId(classIdx, typeIdx, nameIdx));
        }
    }

    /**
     * 获取所有解析得到的字段 ID 项
     *
     * @return 字段 ID 列表
     */
    public List<DexFieldId> getFieldIds() {
        return fieldIds;
    }

    /**
     * 根据索引获取某个字段 ID 项
     *
     * @param index 字段在列表中的索引
     * @return 对应的 DexFieldId 对象，若索引非法则返回 null
     */
    public DexFieldId getFieldId(int index) {
        if (index < 0 || index >= fieldIds.size()) {
            return null;
        }
        return fieldIds.get(index);
    }
}