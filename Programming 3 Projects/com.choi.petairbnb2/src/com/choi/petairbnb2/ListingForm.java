package com.choi.petairbnb2;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.ui.*;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ListingForm {
    private Form current;
    private String url = "http://kjc.ma1geek.org/test.php";
    private String tempText;
    JSONParser json;
    private JSONObject bb, id;
    private Resources theme;
    private Label username , pw , nameOnCard, lastOnCard, billAddress, address ;
    private TextField txtFirst,txtLast;
    private Button forward,back,make, pay1;
    private Container panela,panelb,panelba,panelbb;
    private int x;
    private Image milton;
    ConnectionRequest r;
    LinkedHashMap<Integer,Object> userArrayMap=new LinkedHashMap<>();
    LinkedHashMap<Integer,Object> userData=new LinkedHashMap<>();
    ArrayList userArray;
    ReadParseJson q;
    ArrayList<String> dt;

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
        Toolbar list1= list.getToolbar();


    }


}
