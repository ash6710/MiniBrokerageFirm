/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package excelimport;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author sharm
 */
public class ExcelImport {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
             BufferedReader bt= null;
        String line;
        ArrayList<Double> PrevclosePrice=new ArrayList<>();
        ArrayList<Double> OpenPrice=new ArrayList<>();
        ArrayList<Double> HighPrice=new ArrayList<>();
        ArrayList<Double> LowPrice=new ArrayList<>();
        ArrayList<Double> LastPrice=new ArrayList<>();
        ArrayList<Double> ClosePrice=new ArrayList<>();
        
        try {
            
            bt= new BufferedReader(new FileReader("C:\\Users\\sharm\\OneDrive\\Desktop\\sample_input22.txt"));
             
            while((line=bt.readLine())!=null)
            { 
                
                line=line.replaceAll("[,:{}-]"," ");
                line=line.replaceAll("\\s{2,}"," ").trim();
                if(line.contentEquals(" "))
                {
                    continue;
                }
                //System.out.println(line);
                String[] tokens=line.split(" ");
                List<String>wordlist=Arrays.asList(tokens);
                PrevclosePrice.add(Double.parseDouble(wordlist.get(4)));
                OpenPrice.add(Double.parseDouble(wordlist.get(5)));
                HighPrice.add(Double.parseDouble(wordlist.get(6)));
                LowPrice.add(Double.parseDouble(wordlist.get(7)));
                LastPrice.add(Double.parseDouble(wordlist.get(8)));
                ClosePrice.add(Double.parseDouble(wordlist.get(9)));
                //System.out.println(OpenPrice);
                if(ClosePrice.size()==15)
                {
                    averageStockprice(LastPrice);
                    maxDrawdown(ClosePrice,PrevclosePrice);
                    maxReturnPotential(OpenPrice,ClosePrice);
                }
                
                
            }
                }catch (IOException e){
            e.printStackTrace();
        }
        
    
    }
     
    public static void averageStockprice(ArrayList LastPrice)
    {
        double sum=0.0;
        for(int i=0;i<LastPrice.size();i++)
        {
            sum=sum+ (double)LastPrice.get(i);
        }
        System.out.println("The average price of a given stock: "+sum/15);
        
    }
    
    public static void maxDrawdown(ArrayList ClosePrice,ArrayList prevClosePrice)
    {
        double max=(double)prevClosePrice.get(0)-(double)ClosePrice.get(0);
        
        for(int i=0;i<ClosePrice.size();i++)
        {
            if(max<((double)prevClosePrice.get(i))-((double)ClosePrice.get(i)))
            {
                max=((double)prevClosePrice.get(i))-((double)ClosePrice.get(i));
            }
        }
         System.out.println("MaxDrawdown of the stock is: "+max);
    }
    
    
    public static void maxReturnPotential(ArrayList OpenPrice,ArrayList ClosePrice)
    {
        double maxpotential=0;
        double maxpotentialper=0;
        for(int i=0;i<OpenPrice.size();i++)
        {
            if(((double)OpenPrice.get(i)-(double)ClosePrice.get(i))>0)
                    {
                        maxpotential= maxpotential+((double)OpenPrice.get(i)-(double)ClosePrice.get(i));
                    }
            else if(((double)ClosePrice.get(i)-(double)OpenPrice.get(i)>0))
            {
                 maxpotential= maxpotential+(double)ClosePrice.get(i)-(double)OpenPrice.get(i);
            }
        }
        System.out.println("The Max Return Potential is: " + maxpotential);
        maxpotentialper=((100*maxpotential)/(double)OpenPrice.get(0));
        System.out.println("The Max Return Percentage Potential is: " + maxpotentialper);
    }
}
    
    
    

