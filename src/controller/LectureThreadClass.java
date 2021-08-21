/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import view.PollQues;

/**
 *
 * @author DARSHAN
 */
//public class ChatServer
//{
//
//    
//}

public class LectureThreadClass implements Runnable
{
    Socket clientSok;
    ObjectInputStream ois;
    static int sokIndex;

    public LectureThreadClass(Socket cs) 
    {
        try
        {
            clientSok=cs;
            sokIndex=ServerGenStreamClass.alClientStrm[0].indexOf(clientSok);
            ois=(ObjectInputStream)ServerGenStreamClass.alClientStrm[2].get(sokIndex);
        //    ois=new ObjectInputStream(clientSok.getInputStream());
        }
        catch(Exception e)
        {
            System.out.println("Excep in ChatHandler="+e);e.printStackTrace();
        }
    }
    
    @Override
    public void run()
    {
        boolean flag=false;
        try
        {
            Object obj;
            while((obj=ois.readObject())!=null)
            {
                if(obj instanceof ChatClass)
                {
                    ChatClass cc=(ChatClass)obj;
                    cc.sendMessage();
                    //sendMessage((ChatClass)obj);
                }
                if(obj instanceof PptProfileClass)
                {
                    PptProfileClass ppc=(PptProfileClass)obj;
                    flag=ppc.editUser();
                    
                    ObjectOutputStream oos=(ObjectOutputStream)ServerGenStreamClass.alClientStrm[1].get(sokIndex);
                    oos.writeObject(flag);
                    oos.flush();
                }
                if(obj instanceof Integer)
                {
                    
                }
                
            }
        }
        catch(Exception e)
        {
            System.out.println("Excpe in runChat="+e);e.printStackTrace();
        }
    }
    
    public static void sendWhiteboard(ArrayList alShape[])
    {
        try
        {
            //Iterator itShape=ClientStreamClass.alUser.keySet().iterator();
            Iterator itShape=ServerGenStreamClass.alClientStrm[3].iterator();
            while(itShape.hasNext())
            {
                String unm=itShape.next().toString();
//                Socket sok=(Socket)ClientStreamClass.alUser.get(unm);
//                ObjectOutputStream oos=new ObjectOutputStream(sok.getOutputStream());
                
                int indx=ServerGenStreamClass.alClientStrm[3].indexOf(unm);
                ObjectOutputStream oos=(ObjectOutputStream)ServerGenStreamClass.alClientStrm[1].get(indx);
                oos.writeObject(alShape);
                oos.flush();
            }
        }
        catch(Exception e)
        {
            System.out.println("Excpe in sendWB="+e);e.printStackTrace();
        }
    }
    
    public static void sendPoll(PollQues pq)
    {
        try
        {
            //Iterator itShape=ClientStreamClass.alUser.keySet().iterator();
            Iterator itPoll=ServerGenStreamClass.alClientStrm[3].iterator();
            while(itPoll.hasNext())
            {
                String unm=itPoll.next().toString();
          //      Socket sok=(Socket)ClientStreamClass.alUser.get(unm);
                int indx=ServerGenStreamClass.alClientStrm[3].indexOf(unm);
                ObjectOutputStream oos=(ObjectOutputStream)ServerGenStreamClass.alClientStrm[1].get(indx);
                oos.writeObject(pq);
                oos.flush();
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in sendPoll="+e);e.printStackTrace();
        }
    }
    
    public static void sendUserInfo(LoggedInUserInfo logUser)
    {
        try
        {
            Iterator it=ServerGenStreamClass.alClientStrm[3].iterator();
            while(it.hasNext())
            {
                String unm=it.next().toString();
                int indx=ServerGenStreamClass.alClientStrm[3].indexOf(unm);
                ObjectOutputStream oos=(ObjectOutputStream)ServerGenStreamClass.alClientStrm[1].get(indx);
                oos.writeObject(logUser);
                oos.flush();
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in sendUserInfo="+e);e.printStackTrace();
        }
    }
}

