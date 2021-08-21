/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author DARSHAN
 */
public class DBThreadClass implements Runnable
{

    Socket clientSok;
    ObjectInputStream ois;
    InputStream istrm;
    int sokIndex;
    
    public DBThreadClass(Socket sok) 
    {
        try
        {
            clientSok=sok;
            sokIndex=ServerGenStreamClass.alClientStrm[0].indexOf(clientSok);
            ois=(ObjectInputStream)ServerGenStreamClass.alClientStrm[2].get(sokIndex);
        }
        catch(Exception e)
        {
            System.out.println("Excep in DBThread="+e);
        }
    }
    
    @Override
    public synchronized void run()
    {
        Object obj;
        Boolean flag=false;
        System.out.println("DBThread Started");
        try
        {
            while((obj=ois.readObject())!=null)
            {
                if(obj instanceof PptLoginClass)
                {
                    System.out.println("Thread in Login");
                    PptLoginClass plc=(PptLoginClass)obj;
                    flag=plc.validateUser();
                    if(flag)
                    {
                        System.out.println("Valid User");
              //          ClientStreamClass.alUser.put(plc.usernm, clientSok);
                        
                        ServerGenStreamClass.alClientStrm[3].add(sokIndex,plc.usernm);
                        
//                        LoggedInUserInfo logUser=new LoggedInUserInfo();
//                        logUser.setUserInfo();
//                        LectureThreadClass.sendUserInfo(logUser);
                                
                        Thread t=new Thread(new LectureThreadClass(clientSok),plc.usernm);
                        t.start();
                        System.out.println("LectThread started");
                        break;
                    }
                }
                else if(obj instanceof PptRegisterClass)
                {
                    PptRegisterClass prc=(PptRegisterClass)obj;
                    flag=prc.registerUser();
                }
                else if(obj instanceof PptForgotPwdClass)
                {
                    PptForgotPwdClass pfc=(PptForgotPwdClass)obj;
                    flag=pfc.getPassword();
                }
                
                ObjectOutputStream oos=(ObjectOutputStream)ServerGenStreamClass.alClientStrm[1].get(sokIndex);
                oos.writeObject(flag);
                oos.flush();
            }
            ObjectOutputStream oos=(ObjectOutputStream)ServerGenStreamClass.alClientStrm[1].get(sokIndex);
            oos.writeObject(flag);
            oos.flush();
        }
        catch(Exception e)
        {
            System.out.println("Excep in run");e.printStackTrace();
        }
    }
}

//class PptLoginClass implements Serializable
//{
//    String usernm,passwd;
//    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
//    
//    PptLoginClass(String unm,String pwd)
//    {
//        usernm=unm;
//        passwd=pwd;
//    }
//    
//    Boolean validateUser()
//    {
//        Boolean valid=false;
//        try
//        {
//            String cols[]={"*"};
//            String condtn[]={"User_name","Password","User_role"};
//            String val[]= {usernm,passwd,"V"};
//            String opt[]={"AND","AND"};
//            ArrayList alResult=db.select("UserInfo",cols,condtn,val,opt);
//            
//            if(!alResult.isEmpty())
//            {
//                valid=true;
//            }
//        }
//        catch(Exception e)
//        {
//            System.out.println("Excep in validateUser="+e);e.printStackTrace();
//        }
//        return valid;
//    }
//}

//class PptRegisterClass implements Serializable
//{
//    ArrayList alValues;
//    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
//
//    public PptRegisterClass(ArrayList val)
//    {
//        alValues=val;
//    }
//    
//    Boolean registerUser()
//    {
//        Boolean success=false;
//        try
//        {
//            int rows=db.insert("UserInfo", alValues);
//            if(rows>0)
//            {
//                success=true;
//            }
//        }
//        catch(Exception e)
//        {
//            System.out.println("Excep in regUser="+e);e.printStackTrace();
//        }
//        return success;
//    }
//}

//class PptProfileClass implements Serializable
//{
//    String usernm;
//    String expValue[];
//    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
//
//    public PptProfileClass(String unm,String[] val) 
//    {
//        usernm=unm;
//        expValue=val;
//    }
//    
//    Boolean editUser()
//    {
//        Boolean success=false;
//        try
//        {
//            String colnm[]={"First_name","Last_name","Email_id"};
//            String condtn[]={"User_name"};
//            String val[]={usernm};
//            int result=db.update("UserInfo",colnm,expValue,condtn,null,val);
//            if(result>0)
//            {
//                success=true;
//            }
//        }
//        catch(Exception e)
//        {
//            System.out.println("Excpe in editUser="+e);e.printStackTrace();
//        }
//        return success;
//    }
//}