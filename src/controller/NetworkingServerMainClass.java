/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 *
 * @author DARSHAN
 */
public class NetworkingServerMainClass implements Runnable
{
    ClientStreamClass csc;
    ServerGenStreamClass sgsc;
    static int participants=0;

    public NetworkingServerMainClass() 
    {
        csc=new ClientStreamClass();
        sgsc=new ServerGenStreamClass();
    }
    
    public void run()
    {
        try
        {
            setupNetworking();
        }
        catch(Exception e)
        {
            System.out.println("Excpe in runMain="+e);
        }
    }
    
    private void setupNetworking()
    {
        try
        {
            ServerSocket ss=new ServerSocket(7777);
            while(true)
            {
                System.out.println("Server Running Waiting for Client...");
                Socket cs=ss.accept();
                System.out.println("Client Connected...");
                System.out.println("InetAdd="+cs.getInetAddress());
                String inetAdd=cs.getInetAddress().toString();
                
                ObjectInputStream ois=new ObjectInputStream(cs.getInputStream());
                ObjectOutputStream oos=new ObjectOutputStream(cs.getOutputStream());
                
                ServerGenStreamClass.alClientStrm[0].add(cs);
                ServerGenStreamClass.alClientStrm[1].add(oos);
                ServerGenStreamClass.alClientStrm[2].add(ois);
                
                
                
                
//                System.out.println("flg1");
//                csc.getHmClientOS().put(inetAdd, cs.getOutputStream());
//                System.out.println("flg2");
//                csc.getHmClientIS().put(inetAdd,cs.getInputStream());
//                System.out.println("flg3");
//                csc.getHmClientSok().put(inetAdd,cs);
//                System.out.println("flg4");
                
                
       //         ClientStreamClass.hmOis.put(inetAdd, null)
                
                
                
                participants++;
                Thread t=new Thread(new DBThreadClass(cs),inetAdd);
                System.out.println("flg4");
                t.start();
                System.out.println("flg5");
                
//                System.out.println("flg1");
//                csc.getHmClientOS().put(inetAdd, cs.getOutputStream());
//                System.out.println("flg2");
//                csc.getHmClientIS().put(inetAdd,cs.getInputStream());
//                System.out.println("flg3");
//                csc.getHmClientSok().put(inetAdd,cs);
//                System.out.println("flg4");
                
//                ObjectOutputStream oos=new ObjectOutputStream(cs.getOutputStream());
//                oos.writeObject(true);
//                oos.flush();
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in setUpNetwk="+e);e.printStackTrace();
        }
    }
}

class ClientStreamClass
{
    private HashMap<String,Socket> hmClientSok=new HashMap<>();
    private HashMap<String,OutputStream> hmClientOS=new HashMap<>();
    private HashMap<String,InputStream> hmClientIS=new HashMap<>();
    static HashMap<String,Socket> alUser=new HashMap<>();
    static HashMap<String,ObjectOutputStream> hmOos=new HashMap<>();
    static HashMap<String,ObjectInputStream> hmOis=new HashMap<>();
    private String clientIP;

   
    public void setHmClientOS(HashMap<String, OutputStream> hmClientOS) 
    {
        this.hmClientOS = hmClientOS;
    }

    public HashMap<String, Socket> getHmClientSok() 
    {
        return hmClientSok;
    }

    public void setHmClientSok(HashMap<String, Socket> hmClientSok) 
    {
        this.hmClientSok = hmClientSok;
    }

    public HashMap<String, InputStream> getHmClientIS() 
    {
        return hmClientIS;
    }

    public void setHmClientIS(HashMap<String, InputStream> hmClientIS) {
        this.hmClientIS = hmClientIS;
    }

    public void setClientIP(String clientIP) {
        this.clientIP = clientIP;
    }

    public HashMap<String, OutputStream> getHmClientOS() {
        return hmClientOS;
    }

    public String getClientIP() {
        return clientIP;
    }
    
    
    
}
