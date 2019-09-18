package com.braintreegateway;

/**
 * Abstract class for fluent interface request builders.
 */
public abstract class Request {
    public String toXML() {
        throw new UnsupportedOperationException();
    }

    public String toQueryString(String parent) {
        throw new UnsupportedOperationException();
    }

    public String toQueryString() {
        throw new UnsupportedOperationException();
    }

    public String getKind() {
        return null;
    }

    protected String buildXMLElement(Object element) {
        return RequestBuilder.buildXMLElement(element);
    }

    protected String buildXMLElement(String name, Object element) {
        return RequestBuilder.buildXMLElement(name, element);
    }
}
