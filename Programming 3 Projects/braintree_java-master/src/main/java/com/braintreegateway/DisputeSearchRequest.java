package com.braintreegateway;

public class DisputeSearchRequest extends SearchRequest {

    public RangeNode<DisputeSearchRequest> amountDisputed() {
        return new RangeNode<DisputeSearchRequest>("amount_disputed", this);
    }

    public RangeNode<DisputeSearchRequest> amountWon() {
        return new RangeNode<DisputeSearchRequest>("amount_won", this);
    }

    public TextNode<DisputeSearchRequest> caseNumber() {
        return new TextNode<DisputeSearchRequest>("case_number", this);
    }

    public TextNode<DisputeSearchRequest> id() {
        return new TextNode<DisputeSearchRequest>("id", this);
    }

    public MultipleValueNode<DisputeSearchRequest, Dispute.Kind> kind() {
        return new MultipleValueNode<DisputeSearchRequest, Dispute.Kind>("kind", this);
    }

    public MultipleValueNode<DisputeSearchRequest, String> merchantAccountId() {
        return new MultipleValueNode<DisputeSearchRequest, String>("merchant_account_id", this);
    }

    public MultipleValueNode<DisputeSearchRequest, Dispute.Reason> reason() {
        return new MultipleValueNode<DisputeSearchRequest, Dispute.Reason>("reason", this);
    }

    public MultipleValueNode<DisputeSearchRequest, String> reasonCode() {
        return new MultipleValueNode<DisputeSearchRequest, String>("reason_code", this);
    }

    public DateRangeNode<DisputeSearchRequest> receivedDate() {
        return new DateRangeNode<DisputeSearchRequest>("received_date", this);
    }

    public TextNode<DisputeSearchRequest> referenceNumber() {
        return new TextNode<DisputeSearchRequest>("reference_number", this);
    }

    public DateRangeNode<DisputeSearchRequest> replyByDate() {
        return new DateRangeNode<DisputeSearchRequest>("reply_by_date", this);
    }

    public MultipleValueNode<DisputeSearchRequest, Dispute.Status> status() {
        return new MultipleValueNode<DisputeSearchRequest, Dispute.Status>("status", this);
    }

    public TextNode<DisputeSearchRequest> transactionId() {
        return new TextNode<DisputeSearchRequest>("transaction_id", this);
    }

    public MultipleValueNode<DisputeSearchRequest, String> transactionSource() {
        return new MultipleValueNode<DisputeSearchRequest, String>("transaction_source", this);
    }
}
