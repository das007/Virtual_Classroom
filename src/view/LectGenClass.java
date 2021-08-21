/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DARSHAN
 */
public class LectGenClass {
    
    static String coursenm,lectnm;
    private static Socket clientSocket;
    private static ObjectOutputStream oos=null;
    private static ObjectInputStream ois=null;

    public static ObjectInputStream getOis() {
        return ois;
    }

    public static Socket getClientSocket() {
        return clientSocket;
    }

    public static ObjectOutputStream getOos() {
        return oos;
    }

    public static void setClientSocket(Socket clntSocket) {
        try
        {
            clientSocket = clntSocket;
            oos=new ObjectOutputStream(clientSocket.getOutputStream());
            ois=new ObjectInputStream(clientSocket.getInputStream());
        }
        catch(Exception e)
        {
            System.out.println("Excep in setClientSocket");
        }
    }
    
    
    public static void setCourse_Lect(String cnm,String lnm)
    {
        coursenm=cnm;
        lectnm=lnm;
        System.out.println("crsnm in LECTGENCLASS="+coursenm+" lectnm="+lectnm);
    }
    
    public static void updateUserTable(ArrayList alUser[])
    {
        ArrayList tblInfo=UsersFrame.getTableInfo();
        JScrollPane jspTable=(JScrollPane)tblInfo.get(0);
        JTable jt=(JTable)tblInfo.get(1);
        
        Iterator itUnm=alUser[0].iterator();
        Iterator itRole=alUser[1].iterator();
        while(itUnm.hasNext())
        {
            String row[]={itUnm.next().toString(),itRole.next().toString()};
            ((DefaultTableModel)jt.getModel()).addRow(row);
        }
        jspTable.setViewportView(jt);
    }
    
    
}
