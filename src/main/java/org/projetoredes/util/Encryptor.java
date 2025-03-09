package org.projetoredes.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryptor {
    public static SecretKey genKey(){
        try{
            KeyGenerator keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(256);
            return keyGen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static byte[] prepareKey(SecretKey key){
        byte[] encoded = Base64.getEncoder().encode(key.getEncoded());
        return Base64.getDecoder().decode(encoded);
    }

    public static SecretKey resolveKey(byte[] key){
        return new SecretKeySpec(key, "AES");
    }

    public static byte[] encrypt(String msg, SecretKey key){
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher.doFinal(msg.getBytes());
        }catch (NoSuchAlgorithmException | NoSuchPaddingException e){
            throw new RuntimeException("Erro ao encriptar mensagem: ", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Erro com a KEY de criptografia: ", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Erro ao retornar msg: ", e);
        }
    }


    public static String decrypt(byte[] msg, SecretKey key){
        try{
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(msg));
        }catch (NoSuchAlgorithmException | NoSuchPaddingException e){
            throw new RuntimeException("Erro ao encriptar mensagem: ", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Erro com a KEY de criptografia: ", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Erro ao retornar msg: ", e);
        }
    }
}
