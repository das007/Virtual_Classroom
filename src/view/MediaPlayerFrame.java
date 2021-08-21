/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author DARSHAN
 */
public class MediaPlayerFrame extends JFrame implements ActionListener
{
    JButton btnOpen;
    JLabel lblFilenm;
    JFileChooser jfc;
    
    JPanel pnlOpen,pnlMediaPlyr;
    
    
    private void createForm()
    {
        try
        {
            btnOpen=new JButton("Open file");
            btnOpen.addActionListener(this);
            lblFilenm=new JLabel();

            pnlOpen=new JPanel(new FlowLayout());
            pnlOpen.add(btnOpen);

            pnlMediaPlyr=new JPanel();

            setLayout(new BorderLayout());
            add(pnlOpen,BorderLayout.NORTH);
            add(pnlMediaPlyr,BorderLayout.CENTER);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createFrm="+e);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            if(ae.getSource().equals(btnOpen))
            {
                
                jfc=new JFileChooser();
                int opt=jfc.showOpenDialog(this);
                jfc.setFileFilter(new VideoFileFilter());
                if(opt==JFileChooser.APPROVE_OPTION)
                {
                    File f=jfc.getSelectedFile();
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerfm="+e);
        }
    }
}

class VideoFileFilter extends FileFilter
{
    @Override
    public boolean accept(File f)
    {
        try
        {
            if(f.isDirectory()) return true;
            
            String fileName=f.getName();
            String extsn;
            String ext[]={"mp3","mp4","avi","mp4v","mpeg","mpeg4","mpg","mkv","flv","iso"};
            ArrayList alExt=new ArrayList();
            alExt.addAll(Arrays.asList(ext));

            int i=fileName.lastIndexOf(".");
            if(i>0 && i<fileName.length()-1)
            {
                return alExt.contains(fileName.substring(i+1).toLowerCase());
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in accept="+e);
        }
        return false;
    }
    
    @Override
    public String getDescription()
    {
        return "Audio and Video Files";
    }
}
