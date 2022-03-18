//package eightseconds.hup.global.config;
//
//import org.assertj.core.api.Assertions;
//import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
//import org.junit.jupiter.api.Test;
//
//public class JasyptConfigTest {
//
//    @Test
//    void jasypt(){
//        String url = "39f529be53f9dc3b8d47fe40529c767ed613af3a";
//        String username = "-----BEGIN PRIVATE KEY-----\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCn6klNbduK3n5V\nqte9CElcV3WBr1lTE7eYW0w3VkiJG3i3z/8qtptEYFa8MJYghO7mmhGz6C91huDY\nb/JIQo7Bi0eo6Cjcfu+h97G9nwPWfvPaXWzwMlEsFwqP/PI631P/6zPA+PgQnXVA\nuq38UlR3h4sWR9Zv8Boc+w+0jifgoo0BLLh36cukQxw/mCSNlENryTANVbAGZhIU\n7Q4XXz84HWYXY7GdNZtONMb7dthks5ukc6Ix2jMbi89/55VQ6ad/R0Oq0YlTePEe\ncTaiydrDENtYyBo+7uXx18TUkOw1vHdTWsMto5IQ7VRZ/XXFfuVJofQ+NMKyR16D\nBpssZqHrAgMBAAECggEAMDjbZk4nX4E4HNgcbuAjWU6Qo3a9G1utPSbzA21XFXD7\nQSaNrDGW85MLNa5qXtpuVXFnphgHESR0DBRSKyhaIQEjykEqAvC9VvFwbPluu7nn\nxA1SjMSWhSf005cb5YZfdD6StVeKvhLKKpWyA6WNNmqJqhNlZO46QZpkT0EH+L2e\nBz7Rdm3s4+mJOrMDjUG+RxtpiFBBGStSJooKqZtXrV7dT1oTSn1mUpjq1QOur0D2\nlBjkR5KmQpDlIYUvPFWaGuvpxN4ZcrJjQBH+oRt+cszf8KX+yj3VMS1/FCLTPcgT\n2op4oeaO9bNWBzn5RRNjvc5lp/EU9uNMiJ14K+Z7GQKBgQDo3txYbx/tA9k39xE0\nqvbE7g387Lr/Lw7YmhkF5EocrgemUxiDEc01133qPvCu4rwiKNQy1Sgx2bmecUKe\n1coFUshleye8JGpTeR0Ns0pnqiKeC+NO3blinaRqArjWPhgm6nvkIaZneGodTq5y\na5LLfqgK9kPsCIksZmge4qd4xQKBgQC4l9J+gz2TdxJnITNumJ4EbizLIasI2lPR\n2XCgi3eYPWrtlqCJw6tOaJ6ats3m+2UfBC3CaJWUUTBjbscezaXnnhyEbkK8dNK2\nzY9DkQgPDUvyK5xmSafihyhECqkEssj52lGrGK1Faj+sChkaMCRfvpHIcQyPcb2a\n6kBgmnN67wKBgQCDC2bUHTTKHDBQ3ihAizTy/UPB4TYaBFFGcvFB74YFPanwXzKc\nD5h7hBjHiUPIJq/YSUGgmXNVGkDqeLIU0BovhLZg5T0nExcCglVnME3eqE9ywR3Z\nc8yvQicTwRRi++57d9jgOE98SrbsZyqkGQUynsPR52Oxn9wRsE9HkiTMQQKBgFTA\nkEq54ymzv+VN3xlmRRbvpu1U9fxIkbeFyCpOHojtmgyf3fGivyJu8nNCf2MQBaup\n9YCPGC5S4I6mrBG+he/ELn+3SDjSwnRgCOkSy8ptZnM4BsUICGb/e0NSSav4kkH1\nNQ4DdHqsf7Nzp3bYp7q1+qU3EndB17Yq24f/IgpNAoGAHI0gVxm/N0KbiUncw0JS\nY2b9z4t2cLK/0hUB0Pfo9c9hQ96UGFXu1FYbssZekJQzDZaYc/BjbomHuBiGa2Pu\nok2k65j+qsOQzlPpWq+VkHDMkEy9bG1iO15uvcLf4jw5J/39mWbPX+GawoQ9dfoa\n4QYvfMt7l6HEo+78Yogbemg=\n-----END PRIVATE KEY-----\n";
//        String password = "firebase-adminsdk-dl666@auctionapp-f3805.iam.gserviceaccount.com";
//
//        String encryptUrl = jasyptEncrypt(url);
//        String encryptUsername = jasyptEncrypt(username);
//        String encryptPassword = jasyptEncrypt(password);
//
//        System.out.println("encryptUrl : " + encryptUrl);
//        System.out.println("encryptUsername : " + encryptUsername);
//        System.out.println("encryptPassword : " + encryptPassword);
//
//        Assertions.assertThat(url).isEqualTo(jasyptDecryt(encryptUrl));
//    }
//
//    private String jasyptEncrypt(String input) {
//        String key = "tkfkd1617!";
//        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//        encryptor.setAlgorithm("PBEWithMD5AndDES");
//        encryptor.setPassword(key);
//        return encryptor.encrypt(input);
//    }
//
//    private String jasyptDecryt(String input){
//        String key = "tkfkd1617!";
//        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
//        encryptor.setAlgorithm("PBEWithMD5AndDES");
//        encryptor.setPassword(key);
//        return encryptor.decrypt(input);
//    }
//}