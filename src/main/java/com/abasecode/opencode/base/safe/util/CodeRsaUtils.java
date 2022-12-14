package com.abasecode.opencode.base.safe.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.security.cert.X509Certificate;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author Jon
 * e-mail: ijonso123@gmail.com
 * url: <a href="https://jon.wiki">Jon's blog</a>
 * url: <a href="https://github.com/abasecode">project github</a>
 * url: <a href="https://abasecode.com">AbaseCode.com</a>
 */
public class CodeRsaUtils {
    private static final int KEY_SIZE = 2048;
    private static final String ALGORITHM_SHA1_RSA = "Sha1WithRSA";
    private static final String ALGORITHM_OAEP_SHA = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
    private static final String RSA = "RSA";

    /**
     * create keyPair
     *
     * @return result
     * @throws NoSuchAlgorithmException
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator;
        keyPairGenerator = KeyPairGenerator.getInstance(RSA);
        keyPairGenerator.initialize(KEY_SIZE);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    /**
     * get privateKey
     *
     * @param keyPair keyPair
     * @return result
     */
    public static RSAPrivateKey getRSAPrivateKey(KeyPair keyPair) {
        return (RSAPrivateKey) keyPair.getPrivate();
    }

    /**
     * get privateKey string
     *
     * @param keyPair keyPair
     * @return result
     */
    public static String getRSAPrivateKeyString(KeyPair keyPair) {
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        return Base64.encodeBase64String(rsaPrivateKey.getEncoded());
    }

    /**
     * get privateKey
     *
     * @param rsaPrivateKey rsaPrivateKey
     * @return result
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static PrivateKey getPrivateKey(RSAPrivateKey rsaPrivateKey) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(rsaPrivateKey.getEncoded());
        return KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);
    }

    /**
     * get privateKey
     *
     * @param privateString privateString
     * @return result
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String privateString) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateString);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePrivate(keySpec);
    }

    /**
     * get publicKey
     *
     * @param rsaPublicKey rsaPublicKey
     * @return result
     */
    public static PublicKey getPublicKey(RSAPublicKey rsaPublicKey) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(rsaPublicKey.getEncoded());
        return KeyFactory.getInstance(RSA).generatePublic(x509EncodedKeySpec);
    }

    /**
     * get publicKey
     *
     * @param publicKeyString publicKeyString
     * @return result
     * @throws Exception
     */
    public static PublicKey getPublicKey(String publicKeyString) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKeyString);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA);
        return keyFactory.generatePublic(keySpec);
    }

    /**
     * get RSA
     *
     * @param keyPair keyPair
     * @return result
     */
    public static RSAPublicKey getRSAPublicKey(KeyPair keyPair) {
        return (RSAPublicKey) keyPair.getPublic();
    }

    /**
     * get RSA string
     *
     * @param keyPair keyPair
     * @return result
     */
    public static String getRSAPublicKeyString(KeyPair keyPair) {
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        return Base64.encodeBase64String(rsaPublicKey.getEncoded());
    }

    /**
     * encrypt by publicKey
     *
     * @param source       source
     * @param rsaPublicKey rsaPublicKey
     * @return result
     * @throws Exception
     */
    public static String encrypt(String source, RSAPublicKey rsaPublicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, rsaPublicKey);
        return Base64.encodeBase64String(cipher.doFinal(source.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * encrypt by publicKey
     *
     * @param source    source
     * @param publicKey publicKey
     * @return result
     * @throws Exception
     */
    public static String encrypt(String source, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(source.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * encrypt by publicKey
     *
     * @param source             source
     * @param rsaPublicKeyString rsaPublicKeyString
     * @return result
     * @throws Exception
     */
    public static String encrypt(String source, String rsaPublicKeyString) throws Exception {
        byte[] keyPubByte = Base64.decodeBase64(rsaPublicKeyString);
        RSAPublicKey keyPub = (RSAPublicKey) KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(keyPubByte));
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, keyPub);
        return Base64.encodeBase64String(cipher.doFinal(source.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * encrypt by privateKey
     *
     * @param source     source
     * @param privateKey privateKey
     * @return result
     * @throws Exception
     */
    public static String encrypt(String source, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(source.getBytes(StandardCharsets.UTF_8));
        return Base64.encodeBase64String(result);
    }

    /**
     * encode with x509
     *
     * @param source      source
     * @param certificate x509
     * @return result
     * @throws IllegalBlockSizeException
     */
    public static String encryptOAEP(String source, X509Certificate certificate) throws IllegalBlockSizeException {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_OAEP_SHA);
            cipher.init(Cipher.ENCRYPT_MODE, certificate.getPublicKey());
            byte[] data = source.getBytes(StandardCharsets.UTF_8);
            byte[] ciphertext = cipher.doFinal(data);
            return java.util.Base64.getEncoder().encodeToString(ciphertext);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("The current Java environment does not support RSA v1.5/OAEP", e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("Invalid certificates", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalBlockSizeException("The length of the original encrypted string cannot exceed 214 bytes");
        }
    }


    /**
     * decrypt by privatekey
     *
     * @param encString  encString
     * @param privateKey privateKey
     * @return result
     * @throws BadPaddingException
     */
    public static String decryptOAEP(String encString, PrivateKey privateKey) throws BadPaddingException {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM_OAEP_SHA);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] data = java.util.Base64.getDecoder().decode(encString);
            return new String(cipher.doFinal(data), StandardCharsets.UTF_8);

        } catch (NoSuchPaddingException | NoSuchAlgorithmException e) {
            throw new RuntimeException("The current Java environment does not support RSA v1.5/OAEP", e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException("Invalid private key.", e);
        } catch (BadPaddingException | IllegalBlockSizeException e) {
            throw new BadPaddingException("Failed to decrypt");
        }
    }


    /**
     * decrypt by privateKey
     *
     * @param encString  encString
     * @param privateKey privateKey
     * @return result
     * @throws Exception
     */
    public static String decrypt(String encString, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(encString));
        return new String(result);
    }


    /**
     * decrypt by privateKey
     *
     * @param encString     encString
     * @param rsaPrivateKey rsaPrivateKey
     * @return result
     * @throws Exception
     */
    public static String decrypt(String encString, RSAPrivateKey rsaPrivateKey) throws Exception {
        byte[] encStringByte = Base64.decodeBase64(encString.getBytes(StandardCharsets.UTF_8));
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, rsaPrivateKey);
        return new String(cipher.doFinal(encStringByte));
    }

    /**
     * decrypt by privateKey
     *
     * @param encString           encString
     * @param rsaPrivateKeyString rsaPrivateKeyString
     * @return result
     * @throws Exception
     */
    public static String decrypt(String encString, String rsaPrivateKeyString) throws Exception {
        byte[] encStringByte = Base64.decodeBase64(encString.getBytes(StandardCharsets.UTF_8));
        byte[] keyPrivateByte = Base64.decodeBase64(rsaPrivateKeyString);
        RSAPrivateKey keyPrivate = (RSAPrivateKey) KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(keyPrivateByte));
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, keyPrivate);
        return new String(cipher.doFinal(encStringByte));
    }

    /**
     * decrypt by publickey
     *
     * @param encString encString
     * @param publicKey publicKe
     * @return result
     * @throws Exception
     */
    public static String decrypt(String encString, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(encString));
        return new String(result);
    }

    /**
     * sign by privatekey
     *
     * @param source        source
     * @param rsaPrivateKey rsaPrivateKey
     * @return result
     * @throws Exception
     */
    public static String sign(String source, RSAPrivateKey rsaPrivateKey) throws Exception {
        byte[] signInfo = signByte(source, rsaPrivateKey);
        return Base64.encodeBase64String(signInfo);
    }

    /**
     * sign by privatekey
     *
     * @param source     source
     * @param privateKey privateKey
     * @return result
     * @throws Exception
     */
    public static String sign(String source, PrivateKey privateKey) throws Exception {
        byte[] signInfo = signByte(source, privateKey);
        return Base64.encodeBase64String(signInfo);
    }

    /**
     * sign by privatekey return byte[]
     *
     * @param source     source
     * @param privateKey privateKey
     * @return result
     * @throws Exception
     */
    public static byte[] signByte(String source, PrivateKey privateKey) throws Exception {
        Signature sign = Signature.getInstance(ALGORITHM_SHA1_RSA);
        sign.initSign(privateKey);
        sign.update(source.getBytes(StandardCharsets.UTF_8));
        return sign.sign();
    }


    /**
     * verify sign by publickey
     *
     * @param source       source
     * @param sign         sign
     * @param rsaPublicKey rsaPublicKey
     * @return result
     * @throws Exception
     */
    public static boolean verifySign(String source, String sign, RSAPublicKey rsaPublicKey) throws Exception {
        return verifySign(source.getBytes(StandardCharsets.UTF_8), Base64.decodeBase64(sign), rsaPublicKey);
    }

    /**
     * verify sign by publickey
     *
     * @param source    source
     * @param sign      sign
     * @param publicKey publicKey
     * @return result
     * @throws Exception
     */
    public static boolean verifySign(String source, String sign, PublicKey publicKey) throws Exception {
        return verifySign(source.getBytes(StandardCharsets.UTF_8), Base64.decodeBase64(sign), publicKey);
    }

    /**
     * verify sign by publickey
     *
     * @param sourceByte sourceByte
     * @param signByte   signByte
     * @param publicKey  publicKey
     * @return result
     * @throws Exception
     */
    public static boolean verifySign(byte[] sourceByte, byte[] signByte, PublicKey publicKey) throws Exception {
        Signature sign = Signature.getInstance(ALGORITHM_SHA1_RSA);
        sign.initVerify(publicKey);
        sign.update(sourceByte);
        return sign.verify(signByte);
    }

    /**
     * sign file by privatekey
     *
     * @param file       file
     * @param privateKey privateKey
     * @return result
     * @throws Exception
     */
    public static byte[] signFileByte(File file, PrivateKey privateKey) throws Exception {
        Signature sign = Signature.getInstance(ALGORITHM_SHA1_RSA);
        sign.initSign(privateKey);
        try (InputStream in = new FileInputStream(file)) {
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = in.read(buf)) != -1) {
                sign.update(buf, 0, len);
            }
        }
        return sign.sign();
    }


    /**
     * sign file by privatekey
     *
     * @param filePath   filePath
     * @param privateKey privateKey
     * @return result
     * @throws Exception
     */
    public static String signFile(String filePath, PrivateKey privateKey) throws Exception {
        File file = new File(filePath);
        return Base64.encodeBase64String(signFileByte(file, privateKey));
    }

    /**
     * verify sign by publickey file
     *
     * @param file      file
     * @param signByte  signByte
     * @param publicKey publicKey
     * @return result
     * @throws Exception
     */
    public static boolean verifyFileSign(File file, byte[] signByte, PublicKey publicKey) throws Exception {
        Signature sign = Signature.getInstance(ALGORITHM_SHA1_RSA);
        sign.initVerify(publicKey);
        try (InputStream in = new FileInputStream(file)) {
            byte[] buf = new byte[1024];
            int len = -1;
            while ((len = in.read(buf)) != -1) {
                sign.update(buf, 0, len);
            }
        }
        return sign.verify(signByte);
    }

    /**
     * verify sign by publickey file
     *
     * @param filePath  filepath
     * @param sign      sign
     * @param publicKey publicKey
     * @return result
     * @throws Exception
     */
    public static boolean verifyFileSign(String filePath, String sign, PublicKey publicKey) throws Exception {
        byte[] signByte = Base64.decodeBase64(sign);
        File file = new File(filePath);
        return verifyFileSign(file, signByte, publicKey);
    }


}