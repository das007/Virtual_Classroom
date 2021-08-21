/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author DARSHAN
 */
public class TextComponent extends JDialog implements ItemListener,DocumentListener,ListSelectionListener,ActionListener
{
    JList<String> lstFont,lstStyle;
    JComboBox<Integer> cboSize;
    JTextArea jtaText;
    JButton btnOk,btnCancel;
    
    JPanel pnlFont,pnlSize,pnlFontMain,pnlBtn,pnlMain;
    
    String fontNm,text;
    int fontStyle,fontSize;
    
    Font txtFont;
    CanvasComponent canvas;
    int x1,y1;

    TextComponent(CanvasComponent cnv,int x,int y) 
    {
        canvas=cnv;
        x1=x;
        y1=y;
        createDialog();
    }
    
    private void createDialog()
    {
        try
        {
            System.out.println("WORLD");
            if(txtFont!=null)System.out.println("txtFOnt="+txtFont.getFontName());
            GraphicsEnvironment ge=GraphicsEnvironment.getLocalGraphicsEnvironment();
            String fontName[]=ge.getAvailableFontFamilyNames();

            String style[]={"Plain","Italic","Bold","Bold Italic"};

            Integer size[]={10,12,14,16,20,22,25,30,40,50,72};

            fontNm=fontName[0];
            fontStyle=Font.PLAIN;
            fontSize=size[0];

            lstFont=new JList(fontName);
            lstFont.setSelectedValue(fontNm,true);
       //     lstFont.setSelectedIndex(0);
            lstFont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lstFont.addListSelectionListener(this);
            JScrollPane jspFont=new JScrollPane(lstFont);
            jspFont.setBorder(BorderFactory.createTitledBorder("Font"));

            lstStyle=new JList(style);
            lstStyle.setSelectedIndex(0);
            lstStyle.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            lstStyle.addListSelectionListener(this);
            lstStyle.setFixedCellWidth(100);
            JScrollPane jspStyle=new JScrollPane(lstStyle);
            jspStyle.setBorder(BorderFactory.createTitledBorder("Font Style"));

            cboSize=new JComboBox<>(size);
            cboSize.addItemListener(this);
            cboSize.setBorder(BorderFactory.createTitledBorder("Size"));
            cboSize.setEditable(true);

            jtaText=new JTextArea(10,1);
            jtaText.setFont(new Font(fontNm,fontStyle,fontSize));
            jtaText.getDocument().addDocumentListener(this);

            JScrollPane jspText=new JScrollPane(jtaText);
            jspText.setBorder(BorderFactory.createTitledBorder("Enter Text"));

            pnlFont=new JPanel(new FlowLayout(FlowLayout.CENTER,0,5));
            pnlFont.add(jspFont);
            pnlFont.add(jspStyle);

            pnlFontMain=new JPanel(new BorderLayout());
            pnlFontMain.add(pnlFont,BorderLayout.CENTER);
            pnlFontMain.add(cboSize,BorderLayout.SOUTH);

            btnOk=new JButton("Ok");
            btnOk.addActionListener(this);
            btnCancel=new JButton("Cancel");
            btnCancel.addActionListener(this);
            pnlBtn=new JPanel(new FlowLayout());
            pnlBtn.add(btnOk);
            pnlBtn.add(btnCancel);

            pnlMain=new JPanel(new BorderLayout(0,5));
            //setLayout(new BorderLayout(0,5));
            pnlMain.add(pnlFontMain,BorderLayout.NORTH);
            pnlMain.add(jspText,BorderLayout.CENTER);
            pnlMain.add(pnlBtn,BorderLayout.SOUTH);
            pnlMain.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

            setLayout(new FlowLayout());
 //           setModal(true);
            add(pnlMain);
            setSize(350,450);
            setTitle("Add Text");
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setResizable(false);
            System.out.println("Dialog created1");
            setVisible(true);
            System.out.println("Dialog created2");
        }
        catch(Exception e)
        {
            System.out.println("Excpe in createDiag="+e);
        }
        System.out.println("Dialog created3");
    }
    
    String getTxtString()
    {
        System.out.println("");
        return text;
    }
    
    Font getTxtFont()
    {
        return txtFont;
    }
    
    @Override
    public void actionPerformed(ActionEvent ae)
    {
        try
        {
            String actcmd=ae.getActionCommand();
            switch(actcmd)
            {
                case "Ok":
                {
                    TextClass txtCls=new TextClass(text,txtFont, x1, y1);
                    canvas.alShape[0].add(txtCls);
                    canvas.alShape[1].add(CanvasComponent.color);
                    canvas.alShape[2].add(null);
                    canvas.addMouseListener(new MouseAdapterClass(canvas));
                    dispose();
                    break;
                }
                case "Cancel":
                {
                    canvas.draw=false;
                    canvas.repaint();
                    canvas.addMouseListener(new MouseAdapterClass(canvas));
                    dispose();
                    break;
                }
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in actPerfm="+e);
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
                if(lst.equals(lstFont))
                {
                    fontNm=lst.getSelectedValue().toString();
                //    jtaText.setFont(new Font(fontNm,fontStyle,fontSize));
                }
                else if(lst.equals(lstStyle))
                {
                    String style=lst.getSelectedValue().toString();
                    switch(style)
                    {
                        case "Plain":
                        {
                            fontStyle=Font.PLAIN;
                            break;
                        }
                        case "Italic":
                        {
                            fontStyle=Font.ITALIC;
                            break;
                        }
                        case "Bold":
                        {
                            fontStyle=Font.BOLD;
                            break;
                        }
                        case "Bold Italic":
                        {
                            fontStyle=Font.BOLD | Font.ITALIC;
                            break;
                        }
                    }
                }
                txtFont=new Font(fontNm,fontStyle,fontSize);
                jtaText.setFont(txtFont);
                canvas.repaint();
            }
        }
        catch(Exception e)
        {
            System.out.println("Excep in valChg="+e);
        }
    }
    
    @Override
    public void itemStateChanged(ItemEvent ie)
    {
        try
        {
            if(ie.getStateChange()==ItemEvent.SELECTED)
            {
                fontSize=((Integer)cboSize.getSelectedItem()).intValue();
                if(fontSize<=0) throw new Exception();
                txtFont=new Font(fontNm,fontStyle,fontSize);
                jtaText.setFont(txtFont);
                canvas.repaint();
            }
        }
        catch(ClassCastException e)
        {
            JOptionPane.showMessageDialog(this, "Characters not allowed.","Error",JOptionPane.ERROR_MESSAGE);
            cboSize.setSelectedIndex(0);
        }
        catch(Exception e)
        {
            JOptionPane.showMessageDialog(this, "Zero or Negative Values not allowed.","Error",JOptionPane.ERROR_MESSAGE);
            cboSize.setSelectedIndex(0);
            System.out.println("Excep in itmChg="+e);
        }
    }
    
    @Override
    public void changedUpdate(DocumentEvent de)
    {
        try
        {
            text=jtaText.getText();
            System.out.println(text);
         //   canvas.g2d.drawString(jtaText.getText(),x1,y1);
            canvas.repaint();
        }
        catch(Exception e)
        {
            System.out.println("Excep in chg="+e);
        }
    }
    
    @Override
    public void insertUpdate(DocumentEvent de)
    {
        try
        {
            text=jtaText.getText();
            System.out.println(text);
        //    canvas.g2d.drawString(jtaText.getText(),x1,y1);
            canvas.repaint();
        }
        catch(Exception e)
        {
            System.out.println("Excep in ins="+e);
        }
    }
    
    @Override
    public void removeUpdate(DocumentEvent de)
    {
        try
        {
            text=jtaText.getText();
            System.out.println(text);
         //   canvas.g2d.drawString(jtaText.getText(),x1,y1);
            canvas.repaint();
        }
        catch(Exception e)
        {
            System.out.println("Excep in remove="+e);
        }
    }
    
    public static void main(String arg[])
    {
     //   TextComponent tc=new TextComponent();
    }
}


class TextClass
{
    String strTxt;
    int xPos,yPos;
    Font fnt;

    public TextClass(String str,Font f,int x,int y) 
    {
        strTxt=str;
        fnt=f;
        xPos=x;
        yPos=y;
    }
    
    
}