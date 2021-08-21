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
public class PptRegisterClass implements Serializable
{
    ArrayList alValues;
    

    public PptRegisterClass(ArrayList val)
    {
        alValues=val;
    }
    
    Boolean registerUser()
    {
        DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
        Boolean success=false;
        try
        {
            int rows=db.insert("UserInfo", alValues);
            if(rows>0)
            {
                success=true;
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in regUser="+e);e.printStackTrace();
        }
        return success;
    }
}
