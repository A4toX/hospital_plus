package com.hospital.flow.exception;

/**
 * @author lgx
 */
public final class FlowException extends RuntimeException {

    private String message;

    public FlowException() {
    }

    public FlowException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
