using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.IO;
using System.Text.RegularExpressions;

namespace NaveenCopyFile
{
    class Program
    {
        static void Main(string[] args)
        {
            string htmlFileName = "classwise.html";
            if (File.Exists(htmlFileName))
            {
                File.Delete(htmlFileName);
            }

            string CSVFile = "data\\result.csv";
            StreamWriter sw = new StreamWriter(htmlFileName, true);
            string line, sem1, batch, nos1,revstat;
            int nos, sem;
            string FirstPrt = Program.FirstPart();
            string SecondPrt = Program.SecondPart();
            sw.Write(FirstPrt);
            string str = @"var data = new google.visualization.DataTable();";
            str = str + "data.addColumn('string', 'Topping');";
            str = str + "data.addColumn('number', 'Slices');";
            str = str + "data.addRows([";
            sw.Write(str);

            //Read CSV Line by line 
            StreamReader srDummy = new StreamReader(CSVFile);
            string dummyLine = srDummy.ReadLine(); //Read First Line
            string[] infotemp1 = Regex.Split(dummyLine, ",");
            batch = infotemp1[1];
            sem1 = infotemp1[3];
	    revstat=infotemp1[5];
            sem = Convert.ToInt32(sem1);
            string[] infotemp2 = Regex.Split(dummyLine, "=");
            nos1 = infotemp2[1];
            nos = Convert.ToInt32(nos1);
            dummyLine = srDummy.ReadLine(); //Read Second Line
            using (StreamReader sr = new StreamReader(CSVFile))
            {
                line = sr.ReadLine();
                int cnt = 0;
                // while (((line = sr.ReadLine()) != null) || cnt<4) 
                while (cnt < 4)
                {
                    line = sr.ReadLine();
                    dummyLine = srDummy.ReadLine();
                    string fileLine = line;
                    string[] data = Regex.Split(fileLine, ",");

                    //if ((dummyLine != null) || cnt<4)
                    if (cnt < 3)
                        str = "['" + data[0] + "'," + data[1] + "],";
                    else
                        str = "['" + data[0] + "'," + data[1] + "]";
                    sw.Write(str);
                    cnt += 1;

                }

            }
            sw.Write(SecondPrt);
            int pointer = 8;
            dummyLine = srDummy.ReadLine();//The subject wise break down is as follows:
            if ((sem == 1) || (sem == 2))
            {
                for (int i = 0; i < 7; i++)
                    plot2function(htmlFileName, CSVFile, sw, srDummy, pointer + i * 5);
            }
            else if (sem == 8)
            {
                for (int i = 0; i < 6; i++)
                    plot2function(htmlFileName, CSVFile, sw, srDummy, pointer + i * 5);
            }
            else
            {
                for (int i = 0; i < 8; i++)
                    plot2function(htmlFileName, CSVFile, sw, srDummy, pointer + i * 5);
            }
            sw.Close();

            System.Diagnostics.Process.Start(htmlFileName);
        }

        static void plot2function(string htmlFileName, string CSVFile, StreamWriter sw, StreamReader srDummy, int pointer)
        {//Plotting Second Pie Chart
            string dummyLine = srDummy.ReadLine(); //Read First Line
            string title = dummyLine;
            string ThirdPrt = Program.ThirdPart();
            sw.Write(ThirdPrt);
            string FourthPrt = Program.FourthPart(title);
            string str2 = @"var data = new google.visualization.DataTable();";
            str2 = str2 + "data.addColumn('string', 'Topping');";
            str2 = str2 + "data.addColumn('number', 'Slices');";
            str2 = str2 + "data.addRows([";
            sw.Write(str2);
            using (StreamReader sr = new StreamReader(CSVFile))
            {
                string line;
                for (int i = 1; i <= pointer; i++)
                    line = sr.ReadLine();
                int cnt = 0;
                // while (((line = sr.ReadLine()) != null) || cnt<4) 
                while (cnt < 4)
                {
                    line = sr.ReadLine();
                    dummyLine = srDummy.ReadLine();
                    string fileLine = line;
                    string[] data = Regex.Split(fileLine, "= ,");

                    //if ((dummyLine != null) || cnt<4)
                    if (cnt < 3)
                        str2 = "['" + data[0] + "'," + data[1] + "],";
                    else
                        str2 = "['" + data[0] + "'," + data[1] + "]";
                    sw.Write(str2);
                    cnt += 1;

                }

            }
            sw.Write(FourthPrt);
            return;
        }

        static string FirstPart()
        {


	    string clavgx,bcx,revstatx,semx,nosx,CSVFile = "data\\result.csv";
	    StreamReader srDummy1 = new StreamReader(CSVFile);
            string dummyLine = srDummy1.ReadLine(); //Read First Line
            string[] infotemp1 = Regex.Split(dummyLine, ",");
            bcx = infotemp1[1];
            semx = infotemp1[3];
	    revstatx=infotemp1[5];
            string[] infotemp2 = Regex.Split(dummyLine, "=");
            nosx = infotemp2[1];
	    srDummy1.ReadLine();
	    srDummy1.ReadLine();
	    srDummy1.ReadLine();
	    srDummy1.ReadLine();
	    string[] infotemp3 = Regex.Split(srDummy1.ReadLine()," ");
	    clavgx = infotemp3[2];
	    srDummy1.Close();



            string FirstPart = @"<!DOCTYPE html PUBLIC "" -//W3C//DTD XHTML 1.0 Transitional//EN"" ""http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"">";
            FirstPart = FirstPart + @"<html>";
            FirstPart = FirstPart + @"<head>";
            FirstPart = FirstPart + @"<meta http-equiv=""Content-Type"" content=""text/html; charset=utf-8"" />";
            FirstPart = FirstPart + @"<title>classwise analysis</title>";
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
            FirstPart = FirstPart + @"<div class=""header_01"">Classwise Analysis:</div>";
            FirstPart = FirstPart + @"<p>The number of students who achieved FCD , First , Second or Third Class and Failures are displayed along with Individual                                         Subject Analysis</p>";
            FirstPart = FirstPart + @"</div>";
            FirstPart = FirstPart + @"<div class=""cleaner""></div></div></div>";
            FirstPart = FirstPart + @"<head>";
            FirstPart = FirstPart + @"<div class=""header_03"">";
            FirstPart = FirstPart + @"<pre>Batch Code= "+bcx+"      Sem= "+semx+"      Reval Updated: "+revstatx+"      No. Of Students= "+nosx;
            FirstPart = FirstPart + @"      Class Average Percentage= "+clavgx+"</pre>";
            FirstPart = FirstPart + @"</div><br /><br />";
            FirstPart = FirstPart + @"<script type=""text/javascript"" src=""https://www.google.com/jsapi""></script>";
            FirstPart = FirstPart + @"<script type=""text/javascript"">";
            FirstPart = FirstPart + @"google.load('visualization', '1.0', {'packages':['corechart']});";
            FirstPart = FirstPart + @"google.setOnLoadCallback(drawChart);";
            FirstPart = FirstPart + @"function drawChart() {";

            return FirstPart;
            // Create the data table.
        }

        static string SecondPart()
        {
            string SecondPart = @"]);";
            SecondPart += @"var options = {'title':'CLASS ANALYSIS',";
            SecondPart += @"'width':600,'is3D': true,";
            SecondPart += @"'height':500,'backgroundColor' : '#E0FFFF'};";
            SecondPart += @"var chart = new google.visualization.PieChart(document.getElementById('chart_div'));";
            SecondPart += @"chart.draw(data, options);";
            SecondPart += @"}";
            SecondPart += @"</script>";
            SecondPart += @"</head>";
            SecondPart += @"<body>";
            SecondPart += @"<!--Div that will hold the pie chart-->";
            SecondPart += @"<div STYLE=""position:relative; TOP:0px; LEFT:370px; WIDTH:500px; HEIGHT:500px"" id=""chart_div""></div>";
            SecondPart += @"<pre>                                                    ___________________________________________________________________________________</pre></body>";
            SecondPart += @"</html>";
            return SecondPart;
        }

        static string ThirdPart()
        {
            string ThirdPart = @"<html><head>";
            ThirdPart = ThirdPart + @"<script type=""text/javascript"" src=""https://www.google.com/jsapi""></script>";
            ThirdPart = ThirdPart + @"<script type=""text/javascript"">";
            ThirdPart = ThirdPart + @"google.load('visualization', '1.0', {'packages':['corechart']});";
            ThirdPart = ThirdPart + @"google.setOnLoadCallback(drawChart);";
            ThirdPart = ThirdPart + @"function drawChart() {";
            return ThirdPart;
        }

        static string FourthPart(string title)
        {
            string FourthPart = @"]);";
            FourthPart += @"var options = {'title':'";
            FourthPart += title;
            FourthPart += @"','width':600,";
            FourthPart += @"'height':500,'is3D': true,'backgroundColor' : '#E0FFFF'};";
            FourthPart += @"var chart = new google.visualization.PieChart(document.getElementById('";
            FourthPart += title;
            FourthPart += @"'));";
            FourthPart += @"chart.draw(data, options);";
            FourthPart += @"}";
            FourthPart += @"</script>";
            FourthPart += @"</head>";
            FourthPart += @"<body>";
            FourthPart += @"<!--Div that will hold the pie chart-->";
            FourthPart += @"<div STYLE=""position:relative; TOP:0px; LEFT:370px; WIDTH:500px; HEIGHT:500px"" id=""";
            FourthPart += title;
            FourthPart += @"""></div>";
            FourthPart += @"<pre>                                                    ___________________________________________________________________________________</pre></body>";
            FourthPart += @"</html>";
            return FourthPart;
        }

    }
}
