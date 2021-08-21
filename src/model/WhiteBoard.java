/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author DARSHAN
 */
public class WhiteBoard {
    
    private String wbd_id,wbd_title;
    private BufferedImage bimg;
    DBClass db;
    
    public WhiteBoard()
    {
        db=new DBClass("1521", "orcl", "VClassroom", "Virtual007");
    }
    
    public void insert_WhiteBoard()
    {
        try
        {
            ByteArrayOutputStream bos=new ByteArrayOutputStream();
            ImageIO.write(bimg,"jpeg", bos);
            byte img[]=bos.toByteArray();
            ArrayList al=new ArrayList();
            al.add(wbd_title);
            al.add(img);
            db.insert("Whiteboard", al);
            System.out.println("WB "+wbd_title+" saved successfully...");
        }
        catch(Exception e)
        {
            System.out.println("Excep in insertWhiteBoard="+e);
        }
    }
    
    public void update_WhiteBoard()
    {
        try
        {
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in updateWhiteBoard="+e);
        }
    }
    
    public void delete_WhiteBoard()
    {
        try
        {
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in deleteWhiteBoard="+e);
        }
    }
    
    public void view_WhiteBoard()
    {
        try
        {
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in viewWhiteBoard="+e);
        }
    }
}
