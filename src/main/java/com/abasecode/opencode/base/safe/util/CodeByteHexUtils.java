package com.abasecode.opencode.base.safe.util;

import javax.xml.bind.DatatypeConverter;

/**
 * @author Jon
 * e-mail: ijonso123@gmail.com
 * url: <a href="https://jon.wiki">Jon's blog</a>
 * url: <a href="https://github.com/abasecode">project github</a>
 * url: <a href="https://abasecode.com">AbaseCode.com</a>
 */
public class CodeByteHexUtils {


    /**
     * HexString 2 byte[]
     *
     * @param hexString
     * @return result
     */
    public static byte[] getByteFromHexString(String hexString) {
        return DatatypeConverter.parseHexBinary(hexString);
    }

    /**
     * BASE64 String 2 byte[]
     *
     * @param base64String
     * @return result
     */
    public static byte[] getByteFromBase64String(String base64String) {
        return DatatypeConverter.parseBase64Binary(base64String);
    }


    /**
     * byte[] 2 HexString
     *
     * @param sourceByte
     * @return result
     */
    public static String getHexString(byte[] sourceByte) {
        return DatatypeConverter.printHexBinary(sourceByte);
    }

    /**
     * byte[] 2 BASE64 String
     *
     * @param sourceByte
     * @return result
     */
    public static String getBase64tring(byte[] sourceByte) {
        return DatatypeConverter.printBase64Binary(sourceByte);
    }
}