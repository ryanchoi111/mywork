package com.mycompany.petbnb;

import com.codename1.braintree.Purchase;
import com.codename1.components.*;
import com.codename1.contacts.Contact;
import com.codename1.db.Database;
import com.codename1.googlemaps.MapContainer;
import com.codename1.io.*;
import com.codename1.maps.BoundingBox;
import com.codename1.maps.Coord;
import com.codename1.maps.MapListener;
import com.codename1.processing.Result;
import com.codename1.properties.SQLMap;
import com.codename1.social.FacebookConnect;
import com.codename1.social.GoogleConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.geom.Rectangle;
import com.codename1.ui.layouts.*;
import com.codename1.ui.layouts.mig.Grid;
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import static com.codename1.ui.ComponentSelector.$;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.InteractionDialog;
import com.codename1.components.ToastBar;
import com.codename1.io.Util;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.SideMenuBar;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.UITimer;

import java.io.InputStreamReader;
import java.util.*;



//http://kjc.ma1geek.org/test.php
/**
 *
 * NOTE: If you click the Search Users button the homescreen while the back end is still connecting to the database, a NullPointerException will be thrown.
 * Please wait for the back end to finish connecting to the database before clicking Search Users! Otherwise, no errors occur in the App!

 *
 */

public class PetBnBApp {

    private static final String HTML_API_KEY = "AIzaSyBWeRU02YUYPdwRuMFyTKIXUbHjq6e35Gw";
    private Form current;
    private Resources theme;

    //all major forms
    private Form loadingScreen, search, details, loginForm, listingpage, listing, hi, host;
    //required tools for functionality of app
    private Label first , last , ad, ads, z, ct, st, country1, loginDog, img, imgLoad;
    private TextField txtFirst,txtLast, firstn, lastn, ad1, ad2, zip, city, state1, country;
    private Button  testCoordPositions;
    private Peep[] team;
    private Container panela,panelb,panelba,panelbb;
    private int x;
    private Image milton;
    private Coord coordinates = null;
    private String dbUrl= "http://petbnbactual.ma1geek.org/testFile6.php";
    private String dbSizeUrl="http://petbnbactual.ma1geek.org/testFile7.php";
    private int usersLoaded=0; //Added 4/12
    private ArrayList<Button> userList;
    private ArrayList<MultiButton> userList2;
    private ArrayList<TextField> contentList;
    private Login login;
    Databasehelpr base;
    public boolean loggedIn = false;
    public boolean loading = false;
    private Image mapPic;
    private Image hPetPic;
    private Image hHomePic;
    private Image listingsPic;
    private Image profilePic;
    private Image load;
    private Image settingsPic;
    InfiniteProgress circle;



    Container panel2;
    Button submit;




    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");


        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature

        try {
            Resources theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
            Display.getInstance().setCommandBehavior(Display.COMMAND_BEHAVIOR_SIDE_NAVIGATION);
            UIManager.getInstance().getLookAndFeel().setMenuBarClass(SideMenuBar.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //Geocode help recieved by Jack Weiler
    public Coord geocode(String fullAddress){
        String text="";
        Coord ret = null;
        try {
            final String link = "https://maps.googleapis.com/maps/api/geocode/json";

            // Open the Connection
            ConnectionRequest req = new ConnectionRequest();
            req.setPost(false);
            req.setUrl(link);
            req.addArgument("address", fullAddress);
            req.addArgument("key", HTML_API_KEY);


            //https://gist.github.com/ahmedengu/0869b8c0644eee0d57d3891bc51a00dc
            NetworkManager.getInstance().addToQueueAndWait(req);
            Map<String, Object> response = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(req.getResponseData()), "UTF-8"));
            if (response.get("results") != null) {
                ArrayList results = (ArrayList) response.get("results");
                if (results.size() > 0) {
                    LinkedHashMap location = (LinkedHashMap) ((LinkedHashMap) ((LinkedHashMap) results.get(0)).get("geometry")).get("location");
                    ret = new Coord((double) location.get("lat"), (double) location.get("lng"));



                }
            }
            return ret;

        }
        catch(Exception ex){
            Log.e(ex);
        }

        return null;


        //Methods below all refer to search Locations and panning out desired locations,

    }
    String[] searchLocations(String input) {
        try {
            if(input.length() > 0) {
                ConnectionRequest r = new ConnectionRequest();
                r.setPost(false);
                r.setUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json");
                r.addArgument("key", HTML_API_KEY);
                r.addArgument("input", input);
                NetworkManager.getInstance().addToQueueAndWait(r);
                Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
                String[] res = Result.fromContent(result).getAsStringArray("//description");
                return res;
            }
        } catch(Exception err) {
            Log.e(err);
        }
        return null;
    }

    public ArrayList testurl()
    {
        ConnectionRequest req2=new ConnectionRequest();
        req2.setUrl(dbUrl);
        req2.setPost(false);
        req2.setHttpMethod("GET");
        req2.addArgument("encoding","json");
        NetworkManager.getInstance().addToQueueAndWait(req2);
        JSONParser parser = new JSONParser();
        Map result=null;
        LinkedHashMap user1=null;
        LinkedHashMap user2=null;
        try {
            result =  parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(req2.getResponseData())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList users=(ArrayList)result.get("User");
        return(users);
    }

    public int finddbSize()
    {
        int size;
        ConnectionRequest req2=new ConnectionRequest();
        req2.setUrl(dbSizeUrl);
        req2.setPost(false);
        req2.setHttpMethod("GET");
        req2.addArgument("encoding","json");
        NetworkManager.getInstance().addToQueueAndWait(req2);
        JSONParser parser = new JSONParser();
        Map dbSizeResult=null;
        try {
            dbSizeResult =  parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(req2.getResponseData())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList userNum=(ArrayList)dbSizeResult.get("User");
        size=userNum.size();
        return(size);
    }
//    public String getData(int x, String str){
//        ArrayList userArray;
//        userArray=testurl();
//        LinkedHashMap<Integer,Object> userArrayMap=new LinkedHashMap<>();
//        LinkedHashMap<Integer,Object> userData=new LinkedHashMap<>();
//        for(int y=0;y<userArray.size();y++){
//            userArrayMap.put(y,userArray.get(y));
//        }
//        userData=(LinkedHashMap)userArrayMap.get(x);
//        String s=(String)userData.get(str);
//        return(s);
//    }


    public void btnPress(int x){
        System.out.println(x);
        System.out.println(contentList.get(x));
        details.removeAll();
        //details.add(content);
        details.add(contentList.get(x));
        details.show();
    }
    public LinkedHashMap getData2(){

        ArrayList userArray;
        userArray=testurl();
        LinkedHashMap<Integer,Object> userArrayLinkedHashMap=new LinkedHashMap<>();
        for(int y=0;y<userArray.size();y++){
            userArrayLinkedHashMap.put(y,userArray.get(y));
        }
        return(userArrayLinkedHashMap);
    }
/*
    public void loginLog(){ //Added 4/12
        ConnectionRequest req2=new ConnectionRequest();
        //req2.setUrl(actualLoginUrl);
        req2.setPost(true);
        //req2.setHttpMethod("GET");
        //req2.addArgument("encoding","json");
        //req2.addArgument("format","json");
        NetworkManager.getInstance().addToQueueAndWait(req2);
    }
*/


    //Login methods written by Ryan Choi

    private void showFacebookUser(String token){
        ConnectionRequest req = new ConnectionRequest();
        req.setPost(false);
        req.setUrl("https://graph.facebook.com/v2.3/me");
        req.addArgumentNoEncoding("access_token", token);
        InfiniteProgress ip = new InfiniteProgress();
        Dialog d = ip.showInifiniteBlocking();
        NetworkManager.getInstance().addToQueueAndWait(req);
        byte[] data = req.getResponseData();
        JSONParser parser = new JSONParser();
        Map map = null;
        try {
            map = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String name = (String) map.get("name");
        d.dispose();
        Form userForm = new UserForm(name, (EncodedImage) theme.getImage("user.png"), "https://graph.facebook.com/v2.3/me/picture?access_token=" + token);
        userForm.show();
    }

    private void showGoogleUser(String token){
        ConnectionRequest req = new ConnectionRequest();
        req.addRequestHeader("Authorization", "Bearer" + token);
        req.setUrl("https://www.googleapis.com/plus/v1/people/me");
        req.setPost(false);
        InfiniteProgress ip = new InfiniteProgress();
        Dialog d = ip.showInifiniteBlocking();
        NetworkManager.getInstance().addToQueueAndWait(req);
        d.dispose();
        byte[] data = req.getResponseData();
        JSONParser parser = new JSONParser();
        Map map = null;
        try {
            map = parser.parseJSON(new InputStreamReader(new ByteArrayInputStream(data), "UTF-8"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        String name = (String) map.get("displayName");
        Map im = (Map) map.get("image");
        String url = (String) im.get("url");
        Form userForm = new UserForm(name, (EncodedImage) theme.getImage("user.png"), url);
        userForm.show();
    }

    //Listener written by Ryan Choi
    public class LoginListener extends LoginCallback {

        public static final int FACEBOOK = 0;

        public static final int GOOGLE = 1;

        private int loginType;

        public LoginListener(int loginType) {
            this.loginType = loginType;
        }

        public void loginSuccessful() {

            try {
                AccessToken token = login.getAccessToken();
                if (loginType == FACEBOOK) {
                    showFacebookUser(token.getToken());
                } else if (loginType == GOOGLE) {
                    showGoogleUser(token.getToken());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void loginFailed(String errorMessage) {
            Dialog.show("Login Failed", errorMessage, "Ok", null);
            loggedIn = false;
        }
    }

    MapContainer.MapObject sydney;
    public void start() {

        try {
            load= Image.createImage("/load.jpg");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        circle = new InfiniteProgress();
        loadingScreen = new Form(BoxLayout.y());
        //loadingScreen.add(new ScaleImageLabel(), CENTER);
        loadingScreen.add(circle);
        loadingScreen.show();


        panel2 = new Container();

        try {
            milton= Image.createImage("/dog.png");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        img= new Label("PIC"){
            @Override
            public void paint(Graphics g) {

                g.drawImage(milton,this.getX(),this.getY(),this.getWidth(),this.getHeight());
                g.drawString("Hales", 10,10);


            }
        };
        userList = new ArrayList<Button>();
        userList2 = new ArrayList<MultiButton>();
        contentList = new ArrayList<TextField>();

        LinkedHashMap<Integer,Object> searchLinkedHashMap = getData2();

        for (int n = 0; n < finddbSize(); n++) {
            int y = n;
            LinkedHashMap<Integer,Object> smallLHM=(LinkedHashMap)searchLinkedHashMap.get(y);
            Button btn = new Button(smallLHM.get("firstName") + " " + smallLHM.get("lastName"));
            MultiButton btn2 = new MultiButton();
            btn2.setTextLine1(smallLHM.get("firstName") + " " + smallLHM.get("lastName"));
            btn2.setTextLine2("B");
            TextField text = new TextField((String)smallLHM.get("content"));
            text.setSingleLineTextArea(false);
            text.setEditable(false);
            btn.addActionListener(ext -> btnPress(y));
            btn2.addActionListener(ext -> btnPress(y));

            //btn.addActionListener(evt -> content1=new TextField(getData(y,"content")));
            userList.add(btn);
            userList2.add(btn2);
            contentList.add(text);
            //Added 4/12~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            usersLoaded++;
            System.out.println("Users loaded: " + usersLoaded);
            System.out.println("Database size: " + finddbSize());
            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
            loading = true;
        }


        if (loading == true){
            //login form written by Ryan Choi
            loginForm = new Form("Login to PetBnB");

            loginForm.setLayout(new GridLayout(3, 1 ));
            Container top = new Container();
            Container bottom = new Container();
            top.setLayout(new GridLayout(3, 1));
            bottom.setLayout(new GridLayout(1, 1));
            search = new Form("Search", BoxLayout.y());
            setBackCommand(search);
            host = new Form("Create your listing");
            host.setLayout(new BorderLayout());
            setBackCommand(host);


            base = new Databasehelpr();


            final TextField username = new TextField();
            username.setHint("Username");
            username.setText(username.getText());

            final TextField pass = new TextField();
            pass.setHint("Password");
            pass.setConstraint(TextField.PASSWORD);

            top.addComponent(username);
            top.addComponent(pass);

            try {
                milton= Image.createImage("/dog.png");
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            hi = new Form("Home", new GridLayout(2,1));
            Button signIn = new Button("Sign In");
            signIn.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    if (username.getText().length() == 0 || pass.getText().length() == 0) {
                        System.out.println("YOU have hit the button");
                        return;
                    }
                    else {
                        loggedIn = true;


                        //Added 4/12~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                        String loginUsername=username.getText();
                        String loginPassword=pass.getText();
                        //actualLoginUrl="http://petbnbactual.ma1geek.org/loginInfo.php?username=" + loginUsername + "&password=" + loginPassword;
                        //loginLog();
                        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                        System.out.println("you have entered home screen");

                        Map<String,String> params = new HashMap<String, String>();
                        params.put("name", username.getText());
                        params.put("id", "1");
                        params.put("class", "math");
                        params.put("email", "tanay_srivastava19@milton.edu");
                        params.put("rating", "8");
                        params.put("username", "slump t");
                        params.put("bio", "bio is fun");
                        params.put("password", pass.getText());
                        params.put("classes", "6");

                        //params.put("password", pass.getText());
                        //System.out.println("1111 " + base.addUser(params));

                        final String[] finalAddress = {""};
                        System.out.println("HELLO HERE IT IS");
                        System.out.println(geocode("1600 Amphitheatre Parkway, Mountain View, CA"));
                        testCoordPositions = new Button("Mark Spot");

                        if (current != null) {
                            current.show();
                            return;
                        }
                        try {
                            mapPic= Image.createImage("/mapPic2.png");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        try {
                            hPetPic= Image.createImage("/hPetPic2.png");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        try {
                            hHomePic= Image.createImage("/hHomePic.png");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        try {
                            listingsPic= Image.createImage("/listingsPic.png");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        try {
                            settingsPic= Image.createImage("/settingsPic.png");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }

                        try {
                            profilePic= Image.createImage("/profilePic.png");
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }



                        //Mapsform written by Tanay Srivastava

                        Form mapsform = new Form("PetBnB Maps");
                        Button addLocation = new Button("Add Pin");
                        mapsform.setLayout(new BorderLayout());
                        final MapContainer cnt = new MapContainer(HTML_API_KEY);
                        cnt.setCameraPosition(new Coord(42.3040009, -71.5040407));//this breaks the code //because the Google map is not loaded yet

                        cnt.addMapListener(new MapListener() {

                            @Override
                            public void mapPositionUpdated(Component source, int zoom, Coord center) {
                                System.out.println("Map position updated: zoom=" + zoom + ", Center=" + center);
                            }

                        });

                        cnt.addLongPressListener(e -> {
                            System.out.println("Long press");
                            ToastBar.showMessage("Received longPress at " + e.getX() + ", " + e.getY(), FontImage.MATERIAL_3D_ROTATION);
                        });
                        cnt.addTapListener(e -> {
                            ToastBar.showMessage("Received tap at " + e.getX() + ", " + e.getY(), FontImage.MATERIAL_3D_ROTATION);
                        });

                        int maxZoom = cnt.getMaxZoom();
                        System.out.println("Max zoom is " + maxZoom);
                        Button btnMoveCamera = new Button("Move Camera");
                        btnMoveCamera.addActionListener(e -> {
                            cnt.setCameraPosition(new Coord(-33.867, 151.206));
                        });
                        Style s = new Style();
                        s.setFgColor(0xff0000);
                        s.setBgTransparency(0);
                        FontImage markerImg = FontImage.createMaterial(FontImage.MATERIAL_PLACE, s, 3);

                        Button btnAddMarker = new Button("Add Marker");
                        btnAddMarker.addActionListener(e -> {

                            cnt.setCameraPosition(new Coord(41.889, -87.622));
                            cnt.addMarker(EncodedImage.createFromImage(markerImg, false), cnt.getCameraPosition(), "Hi marker", "Optional long description", new ActionListener() {
                                public void actionPerformed(ActionEvent evt) {
                                    System.out.println("Bounding box is " + cnt.getBoundingBox());
                                    ToastBar.showMessage("You clicked the marker", FontImage.MATERIAL_PLACE);
                                }
                            });

                        });

                        Button btnAddPath = new Button("Add Path");
                        btnAddPath.addActionListener(e -> {

                            cnt.addPath(
                                    cnt.getCameraPosition(),
                                    new Coord(-33.866, 151.195), // Sydney
                                    new Coord(-18.142, 178.431),  // Fiji
                                    new Coord(21.291, -157.821),  // Hawaii
                                    new Coord(37.423, -122.091)  // Mountain View
                            );
                        });


                        Button panTo = new Button("Search Bnb");
                        testCoordPositions.addActionListener(e ->
                        {
                            Coord c1 = new Coord(cnt.getCameraPosition());
                            Coord c2 = new Coord(coordinates);

                            Coord center = new Coord(c1.getLatitude() / 2 + c2.getLatitude() / 2, c1.getLongitude() / 2 + c2.getLongitude() / 2);



                            float zoom = cnt.getZoom();

                            boolean[] finished = new boolean[1];
                            cnt.addMapListener(new MapListener() {

                                @Override
                                public void mapPositionUpdated(Component source, int zoom, Coord c) {

                                    if (Math.abs(c.getLatitude() - center.getLatitude()) > .001 || Math.abs(c.getLongitude() - center.getLongitude()) > .001) {
                                        return;
                                    }
                                    finished[0] = true;
                                    synchronized (finished) {
                                        final MapListener fthis = this;
                                        Display.getInstance().callSerially(() -> {
                                            cnt.removeMapListener(fthis);
                                        });
                                        finished.notify();
                                    }

                                }

                            });
                            cnt.zoom(center, (int) zoom);
                            while (!finished[0]) {
                                Display.getInstance().invokeAndBlock(() -> {
                                    while (!finished[0]) {
                                        Util.wait(finished, 100);
                                    }
                                });
                            }


                            BoundingBox box = cnt.getBoundingBox();
                            if (!box.contains(c1) || !box.contains(c2)) {
                                while (!box.contains(c1) || !box.contains(c2)) {
                                    if (!box.contains(c1)) {
                                        System.out.println("Box " + box + " doesn't contain " + c1);
                                    }
                                    if (!box.contains(c1)) {
                                        System.out.println("Box " + box + " doesn't contain " + c2);
                                    }
                                    zoom -= 1;
                                    final boolean[] done = new boolean[1];

                                    final int fzoom = (int) zoom;
                                    cnt.addMapListener(new MapListener() {

                                        @Override
                                        public void mapPositionUpdated(Component source, int zm, Coord center) {

                                            if (zm == fzoom) {
                                                final MapListener fthis = this;
                                                Display.getInstance().callSerially(() -> {
                                                    cnt.removeMapListener(fthis);
                                                });

                                                done[0] = true;
                                                synchronized (done) {
                                                    done.notify();
                                                }
                                            }
                                        }

                                    });
                                    cnt.zoom(center, (int) zoom);
                                    while (!done[0]) {
                                        Display.getInstance().invokeAndBlock(() -> {
                                            while (!done[0]) {
                                                Util.wait(done, 100);
                                            }
                                        });
                                    }
                                    box = cnt.getBoundingBox();
                                    System.out.println("Zoom now " + zoom);

                                }
                            } else if (box.contains(c1) && box.contains(c2)) {
                                while (box.contains(c1) && box.contains(c2)) {
                                    zoom += 1;
                                    final boolean[] done = new boolean[1];

                                    final int fzoom = (int) zoom;
                                    cnt.addMapListener(new MapListener() {
                                        public void mapPositionUpdated(Component source, int zm, Coord center) {
                                            if (zm == fzoom) {
                                                final MapListener fthis = this;
                                                Display.getInstance().callSerially(() -> {
                                                    cnt.removeMapListener(fthis);
                                                });
                                                done[0] = true;
                                                synchronized (done) {
                                                    done.notify();
                                                }
                                            }
                                        }
                                    });
                                    cnt.zoom(center, (int) zoom);
                                    while (!done[0]) {
                                        Display.getInstance().invokeAndBlock(() -> {
                                            while (!done[0]) {
                                                Util.wait(done, 100);
                                            }
                                        });
                                    }
                                    box = cnt.getBoundingBox();

                                }
                                zoom -= 1;
                                cnt.zoom(center, (int) zoom);
                                cnt.addTapListener(null);
                            }

                        });
                        Button toggleTopMargin = $(new Button("Toggle Margin"))
                                .addActionListener(e -> {
                                    int marginTop = $(cnt).getStyle().getMarginTop();
                                    if (marginTop < Display.getInstance().getDisplayHeight() / 3) {
                                        $(cnt).selectAllStyles().setMargin(Display.getInstance().getDisplayHeight() / 3, 0, 0, 0);
                                    } else {
                                        $(cnt).selectAllStyles().setMargin(0, 0, 0, 0);
                                    }
                                    $(cnt).getComponentForm().revalidate();
                                })
                                .asComponent(Button.class);


                        Button btnClearAll = new Button("Clear All");
                        btnClearAll.addActionListener(e -> {
                            cnt.clearMapLayers();
                        });

                        MapContainer.MapObject mo = cnt.addMarker(EncodedImage.createFromImage(markerImg, false), new Coord(-33.866, 151.195), "test", "test", e -> {
                            System.out.println("Marker clicked");
                            cnt.removeMapObject(sydney);
                        });
                        sydney = mo;
                        System.out.println("MO is " + mo);
                        mo = cnt.addMarker(EncodedImage.createFromImage(markerImg, false), new Coord(-18.142, 178.431), "test", "test", e -> {
                            System.out.println("Marker clicked");
                        });
                        System.out.println("MO is " + mo);
                        cnt.addTapListener(e -> {
                            if (tapDisabled) {
                                return;
                            }
                            tapDisabled = true;
                            TextField enterName = new TextField();
                            Container wrapper = BoxLayout.encloseY(new Label("Name:"), enterName);
                            InteractionDialog dlg = new InteractionDialog("Add Marker");
                            dlg.getContentPane().add(wrapper);
                            enterName.setDoneListener(e2 -> {
                                String txt = enterName.getText();
                                cnt.addMarker(EncodedImage.createFromImage(markerImg, false), cnt.getCoordAtPosition(e.getX(), e.getY()), enterName.getText(), "", e3 -> {
                                    ToastBar.showMessage("You clicked " + txt, FontImage.MATERIAL_PLACE);
                                });
                                dlg.dispose();
                                tapDisabled = false;
                            });
                            dlg.showPopupDialog(new Rectangle(e.getX(), e.getY(), 10, 100));
                            enterName.startEditingAsync();
                        });


// END TANAY MAPS


                        //CREATE AC TEXTFIELD:

                        if (HTML_API_KEY == null) {
                            mapsform.add(new SpanLabel("This demo requires a valid google API key to be set in the constant apiKey, "
                                    + "you can get this key for the webservice (not the native key) by following the instructions here: "
                                    + "https://developers.google.com/places/web-service/get-api-key"));
                            mapsform.getToolbar().addCommandToRightBar("Get Key", null, e -> Display.getInstance().execute("https://developers.google.com/places/web-service/get-api-key"));
                            mapsform.show();
                            return;
                        }
                        final DefaultListModel<String> options = new DefaultListModel<>();
                        AutoCompleteTextField ac = new AutoCompleteTextField(options) {
                            @Override
                            protected boolean filter(String text) {
                                if (text.length() == 0) {
                                    return false;
                                }
                                String[] l = searchLocations(text);
                                if (l == null || l.length == 0) {
                                    return false;
                                }

                                options.removeAll();
                                for (String s : l) {
                                    options.addItem(s);
                                }
                                return true;
                            }

                        };

                        ac.setMinimumElementsShownInPopup(5);
                        Style so = UIManager.getInstance().getComponentStyle("TitleCommand");
                        //FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_SEARCH, so);
                        Form placesAC = new Form("Search BnB", new BoxLayout(BoxLayout.Y_AXIS));
                        //  hi.getToolbar().addCommandToRightBar("", icon, (e) -> placesAC.show());
                        String getAddress = new String();
                        getAddress = ac.getText();
                        System.out.println(getAddress);

                        addLocation.addActionListener(e -> {

                            TextField result = new TextField("Results:");
                            result.setEditable(false);
                            placesAC.addComponent(result);
                            SpanLabel result1 = new SpanLabel("50 The Ledgeway, Milton MA. Owner: Truman");
                            placesAC.addComponent(result1);
                            SpanLabel result2 = new SpanLabel("85 University Ave, Westwood MA. Owner: Ryan");
                            placesAC.addComponent(result2);
                            SpanLabel result3 = new SpanLabel("37 Meadow Lane, Southborough MA. Owner: Tanay");
                            placesAC.addComponent(result3);

                            finalAddress[0] = ac.getText();
                            final String finalAddressTrue = finalAddress[0];
                            coordinates = geocode(finalAddressTrue);
                            Double lng = coordinates.getLongitude();
                            Double lat = coordinates.getLatitude();
                            String formattedLng = lng.toString();
                            String formattedLat = lat.toString();
                            EncodedImage encImg = EncodedImage.createFromImage(markerImg, false);
                            cnt.addMarker(encImg, coordinates, "", "", null);




                        });

                        panTo.addActionListener(ent -> {
                            placesAC.show();

                        });


                        placesAC.add(ac);
                        placesAC.add(addLocation);

                        Button Back = $(new Button("Back"))
                                .addActionListener(e2 -> {
                                    mapsform.showBack();
                                })
                                .asComponent(Button.class);

                        placesAC.add(Back);


                        //END AC TEXTFIELD





                        Container root = LayeredLayout.encloseIn(
                                BorderLayout.center(cnt),
                                BorderLayout.south(
                                        FlowLayout.encloseBottom(panTo, testCoordPositions, toggleTopMargin, btnMoveCamera, btnAddMarker, btnAddPath, btnClearAll)
                                )
                        );

                        mapsform.add(BorderLayout.CENTER, root);
                        setBackCommand(mapsform);

                        //End of Tanay Maps


                        //Form searchLoc written by Truman Marshall
                        Form searchLoc = new Form("Search Locations");
                        searchLoc.setLayout(new BorderLayout());

                        System.out.println("Read...");

                        //GoogleMapsTestApp e = new GoogleMapsTestApp();

//                        Container sideLabel = new Container();
//                        sideLabel.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
//
//
//                        Label listingname = new Label("Listing Name");
//                        sideLabel.add(listingname);


                        //host.add(sideLabel, BorderLayout.EAST);







                        Form petButtonf1 = new Form("Payment", BoxLayout.y());


                        firstn =  new TextField();
                        lastn = new TextField();
                        ad1 = new TextField();
                        ad2 = new TextField();
                        zip = new TextField();
                        city = new TextField();
                        state1 = new TextField();
                        country = new TextField();

                        submit =  new Button("submit");
                        submit.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                Purchase.startOrder(new Purchase.Callback() {
                                    public String fetchToken() {
                                        // this method needs to return the token from the Brain tree server API.
                                        // You need to use this code to connect to your server or return the data
                                        // from a previous connection that fetched the token
                                        return "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiI4NTZhZjk0NjY0NjJjNWY1OTM3NjhjM2E0MjNkOTk5MzViMTQ3YzM0MzBjYzRiMjgzMzY3NTFhMmNiMGM4ZjJmfGNyZWF0ZWRfYXQ9MjAxOC0wNS0wNFQxMjo1MDo1NC40MTMzOTc3MDIrMDAwMFx1MDAyNm1lcmNoYW50X2lkPWtwcHhmOWN4ZmtiYjhtM2tcdTAwMjZwdWJsaWNfa2V5PWN3d3lybnByMzJxcnRjOWoiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMva3BweGY5Y3hma2JiOG0zay9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzL2twcHhmOWN4ZmtiYjhtM2svY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tL2twcHhmOWN4ZmtiYjhtM2sifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiUGV0Qm5CIiwiY2xpZW50SWQiOm51bGwsInByaXZhY3lVcmwiOiJodHRwOi8vZXhhbXBsZS5jb20vcHAiLCJ1c2VyQWdyZWVtZW50VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3RvcyIsImJhc2VVcmwiOiJodHRwczovL2Fzc2V0cy5icmFpbnRyZWVnYXRld2F5LmNvbSIsImFzc2V0c1VybCI6Imh0dHBzOi8vY2hlY2tvdXQucGF5cGFsLmNvbSIsImRpcmVjdEJhc2VVcmwiOm51bGwsImFsbG93SHR0cCI6dHJ1ZSwiZW52aXJvbm1lbnROb05ldHdvcmsiOnRydWUsImVudmlyb25tZW50Ijoib2ZmbGluZSIsInVudmV0dGVkTWVyY2hhbnQiOmZhbHNlLCJicmFpbnRyZWVDbGllbnRJZCI6Im1hc3RlcmNsaWVudDMiLCJiaWxsaW5nQWdyZWVtZW50c0VuYWJsZWQiOnRydWUsIm1lcmNoYW50QWNjb3VudElkIjoicGV0Ym5iIiwiY3VycmVuY3lJc29Db2RlIjoiVVNEIn0sIm1lcmNoYW50SWQiOiJrcHB4ZjljeGZrYmI4bTNrIiwidmVubW8iOiJvZmYifQ==";
                                    }

                                    public void onPurchaseSuccess(String nonce) {
                                        // this is a callback that will be invoked when the purchase succeeds
                                        //write a code segment that can accurately explain that payment is successful
                                    }

                                    public void onPurchaseFail(String errorMessage) {
                                        // this is a callback that will be invoked when the purchase fails
                                    }

                                    public void onPurchaseCancel() {
                                        // this is a callback that will be invoked when the purchase is canceled
                                    }
                                });
                            }
                        });

                        panel2.setLayout(new GridLayout(11, 2));
                        first = new Label("First Name");
                        last = new Label("Last Name");
                        ad = new Label("Address One");
                        ads = new Label("Address Two");
                        z = new Label("Zipcode ");
                        ct = new Label("City");
                        st = new Label("State");
                        country1 = new Label("Country");
                        //put labels and textfields on main page
                        panel2.add(first);
                        panel2.add(firstn);
                        panel2.add(last);
                        panel2.add(lastn);
                        panel2.add(ad);
                        panel2.add(ad1);
                        panel2.add(ads);
                        panel2.add(ad2);
                        panel2.add(z);
                        panel2.add(zip);
                        panel2.add(ct);
                        panel2.add(city);
                        panel2.add(st);
                        panel2.add(state1);
                        panel2.add(country1);
                        panel2.add(country);
                        panel2.add(submit);



                        petButtonf1.add(panel2);
                        setBackCommand(petButtonf1);

//                    Button payment = new Button("Payment");
//                    payment.addActionListener(new ActionListener() {
//                        @Override
//                        public void actionPerformed(ActionEvent evt) {
//
//                            //pay1.show();
//
//
//                        }
//                    });





                        //    Button rb1 = new Button("Listing 1");
                        Button rb1= new Button("Listing 1");

                        Button rb2 = new Button("Listing 2");


                        //Listing button #1 button press
                        rb1.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                listing.show();
                            }

                        });

                        rb2.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                listing.show();
                            }
                        });


                        listingpage = new Form("Your Listings", BoxLayout.y());
                        setBackCommand(listingpage);
                        listingpage.add(rb1);
                        listingpage.add(rb2);



//                    Button b1 = new Button("WARNING: MUST READ");
//                    b1.addActionListener(ert -> Dialog.show("Warning!“, ","If Pet dies, it is not Bnb owner's responsibility", "Ok", "Cancel"));



                        //Create Form1 and Form2 and set a Back Command to navigate back to the home Form
                        Form form1 = new Form("Google Maps Search");
                        Container container1;
                        setBackCommand(form1);
                        Form form2 = new Form("Help");
                        setBackCommand(form2);
                        listing = new Form("listing 1");
                        listing.setLayout(BoxLayout.y());
                        setBackCommand(listing);
                        setBackCommand(form1);
                        listing = new Form("Listing 2");
                        listing.setLayout(BoxLayout.y());
                        setBackCommand(listing);
                        Label nameuser = new Label("Tanay Srivastava");
                        Label deets = new Label("Details:");



                        Form aboutstuff = new Form("", BoxLayout.y());
                        Tabs t = new Tabs();
                        Style s1 = UIManager.getInstance().getComponentStyle("Button");
//                    FontImage radioEmptyImage = FontImage.createMaterial(FontImage.MATERIAL_RADIO_BUTTON_UNCHECKED, s1);
//                    FontImage radioFullImage = FontImage.createMaterial(FontImage.MATERIAL_RADIO_BUTTON_CHECKED, s1);
//                    ((DefaultLookAndFeel)UIManager.getInstance().getLookAndFeel()).setRadioButtonImages(radioFullImage, radioEmptyImage, radioFullImage, radioEmptyImage);

                        TextField address = new TextField("Address: 37 Meadow Lane, Southborough MA");

                        TextField about = new TextField("Has 1 pet");
                        TextField about1 = new TextField("Non-smoking household");
                        TextField about2 = new TextField("2 children");
                        TextField about3 = new TextField("Lives in House");
                        TextField about4 = new TextField("Price: $35/day");
                        address.setEditable(false);
                        about.setEditable(false);
                        about1.setEditable(false);
                        about2.setEditable(false);
                        about3.setEditable(false);
                        about4.setEditable(false);


                        container1 = BoxLayout.encloseY(about, about1, about2, about3, about4);
                        t.addTab("About", container1);
                        t.addTab("Reviews", new SpanLabel("Love the neighborhood! The homeowners are very friendly!"));
                        RadioButton firstTab = new RadioButton("");
                        RadioButton secondTab = new RadioButton("");
                        firstTab.setUIID("Container");
                        secondTab.setUIID("Container");
                        new ButtonGroup(firstTab, secondTab);
                        firstTab.setSelected(true);
                        Container tabsFlow = FlowLayout.encloseCenter(firstTab, secondTab);


                        aboutstuff.add(deets);
                        aboutstuff.add(t);
                        aboutstuff.add(BorderLayout.north(tabsFlow));
                        listing.add(nameuser);
                        listing.add(aboutstuff);








                        //Tab listener
                        t.addSelectionListener((i1, i2) -> {
                            switch(i2) {
                                case 0:
                                    if(!firstTab.isSelected()) {
                                        firstTab.setSelected(true);
                                    }
                                    break;
                                case 1:
                                    if(!secondTab.isSelected()) {
                                        secondTab.setSelected(true);
                                    }
                                    break;
                            }
                        });

//                    NavigationCommand homeCommand = new NavigationCommand("Home");
//                    homeCommand.setNextForm(home);
//                    home.getToolbar().addCommandToSideMenu(homeCommand);
//
//                    NavigationCommand cmd1 = new NavigationCommand("Search For Stay");
//                    cmd1.setNextForm(mapsform);
//                    home.getToolbar().addCommandToSideMenu(cmd1);
//
//                    NavigationCommand cmd3 = new NavigationCommand("Help");
//                    setBackCommand(form2);
//                    home.getToolbar().addCommandToSideMenu(cmd3);


//
//                    //Add Edit, Add and Delete Commands to the home Form context Menu, written by Truman Marshall
//                    Image im = FontImage.createMaterial(FontImage.MATERIAL_MODE_EDIT, UIManager.getInstance().getComponentStyle("Command"));
//                    Command edit = new Command("Edit", im) {
//
//                        @Override
//                        public void actionPerformed(ActionEvent evt) {
//                            System.out.println("Editing");
//
//                        }
//                    };
//                    home.getToolbar().addCommandToOverflowMenu(edit);
//
//                    im = FontImage.createMaterial(FontImage.MATERIAL_LIBRARY_ADD, UIManager.getInstance().getComponentStyle("Command"));
//                    Command add = new Command("Add", im) {
//
//                        @Override
//                        public void actionPerformed(ActionEvent evt) {
//                            System.out.println("Adding");
//                        }
//                    };
//                    home.getToolbar().addCommandToOverflowMenu(add);
//
//                    im = FontImage.createMaterial(FontImage.MATERIAL_DELETE, UIManager.getInstance().getComponentStyle("Command"));
//                    Command delete = new Command("Delete", im) {
//
//                        @Override
//                        public void actionPerformed(ActionEvent evt) {
//                            System.out.println("Deleting");
//                        }
//
//                    };
//                    home.getToolbar().addCommandToOverflowMenu(delete);




                        hi = new Form("Home", new GridLayout(2,1));



//                    topBar.setUIID("SideCommand");
//                    tb.addComponentToSideMenu(topBar);
                        //  hi.getToolbar().setBackCommand("", e -> next.showBack());
                        // tb.addCommandToRightBar(hi.getBackCommand());

//                    tb.addMaterialCommandToSideMenu("Home", FontImage.MATERIAL_HOME, e -> {});
//                    tb.addMaterialCommandToSideMenu("Website", FontImage.MATERIAL_WEB, e -> {});
//                    tb.addMaterialCommandToSideMenu("Settings", FontImage.MATERIAL_SETTINGS, e -> {});
//                    tb.addMaterialCommandToSideMenu("About", FontImage.MATERIAL_INFO, e -> {});




                        Container c = new Container(new FlowLayout());
                        Container top = new Container( new BorderLayout());
                        Container toolBar = new Container (new FlowLayout());
                        Container have = new Container (new GridLayout(1,2));
                        Container buttonset = new Container(new GridLayout(2,2));


                        Button hPet = new Button ("hPet") {public void paint(Graphics g) {

                            g.drawImage(hPetPic,this.getX(),this.getY(),this.getWidth(),this.getHeight());

                        }};

                        Button hHome = new Button(){public void paint(Graphics g) {
                            g.drawImage(hHomePic,this.getX(),this.getY(),this.getWidth(),this.getHeight());

                        }};

                        Button listings = new Button ("listings", theme.getImage("switch-on.png")){public void paint(Graphics g) {
                            g.drawImage(listingsPic,this.getX(),this.getY(),this.getWidth(),this.getHeight());

                        }};


                        Button searchU = new Button ("Search For Users"){public void paint(Graphics g) {
                            g.drawImage(profilePic,this.getX(),this.getY(),this.getWidth(),this.getHeight());

                        }};

                        Button map = new Button ("Map") {public void paint(Graphics g) {
                            g.drawImage(mapPic,this.getX(),this.getY(),this.getWidth(),this.getHeight());

                        }};

                        Button settings = new Button ("Settings"){public void paint(Graphics g) {
                            g.drawImage(settingsPic,this.getX(),this.getY(),this.getWidth(),this.getHeight());

                        }};

                        have.add(hPet).add(hHome);

                        top.addComponent(BorderLayout.CENTER, have);


                        //c.addComponent(BorderLayout.NORTH, new Button("2"));

                        buttonset.add(listings).
                                add(searchU).
                                add(map).
                                add(settings);

                        hi.add(top).add(buttonset);






                        // here is where we add actionlisteners to go to the corresponding pages such as listings, listing details, or maps


                        hHome.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                host.show();
                            }
                        });



                        hPet.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent evt) {

                                //petButtonf1.show();
                                mapsform.show();
                            }

                        });

                        hHome.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent evt) {



                            }});

                        settings.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent evt) {


                            }});

                        map.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent evt) {

                                mapsform.show();



                            }});

                        searchU.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                searchU.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent evt) {
                                        //Modified 4/12~~~~~~~~~~~~~~~~~~~~~~~
                                        if(usersLoaded<finddbSize()){

                                            InteractionDialog loading = new InteractionDialog("Loading");
                                            BorderLayout border = new BorderLayout();
                                            FlowLayout flow = new FlowLayout();
                                            loading.setLayout(border);
                                            Container dialogContainer = new Container();
                                            dialogContainer.setLayout(flow);
                                            Label one = new Label("Please wait for");
                                            Label two = new Label("" + (finddbSize()-usersLoaded));
                                            Label three = new Label("more users to load.");
                                            dialogContainer.add(one);
                                            dialogContainer.add(two);
                                            dialogContainer.add(three);
                                            Button close = new Button("Close");
                                            close.addActionListener((ee) -> loading.dispose());
                                            loading.addComponent(BorderLayout.CENTER, dialogContainer);
                                            loading.addComponent(BorderLayout.SOUTH, close);
                                            Dimension pre = loading.getContentPane().getPreferredSize();
                                            int height=Display.getInstance().getDisplayHeight()/4;
                                            int width=Display.getInstance().getDisplayWidth()/4;
                                            loading.show(height, height, width, width); //Pixels off the top, pixels off the bottom, pixels off the left, pixels off the right
                                        }
                                        else{
                                            search.show();
                                        }

                                        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                                    }
                                });


                            }});

                        listings.addActionListener(new ActionListener() {

                            @Override
                            public void actionPerformed(ActionEvent evt) {
                                listingpage.show();

                            }});



// span label accepts the text and the UIID for the dialog body
                        //dlg.add(new SpanLabel("Dialog Body text", "DialogBody"));


                        //Search Method written by Truman Marshall


                        Toolbar.setGlobalToolbar(true);



                        //previous place for loading users


                        details = new Form("Info");
                        details.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
                        setBackCommand(details);

                        //hi.add(new InfiniteProgress());
                        Display.getInstance().scheduleBackgroundTask(() -> {
                            Display.getInstance().callSerially(() -> {
                /*
                for(int x=0;x<userList.size();x++)
                {
                    search.addComponent(userList.get(x));
                }
                */
                                for (int y = 0; y < userList2.size(); y++) {
                                    search.addComponent(userList2.get(y));
                                }
                                search.revalidate();
                            });
                        });
                        search.getToolbar().addSearchCommand(i -> {
                            String text = (String) i.getSource();
                            if (text == null || text.length() == 0) {
                                // clear search
                                for (Component cmp : search.getContentPane()) {
                                    cmp.setHidden(false);
                                    cmp.setVisible(true);
                                }
                                search.getContentPane().animateLayout(150);
                            } else {
                                text = text.toLowerCase();
                                for (Component cmp : search.getContentPane()) {
                                    MultiButton mb = (MultiButton) cmp;
                                    String line1 = mb.getTextLine1();
                                    String line2 = mb.getTextLine2();
                                    boolean show = line1 != null && line1.toLowerCase().indexOf(text) > -1 ||
                                            line2 != null && line2.toLowerCase().indexOf(text) > -1;
                                    mb.setHidden(!show);
                                    mb.setVisible(show);
                                }
                                search.getContentPane().animateLayout(150);
                            }
                        }, 4);
                        System.out.println("asdjkfhasdkjfhaskjdfhkjsd1213123123");
                        hi.show();

                    }


                }
            });
            top.addComponent(signIn);

            try {
                milton= Image.createImage("/dog.png");
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            loginDog= new Label("logindog"){
                @Override
                public void paint(Graphics g) {
                    // red color
                    //  g.setColor(0xff0000);
                    g.drawImage(milton,this.getX(),this.getY(),this.getWidth(),this.getHeight());


                    // paint the screen in red


                    // draw hi world in white text at the top left corner of the screen
                    //  g.setColor(0xFF9933);
                    if(x>0) {
                        g.drawString(team[0].first, (this.getWidth() / 2) - 20, getY() + this.getHeight() / 2);
                    }
                }
            };
            bottom.addComponent(loginDog);

            loginForm.addComponent(top);
            loginForm.addComponent(bottom);

            Container bottom1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            Button loginWFace = new Button("LOGIN WITH FACEBOOK");
            loginWFace.setUIID("LoginButton");
            loginWFace.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {

                    //create your own app identity on facebook follow the guide here:
                    //facebook-login.html
                    String clientId = "1171134366245722";
                    String redirectURI = "http://www.codenameone.com/";
                    String clientSecret = "";

                    if(clientSecret.length() == 0){
                        System.err.println("create your own facebook app follow the guide here:");
                        System.err.println("http://www.codenameone.com/facebook-login.html");
                        return;
                    }


                    Login fb = FacebookConnect.getInstance();
                    fb.setClientId(clientId);
                    fb.setRedirectURI(redirectURI);
                    fb.setClientSecret(clientSecret);
                    login = fb;
                    fb.setCallback(new PetBnBApp.LoginListener(PetBnBApp.LoginListener.FACEBOOK));
                    if(!fb.isUserLoggedIn()){
                        fb.doLogin();
                    }else{
                        showFacebookUser(fb.getAccessToken().getToken());
                    }

                }
            });
            Button loginWG = new Button("LOGIN WITH GOOGLE");
            loginWG.setUIID("LoginButton");
            loginWG.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    //create your own google project follow the guide here:
                    //http://www.codenameone.com/google-login.html
                    String clientId = "839004709667-n9el6dup3gono67vhi5nd0dm89vplrka.apps.googleusercontent.com";
                    String redirectURI = "https://www.codenameone.com/oauth2callback";
                    String clientSecret = "";

                    if(clientSecret.length() == 0){
                        System.err.println("create your own google project follow the guide here:");
                        System.err.println("http://www.codenameone.com/google-login.html");
                        return;
                    }

                    Login gc = GoogleConnect.getInstance();
                    gc.setClientId(clientId);
                    gc.setRedirectURI(redirectURI);
                    gc.setClientSecret(clientSecret);
                    login = gc;
                    gc.setCallback(new PetBnBApp.LoginListener(PetBnBApp.LoginListener.GOOGLE));
                    if(!gc.isUserLoggedIn()){
                        gc.doLogin();
                    }else{
                        showGoogleUser(gc.getAccessToken().getToken());
                    }
                }
            });
            //bottom.addComponent(loginWFace);
            //bottom.addComponent(loginWG);

            loginForm.show();


            //makeForm();
            //loading.show();




        }







    }


    //Back commands written by Truman Marshall
    protected void setBackCommand(Form f) {

        Command backHome = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                hi.showBack();
            }

        };
        Command backSearch = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                search.showBack();
            }
        };
        Command backMaps = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                hi.showBack();
            }
        };
        Image img = FontImage.createMaterial(FontImage.MATERIAL_ARROW_BACK, UIManager.getInstance().getComponentStyle("TitleCommand"));
        backHome.setIcon(img);
        backSearch.setIcon(img);
        backMaps.setIcon(img);
        f.getToolbar().setTitleCentered(true);
        if(f==details)
        {
            f.getToolbar().addCommandToLeftBar(backSearch);
            f.setBackCommand(backSearch);
        }
        else
        {
            f.getToolbar().addCommandToLeftBar(backHome);
            f.setBackCommand(backHome);
        }
    }

    boolean tapDisabled = false;

    public void stop() {

        current = Display.getInstance().getCurrent();
    }

    public void destroy() {
    }

}