package org.projetoredes.util;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Encryptor {
    // Algoritmo AES
    // modo de operaçao ECB que funciona em blocos
    // preenchimento PKCS5Padding
    private static final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    // Chave AES de 256 bits
    private static final String KEY = "wi0yA3A6pBt0y6uS9LnwGxRqF2bsPNLBhz2qgVzxNlY=";


    private static SecretKey getKey(){
        byte[] decodedKey = Base64.getDecoder().decode(Encryptor.KEY);
        return new SecretKeySpec(decodedKey, "AES");
    }

    public static byte[] encrypt(String msg){
        try{
            Cipher cipher = Cipher.getInstance(Encryptor.TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, Encryptor.getKey());
            return cipher.doFinal(msg.getBytes(StandardCharsets.UTF_8));

        }catch (NoSuchAlgorithmException | NoSuchPaddingException e){
            throw new RuntimeException("Erro ao encriptar mensagem: ", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Erro com a KEY de criptografia: ", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Erro ao retornar msg: ", e);
        }
    }


    public static byte[] decrypt(byte[] msg){
        try{
            Cipher cipher = Cipher.getInstance(Encryptor.TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, Encryptor.getKey());
            return cipher.doFinal(msg);

        }catch (NoSuchAlgorithmException | NoSuchPaddingException e){
            throw new RuntimeException("Erro ao encriptar mensagem: ", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("Erro com a KEY de criptografia: ", e);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException("Erro ao retornar msg: ", e);
        }
    }
}
