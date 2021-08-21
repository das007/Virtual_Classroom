/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import model.DBClass;

/**
 *
 * @author DARSHAN
 */
public class DelParticipant extends JPanel implements ActionListener
{
    JLabel lblPt=new JLabel("Delete Participants");
    JList lstParticipant;
    DefaultListModel dlmPartpnt;
    JScrollPane jspPt;
    JButton btnDelete=new JButton("Delete");
    JButton btnDeleteAll=new JButton("Delete All");
    JPanel pnlPt,pnlLbl,pnlBtn,pnlMain;
    
    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
    String participants[]=null;
    ArrayList partpnt[]=new ArrayList[2];

    public DelParticipant()
    {
        createForm();
    }
    
    private void createForm()
    {
        try
        {
            btnDeleteAll.addActionListener(this);
            btnDelete.addActionListener(this);
            
        //    partcipants=GenClass.getParticipants();
//            partpnt=GenClass.getParticipants();
//            //participants=new String[partpnt[0].length];
//            for(int i=0;i<partpnt[0].length;i++)
//            {
//                //participants[i]=partpnt[0][i]+" ("+partpnt[1][i]+")";
//                dlmPartpnt.addElement(partpnt[0][i]+" ("+partpnt[1][i]+")");
//            }
            
            dlmPartpnt=new DefaultListModel();
            lstParticipant=new JList(dlmPartpnt);
            lstParticipant.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            jspPt=new JScrollPane(lstParticipant);
            
            pnlLbl=new JPanel();
            pnlLbl.add(lblPt);
          
            pnlPt=new JPanel();
            pnlBtn=new JPanel();
            pnlBtn.add(btnDelete);
            pnlBtn.add(btnDeleteAll);
            
            pnlMain=new JPanel(new BorderLayout(10,10));
            pnlMain.add(pnlLbl,BorderLayout.NORTH);
            pnlMain.add(jspPt);
            pnlMain.add(pnlBtn,BorderLayout.SOUTH);
            pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            
            setLayout(new GridLayout());
            add(pnlMain);
            
//            getContentPane().add(pnlMain);
//            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//            setSize(500,500);
//            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Exception in createForm="+e);
        }
    }
    
    void setPptList()
    {
        ArrayList al=GenClass.setPptList();
        dlmPartpnt=(DefaultListModel)al.get(0);
        partpnt=(ArrayList[])al.get(1);
        lstParticipant.setModel(dlmPartpnt);
//        try
//        {
//            partpnt=GenClass.getParticipants();
//            for(int i=0;i<partpnt[0].length;i++)
//            {
//                String val=partpnt[0][i]+" ("+partpnt[1][i]+")";
//                if(!dlmPartpnt.contains(val))dlmPartpnt.addElement(val);
//            }
//        }
//        catch(Exception e)
//        {
//            System.out.println("Excep in setPptList="+e);e.printStackTrace();
//        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            JButton btn=(JButton)ae.getSource();
            if(btn.equals(btnDeleteAll))
            {
                int option=JOptionPane.showConfirmDialog(this, "Are you sure to delete all participants?","Delete Participant",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(option==JOptionPane.YES_OPTION)
                {
                    String condtn[]={"User_role"};
                    String val[]={"V"};
                    int result=db.delete("UserInfo",condtn,val, null);
                    if(result>0)
                    {
                        dlmPartpnt.removeAllElements();
                    }
                }
                //DefaultListModel dlm=(DefaultListModel)lstParticipant.getModel();
                //dlmPartpnt.removeAllElements();
                //lstParticipant.setModel(dlmPartpnt);
            }
            else if(btn.equals(btnDelete))
            {
                int option=JOptionPane.showConfirmDialog(this, "Are you sure to delete the selected participants?","Delete Participant",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                if(option==JOptionPane.YES_OPTION)
                {
                    int result=0;
                    int index[]=lstParticipant.getSelectedIndices();
                    String condtn[]={"User_name"};
                    for(int i=0;i<index.length;i++)
                    {
                        String val[]={(String)partpnt[1].get(index[i])};
                        result+=db.delete("UserInfo",condtn,val,null);
                    }
                    if(result==index.length)
                    {
                        List lst=lstParticipant.getSelectedValuesList();
                        Iterator it=lst.iterator();
                        while(it.hasNext())
                        {
                            dlmPartpnt.removeElement(it.next());
                        }
                    }
                }
              
          //      DefaultListModel dlmLst=(DefaultListModel)lstParticipant.getModel();
//                List lst=lstParticipant.getSelectedValuesList();
//             
//                Iterator it=lst.iterator();
//                while(it.hasNext())
//                {
//                    dlmPartpnt.removeElement(it.next());
//                }
                //lstParticipant.setModel(dlmLst);
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actionPerform="+e);e.printStackTrace();
        }
    }
    
    public static void main(String arg[])
    {
        DelParticipant dp=new DelParticipant();
    }
}
