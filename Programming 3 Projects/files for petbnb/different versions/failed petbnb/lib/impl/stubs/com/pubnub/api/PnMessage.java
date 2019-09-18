package com.pubnub.api;


/**
 *  Pubnub Message Object
 *  
 *  @author Pubnub
 */
public class PnMessage extends org.json.JSONObject {

	/**
	 *  Constructor for Pubnub Message Class
	 *  
	 *  @param pubnub
	 *             Pubnub object
	 *  @param channel
	 *             Channel name
	 *  @param callback
	 *             Callback object
	 */
	public PnMessage(Pubnub pubnub, String channel, Callback callback) {
	}

	/**
	 *  Constructor for Pubnub Message Class
	 */
	public PnMessage() {
	}

	/**
	 *  Constructor for Pubnub Message Class
	 *  
	 *  @param apnsMsg
	 *             Pubnub APNS message object
	 *  @param gcmMsg
	 *             Pubnub GCM message object
	 */
	public PnMessage(PnApnsMessage apnsMsg, PnGcmMessage gcmMsg) {
	}

	/**
	 *  Constructor for Pubnub Message Class
	 *  
	 *  @param pubnub
	 *             Pubnub object
	 *  @param callback
	 *             Callback object
	 *  @param apnsMsg
	 *             Pubnub APNS message object
	 *  @param gcmMsg
	 *             Pubnub GCM message object
	 */
	public PnMessage(Pubnub pubnub, String channel, Callback callback, PnApnsMessage apnsMsg, PnGcmMessage gcmMsg) {
	}

	/**
	 *  Constructor for Pubnub Message Class
	 *  
	 *  @param gcmMsg
	 *             Pubnub GCM message object
	 */
	public PnMessage(PnGcmMessage gcmMsg) {
	}

	/**
	 *  Constructor for Pubnub Message Class
	 *  
	 *  @param pubnub
	 *             Pubnub
	 *  @param channel
	 *             Channel
	 *  @param callback
	 *             Callback object
	 *  @param gcmMsg
	 *             Pubnub GCM message object
	 */
	public PnMessage(Pubnub pubnub, String channel, Callback callback, PnGcmMessage gcmMsg) {
	}

	/**
	 *  Constructor for Pubnub Message Class
	 *  
	 *  @param apnsMsg
	 *             Pubnub APNS message object
	 */
	public PnMessage(PnApnsMessage apnsMsg) {
	}

	/**
	 *  Constructor for Pubnub Message Class
	 *  
	 *  @param pubnub
	 *             Pubnub
	 *  @param channel
	 *             Channel
	 *  @param callback
	 *             Callback object
	 *  @param apnsMsg
	 *             Pubnub APNS message object
	 */
	public PnMessage(Pubnub pubnub, String channel, Callback callback, PnApnsMessage apnsMsg) {
	}

	/**
	 *  Getter for channel set on PnMessage Object
	 *  
	 *  @return channel
	 */
	public String getChannel() {
	}

	/**
	 *  Setter for channel on PnMessage Object
	 *  
	 *  @param channel
	 *             Channel name
	 */
	public void setChannel(String channel) {
	}

	/**
	 *  Getter for callback set on PnMessage object
	 *  
	 *  @return callback
	 */
	public Callback getCallback() {
	}

	/**
	 *  Setter for callback on PnMessage object
	 *  
	 *  @param callback
	 *             Callback
	 */
	public void setCallback(Callback callback) {
	}

	/**
	 *  Getter for pubnub set on PnMessage object
	 *  
	 *  @return pubnub
	 */
	public Pubnub getPubnub() {
	}

	/**
	 *  Setter for pubnub on PnMessage object
	 *  
	 *  @param pubnub
	 *             Pubnub object
	 */
	public void setPubnub(Pubnub pubnub) {
	}

	/**
	 *  Publish Message
	 *  
	 *  @param pubnub
	 *             Pubnub object
	 *  @param channel
	 *             Channel
	 *  @param callback
	 *             Callback object
	 *  @throws PubnubException
	 *              Exception if either channel or pubnub object is not set
	 */
	public void publish(Pubnub pubnub, String channel, Callback callback) {
	}

	/**
	 *  Publish Message
	 *  
	 *  @param pubnub
	 *             Pubnub object
	 *  @param channel
	 *             Channel
	 *  @param storeInHistory
	 *             Store in History
	 *  @param callback
	 *             Callback object
	 *  @throws PubnubException
	 *              Exception if either channel or pubnub object is not set
	 */
	public void publish(Pubnub pubnub, String channel, boolean storeInHistory, Callback callback) {
	}

	/**
	 *  Publish Message
	 *  
	 *  @throws PubnubException
	 *              Exception if either channel or pubnub object is not set
	 */
	public void publish() {
	}

	/**
	 *  Publish Message
	 *  
	 *  @param storeInHistory
	 *             Store in History
	 *  @throws PubnubException
	 *              Exception if either channel or pubnub object is not set
	 */
	public void publish(boolean storeInHistory) {
	}
}
