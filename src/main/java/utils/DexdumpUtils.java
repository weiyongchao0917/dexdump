package utils;

import java.nio.ByteBuffer;

/**
 * @Classname DexdumpUtils
 * @Description TODO
 * @Date 2025/2/6 20:08
 * @Created by youngChar
 */
public class DexdumpUtils {

    /**
     * 从 ByteBuffer 中读取一个无符号的 LEB128 编码整数。
     *
     * LEB128 是一种变长编码格式，用于存储小整数。每个字节的高位（第8位）作为
     * 延续标志，如果为1，说明还有后续字节；如果为0，表示这是最后一个字节。
     *
     * 该方法会从 buffer 中依次读取字节，并根据低7位拼接成结果。注意，该函数会更新
     * ByteBuffer 的 position。
     *
     * @param buffer 包含 LEB128 编码数据的 ByteBuffer
     * @return 解析出的无符号整数
     * @throws RuntimeException 如果 ByteBuffer 中的数据不足
     */
    public static int readUnsignedLeb128(ByteBuffer buffer) {
        int result = 0;
        int shift = 0;
        while (true) {
            if (!buffer.hasRemaining()) {
                throw new RuntimeException("Buffer underflow: insufficient data for LEB128");
            }
            // 读取一个字节，并转换为无符号整数（0~255）
            int b = buffer.get() & 0xFF;
            // 低7位作为数据，累加到 result 中
            result |= (b & 0x7F) << shift;
            // 如果高位不为1，表示这是最后一个字节
            if ((b & 0x80) == 0) {
                break;
            }
            // 每读取一字节，左移 7 位
            shift += 7;
        }
        return result;
    }

    public static void main(String[] args) {
        // 构造一个 ByteBuffer，其中包含一个无符号 LEB128 编码的数字
        // 例如，编码数字 624485 应该是 0xE5 0x8E 0x26
        byte[] leb128Data = {(byte) 0xE5, (byte) 0x8E, (byte) 0x26};
        ByteBuffer buffer = ByteBuffer.wrap(leb128Data);

        int value = readUnsignedLeb128(buffer);
        System.out.println("解析出的 LEB128 数值为: " + value);  // 输出应为 624485
    }
}
