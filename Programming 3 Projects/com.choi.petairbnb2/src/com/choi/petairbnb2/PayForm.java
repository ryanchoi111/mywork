package com.choi.petairbnb2;

import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

public class PayForm {
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
        Form pay = new Form("Payment", BoxLayout.y());

        Toolbar tb = pay.getToolbar();


    }





}
