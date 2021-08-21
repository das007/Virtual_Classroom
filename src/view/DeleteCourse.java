/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author DARSHAN
 */
public class DeleteCourse extends JPanel implements ActionListener
{
    JLabel lblCourseNm=new JLabel("Course Name:");
    JTextField txtCourseNm=new JTextField(20);
    
    JLabel lblLect=new JLabel("Lectures:");
    JLabel lblCht=new JLabel("Chat:");
    JLabel lblWB=new JLabel("White Board:");
    JLabel lblLectVd=new JLabel("Lecture Video:");
    
    String lblFile[]={"Chat:","White Board:","Lecture Video:"};
    JButton btnFile[]=new JButton[3];
    JList lstFile[]=new JList[3];
    JScrollPane jspFile[]=new JScrollPane[3];
    JPanel pnlFile[]=new JPanel[3];
    
    JButton btnSelct=new JButton("Select All");
    JButton btnDelCrs=new JButton("Delete Whole Course");
    JButton btnDelLect=new JButton("Delete Selected Lectures");
    JButton btnDelSelFile=new JButton("Delete Selected Files");
    
    JList lstLecture;
    JScrollPane jspLect;
    
    JPanel pnlCourse,pnlLecture,pnlMain,pnlFileMain,pnlDelSelFile;
    
    public DeleteCourse()
    {
        createForm();
    }
    
    private void createForm()
    {
        Dimension dim=new Dimension(100,15);
        
        btnSelct.addActionListener(this);
        btnDelCrs.addActionListener(this);
        btnDelLect.addActionListener(this);
        btnDelSelFile.addActionListener(this);
        
        lblCourseNm.setPreferredSize(dim);
        lblCourseNm.setHorizontalAlignment(JLabel.LEFT);
        lblLect.setPreferredSize(dim);
        lblLect.setHorizontalAlignment(JLabel.LEFT);
   
        pnlCourse=new JPanel();
        pnlCourse.setLayout(new BoxLayout(pnlCourse, BoxLayout.X_AXIS));
        pnlCourse.add(lblCourseNm);
        pnlCourse.add(txtCourseNm);
        pnlCourse.add(Box.createRigidArea(new Dimension(20,0)));
        pnlCourse.add(Box.createHorizontalGlue());
        pnlCourse.add(btnDelCrs);   //*
        
        JPanel pnlBtn=new JPanel();
        pnlBtn.setLayout(new BoxLayout(pnlBtn,BoxLayout.X_AXIS));
 //       lblLect.setBorder(BorderFactory.createLineBorder(java.awt.Color.yellow));
        pnlBtn.add(lblLect);
        pnlBtn.add(Box.createHorizontalGlue());
        pnlBtn.add(btnSelct);//*
        pnlBtn.add(Box.createRigidArea(new Dimension(10,0)));
        pnlBtn.add(btnDelLect);//*
        
        lstLecture=new JList();
        jspLect=new JScrollPane(lstLecture);
        
        pnlLecture=new JPanel(new BorderLayout(10, 10));
        pnlLecture.add(pnlBtn,BorderLayout.NORTH);
        pnlLecture.add(jspLect,BorderLayout.CENTER);
        
        pnlFileMain=new JPanel(new GridLayout(1,3,10,0));   
        for(int i=0;i<lblFile.length;i++)
        {
            pnlFile[i]=new JPanel(new BorderLayout(0,5));
            pnlFile[i].add(new JLabel(lblFile[i]),BorderLayout.NORTH);
            
            lstFile[i]=new JList();
            jspFile[i]=new JScrollPane(lstFile[i]);
            pnlFile[i].add(jspFile[i],BorderLayout.CENTER);
            
            btnFile[i]=new JButton("Select All");
            btnFile[i].addActionListener(this);
            JPanel pnl=new JPanel();
            pnl.add(btnFile[i]);
            pnlFile[i].add(pnl,BorderLayout.SOUTH);
            
            pnlFileMain.add(pnlFile[i]);
        }
        
        pnlDelSelFile=new JPanel();
        pnlDelSelFile.add(btnDelSelFile);
        
        JPanel pnlSubMain1=new JPanel(new BorderLayout(0,10));
        pnlSubMain1.add(pnlCourse,BorderLayout.NORTH);
        pnlSubMain1.add(pnlLecture,BorderLayout.CENTER);
        
        JPanel pnlSubMain2=new JPanel(new BorderLayout(0,10));
        pnlSubMain2.add(pnlFileMain,BorderLayout.CENTER);
        pnlSubMain2.add(pnlDelSelFile,BorderLayout.SOUTH);
        
        Dimension dm=new Dimension(0,10);
        pnlMain=new JPanel();
        pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.Y_AXIS));
        pnlMain.add(pnlSubMain1);
        //pnlMain.add(Box.createRigidArea(dm));
        //pnlMain.add(pnlSubMain2);

        pnlMain.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        setLayout(new GridLayout());
        add(pnlMain);
        
//        getContentPane().setLayout(new GridLayout());
//        getContentPane().add(pnlMain);
//        
//        setTitle("Delete");
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setSize(500,500);
//        setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerform="+e);
        }
    }
    
    public static void main(String arg[])
    {
        DeleteCourse dc=new DeleteCourse();
    }
}
