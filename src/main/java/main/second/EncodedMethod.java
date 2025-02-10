package main.second;

public class EncodedMethod {
    // 同样存储方法索引增量、访问标志和代码_off（方法实现偏移）
    private int methodIdxDelta;
    private int accessFlags;
    private int codeOff;

    public EncodedMethod(int methodIdxDelta, int accessFlags, int codeOff) {
        this.methodIdxDelta = methodIdxDelta;
        this.accessFlags = accessFlags;
        this.codeOff = codeOff;
    }

    public int getMethodIdxDelta() {
        return methodIdxDelta;
    }

    public int getAccessFlags() {
        return accessFlags;
    }

    public int getCodeOff() {
        return codeOff;
    }

    @Override
    public String toString() {
        return String.format("EncodedMethod{methodIdxDelta=%d, accessFlags=0x%s, codeOff=0x%x}",
                methodIdxDelta, Integer.toHexString(accessFlags), codeOff);
    }
}
