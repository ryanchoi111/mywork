package com.braintreegateway.test;

public class VenmoSdk {
    public enum PaymentMethodCode {
        Visa("4111111111111111"), Invalid("invalid-payment-method-code");

        public String code;

        private PaymentMethodCode(String number) {
            this.code = VenmoSdk.generateTestPaymentMethodCode(number);
        }
    }

    public static String generateTestPaymentMethodCode(String number) {
        return "stub-" + number;
    }

    public enum Session {
        Valid("stub-session"), Invalid("stub-invalid-session");

        public String value;

        private Session(String validity) {
            this.value = validity;
        }
    }
}
