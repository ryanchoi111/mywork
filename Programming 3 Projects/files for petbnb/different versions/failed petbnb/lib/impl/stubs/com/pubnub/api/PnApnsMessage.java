package com.pubnub.api;


/**
 *  Message object for APNS
 *  
 *  @author Pubnub
 */
public class PnApnsMessage extends org.json.JSONObject {

	/**
	 *  Constructor for APNS message object
	 */
	public PnApnsMessage() {
	}

	/**
	 *  Set value of APS alert
	 *  
	 *  @param alert
	 *             String to be set as alert value for APNS message
	 */
	public void setApsAlert(String alert) {
	}

	/**
	 *  Set value of APS badge
	 *  
	 *  @param badge
	 *             int to be set as badge value for APNS message
	 */
	public void setApsBadge(int badge) {
	}

	/**
	 *  Set value of APS sound
	 *  
	 *  @param sound
	 *             String to be set as sound value for APNS message
	 */
	public void setApsSound(String sound) {
	}
}
