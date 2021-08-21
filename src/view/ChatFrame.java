/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.ChatClass;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import model.DBClass;

/**
 *
 * @author DARSHAN
 */
public class ChatFrame extends JPanel implements ActionListener,ItemListener {
    
    CardLayout cd=new CardLayout();
    private JPanel pnlChat,pnlSave;
    private final JPanel pnlSendMsg=new JPanel();
    ArrayList alChat=new ArrayList();
    
    JLabel lblSendTo=new JLabel("Send to:");
    JTextField txtMsg=new JTextField(20);
    JButton btnSend=new JButton("Send");
    JButton btnSave=new JButton("Save");
    String str[]={"Everyone","Tom"};
    JComboBox<String> cboUser=new JComboBox<>(str);
    
    static JTextArea jtaChat;
    
    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
    
    ChatFrame()
    {
        createFrame();
    }
    
    private void createFrame()
    {
        try
        {
        //    createChatCardDeck();
          
            btnSave=new JButton("Save");
            btnSave.addActionListener(this);
            pnlSave=new JPanel(new BorderLayout());
            JPanel pnl=new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pnl.add(btnSave);
            pnlSave.add(pnl,BorderLayout.CENTER);
            pnlSave.add(new JSeparator(),BorderLayout.SOUTH);
            
            jtaChat=new JTextArea();
            jtaChat.setEditable(false);
            JScrollPane jsp=new JScrollPane(jtaChat);
            pnlChat=new JPanel(new GridLayout());
            pnlChat.add(jsp);
            
            cboUser.addItemListener(this);
            JPanel pnlUser=new JPanel(new FlowLayout());
            pnlUser.add(lblSendTo);
            pnlUser.add(cboUser);
            
            JPanel pnlTxt=new JPanel(new FlowLayout());
            pnlTxt.add(txtMsg);
            
            JPanel pnlMsg=new JPanel(new FlowLayout());
            pnlMsg.add(pnlUser);
            
            btnSend.addActionListener(this);
            JPanel pnlBtnSend=new JPanel(new FlowLayout());
          //  JPanel pnlBtnSend=new JPanel();
          //  pnlBtnSend.setLayout(new BoxLayout(pnlBtnSend, BoxLayout.X_AXIS));
            pnlBtnSend.add(pnlTxt); 
            pnlBtnSend.add(btnSend);
            
            pnlSendMsg.setLayout(new GridLayout(2,1));
            pnlSendMsg.add(pnlMsg);
            pnlSendMsg.add(pnlBtnSend);
    //        pnlSendMsg.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            
            setLayout(new BorderLayout(0,10));
            add(pnlSave,BorderLayout.NORTH);
            add(pnlChat,BorderLayout.CENTER);
            add(pnlSendMsg,BorderLayout.SOUTH);
            
            setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    //        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //        setTitle("Chat");
            setPreferredSize(new Dimension(350,250));
    //        setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createFrame="+e);
        }
    }
    
    private void createChatCardDeck()
    {
        for(int i=0;i<cboUser.getItemCount();i++)
        {
            String unm=(String)cboUser.getItemAt(i);
            JPanel pnl=new JPanel(new GridLayout());
    //        pnl.setBackground(Color.GRAY);
            pnl.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),unm,TitledBorder.CENTER,TitledBorder.ABOVE_TOP));
            JTextArea jta=new JTextArea();
            jta.setEditable(false);
            JScrollPane jsp=new JScrollPane(jta);
            pnl.add(jsp);
            pnlChat.add(pnl,unm);            
        }
    }
    
    public static void getmessage(String msg)
    {
        try
        {
            jtaChat.append(msg);
        }
        catch(Exception e)
        {
            System.out.println("Excep in getMsg="+e);e.printStackTrace();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            String actCmd=ae.getActionCommand();
            switch(actCmd)
            {
                case "Save":
                {
                    String chatNm=JOptionPane.showInputDialog(this,"Enter Chat name:","Save Chat",JOptionPane.PLAIN_MESSAGE);
                    if(chatNm==null)
                    {
                        break;
                    }
                    byte bytChat[]=jtaChat.getText().getBytes();
                    ArrayList al=new ArrayList();
                    al.add(chatNm);
                    al.add(bytChat);
                    al.add(LectGenClass.coursenm);
                    al.add(LectGenClass.lectnm);
                    int rows=db.insert("Chat", al);
                    if(rows>0)
                    {
                        JOptionPane.showMessageDialog(this,"Chat '"+chatNm+"' saved successfully.","Success",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, "Some error occured.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
                case "Send":
                {
                    String message=txtMsg.getText().trim();
                    if(!message.equals(""))
                    {
                        String usernm=GenClass.username;
                        String dest=cboUser.getSelectedItem().toString();
                        
                        if(dest.equals("Everyone"))
                        {
                            jtaChat.append("\n\nto "+dest+":\n"+message);
                        }
                        else
                        {
                            jtaChat.append("\n\nto "+dest+"(Privately):\n"+message);
                        }
                        Socket sok=LectGenClass.getClientSocket();
                        ObjectOutputStream oos=new ObjectOutputStream(sok.getOutputStream());
                        ChatClass cc=new ChatClass(usernm, dest, message);
                        oos.writeObject(cc);
                        oos.flush();
                        
                        txtMsg.setText("");
                        txtMsg.requestFocusInWindow();  
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, "Please enter some text to send.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerfm="+e);
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent ie)
    {
        try
        {
            if(ie.getStateChange()==ItemEvent.SELECTED)
            {
                System.out.println("asds");
             //   cd.show(pnlChat,(String)cboUser.getSelectedItem());
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in itmChg="+e);
        }
    }
    
    public static void main(String arg[])
    {
        ChatFrame cf=new ChatFrame();
    }
}
