package main;

import main.second.DexProtoId;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * DexProtoIds 解析 DEX 文件中的方法原型 ID 表
 */
public class DexProtoIds {
    private List<DexProtoId> protoIds = new ArrayList<>();

    public void parse(ByteBuffer buffer, DexFileHeader header) {
        int protoIdsOff = header.getProtoIdsOff();
        int protoIdsSize = header.getProtoIdsSize();
        buffer.position(protoIdsOff);
        for (int i = 0; i < protoIdsSize; i++) {
            DexProtoId protoId = DexProtoId.fromBuffer(buffer);

            // 使用副本解析参数列表，不影响主 buffer 的位置
            ByteBuffer dup = buffer.duplicate();
            protoId.parseParameters(dup);

            protoIds.add(protoId);
        }
    }

    public DexProtoId getProtoId(int index) {
        if (index < 0 || index >= protoIds.size()) {
            return null;
        }
        return protoIds.get(index);
    }

    public List<DexProtoId> getAllProtoIds() {
        return protoIds;
    }
}
