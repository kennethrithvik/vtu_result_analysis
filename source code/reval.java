/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auto.res.analysis;
/**
 *
 * @author Kenneth
 */
import java.io.*;
import java.net.*;
import org.jsoup.*;

public class reval
{
    String fname,bc,f2,ybusn[];
    int count=0,sem;
    public reval()throws IOException
    {
        Retrieve ret=new Retrieve();
        ret.input();
        bc=ret.bc;
        sem=ret.sem;
        ybusn=ret.ybusn;
        System.out.println("proceding to fetch reval");
    }
    
    void input()throws IOException
    {
        String text,ur1,roll;
        double prog;
        System.out.println("Wait for the reval results to be fetched");
        fname=bc+"(sem"+sem+").reval";
        fname="data\\"+fname;
        f2=bc+"(sem"+sem+").txt";
        f2="data\\"+f2;
        File file=new File(fname);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        //to check if reval already updated
        BufferedReader br1=new BufferedReader(new FileReader(f2));
        String cc,cc1="",cc2="";
        while((cc=br1.readLine())!=null)
        {
            cc2=cc1;
            cc1=cc;   
        }
        if(cc2.equals("true"))
        {
            System.out.println("Reval already updated\nexiting");
            pw.close();
            file.delete();
            return;
        }
        br1.close();
        try
        {
        count=0;
        for(int i=1;i<=500;i++)
        {
            roll="";
            if(i==400)
                bc=bc.substring(0, 3)+(Integer.parseInt(bc.substring(3, 5))+1)+bc.substring(5, 7);
            ur1="http://results.vtu.ac.in/vitavireval.php?submit=true&rid="+bc;
            if(i>0&&i<10)
                roll=roll+"00"+i;
            if(i>9&&i<100)
                roll=roll+"0"+i;
            if(i>99&&i<1000)
                roll=roll+i;
            ur1=ur1+roll;
            text = Jsoup.parse(new URL(ur1),0).text();
            //weird space
            //text=text.replaceAll(" +","");
            text=text.replaceAll("[^\\w\\s!-~]+","");

            //space 
            text=text.replaceAll("  +","");
            //date
            text=text.replaceAll("Welcome to VTU(.*)RESULTS\\(PROVISIONAL\\)","");
            text=text.replaceAll("Subject External Marks Old Final Internal Total Result","");
            text=text.replaceAll("Result:"," Result: ");
            //usn
            text=text.replaceAll(" \\(..........\\)","");
            //supplementary and diff sem
            text=text.replaceAll("Semester: "+sem,"sem: "+sem);
            text=text.replaceAll("(Semester:)(.+)(sem)","$3");
            text=text.replaceAll("(Semester:)(.+)","");
            //subject names
            text=text.replaceAll("(\\d) ([A-z]) ([^\\(]+)(\\()","$1 $2\r\n$4$3 - ");
            text=text.replaceAll("FIRST CLASS WITH DISTINCTION","FCD");
            text=text.replaceAll(" (CLASS|FAIL|FCD|LATER) ([^\\(]+)(\\()"," $1\r\n$3$2 - ");
            text=text.replaceAll("FIRST CLASS","FC");
            text=text.replaceAll("SECOND CLASS","SC");
            text=text.replaceAll("TO BE ANNOUNCED LATER","TBAL");
            //text=text.replaceAll("\r([^0-9]+)\\(MATDIP\\d+\\) \\d+ \\d+ \\d+ (P|F|A|W)","");
            text=text.replaceAll("\r\n(.+)MATDIP(.+)\r\n","\r\n");
            //marks order
            text=text.replaceAll("(\\d+) (\\d+) (\\d+) (\\d+)","$2 $3 $4");
            //invalid
            text=text.replaceAll("Results are not yet available for this university seat number or it might not be a valid university seat number Click here to go back"," ");
            //progress
            prog=(i/500.0)*100.0;
            System.out.print("\r"+(int)prog+"%");
            if(text.length()>40)
            {
                pw.println(bc+roll);
                pw.println(text);
                count++;
                pw.println("**");
            }
        }
        bc=bc.substring(0, 3)+(Integer.parseInt(bc.substring(3, 5))-1)+bc.substring(5, 7);
        if(count==0)
        {
            System.out.println("reval not available yet or none applied");
            pw.close();
            file.delete();
            System.exit(0);
        }
        System.out.println();
        pw.print(count);
        pw.close();
        comb();
        }
        catch(Exception e)   
        {
         System.out.println(e+"\n!!!!!!!!!!NOT ABLE TO CONNECT!!!!!!!!!!!");
         pw.close();
         file.delete();
         System.exit(0);
        }
    }
   
    void comb()throws IOException
    {
       String t1,t2; 
       int i,j;
       File fr=new File(fname);
       File fo=new File(f2);
       File ft=new File("data\\temp");
       BufferedReader or=new BufferedReader(new FileReader(fo));
       BufferedReader re=new BufferedReader(new FileReader(fr));
       PrintWriter tmp = new PrintWriter(new BufferedWriter(new FileWriter(ft)));
       
       while((t1=re.readLine())!=null)
       {
           i=0;j=0;
           String t3[]=new String[20];
           String t4[]=new String[20];
           if(t1.length()==10)
           {
               while((t2=or.readLine())!=null)
               {
                   if(t1.equals(t2))
                   {
                      tmp.println(t1);
                      tmp.println(re.readLine());
                      or.readLine();
                      while((t1=re.readLine()).equals("**")==false)
                          t3[i++]=t1;
                      while((t2=or.readLine()).matches("total= (\\d+)")==false)
                          t4[j++]=t2;
                      t4=match(t3,t4);
                      j=0;
                      while(t4[j]!=null)
                          tmp.println(t4[j++]);
                      tmp.println(t2);
                      tmp.println(or.readLine());
                      break; 
                   }
                   else if(t2.equals("false"))
                       tmp.println(true);
                   else if(t2.matches("\\d+"))
                       tmp.print(t2);
                   else
                       tmp.println(t2);
               }
           }
       }
       while((t2=or.readLine())!=null)
       {
           if(t2.equals("false"))
               tmp.println("true");
           else if(t2.matches("\\d+"))
               tmp.print(t2);
           else
               tmp.println(t2);
       }
       tmp.close();
       or.close();
       re.close();
       fo.delete();
       fr.delete();
       ft.renameTo(fo);
       fo.setReadOnly();
       System.out.println("reval results updated");
    }
    String[] match(String t3[],String t4[])
    {
        int i=0,j=0;
        while(t3[i]!=null)
        {
            j=0;
            while(t4[j]!=null)
            {
                if((t3[i].substring(t3[i].indexOf('(')+1,t3[i].indexOf(')'))).equals(t4[j].substring(t4[j].indexOf('(')+1,t4[j].indexOf(')'))))
                    t4[j]=t3[i];
                j++;
            }
            i++;
        }
        return t4;
    }
}