using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Text.RegularExpressions;

namespace rankplot
{
    class Program
    {
        static void Main(string[] args)
        {


	    string clavgx,bcx,revstatx,semx,nosx,CSVFile = "data\\result.csv";
	    StreamReader srDummyx = new StreamReader(CSVFile);
            string dummyLinex = srDummyx.ReadLine(); //Read First Line
            string[] infotemp1x = Regex.Split(dummyLinex, ",");
            bcx = infotemp1x[1];
            semx = infotemp1x[3];
	    revstatx=infotemp1x[5];
            string[] infotemp2x = Regex.Split(dummyLinex, "=");
            nosx = infotemp2x[1];
	    srDummyx.ReadLine();
	    srDummyx.ReadLine();
	    srDummyx.ReadLine();
	    srDummyx.ReadLine();
	    string[] infotemp3 = Regex.Split(srDummyx.ReadLine()," ");
	    clavgx = infotemp3[2];
	    srDummyx.Close(); 



            String htmlFileName = "rankplot.html";
            if (File.Exists(htmlFileName))
            {
                File.Delete(htmlFileName);
            }

            StreamWriter sw = new StreamWriter(htmlFileName, true);
            string line,batch,sem1,nos1,revstat;
            int t,sem,nos,count;
            string FirstPrt = Program.FirstPart();
            string SecondPrt = Program.SecondPart();
            sw.Write(FirstPrt);
            string str = @"<div class=""header_03"">";
            str = str + @"<pre>Batch Code= "+bcx+"      Sem= "+semx+"      Reval Updated: "+revstatx+"      No. Of Students= "+nosx;
            str = str + @"      Class Average Percentage= "+clavgx+"</pre>";
            str = str + @"</div>";
            str = str + @"<br /><br /><p><div class=""header_02"">Subject Averages:</div></p>";
            str = str + "<html>";
            str = str + "<table class=\"table1\">";
            sw.Write(str);

            //Read CSV Line by line 
            StreamReader srDummy = new StreamReader(CSVFile);
            string dummyLine = srDummy.ReadLine(); //Read First Line
            string[] infotemp1 = Regex.Split(dummyLine, ",");
            batch = infotemp1[1];
            sem1 = infotemp1[3];
            revstat=infotemp1[5];
            sem = Convert.ToInt32(sem1);
            if ((sem == 1) || (sem == 2))
                count = 7;
            else if (sem == 8)
                count = 6;
            else count = 8;
            string[] infotemp2 = Regex.Split(dummyLine, "=");
            nos1 = infotemp2[1];
            nos = Convert.ToInt32(nos1);
            
            if ((sem == 1) || (sem == 2))
            {
                for (t = 1; t <= 43; t++)
                    dummyLine = srDummy.ReadLine(); //Read Code			,Average
            }
            else if(sem==8)
            {
                for (t = 1; t <= 38; t++)
                    dummyLine = srDummy.ReadLine(); //Read Code			,Average
            }
            else
            {
                for (t = 1; t <= 48; t++)
                    dummyLine = srDummy.ReadLine(); //Read Code			,Average
            }
            using (StreamReader sr = new StreamReader(CSVFile))
            {
                if ((sem == 1) || (sem == 2))
                {
                    for (t = 1; t <= 43; t++)
                        line = sr.ReadLine();
                }
                else if (sem == 8)
                {
                    for (t = 1; t <= 38; t++)
                        line = sr.ReadLine();
                }
                else
                {
                    for (t = 1; t <= 48; t++)
                        line = sr.ReadLine();
                }
                int cnt = 1;
                // while (((line = sr.ReadLine()) != null) || cnt<4) 
		sr.ReadLine();
		    str = "<tr class=\"tr1\">";
                    str = str + "<th class=\"th1\">SUBJECT CODE</th>";
                    str = str + "<th class=\"th1\">CLASS AVERAGE</th>";
                    str = str + "</tr>";
		    sw.Write(str);
                while (cnt <= count)
                {
                    line = sr.ReadLine();
                    dummyLine = srDummy.ReadLine();
                    string fileLine = line;
                    string[] data = Regex.Split(fileLine, ",");

                    str = "<tr class=\"tr1\">";
                    str = str + "<td class=\"td1\">" + data[0] + "</td>";
                    str = str + "<td class=\"td1\">" + data[1] + "</td>";
                    str = str + "</tr>";
                    //if ((dummyLine != null) || cnt<4)
                    sw.Write(str);
                    cnt += 1;

                }
                sw.Write(SecondPrt);
                line = sr.ReadLine();
                dummyLine = srDummy.ReadLine();

	            str = "<tr class=\"tr1\">";
                    str = str + "<th class=\"th1\">SL NO.</th>";
                    str = str + "<th class=\"th1\">STUDENT NAME</th>";
                    str = str + "<th class=\"th1\">USN</th>";
                    str = str + "<th class=\"th1\">TOTAL MARKS</th>";
                    str = str + "</tr>";
		    sw.Write(str);
		    
                while ((line = sr.ReadLine()) != null)
                {
                    dummyLine = srDummy.ReadLine();
                    string fileLine1 = line;
                    string[] data1 = Regex.Split(fileLine1, ",");
		    
                    str = "<tr class=\"tr1\">";
                    str = str + "<td class=\"td1\">" + data1[0] + "</td>";
                    str = str + "<td class=\"td1\">" + data1[1] + "</td>";
                    str = str + "<td class=\"td1\">" + data1[2] + "</td>";
                    str = str + "<td class=\"td1\">" + data1[3] + "</td>";
                    str = str + "</tr>";
                    sw.Write(str);
                }
                str = "</table>";
                sw.Write(str);
                sw.Close();

                System.Diagnostics.Process.Start(htmlFileName);
            }
        }

        static string FirstPart()
        {
            string FirstPart = @"<!DOCTYPE html PUBLIC "" -//W3C//DTD XHTML 1.0 Transitional//EN"" ""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"">";
            FirstPart = FirstPart + @"<html>";
            FirstPart = FirstPart + @"<head>";
            FirstPart = FirstPart + @"<meta http-equiv=""Content-Type"" content=""text/html; charset=utf-8"" />";
            FirstPart = FirstPart + @"<title>Averages and Rank List</title>";
            FirstPart = FirstPart + @"<meta name=""keywords"" content=""free templates, website templates, CSS, XHTML"" />";
            FirstPart = FirstPart + @"<meta name=""description"" content=""Simple Gray - Professional free XHTML/CSS template provided by templatemo.com"" />";
            FirstPart = FirstPart + @"<link href=""templatemo_style.css"" rel=""stylesheet"" type=""text/css"" />";
            FirstPart = FirstPart + @"<script language=""javascript"" type=""text/javascript"">";
            FirstPart = FirstPart + @"function clearText(field)";
            FirstPart = FirstPart + @"{";
            FirstPart = FirstPart + @"if (field.defaultValue == field.value) field.value = '';";
            FirstPart = FirstPart + @"else if (field.value == '') field.value = field.defaultValue;";
            FirstPart = FirstPart + @"}";
            FirstPart = FirstPart + @"</script></head>";
            FirstPart = FirstPart + @"<body>";
            FirstPart = FirstPart + @"<div id=""templatemo_header_wrapper""><div id=""templatemo_header""><div id=""site_logo""></div><div id=""templatemo_menu"">";
            FirstPart = FirstPart + @"<div id=""templatemo_menu_left""></div><ul><li><a href=""test.html"" class=""current"">Home</a></li>";
            FirstPart = FirstPart + @"</ul></div></div></div>";
            FirstPart = FirstPart + @"<div id=""templatemo_banner_wrapper""><div id=""templatemo_banner""><div id=""templatemo_banner_image"">";
            FirstPart = FirstPart + @"<div id=""templatemo_banner_image_wrapper""><img src=""images/templatemo_image_01.jpg"" alt=""image 1"" />";
            FirstPart = FirstPart + @"</div></div>";
            FirstPart = FirstPart + @"<div id=""templatemo_banner_content"">";
            FirstPart = FirstPart + @"<div class=""header_01"">Averages and Rank:</div>";
            FirstPart = FirstPart + @"<p>The Class Average in each Subject is Displayed along with the Class Rank List</p>";
            FirstPart = FirstPart + @"</div>";
            FirstPart = FirstPart + @"<div class=""cleaner""></div></div></div>";
            return FirstPart;
            // Create the data table.
        }

        static string SecondPart()
        {
            string SecondPart = @"</table>";
            SecondPart += @"<br /><br /><br /><p><div class=""header_02"">Rank List:</div></p>";
            SecondPart += "<html>";
            SecondPart += "<table class=\"table1\">";
            return SecondPart;
        }
    }
}
        /*static string FinalPart()
        {
            string FinalPart = @"]);";
            FinalPart += @"var options = {'title':'CLASS ANALYSIS',";
            FinalPart += @"'width':600,";
            FinalPart += @"'height':500};";
            FinalPart += @"var chart = new google.visualization.PieChart(document.getElementById('chart_div'));";
            FinalPart += @"chart.draw(data, options);";
            FinalPart += @"}";
            FinalPart += @"</script>";
            FinalPart += @"</head>";
            FinalPart += @"<body>";
            FinalPart += @"<!--Div that will hold the pie chart-->";
            FinalPart += @"<div STYLE=""position:relative; TOP:0px; LEFT:370px; WIDTH:500px; HEIGHT:500px"" id=""chart_div""></div>";
            FinalPart += @"</body>";
            FinalPart += @"</html>";
            return FinalPart;
        }
    }
}
         */
