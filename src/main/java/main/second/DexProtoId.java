package main.second;

import main.DexStringIds;
import main.DexTypeIds;
import main.DexTypeList;

import java.nio.ByteBuffer;
import java.util.List;

/**
 * DexProtoId 代表 DEX 文件中的方法原型信息
 */
public class DexProtoId {
    private int shortyIdx;       // 方法的短描述，指向 DexStringIds
    private int returnTypeIdx;   // 返回类型，指向 DexTypeIds
    private int parametersOff;   // 参数列表偏移量，指向 DexTypeList

    private DexTypeList typeList = new DexTypeList(); // 解析出的参数类型列表

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
        // 获取方法的短描述，确保解析时已经去除了 ULEB128 长度前缀
        String shorty = dexStringIds.getStringByIndex(shortyIdx);
        // 获取返回类型
        String returnType = dexTypeIds.getTypeName(returnTypeIdx, dexStringIds);

        // 获取参数类型索引和名称
        List<Integer> paramTypeIndexes = getParameterTypeIndexes();
        List<String> paramTypes = dexTypeIds.getTypeNames(
                paramTypeIndexes.stream().mapToInt(i -> i).toArray(), dexStringIds);

        // 使用 String.join 格式化参数列表
        String paramTypesStr = String.join(", ", paramTypes);

        return    returnType + "(" + paramTypesStr + ")";
    }


    public void parseParameters(ByteBuffer buffer) {
        typeList.parse(buffer, parametersOff);
    }

    /**
     * 获取参数类型索引列表
     */
    public List<Integer> getParameterTypeIndexes() {
        return typeList.getTypeIndexes();
    }
}