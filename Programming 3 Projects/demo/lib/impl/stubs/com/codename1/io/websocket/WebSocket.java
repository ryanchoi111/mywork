package com.codename1.io.websocket;


/**
 * 
 *  @author shannah
 */
public abstract class WebSocket {

	public WebSocket(String url) {
	}

	/**
	 *  Checks if websockets are supported on this platform.
	 *  @return 
	 */
	public static boolean isSupported() {
	}

	/**
	 *  @deprecated Internal callback for native implementations.
	 *  @param id
	 *  @param message 
	 */
	public static void messageReceived(int id, String message) {
	}

	/**
	 *  @deprecated Internal callback for native implementations
	 *  @param id
	 *  @param message 
	 */
	public static void messageReceived(int id, byte[] message) {
	}

	/**
	 *  @deprecated Internal callback for native implementations.  This wraps messageReceived
	 *  to work arround issue with callbacks in javascript port.
	 *  @param id
	 *  @param message 
	 */
	public static void messageReceivedBytes(int id, byte[] message) {
	}

	/**
	 *  @deprecated Internal callback for native implementations
	 *  @param id
	 *  @param statusCode
	 *  @param reason 
	 */
	public static void closeReceived(int id, int statusCode, String reason) {
	}

	/**
	 *  @deprecated Internal callback for native implementations.
	 *  @param id 
	 */
	public static void openReceived(int id) {
	}

	/**
	 *  @deprecated Internal callback for native implementations.
	 *  @param id
	 *  @param message
	 *  @param code 
	 */
	public static void errorReceived(int id, String message, int code) {
	}

	protected abstract void onOpen() {
	}

	protected abstract void onClose(int statusCode, String reason) {
	}

	protected abstract void onMessage(String message) {
	}

	protected abstract void onMessage(byte[] message) {
	}

	protected abstract void onError(Exception ex) {
	}

	public void send(String message) {
	}

	public void send(byte[] message) {
	}

	public void close() {
	}

	public void connect() {
	}

	public WebSocketState getReadyState() {
	}

	public static class WebSocketException {


		public WebSocketException(String message, int code) {
		}

		public int getCode() {
		}
	}
}
