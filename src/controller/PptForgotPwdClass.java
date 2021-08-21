/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.DBClass;
import view.EmailClass;
import view.GenClass;

/**
 *
 * @author DARSHAN
 */
public class PptForgotPwdClass implements Serializable
{
    String username,password,emailID;
    

    public PptForgotPwdClass(String unm) 
    {
        username=unm;
    }
    
    public Boolean getPassword()
    {
        DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
        Boolean flg=false;
        try
        {
            String colnm[]={"Password","Email_id"};
            String condtn[]={"User_name"};
            String val[]={username};
            ArrayList alResult=db.select("UserInfo", colnm, condtn, val, null);
            
            if(alResult.size()>0)
            {
                password=((ArrayList)alResult.get(0)).get(0).toString();
                emailID=((ArrayList)alResult.get(0)).get(1).toString();
                
                String toEmail[]={emailID};
                EmailClass ec=new EmailClass();
                ec.sendEmail(toEmail,"Virtual Classroom - Password",username+", Your passsword is "+password);
                flg=true;
            }
        }
        catch(Exception e)
        {
            System.out.println("Excpe in getPwd="+e);e.printStackTrace();
        }
        return flg;
    }
    
}
