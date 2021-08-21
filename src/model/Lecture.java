/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;

/**
 *
 * @author DARSHAN
 */
public class Lecture {

    private String lect_id,lect_name,lect_content;
    DBClass db;
    
    public Lecture()
    {
        db=new DBClass("1521", "orcl","C##VClassroom", "Virtual007");
    }

    public void setLect_content(String lect_content) {
        this.lect_content = lect_content;
    }
    
    public void setLect_name(String lect_name) {
        this.lect_name = lect_name;
    }
    
    public void insert_Lecture()
    {
        try
        {
            ArrayList al=new ArrayList();
            al.add(lect_name);
            al.add(lect_content);
            db.insert("Lecture",al);
            System.out.println("Course '"+lect_name+"' created...");
        }
        catch(Exception e)
        {
            System.out.println("Excep in insertLecture="+e);
        }
    }
    
    public void update_Lecture()
    {
        try
        {
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in updateLecture="+e);
        }
    }
    
    public void delete_Lecture()
    {
        try
        {
        //    db.delete("Lecture", "Lecture_id", lect_id);
        }
        catch(Exception e)
        {
            System.out.println("Excep in deleteLecture="+e);
        }
    }
    
    public void view_Lecture()
    {
        try
        {
            String colnm[]={"Lect_name","Lect_content"};
   //         ArrayList result=db.select("Lecture", colnm, "Lecture_id", lect_id);
            
        }
        catch(Exception e)
        {
            System.out.println("Excep in viewLecture="+e);
        }
    }
}
