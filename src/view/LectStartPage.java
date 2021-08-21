/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author DARSHAN
 */
public class LectStartPage extends JPanel implements ActionListener
{
    JButton btnInvite,btnEndMeet;
    JPanel pnlInvite,pnlEnd;

    public LectStartPage()
    {
        createForm();
    }
    
    private void createForm()
    {
        btnInvite=new JButton("Invite Participants");
        btnInvite.setFont(new Font("Arial",Font.PLAIN,30));
        btnInvite.addActionListener(this);
        btnInvite.setAlignmentX(CENTER_ALIGNMENT);
        btnInvite.setPreferredSize(new Dimension(300,150));
        btnInvite.setMaximumSize(new Dimension(300,150));
        pnlInvite=new JPanel();
        pnlInvite.setLayout(new BoxLayout(pnlInvite, BoxLayout.Y_AXIS));
        pnlInvite.add(Box.createVerticalGlue());
        pnlInvite.add(btnInvite);
        pnlInvite.add(Box.createVerticalGlue());
        
        btnEndMeet=new JButton("End Meeting");
        btnEndMeet.addActionListener(this);
        pnlEnd=new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlEnd.add(btnEndMeet);
        
        setLayout(new BorderLayout());
        add(pnlEnd,BorderLayout.NORTH);
        add(pnlInvite,BorderLayout.CENTER);
        
    ///    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
   //     setSize(300,300);
   //     setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            String actCmd=ae.getActionCommand();
            switch(actCmd)
            {
                case "Invite Participants":
                {
                    InviteParticipant ippt=new InviteParticipant(this);
                    break;
                }
                case "End Meeting":
                {
                    break;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPErfm="+e);
        }
    }
    
    public static void main(String arg[])
    {
        LectStartPage ls=new LectStartPage();
    }
}
