/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/**
 *
 * @author DARSHAN
 */
public class ClientPollFrame implements ActionListener
{
    JButton btnSubmit;
    ButtonGroup btnGrpOpt;
    JLabel lblQues;
    JCheckBox chkOpt[];
    JRadioButton rdbOpt[];
    JTextArea jtaAnswer;
    PollQues pollQue;
    
    public void createClientPollFrame(PollQues pq)
    {
        pollQue=pq;
        JDialog diagPollClient=new JDialog();
        btnSubmit=new JButton("Submit");
        btnSubmit.addActionListener(this);
        lblQues=new JLabel(pollQue.ques);
        
        JPanel pnlOption=new JPanel();
        pnlOption.setLayout(new BoxLayout(pnlOption,BoxLayout.Y_AXIS));
        
        btnGrpOpt=new ButtonGroup();
        
        if(pollQue.type==0)
        {
            rdbOpt=new JRadioButton[pollQue.option.length];
            for(int i=0;i<rdbOpt.length;i++)
            {
                rdbOpt[i]=new JRadioButton(pollQue.option[i]);
                rdbOpt[i].setActionCommand(i+"");
                rdbOpt[i].setAlignmentX(JComponent.LEFT_ALIGNMENT);
                btnGrpOpt.add(rdbOpt[i]);
                pnlOption.add(rdbOpt[i]);
            }
        }
        else if(pollQue.type==1)
        {
            chkOpt=new JCheckBox[pollQue.option.length];
            for(int i=0;i<chkOpt.length;i++)
            {
                chkOpt[i]=new JCheckBox(pollQue.option[i]);
                chkOpt[i].setActionCommand(i+"");
                chkOpt[i].setAlignmentX(JComponent.LEFT_ALIGNMENT);
                btnGrpOpt.add(chkOpt[i]);
                pnlOption.add(chkOpt[i]);
            }
        }
        else
        {
            
        }
        
        JPanel pnlSubmit=new JPanel(new FlowLayout());
        pnlSubmit.add(btnSubmit);
        
        JPanel pnlMain=new JPanel(new BorderLayout());
        pnlMain.add(lblQues,BorderLayout.NORTH);
        pnlMain.add(pnlOption,BorderLayout.CENTER);
        pnlMain.add(pnlSubmit,BorderLayout.SOUTH);
        
        diagPollClient.setLayout(new FlowLayout());
        diagPollClient.add(pnlMain);
        
        diagPollClient.pack();
        diagPollClient.setTitle("Poll");
        diagPollClient.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        diagPollClient.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            
        }
        catch(Exception e)
        {
            System.out.println("Excpe in Client Pollfrm acrPErfm="+e);e.printStackTrace();
        }
    }
    
}
