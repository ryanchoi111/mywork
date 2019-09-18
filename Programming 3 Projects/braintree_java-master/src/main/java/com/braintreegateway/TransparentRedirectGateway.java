package com.braintreegateway;

import com.braintreegateway.util.Http;
import com.braintreegateway.util.NodeWrapper;
import com.braintreegateway.util.StringUtils;
import com.braintreegateway.util.TrUtil;

public class TransparentRedirectGateway {
    public static String CREATE_TRANSACTION = "create_transaction";
    public static String CREATE_CUSTOMER = "create_customer";
    public static String UPDATE_CUSTOMER = "update_customer";
    public static String CREATE_PAYMENT_METHOD = "create_payment_method";
    public static String UPDATE_PAYMENT_METHOD = "update_payment_method";

    private Http http;
    private Configuration configuration;

    public TransparentRedirectGateway(Http http, Configuration configuration) {
        this.http = http;
        this.configuration = configuration;
    }

    public String url() {
        return new TrUtil(configuration).url();
    }

    public Result<CreditCard> confirmCreditCard(String queryString) {
        return confirmTr(CreditCard.class, queryString);
    }

    public Result<Customer> confirmCustomer(String queryString) {
        return confirmTr(Customer.class, queryString);
    }

    public Result<Transaction> confirmTransaction(String queryString) {
        return confirmTr(Transaction.class, queryString);
    }

    public String trData(Request trData, String redirectURL) {
        return new TrUtil(configuration).buildTrData(trData, redirectURL);
    }

    private <T> Result<T> confirmTr(Class<T> klass, String queryString) {
        TransparentRedirectRequest trRequest = new TransparentRedirectRequest(configuration, queryString);
        NodeWrapper node = http.post(configuration.getMerchantPath() + "/transparent_redirect_requests/" + trRequest.getId() + "/confirm", trRequest);
        if (!node.getElementName().equals(StringUtils.classToXMLName(klass)) && !node.getElementName().equals("api-error-response")) {
            throw new IllegalArgumentException("You attemped to confirm a " + StringUtils.classToXMLName(klass) + ", but received a " + node.getElementName() + ".");
        }

        return new Result<T>(node, klass);
    }
}
