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
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author DARSHAN
 */
public class InviteParticipant extends JDialog implements ActionListener
{
    JButton btnInvite,btnCancel;
    JLabel lblEmail;
    JTextArea jtaEmail;
    
    JPanel pnlEmail,pnlBtn,pnlMain;
    LectStartPage lsp;

    public InviteParticipant(LectStartPage obj)
    {
        lsp=obj;
        createForm();
    }
    
    private void createForm()
    {
        lblEmail=new JLabel("Enter Participant's Email ID (One per line)",JLabel.LEFT);
        jtaEmail=new JTextArea();
        pnlEmail=new JPanel(new BorderLayout());
        pnlEmail.add(lblEmail,BorderLayout.NORTH);
        pnlEmail.add(new JScrollPane(jtaEmail),BorderLayout.CENTER);
        
        btnInvite=new JButton("Invite");
        btnInvite.addActionListener(this);
        btnCancel=new JButton("Cancel");
        btnCancel.addActionListener(this);
        pnlBtn=new JPanel();
        pnlBtn.add(btnInvite);
        pnlBtn.add(btnCancel);
        
        pnlMain=new JPanel(new BorderLayout());
        pnlMain.add(pnlEmail,BorderLayout.CENTER);
        pnlMain.add(pnlBtn,BorderLayout.SOUTH);
        pnlMain.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        setLayout(new GridLayout());
        add(pnlMain);
        
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setTitle("Invite Participants");
        setSize(300, 300);
        setLocationRelativeTo(lsp);
        setVisible(true);

    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            String actCmd=ae.getActionCommand();
            switch(actCmd)
            {
                case "Invite":
                {
                    String toEmail[]=getEmail();
                    EmailClass ec=new EmailClass();
                    ec.sendEmail(toEmail,"Virtual Classroom - Presenter's IP address",GenClass.username+"'s IP address is "+GenClass.getPublicIP());
                    JOptionPane.showMessageDialog(this,"Invitation Mail has been sent to the participants successfully.","Successful Invitation",JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                    break;
                }
                case "Cancel":
                {
                    dispose();
                    break;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPErfm="+e);
        }
    }
    
    String[] getEmail()
    {
        String strEmail[]=null;
        try
        {
            strEmail=jtaEmail.getText().split(Character.toString((char)10));
            for (String str : strEmail) {
                System.out.print(str);
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in getEmail="+e);
        }
        return strEmail;
    }
    
    public static void main(String arg[])
    {
        //InviteParticipant ip=new InviteParticipant();
    }
}
