/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import view.ChatFrame;
import view.ClientPollFrame;
import view.LectGenClass;
import view.PollQues;
import view.WhiteBoardPanel;

/**
 *
 * @author DARSHAN
 */
public class ClientThreadClass implements Runnable
{
    Socket clientSok;
    ObjectInputStream ois;
    
    public ClientThreadClass(Socket sok) 
    {
        try
        {
            clientSok=sok;
            ois=new ObjectInputStream(clientSok.getInputStream());
        }
        catch(Exception e)
        {
            System.out.println("Excep in CleintThreadCls="+e);e.printStackTrace();
        }
    }
    
    
    public void run()
    {
        try
        {
            Object obj;
            while((obj=ois.readObject())!=null)
            {
                if(obj instanceof ChatClass)
                {
                    ChatClass cc=(ChatClass)obj;
                    String msg;
                    if(cc.destUsernm.equals("Everyone"))
                    {
                        msg="\n\nfrom "+cc.sourceUsernm+":\n"+cc.message;
                    }
                    else
                    {
                        msg="\n\nfrom "+cc.sourceUsernm+" (Privately):\n"+cc.message;
                    }
                    ChatFrame.getmessage(msg);
                }
                else if(obj instanceof PollQues)
                {
                    PollQues pq=(PollQues)obj;
                    ClientPollFrame cpf=new ClientPollFrame();
                    cpf.createClientPollFrame(pq);
                }
                else if(obj instanceof LoggedInUserInfo)
                {
                    LoggedInUserInfo lg=(LoggedInUserInfo)obj;
                    LectGenClass.updateUserTable(lg.alUserInfo);
                }
                else if(obj instanceof ArrayList[])
                {
                    ArrayList alShape[]=(ArrayList[])obj;
                    WhiteBoardPanel.setCanvas(alShape);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excpe in run="+e);e.printStackTrace();
        }
    }
    
}
