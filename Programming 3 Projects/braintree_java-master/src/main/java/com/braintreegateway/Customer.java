package com.braintreegateway;

import com.braintreegateway.util.NodeWrapper;

import java.util.*;

public class Customer {

    private Calendar createdAt;
    private Calendar updatedAt;
    private String company;
    private String email;
    private String fax;
    private String firstName;
    private String id;
    private String lastName;
    private String phone;
    private String website;
    private Map<String, String> customFields;
    private List<CreditCard> creditCards;
    private List<PayPalAccount> paypalAccounts;
    private List<ApplePayCard> applePayCards;
    private List<AndroidPayCard> androidPayCards;
    private List<AmexExpressCheckoutCard> amexExpressCheckoutCards;
    private List<CoinbaseAccount> coinbaseAccounts;
    private List<VenmoAccount> venmoAccounts;
    private List<VisaCheckoutCard> visaCheckoutCards;
    private List<MasterpassCard> masterpassCards;
    private List<UsBankAccount> usBankAccounts;
    private List<Address> addresses;

    public Customer(NodeWrapper node) {
        id = node.findString("id");
        firstName = node.findString("first-name");
        lastName = node.findString("last-name");
        company = node.findString("company");
        email = node.findString("email");
        fax = node.findString("fax");
        phone = node.findString("phone");
        website = node.findString("website");
        createdAt = node.findDateTime("created-at");
        updatedAt = node.findDateTime("updated-at");
        customFields = node.findMap("custom-fields/*");
        creditCards = new ArrayList<CreditCard>();
        for (NodeWrapper creditCardResponse : node.findAll("credit-cards/credit-card")) {
            creditCards.add(new CreditCard(creditCardResponse));
        }
        paypalAccounts = new ArrayList<PayPalAccount>();
        for (NodeWrapper paypalResponse : node.findAll("paypal-accounts/paypal-account")) {
            paypalAccounts.add(new PayPalAccount(paypalResponse));
        }
        applePayCards = new ArrayList<ApplePayCard>();
        for (NodeWrapper applePayCardResponse : node.findAll("apple-pay-cards/apple-pay-card")) {
            applePayCards.add(new ApplePayCard(applePayCardResponse));
        }
        androidPayCards = new ArrayList<AndroidPayCard>();
        for (NodeWrapper androidPayCardResponse : node.findAll("android-pay-cards/android-pay-card")) {
            androidPayCards.add(new AndroidPayCard(androidPayCardResponse));
        }
        amexExpressCheckoutCards = new ArrayList<AmexExpressCheckoutCard>();
        for (NodeWrapper amexExpressCheckoutCardResponse : node.findAll("amex-express-checkout-cards/amex-express-checkout-card")) {
            amexExpressCheckoutCards.add(new AmexExpressCheckoutCard(amexExpressCheckoutCardResponse));
        }
        coinbaseAccounts = new ArrayList<CoinbaseAccount>();
        for (NodeWrapper coinbaseAccountResponse : node.findAll("coinbase-accounts/coinbase-account")) {
            coinbaseAccounts.add(new CoinbaseAccount(coinbaseAccountResponse));
        }
        venmoAccounts = new ArrayList<VenmoAccount>();
        for (NodeWrapper venmoAccountResponse : node.findAll("venmo-accounts/venmo-account")) {
            venmoAccounts.add(new VenmoAccount(venmoAccountResponse));
        }
        visaCheckoutCards = new ArrayList<VisaCheckoutCard>();
        for (NodeWrapper visaCheckoutCardResponse : node.findAll("visa-checkout-cards/visa-checkout-card")) {
            visaCheckoutCards.add(new VisaCheckoutCard(visaCheckoutCardResponse));
        }
        masterpassCards = new ArrayList<MasterpassCard>();
        for (NodeWrapper masterpassCardResponse : node.findAll("masterpass-cards/masterpass-card")) {
            masterpassCards.add(new MasterpassCard(masterpassCardResponse));
        }
        usBankAccounts = new ArrayList<UsBankAccount>();
        for (NodeWrapper usBankAccountResponse : node.findAll("us-bank-accounts/us-bank-account")) {
            usBankAccounts.add(new UsBankAccount(usBankAccountResponse));
        }
        addresses = new ArrayList<Address>();
        for (NodeWrapper addressResponse : node.findAll("addresses/address")) {
            addresses.add(new Address(addressResponse));
        }
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public Calendar getUpdatedAt() {
        return updatedAt;
    }

    public String getId() {
        return id;
    }

    public String getCompany() {
        return company;
    }

    public Map<String, String> getCustomFields() {
        return customFields;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getFax() {
        return fax;
    }

    public String getPhone() {
        return phone;
    }

    public String getWebsite() {
        return website;
    }

    public List<Address> getAddresses() {
        return Collections.unmodifiableList(addresses);
    }

    public List<CreditCard> getCreditCards() {
        return Collections.unmodifiableList(creditCards);
    }

    public List<PayPalAccount> getPayPalAccounts() {
        return Collections.unmodifiableList(paypalAccounts);
    }

    public List<CoinbaseAccount> getCoinbaseAccounts() {
        return Collections.unmodifiableList(coinbaseAccounts);
    }

    public List<ApplePayCard> getApplePayCards() {
        return Collections.unmodifiableList(applePayCards);
    }

    public List<AndroidPayCard> getAndroidPayCards() {
        return Collections.unmodifiableList(androidPayCards);
    }

    public List<AmexExpressCheckoutCard> getAmexExpressCheckoutCards() {
        return Collections.unmodifiableList(amexExpressCheckoutCards);
    }

    public List<VenmoAccount> getVenmoAccounts() {
        return Collections.unmodifiableList(venmoAccounts);
    }

    public List<VisaCheckoutCard> getVisaCheckoutCards() {
        return Collections.unmodifiableList(visaCheckoutCards);
    }

    public List<MasterpassCard> getMasterpassCards() {
        return Collections.unmodifiableList(masterpassCards);
    }

    public List<UsBankAccount> getUsBankAccounts() {
        return Collections.unmodifiableList(usBankAccounts);
    }

    public List<? extends PaymentMethod> getPaymentMethods() {
        List<PaymentMethod> paymentMethods = new ArrayList<PaymentMethod>();
        paymentMethods.addAll(getCreditCards());
        paymentMethods.addAll(getPayPalAccounts());
        paymentMethods.addAll(getApplePayCards());
        paymentMethods.addAll(getAndroidPayCards());
        paymentMethods.addAll(getAmexExpressCheckoutCards());
        paymentMethods.addAll(getVenmoAccounts());
        paymentMethods.addAll(getVisaCheckoutCards());
        paymentMethods.addAll(getMasterpassCards());
        paymentMethods.addAll(getCoinbaseAccounts());
        return Collections.unmodifiableList(paymentMethods);
    }

    public PaymentMethod getDefaultPaymentMethod() {
        for (PaymentMethod paymentMethod : getPaymentMethods()) {
            if (paymentMethod.isDefault()) {
                return paymentMethod;
            }
        }
        return null;
    }
}
