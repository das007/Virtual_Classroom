/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author DARSHAN
 */
public class LectureFrame extends JPanel {
    
    JTabbedPane tabPane;
    //String tabTitle[]={"Startup Page","Whiteboard","Share Files","Open Videos","Video Wall","Desktop Viewer"};
    String tabTitle[]={"Startup Page","Whiteboard"};
    //JPanel pnl[];
    FrameGroup fg;
    
    boolean presenter=true;

    public LectureFrame()
    {
        createFrame();
    }
    
    public void setIsPresenter(boolean p)
    {
        presenter=p;
    }
    
    private void createFrame()
    {
        try
        {
            WhiteBoardPanel pnlWb=new WhiteBoardPanel(presenter);
            LectStartPage lsp=new LectStartPage();
            JPanel pnl[]={lsp,pnlWb};
            
            tabPane=new JTabbedPane();
            for(int i=0;i<tabTitle.length;i++)
            {
                if(pnl[i]==null)pnl[i]=new JPanel();
                tabPane.addTab(tabTitle[i], pnl[i]);
            }
            
            fg=new FrameGroup();
            fg.setPreferredSize(new Dimension(385,this.getHeight()));
            
            setLayout(new BorderLayout());
            add(tabPane,BorderLayout.CENTER);
            add(fg,BorderLayout.EAST);
            
//            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            setSize(500,500);
//            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createFrm="+e);
        }
    }
    
    public static void main(String arg[])
    {
        LectureFrame lf=new LectureFrame();
    }
}
