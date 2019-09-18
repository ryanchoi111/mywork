package com.mycompany.petbnb;

import com.codename1.io.Log;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.Resources;

import java.io.IOException;

import static com.codename1.ui.CN.getCurrentForm;

public class testscreen {

    private Form current,hi;
    private Resources theme;
    private Image mapPic;
    private Image hPetPic;
    private Image hHomePic;
    private Image listingsPic;
    private Image profilePic;
    private Image settingsPic;

    private Boolean loggedin = false;
    public void init(Object context) {
        theme = UIManager.initFirstTheme("/theme");

        // Enable Toolbar on all Forms by default
        Toolbar.setGlobalToolbar(true);

        // Pro only feature
        Log.bindCrashProtection(true);
    }

    public void start() {
        if(current != null){
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

        hi = new Form("Home", new GridLayout(2,1));
        Form next = new Form("Home", new GridLayout(2,1));
        Toolbar tb = hi.getToolbar();

        Image icon = theme.getImage("icon.png");
        Container topBar = BorderLayout.east(new Label(icon));
        topBar.add(BorderLayout.SOUTH, new Label("Cool App Tagline...", "SidemenuTagline"));
        topBar.setUIID("SideCommand");
        tb.addComponentToSideMenu(topBar);
      //  hi.getToolbar().setBackCommand("", e -> next.showBack());
       // tb.addCommandToRightBar(hi.getBackCommand());

        tb.addMaterialCommandToSideMenu("Home", FontImage.MATERIAL_HOME, e -> {});
        tb.addMaterialCommandToSideMenu("Website", FontImage.MATERIAL_WEB, e -> {});
        tb.addMaterialCommandToSideMenu("Settings", FontImage.MATERIAL_SETTINGS, e -> {});
        tb.addMaterialCommandToSideMenu("About", FontImage.MATERIAL_INFO, e -> {});



        //Image listingsb = theme.getImage("switch-on.png");
        Container c = new Container(new FlowLayout());
        Container top = new Container( new BorderLayout());
        Container toolBar = new Container(new FlowLayout());
        Container have = new Container(new GridLayout(1,2));
        Container buttonset = new Container(new GridLayout(2,2));
        Button btn = new Button("Test");
        Button btn2 = new Button("Test2");
        Button btn3 = new Button("Test3");
        Button hamburger = new Button("");
        Button home = new Button("Home");
        Button back = new Button("");

        Button hPet = new Button("hPet") {public void paint(Graphics g) {
            // red color
            //  g.setColor(0xff0000);
            g.drawImage(hPetPic,this.getX(),this.getY(),this.getWidth(),this.getHeight());

        }};

        Button hHome = new Button(){public void paint(Graphics g) {
            // red color
            //  g.setColor(0xff0000);
            g.drawImage(hHomePic,this.getX(),this.getY(),this.getWidth(),this.getHeight());

        }};

        Button listings = new Button("listings", theme.getImage("switch-on.png")){public void paint(Graphics g) {
            // red color
            //  g.setColor(0xff0000);
            g.drawImage(listingsPic,this.getX(),this.getY(),this.getWidth(),this.getHeight());

        }};


       /* listings.setPressedIcon (theme.getImage("switch-off.png"));

        listings.getAllStyles().setTextDecoration(Style.TEXT_DECORATION_UNDERLINE);
        listings.getAllStyles().setBgImage(theme.getImage("listings.png"));
        listings.setIcon(FontImage.createMaterial(FontImage.MATERIAL_SAVE, listings.getUnselectedStyle()));
        */
        Button profile = new Button("Profile"){public void paint(Graphics g) {
            // red color
            //  g.setColor(0xff0000);
            g.drawImage(profilePic,this.getX(),this.getY(),this.getWidth(),this.getHeight());

        }};

        Button map = new Button("Map") {public void paint(Graphics g) {
            // red color
            //  g.setColor(0xff0000);
            g.drawImage(mapPic,this.getX(),this.getY(),this.getWidth(),this.getHeight());

        }};

        Button settings = new Button("Settings"){public void paint(Graphics g) {
            // red color
            //  g.setColor(0xff0000);
            g.drawImage(settingsPic,this.getX(),this.getY(),this.getWidth(),this.getHeight());

        }};


      /*  hi.add(new Button("First")).
                add(c).
                add(new Label("Third")).
                add(buttonset).
                add(new Label("Fifth"));

*/
        //toolBar.add(back).add(home).add(hamburger);

        have.add(hPet).add(hHome);

       // top.addComponent(BorderLayout.NORTH, toolBar);
        top.addComponent(BorderLayout.CENTER, have);
        top.addComponent(BorderLayout.SOUTH, btn);

                //c.addComponent(BorderLayout.NORTH, new Button("2"));

        buttonset.add(listings).
                add(profile).
                add(map).
                add(settings);

      hi.add(top).add(buttonset);






        // here is where we add actionlisteners to go to the corresponding pages such as listings, listing details, or maps
        Dialog dlg = new Dialog();
        dlg.setLayout(new BorderLayout());

        TextField password = new TextField("", "Password", 20, TextArea.PASSWORD);
        TextField email = new TextField("", "E-Mail", 20, TextArea.EMAILADDR);
        Label infolab = new Label("Pleas123123e Log In");
        Container info = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        info.add(infolab);
        info.add(email);
        info.add(password);

        dlg.add(BorderLayout.CENTER,info);


        hPet.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                if (loggedin=false){
                    int h = Display.getInstance().getDisplayHeight();
                    dlg.show(h /8 * 5, 0, 0, 0);
            }

            else;}

        });

            hHome.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {

                    if (loggedin == false) {
                        int h = Display.getInstance().getDisplayHeight();
                        //dlg.show(h /8 * 5, 0, 0, 0);
                    } else ;


                }});

                settings.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent evt) {

                        if (loggedin==false){
                            int h = Display.getInstance().getDisplayHeight();
                            //dlg.show(h /8 * 5, 0, 0, 0);
                            //show settings screen
                        }

                        else;

                    }});

        map.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                if (loggedin==false){
                    int h = Display.getInstance().getDisplayHeight();
                    //dlg.show(h /8 * 5, 0, 0, 0);
                    //show map screen
                }

                else;

            }});

        profile.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {

                if (loggedin==false){
                    int h = Display.getInstance().getDisplayHeight();
                    //dlg.show(h /8 * 5, 0, 0, 0);
                    // show profile screen
                }

                else;

            }});

        listings.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                System.out.println("map pressed");
                if (loggedin==false){

                    int h = Display.getInstance().getDisplayHeight();
                    //dlg.show(h /8 * 5, 0, 0, 0);
                    // show listing screen
                }

                else;

            }});

        hi.show();

// span label accepts the text and the UIID for the dialog body
        //dlg.add(new SpanLabel("Dialog Body text", "DialogBody"));
        int h = Display.getInstance().getDisplayHeight();
        dlg.setDisposeWhenPointerOutOfBounds(true);

       // dlg.show(h /8 * 7, 0, 0, 0);
    }

    public void stop() {
        current = getCurrentForm();
        if(current instanceof Dialog) {
            ((Dialog)current).dispose();
            current = getCurrentForm();
        }
    }

    public void destroy() {
    }

}
