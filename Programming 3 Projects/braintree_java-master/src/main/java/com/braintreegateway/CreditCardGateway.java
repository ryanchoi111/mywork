package com.braintreegateway;

import com.braintreegateway.exceptions.NotFoundException;
import com.braintreegateway.util.Http;
import com.braintreegateway.util.NodeWrapper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Provides methods to create, delete, find, and update {@link CreditCard}
 * objects. This class does not need to be instantiated directly. Instead, use
 * {@link BraintreeGateway#creditCard()} to get an instance of this class:
 *
 * <pre>
 * BraintreeGateway gateway = new BraintreeGateway(...);
 * gateway.creditCard().create(...)
 * </pre>
 *
 * For more detailed information on {@link CreditCard CreditCards}, see <a
 * href="https://developers.braintreepayments.com/reference/response/credit-card/java"
 * target
 * ="_blank">https://developers.braintreepayments.com/reference/response/credit-card/java
 * </a><br />
 * For more detailed information on credit card verifications, see <a href=
 * "https://developers.braintreepayments.com/reference/response/credit-card-verification/java"
 * target="_blank">https://developers.braintreepayments.com/reference/response/credit-card-verification/java</a>
 */
public class CreditCardGateway {
    private Configuration configuration;
    private Http http;

    public CreditCardGateway(Http http, Configuration configuration) {
        this.configuration = configuration;
        this.http = http;
    }

    /**
     * Please use gateway.transparentRedirect().confirmCreditCard() instead
     */
    @Deprecated
    public Result<CreditCard> confirmTransparentRedirect(String queryString) {
        TransparentRedirectRequest trRequest = new TransparentRedirectRequest(configuration, queryString);
        NodeWrapper node = http.post(configuration.getMerchantPath() + "/payment_methods/all/confirm_transparent_redirect_request", trRequest);
        return new Result<CreditCard>(node, CreditCard.class);
    }

    /**
     * Creates an {@link CreditCard}.
     *
     * @param request
     *            the request.
     * @return a {@link Result}.
     */
    public Result<CreditCard> create(CreditCardRequest request) {
        NodeWrapper node = http.post(configuration.getMerchantPath() + "/payment_methods", request);
        return new Result<CreditCard>(node, CreditCard.class);
    }

    /**
     * Deletes a {@link CreditCard}.
     *
     * @param token
     *            the CreditCard's token.
     * @return a {@link Result}.
     */
    public Result<CreditCard> delete(String token) {
        http.delete(configuration.getMerchantPath() + "/payment_methods/credit_card/" + token);
        return new Result<CreditCard>();
    }

    /**
     * Finds a {@link CreditCard}.
     *
     * @param token
     *            the CreditCard's token.
     * @return the {@link CreditCard} or raises a
     *         {@link com.braintreegateway.exceptions.NotFoundException}.
     */
    public CreditCard find(String token) {
        if(token == null || token.trim().equals(""))
            throw new NotFoundException();

        return new CreditCard(http.get(configuration.getMerchantPath() + "/payment_methods/credit_card/" + token));
    }

    /**
     * Exchanges a payment method nonce for a {@link CreditCard}.
     *
     * @param nonce
     *            a payment method nonce.
     * @return the {@link CreditCard} or raises a
     *         {@link com.braintreegateway.exceptions.NotFoundException}.
     */
    public CreditCard fromNonce(String nonce) {
        if(nonce == null || nonce.trim().equals(""))
            throw new NotFoundException();

        try {
          return new CreditCard(http.get(configuration.getMerchantPath() + "/payment_methods/from_nonce/" + nonce));
        } catch (NotFoundException e) {
          throw new NotFoundException("Payment method with nonce " + nonce + " locked, consumed or not found");
        }
    }

    /**
     * Please use the Grant API instead.
     */
    @Deprecated
    public Result<PaymentMethodNonce> forward(PaymentMethodForwardRequest forwardRequest) throws NotFoundException {
        throw new NotFoundException("This method of forwarding payment methods is no longer supported. Please consider the Grant API for similar functionality.");
    }

    /**
     * Please use gateway.transparentRedirect().url() instead
     */
    @Deprecated
    public String transparentRedirectURLForCreate() {
        return configuration.getBaseURL() + configuration.getMerchantPath() + "/payment_methods/all/create_via_transparent_redirect_request";
    }

    /**
     * Please use gateway.transparentRedirect().url() instead
     */
    @Deprecated
    public String transparentRedirectURLForUpdate() {
        return configuration.getBaseURL() + configuration.getMerchantPath() + "/payment_methods/all/update_via_transparent_redirect_request";
    }

    /**
     * Updates a {@link CreditCard}.
     *
     * @param token
     *            the CreditCard's token.
     * @param request
     *            the request.
     * @return a {@link Result}.
     */
    public Result<CreditCard> update(String token, CreditCardRequest request) {
        NodeWrapper node = http.put(configuration.getMerchantPath() + "/payment_methods/credit_card/" + token, request);
        return new Result<CreditCard>(node, CreditCard.class);
    }

    /**
     * Returns a {@link ResourceCollection} of all expired credit cards.
     *
     * @return a {@link ResourceCollection}.
     */
    public ResourceCollection<CreditCard> expired() {
        NodeWrapper response = http.post(configuration.getMerchantPath() + "/payment_methods/all/expired_ids");
        return new ResourceCollection<CreditCard>(new ExpiredCreditCardPager(this), response);
    }

    List<CreditCard> fetchExpiredCreditCards(List<String> ids) {
        IdsSearchRequest query = new IdsSearchRequest().ids().in(ids);

        NodeWrapper response = http.post(configuration.getMerchantPath() + "/payment_methods/all/expired", query);

        List<CreditCard> items = new ArrayList<CreditCard>();
        for (NodeWrapper node : response.findAll("credit-card")) {
            items.add(new CreditCard(node));
        }

        return items;
    }

    /**
     * Returns a {@link ResourceCollection} of all credit cards expiring between
     * the given calendars.
     *
     * @return a {@link ResourceCollection}.
     */
    public ResourceCollection<CreditCard> expiringBetween(Calendar start, Calendar end) {
        String queryString = dateQueryString(start, end);
        NodeWrapper response = http.post(configuration.getMerchantPath() + "/payment_methods/all/expiring_ids?" + queryString);
        return new ResourceCollection<CreditCard>(new ExpiringCreditCardPager(this, queryString), response);
    }

    List<CreditCard> fetchExpiringCreditCards(List<String> ids, String queryString) {
        IdsSearchRequest query = new IdsSearchRequest().ids().in(ids);

        NodeWrapper response = http.post(configuration.getMerchantPath() + "/payment_methods/all/expiring?" + queryString, query);

        List<CreditCard> items = new ArrayList<CreditCard>();
        for (NodeWrapper node : response.findAll("credit-card")) {
            items.add(new CreditCard(node));
        }

        return items;
    }

    private String dateQueryString(Calendar start, Calendar end) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMyyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedStart = dateFormat.format(start.getTime());
        String formattedEnd = dateFormat.format(end.getTime());

        return String.format("start=%s&end=%s", formattedStart, formattedEnd);
    }
}
