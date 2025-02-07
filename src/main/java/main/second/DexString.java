package main.second;

public class DexString {
    private final int offset;  // 字符串在 DEX 文件中的偏移量
    private String stringData; // 实际的字符串内容

    public DexString(int offset, String stringData) {
        this.offset = offset;
        this.stringData = stringData;
    }

    public int getOffset() {
        return offset;
    }

    public String getStringData() {
        return stringData;
    }

    public void setString(String stringData) {
        this.stringData = stringData;
    }
}
