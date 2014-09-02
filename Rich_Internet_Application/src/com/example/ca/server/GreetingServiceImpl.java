package com.example.ca.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.example.ca.client.GreetingService;
import com.example.ca.client.SingleClass;
import com.example.ca.client.TimetableClass;
import com.example.utils.Day;
import com.google.gwt.dom.client.Node;
import com.google.gwt.dom.client.Text;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.XMLParser;

/**
 * The server side implementation of the RPC service.
 */


@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements
		GreetingService {
	ArrayList<TimetableClass> listOfTimetables = new ArrayList<TimetableClass>();
	
	private static Day day1 = new Day("Monday");
	private static Day day2 = new Day("Tuesday");
	private static Day day3 = new Day("Wednesday");
	private static Day day4 = new Day("Thursday");
	private static Day day5 = new Day("Friday");
	@Override
	public String greetServer(String name) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<TimetableClass> getTimetables() throws Exception {

		        URL url = new URL("http://timetables.dkit.ie/js/filter.js");
		        
		        
		        BufferedReader in = new BufferedReader(
		        new InputStreamReader(url.openStream()));

		
		        ArrayList<String> list = new ArrayList<String>();
		        
		        String inputLine;
		        int addToList = 0;
		        while ((inputLine = in.readLine()) != null)
		        {
		        	if(inputLine.contains("studsetarray[i] = new Array(2)") || addToList >= 2)
		        	{
		        		addToList = addToList+1;
				        	if(inputLine.contains("studsetarray["))
				        	{
				        		list.add(inputLine);
				        	}
		        	}
		        }
		        in.close();
		        
		        
		        for(int i = 0; i<list.size();i++)
		        {
		        	if(list.get(i).contains("studsetarray[i]"))
		        	{
		        		list.remove(i);
		        		i--;
		        	}
		        }
		        
		
		        
		        //for every 3 elements in the list
		        //create 1 TimeTableClass object
		        
		        for(int i = 0, j = 0; i<list.size();i=i+3, j++)
		        {
		        	TimetableClass tc = new TimetableClass();
		        	listOfTimetables.add(tc);
		        }
		        
		        for(int i = 0, j = 0; i<list.size();i=i+3, j++)
		        {
		        	listOfTimetables.get(j).setTitle(list.get(i));
		        	listOfTimetables.get(j).setIdentifier(list.get(i+1));
		        	listOfTimetables.get(j).setValue(list.get(i+2));
		        }
		        
		        
		        //the next 3 methods remove all the unwanted characters from each of the 3 field in a TimeTableClass object
		        
		        for(int i = 0; i<listOfTimetables.size();i++)
		        {
		        	String s = listOfTimetables.get(i).getTitle();
		        	boolean parse = false;
		        	for(int j =0;j<s.length();j++)
		        	{
		        		if(s.subSequence(j, j+1).equals("="))
		        		{
		        			parse = true;
		        			if(parse == true)
		        			{
		        				listOfTimetables.get(i).setTitle(s.substring(j+3, s.length()-2));
		        				parse = false;
		        			}
		        		}
		        		
		        	}
		        }
		        
		        for(int i = 0; i<listOfTimetables.size();i++)
		        {
		        	String s = listOfTimetables.get(i).getIdentifier();
		        	boolean parse = false;
		        	for(int j =0;j<s.length();j++)
		        	{
		        		if(s.subSequence(j, j+1).equals("="))
		        		{
		        			parse = true;
		        			if(parse == true)
		        			{
		        				listOfTimetables.get(i).setIdentifier(s.substring(j+3, s.length()-2));
		        				parse = false;
		        			}
		        		}
		        		
		        	}
		        }
		        
		        for(int i = 0; i<listOfTimetables.size();i++)
		        {
		        	String s = listOfTimetables.get(i).getValue();
		        	boolean parse = false;
		        	for(int j =0;j<s.length();j++)
		        	{
		        		if(s.subSequence(j, j+1).equals("="))
		        		{
		        			parse = true;
		        			if(parse == true)
		        			{
		        				listOfTimetables.get(i).setValue(s.substring(j+3, s.length()-2));
		        				parse = false;
		        			}
		        		}
		        		
		        	}
		        }
				return listOfTimetables;
			}
	
	
	public ArrayList<SingleClass> displayTimetable(String value) throws Exception {
		Document doc = Jsoup.connect("http://timetables.dkit.ie/process.php?url=http%3A//timetable-01.ad.dkit.ie%3A8080/reporting/textspreadsheet%3Bstudent+set%3Bid%3B"+value+"%250D%250A%3Ft%3Dstudent+set+textspreadsheet%26days%3D1-5%26weeks%3D%26periods%3D1-24%26template%3Dstudent+set+textspreadsheet").get();

		ArrayList<SingleClass> list = new ArrayList<SingleClass>();

		//a new element is created for each day of the week if that weekend is contained in the document
		
		Element monday = doc.select("p ~ table").first();
		Element tuesday = doc.select("p ~ table").get(1);
		Element wednesday = doc.select("p ~ table").get(2);
		Element thursday = doc.select("p ~ table").get(3);
		Element friday = doc.select("p ~ table").get(4);
		

			
		ArrayList<SingleClass> monClasses = new ArrayList<SingleClass>();
		ArrayList<SingleClass> tuesClasses = new ArrayList<SingleClass>();
		ArrayList<SingleClass> wedClasses = new ArrayList<SingleClass>();
		ArrayList<SingleClass> thurClasses = new ArrayList<SingleClass>();
		ArrayList<SingleClass> friClasses = new ArrayList<SingleClass>();

		//the first table row contains unwanted text so it is removed
		
		monday.select("tr:eq(0)").remove();
		tuesday.select("tr:eq(0)").remove();
		wednesday.select("tr:eq(0)").remove();
		thursday.select("tr:eq(0)").remove();
		friday.select("tr:eq(0)").remove();

		//the table that follows an occurrence of the name of a weekend in the document text contains all the timetable information for that day
		
		Elements mon = monday.select("td");
		Elements tues = tuesday.select("td");
		Elements wed = wednesday.select("td");
		Elements thurs = thursday.select("td");
		Elements fri = friday.select("td");
		
		createTimetable(monClasses, mon, day1);
		createTimetable(tuesClasses, tues, day2);
		createTimetable(wedClasses, wed, day3);
		createTimetable(thurClasses, thurs, day4);
		createTimetable(friClasses, fri, day5);
		
		setDay(monClasses,"monday");
		setDay(tuesClasses,"tuesday");
		setDay(wedClasses,"wednesday");
		setDay(thurClasses,"thursday");
		setDay(friClasses,"friday");
		
		list.addAll(monClasses);
		list.addAll(tuesClasses);
		list.addAll(wedClasses);
		list.addAll(thurClasses);
		list.addAll(friClasses);
		
		return list;
		
	}
	
	public void setDay(ArrayList<SingleClass> list, String day)
	{
		for(int i=0; i<list.size();i++)
		{
			list.get(i).setDay(day);
		}
		
	}
	
	public static void createTimetable(ArrayList<SingleClass> list, Elements elements, Day day)
	{
		for(int i=0;i<elements.size();i=i+7)
		{
			SingleClass sc = new SingleClass();
			list.add(sc);
		}
		
		for(int i=0, j=0;i<elements.size();i=i+7,j++)
		{
			list.get(j).setStartTime(elements.get(i).text());
			list.get(j).setEndTime(elements.get(i+1).text());
			list.get(j).setWeeks(elements.get(i+2).text());
			list.get(j).setClassTitle(elements.get(i+3).text());
			list.get(j).setType(elements.get(i+4).text());
			list.get(j).setLecturer(elements.get(i+5).text());
			list.get(j).setRoomNo(elements.get(i+6).text());
		}
		
		
		day.setClasses(list);
	}
	
	public String getRssEntries() throws Exception 
	{
		List<String> xmlList = new ArrayList<String>();
		String xml = "";
		URL url = new URL("https://www.dkit.ie/rss/news");
		
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;

        while ((inputLine = reader.readLine()) != null) {
        	xml=xml+inputLine;
        }
        reader.close();
        return xml;
	}

	@Override
	public String getJson() throws Exception 
	{
		String jsonText = "";
		URL url = new URL("http://api.twitter.com/1/statuses/user_timeline.json?screen_name=dundalkit&include_rts=1");
		
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        String inputLine;

        while ((inputLine = reader.readLine()) != null) {
        	jsonText=jsonText+inputLine;
        }
        reader.close();
        return jsonText;
	}

}

