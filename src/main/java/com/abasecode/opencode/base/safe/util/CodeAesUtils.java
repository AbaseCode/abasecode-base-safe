package com.abasecode.opencode.base.safe.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.security.spec.AlgorithmParameterSpec;

/**
 * @author Jon
 * e-mail: ijonso123@gmail.com
 * url: <a href="https://jon.wiki">Jon's blog</a>
 * url: <a href="https://github.com/abasecode">project github</a>
 * url: <a href="https://abasecode.com">AbaseCode.com</a>
 */
public class CodeAesUtils {

    private static final String AES_NAME = "AES";
    private static final String ALGORITHM_CTR = "AES/CTR/NoPadding";
    private static final String ALGORITHM_GCM = "AES/GCM/NoPadding";
    private static final int GCM_LENGTH = 128;

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    /**
     * encrypt
     *
     * @param source source
     * @param key    key
     * @param iv     iv
     * @return result
     * @throws Exception
     */
    public static String encrypt(String source, String key, String iv) throws Exception {
        byte[] data = null;
        Cipher cipher = Cipher.getInstance(ALGORITHM_CTR);
        SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES_NAME);
        AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, paramSpec);
        data = cipher.doFinal(source.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(data);
    }


    /**
     * decrypt
     *
     * @param encString encrypt data
     * @param key       key
     * @param iv        iv
     * @return result
     * @throws Exception
     */
    public static String decrypt(String encString, String key, String iv) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_CTR);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES_NAME);
        AlgorithmParameterSpec parameterSpec = new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, parameterSpec);
        return new String(cipher.doFinal(Base64.decodeBase64(encString)), StandardCharsets.UTF_8);
    }

    /**
     * encrypt with GCM
     *
     * @param source
     * @param key
     * @param iv
     * @param additionalData
     * @return result
     * @throws Exception
     */
    public static String encrypt(String source, String key, String iv, String additionalData) throws Exception {
        byte[] data = null;
        Cipher cipher = Cipher.getInstance(ALGORITHM_GCM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES_NAME);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_LENGTH, iv.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, parameterSpec);
        cipher.updateAAD(additionalData.getBytes(StandardCharsets.UTF_8));
        data = cipher.doFinal(source.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(data);
    }


    /**
     * decrypt with GMC
     *
     * @param encString      encrypt data
     * @param key            key
     * @param iv             iv , nonce
     * @param additionalData additional data
     * @return result
     * @throws Exception
     */
    public static String decrypt(String encString, String key, String iv, String additionalData) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM_GCM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), AES_NAME);
        GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_LENGTH, iv.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, parameterSpec);
        cipher.updateAAD(additionalData.getBytes(StandardCharsets.UTF_8));
        return new String(cipher.doFinal(java.util.Base64.getDecoder().decode(encString)), StandardCharsets.UTF_8);
    }


}