package eightseconds.domain.user.exception;

import net.bytebuddy.implementation.bind.annotation.Super;

public class AlreadyRegisteredUserException extends RuntimeException{
    public AlreadyRegisteredUserException(String s) {
        super(s);
    }
}
