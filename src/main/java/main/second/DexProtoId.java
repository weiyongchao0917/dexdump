package main.second;

import main.DexStringIds;
import main.DexTypeIds;

import java.nio.ByteBuffer;

/**
 * DexProtoId 代表 DEX 文件中的方法原型信息
 */
public class DexProtoId {
    private int shortyIdx;       // 方法的短描述，指向 DexStringIds
    private int returnTypeIdx;   // 返回类型，指向 DexTypeIds
    private int parametersOff;   // 参数列表偏移量，指向 DexTypeList

    public DexProtoId(int shortyIdx, int returnTypeIdx, int parametersOff) {
        this.shortyIdx = shortyIdx;
        this.returnTypeIdx = returnTypeIdx;
        this.parametersOff = parametersOff;
    }

    public int getShortyIdx() {
        return shortyIdx;
    }

    public int getReturnTypeIdx() {
        return returnTypeIdx;
    }

    public int getParametersOff() {
        return parametersOff;
    }

    @Override
    public String toString() {
        return String.format("DexProtoId{shortyIdx=%d, returnTypeIdx=%d, parametersOff=%d}",
                shortyIdx, returnTypeIdx, parametersOff);
    }

    /**
     * 从 ByteBuffer 解析 DexProtoId
     */
    public static DexProtoId fromBuffer(ByteBuffer buffer) {
        int shortyIdx = buffer.getInt();
        int returnTypeIdx = buffer.getInt();
        int parametersOff = buffer.getInt();
        return new DexProtoId(shortyIdx, returnTypeIdx, parametersOff);
    }


    public String getMethodSignature(DexStringIds dexStringIds, DexTypeIds dexTypeIds) {


        // 获取方法的短描述
        String shorty = dexStringIds.getStringByIndex(shortyIdx);

        // 获取返回类型
        String returnType = dexTypeIds.getTypeName(returnTypeIdx, dexStringIds);

        String paramType = dexTypeIds.getTypeName(parametersOff, dexStringIds);

        // 获取参数类型（暂时省略，需要解析 parametersOff）
        return shorty + " " + returnType + "("+paramType+ ")";
    }
}