/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import view.GenClass;

/**
 *
 * @author DARSHAN
 */
public class LoggedInUserInfo implements Serializable
{
      ArrayList alUserInfo[]=new ArrayList[2];

//    public LoggedInUserInfo() 
//    {
//        alUserInfo=new ArrayList[2];
//    }
    
    public void setUserInfo()
    {
        alUserInfo[0]=ServerGenStreamClass.alClientStrm[3];
        alUserInfo[1]=new ArrayList();
        for(int i=0;i<alUserInfo[0].size();i++)
        {
            alUserInfo[1].add("Participant");
        }
        
        alUserInfo[0].add(GenClass.username);
        alUserInfo[1].add("Presenter");
    }
    
    
}
