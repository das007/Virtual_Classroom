/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

/**
 *
 * @author DARSHAN
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class PollQues implements Serializable
{
	String ques;
	String option[];
        int answer[];
	int type;
        ArrayList<String> alOpt;

	PollQues(String q,ArrayList op,int ans[],int t)
	{
		ques=q;
		alOpt=op;
                answer=ans;
		type=t;
                
                getStrOpt();
	}

        public int[] getAnswer() 
        {
            return answer;
        }

	int getType()
	{
		return type;
	}

	String getQues()
	{
		return ques;
	}

	String[] getOption()
	{
		return option;
	}
        
        private void getStrOpt()
        {
            int size=alOpt.size();
            option=new String[size];
            try
            {
                Iterator it=alOpt.iterator();
                int i=0;
                while(it.hasNext())
                {
                    option[i++]=(String)it.next();
                }
            }
            catch(Exception e)
            {
                System.out.println("Excep in getStrOpt="+e);
            }
        }
}