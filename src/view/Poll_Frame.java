package view;

//import java.awt.*;
//import java.awt.event.*;
//import javax.swing.*;
//import javax.swing.event.*;

import java.awt.BorderLayout;
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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
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
public class Poll_Frame extends JPanel implements DocumentListener,ActionListener,FocusListener,ListSelectionListener {
    
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
    
    Poll_Frame()
    {
        createFrame();
    }
    
    private void createFrame()
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
            JScrollPane jspMain=new JScrollPane(pnl);
            
            setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
            add(jspMain);
            
//            setTitle("Poll");
//            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            setSize(410,560);
//            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createPollFrame="+e);
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
                PollMain.pollQue=new PollQues(jtaQues.getText(),alOption,lstAns.getSelectedIndices(),cboQuesType.getSelectedIndex());
                cpf=new ClosePollFrame();
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
    
//    public static void main(String arg[])
//    {
//        Poll_Frame pf=new Poll_Frame();
//    }
}