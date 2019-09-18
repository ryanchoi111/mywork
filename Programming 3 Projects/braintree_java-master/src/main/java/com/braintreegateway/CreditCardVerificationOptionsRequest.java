package com.braintreegateway;

public class CreditCardVerificationOptionsRequest extends Request {
    private CreditCardVerificationRequest parent;
    private String merchantAccountId;
    private String amount;

    public CreditCardVerificationOptionsRequest(CreditCardVerificationRequest parent) {
        this.parent = parent;
    }

    public CreditCardVerificationOptionsRequest merchantAccountId(String merchantAccountId) {
        this.merchantAccountId = merchantAccountId;
        return this;
    }

    public CreditCardVerificationOptionsRequest amount(String amount) {
        this.amount = amount;
        return this;
    }

    public CreditCardVerificationRequest done() {
        return parent;
    }

    @Override
    public String toXML() {
        return buildRequest("options").toXML();
    }

    protected RequestBuilder buildRequest(String root) {
        RequestBuilder builder = new RequestBuilder(root).
            addElement("merchantAccountId", merchantAccountId).
            addElement("amount", amount);
        return builder;
    }
}
