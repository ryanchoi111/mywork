package com.pubnub.api;


/**
 *  Pubnub object facilitates querying channels for messages and listening on
 *  channels for presence/message events
 * 
 *  @author Pubnub
 */
public class Pubnub extends PubnubCoreAsync {

	/**
	 *  Pubnub Constructor
	 * 
	 *  @param publish_key
	 *             Publish Key
	 *  @param subscribe_key
	 *             Subscribe Key
	 *  @param secret_key
	 *             Secret Key
	 *  @param cipher_key
	 *             Cipher Key
	 *  @param ssl_on
	 *             SSL on ?
	 */
	public Pubnub(String publish_key, String subscribe_key, String secret_key, String cipher_key, boolean ssl_on) {
	}

	/**
	 *  Pubnub Constructor
	 * 
	 *  @param publish_key
	 *             Publish key
	 *  @param subscribe_key
	 *             Subscribe Key
	 *  @param secret_key
	 *             Secret Key
	 *  @param ssl_on
	 *             SSL on ?
	 */
	public Pubnub(String publish_key, String subscribe_key, String secret_key, boolean ssl_on) {
	}

	/**
	 *  Pubnub Constructor
	 * 
	 *  @param publish_key
	 *             Publish Key
	 *  @param subscribe_key
	 *             Subscribe Key
	 */
	public Pubnub(String publish_key, String subscribe_key) {
	}

	/**
	 *  @param publish_key
	 *             Publish Key
	 *  @param subscribe_key
	 *             Subscribe Key
	 *  @param ssl
	 */
	public Pubnub(String publish_key, String subscribe_key, boolean ssl) {
	}

	/**
	 *  @param publish_key
	 *  @param subscribe_key
	 *  @param secret_key
	 */
	public Pubnub(String publish_key, String subscribe_key, String secret_key) {
	}

	/**
	 *  Sets value for UUID
	 * 
	 *  @param uuid
	 *             UUID value for Pubnub client
	 */
	public void setUUID(String uuid) {
	}

	public String uuid() {
	}

	/**
	 *  This method sets timeout value for subscribe/presence. Default value is
	 *  310000 milliseconds i.e. 310 seconds
	 * 
	 *  @param timeout
	 *             Timeout value in milliseconds for subscribe/presence
	 */
	public void setSubscribeTimeout(int timeout) {
	}

	/**
	 *  This method returns timeout value for subscribe/presence.
	 * 
	 *  @return Timeout value in milliseconds for subscribe/presence
	 */
	public int getSubscribeTimeout() {
	}

	/**
	 *  This method set timeout value for non subscribe operations like publish,
	 *  history, hereNow. Default value is 15000 milliseconds i.e. 15 seconds.
	 * 
	 *  @param timeout
	 *             Timeout value in milliseconds for Non subscribe operations
	 *             like publish, history, hereNow
	 */
	public void setNonSubscribeTimeout(int timeout) {
	}

	/**
	 *  This method returns timeout value for non subscribe operations like
	 *  publish, history, hereNow
	 * 
	 *  @return Timeout value in milliseconds for for Non subscribe operations
	 *          like publish, history, hereNow
	 */
	public int getNonSubscribeTimeout() {
	}

	protected String getUserAgent() {
	}
}
