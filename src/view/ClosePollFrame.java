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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;

/**
 *
 * @author DARSHAN
 */
public class ClosePollFrame extends JFrame implements ActionListener {
    
    JButton btnClosePoll,btnEditpoll;
    //static JButton btnEditpoll;
    JCheckBox chkBdcastResult;
    
    JLabel lblQues;
    JRadioButton rdbOption[];
    JCheckBox chkOption[];
    JProgressBar jpb[];
    JLabel lblPercentage[],lblVotes[];
    
    PollQues pq;
    
    JPanel pnlOptions,pnlResult,pnCloseFrmlMain,pnlOptionsMain,pnlBdcastResult,pnlBtn;
    
    ClosePollFrame()
    {
        createFrame();
    }
    
    private void createFrame()
    {
        try
        {
            pq=PollMain.pollQue;
            lblQues=new JLabel(pq.getQues());

            pnlOptions=new JPanel();
            pnlOptions.setLayout(new BoxLayout(pnlOptions, BoxLayout.Y_AXIS));
            
            System.out.println("1");
            
            
            
            int ans[]=pq.answer;
            if(pq.type==0)
            {
                rdbOption=new JRadioButton[pq.option.length];
                int j=0;
                for(int i=0;i<pq.option.length;i++)
                {

                    rdbOption[i]=new JRadioButton(pq.option[i]);
                    if(j<ans.length && i==ans[j])
                    {
                        rdbOption[i].setSelected(true);
                        j++;
                    }
                    rdbOption[i].setEnabled(false);
                    
           //         JPanel pnl=new JPanel();
           //         pnl.setLayout(new BoxLayout(pnl,BoxLayout.X_AXIS));
                    
                    pnlOptions.add(rdbOption[i]);
                }
            }
            else if(pq.type==1)
            {
                chkOption=new JCheckBox[pq.option.length];
                int j=0;
                for(int i=0;i<pq.option.length;i++)
                {
                    chkOption[i]=new JCheckBox(pq.option[i]);
                    if(j<ans.length && i==ans[j])
                    {
                        chkOption[i].setSelected(true);
                        j++;
                    }
                    chkOption[i].setEnabled(false);
                    pnlOptions.add(chkOption[i]);
                }
            }

            System.out.println("2");
            jpb=new JProgressBar[pq.option.length];
            lblPercentage=new JLabel[pq.option.length];
            lblVotes=new JLabel[pq.option.length];

            pnlResult=new JPanel();
            pnlResult.setLayout(new BoxLayout(pnlResult, BoxLayout.Y_AXIS));

            for(int i=0;i<pq.option.length;i++)
            {
                JPanel pnl=new JPanel();
                pnl.setLayout(new BoxLayout(pnl, BoxLayout.X_AXIS));
                jpb[i]=new JProgressBar(0, 100);
                pnl.add(jpb[i]);

                pnl.add(Box.createRigidArea(new Dimension(15,0)));

                lblPercentage[i]=new JLabel();
                pnl.add(lblPercentage[i]);

                pnl.add(Box.createRigidArea(new Dimension(15,0)));

                lblVotes[i]=new JLabel();
                pnl.add(lblVotes[i]);

                pnlResult.add(pnl);
                pnlResult.add(Box.createRigidArea(new Dimension(0,15)));
            }
            System.out.println("3");
            pnlBdcastResult=new JPanel(new FlowLayout(FlowLayout.RIGHT));
            chkBdcastResult=new JCheckBox("Broadcast Results");
            pnlBdcastResult.add(chkBdcastResult);
            System.out.println("zxc");
            pnlOptionsMain=new JPanel(new BorderLayout());
            pnlOptionsMain.add(lblQues,BorderLayout.NORTH);
            pnlOptionsMain.add(pnlOptions,BorderLayout.WEST);
            pnlOptionsMain.add(pnlResult,BorderLayout.EAST);
            pnlOptionsMain.add(pnlBdcastResult,BorderLayout.SOUTH);

            System.out.println("4");
            btnEditpoll=new JButton("Edit");
            btnEditpoll.addActionListener(this);
            btnClosePoll=new JButton("Close Poll");
            btnClosePoll.addActionListener(this);
            pnlBtn=new JPanel(new FlowLayout(FlowLayout.RIGHT));
            pnlBtn.add(btnEditpoll);
            pnlBtn.add(btnClosePoll);

            System.out.println("5");
            pnCloseFrmlMain=new JPanel(new BorderLayout());
            pnCloseFrmlMain.add(pnlBtn,BorderLayout.NORTH);
            pnCloseFrmlMain.add(pnlOptionsMain,BorderLayout.CENTER);

            setLayout(new BorderLayout());
            add(pnCloseFrmlMain,BorderLayout.NORTH);
            //add(pnlOptionsMain,BorderLayout.CENTER);

            setTitle("Poll");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(410,560);
            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createFrm="+e);
        }
    }
    
    void getOptionPnl(Object obj)
    {
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        
    }
    
}
