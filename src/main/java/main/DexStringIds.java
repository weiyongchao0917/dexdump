package main;

import main.DexFileHeader;
import main.second.DexString;
import utils.DexdumpUtils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * DexStringIds 用于解析 DEX 文件中字符串 ID 部分。
 * 该部分存储了字符串偏移地址，需要读取相应地址以获取实际字符串。
 */
public class DexStringIds {

    private final List<DexString> strings = new ArrayList<>();

    /**
     * 解析 Dex 文件中的 StringId 列表
     *
     * @param buffer DEX 文件数据缓冲区
     * @param header 已解析的 DEX 文件头部信息
     */
    public void parse(ByteBuffer buffer, DexFileHeader header) {
        int stringIdsSize = header.getStringIdsSize();
        int stringIdsOff = header.getStringIdsOff();

        // 定位到字符串 ID 表的起始位置
        buffer.position(stringIdsOff);

        // 先解析字符串偏移地址
        List<Integer> stringOffsets = new ArrayList<>();
        for (int i = 0; i < stringIdsSize; i++) {
            int stringDataOffset = buffer.getInt();
            stringOffsets.add(stringDataOffset);
        }

        // 解析字符串数据
        for (Integer offset : stringOffsets) {
            buffer.position(offset);
            String str = readDexString(buffer);
            strings.add(new DexString(offset, str)); // 存储解析出的字符串
        }
    }

    private String readDexString(ByteBuffer buffer) {
        // 先读取 ULEB128 编码的字符串长度，但不使用这个值作为字符串内容
        DexdumpUtils.readUnsignedLeb128(buffer);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
            byte b = buffer.get();
            if (b == 0) { // 遇到 0 表示字符串结束
                break;
            }
            baos.write(b);
        }
        // 使用 UTF-8 编码转换成字符串
        return new String(baos.toByteArray(), StandardCharsets.UTF_8);
    }

    /**
     * 获取解析到的字符串列表
     */
    public List<DexString> getStrings() {
        return strings;
    }

    /**
     * 获取字符串数量
     */
    public int getStringCount() {
        return strings.size();
    }


    /**
     * 通过索引获取字符串
     */
    public String getStringByIndex(int index) {
        if (index < 0 || index >= strings.size()) {
            return "UNKNOWN";
        }
        return strings.get(index).getStringData();
    }
}
