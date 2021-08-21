/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author DARSHAN
 */
public class ConnectFrame extends JDialog implements ActionListener {
    
    JTextField[] txtIP;
    JLabel lblIp;
    JButton btnConnect,btnCancel;
    
    JPanel pnlIp,pnlBtn,pnlIPMain;
    
//    String strIpAdd;
    Socket clientSok;
    ObjectInputStream ois;
    ObjectOutputStream oos;
    
    JPanel pnlMain;
    CardLayout card;
    LectureFrame lectFrm;

    public ConnectFrame(JPanel pnl,CardLayout cd,LectureFrame lf)
    {
        pnlMain=pnl;
        card=cd;
        lectFrm=lf;
        createFrame();
    }
    
    
    private void createFrame()
    {
        try
        {
            lblIp=new JLabel("Enter IP address of Presenter",JLabel.CENTER);

            txtIP=new JTextField[4];
            pnlIp=new JPanel(new FlowLayout(FlowLayout.CENTER,10,0));
            for(int i=0;i<txtIP.length;i++)
            {
                txtIP[i]=new JTextField();
                txtIP[i].setPreferredSize(new Dimension(55, 35));
                pnlIp.add(txtIP[i]);
            }

            btnConnect=new JButton("Connect");
            btnConnect.addActionListener(this);

            btnCancel=new JButton("Cancel");
            btnCancel.addActionListener(this);

            pnlBtn=new JPanel(new FlowLayout());
            pnlBtn.add(btnConnect);
            pnlBtn.add(btnCancel);

            pnlIPMain=new JPanel(new GridLayout(2,1));
            pnlIPMain.add(lblIp);
            pnlIPMain.add(pnlIp);

            setLayout(new BorderLayout());
            add(pnlIPMain,BorderLayout.NORTH);
            add(pnlBtn,BorderLayout.SOUTH);

            setSize(300,170);
            setTitle("Connect");
     //       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
    //        setModal(true);
            setResizable(false);
            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createFrm="+e);e.printStackTrace();
        }
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            JButton btn=(JButton)ae.getSource();
            if(btn.equals(btnConnect))
            {
                for(int i=0;i<txtIP.length;i++)
                {
                    int ip=Integer.parseInt(txtIP[i].getText());
                    if(ip<0 || ip>255)
                    {
                        throw new NumberFormatException();
                    //    JOptionPane.showMessageDialog(this,"Invalid IP address.\nEnter value between 0 and 255.","Invalid IP address",JOptionPane.ERROR_MESSAGE);
                    }
                }
                String strIpAdd="";
                int i;
                for(i=0;i<txtIP.length-1;i++)
                {
                    strIpAdd+=txtIP[i].getText().trim()+".";
                }
                strIpAdd+=txtIP[i].getText().trim();
                setUpNetworking(strIpAdd);
                LoginForm login=new LoginForm(false);
                login.setCardInfo(pnlMain, card, lectFrm);
                dispose();
            }
            else
            {
                dispose();
            }
        }
        catch(NumberFormatException ne)
        {
            JOptionPane.showMessageDialog(this,"Invalid IP address.\nEnter value between 0 and 255.\nCharacters not allowed.","Invalid IP address",JOptionPane.ERROR_MESSAGE);
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerf="+e);e.printStackTrace();
        }
    }
    
    public void setUpNetworking(String ipAdd)
    {
        try
        {
            clientSok=new Socket(ipAdd,7777);
            System.out.println("Client Connected to server");
            LectGenClass.setClientSocket(clientSok);
//     //       oos=new ObjectOutputStream(clientSok.getOutputStream());
//            System.out.println("Client Connected to server");
//            //ois=new ObjectInputStream(clientSok.getInputStream());
//            System.out.println("Client Connected to server");
            System.out.println("Client connected to "+clientSok.getInetAddress());
        }
        catch(Exception e)
        {
            System.out.println("Excpe in setpuNetwkClient="+e);e.printStackTrace();
        }
    }
    
    public static void main(String arg[])
    {
     //   ConnectFrame con=new ConnectFrame();
    }
}
