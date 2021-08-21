/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.Serializable;
import java.util.ArrayList;
import model.DBClass;
import view.GenClass;

/**
 *
 * @author DARSHAN
 */
public class PptLoginClass implements Serializable
{
    String usernm,passwd;
        
    public PptLoginClass(String unm,String pwd)
    {
        usernm=unm;
        passwd=pwd;
    }
    
    Boolean validateUser()
    {
        DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
        Boolean valid=false;
        try
        {
            String cols[]={"*"};
            String condtn[]={"User_name","Password","User_role"};
            String val[]= {usernm,passwd,"V"};
            String opt[]={"AND","AND"};
            ArrayList alResult=db.select("UserInfo",cols,condtn,val,opt);
            System.out.println("Thread in validateUser");
            if(!alResult.isEmpty())
            {
                valid=true;
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in validateUser="+e);e.printStackTrace();
        }
        return valid;
    }
}
