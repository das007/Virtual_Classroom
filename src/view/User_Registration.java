/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.PptRegisterClass;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.DBClass;

/**
 *
 * @author DARSHAN
 */
public class User_Registration extends JDialog implements ActionListener {

 //   JLabel lblFirstnm,lblLastnm,lblUsernm,lblPassword,lblConfirmpwd,lblEmail;
    
 //   JLabel lbl[]=new JLabel[6];
    
    private final JLabel lblMandatory=new JLabel("All fields are Mandatory");
    private final JTextField txtFirstnm,txtLastnm,txtUsernm,txtEmail;
    private final JPasswordField txtPassword,confirmPwd;
    JPasswordField emailPwd=null;
    private final JButton btnRegister,btnReset;
    private final ArrayList al=new ArrayList();
    private final Object objTxt[];
    boolean presenter=true;
    
    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
    
//    @SuppressWarnings("empty-statement")
    public User_Registration(boolean p)
    {
//        lblFirstnm=new JLabel("First Name:");
//        lblLastnm=new JLabel("Last Name:");
//        lblUsernm=new JLabel("User Name:");
//        lblPassword=new JLabel("Password:");
//        lblConfirmpwd=new JLabel("Confirm Password:");
//        lblEmail=new JLabel("Email:");
      
        presenter=p;
        
        txtFirstnm=new JTextField(15);
        txtLastnm=new JTextField(15);
        txtUsernm=new JTextField(15);
        txtEmail=new JTextField(15);
        
        txtPassword=new JPasswordField(15);
        confirmPwd=new JPasswordField(15);
        if(presenter)
        {
            emailPwd=new JPasswordField(15);
            Object obj[]={txtFirstnm,txtLastnm,txtUsernm,txtPassword,confirmPwd,txtEmail,emailPwd};
            objTxt=obj;
        }
        else
        {
            Object obj[]={txtFirstnm,txtLastnm,txtUsernm,txtPassword,confirmPwd,txtEmail};
            objTxt=obj;
        }
        btnRegister=new JButton("Register");
        btnReset=new JButton("Reset");
       
        createForm();
    }
    
    private void createForm()
    {
        try
        {
            JPanel pnlForm=new JPanel(new GridLayout(7,1,0,10));
            String[] lblName;
            if(presenter)
            {
                String lblNm[]={"First Name:","Last Name:","User Name:","Password:","Confirm Password:","Email:","Email Password:"};
                lblName=lblNm;
            }
            else
            {
                String lblNm[]={"First Name:","Last Name:","User Name:","Password:","Confirm Password:","Email:"};
                lblName=lblNm;
            }
            
            PanelContainer pnl;
            for (int i=0;i<lblName.length;i++) 
            {
                pnl=new PanelContainer();
                pnl.pnllbl.add(new JLabel(lblName[i]));     
                
                if(objTxt[i] instanceof JTextField)
                {
                    pnl.pnltxt.add((JTextField)objTxt[i]);
                }
                else
                {
                    pnl.pnltxt.add((JPasswordField)objTxt[i]);
                }
                pnlForm.add(pnl);
            }
           
            JPanel pnlBtn=new JPanel();
            btnRegister.addActionListener(this);
            btnReset.addActionListener(this);
            pnlBtn.add(btnRegister);
            pnlBtn.add(btnReset);
            
            lblMandatory.setFont(new Font("Times New Roman",Font.BOLD | Font.ITALIC,14));
            lblMandatory.setForeground(Color.RED);
            JPanel pnlMandatory=new JPanel(new FlowLayout());
            pnlMandatory.add(lblMandatory);
            
            JPanel pnlMain=new JPanel(new BorderLayout(0,30));
            pnlMain.add(pnlMandatory,BorderLayout.NORTH);
            pnlMain.add(pnlForm,BorderLayout.CENTER);
            pnlMain.add(pnlBtn,BorderLayout.SOUTH);
            
  //          pnlMain.setBorder(BorderFactory.createLineBorder(Color.RED));
            getContentPane().setLayout(new FlowLayout());
            getContentPane().add(pnlMain);
            
            //setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setTitle("User Registration");
            setSize(350,450);
 //           setResizable(false);
  //          System.out.print("Hello");
            setModalityType(DEFAULT_MODALITY_TYPE);
            setLocationRelativeTo(null);
            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createForm="+e);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            JButton btn=(JButton)ae.getSource();
            if(btn.equals(btnReset))
            {
                for (Object obj1 : objTxt) 
                {
                    if (obj1 instanceof JTextField) 
                    {
                        JTextField jt = (JTextField) obj1;
                        jt.setText("");
                    } 
                    else 
                    {
                        JPasswordField jp = (JPasswordField) obj1;
                        jp.setText("");
                    }            
                }
            }
            else if(btn.equals(btnRegister))
            {
                boolean flg=true;
                for(int i=0;i<objTxt.length;i++)
                {
                    if(((JTextField)objTxt[i]).getText().trim().equals(""))
                    {
                        flg=false;
                        break;
                    }
                }
                if(flg && ((JTextField)objTxt[3]).getText().trim().equals(((JTextField)objTxt[4]).getText().trim()))
                {
                    ArrayList<String> alValues=new ArrayList<>();
                    String val;
                    for (int i=0;i<objTxt.length;i++) 
                    {
                        if(i!=4)
                        {
                            if (objTxt[i] instanceof JTextField) 
                            {
                                val = ((JTextField) objTxt[i]).getText();
                            } 
                            else 
                            {
                                val = String.valueOf(((JPasswordField) objTxt[i]).getPassword());
                            }
                            alValues.add(val);
                        }
                    }
                    
                    boolean flag=false;
                    if(presenter)
                    {
                        alValues.add("P");
                        int rows=db.insert("UserInfo", alValues);
                        if(rows>0)
                        {
                            flag=true;   
                        }
                    }
                    else
                    {
                        alValues.add(null);
                        alValues.add("V");
                        
                        PptRegisterClass pptReg=new PptRegisterClass(alValues);
                //        Socket sok=LectGenClass.getClientSocket();
                        ObjectOutputStream oos=LectGenClass.getOos();
                        oos.writeObject(pptReg);
                        oos.flush();
                        
                        ObjectInputStream ois=LectGenClass.getOis();
                        Boolean success=(Boolean)ois.readObject();
                        flag=success;
                    }
                    if(flag)
                    {
                        JOptionPane.showMessageDialog(this,"User '"+txtUsernm.getText()+"' registered successfully...","Successful Registration",JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this,"User '"+txtUsernm.getText()+"' already exists.\nPlease choose some other username.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else
                {
                    if(flg)
                    {
                        JOptionPane.showMessageDialog(this,"Passwords did not match.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this,"All fields are mandatory.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actionPerform="+e);
        }
    }
    
    public static void main(String arg[])
    {
        User_Registration user=new User_Registration(true);
    }
       
}


class PanelContainer extends JComponent
{
    JPanel pnllbl=new JPanel(new FlowLayout(FlowLayout.LEFT));
    JPanel pnltxt=new JPanel(new FlowLayout(FlowLayout.RIGHT));

    public PanelContainer() 
    {
        setLayout(new BorderLayout());
  //      setBorder(BorderFactory.createLineBorder(Color.yellow));
 //       pnllbl.setBorder(BorderFactory.createLineBorder(Color.BLUE));
   //     pnltxt.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        add(pnllbl,BorderLayout.WEST);
        add(pnltxt,BorderLayout.CENTER);
    }
   
}