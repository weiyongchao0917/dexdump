package main.second;

public class EncodedField {
    // 实际上存储的是字段索引增量和访问标志
    private int fieldIdxDelta;
    private int accessFlags;

    public EncodedField(int fieldIdxDelta, int accessFlags) {
        this.fieldIdxDelta = fieldIdxDelta;
        this.accessFlags = accessFlags;
    }

    public int getFieldIdxDelta() {
        return fieldIdxDelta;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    @Override
    public String toString() {
        return String.format("EncodedField{fieldIdxDelta=%d, accessFlags=0x%s}",
                fieldIdxDelta, Integer.toHexString(accessFlags));
    }
}
