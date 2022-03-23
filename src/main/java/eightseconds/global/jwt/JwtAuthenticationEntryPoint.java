package eightseconds.global.jwt;

import com.nimbusds.jose.shaded.json.JSONObject;
import eightseconds.global.constant.GlobalConstants.ExceptionCode;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String exception = (String)request.getAttribute("exception");
        if(exception == null) {
            setResponse(response, ExceptionCode.UNKNOWN_ERROR);
        }
        //잘못된 타입의 토큰인 경우
        else if(exception.equals(ExceptionCode.WRONG_TYPE_TOKEN.getCode())) {
            setResponse(response, ExceptionCode.WRONG_TYPE_TOKEN);
        }
        //토큰 만료된 경우
        else if(exception.equals(ExceptionCode.EXPIRED_TOKEN.getCode())) {
            setResponse(response, ExceptionCode.EXPIRED_TOKEN);
        }
        //지원되지 않는 토큰인 경우
        else if(exception.equals(ExceptionCode.UNSUPPORTED_TOKEN.getCode())) {
            setResponse(response, ExceptionCode.UNSUPPORTED_TOKEN);
        }
        else {
            setResponse(response, ExceptionCode.ACCESS_DENIED);
        }
    }
    //한글 출력을 위해 getWriter() 사용
    private void setResponse(HttpServletResponse response, ExceptionCode exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("messages", Arrays.asList(exceptionCode.getMessage()));
        responseJson.put("code", exceptionCode.getCode());

        response.getWriter().print(responseJson);
    }
}