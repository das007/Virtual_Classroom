/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.Serializable;
import model.DBClass;
import view.GenClass;

/**
 *
 * @author DARSHAN
 */
public class PptProfileClass implements Serializable
{
    String usernm;
    String expValue[];
    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);

    public PptProfileClass(String unm,String[] val) 
    {
        usernm=unm;
        expValue=val;
    }
    
    Boolean editUser()
    {
        Boolean success=false;
        try
        {
            String colnm[]={"First_name","Last_name","Email_id"};
            String condtn[]={"User_name"};
            String val[]={usernm};
            int result=db.update("UserInfo",colnm,expValue,condtn,null,val);
            if(result>0)
            {
                success=true;
            }
        }
        catch(Exception e)
        {
            System.out.println("Excpe in editUser="+e);e.printStackTrace();
        }
        return success;
    }
}
