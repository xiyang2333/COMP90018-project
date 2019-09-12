package com.unimelb.studypartner.web.entity;

/**
 * Created by xiyang on 2019/9/10
 */
public class RequestHeader {

    private String referenceNo;

    private String signature;

    public String getReferenceNo() {
        return referenceNo;
    }

    public void setReferenceNo(String referenceNo) {
        this.referenceNo = referenceNo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
