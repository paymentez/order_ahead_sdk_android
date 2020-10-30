package ar.com.fennoma.paymentezsdk.exceptions;

import ar.com.fennoma.paymentezsdk.models.PmzErrorMessage;

public class PmzException extends Exception {

    private String errorCode;
    private String errorMessage;

    public PmzException() {
        errorCode = PmzErrorMessage.ErrorCode.UNEXPECTED.getErrorCode();
    }

    public PmzException(String errorCode) {
        this.errorCode = errorCode;
    }

    public PmzException(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
