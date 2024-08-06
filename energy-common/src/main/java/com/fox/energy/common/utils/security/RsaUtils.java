package com.fox.energy.common.utils.security;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RsaUtils {
    private static String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjV+uSsM9XcALPMnxjS1v3rhPLfkehaEx2q1eoX5x8YDizqjAO3bLmmzXIhmQIgu1saBKBN9HPQ3q+izpN7b0MGMPeYLiXVWXpe6z40L1RTSt7Y96nMBqB2RwD6ezimc0d9RWvmB7KpeBzLYS1u5ov8kDGqjWvY+zV6EcPB1lC+oMvhUezAnzBSAtyZqnByHznXIFuL9TkJuPoGOj7wykNk7j4enrnSrOhCv26Wyte7NVvNS3Gqetf4OkPCub+28g9reEi+6DPBOVDja2e8DPcIGQxqLRkA7AaSJPB/B/WzXoMq+MOQ5NJBseRvUg5owgWCcTH3NxLLN/kfu370L7jwIDAQAB";

    public static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCNX65Kwz1dwAs8yfGNLW/euE8t+R6FoTHarV6hfnHxgOLOqMA7dsuabNciGZAiC7WxoEoE30c9Der6LOk3tvQwYw95guJdVZel7rPjQvVFNK3tj3qcwGoHZHAPp7OKZzR31Fa+YHsql4HMthLW7mi/yQMaqNa9j7NXoRw8HWUL6gy+FR7MCfMFIC3JmqcHIfOdcgW4v1OQm4+gY6PvDKQ2TuPh6eudKs6EK/bpbK17s1W81Lcap61/g6Q8K5v7byD2t4SL7oM8E5UONrZ7wM9wgZDGotGQDsBpIk8H8H9bNegyr4w5Dk0kGx5G9SDmjCBYJxMfc3Ess3+R+7fvQvuPAgMBAAECggEAJnzwBGaW5FkONiR8YIUIOchPtiJmTtcNrY0ZgDneZ3NBTCXg/89dqtMK1rvC46Hwe0U6IY1v5Y1OdYGYlJR4clvlsJ0TZ3mP6ZrM2og2IfsvhsdTCC3SL+UGJRzw4jMqs5zZaMb3gSbyLXs8McuCNQRSlgV1QPJITwkLmMof4lgSoYi4yroh1+68zVCEtLko9WyeaZu8dylXhm6ufrL8NkmavCgT3gjyx6YvtZHROyC/t1wLOTS7atWx+E2YdeB8jK+xLj86WZc20TFPJZSDKXYmT3EowiyOPjlMLsn4av2SrJ6u/MEyaAFLa7jDMAf9li8eu5/F/YoOpLk+dRNh4QKBgQDi5MugOSiO7ptRHPjHQX0x3C+nDCSH+vBRCrv1OzKQR93aecIs11geWMmKdsUlpDqQ0dqwVYF9xEB3DdMdika9+vbCaKkT8WZiTIjLwC8q4Etaua2Bm+EVL3iyhMSi+Tpqxm5lwTBXA0bRSeAYLoAhnHdX5eHKHGiyau/hkW0W9wKBgQCfgmdkLlgKyLcDPdEbpLsWUIvOW2uswHyTCog/I9xyUveqT4Wq/9XVQ+ftnRMLV+q10nHangIMf6Tnl6rewSZJI89AfRW7nLnC683D1h4v07clpGV7wQXNvFoskRY8Nvd6LWsyLzSM4axHEATkx5yv/OcwLjKsWodnyS6hC06iKQKBgQC9gqJ9giBZOfyqhEtail2aTrHSkfbVSQh+eDvDuZ0jG5O3DqnbsEYemsRDja5fIUv1eAJz9AdDV68MgZSLANrThZGjka1fRerhQdGkdTIwWsU10w6TyfH3Mv5aBUEEaqybUIAXJ4RLuSXmWxD8waIt8LVRymWL3aypEV6pBkGawwKBgBcT/PWHpwFYbPIxHXIMcW0XFkMzAu8polx7556LB7gavO6NNt+4yC1k98dvNHQtsi3AVuy0c8NvLGK9lOFJvMpUPgYsktpkIJRNHaY58oAk95igonY3RSmxGLLeAwOPSFsK+zl1jvrbI9vcO3kGUGs+Agz8giRhrLmdZ/uw8R2RAoGBANxXqFQpRIQ0pk5Oh2afENtS5qdNiMXMbK+GbvLRs7l4fkrIt/vJjTOorzf8nFxdlkcSLwNoGTy5r7nlkNhoyKxOtuTWMFFD5OKkzKRbsHNONns4NbXJCK4cbtynKxbCAmAC5ATV3jPOFOT8AG+weJ5aVYqJfC4aMCbY52N2k50J";

    public static void main(String[] args) throws Exception {
        String a = "DECRYPT:Aa111111";
        String s = encryptByPublicKey(publicKey, a);
        System.out.println(s);
        String aa = decryptByPrivateKey(s);
        System.out.println(aa);
    }

    public static String decryptByPrivateKey(String text) throws Exception {
        String design = decryptByPrivateKey(privateKey, text);
        if (design.contains("DECRYPT:")) {
            design = design.substring(design.indexOf("DECRYPT:") + 8);
        }
        return design;
    }

    public static String decryptByPublicKey(String publicKeyString, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, publicKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    public static String encryptByPrivateKey(String privateKeyString, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, privateKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    public static String decryptByPrivateKey(String privateKeyString, String text) throws Exception {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec5 = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec5);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(2, privateKey);
        byte[] result = cipher.doFinal(Base64.decodeBase64(text));
        return new String(result);
    }

    public static String encryptByPublicKey(String publicKeyString, String text) throws Exception {
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec2);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(1, publicKey);
        byte[] result = cipher.doFinal(text.getBytes());
        return Base64.encodeBase64String(result);
    }

    public static RsaKeyPair generateKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(1024);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey rsaPublicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey rsaPrivateKey = (RSAPrivateKey) keyPair.getPrivate();
        String publicKeyString = Base64.encodeBase64String(rsaPublicKey.getEncoded());
        String privateKeyString = Base64.encodeBase64String(rsaPrivateKey.getEncoded());
        return new RsaKeyPair(publicKeyString, privateKeyString);
    }


    public static class RsaKeyPair {
        private final String publicKey;

        private final String privateKey;

        public RsaKeyPair(String publicKey, String privateKey) {
            this.publicKey = publicKey;
            this.privateKey = privateKey;
        }

        public String getPublicKey() {
            return this.publicKey;
        }

        public String getPrivateKey() {
            return this.privateKey;
        }
    }
}
