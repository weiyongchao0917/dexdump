package main.second;

/**
 * DexTypeId 代表 DEX 文件中的一个类型 ID
 * 它的作用是指向 DexStringIds 中的某个字符串，该字符串代表某个类型（类、接口、数组等）
 */
public class DexTypeId {
    private int descriptorIdx; // 指向 DexStringIds 表的索引

    public DexTypeId(int descriptorIdx) {
        this.descriptorIdx = descriptorIdx;
    }

    public int getDescriptorIdx() {
        return descriptorIdx;
    }

    @Override
    public String toString() {
        return "DexTypeId{" +
                "descriptorIdx=" + descriptorIdx +
                '}';
    }




}
