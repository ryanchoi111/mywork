package com.braintreegateway;

import com.braintreegateway.util.NodeWrapper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Comparator;
import java.util.Collections;

import static com.braintreegateway.util.EnumUtils.findByToString;

public class CreditCard implements PaymentMethod {
    public static final String VALUE_YES = "Yes";
    public static final String VALUE_NO = "No";
    public static final String VALUE_UNKNOWN = "Unknown";

    public enum CardType {
        AMEX("American Express"),
        CARTE_BLANCHE("Carte Blanche"),
        CHINA_UNION_PAY("China UnionPay"),
        DINERS_CLUB_INTERNATIONAL("Diners Club"),
        DISCOVER("Discover"),
        JCB("JCB"),
        LASER("Laser"),
        UK_MAESTRO("UK Maestro"),
        MAESTRO("Maestro"),
        MASTER_CARD("MasterCard"),
        SOLO("Solo"),
        SWITCH("Switch"),
        VISA("Visa");

        private final String name;

        CardType(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum CustomerLocation {
        INTERNATIONAL("international"),
        US("us");

        private final String name;

        CustomerLocation(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    public enum Commercial {
        YES(VALUE_YES),
        NO(VALUE_NO),
        UNKNOWN(VALUE_UNKNOWN);

        private final String value;

        Commercial(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Debit {
        YES(VALUE_YES),
        NO(VALUE_NO),
        UNKNOWN(VALUE_UNKNOWN);

        private final String value;

        Debit(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum DurbinRegulated {
        YES(VALUE_YES),
        NO(VALUE_NO),
        UNKNOWN(VALUE_UNKNOWN);

        private final String value;

        DurbinRegulated(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Healthcare {
        YES(VALUE_YES),
        NO(VALUE_NO),
        UNKNOWN(VALUE_UNKNOWN);

        private final String value;

        Healthcare(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Payroll {
        YES(VALUE_YES),
        NO(VALUE_NO),
        UNKNOWN(VALUE_UNKNOWN);

        private final String value;

        Payroll(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    public enum Prepaid {
        YES(VALUE_YES),
        NO(VALUE_NO),
        UNKNOWN(VALUE_UNKNOWN);

        private final String value;

        Prepaid(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    private Address billingAddress;
    private String bin;
    private String cardholderName;
    private String cardType;
    private Calendar createdAt;
    private String customerId;
    private String customerLocation;
    private String expirationMonth;
    private String expirationYear;
    private boolean isDefault;
    private boolean isVenmoSdk;
    private boolean isExpired;
    private String imageUrl;
    private String last4;
    private String commercial;
    private String debit;
    private String durbinRegulated;
    private String healthcare;
    private String payroll;
    private String prepaid;
    private String productId;
    private String countryOfIssuance;
    private String issuingBank;
    private String uniqueNumberIdentifier;
    private List<Subscription> subscriptions;
    private String token;
    private Calendar updatedAt;
    private CreditCardVerification verification;

    public CreditCard(NodeWrapper node) {
        token = node.findString("token");
        createdAt = node.findDateTime("created-at");
        updatedAt = node.findDateTime("updated-at");
        bin = node.findString("bin");
        cardType = node.findString("card-type");
        cardholderName = node.findString("cardholder-name");
        customerId = node.findString("customer-id");
        customerLocation = node.findString("customer-location");
        expirationMonth = node.findString("expiration-month");
        expirationYear = node.findString("expiration-year");
        imageUrl = node.findString("image-url");
        isDefault = node.findBoolean("default");
        isVenmoSdk = node.findBoolean("venmo-sdk");
        isExpired = node.findBoolean("expired");
        last4 = node.findString("last-4");
        commercial = node.findString("commercial");
        debit = node.findString("debit");
        durbinRegulated = node.findString("durbin-regulated");
        healthcare = node.findString("healthcare");
        payroll = node.findString("payroll");
        prepaid = node.findString("prepaid");
        productId = node.findString("product-id");
        countryOfIssuance = node.findString("country-of-issuance");
        issuingBank = node.findString("issuing-bank");
        uniqueNumberIdentifier = node.findString("unique-number-identifier");
        NodeWrapper billingAddressResponse = node.findFirst("billing-address");
        if (billingAddressResponse != null) {
            billingAddress = new Address(billingAddressResponse);
        }
        subscriptions = new ArrayList<Subscription>();
        for (NodeWrapper subscriptionResponse : node.findAll("subscriptions/subscription")) {
            subscriptions.add(new Subscription(subscriptionResponse));
        }

        final List<NodeWrapper> verificationNodes = node.findAll("verifications/verification");
        verification = findNewestVerification(verificationNodes);
    }

    private CreditCardVerification findNewestVerification(List<NodeWrapper> verificationNodes) {
        if(verificationNodes.size() > 0) {
            Collections.sort(verificationNodes, new Comparator<NodeWrapper>() {
                public int compare(NodeWrapper node1, NodeWrapper node2) {
                    Calendar createdAt1 = node1.findDateTime("created-at");
                    Calendar createdAt2 = node2.findDateTime("created-at");

                    return createdAt2.compareTo(createdAt1);
                }
            });

            return new CreditCardVerification(verificationNodes.get(0));
        }

        return null;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public String getBin() {
        return bin;
    }

    public String getCardholderName() {
        return cardholderName;
    }

    public String getCardType() {
        return cardType;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getCustomerLocation() {
        return customerLocation;
    }

    public String getExpirationDate() {
        return expirationMonth + "/" + expirationYear;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getLast4() {
        return last4;
    }

    public String getMaskedNumber() {
        return getBin() + "******" + getLast4();
    }

    public Commercial getCommercial() {
        return findByToString(Commercial.values(), commercial, Commercial.UNKNOWN);
    }

    public Debit getDebit() {
        return findByToString(Debit.values(), debit, Debit.UNKNOWN);
    }

    public DurbinRegulated getDurbinRegulated() {
        return findByToString(DurbinRegulated.values(), durbinRegulated, DurbinRegulated.UNKNOWN);
    }

    public Healthcare getHealthcare() {
        return findByToString(Healthcare.values(), healthcare, Healthcare.UNKNOWN);
    }

    public Payroll getPayroll() {
        return findByToString(Payroll.values(), payroll, Payroll.UNKNOWN);
    }

    public Prepaid getPrepaid() {
        return findByToString(Prepaid.values(), prepaid, Prepaid.UNKNOWN);
    }

    public String getProductId() {
        if ("".equals(productId)) {
            return "Unknown";
        } else {
            return productId;
        }
    }

    public String getCountryOfIssuance() {
        if ("".equals(countryOfIssuance)) {
            return "Unknown";
        } else {
            return countryOfIssuance;
        }
    }

    public String getIssuingBank() {
        if ("".equals(issuingBank)) {
            return "Unknown";
        } else {
            return issuingBank;
        }
    }

    public String getUniqueNumberIdentifier() {
        return uniqueNumberIdentifier;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public String getToken() {
        return token;
    }

    public Calendar getUpdatedAt() {
        return updatedAt;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public boolean isVenmoSdk() {
      return isVenmoSdk;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public CreditCardVerification getVerification() {
        return verification;
    }
}
