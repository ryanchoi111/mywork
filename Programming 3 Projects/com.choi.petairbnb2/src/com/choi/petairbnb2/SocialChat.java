package com.choi.petairbnb2;

import com.codename1.social.FacebookConnect;
import com.codename1.social.GoogleConnect;
import com.codename1.social.Login;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

public class SocialChat {
    Form current;
    public void start() {
        if(current != null){
            current.show();
            return;
        }
        showLoginForm();
    }

    private void showLoginForm() {
        Form loginForm = new Form();

        // the blue theme styles the title area normally this is good but in this case we don't want the blue bar at the top
        loginForm.getTitleArea().setUIID("Container");
        loginForm.setLayout(new BorderLayout());
        loginForm.setUIID("MainForm");
        Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        cnt.setUIID("Padding");
        Button loginWithGoogle = new Button("Signin with Google");
        Button loginWithFacebook = new Button("Signin with Facebook");
        cnt.addComponent(loginWithGoogle);
        cnt.addComponent(loginWithFacebook);
        loginWithGoogle.addActionListener((e) -> {
            doLogin(GoogleConnect.getInstance());
        });



        loginWithFacebook.addActionListener((e) -> {
            doLogin(FacebookConnect.getInstance());
        });
        loginForm.addComponent(BorderLayout.SOUTH, cnt);
        loginForm.show();

    }

    void doLogin(Login lg) {
        // TODO...
    }

}
