/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.EventQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import model.DBClass;

/**
 *
 * @author DARSHAN
 */
public class GenClass {
    public static final String port="1046",sid="orcl",dbUser="C##VClassroom07",dbPwd="Virtual07";
    static DBClass db=new DBClass(port,sid,dbUser,dbPwd);
    private static boolean hideFlg=false;
    public static String username=null;
    
    static void setUsername(String unm)
    {
        username=unm;
    }
    
    static ArrayList[] getParticipants()
    {
        //db=new DBClass(port,sid,dbUser,dbPwd);
   //     String partpnt[][]=new String[2][];
        ArrayList alPpt[]=new ArrayList[2];
        alPpt[0]=new ArrayList();
        alPpt[1]=new ArrayList();
        try
        {
            String colnm[]={"User_name","First_name","Last_name"};
            String condtn[]={"User_role"};
            String val[]={"V"};
            ArrayList alResult=db.select("UserInfo", colnm,condtn,val,null);
            Iterator it=alResult.iterator();
       //     partpnt[0]=new String[alResult.size()];
       //     partpnt[1]=new String[alResult.size()];
       //     int i=0;
            while(it.hasNext())
            {
                ArrayList alrow=(ArrayList)it.next();
                Iterator itRow=alrow.iterator();
                String unm=(String)itRow.next();
                String name=itRow.next()+" "+itRow.next();
          //      partpnt[0][i]=name;
          //      partpnt[1][i++]=unm;
                alPpt[0].add(name);
                alPpt[1].add(unm);
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in getParticipant="+e);
        }
        return alPpt;
    }
    
    static ArrayList setPptList()
    {
        ArrayList al=new ArrayList();
        DefaultListModel dlmPartpnt=new DefaultListModel();
        try
        {
        //    String partpnt[][];
            ArrayList alPpt[]=getParticipants();
            //partpnt=getParticipants();
            for(int i=0;i<alPpt[0].size();i++)
            {
                String val=alPpt[0].get(i)+" ("+alPpt[1].get(i)+")";
                if(!dlmPartpnt.contains(val))dlmPartpnt.addElement(val);
            }
            al.add(dlmPartpnt);
            al.add(alPpt);
        }
        catch(Exception e)
        {
            System.out.println("Excep in setPptList="+e);e.printStackTrace();
        }
        return al;
    }
    
    static String getPublicIP()
    {
        String ipAdd=null;
        try
        {
            URL extIP=new URL("http://checkip.amazonaws.com");
            BufferedReader br=new BufferedReader(new InputStreamReader(extIP.openStream()));
            ipAdd=br.readLine();
            System.out.println("My External ip address="+ipAdd);
            br.close();
        }
        catch(Exception e)
        {
            System.out.println("Excep in getPubIP="+e);
        }
        return ipAdd;
    }
    
//    public static ArrayList[] setDefaultCboMdl(JComboBox cbo,DefaultComboBoxModel dcbm)
//    {
//        ArrayList al[]=new ArrayList[2];
//        try
//        {
//            al[0]=new ArrayList();
//            al[1]=new ArrayList();
//            String colCrs[]={"Course_name"};
//            String condtn[]={"Presenter_unm"};
//            String val[]={username};
//            ArrayList alResult=db.select("Course",colCrs,condtn,val,null);
//            Iterator it=alResult.iterator();
//            
//            dcbm=(DefaultComboBoxModel<String>)cbo.getModel();
//            
//            while(it.hasNext())
//            {
//                String str=(String)((ArrayList)it.next()).get(0);
//                al[0].add(str);
//                dcbm.addElement(str);
//            }
//            al[1].add(dcbm);
////            cboCoursenm.setModel(dcbmCrs);
//            
////            String colLect[]={"Lect_name"};
////            alLectnm=db.select("Lecture", colLect, null, null, null);
//            
//        }
//        catch(Exception e)
//        {
//            System.out.println("Excep in setCrsLectLsit="+e);e.printStackTrace();
//        }
//        return al;
//    }
    
//    public static void setCboModel(DefaultComboBoxModel cboMdl,String str,JComboBox cbo,JTextField txtEditor)
//    {
//        //dcbmCrs=cboMdl;
//        cbo.setModel(cboMdl);
//        cbo.setSelectedIndex(-1);
//        txtEditor.setText(str);
//    }
    
//    public static DefaultComboBoxModel getFilteredModel(ArrayList<String> al,String strCboEdtr)
//    {
//        DefaultComboBoxModel dcbm=new DefaultComboBoxModel();
//        Iterator<String> it=al.iterator();
//        while(it.hasNext())
//        {
//            String str=it.next();
//            if((str.toLowerCase()).startsWith(strCboEdtr.toLowerCase()))  dcbm.addElement(str);
//        }
//        return dcbm;
//    }
//    
//    public static boolean showCboPopup(final JTextField txtCboEditor,final ArrayList al,final JComboBox cbo,final boolean flg)
//    {
//        try
//        {
//            //makePopupVisible();
//            System.out.println("cboPopup called");
//            EventQueue.invokeLater(new Runnable() {
//                boolean flag=flg;
//                @Override
//                public void run() {
//                    String crsnm=txtCboEditor.getText();
//                    if(crsnm.length()>0)
//                    {
//                        DefaultComboBoxModel cmdl=getFilteredModel(al, crsnm);
//                        if(cmdl.getSize()==0 || flag)
//                        {
//                            cbo.hidePopup();
//                            flag=false;
//                            GenClass.setFlag(flag);
//                        }
//                        else
//                        {
//                            setCboModel(cmdl, crsnm,cbo,txtCboEditor);
//                            cbo.showPopup();
//                        }
//                    }
////                    else
////                    {
////                        cboCoursenm.hidePopup();
////                     //   setCboModel(dcbmCrs,"");
////                    }
//                }
//            });
//            
//        }
//        catch(Exception e)
//        {
//            System.out.println("Excep in showCbo="+e);e.printStackTrace();
//        }
//        return hideFlg;
//    }
//    
//    private static void setFlag(boolean f)
//    {
//        hideFlg=f;
//    }
    
    public static DefaultComboBoxModel setComboBoxModel(JComboBox cbo,String tblnm,String[] colnm,String[] condtn,String[] val,String[] opt)
    {
        DefaultComboBoxModel dcbmCrs=null;
        try
        {
            ArrayList alResult=db.select(tblnm,colnm,condtn,val,opt);
            Iterator it=alResult.iterator();
            
            dcbmCrs=(DefaultComboBoxModel<String>)cbo.getModel();            
            while(it.hasNext())
            {
                String str=(String)((ArrayList)it.next()).get(0);
                dcbmCrs.addElement(str);
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in setCrsLectLsit="+e);e.printStackTrace();
        }
        return dcbmCrs;
    }
}
