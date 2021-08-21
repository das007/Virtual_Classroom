/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.Iterator;
import view.GenClass;

/**
 *
 * @author DARSHAN
 */
public class ChatClass implements Serializable
{
    String destUsernm;
    String sourceUsernm;
    String message;

    public ChatClass(String src,String dest,String msg) 
    {
        sourceUsernm=src;
        destUsernm=dest;
        message=msg;
    }
    
    public void sendMessage()
    {
        try
        {
            ObjectOutputStream oos=null;
            if(destUsernm.equals("Everyone"))
            {
                Iterator itKey=ClientStreamClass.alUser.keySet().iterator();
                while(itKey.hasNext())
                {
                    String unm=itKey.next().toString();
                    if(!sourceUsernm.equals(unm))
                    {
                        Socket sok=ClientStreamClass.alUser.get(unm);
                        oos=new ObjectOutputStream(sok.getOutputStream());
                        oos.writeObject(this);
                        oos.flush();
                    }
                }
            }
            else if(destUsernm.equals(GenClass.username))
            {
                
            }
            else
            {
                Socket sok=ClientStreamClass.alUser.get(destUsernm);
                oos=new ObjectOutputStream(sok.getOutputStream());
                oos.writeObject(this);
                oos.flush();
            }    
        }
        catch(Exception e)
        {
            System.out.println("Excep in sendMsg="+e);e.printStackTrace();
        }
    } 
    
}
