package exceptions;

public class ManagerValidateException extends RuntimeException {
    public ManagerValidateException(final String massage) {
        super(massage);
    }
}

