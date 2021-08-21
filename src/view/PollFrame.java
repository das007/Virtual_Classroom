/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.LectureThreadClass;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author DARSHAN
 */
public class PollFrame extends JPanel implements DocumentListener,ActionListener,FocusListener,ListSelectionListener
{
    
    JLabel lblQue=new JLabel("Question");
    JLabel lblQuesType=new JLabel("Type:");
    JLabel lblOption=new JLabel("Options (one per line)");
    JLabel lblPollType=new JLabel("Poll Type:");
    JLabel lblAns=new JLabel("Select Answer");
    
    ArrayList alOption;
    
    String typ[]={"Multiple Choice (Single Answer)","Multiple Choice (Multiple Answers)","Short Answer"};
    JComboBox<String> cboQuesType=new JComboBox<>(typ);
    
    String polltyp[]={"Poll","Question/Answer"};
    JComboBox<String> cboPollType=new JComboBox<>(polltyp);
    
    JList<String> lstAns;
    
    JButton btnOpenPoll=new JButton("Open Poll");
    
    JTextArea jtaQues=new JTextArea(2,25);
    JTextArea jtaOptions=new JTextArea(10,25);
    
    JScrollPane jspQues,jspOptions,jspAns;
    
    JPanel pnlQuesTxtArea,pnlQuesType,pnlOptionPane,pnlAnswer,pnlPollType,pnlAnswerPane,pnlSub,pnlMain;
    
    ClosePollFrame cpf;
    
    
    
    JButton btnClosePoll,btnEditpoll;
    //static JButton btnEditpoll;
    JCheckBox chkBdcastResult;
    
    JLabel lblQues;
    JRadioButton rdbOption[];
    JCheckBox chkOption[];
    JProgressBar jpb[];
    JLabel lblPercentage[],lblVotes[];
    
    PollQues pollQ;
    
    JPanel pnlOptions,pnlResult,pnlOptionsMain,pnlBdcastResult,pnlBtn,pnlOpenPollMain,pnlCloseFrmMain,pnlCard;
    
    CardLayout pollCard;
    
    PollFrame()
    {
        pnlOpenPollMain=new JPanel();
        createPollPanel();
        //createClosePollFrame();
        
        pollCard=new CardLayout();
//        pnlCard=new JPanel(pollCard);
//        pnlCard.add(pnlOpenPollMain,"Open_Poll");
//        pnlCard.add(pnlCloseFrmMain,"Close_Poll");
        
        pnlCloseFrmMain=new JPanel();
        setLayout(pollCard);
        add(pnlOpenPollMain,"Open_Poll");
        add(pnlCloseFrmMain,"Close_Poll");
        pollCard.show(this,"Open_Poll");
        
        //pollCard.show(getContentPane(),"Open_Poll");
        
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setSize(400,700);
//        setVisible(true);
    }
    
    public static void main(String arg[])
    {
        PollFrame pf=new PollFrame();
    }
    
    private void createPollPanel()
    {
        try
        {
            jtaQues.getDocument().addDocumentListener(this);
            jspQues=new JScrollPane(jtaQues);
            pnlQuesTxtArea=new JPanel(new BorderLayout());
            JPanel pnlLbl=new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlLbl.add(lblQue);
            pnlQuesTxtArea.add(pnlLbl,BorderLayout.NORTH);
            pnlQuesTxtArea.add(jspQues,BorderLayout.CENTER);
            
            pnlQuesType=new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlQuesType.add(lblQuesType);
            cboQuesType.addActionListener(this);
            pnlQuesType.add(cboQuesType);
            btnOpenPoll.addActionListener(this);
            btnOpenPoll.setEnabled(false);
            pnlQuesType.add(btnOpenPoll);
            
            pnlOptionPane=new JPanel(new BorderLayout());
            JPanel pnlOptLbl=new JPanel(new FlowLayout(FlowLayout.LEADING));
            pnlOptLbl.add(lblOption);
            JPanel pnlOpttxt=new JPanel(new GridLayout());
            jtaOptions.addFocusListener(this);
            jtaOptions.getDocument().addDocumentListener(this);
            jspOptions=new JScrollPane(jtaOptions);
            pnlOpttxt.add(jspOptions);
            
            pnlOptionPane.add(pnlOptLbl,BorderLayout.NORTH);
            pnlOptionPane.add(pnlOpttxt,BorderLayout.CENTER);
            
            pnlPollType=new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlPollType.add(lblPollType);
            cboPollType.addActionListener(this);
            pnlPollType.add(cboPollType);
            
            lstAns=new JList<>();
            lstAns.addListSelectionListener(this);
            lstAns.setLayoutOrientation(JList.VERTICAL);
            jspAns=new JScrollPane(lstAns);
            pnlAnswer=new JPanel(new BorderLayout());

            pnlSub=new JPanel();
            pnlSub.setLayout(new BoxLayout(pnlSub, BoxLayout.PAGE_AXIS));
            pnlSub.add(pnlOptionPane);
            pnlSub.add(Box.createRigidArea(new Dimension(0,5)));
            pnlSub.add(pnlPollType);
            pnlSub.add(Box.createRigidArea(new Dimension(0,5)));
            pnlSub.add(pnlAnswer);
            
            pnlAnswerPane=new JPanel(new GridLayout());
            pnlAnswerPane.add(pnlSub);
            
            pnlMain=new JPanel();
            pnlMain.setLayout(new BoxLayout(pnlMain,BoxLayout.PAGE_AXIS));
            pnlMain.add(pnlQuesType);
            pnlMain.add(Box.createRigidArea(new Dimension(0,5)));
            pnlMain.add(pnlQuesTxtArea);
            pnlMain.add(Box.createRigidArea(new Dimension(0,5)));
            pnlMain.add(pnlAnswerPane);
            
            JPanel pnl=new JPanel(new FlowLayout());
            pnl.add(pnlMain);
   //         JScrollPane jspMain=new JScrollPane(pnl);
            
            pnlOpenPollMain.setLayout(new GridLayout());
            //setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
            pnlOpenPollMain.add(pnl);
            
//            setTitle("Poll");
//            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            setSize(410,560);
//            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createPollFrame="+e);e.printStackTrace();
        }
    }
    
    private void createClosePollFrame()
    {
        try
        {
            pnlCloseFrmMain.removeAll();
            //pollQ=new PollQues(null, alOption, ans, WIDTH);
            lblQues=new JLabel(pollQ.getQues());

            pnlOptions=new JPanel();
            pnlOptions.setLayout(new BoxLayout(pnlOptions, BoxLayout.Y_AXIS));
            
            System.out.println("1");
            
            
            
            int ans[]=pollQ.answer;
            if(pollQ.type==0)
            {
                rdbOption=new JRadioButton[pollQ.option.length];
                int j=0;
                for(int i=0;i<pollQ.option.length;i++)
                {

                    rdbOption[i]=new JRadioButton(pollQ.option[i]);
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
            else if(pollQ.type==1)
            {
                chkOption=new JCheckBox[pollQ.option.length];
                int j=0;
                for(int i=0;i<pollQ.option.length;i++)
                {
                    chkOption[i]=new JCheckBox(pollQ.option[i]);
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
            jpb=new JProgressBar[pollQ.option.length];
            lblPercentage=new JLabel[pollQ.option.length];
            lblVotes=new JLabel[pollQ.option.length];

            pnlResult=new JPanel();
            pnlResult.setLayout(new BoxLayout(pnlResult, BoxLayout.Y_AXIS));

            for(int i=0;i<pollQ.option.length;i++)
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
            pnlCloseFrmMain.setLayout(new BorderLayout());
            pnlCloseFrmMain.add(pnlBtn,BorderLayout.NORTH);
            pnlCloseFrmMain.add(pnlOptionsMain,BorderLayout.CENTER);

//            setLayout(new BorderLayout());
//            add(pnCloseFrmlMain,BorderLayout.NORTH);
//            //add(pnlOptionsMain,BorderLayout.CENTER);
//
//            setTitle("Poll");
//            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            setSize(410,560);
//            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createCloseFrm="+e);e.printStackTrace();
        }
    }
    
    String[] getOptions()
    {
        String strOpt[]=null;
        try
        {
            
            strOpt=jtaOptions.getText().split(Character.toString((char)10));
            for (String str : strOpt) {
                System.out.print(str);
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in createOpt="+e);
        }
        return strOpt;
    }
    
    void setAnswerPanel(int type)
    {
        try
        {
            DefaultListModel<String> dlm=new DefaultListModel<>();
            String opt[]=getOptions();
            alOption=new ArrayList();
            System.out.println("no of opt="+opt.length);
            for(int i=0;i<opt.length;i++)
            {
                if(!(opt[i].trim().equals("")))
                {
                    dlm.addElement(opt[i]);
                    alOption.add(opt[i]);
                    System.out.println("opt="+opt[i]);
                }
            }
            lstAns.setModel(dlm);
            lstAns.setSelectionMode((type==0)?ListSelectionModel.SINGLE_SELECTION:ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            if(type==0)
            {
                lstAns.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                lblAns.setText("Select Answer");
            }
            else
            {
                lstAns.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                lblAns.setText("Select Answer (Press Ctrl key for selecting multiple answers)");
            }
            pnlAnswer.add(lblAns,BorderLayout.NORTH);
            pnlAnswer.add(jspAns,BorderLayout.CENTER);
        }
        catch(Exception e)
        {
            System.out.println("Excep in setAnsPnl="+e);
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        Object obj=ae.getSource();
        try
        {
            if(obj instanceof JComboBox)
            {
                int type=cboQuesType.getSelectedIndex();

                if(type==0 || type==1)
                {
                    if(cboPollType.getSelectedIndex()==1)
                    {
                        System.out.println("typ="+type);
                        setAnswerPanel(type);
                        btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals("") && !jtaOptions.getText().trim().equals("") && lstAns.getSelectedIndices().length!=0);
                    }
                    else
                    {
                        System.out.println("typ=0 or 1");
                        if(pnlAnswer.isAncestorOf(jspAns)) pnlAnswer.removeAll();
                        btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals("") && !jtaOptions.getText().trim().equals(""));
                    }
                    pnlAnswerPane.add(pnlSub);
                }
                else
                {
                    System.out.println("else");
                    if(pnlAnswerPane.isAncestorOf(pnlSub)) pnlAnswerPane.removeAll();
                    btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals(""));
                }
            }
            else if(obj.equals(btnOpenPoll))
            {
                System.out.println("PollFrm");
                pollQ=new PollQues(jtaQues.getText(),alOption,lstAns.getSelectedIndices(),cboQuesType.getSelectedIndex());
                LectureThreadClass.sendPoll(pollQ);
                createClosePollFrame();
                pollCard.show(this,"Close_Poll");
            }
            else if(obj.equals(btnEditpoll))
            {
                pollCard.show(this,"Open_Poll");
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actionPerformed="+e);e.printStackTrace();
        }
        finally
        {
            pnlAnswerPane.revalidate();
            pnlAnswerPane.repaint();
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent ls)
    {
        try
        {
            if(cboPollType.getSelectedIndex()==1)
            {
                btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals("") && !jtaOptions.getText().trim().equals("") && lstAns.getSelectedIndices().length!=0);
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in valueChg="+e);
        }
    }
    
    @Override
    public void insertUpdate(DocumentEvent de)
    {
        try
        {
            int type=cboQuesType.getSelectedIndex();
            if(type==0 || type==1)
            {
                if(cboPollType.getSelectedIndex()==1)
                {
                    btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals("") && !jtaOptions.getText().trim().equals("") && lstAns.getSelectedIndices().length!=0);
                }
                else
                {
                    btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals("") && !jtaOptions.getText().trim().equals(""));
                }
            }
            else
            {
                btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals(""));
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in insertUpdate="+e);
        }
    }
    
    @Override
    public void removeUpdate(DocumentEvent de)
    {
        try
        {
            int type=cboQuesType.getSelectedIndex();
            if(type==0 || type==1)
            {
                if(cboPollType.getSelectedIndex()==1)
                {
                    btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals("") && !jtaOptions.getText().trim().equals("") && lstAns.getSelectedIndices().length!=0);
                }
                else
                {
                    btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals("") && !jtaOptions.getText().trim().equals(""));
                }
            }
            else
            {
                btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals(""));
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in insertUpdate="+e);
        }
    }
    
    @Override
    public void changedUpdate(DocumentEvent de)
    {
        try
        {
            int type=cboQuesType.getSelectedIndex();
            if(type==0 || type==1)
            {
                if(cboPollType.getSelectedIndex()==1)
                {
                    btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals("") && !jtaOptions.getText().trim().equals("") && lstAns.getSelectedIndices().length!=0);
                }
                else
                {
                    btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals("") && !jtaOptions.getText().trim().equals(""));
                }
            }
            else
            {
                btnOpenPoll.setEnabled(!jtaQues.getText().trim().equals(""));
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in insertUpdate="+e);
        }
    }
    
    @Override
    public void focusLost(FocusEvent fe)
    {
        try
        {
            if(cboPollType.getSelectedIndex()==1) setAnswerPanel(cboQuesType.getSelectedIndex());
        }
        catch(Exception e)
        {
            System.out.println("Excep in focusLost="+e);
        }
    }
    
    @Override
    public void focusGained(FocusEvent fe){}
    
}
