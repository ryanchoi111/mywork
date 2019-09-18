package com.codename1.googlemaps;


/**
 *  An abstract Map API that encapsulates the device native map and seamlessly replaces
 *  it with MapComponent when unsupported by the platform.
 * 
 *  @author Shai Almog
 */
public class MapContainer extends com.codename1.ui.Container {

	/**
	 *  Map type for native maps
	 */
	public static final int MAP_TYPE_TERRAIN = 1;

	/**
	 *  Map type for native maps
	 */
	public static final int MAP_TYPE_HYBRID = 2;

	/**
	 *  Map type for native maps
	 */
	public static final int MAP_TYPE_NONE = 3;

	/**
	 *  Default constructor creates an instance with the standard OpenStreetMap version if necessary
	 */
	public MapContainer() {
	}

	/**
	 *  Uses the given provider in case of a fallback
	 *  
	 *  @param provider the map provider
	 */
	public MapContainer(com.codename1.maps.providers.MapProvider provider) {
	}

	/**
	 *  Uses HTML JavaScript google maps on fallback platforms instead of the tiled map
	 *  @param javaScriptMapsAPIKey the API key for HTML maps
	 */
	public MapContainer(String javaScriptMapsAPIKey) {
	}

	/**
	 *  @inheritDoc
	 */
	@java.lang.Override
	protected void initComponent() {
	}

	/**
	 *  @inheritDoc
	 */
	@java.lang.Override
	protected void deinitialize() {
	}

	/**
	 *  Returns true if native maps are used
	 *  @return false if the lightweight maps are used
	 */
	public boolean isNativeMaps() {
	}

	/**
	 *  Adds a marker to the map with the given attributes
	 *  @param icon the icon, if the native maps are used this value can be null to use the default marker
	 *  @param location the coordinate for the marker
	 *  @param text the string associated with the location
	 *  @param longText longer description associated with the location
	 *  @param onClick will be invoked when the user clicks the marker. Important: events are only sent when the native map is in initialized state
	 *  @return marker reference object that should be used when removing the marker
	 */
	public MapContainer.MapObject addMarker(com.codename1.ui.EncodedImage icon, com.codename1.maps.Coord location, String text, String longText, com.codename1.ui.events.ActionListener onClick) {
	}

	/**
	 *  Draws a path on the map
	 *  @param path the path to draw on the map
	 *  @return a map object instance that allows us to remove the drawn path
	 */
	public MapContainer.MapObject addPath(com.codename1.maps.Coord[] path) {
	}

	/**
	 *  Returns the max zoom level of the map
	 * 
	 *  @return max zoom level
	 */
	public int getMaxZoom() {
	}

	/**
	 *  Returns the min zoom level of the map
	 * 
	 *  @return min zoom level
	 */
	public int getMinZoom() {
	}

	/**
	 *  Removes the map object from the map
	 *  @param obj the map object to remove
	 */
	public void removeMapObject(MapContainer.MapObject obj) {
	}

	/**
	 *  Removes all the layers from the map
	 */
	public void clearMapLayers() {
	}

	/**
	 *  Zoom to the given coordinate on the map
	 *  @param crd the coordinate
	 *  @param zoom the zoom level
	 */
	public void zoom(com.codename1.maps.Coord crd, int zoom) {
	}

	/**
	 *  Returns the current zoom level
	 *  @return the current zoom level between min/max zoom
	 */
	public float getZoom() {
	}

	public com.codename1.maps.BoundingBox getBoundingBox() {
	}

	/**
	 *  Sets the native map type to one of the MAP_TYPE constants
	 *  @param type one of the MAP_TYPE constants
	 */
	public void setMapType(int type) {
	}

	/**
	 *  Returns the native map type
	 *  @return one of the MAP_TYPE constants
	 */
	public int getMapType() {
	}

	/**
	 *  Position the map camera
	 *  @param crd the coordinate
	 */
	public void setCameraPosition(com.codename1.maps.Coord crd) {
	}

	/**
	 *  Returns the position in the center of the camera
	 *  @return the position
	 */
	public com.codename1.maps.Coord getCameraPosition() {
	}

	/**
	 *  Returns the lat/lon coordinate at the given x/y position
	 *  @param x the x position in component relative coordinate system
	 *  @param y the y position in component relative coordinate system
	 *  @return a lat/lon coordinate
	 */
	public com.codename1.maps.Coord getCoordAtPosition(int x, int y) {
	}

	/**
	 *  Returns the screen position for the coordinate in component relative position
	 *  @param lat the latitude
	 *  @param lon the longitude
	 *  @return the x/y position in component relative position
	 */
	public com.codename1.ui.geom.Point getScreenCoordinate(double lat, double lon) {
	}

	/**
	 *  Returns the location on the screen for the given coordinate
	 *  @param c the coordinate
	 *  @return the x/y position in component relative position
	 */
	public com.codename1.ui.geom.Point getScreenCoordinate(com.codename1.maps.Coord c) {
	}

	/**
	 *  Adds a listener to user tapping on a map location, this shouldn't fire for 
	 *  dragging.
	 *  
	 *  @param e the tap listener
	 */
	public void addTapListener(com.codename1.ui.events.ActionListener e) {
	}

	/**
	 *  Removes the listener to user tapping on a map location, this shouldn't fire for 
	 *  dragging.
	 *  
	 *  @param e the tap listener
	 */
	public void removeTapListener(com.codename1.ui.events.ActionListener e) {
	}

	/**
	 *  Adds a listener to user long pressing on a map location, this shouldn't fire for 
	 *  dragging.
	 *  
	 *  @param e the tap listener
	 */
	public void addLongPressListener(com.codename1.ui.events.ActionListener e) {
	}

	/**
	 *  Removes the long press listener to user tapping on a map location, this shouldn't fire for 
	 *  dragging.
	 *  
	 *  @param e the tap listener
	 */
	public void removeLongPressListener(com.codename1.ui.events.ActionListener e) {
	}

	/**
	 *  Adds a listener to map panning/zooming Important: events are only sent when the native map is in initialized state
	 *  @param listener the listener callback
	 */
	public void addMapListener(com.codename1.maps.MapListener listener) {
	}

	/**
	 *  Removes the map listener callback
	 *  @param listener the listener
	 */
	public void removeMapListener(com.codename1.maps.MapListener listener) {
	}

	/**
	 *  Show my location is a feature of the native maps only that allows marking
	 *  a users location on the map with a circle
	 *  @return the showMyLocation
	 */
	public boolean isShowMyLocation() {
	}

	/**
	 *  Show my location is a feature of the native maps only that allows marking
	 *  a users location on the map with a circle
	 *  @param showMyLocation the showMyLocation to set
	 */
	public void setShowMyLocation(boolean showMyLocation) {
	}

	/**
	 *  @return the rotateGestureEnabled
	 */
	public boolean isRotateGestureEnabled() {
	}

	/**
	 *  @param rotateGestureEnabled the rotateGestureEnabled to set
	 */
	public final void setRotateGestureEnabled(boolean rotateGestureEnabled) {
	}

	/**
	 *  Object on the map
	 */
	public static class MapObject {


		public MapObject() {
		}
	}
}
