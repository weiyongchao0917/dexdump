package main;

import main.DexFileHeader;
import utils.DexdumpUtils;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * DexStringIds 用于解析 DEX 文件中字符串 ID 部分。
 * 实际上，该部分包含一个字符串偏移量的数组，每个偏移量指向文件中的字符串数据。
 * 这里给出一个简单的占位实现，实际解析需要参考 DEX 文件格式规范。
 */
public class DexStringIds {


    private List<String> strings = new ArrayList<>();

    /**
     * 从 ByteBuffer 中解析字符串 ID 部分。
     *
     * @param buffer 包含整个 DEX 文件数据的 ByteBuffer
     * @param header 已解析的 DEX 文件头部信息
     */
    public void parse(ByteBuffer buffer, DexFileHeader header) {
        int stringIdsSize = header.getStringIdsSize();
        int stringIdsOff = header.getStringIdsOff();

        // 定位到字符串 ID 表的起始位置
        buffer.position(stringIdsOff);

        // 每个字符串 ID 项占用 4 字节（整数），表示字符串数据的偏移量
        for (int i = 0; i < stringIdsSize; i++) {
            int stringDataOffset = buffer.getInt();
            String str = readDexString(buffer, stringDataOffset);
            strings.add(str);
        }
    }

    /**
     * 根据给定的 offset 读取一个字符串。
     *
     * @param buffer ByteBuffer（整个 DEX 文件映射）
     * @param offset 字符串数据在文件中的偏移量
     * @return 解析得到的字符串
     */
    private String readDexString(ByteBuffer buffer, int offset) {
        // 保存当前的 buffer 位置，以便后续恢复
        int oldPos = buffer.position();
        // 定位到字符串数据处
        buffer.position(offset);

        // 第一个值使用 LEB128 编码，表示字符串的 UTF-16 长度（可以忽略该值，用于校验）
        int utf16Length = DexdumpUtils.readUnsignedLeb128(buffer);
        // 读取字符串数据直到遇到终止符（0）
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
             byte b = buffer.get();
               if (b == 0) { // 字符串以 0 结束
                break;
            }
            baos.write(b);
        }
        // 将读取到的字节使用 UTF-8 编码转换成字符串
        String result = new String(baos.toByteArray(), StandardCharsets.UTF_8);
        // 恢复 buffer 之前的位置
        buffer.position(oldPos);
        return result;
    }
    /**
     * 获取解析得到的字符串列表
     */
    public List<String> getStrings() {
        return strings;
    }

    /**
     * 获取字符串数量
     */
    public int getStringCount() {
        return strings.size();
    }
}
