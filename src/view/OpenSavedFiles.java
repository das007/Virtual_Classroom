/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Iterator;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.DBClass;

/**
 *
 * @author DARSHAN
 */
public class OpenSavedFiles extends JPanel implements ActionListener,ItemListener,ListSelectionListener
{
    JLabel lblFileType,lblCoursenm,lblLectnm,lblFile,lblType,lblWBoard;
    String strFileType[]={"Chat","Whiteboard","Lecture Video"};
    String strType[]={"Non-Lecture Files","Lecture Files"};
    JComboBox<String> cboFileType,cboType,cboCoursenm,cboLectnm;
    DefaultComboBoxModel<String> dcbmCrs,dcbmLect;
    
    JList lstFile;
    DefaultListModel<String> dlmFile;
    JScrollPane jspFile;
    
    JTextField txtCoursenm=new JTextField(20);
    JTextField txtLectnm=new JTextField(20);
    JTextField txtTitle=new JTextField(20);
    
    JButton btnDelSelected,btnDelAll;
    
    JPanel pnlFileMain,pnlTypeMain,pnlFormMain,pnlCrsLectSub,pnlCrsLectMain,pnlBtn;
    JPanel pnlOpenedFile,pnlOpenedChat,pnlOpenedWb,pnlOpenedLvd;
    CardLayout cdFile;
    
    JTextArea jtaChat;
    
    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
    String tablenm[]={"Chat","Whiteboard","Lecture_Video"};
    String[] colmnName={"Chat_title","WBoard_title","LVideo_title"};

    public OpenSavedFiles()
    {
        createForm();
    }
    
    private void createForm()
    {
        try
        {
            Dimension dim=new Dimension(100,15);
            
            lblType=new JLabel("Type:",JLabel.LEFT);
            lblType.setPreferredSize(dim);
            cboType=new JComboBox<>(strType);
            cboType.addItemListener(this);
            JPanel pnlType=new JPanel(new FlowLayout(FlowLayout.LEFT,0,5));
            pnlType.add(lblType);
            pnlType.add(cboType);
            
            lblFileType=new JLabel("File Type:",JLabel.LEFT);
            lblFileType.setPreferredSize(dim);
            cboFileType=new JComboBox<>(strFileType);
            cboFileType.addItemListener(this);
            JPanel pnlFileType=new JPanel(new FlowLayout(FlowLayout.LEFT,0,5));
            pnlFileType.add(lblFileType);
            pnlFileType.add(cboFileType);
            
            pnlTypeMain=new JPanel();
            pnlTypeMain.setLayout(new BoxLayout(pnlTypeMain, BoxLayout.Y_AXIS));
            pnlTypeMain.add(pnlFileType);
            pnlTypeMain.add(pnlType);
            //pnlTypeMain.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
            lblCoursenm=new JLabel("Course Name:",JLabel.LEFT);
            lblCoursenm.setPreferredSize(dim);
            
            dcbmCrs=new DefaultComboBoxModel<>();
            cboCoursenm=new JComboBox<>(dcbmCrs);
            cboCoursenm.addItemListener(this);
            JPanel pnlCourse=new JPanel(new FlowLayout(FlowLayout.LEFT,0,5));
            pnlCourse.add(lblCoursenm);
            pnlCourse.add(cboCoursenm);
            
            lblLectnm=new JLabel("Lecture Name:",JLabel.LEFT);
            lblLectnm.setPreferredSize(dim);
            dcbmLect=new DefaultComboBoxModel<>();
            cboLectnm=new JComboBox<>(dcbmLect);
            cboLectnm.addItemListener(this);
            JPanel pnlLecture=new JPanel(new FlowLayout(FlowLayout.LEFT,0,5));
            pnlLecture.add(lblLectnm);
            pnlLecture.add(cboLectnm);
            
            pnlCrsLectSub=new JPanel();
            pnlCrsLectSub.setLayout(new BoxLayout(pnlCrsLectSub, BoxLayout.Y_AXIS));
            pnlCrsLectSub.add(pnlCourse);
            pnlCrsLectSub.add(pnlLecture);
            //pnlCrsLectSub.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
            lblFile=new JLabel("Chat:");
            dlmFile=new DefaultListModel<>();
            lstFile=new JList(dlmFile);
            lstFile.addListSelectionListener(this);
            lstFile.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            jspFile=new JScrollPane(lstFile);
            pnlFileMain=new JPanel(new BorderLayout(0,5));
            pnlFileMain.add(lblFile,BorderLayout.NORTH);
            pnlFileMain.add(jspFile,BorderLayout.CENTER);
            
            btnDelSelected=new JButton("Delete Selected Files");
            btnDelSelected.addActionListener(this);
            btnDelAll=new JButton("Delete All Files");
            btnDelAll.addActionListener(this);
            pnlBtn=new JPanel(new FlowLayout());
            pnlBtn.add(btnDelSelected);
            pnlBtn.add(btnDelAll);
            
            pnlCrsLectMain=new JPanel(new GridLayout());
            //pnlCrsLectMain.setBorder(BorderFactory.createLineBorder(Color.RED));
            pnlFormMain=new JPanel();
            pnlFormMain.setLayout(new BoxLayout(pnlFormMain, BoxLayout.Y_AXIS));
            pnlFormMain.add(pnlTypeMain);
            pnlFormMain.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlFormMain.add(pnlCrsLectMain);
            pnlFormMain.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlFormMain.add(pnlFileMain);
            pnlFormMain.add(Box.createRigidArea(new Dimension(0, 5)));
            pnlFormMain.add(pnlBtn);
            pnlFormMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            
            jtaChat=new JTextArea();
            JScrollPane jspChat=new JScrollPane(jtaChat);
            pnlOpenedChat=new JPanel(new GridLayout());
            pnlOpenedChat.add(jspChat);
            
            lblWBoard=new JLabel();
            pnlOpenedWb=new JPanel(new GridLayout());
            pnlOpenedWb.add(new JScrollPane(lblWBoard));
            pnlOpenedLvd=new JPanel(new GridLayout());
            cdFile=new CardLayout();
            pnlOpenedFile=new JPanel(cdFile);
            pnlOpenedFile.add(pnlOpenedChat,"Chat");
            pnlOpenedFile.add(pnlOpenedWb,"Whiteboard");
            pnlOpenedFile.add(pnlOpenedLvd,"Lect_Video");
            
            setLayout(new BorderLayout());
            add(pnlFormMain,BorderLayout.WEST);
            add(pnlOpenedFile,BorderLayout.CENTER);
            //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            //setSize(500, 500);
            //setVisible(true);
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in createForm="+e);e.printStackTrace();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        Object obj=ae.getSource();
        try
        {
            if(obj instanceof JButton)
            {
//                if(lstFile.getSelectedIndices().length==0)
//                {
//                    JOptionPane.showMessageDialog(this, "No files selected.\nPlease select files to be deleted.","Error",JOptionPane.INFORMATION_MESSAGE);
//                }
//                else
//                {
//                    int option=JOptionPane.showConfirmDialog(this, "Are you sure to delete the files?","Confirm Deletion",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
//                    if(option==JOptionPane.YES_OPTION)
//                    {
                        if(obj.equals(btnDelAll))
                        {
                            int option=JOptionPane.showConfirmDialog(this, "Are you sure to delete the files?","Confirm Deletion",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                            if(option==JOptionPane.YES_OPTION)
                            {
                                //delete from tblnm where cnm=qwe AND lnm=wqe;
                                int index=cboFileType.getSelectedIndex();
                                String condtn[]={"Course_name","Lect_name"};
                                String opt[]={"AND"};
                                int result;
                                if(cboType.getSelectedIndex()==1)
                                {
                                    String cnm=(String)cboCoursenm.getSelectedItem();
                                    String lnm=(String)cboLectnm.getSelectedItem();
                                    String val[]={cnm,lnm};
                                    result=db.delete(tablenm[index],condtn,val,opt);
                                }
                                else
                                {
                                    String val[]={null,null};
                                    result=db.delete(tablenm[index], condtn, val, opt);
                                }
                                if(result>0)
                                {
                                    dlmFile.removeAllElements();
                                }
        //                        else
        //                        {
        //                            JOptionPane.showMessageDialog(this, "Some error occured.","Error",JOptionPane.ERROR_MESSAGE);
        //                        }
                            }
                        }
                        else
                        {
                            if(lstFile.getSelectedIndices().length==0)
                            {
                                JOptionPane.showMessageDialog(this, "No files selected.\nPlease select files to be deleted.","Error",JOptionPane.INFORMATION_MESSAGE);
                            }
                            else
                            {
                                int option=JOptionPane.showConfirmDialog(this, "Are you sure to delete the files?","Confirm Deletion",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
                                if(option==JOptionPane.YES_OPTION)
                                {
                                    //delete from tblnm where cnm=qwe AND lnm=qwe AND title=qwe
                                   //String fileTitle[]={"Chat_title","WBoard_title","LVideo_title"};
                                    int index=cboFileType.getSelectedIndex();
                                    String condtn[]={colmnName[index],"Course_name","Lect_name"};
                                    String opt[]={"AND","AND"};
                                    int result;
                                    if(cboType.getSelectedIndex()==1)
                                    {
                                        String cnm=(String)cboCoursenm.getSelectedItem();
                                        String lnm=(String)cboLectnm.getSelectedItem();
                                        ArrayList al=(ArrayList)lstFile.getSelectedValuesList();
                                        Iterator it=al.iterator();
                                        while(it.hasNext())
                                        {
                                            String title=(String)it.next();
                                            String val[]={title,cnm,lnm};
                                            result=db.delete(tablenm[index],condtn,val,opt);
                                            if(result>0) dlmFile.removeElement(title);
                                            else JOptionPane.showMessageDialog(this, "Some error occured.","Error",JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    else
                                    {
                                        ArrayList al=(ArrayList)lstFile.getSelectedValuesList();
                                        Iterator it=al.iterator();
                                        while(it.hasNext())
                                        {
                                            String title=(String)it.next();
                                            String val[]={title,null,null};
                                            result=db.delete(tablenm[index], condtn, val, opt);
                                            if(result>0) dlmFile.removeElement(title);
                                            else JOptionPane.showMessageDialog(this, "Some error occured.","Error",JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                }
                            }
                        }
  //                  }
//                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerfm="+e);e.printStackTrace();
        }
    }
    
    @Override
    public void valueChanged(ListSelectionEvent lse)
    {
        try
        {
            if(!lse.getValueIsAdjusting())
            {
                JList lst=(JList)lse.getSource();
                int index[]=lst.getSelectedIndices();
                if(index.length==1)
                {
                    int indx=cboFileType.getSelectedIndex();
                    //String condtn[]={"Course_name","Lect_name"};
                    //String opt[]={"AND"};
                    //int result;
              //      if(cboType.getSelectedIndex()==1)
            //        {
                    //    String cnm=(String)cboCoursenm.getSelectedItem();
                    //    String lnm=(String)cboLectnm.getSelectedItem();
                    //    String val[]={cnm,lnm};
                        // select Wboardfile from Whiteboard where wbtitle='dfdf';
                    
                    String colnm[]=null,condtn[]=null;
                    if(indx==0)
                    {
                        String col[]={"Chat_title","Chat_data"};
                        colnm=col;
                        String con[]={"Chat_title"};
                        condtn=con;
                    }
                    else if(indx==1)
                    {
                        String col[]={"WBoard_title","WBoard_img"};
                        colnm=col;
                        String con[]={"WBoard_title"};
                        condtn=con;
                    }
                //        String colnm[]={"WBoard_title","WBoard_img"};
                 //       String colnm[]={"WBoard_img"};
                 //       String condtn[]={"WBoard_title"};
                        String val[]={((String)lst.getSelectedValue())};
                        ArrayList result=db.select(tablenm[indx],colnm,condtn,val,null);
                        
                        byte byt[]=(byte[])((ArrayList)result.get(0)).get(1);
                        
                        if(indx==0)
                        {
                            String cht=new String(byt);
                            jtaChat.setText(cht);
                        }
                        else if(indx==1)
                        {
                            ByteArrayInputStream bin=new ByteArrayInputStream(byt);
                            BufferedImage bimg=ImageIO.read(bin);

    //                        FileOutputStream fos=new FileOutputStream("WB.jpg");
    //                        fos.write(wbByt);
    //                        fos.flush();
    //                        fos.close();

    //                        Graphics2D g2d=(Graphics2D)bimg.getGraphics();
    //                        g2d.drawImage(bimg, 0, 0,null);
                         //   JLabel lblWb=new JLabel("LABEL",new ImageIcon(bimg),JLabel.CENTER);
                         //   pnlOpenedWb.add(lblWb);
                            lblWBoard.setHorizontalAlignment(JLabel.CENTER);
                            lblWBoard.setIcon(new ImageIcon(bimg));
                            pnlOpenedWb.revalidate();
                            pnlOpenedWb.repaint();
                        }
//                    }
//                    else
//                    {
//                        
//                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in valueChg="+e);e.printStackTrace();
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent ie)
    {
        Object obj=ie.getSource();
        try
        {
            if(ie.getStateChange()==ItemEvent.SELECTED)
            {
                if(obj.equals(cboFileType))
                {
                    int typ=cboFileType.getSelectedIndex();
                    switch(typ)
                    {
                        case 0:
                        {
                            lblFile.setText("Chat:");
                            cdFile.show(pnlOpenedFile, "Chat");
                            break;
                        }
                        case 1:
                        {
                            lblFile.setText("Whiteboard:");
                            cdFile.show(pnlOpenedFile, "Whiteboard");
                            break;
                        }
                        case 2:
                        {
                            lblFile.setText("Lecture Video:");
                            cdFile.show(pnlOpenedFile, "Lect_Video");
                            break;
                        }
                    }
                    obj=cboType;
                }
                if(obj.equals(cboType))
                {
                    int typ=cboType.getSelectedIndex();
                    if(typ==1)
                    {
                        pnlCrsLectMain.add(pnlCrsLectSub);
                        setCourseCboModel();
                        pnlCrsLectMain.revalidate();
                        pnlCrsLectMain.repaint();
                    }
                    else
                    {
                        //select title from tblnm where cnm=null and lnm=null
                        dlmFile.removeAllElements();
                        int index=cboFileType.getSelectedIndex();
                        String colnm[]={colmnName[index]};
                        String condtn[]={"Course_name","Lect_name"};
                        String val[]={null,null};
                        String opt[]={"AND"};
                        ArrayList alTitle=db.select(tablenm[index], colnm, condtn, val, opt);
                        Iterator it=alTitle.iterator();
                        while(it.hasNext())
                        {
                            String title=(String)((ArrayList)it.next()).get(0);
                            dlmFile.addElement(title);
                        }
                        pnlCrsLectMain.removeAll();
                        pnlCrsLectMain.revalidate();
                        pnlCrsLectMain.repaint();
                    }
                }
                else if(obj.equals(cboCoursenm))
                {
                    String colnm[]={"Lect_name"};
                    String condtn[]={"Course_name"};
                    String val[]={(String)cboCoursenm.getSelectedItem()};
                    if(dcbmLect!=null)dcbmLect.removeAllElements();
                    dcbmLect=GenClass.setComboBoxModel(cboLectnm,"Lecture", colnm, condtn, val,null);
                    if(dcbmLect.getSize()==0) dlmFile.removeAllElements();
                }
                else if(obj.equals(cboLectnm))
                {
                    dlmFile.removeAllElements();
                    int index=cboFileType.getSelectedIndex();
                    String cnm=(String)cboCoursenm.getSelectedItem();
                    String lectnm=(String)cboLectnm.getSelectedItem();
                    String colnm[]={colmnName[index]};
                    String condtn[]={"Course_name","Lect_name"};
                    String val[]={cnm,lectnm};
                    String opt[]={"AND"};
                    ArrayList alTitle=db.select(tablenm[index], colnm, condtn, val, opt);
                    Iterator it=alTitle.iterator();
                    while(it.hasNext())
                    {
                        String title=(String)((ArrayList)it.next()).get(0);
                        dlmFile.addElement(title);
                    }
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in itmStChg="+e);
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
    
    public static void main(String arg[])
    {
        OpenSavedFiles osf=new OpenSavedFiles();
    }
}
