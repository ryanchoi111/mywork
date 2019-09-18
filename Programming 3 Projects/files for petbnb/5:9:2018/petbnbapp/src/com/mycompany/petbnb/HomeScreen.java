package com.mycompany.petbnb;

import com.codename1.components.InfiniteProgress;
import com.codename1.db.Database;
import com.codename1.io.*;
import com.codename1.properties.SQLMap;
import com.codename1.properties.UiBinding;
import com.codename1.social.FacebookConnect;
import com.codename1.social.GoogleConnect;
import com.codename1.social.Login;
import com.codename1.social.LoginCallback;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

public class HomeScreen {

    private Form loginForm;

    private Resources theme;
    private Database db;
    SQLMap sm;
    UiBinding uib = new UiBinding();

    private Login login;


    public void init(Object context) {

        try {
            theme = Resources.openLayered("/theme");
            UIManager.getInstance().setThemeProps(theme.getTheme(theme.getThemeResourceNames()[0]));
//            c.name.set(“Ryan”);
//            c.email.set(“seokmin_choi19@milton.edu”);
//            c.phone.set(“617-678-3248");
//            c.rank.set(1);
//            db = Display.getInstance().openOrCreate(“propertiesdemo.db”);
//            sm = SQLMap.create(db);
//            sm.setPrimaryKeyAutoIncrement(c, c.id);
//            sm.insert(c);
//
//            Storage.getInstance().writeObject(“MyContact”, c);
//
//
//            Contact readContact = (Contact)Storage.getInstance().readObject(“MyContact”);
//
//
//
//
//
//
//
//            Log.p(“My name is: ” + c.name.get());





        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() {


        loginForm = new Form("Sign in Demo");
        loginForm.setLayout(new BorderLayout());
        Container center = new Container(new BoxLayout(BoxLayout.Y_AXIS)) ;
        center.setUIID("ContainerWithPadding");

        // Image logo = theme.getImage(“Codename0ne.png”);
        //Label l = new Label(logo);
        Container flow = new Container(new FlowLayout(Component.CENTER));
        //flow.addComponent(l);
        //center.addComponent(flow);



        final TextField username = new TextField();
        username.setHint("Username");
        username.setText(username.getText());





        final TextField pass = new TextField();
        pass.setHint("Password");
        pass.setConstraint(TextField.PASSWORD);
        //System.out.println(mainc.pw.get());





        center.addComponent(username);
        center.addComponent(pass);

        Button signIn = new Button("Sign In");
        signIn.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                if (username.getText().length() == 0 || pass.getText().length() == 0) {
                    return;
                }
                //UserForm userForm = new UserForm(username.getText(), null, null);
                //userForm.show();
                //HomeScreen home = new HomeScreen();
                //home.start();
            }
        });
        center.addComponent(signIn);
        loginForm.addComponent(BorderLayout.CENTER, center);

        Container bottom = new Container(new BoxLayout(BoxLayout.Y_AXIS));
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
                fb.setCallback(new LoginListener(LoginListener.FACEBOOK));
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
                gc.setCallback(new LoginListener(LoginListener.GOOGLE));
                if(!gc.isUserLoggedIn()){
                    gc.doLogin();
                }else{
                    showGoogleUser(gc.getAccessToken().getToken());
                }
            }
        });

        bottom.addComponent(loginWFace);
        bottom.addComponent(loginWG);

        loginForm.addComponent(BorderLayout.SOUTH, bottom);

        loginForm.show();
    }

    public void destroy() {
    }

    public void stop() {
    }

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
        }
    }
}
