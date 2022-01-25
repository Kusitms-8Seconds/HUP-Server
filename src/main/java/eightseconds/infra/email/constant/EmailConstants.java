package eightseconds.infra.email.constant;

import lombok.Getter;

public class EmailConstants {

    @Getter
    public enum EEmailAuth{
        eEmailValidationTime(30),
        eExpiredAuthCodeTimeExceptionMessage("인증코드가 유효한 시간인 30분을 초과했습니다.");
        private String value;
        private int minutes;

        EEmailAuth(String value) {this.value = value;}
        EEmailAuth(int minutes) {this.minutes = minutes;}
    }

    @Getter
    public enum EEmailServiceImpl{
        eSendAuthCodeMessage("이메일로 인증코드를 보냈습니다."),
        eInvalidAuthCodeExceptionMessage("잘못된 인증코드 입니다."),
        eCompleteAuthMessage("인증이 완료되었습니다."),
        eSubject("HUP회원가입 이메일 인증"),
        eEmpty(""),
        eSendMessage1("<div style='margin:100px;'>"),
        eSendMessage2("<h1> 안녕하세요 HUP입니다. </h1>"),
        eSendMessage3("<br>"),
        eSendMessage4("<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>"),
        eSendMessage5("<br>"),
        eSendMessage6("<p>감사합니다!<p>"),
        eSendMessage7("<br>"),
        eSendMessage8("<div align='center' style='border:1px solid black; font-family:verdana';>"),
        eSendMessage9("<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>"),
        eSendMessage10("<div style='font-size:130%'>"),
        eSendMessage11("CODE : <strong>"),
        eSendMessage12("</strong><div><br/> "),
        eSendMessage13("</div>"),
        eSendMessageCharset("utf-8"),
        eSendMessageSubType("html"),
        eSendMessageAddress("hup.contact.auth@gmail.com"),
        eSendMessagePersonal("HUP"),
        eZero(0),
        eEight(8),
        eTwentySix(26),
        eNinetySeven(97),
        eThree(3),
        eOne(1),
        eTwo(2),
        eSixtyFive(65),
        eTen(10);


        private String value;
        private int size;

        EEmailServiceImpl(String value) {this.value = value;}
        EEmailServiceImpl(int size) {this.size = size;}
    }
}
