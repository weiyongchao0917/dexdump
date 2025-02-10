package main;

import main.second.EncodedField;
import main.second.EncodedMethod;
import utils.DexdumpUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class DexClassData {
    private int staticFieldsSize;
    private int instanceFieldsSize;
    private int directMethodsSize;
    private int virtualMethodsSize;

    private List<EncodedField> staticFields = new ArrayList<>();
    private List<EncodedField> instanceFields = new ArrayList<>();
    private List<EncodedMethod> directMethods = new ArrayList<>();
    private List<EncodedMethod> virtualMethods = new ArrayList<>();

    /**
     * 解析 class_data_item，从 offset 处开始
     */
    public static DexClassData parse(ByteBuffer buffer, int offset) {
        if(offset == 0) {
            return null; // 无 class data
        }
        buffer.position(offset);
        DexClassData data = new DexClassData();
        data.staticFieldsSize = DexdumpUtils.readUnsignedLeb128(buffer);
        data.instanceFieldsSize = DexdumpUtils.readUnsignedLeb128(buffer);
        data.directMethodsSize = DexdumpUtils.readUnsignedLeb128(buffer);
        data.virtualMethodsSize = DexdumpUtils.readUnsignedLeb128(buffer);

        // 注意：下面的解析需要依赖于 delta 编码的累加逻辑，
        // 这里只给出简单示例，实际实现时需要参照 DEX 格式详细解析。
        for (int i = 0; i < data.staticFieldsSize; i++) {
            int fieldIdxDelta = DexdumpUtils.readUnsignedLeb128(buffer);
            int accessFlags = DexdumpUtils.readUnsignedLeb128(buffer);
            data.staticFields.add(new EncodedField(fieldIdxDelta, accessFlags));
        }
        for (int i = 0; i < data.instanceFieldsSize; i++) {
            int fieldIdxDelta = DexdumpUtils.readUnsignedLeb128(buffer);
            int accessFlags = DexdumpUtils.readUnsignedLeb128(buffer);
            data.instanceFields.add(new EncodedField(fieldIdxDelta, accessFlags));
        }
        for (int i = 0; i < data.directMethodsSize; i++) {
            int methodIdxDelta = DexdumpUtils.readUnsignedLeb128(buffer);
            int accessFlags = DexdumpUtils.readUnsignedLeb128(buffer);
            int codeOff = DexdumpUtils.readUnsignedLeb128(buffer);
            data.directMethods.add(new EncodedMethod(methodIdxDelta, accessFlags, codeOff));
        }
        for (int i = 0; i < data.virtualMethodsSize; i++) {
            int methodIdxDelta = DexdumpUtils.readUnsignedLeb128(buffer);
            int accessFlags = DexdumpUtils.readUnsignedLeb128(buffer);
            int codeOff = DexdumpUtils.readUnsignedLeb128(buffer);
            data.virtualMethods.add(new EncodedMethod(methodIdxDelta, accessFlags, codeOff));
        }
        return data;
    }

    public int getStaticFieldsSize() {
        return staticFieldsSize;
    }

    public int getInstanceFieldsSize() {
        return instanceFieldsSize;
    }

    public int getDirectMethodsSize() {
        return directMethodsSize;
    }

    public int getVirtualMethodsSize() {
        return virtualMethodsSize;
    }

    public List<EncodedField> getStaticFields() {
        return staticFields;
    }

    public List<EncodedField> getInstanceFields() {
        return instanceFields;
    }

    public List<EncodedMethod> getDirectMethods() {
        return directMethods;
    }

    public List<EncodedMethod> getVirtualMethods() {
        return virtualMethods;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ClassData:\n");
        sb.append("  Static Fields (").append(staticFieldsSize).append("):\n");
        for (EncodedField f : staticFields) {
            sb.append("    ").append(f).append("\n");
        }
        sb.append("  Instance Fields (").append(instanceFieldsSize).append("):\n");
        for (EncodedField f : instanceFields) {
            sb.append("    ").append(f).append("\n");
        }
        sb.append("  Direct Methods (").append(directMethodsSize).append("):\n");
        for (EncodedMethod m : directMethods) {
            sb.append("    ").append(m).append("\n");
        }
        sb.append("  Virtual Methods (").append(virtualMethodsSize).append("):\n");
        for (EncodedMethod m : virtualMethods) {
            sb.append("    ").append(m).append("\n");
        }
        return sb.toString();
    }
}
