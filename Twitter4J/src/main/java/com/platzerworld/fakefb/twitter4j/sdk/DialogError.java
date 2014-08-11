package com.platzerworld.fakefb.twitter4j.sdk;

/**
 * Created by platzerworld on 11.08.14.
 */
public class DialogError extends Throwable {

    private static final long serialVersionUID = -992704825747001028L;

    private int mErrorCode;
    private String mFailingUrl;

    public DialogError(String message, int errorCode, String failingUrl) {
        super(message);
        mErrorCode = errorCode;
        mFailingUrl = failingUrl;
    }

    public int getErrorCode() {
        return mErrorCode;
    }

    public String getFailingUrl() {
        return mFailingUrl;
    }

}