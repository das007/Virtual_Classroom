/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import com.sun.imageio.plugins.jpeg.JPEGImageWriter;
import java.awt.Robot;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author DARSHAN
 */
public class Lecture_Video {
    
    private String lect_vd_id,lect_vd_title;
    private byte lect_vd[];
    
    DBClass db;
    
    public Lecture_Video()
    { 
        db=new DBClass("1521", "orcl", "VClassroom", "Virtual007");
    }
    
    public void insert_Lecture_Video()
    {
        try
        {
            ArrayList al=new ArrayList();
            al.add(lect_vd_title);
            al.add(lect_vd);
            db.insert("Whiteboard", al);
            System.out.println("WB "+lect_vd_title+" saved successfully...");
        }
        catch(Exception e)
        {
            System.out.println("Excep in insertLecture_Video="+e);
        }
    }
    
    public void update_Lecture_Video()
    {
        try
        {
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in updateLecture_Video="+e);
        }
    }
    
    public void delete_Lecture_Video()
    {
        try
        {
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in deleteLecture_Video="+e);
        }
    }
    
    public void view_Lecture_Video()
    {
        try
        {
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in viewLecture_Video="+e);
        }
    }
}
