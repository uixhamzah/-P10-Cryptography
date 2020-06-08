package crypto;

import java.io.*;
import java.security.*;
import javax.crypto.*;

/**
 *
 * @author Hmzh
 */
public class AsymetricRSA {
    public static void generateKey(String privateKeyFile, String publicKeyFile) {
        ObjectOutputStream oosPublic, oosPrivate;
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(1024);
            KeyPair key = keyGen.generateKeyPair();

            File publicKey = new File(publicKeyFile);
            File privateKey = new File(privateKeyFile);
            publicKey.createNewFile();
            privateKey.createNewFile();

            FileOutputStream fPublic = new FileOutputStream(publicKeyFile);
            oosPublic = new ObjectOutputStream(fPublic);
            oosPublic.writeObject(key.getPublic());
            oosPublic.close();

            FileOutputStream fPrivate = new FileOutputStream(publicKeyFile);
            oosPrivate = new ObjectOutputStream(fPrivate);
            oosPrivate.writeObject(key.getPrivate());
            oosPrivate.close();
        } catch (IOException | NoSuchAlgorithmException e) {
        }
    }

    public static PublicKey getPublicKey(String keyFile) {
        PublicKey pbKey = null;
        try {
            FileInputStream fis = new FileInputStream(keyFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            pbKey = (PublicKey) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return pbKey;
    }

    public static PrivateKey getPrivateKey(String keyFile) {
        PrivateKey pvKey = null;
        try {
            FileInputStream fis = new FileInputStream(keyFile);
            ObjectInputStream ois = new ObjectInputStream(fis);
            pvKey = (PrivateKey) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
        }
        return pvKey;
    }

    public static byte[] encrypt(String message, PublicKey key) {
        byte[] cipherText = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipherText = cipher.doFinal(message.getBytes());
        } catch (InvalidKeyException
                | NoSuchAlgorithmException | BadPaddingException
                | IllegalBlockSizeException | NoSuchPaddingException e) {
        }
        return cipherText;
    }

    public static String decrypt(byte[] encrypted, PrivateKey key) {
        byte[] decrypted = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, key);
            decrypted = cipher.doFinal(encrypted);
        } catch (InvalidKeyException
                | NoSuchAlgorithmException | BadPaddingException
                | IllegalBlockSizeException | NoSuchPaddingException e) {
        }
        return new String(decrypted);
    }

    public static void main(String[] args) {
        String path = System.getProperty("user.dir") + File.separator;
        String privateFile = path + "PRIVATE.cer";
        String publicFile = path + "PUBLIC.cer";
        generateKey(privateFile, publicFile);

        String message = "Asymetric Cryptography dengan algoritma RSA";
        PublicKey pubKey = getPublicKey(publicFile);
        byte[] encrypted = encrypt(message, pubKey);

        PrivateKey privateKey = getPrivateKey(privateFile);
        String plainText = decrypt(encrypted, privateKey);

        System.out.println("Message : " + message);
        System.out.println("Encrypted : " + new String(encrypted));
        System.out.println("Encrypted Hex : " + MyStringUtils.getHexString(encrypted));
        System.out.println("Decrypted : " + plainText);
    }

}
