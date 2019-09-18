package com.braintreegateway;

import com.braintreegateway.org.apache.commons.codec.binary.Base64;
import com.braintreegateway.util.Sha1Hasher;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

public class WebhookTestingGateway {
    private Configuration configuration;

    public WebhookTestingGateway(Configuration configuration) {
        this.configuration = configuration;
    }

    private String buildPayload(WebhookNotification.Kind kind, String id, String sourceMerchantId) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        String timestamp = dateFormat.format(new Date());

        String payload = "<notification>";
        payload += "<timestamp type=\"datetime\">" + timestamp + "</timestamp>";
        payload += "<kind>" + kind + "</kind>";
        if (sourceMerchantId != null) {
            payload += "<source-merchant-id>" + sourceMerchantId + "</source-merchant-id>";
        }
        payload += "<subject>" + subjectXml(kind, id) + "</subject>";
        payload += "</notification>";

        return Base64.encodeBase64String(payload.getBytes()).replace("\r", "");
    }

    private String publicKeySignaturePair(String stringToSign) {
        return String.format("%s|%s", configuration.getPublicKey(), new Sha1Hasher().hmacHash(configuration.getPrivateKey(), stringToSign));
    }

    public HashMap<String, String> sampleNotification(WebhookNotification.Kind kind, String id) {
        return sampleNotification(kind, id, null);
    }

    public HashMap<String, String> sampleNotification(WebhookNotification.Kind kind, String id, String sourceMerchantId) {
        HashMap<String, String> response = new HashMap<String, String>();
        String payload = buildPayload(kind, id, sourceMerchantId);
        response.put("bt_payload", payload);
        response.put("bt_signature", publicKeySignaturePair(payload));

        return response;
    }

    private String subjectXml(WebhookNotification.Kind kind, String id) {
        switch(kind) {
            case CHECK: return checkXml();
            case SUB_MERCHANT_ACCOUNT_APPROVED: return merchantAccountXmlActive(id);
            case SUB_MERCHANT_ACCOUNT_DECLINED: return merchantAccountXmlDeclined(id);
            case TRANSACTION_DISBURSED: return transactionXml(id);
            case TRANSACTION_SETTLED: return transactionSettledXml(id);
            case TRANSACTION_SETTLEMENT_DECLINED: return transactionSettlementDeclinedXml(id);
            case DISBURSEMENT: return disbursementXml(id);
            case DISPUTE_OPENED: return disputeOpenedXml(id);
            case DISPUTE_LOST: return disputeLostXml(id);
            case DISPUTE_WON: return disputeWonXml(id);
            case DISBURSEMENT_EXCEPTION: return disbursementExceptionXml(id);
            case PARTNER_MERCHANT_CONNECTED: return partnerMerchantConnectedXml(id);
            case PARTNER_MERCHANT_DISCONNECTED: return partnerMerchantDisconnectedXml(id);
            case PARTNER_MERCHANT_DECLINED: return partnerMerchantDeclinedXml(id);
            case CONNECTED_MERCHANT_STATUS_TRANSITIONED: return connectedMerchantStatusTransitionedXml(id);
            case CONNECTED_MERCHANT_PAYPAL_STATUS_CHANGED: return connectedMerchantPayPalStatusChangedXml(id);
            case SUBSCRIPTION_CHARGED_SUCCESSFULLY: return subscriptionChargedSuccessfullyXml(id);
            case ACCOUNT_UPDATER_DAILY_REPORT: return accountUpdaterDailyReportXml(id);
            case IDEAL_PAYMENT_COMPLETE: return idealPaymentCompleteXml(id);
            case IDEAL_PAYMENT_FAILED: return idealPaymentFailedXml(id);
            case GRANTED_PAYMENT_INSTRUMENT_UPDATE: return grantedPaymentInstrumentUpdateXml();
            default: return subscriptionXml(id);
        }
    }

    private String[][] TYPE_DATE = {{"type", "date"}};
    private String[][] TYPE_ARRAY = {{"type", "array"}};
    private String[][] TYPE_SYMBOL = {{"type", "symbol"}};
    private String[][] TYPE_BOOLEAN = {{"type", "boolean"}};
    private String[][] NIL_TRUE = {{"nil", "true"}};

    private String merchantAccountXmlDeclined(String id) {
        return node("api-error-response",
                node("message", "Credit score is too low"),
                node("errors", TYPE_ARRAY,
                    node("merchant-account",
                        node("errors", TYPE_ARRAY,
                            node("error",
                                node("code", "82621"),
                                node("message", "Credit score is too low"),
                                node("attribute", TYPE_SYMBOL, "base")
                            )
                        )
                    )
                ),
                node("merchant-account",
                    node("id", id),
                    node("status", "suspended"),
                    node("master-merchant-account",
                        node("id", "master_ma_for_" + id),
                        node("status", "suspended")
                    )
                )
        );
    }

    private String merchantAccountXmlActive(String id) {
          return node("merchant-account",
                  node("id", id),
                  node("master-merchant-account",
                      node("id", id),
                      node("status", "active")
                  ),
                  node("status", "active")
        );
    }

    private String subscriptionXml(String id) {
        return node("subscription",
                node("id", id),
                node("transactions", TYPE_ARRAY),
                node("add_ons", TYPE_ARRAY),
                node("discounts", TYPE_ARRAY)
        );
    }

    private String subscriptionChargedSuccessfullyXml(String id) {
        return node("subscription",
                node("id", id),
                node("add_ons", TYPE_ARRAY),
                node("transactions",
                    node("transaction",
                        node("id", "1"),
                        node("status", "submitted_for_settlement"),
                        node("amount", "49.99"),
                        node("billing"),
                        node("credit-card"),
                        node("customer"),
                        node("descriptor"),
                        node("shipping"),
                        node("disbursement-details", TYPE_ARRAY),
                        node("subscription")
                    )
                ),
                node("discounts", TYPE_ARRAY)
        );
    }

    private String transactionXml(String id) {
        return node("transaction",
                node("id", id),
                node("amount", "100"),
                node("disbursement-details",
                    node("disbursement-date", TYPE_DATE, "2013-07-09")
                ),
                node("billing"),
                node("credit-card"),
                node("customer"),
                node("descriptor"),
                node("shipping"),
                node("subscription")
        );
    }

    private String transactionSettledXml(String id) {
        return node("transaction",
                node("id", id),
                node("status", "settled"),
                node("amount", "100"),
                node("us-bank-account",
                    node("routing-number", "123456789"),
                    node("last-4", "1234"),
                    node("account-type", "checking"),
                    node("account-holder-name", "Dan Schulman"),
                    node("ach-mandate",
                        node("text", "Sample ACH Mandate Text"),
                        node("accepted-at", "2017-01-17")
                    )
                ),
                node("disbursement-details"),
                node("billing"),
                node("credit-card"),
                node("customer"),
                node("descriptor"),
                node("shipping"),
                node("subscription")
        );
    }

    private String transactionSettlementDeclinedXml(String id) {
        return node("transaction",
                node("id", id),
                node("status", "settlement_declined"),
                node("amount", "100"),
                node("us-bank-account",
                    node("routing-number", "123456789"),
                    node("last-4", "1234"),
                    node("account-type", "checking"),
                    node("account-holder-name", "Dan Schulman"),
                    node("ach-mandate",
                        node("text", "Sample ACH Mandate Text"),
                        node("accepted-at", "2017-01-17")
                    )
                ),
                node("disbursement-details"),
                node("billing"),
                node("credit-card"),
                node("customer"),
                node("descriptor"),
                node("shipping"),
                node("subscription")
        );
    }

    private String disputeOpenedXml(String id) {
        return node("dispute",
                node("id", id),
                node("amount", "250.00"),
                node("received-date", TYPE_DATE, "2014-03-21"),
                node("reply-by-date", TYPE_DATE, "2014-03-21"),
                node("date-opened", TYPE_DATE, "2014-03-21"),
                node("kind", "chargeback"),
                node("currency-iso-code", "USD"),
                node("status", "open"),
                node("reason", "fraud"),
                node("transaction",
                    node("id", id),
                    node("amount", "250.00")
                )
        );
    }

    private String disputeLostXml(String id) {
        return node("dispute",
                node("id", id),
                node("amount", "250.00"),
                node("received-date", TYPE_DATE, "2014-03-21"),
                node("reply-by-date", TYPE_DATE, "2014-03-21"),
                node("date-opened", TYPE_DATE, "2014-03-21"),
                node("kind", "chargeback"),
                node("currency-iso-code", "USD"),
                node("status", "lost"),
                node("reason", "fraud"),
                node("transaction",
                    node("id", id),
                    node("amount", "250.00")
                )
        );
    }

    private String disputeWonXml(String id) {
        return node("dispute",
                node("id", id),
                node("amount", "250.00"),
                node("received-date", TYPE_DATE, "2014-03-21"),
                node("reply-by-date", TYPE_DATE, "2014-03-21"),
                node("date-opened", TYPE_DATE, "2014-03-21"),
                node("date-won", TYPE_DATE, "2014-03-22"),
                node("kind", "chargeback"),
                node("currency-iso-code", "USD"),
                node("status", "won"),
                node("reason", "fraud"),
                node("transaction",
                    node("id", id),
                    node("amount", "250.00")
                )
        );
    }

    private String disbursementXml(String id) {
        return node("disbursement",
                node("id", id),
                node("transaction-ids", TYPE_ARRAY,
                    node("item", "asdf"),
                    node("item", "qwer")
                ),
                node("success", TYPE_BOOLEAN, "true"),
                node("retry", TYPE_BOOLEAN, "false"),
                node("exception-message", NIL_TRUE),
                node("amount", "100.00"),
                node("disbursement-date", TYPE_DATE, "2014-02-10"),
                node("follow-up-action", NIL_TRUE),
                node("merchant-account",
                    node("id", "merchant_account_token"),
                    node("currency-iso-code", "USD"),
                    node("sub-merchant-account", TYPE_BOOLEAN, "false"),
                    node("status", "active")
                )
        );
    }

    private String disbursementExceptionXml(String id) {
        return node("disbursement",
                node("id", id),
                node("transaction-ids", TYPE_ARRAY,
                    node("item", "asdf"),
                    node("item", "qwer")
                ),
                node("success", TYPE_BOOLEAN, "false"),
                node("retry", TYPE_BOOLEAN, "false"),
                node("exception-message", "bank_rejected"),
                node("amount", "100.00"),
                node("disbursement-date", TYPE_DATE, "2014-02-10"),
                node("follow-up-action", "update_account_information"),
                node("merchant-account",
                    node("id", "merchant_account_token"),
                    node("currency-iso-code", "USD"),
                    node("sub-merchant-account", TYPE_BOOLEAN, "false"),
                    node("status", "active")
                )
        );
    }

    private String partnerMerchantConnectedXml(String id) {
        return node("partner-merchant",
                node("partner-merchant-id", "abc123"),
                node("merchant-public-id", "public_id"),
                node("public-key", "public_key"),
                node("private-key", "private_key"),
                node("client-side-encryption-key", "cse_key")
        );
    }

    private String partnerMerchantDisconnectedXml(String id) {
        return node("partner-merchant",
                node("partner-merchant-id", "abc123")
        );
    }

    private String partnerMerchantDeclinedXml(String id) {
        return node("partner-merchant",
                node("partner-merchant-id", "abc123")
        );
    }

    private String connectedMerchantStatusTransitionedXml(String id) {
        return node("connected-merchant-status-transitioned",
                node("oauth-application-client-id", "oauth_application_client_id"),
                node("merchant-public-id", id),
                node("status", "new_status")
        );
    }

    private String connectedMerchantPayPalStatusChangedXml(String id) {
        return node("connected-merchant-paypal-status-changed",
                node("oauth-application-client-id", "oauth_application_client_id"),
                node("merchant-public-id", id),
                node("action", "link")
        );
    }

    private String accountUpdaterDailyReportXml(String id) {
        return node("account-updater-daily-report",
                node("report-url", "link-to-csv-report"),
                node("report-date", TYPE_DATE, "2016-01-14")
        );
    }

    private String idealPaymentCompleteXml(String id) {
        return node("ideal-payment",
                node("id", id),
                node("status", "COMPLETE"),
                node("issuer", "ABCISSUER"),
                node("order-id", "ORDERABC"),
                node("currency", "EUR"),
                node("amount", "10.00"),
                node("created-at", "2016-11-29T23:27:34.547Z"),
                node("approval-url", "https://example.com"),
                node("ideal-transaction-id", "1234567890")
        );
    }

    private String idealPaymentFailedXml(String id) {
        return node("ideal-payment",
                node("id", id),
                node("status", "FAILED"),
                node("issuer", "ABCISSUER"),
                node("order-id", "ORDERABC"),
                node("currency", "EUR"),
                node("amount", "10.00"),
                node("created-at", "2016-11-29T23:27:34.547Z"),
                node("approval-url", "https://example.com"),
                node("ideal-transaction-id", "1234567890")
        );
    }

    private String grantedPaymentInstrumentUpdateXml() {
        return node("granted-payment-instrument-update",
                node("grant-owner-merchant-id", "vczo7jqrpwrsi2px"),
                node("grant-recipient-merchant-id", "cf0i8wgarszuy6hc"),
                node("payment-method-nonce",
                    node("nonce", "ee257d98-de40-47e8-96b3-a6954ea7a9a4"),
                    node("consumed", TYPE_BOOLEAN, "false"),
                    node("locked", TYPE_BOOLEAN, "false")
                    ),
                node("token", "abc123z"),
                node("updated-fields", TYPE_ARRAY,
                    node("item", "expiration-month"),
                    node("item", "expiration-year")
                    )
                );
    }

    private String checkXml() {
        return node("check", TYPE_BOOLEAN, "true");
    }

    private static String node(String name, String... contents) {
        return node(name, null, contents);
    }

    private static String node(String name, String[][] attributes, String... contents) {
        StringBuilder buffer = new StringBuilder();
        buffer.append('<').append(name);
        if (attributes != null) {
            for (String[] pair : attributes) {
                buffer.append(" ");
                buffer.append(pair[0]).append("=\"").append(pair[1]).append("\"");
            }
        }
        buffer.append('>');
        for (String content : contents) {
            buffer.append(content);
        }
        buffer.append("</").append(name).append('>');
        return buffer.toString();
    }
}
