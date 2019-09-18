package com.braintreegateway.util;

import com.braintreegateway.*;
import com.braintreegateway.testhelpers.TestHelper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TrUtilTest {

    private Configuration configuration;

    @Before
    public void createConfiguration() {
        this.configuration = new Configuration(Environment.DEVELOPMENT, "baseMerchantURL", "integration_public_key", "integration_private_key");
    }

    @Test
    public void buildTrData() {
        CreditCardRequest request = new CreditCardRequest().customerId("123");
        String trData = new TrUtil(configuration).buildTrData(request, "http://example.com");
        TestHelper.assertValidTrData(configuration, trData);
    }

    @Test
    public void isValidTrQueryStringForValidString() {
        String queryString = "http_status=200&id=6kdj469tw7yck32j&hash=99c9ff20cd7910a1c1e793ff9e3b2d15586dc6b9";
        assertTrue(new TrUtil(configuration).isValidTrQueryString(queryString));
    }

    @Test
    public void isValidTrQueryStringForInvalidString() {
        String queryString = "http_status=200&id=6kdj469tw7yck32j&hash=99c9ff20cd7910a1c1e793ff9e3b2d15586dc6b8";
        assertFalse(new TrUtil(configuration).isValidTrQueryString(queryString));
    }

    @Test
    public void apiVersionIsCorrectInTrData() {
        TestHelper.assertIncludes("api_version=4", new TrUtil(configuration).buildTrData(new TransactionRequest(), "http://google.com"));
    }

    @Test
    public void url() {
        TestHelper.assertIncludes("baseMerchantURL/transparent_redirect_requests", new TrUtil(configuration).url());
    }
}
