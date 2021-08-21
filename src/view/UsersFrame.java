/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DARSHAN
 */
public class UsersFrame extends JPanel
{
    static JTable jtUser;
    static DefaultTableModel dtmUser;
    static JScrollPane jspTable;
    JButton btnRaiseHand;
    
    
    
    
    UsersFrame()
    {
        createFrame();
    }
    
    static ArrayList getTableInfo()
    {
        ArrayList alTableInfo=new ArrayList();
        alTableInfo.add(jspTable);
        alTableInfo.add(jtUser);
        return alTableInfo;
    }
    
    private void createFrame()
    {
        try
        {
            BufferedImage bimg=ImageIO.read(new File("Lines\\raiseHand.png"));
            
            //Image img=ImageIO.read(new File(""));img.getScaledInstance(WIDTH, WIDTH, WIDTH);
            Image img=bimg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            ImageIcon icon=new ImageIcon(img);
            
            String cols[]={"Username","Role"};
            dtmUser=new DefaultTableModel(cols,0);
            jtUser=new JTable(dtmUser);
            
            jspTable=new JScrollPane();
            jspTable.setViewportView(jtUser);
            
            JPanel pnlBtn=new JPanel(new FlowLayout(FlowLayout.CENTER));
            btnRaiseHand=new JButton(icon);
            
     //       btnRaiseHand.setPreferredSize(new Dimension(50, 50));
            pnlBtn.add(btnRaiseHand);
            
            setLayout(new BorderLayout());
            add(jspTable,BorderLayout.CENTER);
            //add(pnlBtn,BorderLayout.SOUTH);
            
        //    setTitle("Attendees");
         //   setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       //     setLocationRelativeTo(null);
            setPreferredSize(new Dimension(290,250));
       //     setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createFrame="+e);
        }
    }
    
    public static void main(String arg[])
    {
        UsersFrame uf=new UsersFrame();
    }
}
