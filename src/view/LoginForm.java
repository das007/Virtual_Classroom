/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.PptForgotPwdClass;
import controller.PptLoginClass;
import java.awt.CardLayout;
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
import javax.swing.JFrame;
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
public class LoginForm extends JFrame implements ActionListener {
    
    private JLabel lblUnm=new JLabel("User Name:");
    private JLabel lblPwd=new JLabel("Password:");
    private JLabel lblForgotpwd=new JLabel("Forgot Password?");
    
    private JTextField txtUnm=new JTextField(20);
    private JPasswordField jpwd=new JPasswordField(20);
    
    private JButton btnLogin=new JButton("Login");
    private JButton btnRegister=new JButton("Register");
    private JButton btnForgotpwd=new JButton("Forgot Password?");
    
    JPanel pnlMain=new JPanel();
    //public static String port="1521",sid="orcl",dbUser="C##VClassroom007",dbPwd="Virtual007";
    DBClass db;
    
    JPanel pnlCardMain;
    CardLayout card;
    LectureFrame lectFrm;
    
    boolean presenter=true;
    
    public LoginForm(boolean p)
    {
        presenter=p;
        createForm();
    }
    
    void setCardInfo(JPanel pnl,CardLayout cd,LectureFrame lf)
    {
        pnlCardMain=pnl;
        card=cd;
        lectFrm=lf;
        System.out.println("Card="+card.toString()+" pnl="+pnlCardMain.toString()+" lectFrm="+lectFrm.toString());
    }
    
    private void createForm()
    {
        try
        {
            db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
            
            JPanel pnlUnm=new JPanel(new FlowLayout());
            pnlUnm.add(lblUnm);
            pnlUnm.add(txtUnm);
            
            JPanel pnlPwd=new JPanel(new FlowLayout());
            pnlPwd.add(lblPwd);
            pnlPwd.add(jpwd);
            
            btnLogin.addActionListener(this);
            btnRegister.addActionListener(this);
            btnForgotpwd.addActionListener(this);
            
            JPanel pnlBtn=new JPanel(new FlowLayout());
            pnlBtn.add(btnLogin);
            pnlBtn.add(btnRegister);
            
            btnForgotpwd.setFont(new Font("Times New Roman",Font.BOLD | Font.ITALIC,14));
            btnForgotpwd.setForeground(Color.RED);
            JPanel pnlFPwd=new JPanel(new FlowLayout());
            pnlFPwd.add(btnForgotpwd);
            
            pnlMain.setLayout(new GridLayout(4,1));
            pnlMain.add(pnlUnm);
            pnlMain.add(pnlPwd);
            pnlMain.add(pnlBtn);
            pnlMain.add(pnlFPwd);
            
            getContentPane().add(pnlMain);
            if(presenter)
            {
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
            else
            {
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
            setTitle("Virtual Classroom");
            setSize(300,250);
            setResizable(false);
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
        
        JButton btn=(JButton)ae.getSource();
        try
        {
        if(btn.equals(btnLogin))
        {
//            DBClass db=new DBClass("1521", "orcl", "C##VClassroom007", "Virtual007");
                String usernm=txtUnm.getText().trim();
                String pwd=String.valueOf(jpwd.getPassword()).trim();
                if(usernm.equals("") || pwd.equals(""))
                {
                    JOptionPane.showMessageDialog(this, "Username or password should not be blank.","Error",JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    if(presenter)
                    {
                        String cols[]={"*"};
                        String condtn[]={"User_name","Password","User_role"};
                        String val[]= {usernm,pwd,"P"};
                        String opt[]={"AND","AND"};
                        ArrayList alResult=db.select("UserInfo",cols,condtn,val,opt);
                        if(!alResult.isEmpty())
                        {
                            VCMain vc=new VCMain(usernm);
                            dispose();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this, "Username or password is incorrect...","Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                     //   Socket sok=LectGenClass.getClientSocket();
                  //      ObjectOutputStream oos=new ObjectOutputStream(sok.getOutputStream());
                        ObjectOutputStream oos=LectGenClass.getOos();
                        PptLoginClass pptLogin=new PptLoginClass(usernm,pwd);
                        oos.writeObject(pptLogin);
                        oos.flush();
                        
                        ObjectInputStream ois=LectGenClass.getOis();
                        Boolean valid=(Boolean)ois.readObject();
                        if(valid)
                        {
                            lectFrm.setIsPresenter(presenter);
                            card.show(pnlCardMain, "LectFrame");
                            dispose();
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this, "Username or password is incorrect...","Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
        }
        else if(btn.equals(btnRegister))
        {
            User_Registration ur=new User_Registration(presenter);
        }
        else
        {
            //E-Mail pwd to user.
            String usernm=txtUnm.getText().trim();
            boolean success=false;
            if(!usernm.equals(""))
            {
                //select pwd from UserInfo where unm="";
                if(presenter)
                {
                    String colnm[]={"Password","Email_id"};
                    String condtn[]={"User_name"};
                    String val[]={usernm};

                    ArrayList alResult=db.select("UserInfo", colnm, condtn, val, null);
                    if(alResult.size()>0)
                    {
                        String password=((ArrayList)alResult.get(0)).get(0).toString();
                        String email=((ArrayList)alResult.get(0)).get(1).toString();

                        String toEmail[]={email};
                        EmailClass ec=new EmailClass();
                        ec.sendEmail(toEmail,"Virtual Classroom - Password",usernm+", Your passsword is "+password);
                        success=true;
    //                    JOptionPane.showMessageDialog(this, "Your password is sent to your email id.","Forgot Password",JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else
                {
                    Socket sok=LectGenClass.getClientSocket();
                    ObjectOutputStream oos=new ObjectOutputStream(sok.getOutputStream());
                    PptForgotPwdClass pfc=new PptForgotPwdClass(usernm);
                    oos.writeObject(pfc);
                    oos.flush();

                    ObjectInputStream ois=new ObjectInputStream(sok.getInputStream());
                    success=(Boolean)ois.readObject();
                }
                
                if(success)
                {
                    JOptionPane.showMessageDialog(this, "Your password is sent to your email id.","Forgot Password",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Username '"+usernm+"' does not exist.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(this, "Please enter Username.","Error",JOptionPane.ERROR_MESSAGE);
            }
        }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerfm="+e);e.printStackTrace();
        }
    }
    
    public static void main(String arg[])
    {
        LoginForm lf=new LoginForm(true);
    }
}
