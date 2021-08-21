/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author DARSHAN
 */
public class Login extends JFrame {
    
    GridBagLayout gbl=new GridBagLayout();
    JButton btnLogin=new JButton("Login");
    
    JLabel lblWelcome=new JLabel("Welcome",JLabel.LEFT);
    JLabel lblUnm=new JLabel("User Name:");
    JLabel lblpwd=new JLabel("Password:");
    JLabel lblBtnpress=new JLabel();
    
    JTextField txtUnm=new JTextField(20);
    JPasswordField jpwd=new JPasswordField(20);
    
    JPanel pnlMain=new JPanel();
    
    Login()
    {
        createForm();
    }
    
    void createForm()
    {
        try
        {   if(txtUnm instanceof JTextField)
            {
                System.out.println("Hello");
            }
            pnlMain.setLayout(gbl);
            pnlMain.setBorder(BorderFactory.createLineBorder(Color.RED));
            JPanel pnlWelcome=new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlWelcome.setBorder(BorderFactory.createLineBorder(Color.yellow));
            GridBagConstraints con=new GridBagConstraints();
            con.fill=GridBagConstraints.HORIZONTAL;
            con.anchor=GridBagConstraints.EAST;
            con.ipady=40;
            con.weightx=1.0;
            con.gridwidth=2;
            con.gridx=0;
            con.gridy=0;
            lblWelcome.setFont(new Font("Tahoma", Font.PLAIN, 20));
            lblWelcome.setBorder(BorderFactory.createLineBorder(new Color(0,152,217),2));
            pnlWelcome.add(lblWelcome);
            pnlMain.add(pnlWelcome, con);
            
            con=new GridBagConstraints();
            con.fill=GridBagConstraints.VERTICAL;
            con.weightx=0.5;
            con.gridx=0;
            con.gridy=2;
            lblUnm.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            pnlMain.add(lblUnm,con);
            
            con=new GridBagConstraints();
            con.fill=GridBagConstraints.VERTICAL;
            con.weightx=0.5;
     //       con.gridwidth=2;
            con.gridx=1;
            con.gridy=2;
            txtUnm.setBorder(BorderFactory.createLineBorder(new Color(1,152,217),1,true));
            pnlMain.add(txtUnm,con);
            
            con=new GridBagConstraints();
            con.fill=GridBagConstraints.VERTICAL;
            con.weightx=0.5;
            con.gridx=0;
            con.gridy=3;
            lblpwd.setBorder(BorderFactory.createLineBorder(Color.GREEN));
            pnlMain.add(lblpwd,con);
            
            con=new GridBagConstraints();
            con.fill=GridBagConstraints.VERTICAL;
            con.weightx=0.5;
       //     con.gridwidth=2;
            con.gridx=1;
            con.gridy=3;
            jpwd.setBorder(BorderFactory.createLineBorder(new Color(0,152,217), 2,true));
            pnlMain.add(jpwd,con);
            
            con=new GridBagConstraints();
            con.fill=GridBagConstraints.NONE;
            con.weightx=0.5;
            con.gridx=1;
            con.gridy=5;
            con.insets=new Insets(10,0,0,0);
            con.anchor=GridBagConstraints.EAST;
            btnLogin.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            pnlMain.add(btnLogin,con);
            
            getContentPane().setLayout(new FlowLayout(FlowLayout.CENTER));
            getContentPane().add(pnlMain);
   //         setUndecorated(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setFont(new Font("Papyrus", Font.BOLD, 20));
            setSize(500, 500);
            
            setLocation(500, 150);
            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Exception in createForm="+e);
        }
    }
    
    public static void main(String args[])
    {
        Login l=new Login();
    }
}
