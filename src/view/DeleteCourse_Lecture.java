/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.DBClass;

/**
 *
 * @author DARSHAN
 */
public class DeleteCourse_Lecture extends JPanel implements ActionListener,ItemListener
{
    JLabel lblCourseNm=new JLabel("Course Name:");
    JComboBox<String> cboCoursenm;
    DefaultComboBoxModel<String> dcbmCrs;
    
    JLabel lblLect=new JLabel("Lectures:");
    JButton btnDelCrs,btnDelLect;
    
    JCheckBox chkbx[]=new JCheckBox[3];
    String strChk[]={"Delete respective Chats","Delete respective WhiteBoards"};
    String tblnm[]={"Chat","Whiteboard","Lecture_Video"};
    
    JList lstLecture;
    JScrollPane jspLect;
    DefaultListModel<String> dlmLect;
    
    JPanel pnlCourse,pnlLecture,pnlChkbx,pnlBtn;
    
    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);

    public DeleteCourse_Lecture() 
    {
        createForm();
    }
    
    private void createForm()
    {
        try
        {
            Dimension dim=new Dimension(100,15);
            
            lblCourseNm.setPreferredSize(dim);
            lblCourseNm.setHorizontalAlignment(JLabel.LEFT);
            lblLect.setPreferredSize(dim);
            lblLect.setHorizontalAlignment(JLabel.LEFT);
            
            dcbmCrs=new DefaultComboBoxModel<>();
            cboCoursenm=new JComboBox<>(dcbmCrs);
            cboCoursenm.addItemListener(this);
            pnlCourse=new JPanel(new FlowLayout(FlowLayout.LEFT,0,5));
            pnlCourse.add(lblCourseNm);
            pnlCourse.add(cboCoursenm);
            
            pnlChkbx=new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
            for(int i=0;i<strChk.length;i++)
            {
                chkbx[i]=new JCheckBox(strChk[i]);
                pnlChkbx.add(chkbx[i]);
            }
            
            dlmLect=new DefaultListModel<>();
            lstLecture=new JList(dlmLect);
            jspLect=new JScrollPane(lstLecture);
            JPanel pnlLectLst=new JPanel(new GridLayout());
            pnlLectLst.add(jspLect);
            
            pnlLecture=new JPanel(new BorderLayout(0,5));
            pnlLecture.add(lblLect,BorderLayout.NORTH);
            pnlLecture.add(pnlLectLst,BorderLayout.CENTER);
            pnlLecture.add(pnlChkbx,BorderLayout.SOUTH);
            
            btnDelCrs=new JButton("Delete Whole Course");
            btnDelCrs.addActionListener(this);
            btnDelLect=new JButton("Delete Selected Lectures");
            btnDelLect.addActionListener(this);
            pnlBtn=new JPanel(new FlowLayout());
            pnlBtn.add(btnDelCrs);
            pnlBtn.add(btnDelLect);
            
            setLayout(new BorderLayout(0,10));
            add(pnlCourse,BorderLayout.NORTH);
            add(pnlLecture,BorderLayout.CENTER);
            add(pnlBtn,BorderLayout.SOUTH);
            
            setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        }
        catch(Exception e)
        {
            System.out.println("Excep in createFrm="+e); e.printStackTrace();
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        Object obj=ae.getSource();
        try
        {
            String crsnm=(String)cboCoursenm.getSelectedItem();
            if(obj.equals(btnDelCrs))
            {
                String condtn[]={"Course_name"};
                String val[]={crsnm};
                for(int i=0;i<strChk.length;i++)
                {
                    if(chkbx[i].isSelected())
                    {
                        int rows=db.delete(tblnm[i],condtn,val,null);
                    }
                }
                int rows=db.delete("Course",condtn,val,null);
                if(rows>0)
                {
                    dcbmCrs.removeElement(crsnm);
                    JOptionPane.showMessageDialog(this, "Course '"+crsnm+"' is successfully deleted.","Course Deletion",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Some error occured.","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
            else if(obj.equals(btnDelLect))
            {
                String condtn[]={"Course_name","Lect_name"};
                String opt[]={"AND"};
                ArrayList al=(ArrayList)lstLecture.getSelectedValuesList();
                Iterator it=al.iterator();
                int rows=0,rowFile=0;
                while(it.hasNext())
                {
                    String lectnm=(String)it.next();
                    String val[]={crsnm,lectnm};
                    for(int i=0;i<strChk.length;i++)
                    {
                        if(chkbx[i].isSelected())
                        {
                            rowFile=db.delete(tblnm[i],condtn,val,opt);
                        }
                    }
                    rows=db.delete("Lecture", condtn, val, opt);
                    dlmLect.removeElement(lectnm);
                }
                if(rows>0)
                {
                    JOptionPane.showMessageDialog(this, "Selected lectures are successfully deleted.","Lecture Deletion",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(this, "Some error occured.","Error",JOptionPane.ERROR_MESSAGE);
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
                dlmLect.removeAllElements();
                String colnm[]={"Lect_name"};
                String condtn[]={"Course_name"};
                String val[]={(String)cboCoursenm.getSelectedItem()};
                ArrayList al=db.select("Lecture", colnm, condtn, val, null);
                Iterator it=al.iterator();
                while(it.hasNext())
                {
                    String lectnm=(String)((ArrayList)it.next()).get(0);
                    dlmLect.addElement(lectnm);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in itmStchg="+e);
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
        DeleteCourse_Lecture dc=new DeleteCourse_Lecture();
    }
}
