package main;

import main.second.DexMethodId;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

/**
 * DexMethodIds 用于解析 DEX 文件中的 method_ids 表。
 *
 * DEX 文件头中包含 method_ids_size 和 method_ids_off 两个字段，
 * 用于确定方法 ID 表的数量和起始位置。
 *
 * 每个方法 ID 项的结构（共 8 字节）：
 *   [ class_idx (2 bytes) | proto_idx (2 bytes) | name_idx (4 bytes) ]
 */
public class DexMethodIds {
    private List<DexMethodId> methodIds = new ArrayList<>();

    /**
     * 解析方法 ID 表
     *
     * @param buffer DEX 文件数据的 ByteBuffer
     * @param header 已解析的 DexFileHeader，其中包含 method_ids_size 和 method_ids_off 信息
     */
    public void parse(ByteBuffer buffer, DexFileHeader header) {
        int methodIdsSize = header.getMethodIdsSize();
        int methodIdsOff = header.getMethodIdsOff();

        // 设置字节序为 little-endian（DEX 文件采用小端字节序）
        buffer.order(ByteOrder.LITTLE_ENDIAN);
        // 定位到方法 ID 表的起始位置
        buffer.position(methodIdsOff);

        for (int i = 0; i < methodIdsSize; i++) {
            // 读取 2 字节的 class_idx，无符号转换
            int classIdx = buffer.getShort() & 0xFFFF;
            // 读取 2 字节的 proto_idx，无符号转换
            int protoIdx = buffer.getShort() & 0xFFFF;
            // 读取 4 字节的 name_idx
            int nameIdx = buffer.getInt();
            methodIds.add(new DexMethodId(classIdx, protoIdx, nameIdx));
        }
    }

    /**
     * 获取所有解析得到的 MethodId 项
     */
    public List<DexMethodId> getMethodIds() {
        return methodIds;
    }

    /**
     * 获取方法 ID 项数量
     */
    public int getMethodCount() {
        return methodIds.size();
    }

    /**
     * 根据索引获取单个方法 ID 项
     *
     * @param index 方法在列表中的索引
     * @return 对应的 DexMethodId 对象，若索引非法则返回 null
     */
    public DexMethodId getMethodId(int index) {
        if (index < 0 || index >= methodIds.size()) {
            return null;
        }
        return methodIds.get(index);
    }
}
