/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;

/**
 *
 * @author DARSHAN
 */
public class PollMain extends JFrame implements ActionListener {
    
    CardLayout cd=new CardLayout();
    Poll_Frame pf=new Poll_Frame();
    ClosePollFrame cpf;
    static PollQues pollQue;
    
    PollMain()
    {
        createFrame();
    }
    
    private void createFrame()
    {
    //    pf.btnOpenPoll.addActionListener(this);
        getContentPane().setLayout(cd);
        getContentPane().add(pf,"OpenPoll");
 //       getContentPane().add(cpf,"ClosePoll");
        
        setTitle("Poll");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(410,560);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae)
    {
        Object obj=ae.getSource();
        try
        {
//            if(obj.equals(pf.btnOpenPoll))
//            {
//                System.out.println("PollMain");
//                if(obj instanceof JComboBox)
//                {
//                    int type=pf.cboQuesType.getSelectedIndex();
//
//                    if(type==0 || type==1)
//                    {
//                        if(pf.cboPollType.getSelectedIndex()==1)
//                        {
//                            System.out.println("typ="+type);
//                            pf.setAnswerPanel(type);
//                            pf.btnOpenPoll.setEnabled(!pf.jtaQues.getText().trim().equals("") && !pf.jtaOptions.getText().trim().equals("") && pf.lstAns.getSelectedIndices().length!=0);
//                        }
//                        else
//                        {
//                            System.out.println("typ=0 or 1");
//                            if(pf.pnlAnswer.isAncestorOf(pf.jspAns)) pf.pnlAnswer.removeAll();
//                            pf.btnOpenPoll.setEnabled(!pf.jtaQues.getText().trim().equals("") && !pf.jtaOptions.getText().trim().equals(""));
//                        }
//                        pf.pnlAnswerPane.add(pf.pnlSub);
//                    }
//                    else
//                    {
//                        System.out.println("else");
//                        if(pf.pnlAnswerPane.isAncestorOf(pf.pnlSub)) pf.pnlAnswerPane.removeAll();
//                        pf.btnOpenPoll.setEnabled(!pf.jtaQues.getText().trim().equals(""));
//                    }
//                }
//                else if(obj.equals(pf.btnOpenPoll))
//                {
//                    System.out.println("PollFrm");
//                    PollMain.pollQue=new PollQues(pf.jtaQues.getText(),pf.alOption,pf.lstAns.getSelectedIndices(),pf.cboQuesType.getSelectedIndex());
//                    cpf=new ClosePollFrame();
//                    System.out.println("PollMain");
//                }
//                else if(ae.getSource().equals(cpf.btnEditpoll))
//                {
//                    cd.show(getContentPane(), "OpenPoll");
//                }
//                pf.pnlAnswerPane.revalidate();
//                pf.pnlAnswerPane.repaint();
//                
//                cd.show(getContentPane(),"ClosePoll");
//            }
            
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerform="+e);e.printStackTrace();
        }
        finally
        {
            
        }
    }
    
    public static void main(String arg[])
    {
        PollMain pm=new PollMain();
    }
}
