/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.Box;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author DARSHAN
 */
public class VCMain extends JFrame implements ActionListener {
    JDesktopPane jdp=new JDesktopPane();
    JMenuBar mnubar=new JMenuBar();
    JMenu mnuMeeting,mnuSetup,mnuOpen;
    CardLayout cdMain;
    JPanel pnlMain;
    
    String strMenu[]={"Meeting","Setup","Open","Welcome"};
    String strMnuItm[][]={{"Create Course","Add Lecture","Update Lecture","Delete","Join Lecture","Start Lecture"},
                          {"Edit Presenter Profile","Edit Participant Profile","Delete Participant"},
                          {"Saved Files"},
                          {"Log out"}};
    
    HashMap<String, JMenu> hmMenu=new HashMap<>();
    HashMap<String, JMenuItem> hmMeeting=new HashMap<>();
    HashMap<String, JMenuItem> hmSetup=new HashMap<>();
    HashMap<String, JMenuItem> hmOpen=new HashMap<>();
    HashMap<String, JMenuItem> hmUsernm=new HashMap<>();
    HashMap hm[]={hmMeeting,hmSetup,hmOpen,hmUsernm};
    
    CardLayout cardMain=new CardLayout();
    String username;
    boolean update;
    
    CreateCourse cc;
    AddLecture addlect,updateLect,startLect;
    DeleteCourse_Lecture dc;
    DelParticipant dp;
    OpenSavedFiles osf;
    LectureFrame lectFrm;
    
    VCMain(String usernm)
    {
        username=usernm;
        GenClass.setUsername(username);
        createMainFrame();
    }
    
    private void createMainFrame()
    {
        try
        {
            cardMain=new CardLayout();
            pnlMain=new JPanel(cardMain);
            
            createMenu();
            setJMenuBar(mnubar);
            
            cc=new CreateCourse();
            addlect=new AddLecture(0);
            updateLect=new AddLecture(1);
            startLect=new AddLecture(2);
            dc=new DeleteCourse_Lecture();
       //     EditProfileFrm ep=new EditProfileFrm();
            dp=new DelParticipant();
            osf=new OpenSavedFiles();
            lectFrm=new LectureFrame();

            pnlMain.add(cc, "Create_Course");
            pnlMain.add(addlect,"Add_Lect");
            pnlMain.add(updateLect,"Update_Lect");
            pnlMain.add(dc,"Delete_Course_Lect");
    //        pnlMain.add(ep,"Edit_Profile");
            pnlMain.add(dp,"Delete_Partpnt");
            pnlMain.add(osf,"OpenFile");
            pnlMain.add(startLect,"StartLect");
            pnlMain.add(lectFrm,"LectFrame");
            
            getContentPane().setLayout(new GridLayout(1,1));
            getContentPane().add(pnlMain);

            setTitle("Virtual Classroom");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setState(JFrame.NORMAL);
            setSize(500,500);
            setExtendedState(JFrame.MAXIMIZED_BOTH);
            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in crtmainFrm="+e);e.printStackTrace();
        }
    }
    
    private void createMenu()
    {
        try
        {
            for(int i=0;i<strMenu.length;i++)
            {
                JMenu mnu;
                if(strMenu[i].equals("Welcome"))
                {
                    mnu=new JMenu("Welcome, "+username);
                    mnubar.add(Box.createHorizontalGlue());
                }
                else
                {
                    mnu=new JMenu(strMenu[i]);
                }
                hmMenu.put(strMenu[i], mnu);
                
                for (String item : strMnuItm[i]) 
                {
                    JMenuItem mnuItm = new JMenuItem(item);
                    mnuItm.addActionListener(this);
                    mnu.add(mnuItm);
                    hm[i].put(item, mnuItm);
                }
                mnubar.add(mnu);
            }   
        }
        catch(Exception e)
        {
            System.out.println("Excep in createMenu="+e);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        String ac=ae.getActionCommand();
        try
        {
            switch(ac)
            {
                case "Create Course":{
                                        cc.setDlmCourse();
                                        cardMain.show(pnlMain,"Create_Course");
                                        //cc.setUsernm(username);
                                  //      CreateCourse cc=new CreateCourse();
                                 //       revalidate();
                                 //       repaint();
                                        break;
                                     }
                case "Add Lecture":{
                                        addlect.setPptList();
                                        addlect.setCourseCboModel();
                                        //AddLecture al=new AddLecture(false);
                                        cardMain.show(pnlMain,"Add_Lect");
                                        //addlect.setUsername(username);
//                                        addlect.setPptList();
//                                        addlect.setCourseCboModel();
                                        break;
                                   }
                case "Update Lecture":{
                                        updateLect.setPptList();
                                        updateLect.setCourseCboModel();
                                        //AddLecture al=new AddLecture(false);
                                        cardMain.show(pnlMain,"Update_Lect");
                                        break;
                                   }
                case "Delete":{
                                    dc.setCourseCboModel();
                                    //DeleteCourse dc=new DeleteCourse();
                                    cardMain.show(pnlMain, "Delete_Course_Lect");
                                    //dc.setUsername(username);
                                    break;
                              }
                case "Join Lecture":{
                                        ConnectFrame con=new ConnectFrame(pnlMain,cardMain,lectFrm);
                                        break;
                                    }
                case "Start Lecture":{
                                        startLect.setPptList();
                                        startLect.setCourseCboModel();
                                        startLect.setCardInfo(pnlMain,cardMain,lectFrm);
                                        cardMain.show(pnlMain,"StartLect");
                                        //startLect.setCardInfo(pnlMain,cdMain,lectFrm);
                                        break;
                                     }
                case "Edit Presenter Profile":
                                            {
                                                EditProfileFrm epf=new EditProfileFrm(username,true);
                                                //cardMain.show(pnlMain,"Edit_Profile");
                                                break;
                                            }
                case "Edit Participant Profile":
                                                {
                                                    EditProfileFrm epf=new EditProfileFrm(username,false);
                                                    //cardMain.show(pnlMain,"Edit_Profile");
                                                    break;
                                                }
                case "Delete Participant":
                                            {
                                                //DelParticipant dp=new DelParticipant();
                                                cardMain.show(pnlMain, "Delete_Partpnt");
                                                dp.setPptList();
                                                break;
                                            }
                case "Saved Files":{
                                        //OpenSavedFiles os=new OpenSavedFiles();
                                        cardMain.show(pnlMain, "OpenFile");
                                        break;
                                    }
//                case "WhiteBoard":{
//                                    //OpenSavedFiles os=new OpenSavedFiles();
//                                    cardMain.show(pnlMain, "OpenFile");
//                                    break;
//                                  }
//                case "Chat":{
//                                //OpenSavedFiles os=new OpenSavedFiles();
//                                cardMain.show(pnlMain, "OpenFile");
//                                break;
//                            }
                case "Log out":
                            {
                                int option=JOptionPane.showConfirmDialog(this,"Are you sure, you want to LOG OUT?","Log out",JOptionPane.YES_NO_OPTION,JOptionPane.INFORMATION_MESSAGE);
                                if(option==JOptionPane.YES_OPTION)
                                {
                                    System.exit(0);
                                }
                                break;
                            }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actionPerformed="+e);
        }
    }
    
    public static void main(String args[])
    {
        VCMain vcm=new VCMain("Tom007");
    }
}
