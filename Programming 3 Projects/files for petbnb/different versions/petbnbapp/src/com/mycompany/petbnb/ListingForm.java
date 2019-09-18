package com.mycompany.petbnb;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class ListingForm {
    private Form current;
    private Resources theme;
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
        Form list = new Form("", BoxLayout.y());
        Label nameuser = new Label("Ryan Choi");
        Label deets = new Label("Details:");
        TextArea about = new TextArea("3 dogs: Boomer 1 yr old Bulldog, Bud 3 yrs old Golden Retriever, and Tom 4 yrs old Siberian Husky " +
                "Non-smoking household" + "No children" + "Lives in House" + "Price");
        list.add(nameuser);
        list.add(deets);
        list.add(about);


    }



}
