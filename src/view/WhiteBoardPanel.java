/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import controller.LectureThreadClass;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import model.DBClass;

/**
 *
 * @author DARSHAN
 */
public class WhiteBoardPanel extends JPanel implements ActionListener,ItemListener
{
    JPanel ctrlPnl,ctrlMainpnl,canvasPnl,navPnl;
    String[] strCtrlTgb={"Pencil","Rectangle","Oval","Line","Text","Eraser"};
//    String strCtrlbtn[]={"Line Width","Clear","Color"};
    JButton btnClear,btnColor;
    Integer intWidth[]={1,2,3,4,5,10,15,20,40,50,60,70,80,90,100};
    JComboBox<Integer> cboLineWidth;
    //JToggleButton btnCtrl[];
    HashMap<String,JToggleButton> hmTogglebtn;
    CardLayout canvasCard;
    
    JButton btnPrev,btnNext,btnNew,btnOpen,btnSave;
    JTextField txtCardNo=new JTextField("1",5);
    //JTextField txtLineWidth=new JTextField(5);
    String strNav[]={"<<",">>","New Whiteboard","Save"};
    JButton btnNav[];
    HashMap<String,JButton> hmNavbtn;
    String selectedBtn="";
    
    public static HashMap<String, CanvasComponent> hmCanvas;
    int totalCanvas=1;
    
    DBClass db=new DBClass(GenClass.port,GenClass.sid,GenClass.dbUser,GenClass.dbPwd);
    
    boolean presenter=true;
    
    public static void main(String arg[])
    {
        WhiteBoardPanel wb=new WhiteBoardPanel(true);
    }
    
    public WhiteBoardPanel(boolean p) 
    {
        presenter=p;
        createWBFrame();
    }
    
    private void createWBFrame()
    {
        try
        {
            //btnCtrl=new JToggleButton[strCtrl.length];
            
            ctrlPnl=new JPanel(new GridLayout(9,1,5,5));
        //    ctrlPnl.setLayout(new BoxLayout(ctrlPnl, BoxLayout.Y_AXIS));
            
            hmTogglebtn=new HashMap<>();
            JToggleButton jtb;
            for(int i=0;i<strCtrlTgb.length;i++)
            {
                jtb=new JToggleButton(strCtrlTgb[i]);
                //btnCtrl[i]=new JToggleButton(strCtrl[i]);
                jtb.addActionListener(this);
                ctrlPnl.add(jtb);
                hmTogglebtn.put(strCtrlTgb[i],jtb);
            }
            
            cboLineWidth=new JComboBox<>(intWidth);
            cboLineWidth.setEditable(true);
            cboLineWidth.addItemListener(this);
            cboLineWidth.setBorder(BorderFactory.createTitledBorder("Line Width"));
    //        btnLineWd=new JButton("Line Width");
    //        btnLineWd.addActionListener(this);
            btnClear=new JButton("Clear");
            btnClear.addActionListener(this);
            btnColor=new JButton("Color");
            btnColor.addActionListener(this);
 //           ctrlPnl.add(btnLineWd);
            ctrlPnl.add(cboLineWidth);
            ctrlPnl.add(btnColor);
            ctrlPnl.add(btnClear);
            ctrlPnl.setPreferredSize(new Dimension(100,410));
            
            ctrlMainpnl=new JPanel(new FlowLayout());
            ctrlMainpnl.add(ctrlPnl);
            
            hmCanvas=new HashMap<>();
            CanvasComponent cc=new CanvasComponent(presenter);
            cc.setBackground(Color.WHITE);
            hmCanvas.put("1",cc);
            canvasCard=new CardLayout();
            canvasPnl=new JPanel(canvasCard);
            canvasPnl.add(new JScrollPane(cc),"1");
            
            hmNavbtn=new HashMap<>();
            navPnl=new JPanel();
            navPnl.setLayout(new BoxLayout(navPnl, BoxLayout.X_AXIS));
//            navPnl.add();
//            hmNavbtn=new HashMap<>();
            
            JButton btn;
            for(int i=0;i<strNav.length;i++)
            {
                if(i==1)
                {
                    navPnl.add(Box.createRigidArea(new Dimension(5,0)));
                    txtCardNo.setEnabled(false);
                    navPnl.add(txtCardNo);
                }
                else if(i==3)
                {
                    navPnl.add(Box.createHorizontalGlue());
                }
                
                
                btn=new JButton(strNav[i]);
                btn.addActionListener(this);
                if(strNav[i].equals("<<") || strNav[i].equals(">>"))
                {
                    btn.setEnabled(false);
                }
                navPnl.add(Box.createRigidArea(new Dimension(5,0)));
                navPnl.add(btn);
                hmNavbtn.put(strNav[i], btn);
            }
            navPnl.add(Box.createRigidArea(new Dimension(5,0)));
            
            setLayout(new BorderLayout(5,5));
            add(canvasPnl,BorderLayout.CENTER);
            add(ctrlMainpnl,BorderLayout.WEST);
            add(navPnl,BorderLayout.NORTH);
            
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            
//            setSize(700,700);
//            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            setVisible(true);
        }
        catch(Exception e)
        {
            System.out.println("Excep in createEBFrm="+e);
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent ie)
    {
        try
        {
            if(ie.getStateChange()==ItemEvent.SELECTED)
            {
                Integer stk=(Integer)cboLineWidth.getSelectedItem();
                if(stk.intValue()<=0) throw new Exception();
                CanvasComponent.stroke=new BasicStroke(stk);
            }
        }
        catch(ClassCastException cls)
        {
            JOptionPane.showMessageDialog(this, "Characters not allowed.","Error",JOptionPane.ERROR_MESSAGE);
            cboLineWidth.setSelectedIndex(0);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "Zero or Negative Values not allowed.","Error",JOptionPane.ERROR_MESSAGE);
            cboLineWidth.setSelectedIndex(0);
            System.out.println("Excep in itmStateChg="+e);
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            AbstractButton absbtn=(AbstractButton)ae.getSource();
            String str=ae.getActionCommand();
            if(absbtn instanceof JButton)
            {
                //JButton btn=(JButton)absbtn;
                switch(str)
                {
                    case "<<":
                    {
                        int cno=Integer.parseInt(txtCardNo.getText());
                        cno--;
                        canvasCard.show(canvasPnl,cno+"");
                        txtCardNo.setText(cno+"");
                        if(cno==1)
                        {
                            JButton btn=hmNavbtn.get("<<");
                            btn.setEnabled(false);
                        }
                        if(cno<totalCanvas) hmNavbtn.get(">>").setEnabled(true);
                        break;
                    }
                    case ">>":
                    {
                        int cno=Integer.parseInt(txtCardNo.getText());
                        cno++;
                        canvasCard.show(canvasPnl,cno+"");
                        txtCardNo.setText(cno+"");
                        if(cno==totalCanvas)
                        {
                            hmNavbtn.get(">>").setEnabled(false);
                        }
                        if(cno>1) hmNavbtn.get("<<").setEnabled(true);
                        break;
                    }   
                    case "New Whiteboard":
                    {
                        CanvasComponent cc=new CanvasComponent(presenter);
                        cc.setBackground(Color.WHITE);
                        totalCanvas++;
                        hmCanvas.put(totalCanvas+"",cc);
                        canvasPnl.add(new JScrollPane(cc),totalCanvas+"");
                        hmNavbtn.get("<<").setEnabled(true);
                        hmNavbtn.get(">>").setEnabled(false);
                        txtCardNo.setText(totalCanvas+"");
                        canvasCard.show(canvasPnl,totalCanvas+"");
                        //canvasPnl.repaint();
                        //repaint();
                        break;
                    }
                    case "Open":
                    {
                        break;
                    }
                    case "Save":
                    {
                        String wbNm=JOptionPane.showInputDialog(this,"Enter Whiteboard name:","Save Whiteboard",JOptionPane.PLAIN_MESSAGE);
                        if(wbNm==null)
                        {
                            break;
                        }
                        System.out.println("error1");
                        CanvasComponent cc=hmCanvas.get(txtCardNo.getText());
                        
                        BufferedImage wb=new BufferedImage(cc.getWidth(), cc.getHeight(), BufferedImage.TYPE_INT_RGB);
                        cc.paintComponent(wb.getGraphics());
                        
                        ByteArrayOutputStream bos=new ByteArrayOutputStream();
                        ImageIO.write(wb,"jpeg", bos);
                        byte img[]=bos.toByteArray();
                        
                        
                        ByteArrayInputStream bin=new ByteArrayInputStream(img);
                        BufferedImage bimg=ImageIO.read(bin);
                        
                        FileOutputStream fos=new FileOutputStream("WB1.jpg");
                        fos.write(img);
                        fos.flush();
                        fos.close();
                        
                        
                        System.out.println("error5");
                        ArrayList al=new ArrayList();
                        al.add(wbNm);
                        al.add(img);
                        al.add(LectGenClass.coursenm);
                        al.add(LectGenClass.lectnm);
                        System.out.println("error10");
                        int rows=db.insert("Whiteboard", al);
                        System.out.println("error11");
                        
                        if(rows>0)
                        {
                            JOptionPane.showMessageDialog(this,"Whiteboard '"+wbNm+"' saved successfully.","Success",JOptionPane.INFORMATION_MESSAGE);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(this, "Some error occured.","Error",JOptionPane.ERROR_MESSAGE);
                        }
                        break;
                    }
                    case "Clear":
                    {
                        System.out.println("Clear");
                        CanvasComponent cc=hmCanvas.get(txtCardNo.getText().trim());
                        cc.alShape[0].clear();
                        cc.alShape[1].clear();
                        cc.alShape[2].clear();
                        cc.alShape[3].clear();
                        cc.draw=false;
                        cc.repaint();
                        //MouseAdapterClass.setCtrlName("Clear");
                        break;
                    }
                    case "Color":
                    {
                        Color c=JColorChooser.showDialog(this,"Select Line Color", Color.BLACK);
                        
                        if(c!=null)
                        {
//                            if(hmTogglebtn.get("Fill").isSelected())
//                            {
//                                CanvasComponent.fillColor=c;
//                            }
//                            else
 //                           {
                                CanvasComponent.color=c;
 //                           }
                        }
                        break;
                    }
//                    case "Line Width":
//                    {
//                        String stroke=JOptionPane.showInputDialog(this,"Enter line width","Line Width",JOptionPane.PLAIN_MESSAGE);
//                        if(stroke!=null)
//                        {
//                        //    int wd=Integer.parseInt(stroke);
//                            CanvasComponent.stroke=new BasicStroke(Integer.parseInt(stroke));
//                        }
//                        break;
//                    }
                }
                
            }
            else if(absbtn instanceof JToggleButton)
            {
                //JToggleButton tgb=(JToggleButton)absbtn;
                if(!selectedBtn.equals("")) hmTogglebtn.get(selectedBtn).setSelected(false);
                
                JToggleButton tgb=hmTogglebtn.get(str);
                if(tgb.isSelected())
                {
                    selectedBtn=str;
                    MouseAdapterClass.setCtrlName(str);
                }   
                else
                {
                    selectedBtn="";
                    MouseAdapterClass.setCtrlName("");
                }
//                    if(str.equals("Line Width"))
//                    {
//                        String stroke=JOptionPane.showInputDialog(this,"Enter line width","Line Width",JOptionPane.INFORMATION_MESSAGE);
//                        //tgb.setSelected(false);
//                        if(stroke!=null)
//                        {
//                            CanvasComponent.stroke=new BasicStroke(Integer.parseInt(stroke));
//                        }
//                    }
//                    else
//                    {
////                        selectedBtn=str;
//                        MouseAdapterClass.setCtrlName(str);
//                        if(selectedBtn.equals("Fill"))
//                        {
//                            CanvasComponent.fillColor=CanvasComponent.color;
//                            CanvasComponent.fill=true;
//                            //MouseAdapterClass.setFill(true);
//                        }
//                        else
//                        {
//                            MouseAdapterClass.setCtrlName(str);
                   //     }
                //    }
          //      }
                
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerfm="+e);e.printStackTrace();
        }
    }
    
    public static void setCanvas(ArrayList shape[])
    {
    //    hmCanvas.get("1").alShape=shape;
        hmCanvas.get("1").setShape(shape);
    }
}


class CanvasComponent extends JPanel
{
    
    GeneralPath pencil=new GeneralPath();
    GeneralPath erase=new GeneralPath();
    Rectangle rect,eraser;
    Ellipse2D.Double ellipse;
    Line2D.Double line;
    ArrayList alShape[]=new ArrayList[4];   //0-Shape  1-Color  2-Stroke 3-Fill
    static Color color=Color.BLACK;
    static Color fillColor;
    static boolean fill=false;
    static Stroke stroke=new BasicStroke(1);
    int x1,y1,x2,y2;
    boolean draw=false;
    
    Graphics2D g2d;
    TextComponent textComp;
    TextClass tcls;
    boolean presenter;
    
    public CanvasComponent(boolean p) 
    {
        presenter=p;
        for(int i=0;i<alShape.length;i++)
        {
            alShape[i]=new ArrayList();
        }
        if(presenter)
        {
            addMouseListener(new MouseAdapterClass(this));
            addMouseMotionListener(new MouseAdapterClass(this));
        }
    }
    
    public void setShape(ArrayList shape[])
    {
        alShape=shape;
        repaint();
    }
    
    @Override
    public void paintComponent(Graphics g)
    {
        try
        {
            super.paintComponent(g);
            g2d=(Graphics2D)g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
       //     g2d.setBackground(Color.WHITE);
            
            for(int i=0;i<alShape[0].size();i++)
            {
                if(alShape[0].get(i) instanceof TextClass)
                {
                    TextClass txtcls=(TextClass)alShape[0].get(i);
                    g2d.setFont(txtcls.fnt);
                    g2d.setColor((Color)alShape[1].get(i));
                    g2d.drawString(txtcls.strTxt,txtcls.xPos,txtcls.yPos);
                }
                else
                {
                    g2d.setColor((Color)alShape[1].get(i));
                    g2d.setStroke((Stroke)alShape[2].get(i));
                    g2d.draw((Shape)alShape[0].get(i));
                }
//                Color fillc=(Color)alShape[3].get(i);
//                if(fillc!=null)
//                {
//                    g2d.setColor(fillc);
//                    g2d.fill((Shape)alShape[0].get(i));
//                }
            }
            
            if(presenter)
            {
                g2d.setColor(color);
                g2d.setStroke(stroke);
                System.out.println("Draw="+draw);
                if(draw)
                {
                    String ctrl=MouseAdapterClass.getCtrlName();
                    System.out.println(ctrl);
                    switch(ctrl)
                    {
                        case "Pencil":
                        {
                            g2d.draw(pencil);
                            break;
                        }
                        case "Rectangle":
                        {
                        //    if(fill) fillComponent(ctrl);
                            g2d.drawRect(x1, y1,x2-x1,y2-y1);
                            break;
                        }
                        case "Oval":
                        {
                        //    if(fill) fillComponent(ctrl);
                            g2d.drawOval(x1, y1,x2-x1,y2-y1);
                            break;
                        }
                        case "Line":
                        {
                            g2d.drawLine(x1, y1, x2, y2);
                            break;
                        }
                        case "Eraser":
                        {
                            g2d.setColor(Color.WHITE);
                            g2d.draw(erase);
                            System.out.println("Eraser");
                            //g2d.fillRect(x1, y1,10,10);
                            //g2d.drawRect(x1, y1,10,10);
                            break;
                        }
                        case "Text":
                        {
                            System.out.println(textComp.getTxtString()+"fsdf");
                            g2d.setFont(textComp.getTxtFont());
                            g2d.drawString(textComp.jtaText.getText(),x1,y1);
                            break;
                        }
                    }
                }
            }
            System.out.println("repainted");
        }
        catch(Exception e)
        {
            System.out.println("Excep in paint="+e);e.printStackTrace();
        }
    }
    
    void fillComponent(String shp)
    {
        g2d.setColor(fillColor);
        if(shp.equals("Rectangle"))
        {
            g2d.fillRect(x1, y1,x2-x1,y2-y1);
        }
        else if(shp.equals("Oval"))
        {
            g2d.fillOval(x1, y1,x2-x1,y2-y1);
        }
    }
}

class MouseAdapterClass extends MouseAdapter
{
    int x1,y1,x2,y2;
    //private static int ctrlno;
    CanvasComponent canvas;
    private static String ctrlName="";
    private static boolean fill=false;
    //GeneralPath gp;

    static void setCtrlName(String nm)
    {
        ctrlName=nm;
    }
    
    static String getCtrlName()
    {
        return ctrlName;
    }
    
    static void setFill(boolean f)
    {
        fill=f;
    }
    
    static boolean getFill()
    {
        return fill;
    }
    
    public MouseAdapterClass(CanvasComponent cnv)
    {
        try
        {
            canvas=cnv;
        }
        catch(Exception e)
        {
            System.out.println("Excep in MseAdpCls==+e"+e);
        }
    }
    
    
    @Override
    public void mousePressed(MouseEvent me)
    {
        System.out.println("Mouse pressed");
        canvas.draw=true;
        try
        {
            x1=me.getX();
            y1=me.getY();
            canvas.x1=x1;
            canvas.y1=y1;
            switch(ctrlName)
            {
                case "Pencil":
                {
                    canvas.pencil=new GeneralPath();
                    
                    //gp=new GeneralPath();
                    canvas.pencil.moveTo(x1,y1);
                    System.out.println("Mouse pressed in pencil");
                    break;
                }
                case "Rectangle":
                {
                 //   Rectangle rect=canvas.rect;
                //    rect=new Rectangle(new Point(x1,y1));
                    canvas.g2d.drawRect(x1,y1,x1,y1);
                    break;
                    
                }
                case "Oval":
                {
                //    Ellipse2D.Double ellipse=canvas.ellipse;
                    canvas.g2d.drawOval(x1, y1, x1, y1);
                //    canvas.repaint();
                    break;
                }
                case "Line":
                {
                 //   Line2D.Double line=canvas.line;
                //    line=new Line2D.Double(ctrlno, ctrlno, ctrlno, ctrlno);
                    canvas.g2d.drawLine(x1, y1, x1, y1);
                //    canvas.repaint();
                    break;
                }
                case "Eraser":
                {
                //      canvas.g2d.setColor(Color.WHITE);
                      canvas.erase=new GeneralPath();
                      canvas.erase.moveTo(x1,y1);
                      System.out.println("Mouse pressed in eraser");
//                    canvas.g2d.setColor(Color.WHITE);
//                    canvas.g2d.fillRect(x1,y1,10,10);
//                    canvas.g2d.drawRect(x1,y1,10,10);
                    break;
                }
                case "Text":
                {
                    System.out.println("Hello");
                    canvas.textComp=new TextComponent(canvas,x1,y1);
                    System.out.println("TEXTCOMP="+canvas.textComp.toString());
                    canvas.removeMouseListener(this);
                    break;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in Press="+e);e.printStackTrace();
        }
    }
    
    @Override
    public void mouseReleased(MouseEvent me)
    {
        System.out.println("Mouse released");
        canvas.draw=false;
        try
        {
            x2=me.getX();
            y2=me.getY();
//            canvas.x2=x2;
//            canvas.y2=y2;
            switch(ctrlName)
            {
                case "Pencil":
                {
                    if(canvas.pencil!=null)
                    {
                        canvas.alShape[0].add(canvas.pencil);
                        canvas.alShape[1].add(CanvasComponent.color);
                        canvas.alShape[2].add(CanvasComponent.stroke);
                        canvas.alShape[3].add(null);
                        canvas.repaint();
                    }
                    break;
                }
                case "Rectangle":
                {
                    canvas.rect=new Rectangle(x1,y1,(x2-x1),(y2-y1));
                    canvas.alShape[0].add(canvas.rect);
                    canvas.alShape[1].add(CanvasComponent.color);
                    canvas.alShape[2].add(CanvasComponent.stroke);
                    if(CanvasComponent.fill) canvas.alShape[3].add(CanvasComponent.fillColor);
                    else canvas.alShape[3].add(null);
                    canvas.repaint();
                    break;
                }
                case "Oval":
                {
                    canvas.ellipse=new Ellipse2D.Double(x1, y1, (x2-x1),(y2-y1));
                    canvas.alShape[0].add(canvas.ellipse);
                    canvas.alShape[1].add(CanvasComponent.color);
                    canvas.alShape[2].add(CanvasComponent.stroke);
                    if(CanvasComponent.fill) canvas.alShape[3].add(CanvasComponent.fillColor);
                    else canvas.alShape[3].add(null);
                    canvas.repaint();
                    break;
                }
                case "Line":
                {
                    canvas.line=new Line2D.Double(x1, y1, x2, y2);
                    Line2D.Double line=canvas.line;
                    canvas.alShape[0].add(line);
                    canvas.alShape[1].add(CanvasComponent.color);
                    canvas.alShape[2].add(CanvasComponent.stroke);
                    canvas.alShape[3].add(null);
                    canvas.repaint();
                    break;
                }
                case "Eraser":
                {
                    if(canvas.erase!=null)
                    {
                        System.out.println("Eraser Released");
                        canvas.alShape[0].add(canvas.erase);
                        canvas.alShape[1].add(Color.WHITE);
                        canvas.alShape[2].add(CanvasComponent.stroke);
                        canvas.alShape[3].add(null);
                        canvas.repaint();
                    }
                    break;
                }
                case "Text":
                {
                    canvas.draw=true;
                }
            }
            
            LectureThreadClass.sendWhiteboard(canvas.alShape);
        }
        catch(Exception e)
        {
            System.out.println("Excep in Release="+e);e.printStackTrace();
        }
    }
    
    @Override
    public void mouseMoved(MouseEvent me)
    {
        try
        {
//            switch(ctrlName)
//            {
//                case "Pencil":
//                case "Rectangle":
//                case "Oval":
//                case "Line":
//            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in Move="+e);e.printStackTrace();
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent me)
    {
        System.out.println("Mouse dragged");
        try
        {
            x2=me.getX();
            y2=me.getY();
            canvas.x2=x2;
            canvas.y2=y2;
            switch(ctrlName)
            {
                case "Pencil":
                {
                    canvas.pencil.lineTo(x2,y2);
                    canvas.repaint();
                    break;
                }
                case "Rectangle":
                {
                //    if(CanvasComponent.fill) canvas.g2d.fillRect(x1, y1,x2-x1,y2-y1);
                    canvas.g2d.drawRect(x1, y1,(x2-x1),(y2-y1));
                    canvas.repaint();
                    break;
                }
                case "Oval":
                {
                //    if(CanvasComponent.fill) canvas.g2d.fillOval(x1, y1,x2-x1,y2-y1);
                    canvas.g2d.drawOval(x1, y1, (x2-x1), (y2-y1));
                    canvas.repaint();
                    break;
                }
                case "Line":
                {
                    canvas.g2d.drawLine(x1, y1, x2, y2);
                    canvas.repaint();
                    break;
                }
                case "Eraser":
                {
                    canvas.erase.lineTo(x2,y2);
                    canvas.repaint();
//                    canvas.g2d.fillRect(x2, y2,10,10);
//                    canvas.g2d.drawRect(x2,y2,10,10);
//                    canvas.repaint();
                    break;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in Dragged="+e);e.printStackTrace();
        }
    }
    
}

//class TextClass
//{
//    String strTxt;
//    int xPos,yPos,size,style;
//    Color txtColor;
//    Font fnt;
//}