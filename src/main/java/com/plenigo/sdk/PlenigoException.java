package com.plenigo.sdk;

import com.plenigo.sdk.internal.ErrorCode;
import com.plenigo.sdk.models.ErrorDetail;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 * This represents any exception that can come from
 * the plenigo API or SDK.
 * </p>
 * <p>
 * <strong>Thread safety:</strong> This class is thread safe and can be injected.
 * </p>
 */
public class PlenigoException extends Exception {

    /**
     * The response code that came from the server.
     */
    private String responseCode;


    /**
     * List of errors.
     */
    private List<ErrorDetail> errorDetails;

    /**
     * This constructor is used when an error code
     * has been obtained from the server.
     *
     * @param rspCode The response code
     * @param msg     The response message
     */
    public PlenigoException(final String rspCode, final String msg) {
        super(msg);
        this.responseCode = rspCode;
        this.errorDetails = new LinkedList<ErrorDetail>();
    }

    /**
     * This constructor is used when an error code
     * has been obtained from the server.
     *
     * @param rspCode      The response code
     * @param msg          The response message
     * @param errorDetails The error details
     */
    public PlenigoException(String rspCode, String msg, List<ErrorDetail> errorDetails) {
        super(msg);
        this.responseCode = rspCode;
        this.errorDetails = errorDetails;
    }


    /**
     * The resulting Response Code of a plenigo API Request.
     *
     * @return Response Code
     */
    public String getResponseCode() {
        return responseCode;
    }

    /**
     * This constructor is used when there is no error code
     * but there was an error during server communication or a process.
     *
     * @param message the message to send
     * @param e       the thrown exception
     */
    public PlenigoException(final String message, final Throwable e) {
        super(message, e);
    }


    /**
     * This constructor is used when there is no error code
     * but there was an error during server communication or a process.
     *
     * @param message   the message to send
     * @param e         the thrown exception
     * @param errorCode the errorCode for this exception
     */
    public PlenigoException(final ErrorCode errorCode, final String message, final Throwable e) {
        this(message, e);
        this.responseCode = errorCode.getCode();
    }


    /**
     * The list of additional detailed errors that came as a response from the server.
     *
     * @return List of errors.
     */
    public List<ErrorDetail> getErrors() {
        return errorDetails;
    }

}
