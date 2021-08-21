package model;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 *
 * @author DARSHAN
 */
public class DBClass {

    private Connection con;
    private Statement st;
    private PreparedStatement pst;
    private ResultSet rs;
    private final String port,username,passwd,sid;
    HashMap<String,String> hm;

    public DBClass(String prt,String id,String unm,String pwd)
    {
        port=prt;
        sid=id;
        username=unm;
        passwd=pwd;
        //"insert into Course_User_Info(Course_name,Lect_name,User_name) values(?,?,?)",
        String insert_q[]={"insert into Course(Course_name,Presenter_unm) values(?,?)",
                               "insert into Lecture(Lect_name,Lect_content,Course_name) values(?,?,?)",
                               "insert into UserInfo(First_Name,Last_name,User_name,Password,Email_id,Email_pwd,User_Role) values(?,?,?,?,?,?,?)",
                        //       "insert into Course_Presenter_Info(Course_name,Presenter_unm) values(?,?)",
                               "insert into Course_Viewer_Info(Course_name,Lect_name,Viewer_unm) values(?,?,?)",
                               "insert into Chat(Chat_title,Chat_data,Course_name,Lect_name) values(?,?,?,?)",
                               "insert into Whiteboard(WBoard_title,WBoard_img,Course_name,Lect_name) values(?,?,?,?)",
                               "insert into Lecture_Video(LVideo_title,LVideo_data,Course_name,Lect_name) values(?,?,?,?)"};
        //String key[]={"Course","Lecture","UserInfo","Course_User_Info","Chat","Whiteboard","Lecture_Video"};
        String key[]={"Course","Lecture","UserInfo","Course_Viewer_Info","Chat","Whiteboard","Lecture_Video"};
        hm=new HashMap<>();
        for(int i=0;i<key.length;i++)
        {
            hm.put(key[i], insert_q[i]);
        }
    }
    
    private void connect()
    {
        try
        {
            System.out.println("sid="+sid);
            Class.forName("oracle.jdbc.OracleDriver");
            con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:"+port+":"+sid,username,passwd);
            
            String url="jdbc:oracle:thin:@"+
            "(DESCRIPTION = (ADDRESS = (PROTOCOL = TCP)(HOST = localhost)(PORT = "+port+"))(CONNECT_DATA = (SERVER = DEDICATED) (SERVICE_NAME = "+sid+")))";
            System.out.println(url);
//            con=DriverManager.getConnection(url,username,passwd); 
            
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
    
 /*   public void createDB()
    {
        try
        {
            String q="create database VClassroom identified by Virtual007";
            connect();
            st.executeUpdate(q);
            
            String q1="GRANT ALL PRIVILEGES to VClassroom WITH ADMIN OPTION";
            st.executeUpdate(q1);
        }
        catch(SQLException e)
        {
            System.out.println("SQLException in createDB"+e);
        }
        finally{
            close();
        }
    }
    
    public void createTable()
    {
        try
        {
            q_crtTable[0]="create table Course(Course_id varchar2(8) primary key,Course_name varchar2(30) NOT NULL)";
            //courseid=crsxxxxx
            
            q_crtTable[1]="create table Lecture(Lecture_id varchar2(8) primary key,Lect_name varchar2(30) NOT NULL,Lect_content varchar2(100) NOT NULL,Course_id varchar2(8) REFERENCES Course(Course_id) ON DELETE CASCADE)";
            //lect id= lctxxxxx
            
            q_crtTable[2]="create table User(User_id varchar2(8) primary key,User_name varchar2(30) NOT NULL,Password varchar2(30) NOT NULL,Type_of_User char(6) NOT NULL,Email_id varchar2(50) NOT NULL)";
            // presenter=prsntr,participant=partpt == Presenter & participant may have different tables
            // uid=usrxxxxx
            
            q_crtTable[3]="create table Course_User_Info(Course_id varchar2(8) references Course(Course_id) on delete cascade,Lecture_id varchar2(8) Lecture(Lecture_id) on delete cascade,User_id varchar2(8) references User(User_id) on delete cascade,PRIMARY KEY(Course_id,Lecture_id,User_id))";
            
            q_crtTable[4]="create table Chat(Chat_id varchar2(8) primary key,Chat_title varchar2(30) NOT NULL,Chat_Data blob NOT NULL,Course_id varchar2(8) REFERENCES Course(Course_id),Lecture_id varchar2(8) REFERENCES Lecture(Lecture_id))";
            // Course_id column may not be required
            // Chat id=chtxxxxx
            
            q_crtTable[5]="create table Whiteboard(WBoard_id varchar2(8) PRIMARY KEY, WBoard_title varchar2(30) NOT NULL,WBoard_img blob NOT NULL,Course_id varchar2(8) REFERENCES Course(Course_id),Lecture_id varchar2(8) REFERENCES Lecture(Lecture_id))";
            // Wbrd id= wbdxxxxx
            
            q_crtTable[6]="create table Lecture_Video(LVideo_id varchar2(8) PRIMARY KEY,LVideo_title varchar2(30) NOT NULL,LVideo_data blob NOT NULL,Course_id varchar2(8) REFERENCES Course(Course_id),Lecture_id varchar2(8) REFERENCES Lecture(Lecture_id))";
            // lvideo id=lvdxxxxx
            
            connect();
            for(int i=0;i<7;i++)
            {
                st.executeUpdate(q_crtTable[i]);
                System.out.println("Table "+i+" created");
            }
        }
        catch(SQLException e)
        {
            System.out.println("SQLException in createTable="+e);
        }
        finally
        {
            close();
        }
    }
    
    private void create_Sequence()
    {
        try
        {
            String seqnm[]={"Course_seq","Lect_seq","User_seq","Chat_seq","WB_seq","Lect_vd_seq"};
            connect();
            for (String snm : seqnm) 
            {
                String seq = "create sequence " + snm + " increment by 1 start with 1 MAXVALUE 99999 MINVALUE 1 CYCLE NOCACHE ORDER";
                st.executeUpdate(seq);
                System.out.println(snm+" created...");
            }
            
            String colnm[]={"Course_id","Lecture_id","User_id","Chat_id","WBoard_id","LVideo_id"};
            String tbnm[]={"Course","Lecture","User","Chat","Whiteboard","Lecture_Video"};
            String idprefix[]={"crs","lct","usr","cht","wbd","lvd"};
            
            for(int i=0;i<seqnm.length;i++)
            {
                String trigger="create or replace trigger "+colnm[i]+"_gen before INSERT ON "+tbnm[i]+" FOR EACH ROW "
                        + "DECLARE "
                        + "pk_val varchar2(10);"
                        + "BEGIN"
                        + "select "+ idprefix+" || LPAD(TO_CHAR("+seqnm[i]+".NEXTVAL),5,'0') into pk_val from dual;"
                        + ":new."+colnm[i]+":=pk_val;"
                        + "END;";
                cs=con.prepareCall(trigger);
                cs.executeUpdate(trigger);
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
    }*/
    
    public int insert(String tablenm,ArrayList values)
    {
        String q;
        int rows=0;
        try
        { 
            connect();
            System.out.println("1");
            q=hm.get(tablenm);
            pst=con.prepareStatement(q);
            Iterator it=values.iterator();
            for(int i=0;i<values.size();i++)
            {
                Object obj=it.next();
                if(i==1 && (tablenm.equals("Chat") || tablenm.equals("Whiteboard") || tablenm.equals("Lecture_Video")))
                {
                    byte b[]=(byte[])obj;
                    pst.setBytes(i+1, b);
                }
                else
                {
                    pst.setString(i+1,(String)obj);
                }
            }
            System.out.println("2"+pst.toString());
            rows=pst.executeUpdate();
            System.out.println(rows+" row inserted...");
            
        }
        catch(SQLException e)
        {
            System.out.println("Excep in insert="+e);e.printStackTrace();
            System.out.println("Err Code="+e.getErrorCode()+", Error msg="+e.getMessage()+", State="+e.getSQLState());
        }
        finally
        {
            close();
        }
        return rows;
    }
    
    public int update(String tablenm,String colnm[],String exp[],String condtn[],String logop[],String val[])
    {
        String q;
        int result=0;
        try
        {
            q="update "+tablenm+" set ";
            for(int i=0;i<colnm.length-1;i++)
            {
                q+=colnm[i]+"='"+exp[i]+"',";
            }
            q+=colnm[colnm.length-1]+"='"+exp[exp.length-1]+"'";
            
            if(condtn!=null)
            {
            //    q+=" where "+condtn+"='"+val+"'";
                q+=" where ";
                
                for(int i=0;i<condtn.length;i++)
                {
                    q+=condtn[i]+"='"+val[i]+"'";
                    
                    if(logop!=null && i<logop.length)
                    {
                        q+=" "+logop[i]+" ";
                    }
                }
            }
            System.out.println(q);
            connect();
            result=st.executeUpdate(q);
            System.out.println(result+" rows updated...");
        }
        catch(SQLException e)
        {
            System.out.println("Excep in update="+e);
        }
        finally
        {
            close();
        }
        return result;
    }
    
    public int delete(String tablenm,String condtn[],String val[],String opt[])
    {
        String q;
        int result=0;
        try
        {
            q="DELETE FROM "+tablenm;
            if(condtn!=null)
            {
                q+=" where ";
                
                for(int i=0;i<condtn.length;i++)
                {
                    if(val[i]==null)
                    {
                        q+=condtn[i]+" is NULL";
                    }
                    else
                    {
                        q+=condtn[i]+"='"+val[i]+"'";
                    }
                    if(opt!=null && i<opt.length)
                    {
                        q+=" "+opt[i]+" ";
                    }
                }
            }
            System.out.println("Delete="+q);
            connect();
            result=st.executeUpdate(q);
            System.out.println(result+" row deleted");
        }
        catch(SQLException e)
        {
            System.out.println("Excep in delete="+e);
        }
        finally
        {
            close();
        }
        return result;
    }
    
    public ArrayList select(String tablenm,String cols[],String condtn[],String val[],String opt[])
    {
        System.out.println("=======select method=======");
        String q;
        ArrayList result=new ArrayList();
        try
        {
            q="select ";
            System.out.println("Query="+q);
            for(int i=0;i<cols.length-1;i++)
            {
                q+=cols[i]+",";
            }
            q+=cols[cols.length-1]+" from "+tablenm;
            
            if(condtn!=null)
            {
                q+=" where ";
                
                for(int i=0;i<condtn.length;i++)
                {
                    if(val[i]==null)
                    {
                        q+=condtn[i]+" is NULL";
                    }
                    else
                    {
                        q+=condtn[i]+"='"+val[i]+"'";
                    }
                    
                    if(opt!=null && i<opt.length)
                    {
                        q+=" "+opt[i]+" ";
                    }
                }
            }
            System.out.println("Query="+q);
            
            connect();
            rs=st.executeQuery(q);
            
            ResultSetMetaData rsmd=rs.getMetaData();
            if((tablenm.equals("Chat") || tablenm.equals("Whiteboard") || tablenm.equals("Lecture_Video")))
            {
                while(rs.next())
                {
                    ArrayList row=new ArrayList();

                    //row.add(rs.getBlob(1));
                    for(int i=1;i<=rsmd.getColumnCount();i++)
                    {
                    //    row.add((i==2)?rs.getBlob(i):rs.getString(i));
                        if(i==2)
                        {
                            Blob blb=rs.getBlob(i);
                            byte imgByts[]=blb.getBytes(1,(int)blb.length());
                            row.add(imgByts);
                        }
                        else
                        {
                            row.add(rs.getString(i));
                        }
                    }   
                    result.add(row);
                }
            }
            else
            {
                while(rs.next())
                {
                    ArrayList row=new ArrayList();

                    for(int i=1;i<=rsmd.getColumnCount();i++)
                    {
                        row.add(rs.getString(i));
                    }
                    result.add(row);
                }
            }
            rs.close();
        }
        catch(SQLException e)
        {
            System.out.println("Excep in select="+e);e.printStackTrace();
        }
        finally
        {
            close();
        }
        return result;
    }
    
    private void close()
    {
        try
        {
            if(pst!=null)pst.close();
            if(st!=null)st.close();
            if(con!=null)con.close();
        }
        catch(SQLException e)
        {
            System.out.println("SQLException in close="+e);
        }
    }
}
