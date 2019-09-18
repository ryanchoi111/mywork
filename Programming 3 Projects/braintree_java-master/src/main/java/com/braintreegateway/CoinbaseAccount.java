package com.braintreegateway;

import com.braintreegateway.util.NodeWrapper;
import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;

public class CoinbaseAccount implements PaymentMethod {
    private String userId;
    private String userEmail;
    private String userName;
    private String token;
    private boolean isDefault;
    private String imageUrl;
    private String customerId;
    private Calendar createdAt;
    private Calendar updatedAt;
    private List<Subscription> subscriptions;

    public CoinbaseAccount(NodeWrapper node) {
        this.userId = node.findString("user-id");
        this.userEmail = node.findString("user-email");
        this.userName = node.findString("user-name");
        this.token = node.findString("token");
        this.isDefault = node.findBoolean("default");
        this.imageUrl = node.findString("image-url");
        this.customerId = node.findString("customer-id");
        this.createdAt = node.findDateTime("created-at");
        this.updatedAt = node.findDateTime("updated-at");
        this.subscriptions = new ArrayList<Subscription>();
        for (NodeWrapper subscriptionResponse : node.findAll("subscriptions/subscription")) {
            this.subscriptions.add(new Subscription(subscriptionResponse));
        }
    }

    public String getUserId() {
        return userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public String getToken() {
        return token;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCustomerId() {
        return customerId;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public Calendar getUpdatedAt() {
        return updatedAt;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }
}
