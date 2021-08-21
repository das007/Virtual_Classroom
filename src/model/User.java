/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;

/**
 *
 * @author DARSHAN
 */
public class User {
    
    private String username,firstnm,lastnm,password,type_of_user,email_id;
    DBClass db;

    public void User()
    {
        db=new DBClass("1521", "orcl","VClassroom", "Virtual007");
    }

    public void setFirstnm(String firstnm) {
        this.firstnm = firstnm;
    }

    public void setLastnm(String lastnm) {
        this.lastnm = lastnm;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setType_of_user(String type_of_user) {
        this.type_of_user = type_of_user;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }
    
    public void insert_User()
    {
        try
        {
            ArrayList al=new ArrayList();
            al.add(username);
            al.add(firstnm);
            al.add(lastnm);
            al.add(password);
            al.add(type_of_user);
            al.add(email_id);
            db.insert("User", al);
            System.out.println("User "+username+" created successfully...");
        }
        catch(Exception e)
        {
            System.out.println("Excep in insertUser="+e);
        }
    }
    
    public void update_User()
    {
        try
        {
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in updateUser="+e);
        }
    }
    
    public void delete_User()
    {
        try
        {
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in deleteUser="+e);
        }
    }
    
    public void view_User(String colnm[])
    {
        try
        {
         //   db.select("User", colnm, "User_name", username);
        }
        catch(Exception e)
        {
            System.out.println("Excep in viewUser="+e);
        }
    }
    
}
