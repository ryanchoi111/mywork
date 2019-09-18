package com.choi.petairbnb2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;

/**
 *
 * @author Chen
 */
public class UserForm extends Form{

    private String name;
    private String imageURL;

    public UserForm(String name, EncodedImage placeHolder, String imageURL) {
        this.name = name;
        this.imageURL = imageURL;
        setTitle("Welcome!");
        setLayout(new BorderLayout());

        Label icon = new Label(placeHolder);
        icon.setUIID("Picture");
        if(imageURL != null){
            icon.setIcon(URLImage.createToStorage(placeHolder, name, imageURL, null));
        }
        addComponent(BorderLayout.CENTER, icon);
        Label nameLbl = new Label(name);
        nameLbl.setUIID("Name");
        addComponent(BorderLayout.SOUTH, nameLbl);
        final Form current = Display.getInstance().getCurrent();
        Command back = new Command("Back"){

            @Override
            public void actionPerformed(ActionEvent evt) {
                current.showBack();;
            }

        };
        addCommand(back);
        setBackCommand(back);

    }


    }
