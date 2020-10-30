package ar.com.fennoma.paymentezsdk.services;

public enum ErrorMessages {

    GENERIC_ERROR("generic_error"),

    //INTERNAL ERRORS
    INVALID_SESSION("invalid_session"),
    UNEXPECTED("unexpected");

    public static final String GENERIC_ERROR_IDENTIFIER = "generic_error";

    ErrorMessages(String errorCode) {
        this.errorCode = errorCode;
    }

    private String errorCode;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public static ErrorMessages getError(String errorCode) {
        if (errorCode == null) {
            return null;
        }
        for (ErrorMessages key : values()) {
            if (errorCode.equalsIgnoreCase(key.getErrorCode())) {
                return key;
            }
        }
        return UNEXPECTED;
    }

}
