/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import model.DBClass;

/**
 *
 * @author DARSHAN
 */
public class ChgPasswd extends JDialog implements ActionListener
{
    private final String lblPwd[]={"Current Password","New Password","Confirm Password"};
    private HashMap<String,JPasswordField> hmPwd;
    JPanel pnlMain,pnlForm,pnlButton;
    private JButton btnSave,btnCancel;
    String usernm;
    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
    
    public ChgPasswd(String unm)
    {
        usernm=unm;
        createForm();
    }
  
    private void createForm()
    {
        try
        {
            pnlForm=new JPanel();
            pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
            
            PanelContainer pc;
            hmPwd=new HashMap<>();
            for (String lblPwd1 : lblPwd) {
                JPasswordField pwd=new JPasswordField(15);
                hmPwd.put(lblPwd1, pwd);
                
                pc=new PanelContainer();
                pc.pnllbl.add(new JLabel(lblPwd1+":"));
                pc.pnltxt.add(pwd);
                pnlForm.add(Box.createRigidArea(new Dimension(0,10)));
                pnlForm.add(pc);
            }
            
            btnSave=new JButton("Save");
            btnSave.addActionListener(this);
            //btnCancel=new JButton("Cancel");
            pnlButton=new JPanel(new FlowLayout());
            pnlButton.add(btnSave);
            //pnlButton.add(btnCancel);

            pnlMain=new JPanel(new BorderLayout(0,30));
            pnlMain.add(pnlForm,BorderLayout.CENTER);
            pnlMain.add(pnlButton,BorderLayout.SOUTH);
            
            getContentPane().setLayout(new FlowLayout());
            getContentPane().add(pnlMain);

            setTitle("Change Password");
            setSize(400,250);
            setLocationRelativeTo(null);
            setModalityType(DEFAULT_MODALITY_TYPE);
            //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createFrm="+e);
            //e.printStackTrace();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            String newPwd=String.valueOf(hmPwd.get("New Password").getPassword());
            String confPwd=String.valueOf(hmPwd.get("Confirm Password").getPassword());
            boolean blnkflds=String.valueOf(hmPwd.get("Current Password").getPassword()).trim().equals("") || String.valueOf(hmPwd.get("New Password").getPassword()).trim().equals("") || String.valueOf(hmPwd.get("Confirm Password").getPassword()).trim().equals("");
            if(newPwd.equals(confPwd) && !blnkflds)
            {
                if(ae.getSource().equals(btnSave))
                {
                    String colnm[]={"Password"};
                    String exp[]={String.valueOf(hmPwd.get("New Password").getPassword())};
                    String condtn[]={"User_name","Password"};
                    String logop[]={"AND"};
                    String val[]={usernm,String.valueOf(hmPwd.get("Current Password").getPassword())};
                    int result=db.update("UserInfo",colnm,exp,condtn,logop,val);
                    if(result>0)
                    {
                        JOptionPane.showMessageDialog(this,"Password changed successfully.","Change password Success",JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, "Your old password is incorrect.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            else
            {
                if(blnkflds) JOptionPane.showMessageDialog(this, "Fields should not be blank","Error",JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(this, "New Password and Confirm Password did not match.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actionPerf="+e);
        }
    }
    
    public static void main(String arg[])
    {
        ChgPasswd cp=new ChgPasswd("Tom007");
    }
}
