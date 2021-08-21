/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.NetworkingServerMainClass;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import model.DBClass;

/**
 *
 * @author DARSHAN
 */
public class AddLecture extends JPanel implements ActionListener,ItemListener//,DocumentListener,FocusListener,KeyListener,ItemListener
{
    JLabel lblCrsNm=new JLabel("Course Name:",JLabel.LEFT);
    JLabel lblLectNm=new JLabel("Lecture Name:",JLabel.LEFT);
    JLabel lblLectCnt=new JLabel("Lecture Content: (Max. 200 Characters)",JLabel.LEFT);
    
    JTextField txtCoursenm=new JTextField(20);
    JTextField txtLectnm=new JTextField(20);
    String strLectCont=null;
    
    JComboBox<String> cboCoursenm;
    JComboBox<String> cboLectnm;
    JTextField txtCrsEditor,txtLectEditor;
    ArrayList<String> alCrsnm,alLectnm;
    DefaultComboBoxModel<String> dcbmCrs=new DefaultComboBoxModel<>();
    DefaultComboBoxModel<String> dcbmLect=new DefaultComboBoxModel<>();
    
    JTextArea jtaLectContent=new JTextArea(7,20);
    
    JList lstPartcipant,lstLectPartcipant;
    JScrollPane jspPartpnt,jspLectPartpnt,jspLectCnt;
    ArrayList alParticipants,lectPptUnm;
    
    JButton btnAdd=new JButton("Add");
    JButton btnRemove=new JButton("Remove");
    JButton btnAddAll=new JButton("Add all");
    JButton btnReset=new JButton("Reset");
    JButton btnRemoveAll=new JButton("Remove All");
    JButton btnUpdate,btnAddLect,btnStartLect;
    JPanel pnlCrsNm,pnlLectnm,pnlLectCnt,pnlPartpnt,pnlBtn,pnlMain;
    
    DefaultListModel dlmParticipant=new DefaultListModel();
    DefaultListModel dlmLectPartpnt=new DefaultListModel();
    String[] strBeforeUp,strAfterUp;
    ArrayList alBeforeUp,alAfterUp,alUsernm;
    
    JPanel pnlCardMain=null;
    CardLayout card=null;
    LectureFrame lectFrm=null;
    
    Boolean hideFlag=false;
    JPopupMenu popUpMnu;
    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
    //String username=null;
    
    int form;
    
    AddLecture(int f)
    {
        form=f;
        createForm();
    }
    
    private void createForm()
    {
        try
        {
            Dimension dim=new Dimension(100,15);
            //createPopupMenu();
            
 //           txtCoursenm.getDocument().addDocumentListener(this);
 //           txtCoursenm.addFocusListener(this);
            
            lblCrsNm.setPreferredSize(dim);
            pnlCrsNm=new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
            pnlCrsNm.add(lblCrsNm);
           // pnlCrsNm.add(txtCoursenm);
            
            cboCoursenm=new JComboBox<>(dcbmCrs);
            pnlCrsNm.add(cboCoursenm);
            
            pnlLectnm=new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
            lblLectNm.setPreferredSize(dim);
            pnlLectnm.add(lblLectNm);
           // pnlLectnm.add(txtLectnm);
            
            pnlBtn=new JPanel();
            if(form==1) 
            {
                btnUpdate=new JButton("Update Lecture");
                btnUpdate.addActionListener(this);
                pnlBtn.add(btnUpdate);
                
//                cboCoursenm.addItemListener(this);
//                                
//                cboLectnm=new JComboBox<>(dcbmLect);
//                cboLectnm.addItemListener(this);
//                pnlLectnm.add(cboLectnm);
            }
            else if(form==0)
            {
                btnAddLect=new JButton("Add Lecture");
                btnAddLect.addActionListener(this);
                pnlBtn.add(btnAddLect);
                
                //pnlCrsNm.add(txtCoursenm);
                pnlLectnm.add(txtLectnm);
            }
            else if(form==2)
            {
                btnStartLect=new JButton("Start Lecture");
                btnStartLect.addActionListener(this);
                pnlBtn.add(btnStartLect);
                
                jtaLectContent.setEditable(false);   
            }
            
            if(form!=0)
            {
                cboCoursenm.addItemListener(this);
                                
                cboLectnm=new JComboBox<>(dcbmLect);
                cboLectnm.addItemListener(this);
                pnlLectnm.add(cboLectnm);
            }
            
            if(form!=2)
            {
                pnlBtn.add(btnReset);
            }
            
            jspLectCnt=new JScrollPane(jtaLectContent);
            //pnlLectCnt=new JPanel(new FlowLayout(FlowLayout.LEFT));
            pnlLectCnt=new JPanel();
            pnlLectCnt.setLayout(new BoxLayout(pnlLectCnt,BoxLayout.X_AXIS));
            lblLectCnt.setPreferredSize(dim);
            lblLectCnt.setAlignmentY(TOP_ALIGNMENT);
 //           lblLectCnt.setBorder(BorderFactory.createLineBorder(Color.RED));
            jspLectCnt.setAlignmentY(TOP_ALIGNMENT);
            pnlLectCnt.add(lblLectCnt);
            pnlLectCnt.add(jspLectCnt);
            pnlLectCnt.add(Box.createGlue());

//            lstPartcipant=new JList(dlmParticipant);
//            Dimension dJsp=new Dimension(170,125);
//            jspPartpnt=new JScrollPane(lstPartcipant);
//            jspPartpnt.setPreferredSize(dJsp);
//            JPanel pnl1=new JPanel(new GridLayout());
//            pnl1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"My Participants"));
//            pnl1.add(jspPartpnt);
            
            
            Dimension dJsp=new Dimension(170,125);
            lstLectPartcipant=new JList(dlmLectPartpnt);
            jspLectPartpnt=new JScrollPane(lstLectPartcipant);
            jspLectPartpnt.setPreferredSize(dJsp);
            JPanel pnl2=new JPanel(new GridLayout());
            pnl2.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"Lecture Participants"));
            pnl2.add(jspLectPartpnt);
            
            pnlPartpnt=new JPanel();
            pnlPartpnt.setLayout(new BoxLayout(pnlPartpnt, BoxLayout.X_AXIS));
            pnlPartpnt.add(pnl2);
            
            if(form!=2)
            {
                lstPartcipant=new JList(dlmParticipant);
                jspPartpnt=new JScrollPane(lstPartcipant);
                jspPartpnt.setPreferredSize(dJsp);
                JPanel pnl1=new JPanel(new GridLayout());
                pnl1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),"My Participants"));
                pnl1.add(jspPartpnt);


                btnAdd.addActionListener(this);
                btnRemove.addActionListener(this);
                btnRemoveAll.addActionListener(this);
                btnAddAll.addActionListener(this);
                JPanel pnlAddbtn=new JPanel(new GridLayout(4, 1,0,10));
                pnlAddbtn.add(btnAdd);
                pnlAddbtn.add(btnRemove);
                pnlAddbtn.add(btnAddAll);
                pnlAddbtn.add(btnRemoveAll);
                JPanel pnlAddBtnMain=new JPanel();
                pnlAddBtnMain.add(pnlAddbtn);
                
                pnlPartpnt.add(Box.createRigidArea(new Dimension(10, 0)));
                pnlPartpnt.add(pnlAddBtnMain);
                pnlPartpnt.add(Box.createRigidArea(new Dimension(10, 0)));
                pnlPartpnt.add(pnl1);
            }
            
//            pnlPartpnt=new JPanel();
//            pnlPartpnt.setLayout(new BoxLayout(pnlPartpnt, BoxLayout.X_AXIS));
//            pnlPartpnt.add(pnl2);
//            pnlPartpnt.add(Box.createRigidArea(new Dimension(10, 0)));
//            pnlPartpnt.add(pnlAddBtnMain);
//            pnlPartpnt.add(Box.createRigidArea(new Dimension(10, 0)));
//            pnlPartpnt.add(pnl1);
            
            pnlCrsNm.setAlignmentX(Component.LEFT_ALIGNMENT);
            pnlLectnm.setAlignmentX(Component.LEFT_ALIGNMENT);
            pnlLectCnt.setAlignmentX(Component.LEFT_ALIGNMENT);
            pnlPartpnt.setAlignmentX(Component.LEFT_ALIGNMENT);
            pnlBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
            
            pnlMain=new JPanel();
            pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
            pnlMain.add(Box.createRigidArea(new Dimension(0,10)));
            pnlMain.add(pnlCrsNm);
            pnlMain.add(Box.createRigidArea(new Dimension(0,10)));
            pnlMain.add(pnlLectnm);
            pnlMain.add(Box.createRigidArea(new Dimension(0,10)));
            pnlMain.add(pnlLectCnt);
            pnlMain.add(Box.createRigidArea(new Dimension(0,10)));
            pnlMain.add(pnlPartpnt);
            pnlMain.add(Box.createRigidArea(new Dimension(0,10)));
            pnlMain.add(pnlBtn);
            pnlMain.setAlignmentX(CENTER_ALIGNMENT);
            pnlMain.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            pnlMain.add(Box.createGlue());
            
    //        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
            setLayout(new GridLayout());
 //           add(Box.createVerticalGlue());
            add(pnlMain);
            System.out.println("panel created");
 //           add(Box.createVerticalGlue());

 //           getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
//            getContentPane().add(Box.createVerticalGlue());
 //           getContentPane().add(pnlMain);
//            getContentPane().add(Box.createVerticalGlue());
//            setTitle("Add Lecture");
            
//            getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
//            getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
//            getContentPane().add(pnlCrsNm);
//            getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
//            getContentPane().add(pnlLectnm);
//            getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
//            getContentPane().add(pnlLectCnt);
//            getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
//            getContentPane().add(pnlPartpnt);
//            getContentPane().add(Box.createRigidArea(new Dimension(0,10)));
//            getContentPane().add(pnlBtn);
            
            setSize(500,500);
   //         setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createForm="+e);e.printStackTrace();
        }
    }
    
    void setPptList()
    {
        alParticipants=GenClass.setPptList();
        if(form!=2)
        {
            dlmParticipant=(DefaultListModel)alParticipants.get(0);
            lstPartcipant.setModel(dlmParticipant);
        }
    }
    
    void setCourseCboModel()
    {
        if(dcbmCrs!=null)dcbmCrs.removeAllElements();
        String colnm[]={"Course_name"};
        String condtn[]={"Presenter_unm"};
        String val[]={GenClass.username};
        dcbmCrs=GenClass.setComboBoxModel(cboCoursenm,"Course",colnm,condtn,val,null);
    }
    
    void setCardInfo(JPanel pnl,CardLayout cd,LectureFrame lf)
    {
        pnlCardMain=pnl;
        card=cd;
        lectFrm=lf;
        System.out.println("Card="+card.toString()+" pnl="+pnlCardMain.toString()+" lectFrm="+lectFrm.toString());
    }
    
//    void setUsername(String unm)
//    {
//        username=unm;
//    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        Object obj=ae.getSource();
        //JButton btn=null;
        //JMenuItem mnuitm=null;
        try
        {
            if(obj instanceof JButton)
            {
                JButton btn=(JButton)obj;
                if(btn.equals(btnAdd))
                {
                    List lst=lstPartcipant.getSelectedValuesList();
                    Iterator it=lst.iterator();
                    while(it.hasNext())
                    {
                        String v=(String)it.next();
                        if(!dlmLectPartpnt.contains(v))
                        {
                            System.out.println(v);
                            dlmLectPartpnt.addElement(v);
                        }
                    }
                    lstLectPartcipant.setModel(dlmLectPartpnt);
                    lstPartcipant.clearSelection();
                }
                else if(btn.equals(btnRemove))
                {
                    List lst=lstLectPartcipant.getSelectedValuesList();
                    Iterator it=lst.iterator();
                    while(it.hasNext())
                    {
                        dlmLectPartpnt.removeElement(it.next());
                    }
                    lstLectPartcipant.setModel(dlmLectPartpnt);
                }
                else if(btn.equals(btnAddAll))
                {
                    lstPartcipant.addSelectionInterval(0, lstPartcipant.getModel().getSize()-1);
                    List lst=lstPartcipant.getSelectedValuesList();
                    Iterator it=lst.iterator();
                    while(it.hasNext())
                    {
                        String val=(String)it.next();
                        if(!dlmLectPartpnt.contains(val)) dlmLectPartpnt.addElement(val);
                    }
                    lstLectPartcipant.setModel(dlmLectPartpnt);
                    lstPartcipant.clearSelection();   
                }
                else if(btn.equals(btnRemoveAll))
                {
                    DefaultListModel dlm=(DefaultListModel)lstLectPartcipant.getModel();
                    dlm.removeAllElements();
                    lstLectPartcipant.setModel(dlm);
                }
                else if(btnAddLect!=null && btn.equals(btnAddLect))
                {
                    String lectnm=txtLectnm.getText().trim();
                    if(!lectnm.equals(""))
                    {
                        String crsnm=(String)cboCoursenm.getSelectedItem();
                        ArrayList alValues=new ArrayList();
                        alValues.add(lectnm);
                        alValues.add(jtaLectContent.getText());
                        alValues.add(crsnm);
                        int result=db.insert("Lecture",alValues);

                        int res1=0;
                        if(lstLectPartcipant.getModel().getSize()>0 && result>0)
                        {
                            alValues=new ArrayList();
                            alValues.add(crsnm);
                            alValues.add(lectnm);
                            for(int i=0;i<dlmLectPartpnt.getSize();i++)
                            {
                                String str=(String)dlmLectPartpnt.get(i);
                                ArrayList alst[]=(ArrayList[])alParticipants.get(1);
                                int index=((DefaultListModel)alParticipants.get(0)).indexOf(str);
                                //String unm=((String[][])alParticipants.get(1))[1][index];
                                String unm=(String)alst[1].get(index);
                                System.out.println(crsnm+","+lectnm+","+dlmLectPartpnt.get(i)+","+index+","+unm);
                                alValues.add(unm);
                                res1+=db.insert("Course_Viewer_Info", alValues);
                                alValues.remove(2);
                            }
                            if(res1==dlmLectPartpnt.getSize())
                            {
                                JOptionPane.showMessageDialog(this, "Lecture "+lectnm+" is successfully created.","Success",JOptionPane.INFORMATION_MESSAGE);
                                txtLectnm.setText("");
                                jtaLectContent.setText("");
                                dlmLectPartpnt.removeAllElements();
                            }
                            else
                            {
                                System.out.println("error");
                                JOptionPane.showMessageDialog(this, "Some error occured.","Error",JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else if(result>0)
                        {
                            JOptionPane.showMessageDialog(this, "Lecture "+lectnm+" is successfully created.","Success",JOptionPane.INFORMATION_MESSAGE);
                            txtLectnm.setText("");
                            jtaLectContent.setText("");
                        }
                        else if(result<=0)
                        {
                            JOptionPane.showMessageDialog(this, "Some error occured.","Error",JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(this, "Lecture Name field should not be blank.","Error",JOptionPane.ERROR_MESSAGE);
                    }
                }
                else if(btnUpdate!=null && btn.equals(btnUpdate))
                {
                    String lectCnt=jtaLectContent.getText();
                    String crsnm=(String)cboCoursenm.getSelectedItem();
                    String lectnm=(String)cboLectnm.getSelectedItem();
                    if(!lectCnt.equals(strLectCont))
                    {
                        String colnm[]={"Lect_content"};
                        String exp[]={lectCnt};
                        String condtn[]={"Lect_name","Course_name"};
                        String val[]={lectnm,crsnm};
                        String log[]={"AND"};
                        int result=db.update("Lecture",colnm,exp,condtn,log,val);
                    }
                    
                    alAfterUp=new ArrayList();
                    for(int i=0;i<dlmLectPartpnt.size();i++)
                    {
                        String p=(String)dlmLectPartpnt.elementAt(i);
                        System.out.println("After up contains="+p);
                        alAfterUp.add(dlmLectPartpnt.elementAt(i));
                    }
                    
                    ArrayList alppt[]=(ArrayList[])alParticipants.get(1);
                    DefaultListModel dlmPpt=(DefaultListModel)alParticipants.get(0);
                    for(int i=0;i<alBeforeUp.size();i++)
                    {
                        String p=(String)alBeforeUp.get(i);
                        System.out.println("BeforeUp size="+alBeforeUp.size()+"  Pppt="+p);
                        if(alAfterUp.contains(p))
                        {
                           alAfterUp.remove(p);
                           alBeforeUp.remove(p);
                           System.out.println("beforeUp="+p+" removed");
                        }
                        else
                        {
                            //delete from Course_Viewer_Info where Course_name=qwew AND Lect_name=ads and Viewer_unm=erwer;
                            int index=dlmPpt.indexOf(p);
                            String unm=(String)alppt[1].get(index);
                            System.out.println("unmElse="+unm);
                            String condtn[]={"Lect_name","Course_name","Viewer_unm"};
                            String val[]={lectnm,crsnm,unm};
                            String opt[]={"AND","AND"};
                            int rows=db.delete("Course_Viewer_Info",condtn,val,opt);
                            if(rows>0)System.out.println(unm+" deleted");
                            alBeforeUp.remove(p);
                        }
                        i=-1;
                    } 
                    
                    for(int i=0;i<alAfterUp.size();i++)
                    {
                        String p=(String)alAfterUp.get(i);
                        int index=dlmPpt.indexOf(p);
                        String unm=(String)alppt[1].get(index);
                        System.out.println("unm="+unm);
                        ArrayList alVal=new ArrayList();
                        alVal.add(crsnm);
                        alVal.add(lectnm);
                        alVal.add(unm);
                        int rows=db.insert("Course_Viewer_Info", alVal);
                        if(rows>0)System.out.println(unm+" inserted");
                    }
                }
                else if(btnStartLect!=null && btn.equals(btnStartLect))
                {
                    //select email_id from UserInfo where username="asd";
                    if(lectPptUnm.size()>0)
                    {
                        String pptEmail[]=new String[lectPptUnm.size()];
                        String colnm[]={"Email_id"};
                        String condtn[]={"User_name"};
                        Iterator it=lectPptUnm.iterator();
                        int indx=0;
                        while(it.hasNext())
                        {
                            String val[]={(String)it.next()};
                            ArrayList alResult=db.select("UserInfo",colnm,condtn,val,null);
                            pptEmail[indx++]=((ArrayList)alResult.get(0)).get(0).toString();
                        }
                        EmailClass ec=new EmailClass();
                        ec.sendEmail(pptEmail,"Virtual Classroom - Presenter's IP address",GenClass.username+"'s IP address is "+GenClass.getPublicIP());
                    }
                    
                    ArrayList alUser[]=new ArrayList[2];
                    alUser[0]=new ArrayList();
                    alUser[1]=new ArrayList();
                    alUser[0].add(GenClass.username);
                    alUser[1].add("Presenter");
                    //String row[]={GenClass.username,"Presenter"};
                    LectGenClass.updateUserTable(alUser);
                    
                    String crsnm=(String)cboCoursenm.getSelectedItem();
                    String lectnm=(String)cboLectnm.getSelectedItem();
                    System.out.println("crsnm="+crsnm+" lectnm="+lectnm);
                    LectGenClass.setCourse_Lect(crsnm,lectnm);
                    lectFrm.setIsPresenter(true);
                    card.show(pnlCardMain,"LectFrame");
                    
                    NetworkingServerMainClass netwk=new NetworkingServerMainClass();
                    Thread t=new Thread(netwk,"ThreadMain");
                    t.start();
                    
                //    LectureFrame lectFrm=new LectureFrame();
                }
            }
            else
            {
//                JMenuItem mnuitm=(JMenuItem)obj;
//                txtCoursenm.setText(mnuitm.getActionCommand());
//                popUpMnu.setVisible(false);
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerform="+e);e.printStackTrace();
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent ie)
    {
        try
        {
            if(ie.getStateChange()==ItemEvent.SELECTED)
            {
                Object obj=ie.getSource();
                if(obj.equals(cboCoursenm))
                {
                    System.out.println("actlstn");

                    String colnm[]={"Lect_name"};
                    String condtn[]={"Course_name"};
                    String val[]={(String)cboCoursenm.getSelectedItem()};
                    if(dcbmLect!=null)dcbmLect.removeAllElements();
                    dcbmLect=GenClass.setComboBoxModel(cboLectnm,"Lecture", colnm, condtn, val,null);
                    if(dcbmLect.getSize()==0) jtaLectContent.setText("");
                }
                else if(obj.equals(cboLectnm))
                {
                    dlmLectPartpnt.removeAllElements();
                    Object str=cboLectnm.getSelectedItem();
                    if(str!=null)
                    {
                        String cnm=(String)cboCoursenm.getSelectedItem();
                        String lectnm=(String)str;
                        String colnm[]={"Lect_content"};
                        String condtn[]={"Course_name","Lect_name"};
                        String val[]={cnm,lectnm};
                        String opt[]={"AND"};
                        ArrayList alLectCnt=db.select("Lecture", colnm, condtn, val, opt);
                        ArrayList al=(ArrayList)alLectCnt.get(0);
                        jtaLectContent.setText((String)al.get(0));
                        strLectCont=jtaLectContent.getText();
                        
                        //select UserName from CourseUserInfo where Crsnm=asd AND Lectnm=eqw
                        
                        DefaultListModel dlm=(DefaultListModel)alParticipants.get(0);
//                        String user[][]=(String[][])alParticipants.get(1);
                        
                        lectPptUnm=new ArrayList();
                        alBeforeUp=new ArrayList();
                        String colnm1[]={"Viewer_unm"};
                        ArrayList alLectPpt=db.select("Course_Viewer_Info", colnm1, condtn, val, opt);
                        Iterator it=alLectPpt.iterator();
                        while(it.hasNext())
                        {
                            String ppt=(String)((ArrayList)it.next()).get(0);
                            lectPptUnm.add(ppt);
                            int index=((ArrayList[])alParticipants.get(1))[1].indexOf(ppt);
                            System.out.println("index="+index);
                            DefaultListModel dlmLtPpt=(DefaultListModel)alParticipants.get(0);
                            String p=(String)dlmLtPpt.get(index);
                            dlmLectPartpnt.addElement(p);
                            System.out.println("Before up contains="+p);
                            alBeforeUp.add(p);
                        }
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("addLect="+e);
        }
    }
    
    public static void main(String arg[])
    {
        AddLecture alect=new AddLecture(2);
    }
}