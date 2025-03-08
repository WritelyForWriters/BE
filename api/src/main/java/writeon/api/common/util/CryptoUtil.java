package writeon.api.common.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

@Component
public class CryptoUtil {
    /**
     * Encrypt / Decrypt
     */
    private static final String CRYPT_ALGO = "AES";
    private static final String CRYPT_MODE = "CBC";
    private static final String CRYPT_PADDING = "PKCS5Padding";

    @Value("${crypto.secret}")
    private String SECRET;
    @Value("${crypto.iv}")
    private String IV;

    public String encrypt(String plainText) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET.getBytes(), CRYPT_ALGO);
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());

        try {
            Cipher cipher = Cipher.getInstance(CRYPT_ALGO + "/" + CRYPT_MODE + "/" + CRYPT_PADDING);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivSpec);

            byte[] cipherText = cipher.doFinal(plainText.getBytes());

            return encodeBase64(cipherText);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    public String decrypt(String cipherText) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(SECRET.getBytes(), CRYPT_ALGO);
        IvParameterSpec ivSpec = new IvParameterSpec(IV.getBytes());

        try {
            Cipher cipher = Cipher.getInstance(CRYPT_ALGO + "/" + CRYPT_MODE + "/" + CRYPT_PADDING);
            cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivSpec);

            byte[] decoded = decodeBase64(cipherText);
            byte[] plainText = cipher.doFinal(decoded);

            return new String(plainText);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    /**
     * Hashing
     */
    private static final String HASH_ALGO = "SHA-256";
    private static final Integer HASH_ROUND = 3;

    public String hash(String plainText) {
        return hash(plainText + SECRET, HASH_ROUND);
    }

    private String hash(String hashText, Integer rounds) {
        if (rounds == 0) return hashText;

        try {
            MessageDigest md = MessageDigest.getInstance(HASH_ALGO);
            md.update((hashText).getBytes());

            return hash(bytesToHex(md.digest()), --rounds);
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }

    /**
     * encode / decode
     */
    public String encodeBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public byte[] decodeBase64(String encodedStr) {
        return Base64.getDecoder().decode(encodedStr.getBytes());
    }
}