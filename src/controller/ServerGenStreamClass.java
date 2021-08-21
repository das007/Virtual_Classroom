/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.util.ArrayList;

/**
 *
 * @author DARSHAN
 */
public class ServerGenStreamClass 
{
    public static ArrayList alClientStrm[];         // 0-Sok 1-oos  2-ois  3-unm

    public ServerGenStreamClass() 
    {
        alClientStrm=new ArrayList[4];
        for(int i=0;i<alClientStrm.length;i++)
        {
            alClientStrm[i]=new ArrayList();
        }
    }
    
}
