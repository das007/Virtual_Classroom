/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author DARSHAN
 */
public class Course {
    
    private String course_id,course_nm;
    DBClass db;
    
    public Course()
    {
        db=new DBClass("1521", "orcl", "VClassroom", "Virtual007");
    }

    public void setCourse_nm(String course_nm) 
    {
        this.course_nm = course_nm;
        create_Course();
    }

    public String getCourse_nm() 
    {
        return course_nm;
    }
    
    
    
    public void create_Course()
    {
        try
        {
            ArrayList al=new ArrayList();
            al.add(course_nm);
            db.insert("Course",al);
            System.out.println("Course '"+course_nm+"' created successfully...");
        }
        catch(Exception e)
        {
            System.out.println("Excep in insertCourse="+e);
        }
    }
    
    public void update_Course(String c_nm)
    {
        try
        {
            course_nm=c_nm;
            String colnm[]={"Course_name"};
            String exp[]={c_nm};
       //     db.update("Course",colnm, exp,"Course_id",course_id);
        }
        catch(Exception e)
        {
            System.out.println("Excep in updateCourse="+e);
        }
    }
    
    public void delete_Course(String c_id)
    {
        try
        {
        //    db.delete("Course","Course_id",c_id);
        }
        catch(Exception e)
        {
            System.out.println("Excep in deleteCourse="+e);
        }
    }
    
    public void view_Course(String c_id)
    {
        try
        {
  //          String colnm[]={"Course_name"};
  //          ArrayList result=db.select("Course", colnm,"Course_id",c_id);
  //          course_id=c_id;
 //           Iterator it=result.iterator();
  //          course_nm=(String)it.next();
        }
        catch(Exception e)
        {
            System.out.println("Excep in viewCourse="+e);
        }
    }
}
