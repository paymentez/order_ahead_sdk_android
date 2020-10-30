package ar.com.fennoma.paymentezsdk.services;

import ar.com.fennoma.paymentezsdk.models.PmzErrorMessage;

public class ServiceException extends Exception {

    String errorCode;
    String errorMessage;
    String additionalData;

    public ServiceException() {
        super();
        errorCode = PmzErrorMessage.GENERIC_ERROR_IDENTIFIER;
    }

    public ServiceException(String errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public ServiceException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ServiceException(String errorCode, String errorMessage, String additionalData) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.additionalData = additionalData;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
