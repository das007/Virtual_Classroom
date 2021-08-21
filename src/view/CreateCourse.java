/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import model.DBClass;

/**
 *
 * @author DARSHAN
 */
public class CreateCourse extends JPanel implements ActionListener,DocumentListener
{
    JLabel lblCrsnm=new JLabel("Course Name:");
    JLabel lblMyCrs=new JLabel("My Courses");
    JTextField txtCoursenm=new JTextField(20);
    JButton btnCreateCrs=new JButton("Create Course");
    JList lstCourse;
    DefaultListModel<String> dlmCrsnm;
    JScrollPane jspCourse;
    JPanel pnlCourse,pnlMain;
    
    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
////    String username=null;
    
    public CreateCourse()
    {
        createForm();
    }
    
    private void createForm()
    {
        try
        {
            txtCoursenm.getDocument().addDocumentListener(this);
            
            pnlCourse=new JPanel();
            pnlCourse.setLayout(new BoxLayout(pnlCourse, BoxLayout.X_AXIS));
            pnlCourse.add(lblCrsnm);
            pnlCourse.add(txtCoursenm);
            pnlCourse.add(Box.createRigidArea(new Dimension(10,0)));
    //        pnlCourse.add(Box.createHorizontalGlue());
            btnCreateCrs.setEnabled(false);
            btnCreateCrs.addActionListener(this);
            pnlCourse.add(btnCreateCrs);
            
            dlmCrsnm=new DefaultListModel<>();
            lstCourse=new JList(dlmCrsnm);
            jspCourse=new JScrollPane(lstCourse);
            
            JPanel pnlMyCrs=new JPanel(new BorderLayout(0,5));
            pnlMyCrs.add(lblMyCrs,BorderLayout.NORTH);
            pnlMyCrs.add(jspCourse,BorderLayout.CENTER);
            
//            pnlMain=new JPanel(new BorderLayout(0,15));
//            pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
//            pnlMain.add(pnlCourse,BorderLayout.NORTH);
//            pnlMain.add(pnlMyCrs,BorderLayout.CENTER);
            
            setLayout(new BorderLayout(0,15));
            setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            add(pnlCourse,BorderLayout.NORTH);
            add(pnlMyCrs,BorderLayout.CENTER);
            setDlmCourse();
      //      getContentPane().add(pnlMain);
      //      setSize(500,500);
      //      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //      setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createForm="+e);
        }
    }
    
//    void setUsernm(String unm)
//    {
//        username=unm;
//    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            System.out.println(GenClass.username);
            String crsnm=txtCoursenm.getText().trim();
            ArrayList alValue=new ArrayList();
            alValue.add(crsnm);
            alValue.add(GenClass.username);
            System.out.println(GenClass.username);
            int rows=db.insert("Course",alValue);
            
            //alValue=new ArrayList();
            //alValue.add(val);
            System.out.println(GenClass.username);
            if(rows>0)
            {
//                System.out.println(GenClass.username+" if");
//                alValue.add(GenClass.username);
//                int rows1=db.insert("Course_Presenter_Info", alValue);
                dlmCrsnm.addElement(crsnm);
                JOptionPane.showMessageDialog(this,"Course "+crsnm+" created successfully...","Success",JOptionPane.INFORMATION_MESSAGE);
                txtCoursenm.setText("");   
            }
            else
            {
                System.out.println(GenClass.username+" else");
                JOptionPane.showMessageDialog(this,"Some error occured.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerformed="+e);
        }
    }
    
    void setDlmCourse()
    {
        try
        {
            dlmCrsnm.removeAllElements();
            String col[]={"Course_name"};
            String condtn[]={"Presenter_unm"};
            String val[]={GenClass.username};
            ArrayList al=db.select("Course",col,condtn,val,null);
            Iterator it=al.iterator();
            while(it.hasNext())
            {
                String cnm=(String)((ArrayList)it.next()).get(0);
                dlmCrsnm.addElement(cnm);
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in dlmcourse="+e);
        }
    }
    
    @Override
    public void insertUpdate(DocumentEvent de)
    {
        try
        {
            enableBtn();
        }
        catch(Exception e)
        {
            System.out.println("Excep in insUpd="+e);
        }
    }
    
    @Override
    public void removeUpdate(DocumentEvent de)
    {
        try
        {
            enableBtn();
        }
        catch(Exception e)
        {
            System.out.println("Excep in remUpd="+e);
        }
    }
    
    @Override
    public void changedUpdate(DocumentEvent de)
    {
        try
        {
            enableBtn();
        }
        catch(Exception e)
        {
            System.out.println("Excep in chgUp="+e);
        }
    }
    
    private void enableBtn()
    {
        if(!txtCoursenm.getText().trim().equals(""))
        {
            btnCreateCrs.setEnabled(true);
        }
        else
        {
            btnCreateCrs.setEnabled(false);
        }
    }
    
//    public static void main(String arg[])
//    {
//        CreateCourse cc=new CreateCourse();
//    }
}
