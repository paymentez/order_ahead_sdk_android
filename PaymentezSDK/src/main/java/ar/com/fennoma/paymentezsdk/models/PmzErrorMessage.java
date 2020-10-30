package ar.com.fennoma.paymentezsdk.models;

import android.content.Context;

import ar.com.fennoma.paymentezsdk.R;

public class PmzErrorMessage {

    public enum ErrorCode {
        INVALID_SESSION("invalid_session"),
        INVALID_DATA("Los datos ingresados son incorrectos."),
        INVALID_LOGIN("El usuario o la clave son incorrectos."),
        SINISTER_CREATE_INVALID_ZIPCODE("Debe indicar un código postal valido."),
        UNEXPECTED("unexpected");

        private String errorCode;

        ErrorCode(String errorCode) {
            this.errorCode = errorCode;
        }

        public String getErrorCode() {
            return errorCode;
        }

        public static ErrorCode getError(String errorCode) {
            if (errorCode == null) {
                return null;
            }
            for (ErrorCode key : values()) {
                if (errorCode.equalsIgnoreCase(key.getErrorCode())) {
                    return key;
                }
            }
            return UNEXPECTED;
        }

        public boolean equals(String errorCode) {
            return this.errorCode.equals(errorCode);
        }
    }

    public static final String GENERIC_ERROR_IDENTIFIER = "generic error";

    private ErrorCode code;
    private String errorMessage;

    public ErrorCode getErrorCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static PmzErrorMessage getError(String errorCode) {
        PmzErrorMessage errorMessage = new PmzErrorMessage();
        errorMessage.seterrorCode(ErrorCode.getError(errorCode));
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isInvalidSession() {
        return code != null && code.equals(ErrorCode.INVALID_SESSION.errorCode);
    }

    public String getErrorMessage(Context context) {
        switch (code) {
            case UNEXPECTED:
                return context.getString(R.string.generic_error);
        }
        return context.getString(R.string.generic_error);
    }

    public void seterrorCode(ErrorCode code) {
        this.code = code;
    }
}
