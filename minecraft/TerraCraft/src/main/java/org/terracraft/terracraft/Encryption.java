package org.terracraft.terracraft;

import com.google.protobuf.ByteString;
import org.terracraft.grpc.Chat;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class Encryption {
    private final SecretKeySpec secretKey;
    private final Cipher cipher;

    public Encryption(String token) {
        if (token.length() != 32) {
            throw new IllegalArgumentException("The token must be 32 characters long");
        }
        this.secretKey = new SecretKeySpec(token.getBytes(StandardCharsets.UTF_8), "AES");
        try {
            this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new RuntimeException("Cipher initialization failed", e);
        }

    }

    public Chat.EncryptedData Encrypt(String plainText) throws GeneralSecurityException {
        byte[] iv = generateIV();
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
        byte[] cipherText = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));

        return Chat.EncryptedData.newBuilder()
                .setIv(ByteString.copyFrom(iv))
                .setData(ByteString.copyFrom(cipherText))
                .build();
    }

    public String Decrypt(Chat.EncryptedData data) throws GeneralSecurityException {
        cipher.init(Cipher.DECRYPT_MODE, this.secretKey, new IvParameterSpec(data.getIv().toByteArray()));
        return new String(cipher.doFinal(data.getData().toByteArray()), StandardCharsets.UTF_8);
    }

    private byte[] generateIV() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return iv;
    }
}
