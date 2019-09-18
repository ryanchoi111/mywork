package com.braintreegateway;

import java.math.BigDecimal;

public class UpdateModificationRequest extends ModificationRequest {

    private String existingId;

    public UpdateModificationRequest(ModificationsRequest parent) {
        super(parent);
    }

    public UpdateModificationRequest(ModificationsRequest parent, String existingId) {
        super(parent);
        this.existingId = existingId;
    }

    public UpdateModificationRequest existingId(String existingId) {
        this.existingId = existingId;
        return this;
    }

    @Override
    public UpdateModificationRequest amount(BigDecimal amount) {
        super.amount(amount);
        return this;
    }

    @Override
    public UpdateModificationRequest neverExpires(Boolean neverExpires) {
        super.neverExpires(neverExpires);
        return this;
    }

    @Override
    public UpdateModificationRequest numberOfBillingCycles(Integer numberOfBillingCycles) {
        super.numberOfBillingCycles(numberOfBillingCycles);
        return this;
    }

    @Override
    public UpdateModificationRequest quantity(Integer quantity) {
        super.quantity(quantity);
        return this;
    }

    @Override
    protected RequestBuilder buildRequest(String root) {
        return super.buildRequest(root).addElement("existingId", existingId);
    }
}
