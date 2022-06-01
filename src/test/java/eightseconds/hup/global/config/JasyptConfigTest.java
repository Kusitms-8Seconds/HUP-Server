package eightseconds.hup.global.config;

import org.assertj.core.api.Assertions;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

public class JasyptConfigTest {

    @Test
    void jasypt(){
//        String region = "ap-northeast-2";
//        String bucket = "hup-bucket";
//        String url = "AKIAVKTYRMM64SB6SDUI";
//        String password = "bDKw3H2JurnakNqzpXzyDI3qAdIbR6EMH1TwL4KW";
//
//        String encryptRegion = jasyptEncrypt(region);
//        String encryptBucket = jasyptEncrypt(bucket);
//        String encryptUrl = jasyptEncrypt(url);
//        String encryptPassword = jasyptEncrypt(password);
//
//        System.out.println("region : " + encryptRegion);
//        System.out.println("hup-bucket : " + encryptBucket);
//        System.out.println("encryptUrl : " + encryptUrl);
//        System.out.println("encryptPassword : " + encryptPassword);
//
//        Assertions.assertThat(region).isEqualTo(jasyptDecryt(encryptRegion));
//        Assertions.assertThat(bucket).isEqualTo(jasyptDecryt(encryptBucket));
//        Assertions.assertThat(url).isEqualTo(jasyptDecryt(encryptUrl));
//        Assertions.assertThat(password).isEqualTo(jasyptDecryt(encryptPassword));
        System.out.println("체크"+jasyptDecryt("hUhy2YDKe6zfK2L0wQ+WjUX3WI2L66FnAArFJFuJrhTFfxPcdxFAzQ=="));

    }

    private String jasyptEncrypt(String input) {
        String key = "tkfkd1617!";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.encrypt(input);
    }

    private String jasyptDecryt(String input){
        String key = "tkfkd1617!";
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setAlgorithm("PBEWithMD5AndDES");
        encryptor.setPassword(key);
        return encryptor.decrypt(input);
    }
}
