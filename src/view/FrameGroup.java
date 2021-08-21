/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;

/**
 *
 * @author DARSHAN
 */
public class FrameGroup extends JPanel implements ActionListener
{
    
    JPanel pnlAtndeMain,pnlChatMain,pnlVideoMain,pnlPollMain;
    JPanel pnlAtndeTop,pnlAtndeCenter,pnlChatTop,pnlChatCenter,pnlVideoTop,pnlVideoCenter,pnlPollTop,pnlPollCenter,pnlFrameMain;
    JPanel pnlCenter[];
    JPanel pnlMain[];
    JPanel pnlCenterMain[];
    JPanel pnlToggle[];
    
 //   JLabel lblPpt,lblChat,lblVideo;
//    JButton btnAtnde,btnChat,btnVideo;
 //   JToggleButton tbtnAtnde,tbtnChat,tbtnVideo;
    JToggleButton btnToggle[];
    String strBtnlbl[];
 
    VideoFrame vf;
    UsersFrame uf;
    ChatFrame cf;
    PollFrame pf;
    
    boolean presenter=true;

    public FrameGroup()
    {
        createFrame();
    }
    
    private void createFrame()
    {
        try
        {
            vf=new VideoFrame();
            uf=new UsersFrame();
            cf=new ChatFrame();
            
            JPanel obj[];
            if(presenter)
            {
                pf=new PollFrame();
                JPanel ob[]={vf,uf,cf,pf};
                obj=ob;
                
                pnlCenter=new JPanel[4];
                pnlMain=new JPanel[4];
                pnlCenterMain=new JPanel[4];
                pnlToggle=new JPanel[4];
                btnToggle=new JToggleButton[4];
                String btnlbl[]={"Video","Attendees","Chat","Poll"};
                strBtnlbl=btnlbl;
            }
            else
            {
                JPanel ob[]={vf,uf,cf};
                obj=ob;
                
                pnlCenter=new JPanel[3];
                pnlMain=new JPanel[3];
                pnlCenterMain=new JPanel[3];
                pnlToggle=new JPanel[3];
                btnToggle=new JToggleButton[3];
                String btnlbl[]={"Video","Attendees","Chat"};
                strBtnlbl=btnlbl;
            }
            
            pnlFrameMain=new JPanel(new GridBagLayout());
            GridBagConstraints gbc=new GridBagConstraints();
            gbc.fill=GridBagConstraints.HORIZONTAL;
            gbc.gridx=0;
            gbc.gridy=0;
            gbc.weightx=1.0;
            int y=0;

            for(int i=0;i<pnlMain.length;i++)
            {
                pnlCenter[i]=new JPanel(new GridLayout());
                pnlCenter[i].add(new JScrollPane(obj[i]));
                
                pnlCenterMain[i]=new JPanel(new GridLayout());
                pnlCenterMain[i].add(new JScrollPane(pnlCenter[i]));
                pnlCenterMain[i].setBorder(BorderFactory.createLineBorder(Color.RED));
                
                btnToggle[i]=new JToggleButton(strBtnlbl[i]);
//                if(btnToggle[i].getText().equals("Video"))
//                {
//                    btnToggle[i].setSelected(false);
//                    pnlCenterMain[i].setVisible(false);
//                }
//                else
//                {
                    btnToggle[i].setSelected(true);
 //               }
                btnToggle[i].addActionListener(this);
                pnlToggle[i]=new JPanel(new GridLayout());
                pnlToggle[i].add(btnToggle[i]);
                
                gbc.gridy=y++;
                pnlFrameMain.add(pnlToggle[i],gbc);
                gbc.gridy=y++;
        //        pnlCenterMain[i].setPreferredSize(new Dimension(290,250));
                pnlFrameMain.add(pnlCenterMain[i],gbc);
            }
            gbc.weighty=1.0;
            gbc.gridy=y;
            pnlFrameMain.add(new JLabel(),gbc);
    //        pnlFrameMain.setPreferredSize(new Dimension(350,700));
            JScrollPane jsp=new JScrollPane(pnlFrameMain);
            //jsp.setWheelScrollingEnabled(true);
            add(jsp);
            
  //          setPreferredSize(new Dimension(300,500));
//            getContentPane().add(jsp);
//
//            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            setSize(430,700);
//            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createFrm="+e);e.printStackTrace();
        }
    }
 
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        Object obj=ae.getSource();
        try
        {
            for(int i=0;i<btnToggle.length;i++)
            {
                if(obj.equals(btnToggle[i]))
                {
                    showPanel(i);
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerfm="+e);
        }
    }
    
    private void showPanel(int index)
    {
        try
        {
            if(btnToggle[index].isSelected())
            {
                System.out.println("btn Selected");
                pnlCenterMain[index].setVisible(true);
//                if(btnToggle[index].getText().equals("Video"))
//                {
//                    vf.emp.getMediaPlayer().playMedia("dshow://", ":sout=#transcode{vcodec=mpgv,vb=4094,scale=1,acodec=mpg,ab=160,channels=2,samplerate=44100}:duplicate{dst=file{dst=F:\\abc.mp4},dst=display}");
//                }
            }
            else
            {
                System.out.println("btn deSelected");
                pnlCenterMain[index].setVisible(false);
//                if(btnToggle[index].getText().equals("Video"))
//                {
//                    vf.emp.getMediaPlayer().stop();
//                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in showPanel="+e);
        }
    }
    
    public static void main(String arg[])
    {
        FrameGroup fg=new FrameGroup();
    }
}
