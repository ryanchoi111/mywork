package com.mycompany.petbnb;



/**
 * Created by jaweiler5454 on 10/22/17.
 */

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;

public class Databasehelpr {

    public Databasehelpr(){

    }

    public boolean deleteStand(String userID){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://lemonade.ma1geek.org/deleteStand.php");
            r.setPost(true);
            //r.addArgument();

            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return Boolean.parseBoolean(result.get("worked").toString());
        } catch(Exception err) {
            Log.e(err);
            return false;
        }
        ///EXAMPLE (might not work if post 1010 does not exist)


    }

    public boolean addStand(Map<String,String> params){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://lemonade.ma1geek.org/createStand.php");
            //  r.setPost(false);
            for(String key: params.keySet())
            {
                r.addArgument(key,params.get(key));
            }
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return Boolean.parseBoolean(result.get("worked").toString());
        } catch(Exception err) {
            Log.e(err);
            return false;
        }

       /* EXAMPLE
        DatabaseHelper base = new DatabaseHelper();
         Map<String,String> params = new HashMap<String, String>();
         params.put("id", "111");
         params.put("username", "helloWorld");
         params.put("name", "Test Hello");
         params.put("class", "2018");
         params.put("email", "test_hello18@milton.edu");
         params.put("rating", "2");
         params.put("bio", "i am a test user");
         params.put("classes", "|Programming 2|English Workshop|");
         System.out.println(base.addUser(params));
         */
    }










    public Map<String, Object> getUserByUsername(String username){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tutormilton.ma1geek.org/getUser.php");
            r.setPost(false);
            r.addArgument("username",username);
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return result;
        } catch(Exception err) {
            Log.e(err);
            return null;
        }
       /* EXAMPLE
          DatabaseHelper base = new DatabaseHelper();
        System.out.println(base.getUserByUsername("scottyc"));
        */
    }
    public Map<String, Object> getUserByID(String id){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tutormilton.ma1geek.org/getUser.php");
            r.setPost(false);
            r.addArgument("id",id);
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return result;
        } catch(Exception err) {
            Log.e(err);
            return null;
        }
       /* EXAMPLE
          DatabaseHelper base = new DatabaseHelper();
        System.out.println(base.getUserByID("123456"));
        */
    }
    public ArrayList<Map<String, Object>> getPosts(String className){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tutormilton.ma1geek.org/getPosts.php");
            r.setPost(false);
            r.addArgument("class","%|" + className + "|%");
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            ArrayList<Map<String, Object>> content = (ArrayList<Map<String, Object>>)result.get("root");
            return content;
        } catch(Exception err) {
            Log.e(err);
            return null;
        }
       /* EXAMPLE
          DatabaseHelper base = new DatabaseHelper();
        System.out.println(base.getPosts("Programming 2"));
        */
    }
    public ArrayList<Map<String, Object>> getComments(String postID){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tutormilton.ma1geek.org/getComments.php");
            r.setPost(false);
            r.addArgument("qid",postID);
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            ArrayList<Map<String, Object>> content = (ArrayList<Map<String, Object>>)result.get("root");
            return content;
        } catch(Exception err) {
            Log.e(err);
            return null;
        }
       /* EXAMPLE
          DatabaseHelper base = new DatabaseHelper();
        System.out.println(base.getComments("12345"));
        */
    }

    public boolean addComment(Map<String,String> params){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tutormilton.ma1geek.org/addComment.php");
            r.setPost(true);
            for(String key: params.keySet())
            {
                r.addArgument(key,params.get(key));
            }
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return Boolean.parseBoolean(result.get("worked").toString());
        } catch(Exception err) {
            Log.e(err);
            return false;
        }
       /* EXAMPLE
        DatabaseHelper base = new DatabaseHelper();
         Map<String,String> params = new HashMap<String, String>();
         params.put("username", "scottyc");
         params.put("comment", "my test comment");
         params.put("qid", "12345");
         System.out.println(base.addComment(params));
         */
    }
    public boolean addUser(Map<String,String> params){
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://petbnb.ma1geek.org/collectUser.php");

            r.setPost(true);
            for(String key: params.keySet())
            {
                r.addArgument(key,params.get(key));
                System.out.println(key);
            }
            System.out.println("helloo");
            NetworkManager.getInstance().addToQueueAndWait(r);   //ERROR
            System.out.println("WORK? " + r.getResponseData()); //null
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            System.out.println("WORK NA" + r.getResponseData());
            System.out.println(result.toString());
            return Boolean.parseBoolean(result.get("worked").toString());

        } catch(Exception err) {
            Log.e(err);

            return false;
        }
       /* EXAMPLE
        DatabaseHelper base = new DatabaseHelper();
        Date d = new Date();
         Map<String,String> params = new HashMap<String, String>();
         params.put("username", "scottyc");
         params.put("title", "my test post");
         params.put("body", "hey guys whats up");
         params.put("qid", d.getTime() + "");
         params.put("pic", "www.memes.com/asfdfsa");
         params.put("tags", "|Programming 2|English Workshop|");
         System.out.println(base.addPost(params));
         */
    }

    public int addLike(String qid, String username){ //returns likes if works, -10000 if doesn't
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tutormilton.ma1geek.org/addLike.php");
            r.setPost(true);
            r.addArgument("qid", qid);
            r.addArgument("username", "|" + username + "|");
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return Integer.parseInt(result.get("Likes").toString());
        } catch(Exception err) {
            Log.e(err);
            return -10000;
        }
       /* EXAMPLE (might not work if post 1010 does not exist)
        DatabaseHelper base = new DatabaseHelper();
         System.out.println(base.addLike("1","scottyc"));
         */
    }
    public int removeLike(String qid, String username){ //returns likes if works, -10000 if doesn't
        try {
            ConnectionRequest r = new ConnectionRequest();
            r.setUrl("http://tutormilton.ma1geek.org/removeLike.php");
            r.setPost(true);
            r.addArgument("qid", qid);
            r.addArgument("username", "|" + username + "|");
            NetworkManager.getInstance().addToQueueAndWait(r);
            Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
            return Integer.parseInt(result.get("Likes").toString());
        } catch(Exception err) {
            Log.e(err);
            return -10000;
        }
       /* EXAMPLE (might not work if post 1010 does not exist)
        DatabaseHelper base = new DatabaseHelper();
         System.out.println(base.removeLike("1","scottyc"));
         */
    }
}
