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

import java.text.DecimalFormat;
import java.io.*;
import org.jsoup.*;
import java.net.*;



public class Retrieve
{
    public String fname,bc,csv="data\\result.csv",revstat,ybusn[];
    public int count=0,sem;

    public static void main(String args[]) throws IOException
    {
        int reval;
        /*BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        System.out.println("enter 1 to update reval or 0 for result analysis");
        reval=Integer.parseInt(br.readLine());*/
        BufferedReader br1=new BufferedReader(new FileReader("data\\input.txt"));
        reval=Integer.parseInt(br1.readLine());
        br1.close();
        if(reval==1)  
        {
            reval re=new reval();
            re.input();
            Retrieve ret1=new Retrieve();
            ret1.input();
            ret1.calculate();
        }
        else
        {
            Retrieve ret=new Retrieve();
            ret.input();
            ret.calculate();
        }
    }
    
    void input()throws IOException
    {
        String text,ur1,roll;
        double prog;
        File csvfile=new File(csv);
        csvfile.delete();
        /*BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        
        System.out.println("Enter batch code: Eg-1DS11CS");
        bc=br.readLine();
        System.out.println("Enter the sem");
        sem=Integer.parseInt(br.readLine());*/
        BufferedReader br1=new BufferedReader(new FileReader("data\\input.txt"));
        br1.readLine();
        bc=br1.readLine();
        sem=Integer.parseInt(br1.readLine());
        ybusn=(br1.readLine()).split(",");
        
        System.out.println("Wait for the results to be fetched");
        try
        {
        fname=bc+"(sem"+sem+").txt";
        fname="data\\"+fname;
        File file=new File(fname);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
        
        try
        {
        count=0;
        for(int i=1;i<=500;i++)
        {
            roll="";
            if(i==400)
                bc=bc.substring(0, 3)+(Integer.parseInt(bc.substring(3, 5))+1)+bc.substring(5, 7);
            ur1="http://results.vtu.ac.in/vitavi.php?submit=true&rid="+bc;
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
            text=text.replaceAll("(\\w)+ (\\d)+, (\\d)+ ","");
            text=text.replaceAll("Welcome to VTUREVALUATION RESULTS \\(PROVISIONAL\\)","");
            text=text.replaceAll("Subject External Internal Total Result ","");
            text=text.replaceAll("Result:"," Result: ");
            text=text.replaceAll("Sd/- REGISTRAR\\(EVALUATION\\)","");
            //usn
            text=text.replaceAll(" \\(..........\\)","");
            //supplementary and diff sem
            text=text.replaceAll("Semester: "+sem,"sem: "+sem);
            text=text.replaceAll("(Semester:)(.+)(Total)","$3");
            //subject names
            text=text.replaceAll("(\\d) ([A-z]) ([^\\(]+)(\\()","$1 $2\r\n$4$3 - ");
            text=text.replaceAll("FIRST CLASS WITH DISTINCTION","FCD");
            text=text.replaceAll(" (CLASS|FAIL|FCD|LATER) ([^\\(]+)(\\()"," $1\r\n$3$2 - ");
            text=text.replaceAll("FIRST CLASS","FC");
            text=text.replaceAll("SECOND CLASS","SC");
            text=text.replaceAll("TO BE ANNOUNCED LATER","TBAL");
            //text=text.replaceAll("\r([^0-9]+)\\(MATDIP\\d+\\) \\d+ \\d+ \\d+ (P|F|A|W)","");
            text=text.replaceAll("\r\n(.+)MATDIP(.+)\r\n","\r\n");
            text=text.replaceAll(" Total Marks: ","\r\ntotal= ");
            //invalid
            text=text.replaceAll("Results are not yet available for this university seat number or it might not be a valid university seat number Click here to go back"," ");
            //progress
            prog=(i/500.0)*100.0;
            System.out.print("\r"+(int)prog+"%");
            if(text.length()>50)
            {
                pw.println(bc+roll);
                pw.println(text);
                count++;
                pw.println("**");
            }
        }
        for(String yx:ybusn)
        {try
        {
            
            ur1="http://results.vtu.ac.in/vitavi.php?submit=true&rid="+yx;
            
            text = Jsoup.parse(new URL(ur1),0).text();
            //weird space
            //text=text.replaceAll(" +","");
            text=text.replaceAll("[^\\w\\s!-~]+","");
            
            //space 
            text=text.replaceAll("  +","");
            //date
            text=text.replaceAll("(\\w)+ (\\d)+, (\\d)+ ","");
            text=text.replaceAll("Welcome to VTUREVALUATION RESULTS \\(PROVISIONAL\\)","");
            text=text.replaceAll("Subject External Internal Total Result ","");
            text=text.replaceAll("Result:"," Result: ");
            text=text.replaceAll("Sd/- REGISTRAR\\(EVALUATION\\)","");
            //usn
            text=text.replaceAll(" \\(..........\\)","");
            //supplementary and diff sem
            text=text.replaceAll("Semester: "+sem,"sem: "+sem);
            text=text.replaceAll("(Semester:)(.+)(Total)","$3");
            //subject names
            text=text.replaceAll("(\\d) ([A-z]) ([^\\(]+)(\\()","$1 $2\r\n$4$3 - ");
            text=text.replaceAll("FIRST CLASS WITH DISTINCTION","FCD");
            text=text.replaceAll(" (CLASS|FAIL|FCD|LATER) ([^\\(]+)(\\()"," $1\r\n$3$2 - ");
            text=text.replaceAll("FIRST CLASS","FC");
            text=text.replaceAll("SECOND CLASS","SC");
            text=text.replaceAll("TO BE ANNOUNCED LATER","TBAL");
            //text=text.replaceAll("\r([^0-9]+)\\(MATDIP\\d+\\) \\d+ \\d+ \\d+ (P|F|A|W)","");
            text=text.replaceAll("\r\n(.+)MATDIP(.+)\r\n","\r\n");
            text=text.replaceAll(" Total Marks: ","\r\ntotal= ");
            //invalid
            text=text.replaceAll("Results are not yet available for this university seat number or it might not be a valid university seat number Click here to go back"," ");
            if(text.length()>50)
            {
                pw.println(yx);
                pw.println(text);
                count++;
                pw.println("**");
            }
        }catch(Exception e)
        {
            System.out.println("Result not availlable for this year back usn:-");
            System.out.println(yx);
        }
        }
        bc=bc.substring(0, 3)+(Integer.parseInt(bc.substring(3, 5))-1)+bc.substring(5, 7);
        if(count==0)
        {
            System.out.println("");
            System.out.println("Results not available yet");
            pw.close();
            file.delete();
            System.exit(0);
        }
        System.out.println();
        pw.println("false");
        pw.print(count);
        pw.close();
        }
        catch(Exception e)   
        {
         System.out.println(e+"\n!!!!!!!!!!NOT ABLE TO CONNECT!!!!!!!!!!!");
         pw.close();
         file.delete();
         System.exit(0);
        }
        file.setReadOnly();
        }catch(FileNotFoundException e)
        {
            System.out.println("\n\n!!Original file already exists\n");
        }
    }
    void calculate() throws IOException
    {
        BufferedReader br=new BufferedReader(new FileReader(fname)),br2=new BufferedReader(new FileReader(fname));
        BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
        File xfile=new File(csv);
        PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(xfile)));
        System.out.println("Proceeding to Calculate");
        String cc,cc1="",cc2="";
        int nos=8,nots=6;//Number of subjects, theory subjects and lab subjects
        while((cc=br2.readLine())!=null)
        {
            cc2=cc1;
            cc1=cc;
        }
        count=Integer.parseInt(cc1);
        revstat=cc2;
        br2.close();
        pw.print("Batch name,"+bc+",Semester,"+sem+",Reval Updated,"+revstat);
        Students[] S=new Students[count+1]; 
        Ranks[] R=new Ranks[count+1];
        int nolines=0;
        if ( sem == 1 || sem == 2 )
        {
            nos=7;
            nots=5;
            nolines++;
        }   
        else if ( sem == 8 )
        {
           nos=6; 
           nots=4;
        }
        nolines+=nos+4;//Number of lines in file per student
        double outof;
        if( sem == 8)
           outof=nots*125.0+250.0; 
        else
           outof=nots*125.0+(nos-nots)*75;//Total marks i.e. 900 or 775
        int fcdmarks=(int)(0.7*outof);//For grace marks
        int fcmarks=(int)(0.6*outof);//For grace marks
        //Initialize array of objects for class Students
        for (int k=0;k<count+1;k++) 
           S[k]=new Students();
        //Initialize array of objects for class Ranks
        for (int k=0;k<count+1;k++) 
           R[k]=new Ranks();
        //Subject averages
        double[] subavg={0,0,0,0,0,0,0,0};
        //Subject wise breakdown
        int[][] subbreak;
        subbreak = new int[8][4];
        //Assign to 0
        for(int i=0;i<nos;i++)
            for(int j=0;j<4;j++)
                subbreak[i][j]=0;
       String line;
       String toks[]=new String[20];//Tokens 
       for(int i=1;i<=count;i++)
        {
        //For each student
        S[i].nop=0;
        S[i].nof=0;
        int z;
        S[i].name="";
        int l=0;//Number of lines read
        line=br.readLine(); 
        l++;
        // process the line.
        S[i].usn=line;
        R[i].usns=S[i].usn;
        line=br.readLine();
        l++;
              //Split into tokens
              toks=line.split("\\s");
              for(z=0;z<toks.length;z++)
              {
                  //Extract name
                  if(toks[z].equalsIgnoreCase("sem:"))
                      break;
                  S[i].name+=toks[z]+" ";
              }
              R[i].names=S[i].name;
              z++;//Skip "sem:"
              S[i].sem=Integer.parseInt(toks[z]);//Assign sem
              z+=2;//Skip "Result:"
              S[i].grade=toks[z];
              for(int j=0;j<nos;j++)
              {
                String codln;int z1,z2;
                line=br.readLine();
                z1=line.indexOf('(');z2=line.indexOf(')')+1;
                codln=line.substring(z1,z2);
                line=line.replaceFirst("\\(.+\\)","replace");
                l++;
                //Split tokens
                toks=line.split("\\s");
                //Subject code
                //S[i].code[j]=toks[0];
                S[i].code[j]=codln;
                //Marks in externals
                S[i].externals[j]=Integer.parseInt(toks[1]);
                //Marks in internals
                S[i].internals[j]=Integer.parseInt(toks[2]);
                //Total in subjecct
                S[i].marks[j]=Integer.parseInt(toks[3]);  
                //Calculate percentage of marks for each subject
                if( sem == 8 )
                {
                    if ( j<nots-1 || j == 4 )//First 3 theory subjects
                        S[i].subpercent[j]=S[i].marks[j]/125.0*100.0;
                    else if ( j==3 )//Project
                        S[i].subpercent[j]=S[i].marks[j]/200.0*100.0;
                    else//seminar
                        S[i].subpercent[j]=S[i].marks[j]/50.0*100.0;
                }
                else
                {    
                  if(j<nots) //Theory subjects
                    S[i].subpercent[j]=S[i].marks[j]/125.0*100.0;
                  else //Lab subjects
                    S[i].subpercent[j]=S[i].marks[j]/75.0*100.0;
                }
                  //For subject wise average
                subavg[j]+=S[i].subpercent[j]/100.0;
                //For subject wise breakdown
                S[i].fail[j]=false;//Default is pass
                if(toks[4].charAt(0)!='P') //Fail
                    subbreak[j][3]++;
                else if(S[i].subpercent[j]>=70) //FCD
                    subbreak[j][0]++;
                else if(S[i].subpercent[j]>=60) //First Class
                    subbreak[j][1]++;
                else //Second class
                    subbreak[j][2]++;
                //Number of subjects passed & failed
                if(toks[4].charAt(0)=='P')
                    S[i].nop++;
                else
                {
                    S[i].nof++;
                    S[i].fail[j]=true;//Failed
                }
                //Calculate total
                S[i].total+=S[i].marks[j];
              }
              //Grace marks for FCD
              if(S[i].total>=(fcdmarks-5)&&S[i].total<fcdmarks)//Grace marks for FCD
                  S[i].total=fcdmarks;
              if(S[i].total>=(fcmarks-5)&&S[i].total<fcmarks)//Grace marks for FCD
                  S[i].total=fcmarks;
              R[i].aggregate=S[i].total;
              //Percentage
              S[i].percent=S[i].total/(outof)*100.0;//CHANGE
              while(l<nolines)//Read remaining lines
              {
                 line=br.readLine();
                 l++;
              }
        }
       br.close();
       int nfcd=0,nfc=0,nsc=0,nf=0;
       double agg=0,avg;
       for(int i=1;i<=count;i++)
       {
           //Break down of Classes e.g. FCD etc
          if(S[i].grade.equalsIgnoreCase("FCD"))
              nfcd++;
          else if(S[i].grade.equalsIgnoreCase("FC"))
              nfc++;
          else if(S[i].grade.equalsIgnoreCase("SC"))
              nsc++;
          else
              nf++;
           //Total Percentage of all students
          agg+=S[i].percent;
       }
       //Average percentage of all students
       avg=agg/count;
       DecimalFormat df = new DecimalFormat("##.##");
       //Display break down of classes and average
       pw.println(",no. of stdents="+count);
       pw.println("FCD ,"+nfcd+"\r\n"+"First Class ,"+nfc+"\r\n"+"Second Class ,"+nsc+"\r\n"+"Fail ,"+nf);
       pw.println("Average  "+df.format(avg));
       /*System.out.println("Enter the no. of subjects for which you need to know\n the no. of students who have failed");
       int v=Integer.parseInt(br1.readLine());  
       System.out.println("The students who failed in "+v+" subject(s) are:");
       int c=0;
       for(int i = 1;i<=count;i++)
       {
           //Check for number of students who've failed given number of subjects 
           if(S[i].nof==v)
           {
               System.out.print(S[i].name+" "+S[i].usn+" ");
               for(int j=0;j<nos;j++)
               {
                   if(S[i].fail[j])
                       System.out.print(S[i].code[j]+" ");
               }
               c++;
               System.out.println("");
           }
       }
       if(c == 0)
       System.out.println("None");
       System.out.println("The number = "+c);
       //Subject analysis
       //Subject averages
       /*pw.println("The subject averages are: ");
       pw.println("  Code"+"\t\t\t"+"Average");
       for(int j=0;j<nos;j++)
       {
           subavg[j]=subavg[j]/count*100.0;
           pw.println(S[1].code[j]+"\t\t"+df.format(subavg[j]));
       }*/
       //Display subject wise breakdown
       pw.println("The subject wise break down is as follows:");
       for(int j=0;j<nos;j++)
       {
           pw.println(S[1].code[j]);
           pw.println("FCD = ,"+subbreak[j][0]+"\r\nFirst Class = ,"+subbreak[j][1]+
                   "\r\nSecond Class = ,"+subbreak[j][2]+"\r\nFail = ,"+subbreak[j][3]);
       }
       
    //Sort according to aggregate   
    Ranks r1;
    for (int i = 1; i <= count; i++) {
      for (int j = 1; j <= count - i ; j++) {
        if (R[j].aggregate < R[j+1].aggregate) /* For descending order use < */
        {
          r1=R[j];
          R[j]=R[j+1];
          R[j+1] =r1;
        }
      }
    }
       //Subject averages
    pw.println("The subject averages are: ");
       pw.println("  Code"+"\t\t\t"+",Average");
       for(int j=0;j<nos;j++)
       {
           subavg[j]=subavg[j]/count*100.0;
           pw.println(S[1].code[j]+"\t\t,"+df.format(subavg[j]));
       }
    int rank;  
    //class rank
    pw.println("The class rank list is as follows:");
    for(int i=1;i<=count;i++)
    {
        rank=i;
        //Joint ranks
        while(R[rank].aggregate==R[rank-1].aggregate)
            rank--;
        pw.println(rank+","+R[i].names+","+R[i].usns+","+R[i].aggregate);
    }
    pw.close();
  }
}
class Students
{
    String usn;
    String name;
    int sem;
    String grade;//FCD, First class, Second class, Fail
    String[] code=new String[8];//Subject code
    boolean[] fail=new boolean[8];//Keep track of failed subjects
    int[] externals=new int[8];
    int[] internals=new int[8];
    int[] marks=new int[8];
    double[] subpercent=new double[8];//Percentage in each subject
    int total;
    double percent;
    int nop; //Number of subjects passed  
    int nof; //Number of subjects failed
}
class Ranks
{
    String names;
    String usns;
    int aggregate;
}