/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.PptLoginClass;
import controller.PptProfileClass;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
//import java.awt.GridLayout;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
//import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
//import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.DBClass;

/**
 *
 * @author DARSHAN
 */
public class EditProfileFrm extends JDialog implements ActionListener
{
//    private JTextField txtUsernm,txtFirstnm,txtLastnm,txtEmail;
//    private JPasswordField pwdCurr,pwdNew,pwdConfirm;
    private String lblTxt[]={"Username","First Name","Last Name","Email ID"};
//    private final String lblPwd[]={"Current Password:","New Password:","Confirm Password:"};
    private HashMap<String,JTextField> hmTxt;
    JPasswordField pwdEmail;
//    private HashMap<String,JPasswordField> hmPwd;
    
    JButton btnSave,btnCancel,btnChgPwd;
    
    JPanel pnlMain,pnlForm,pnlButton;
    String usernm;
    //DBClass db=new DBClass(LoginForm.port,LoginForm.sid,LoginForm.dbUser,LoginForm.dbPwd);
    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
    
    boolean presenter;

    public EditProfileFrm(String unm,boolean flg) 
    {
        usernm=unm;
        presenter=flg;
        createForm();
    }
    
    private void createForm()
    {
        try
        {
   //         JPanel pnlPersonalInfo=createPanel(true, lblTxt);
            
            if(presenter)
            {
                String lbl[]={"Username","First Name","Last Name","Email ID","Email Password"};
                lblTxt=lbl;
            }
            
            
            pnlForm=new JPanel();
            pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
            ArrayList alValues=getFormValues();
            
            PanelContainer pc;
            hmTxt=new HashMap<>();
            for (int i=0;i<lblTxt.length;i++) 
            {   
                JTextField txt;
                if(presenter && i==(lblTxt.length-1))
                {
                    txt=new JPasswordField(15);
                }
                else
                {
                    txt=new JTextField(15);
                }
                hmTxt.put(lblTxt[i], txt);
                if(lblTxt[i].equals("Username"))
                {
                    txt.setText(usernm);
                    txt.setEnabled(false);
                }
                else
                {
                    txt.setText(alValues.get(i-1).toString());
                }
                pc=new PanelContainer();
                pc.pnllbl.add(new JLabel(lblTxt[i]+":"));
                pc.pnltxt.add(txt);
                pnlForm.add(Box.createRigidArea(new Dimension(0,10)));
                pnlForm.add(pc);
            }
            
            btnSave=new JButton("Save");
            //btnCancel=new JButton("Cancel");
            btnChgPwd=new JButton("Change Password");

            btnSave.addActionListener(this);
            //btnCancel.addActionListener(this);
            btnChgPwd.addActionListener(this);

            pnlButton=new JPanel(new FlowLayout());
            pnlButton.add(btnSave);
            //pnlButton.add(btnCancel);
            pnlButton.add(btnChgPwd);
            
            pnlMain=new JPanel(new BorderLayout(0,30));
            pnlMain.add(pnlForm,BorderLayout.CENTER);
            pnlMain.add(pnlButton,BorderLayout.SOUTH);
            
            getContentPane().setLayout(new FlowLayout());
            getContentPane().add(pnlMain);

            setTitle("Edit Profile");
            setSize(350,320);
            setModalityType(DEFAULT_MODALITY_TYPE);
            setLocationRelativeTo(null);
        //    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createFrm="+e);e.printStackTrace();
        }
    }
    
    private ArrayList getFormValues()
    {
        ArrayList result=null;
        try
        {
            String colnm[];
            if(presenter)
            {
                String col[]={"First_name","Last_name","Email_id","Email_pwd"};
                colnm=col;
            }
            else
            {
                String col[]={"First_name","Last_name","Email_id"};
                colnm=col;
            }
            String condtn[]={"User_name"};
            String val[]={usernm};
            ArrayList al=db.select("UserInfo",colnm,condtn,val,null);
            result=(ArrayList)al.get(0);
        }
        catch(Exception e)
        {
            System.out.println("Excep in getFormVal="+e);e.printStackTrace();
        }
        return result;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        JButton btn=(JButton)ae.getSource();
        
        try
        {
            if(btn.equals(btnChgPwd))
            {
                ChgPasswd cp=new ChgPasswd(usernm);
            }
            else if(btn.equals(btnSave))
            {
                String fnm=hmTxt.get("First Name").getText().trim();
                String lnm=hmTxt.get("Last Name").getText().trim();
                String email=hmTxt.get("Email ID").getText().trim();
                String emailPwd=null;
                boolean flg=false;
                if(presenter)
                {
                    JPasswordField pwd=(JPasswordField)hmTxt.get("Email Password");
                    emailPwd=String.valueOf(pwd.getPassword());
                    flg=(!fnm.equals("") && !lnm.equals("") && !email.equals("") && !emailPwd.equals(""));
                }
                else
                {
                    flg=(!fnm.equals("") && !lnm.equals("") && !email.equals(""));
                }
                if(flg)
                {
                    String colnm[],exp[];
                    boolean success=false;
                    if(presenter)
                    {
                        String col[]={"First_name","Last_name","Email_id","Email_pwd"};
                        colnm=col;

                        String e[]={fnm,lnm,email,emailPwd};
                        exp=e;
                        String condtn[]={"User_name"};
                        String val[]={usernm};

                        int result=db.update("UserInfo",colnm,exp,condtn,null,val);
                        if(result>0) success=true;
                    }
                    else
                    {
                        String col[]={"First_name","Last_name","Email_id"};
                        colnm=col;

                        String e[]={fnm,lnm,email};
                        exp=e;

                        PptProfileClass ppc=new PptProfileClass(usernm, exp);
                        Socket sok=LectGenClass.getClientSocket();
                        ObjectOutputStream oos=new ObjectOutputStream(sok.getOutputStream());
                        oos.writeObject(ppc);
                        oos.flush();

                        ObjectInputStream ois=new ObjectInputStream(sok.getInputStream());
                        success=(Boolean)ois.readObject();
                    }

        //                String condtn[]={"User_name"};
        //                String val[]={usernm};
        //
        //                int result=db.update("UserInfo",colnm,exp,condtn,null,val);
                    if(success)
                    {
                        JOptionPane.showMessageDialog(this,"Your Profile edited successfully","Edit Profile",JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, "Some error occured.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Fields should not be blank.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {

            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerfm="+e);e.printStackTrace();
        }
    }
    
    public static void main(String arg[])
    {
        EditProfileFrm ed=new EditProfileFrm("Tom007",true);
    }
}

//class GridLayoutForm extends JComponent
//{
//    JPanel pnl1=new JPanel(new FlowLayout(FlowLayout.RIGHT));
//    JPanel pnl2=new JPanel(new FlowLayout(FlowLayout.LEFT));
//
//    public GridLayoutForm() 
//    {
//        setLayout(new GridLayout(1,2));
//        add(pnl1);
//        add(pnl2);
//    }
//    
//    
//}
