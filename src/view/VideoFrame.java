/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import com.sun.jna.NativeLibrary;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
/**
 *
 * @author DARSHAN
 */
public class VideoFrame extends JPanel implements ActionListener {

    JButton btnStartWebCam=new JButton("Start My WebCam");
    JButton btnStopWebcam=new JButton("Stop My WebCam");
    JLabel lbl=new JLabel("Video Wall");
    
    JPanel pnlBtn,pnlVideoWall,pnlBtnStop,pnlVideoMain;
    CardLayout cd=new CardLayout();
    
    EmbeddedMediaPlayerComponent emp;
    
    public VideoFrame()
    {
    //    MediaPlayer mp=new MediaPlayer(new Media("C:\\Users\\DARSHAN\\Desktop\\abc.mp4"));
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "VLCJ");
        createFrame();
    //    mp.play();
    }
    
    private void createFrame()
    {
        try
        {
            btnStartWebCam.addActionListener(this);
            btnStopWebcam.addActionListener(this);
            
            pnlBtn=new JPanel();
            pnlBtn.setLayout(new BoxLayout(pnlBtn,BoxLayout.Y_AXIS));
            btnStartWebCam.setAlignmentX(Component.CENTER_ALIGNMENT);
            pnlBtn.add(Box.createVerticalGlue());
            pnlBtn.add(btnStartWebCam);
            pnlBtn.add(Box.createVerticalGlue());
            
            pnlBtnStop=new JPanel();
            pnlBtnStop.setLayout(new BoxLayout(pnlBtnStop, BoxLayout.X_AXIS));
            //btnStopWebcam.setAlignmentX(Component.RIGHT_ALIGNMENT);
            pnlBtnStop.add(Box.createHorizontalGlue());
            pnlBtnStop.add(btnStopWebcam);
            pnlBtnStop.add(Box.createRigidArea(new Dimension(10,0)));
            
            JPanel pnlBtnMain=new JPanel();
            pnlBtnMain.setLayout(new BoxLayout(pnlBtnMain, BoxLayout.Y_AXIS));
            pnlBtnMain.add(Box.createRigidArea(new Dimension(0,10)));
            pnlBtnMain.add(pnlBtnStop);
            pnlBtnMain.add(Box.createRigidArea(new Dimension(0,10)));
            pnlBtnMain.add(new JSeparator());
            
            emp=new EmbeddedMediaPlayerComponent();
            pnlVideoWall=new JPanel(new GridLayout());
            pnlVideoWall.add(emp);
            
            pnlVideoMain=new JPanel(new BorderLayout());
            pnlVideoMain.add(pnlBtnMain,BorderLayout.NORTH);
            pnlVideoMain.add(pnlVideoWall,BorderLayout.CENTER);
            
            setLayout(cd);
            add(pnlBtn,"Btnpnl");
 //           setLayout(new GridLayout());
            add(pnlVideoMain,"VideoWall");
            
 //           Desktop.getDesktop().open(new File("C:\\Users\\DARSHAN\\Desktop"));
            
      //      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setPreferredSize(new Dimension(290,250));
      //      setLocationRelativeTo(null);
     //       setVisible(true);
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
            if(ae.getSource().equals(btnStartWebCam))
            {
                cd.show(this,"VideoWall");
                emp.getMediaPlayer().playMedia("dshow://", ":sout=#transcode{vcodec=mpgv,vb=4094,scale=1,acodec=mpg,ab=160,channels=2,samplerate=44100}:duplicate{dst=file{dst=F:\\abc.mp4},dst=display}");
            }
            else if(ae.getSource().equals(btnStopWebcam))
            {
                cd.show(this, "Btnpnl");
                emp.getMediaPlayer().stop();
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerfm="+e);
        }
    }
    
    public static void main(String arg[])
    {
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "VLCJ");
        VideoFrame vf=new VideoFrame();
    }
}
