package com.mycompany.petbnb;


import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import com.pubnub.api.*;

public class Pubnub {
    Form current;
    Resources theme;
    Pubnub pubnub;
    String uuid;
    String auth_key;
    java.lang.String filter;

    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");


        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature, uncomment if you have a pro subscription
        // Log.bindCrashProtection(true);

    }

    public void start() {
        if (current != null) {
            current.show();
            return;
        }


        pubnub = new Pubnub();
        pubnub.Pubnub("pub-c-ec1d8145-7256-43b2-8573-1e7602dc263d", "sub-c-0a7cb448-0d5d-11e8-92ea-7a3d09c63f1f", "", "", true);
        pubnub.Pubnub("pub-c-ec1d8145-7256-43b2-8573-1e7602dc263d", "sub-c-0a7cb448-0d5d-11e8-92ea-7a3d09c63f1f");
        String uuid = "Stephen";
        pubnub.setUUID(uuid);




    }

    public void Pubnub (String publish_key, String subscribe_key, String secret_key,boolean ssl ){
        Pubnub("pub-c-ec1d8145-7256-43b2-8573-1e7602dc263d", "sub-c-0a7cb448-0d5d-11e8-92ea-7a3d09c63f1f", "sec-c-OWNkMWJjZjUtN2Y2Zi00MDdlLTgwZjktN2JhN2NlNTdjZDZl", true);


    }

    public void Pubnub(String publish_key, String subscribe_key){
        Pubnub("pub-c-ec1d8145-7256-43b2-8573-1e7602dc263d", "sub-c-0a7cb448-0d5d-11e8-92ea-7a3d09c63f1f");
    }

    public void Pubnub(String publish_key, String subscribe_key, boolean ssl){
        Pubnub("pub-c-ec1d8145-7256-43b2-8573-1e7602dc263d", "sub-c-0a7cb448-0d5d-11e8-92ea-7a3d09c63f1f", true);
    }

    public void Pubnub(String publish_key, String subscribe_key, String secret_key){
        Pubnub("pub-c-ec1d8145-7256-43b2-8573-1e7602dc263d", "sub-c-0a7cb448-0d5d-11e8-92ea-7a3d09c63f1f","sec-c-OWNkMWJjZjUtN2Y2Zi00MDdlLTgwZjktN2JhN2NlNTdjZDZl");
    }

    public void Pubnub(String publish_key, String subscribe_key, String secret_key, String cipher_key, boolean ssl){
        Pubnub("pub-c-ec1d8145-7256-43b2-8573-1e7602dc263d", "sub-c-0a7cb448-0d5d-11e8-92ea-7a3d09c63f1f","sec-c-OWNkMWJjZjUtN2Y2Zi00MDdlLTgwZjktN2JhN2NlNTdjZDZl", "",true);
    }

    public void Pubnub(String publish_key, String subscribe_key, String secret_key, String cipher_key, boolean ssl, String iv){
        Pubnub("pub-c-ec1d8145-7256-43b2-8573-1e7602dc263d", "sub-c-0a7cb448-0d5d-11e8-92ea-7a3d09c63f1f","sec-c-OWNkMWJjZjUtN2Y2Zi00MDdlLTgwZjktN2JhN2NlNTdjZDZl", "",true, "");
    }


    public void setUUID(String uuid){
        this.uuid = uuid;
    }

    public String getUUID(){
        return uuid;
    }

    public String uuid(){
        return getUUID();
    }


    Callback callback = new Callback() {
        public void successCallback(String channel, Object response) {
            System.out.println(response.toString());
        }
        public void errorCallback(String channel, PubnubError error) {
            System.out.println(error.toString());
        }
    };








    public void setAuthKey(String auth_key){
        this.auth_key = auth_key;
    }

    public String getAuthKey(){
        return auth_key;
    }

    public void unsetAuthKey(){
        this.auth_key = "";
    }


    public void setResumeOnReconnect(boolean resumeOnReconnect){
        if(resumeOnReconnect == true){
            //pubnub catches up on reconnection after disconnection
        }

        else{
            //messages sent on the channel between disconnection and reconnection are not received
        }
    }


    public void setFilter(java.lang.String filter){
        this.filter = filter;
    }

    public java.lang.String getFilter(){
        return filter;
    }










}
