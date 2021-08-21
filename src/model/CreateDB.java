/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author DARSHAN
 */
public class CreateDB {
    
    private final String q_crtTable[]=new String[7];
    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private CallableStatement cs;
    private ResultSet rs;
    private String port,username,passwd,sid;
    
    
    CreateDB(String prt,String id,String unm,String pwd)
    {
        port=prt;
        sid=id;
        username=unm;
        passwd=pwd;
        boolean flg=createDB("C##VClassroom07","Virtual07");      
        if(flg)
        {
            flg=createTable();
        }
        if(flg)
        {
        //    create_Sequence();
        }
    }

    private void connect()
    {
        try
        {
            Class.forName("oracle.jdbc.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:"+port+":"+sid,username,passwd);
            st=con.createStatement();
 //           pst=con.prepareStatement("insert into ? values(?,?,?,?,?)");
            
            // for createDB username= system and pwd=Oracle007 
            // but for CreateTable unm=VClassroom & pwd=Virtual007
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println("SQLException in connect="+e);
        }
    }
    
    private boolean createDB(String usernm,String pwd)
    {
        boolean flg=false;
        try
        {
         //   String q="create database C##VClassroom identified by Virtual007";
            String q="create user "+usernm+" identified by "+pwd;
            connect();
            st.executeUpdate(q);
            System.out.println("User "+usernm+" created successfully...");
            
            String q1="GRANT ALL PRIVILEGES to "+usernm+" WITH ADMIN OPTION";
            st.executeUpdate(q1);
            System.out .println("All Privileges granted to "+usernm);
            
            username=usernm;
            passwd=pwd;
            flg=true;
        }
        catch(SQLException e)
        {
            System.out.println("SQLException in createDB"+e);
        }
        finally{
            close();
        }
        return flg;
    }
    
    private boolean createTable()
    {
        boolean flg=false;
        try
        {
            q_crtTable[0]="create table UserInfo(User_name varchar2(30) primary key,First_name varchar2(25),Last_Name varchar2(25),Password varchar2(30) NOT NULL,User_Role char(1) NOT NULL,Email_id varchar2(50) NOT NULL,Email_pwd varchar2(30))";
//            q_crtTable[0]="create table Course(Course_name varchar2(30) primary key)";
            q_crtTable[1]="create table Course(Course_name varchar2(30) PRIMARY KEY,Presenter_unm varchar2(30) references UserInfo(User_name) ON DELETE CASCADE)";  //PRIMARY KEY(Course_name,Presenter_unm)
            //courseid=crsxxxxx
            
            q_crtTable[2]="create table Lecture(Lect_name varchar2(30) NOT NULL,Lect_content varchar2(200),Course_name varchar2(30) REFERENCES Course(Course_name) ON DELETE CASCADE,PRIMARY KEY(Lect_name,Course_name))";
            //lect id= lctxxxxx
            
      //      q_crtTable[0]="create table UserInfo(User_name varchar2(30) primary key,First_name varchar2(25),Last_Name varchar2(25),Password varchar2(30) NOT NULL,User_Role char(1) NOT NULL,Email_id varchar2(50) NOT NULL)";
            // presenter=p,participant=v(viewer) == Presenter & participant may have different tables
            // uid=usrxxxxx
            
            //q_crtTable[3]="create table Course_User_Info(Course_name varchar2(30),Lect_name varchar2(30),User_name varchar2(30) references UserInfo(User_name) on delete cascade,FOREIGN KEY(Lect_name,Course_name) references Lecture(Lect_name,Course_name) ON DELETE CASCADE,PRIMARY KEY(Course_name,Lect_name,User_name))";
      //      q_crtTable[3]="create table Course_Presenter_Info(Course_name varchar2(30) references Course(Course_name) on delete cascade,Presenter_unm varchar2(30) references UserInfo(User_name) on delete cascade,PRIMARY KEY(Course_name,Presenter_unm))";
            q_crtTable[3]="create table Course_Viewer_Info(Course_name varchar2(30),Lect_name varchar2(30),Viewer_unm varchar2(30) references UserInfo(User_name) on delete cascade,FOREIGN KEY(Lect_name,Course_name) references Lecture(Lect_name,Course_name) ON DELETE CASCADE,PRIMARY KEY(Course_name,Lect_name,Viewer_unm))";
            
            q_crtTable[4]="create table Chat(Chat_title varchar2(30) PRIMARY KEY,Chat_Data blob NOT NULL,Course_name varchar2(30),Lect_name varchar2(30),FOREIGN KEY(Lect_name,Course_name) REFERENCES Lecture(Lect_name,Course_name) ON DELETE SET NULL)";
            // Course_id column may not be required
            // Chat id=chtxxxxx
            
            q_crtTable[5]="create table Whiteboard(WBoard_title varchar2(30) PRIMARY KEY,WBoard_img blob NOT NULL,Course_name varchar2(30),Lect_name varchar2(30),FOREIGN KEY(Lect_name,Course_name) references Lecture(Lect_name,Course_name) ON DELETE SET NULL)";
            // Wbrd id= wbdxxxxx
            
            //q_crtTable[6]="create table Lecture_Video(LVideo_title varchar2(30) NOT NULL,LVideo_data blob NOT NULL,Course_name varchar2(30),Lect_name varchar2(30),FOREIGN KEY(Lect_name,Course_name) references Lecture(Lect_name,Course_name) ON DELETE SET NULL,PRIMARY KEY(LVideo_title,Course_name,Lect_name))";
            q_crtTable[6]="create table Lecture_Video(LVideo_title varchar2(30) PRIMARY KEY,LVideo_data blob NOT NULL,Course_name varchar2(30),Lect_name varchar2(30),FOREIGN KEY(Lect_name,Course_name) references Lecture(Lect_name,Course_name) ON DELETE SET NULL)";
            // lvideo id=lvdxxxxx
            
            connect();
            for(int i=0;i<q_crtTable.length;i++)
            {
                st.executeUpdate(q_crtTable[i]);
                System.out.println("Table "+i+" created");
            }
            flg=true;
        }
        catch(SQLException e)
        {
            System.out.println("SQLException in createTable="+e);
        }
        finally
        {
            close();
        }
        return flg;
    }
    
    private void create_Sequence()
    {
        try
        {
            String seqnm[]={"Course_seq","Lect_seq","Chat_seq","WB_seq","Lect_vd_seq"};
            connect();
            for (String snm : seqnm) 
            {
                String seq = "create sequence " + snm + " increment by 1 start with 1 MAXVALUE 99999 MINVALUE 1 NOCYCLE NOCACHE ORDER";
                st.executeUpdate(seq);
                System.out.println(snm+" created...");
            }
            
            String colnm[]={"Course_id","Lecture_id","Chat_id","WBoard_id","LVideo_id"};
            String tbnm[]={"Course","Lecture","Chat","Whiteboard","Lecture_Video"};
            String idprefix[]={"crs","lct","cht","wbd","lvd"};
            
            for(int i=0;i<seqnm.length;i++)
            {
                String trigger="create or replace trigger "+colnm[i]+"_gen before INSERT ON "+tbnm[i]+" FOR EACH ROW "
                        + "DECLARE "
                        + "pk_val varchar2(10);"
                        + "BEGIN "
                        + "select UPPER('"+ idprefix[i]+"') || LPAD(TO_CHAR("+seqnm[i]+".NEXTVAL),5,'0') into pk_val from dual;"
                        + ":new."+colnm[i]+":=pk_val;"
                        + "END;";
                System.out.println("trigger="+trigger);
                st.executeUpdate(trigger);
                System.out.println("Trigger "+colnm[i]+"_gen created successfully...");
            }
        }
        catch(SQLException e)
        {
            System.out.println("Excep in create_Seq="+e);
        }
        finally
        {
            close();
        }
    }
    
    private void close()
    {
        try
        {
            st.close();
            con.close();
        }
        catch(Exception e)
        {
            System.out.println("SQLException in close="+e);
        }
    }
}


/*
            q_crtTable[0]="create table Course(Course_id varchar2(8),Course_name varchar2(30) primary key)";
            //courseid=crsxxxxx
            
            q_crtTable[1]="create table Lecture(Lecture_id varchar2(8),Lect_name varchar2(30) NOT NULL,Lect_content varchar2(100) NOT NULL,Course_id varchar2(8) REFERENCES Course(Course_name) ON DELETE CASCADE,PRIMARY KEY(Lect_name,Course_name))";
            //lect id= lctxxxxx
            
            q_crtTable[2]="create table UserInfo(User_name varchar2(30) primary key,First_name varchar2(25),Last_Name varchar2(25),Password varchar2(30) NOT NULL,User_Role char(1) NOT NULL,Email_id varchar2(50) NOT NULL)";
            // presenter=p,participant=v(viewer) == Presenter & participant may have different tables
            // uid=usrxxxxx
            
            q_crtTable[3]="create table Course_User_Info(Course_name varchar2(30) references Course(Course_name) on delete cascade,Lect_name varchar2(30) references Lecture(Lect_name) on delete cascade,User_name varchar2(30) references UserInfo(User_name) on delete cascade,PRIMARY KEY(Course_name,Lect_name,User_name))";
            
            q_crtTable[4]="create table Chat(Chat_id varchar2(8),Chat_title varchar2(30) NOT NULL,Chat_Data blob NOT NULL,Course_name varchar2(30) REFERENCES Course(Course_name)  on delete cascade,Lect_name varchar2(30) REFERENCES Lecture(Lect_name) on delete cascade,PRIMARY KEY(Chat_title,Course_name,Lect_name))";
            // Course_id column may not be required
            // Chat id=chtxxxxx
            
            q_crtTable[5]="create table Whiteboard(WBoard_id varchar2(8), WBoard_title varchar2(30) NOT NULL,WBoard_img blob NOT NULL,Course_name varchar2(30) REFERENCES Course(Course_id on delete cascade),Lect_name varchar2(30) REFERENCES Lecture(Lecture_id) on delete cascade,PRIMARY KEY(WBoard_title,Course_name,Lect_name))";
            // Wbrd id= wbdxxxxx
            
            q_crtTable[6]="create table Lecture_Video(LVideo_id varchar2(8),LVideo_title varchar2(30) NOT NULL,LVideo_data blob NOT NULL,Course_name varchar2(30) REFERENCES Course(Course_id) on delete cascade,Lect_name varchar2(30) REFERENCES Lecture(Lecture_id) on delete cascade,PRIMARY KEY(LVideo_title,Course_name,Lect_name))";
            // lvideo id=lvdxxxxx
*/