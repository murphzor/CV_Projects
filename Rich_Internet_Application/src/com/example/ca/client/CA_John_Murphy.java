package com.example.ca.client;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.xml.client.DOMException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.NodeList;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CA_John_Murphy implements EntryPoint {

	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";


	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	final Label classListLabel = new Label("List of available DKIT timetables");
	final Label timetableLabel = new Label("");
	final Label jsonLabel = new Label("Json feed results");
	final Label xmlLabel = new Label("DKIT News Feed");

	
    ArrayList<TimetableClass> listOfTimetables = new ArrayList<TimetableClass>();
	ArrayList<SingleClass> monClasses = new ArrayList<SingleClass>();
	ArrayList<SingleClass> tuesClasses = new ArrayList<SingleClass>();
	ArrayList<SingleClass> wedClasses = new ArrayList<SingleClass>();
	ArrayList<SingleClass> thurClasses = new ArrayList<SingleClass>();
	ArrayList<SingleClass> friClasses = new ArrayList<SingleClass>();
	ArrayList<NewsEntry> newsEntries = new ArrayList<NewsEntry>();
	
    CellTable<SingleClass> table;
    TextColumn<SingleClass> startColumn;
    TextColumn<SingleClass> endColumn;
    TextColumn<SingleClass> lecturerColumn;
    TextColumn<SingleClass> titleColumn;
    TextColumn<SingleClass> roomColumn;
    final HTML delHtml = new HTML();
	String filter = "";
    
    
	public void onModuleLoad() {
	   // final VerticalPanel panel1 = new VerticalPanel();
		final ListBox classListBox = new ListBox();
		final ListBox filterListBox = new ListBox();
		filterListBox.setVisibleItemCount(0);
		filterListBox.addItem("");
		filterListBox.addItem("ICOM ");
		filterListBox.addItem("NNUR ");
		filterListBox.addItem("BHUM ");
		filterListBox.addItem("EBSV ");
		filterListBox.addItem("NGEN ");
		filterListBox.addItem("BHOS ");
		filterListBox.addItem("EIEE ");
		filterListBox.addItem("BBUS ");
		filterListBox.addItem("BFMP ");
		filterListBox.addItem("EETR ");
		filterListBox.addItem("EEME ");
		filterListBox.addItem("IMCM ");
		


		timetableLabel.setVisible(false);
		
	    RootPanel.get("classListDiv").add(classListBox);
	    RootPanel.get("classFilterDiv").add(filterListBox);
		RootPanel.get("jsonLabelDiv").add(jsonLabel);
		RootPanel.get("xmlLabelDiv").add(xmlLabel);

		greetingService.getRssEntries(new AsyncCallback<String>() 
		{

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) 
			{
				parseMessage(result);
			}
			
		});

		greetingService.getJson(new AsyncCallback<String>() 
		{

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(String result) 
			{
				parseJson(result);
			}
			
		});
		
		greetingService.getTimetables(new AsyncCallback<ArrayList<TimetableClass>>() {
			public void onFailure(Throwable caught) {

			}

			@Override
			public void onSuccess(ArrayList<TimetableClass> result) {
				listOfTimetables = result;
				ArrayList<String> sortedList = new ArrayList<String>();

			    for(int i=0;i<listOfTimetables.size();i++)
			    {
					sortedList.add(listOfTimetables.get(i).getTitle());
			    }
			    Collections.sort(sortedList);
			    for(int i=0;i<sortedList.size();i++)
			    {
			    	classListBox.addItem(sortedList.get(i));
			    }	

			    classListBox.setVisibleItemCount(15);
			}
		});
		
        filterListBox.addChangeHandler(new ChangeHandler()
        {
			@Override
			public void onChange(ChangeEvent event) 
			{	
				filter = filterListBox.getItemText(filterListBox.getSelectedIndex());
				classListBox.clear();
				System.out.println(filterListBox.getSelectedIndex());
				ArrayList<String> sortedList = new ArrayList<String>();
				if(filterListBox.getSelectedIndex()==0)
				{
				    for(int i=0;i<listOfTimetables.size();i++)
				    {
						sortedList.add(listOfTimetables.get(i).getTitle());
				    }
				    System.out.println("0");
				    Collections.sort(sortedList);
				    for(int i=0;i<sortedList.size();i++)
				    {
				    	classListBox.addItem(sortedList.get(i));
				    }
				}
				else
				{
				    for(int i=0;i<listOfTimetables.size();i++)
				    {
				    	System.out.println("filter: " + filter);
				    	System.out.println("list: "+ listOfTimetables.get(i).getIdentifier());
				    	if(filter.equals(listOfTimetables.get(i).getIdentifier()))
				    	{
				    		sortedList.add(listOfTimetables.get(i).getTitle());
				    		System.out.println("eq");
				    	}
				    }
				    Collections.sort(sortedList);
				    for(int i=0;i<sortedList.size();i++)
				    {
				    	classListBox.addItem(sortedList.get(i));
				    }
				}
			    classListBox.setVisibleItemCount(15);
			}
			}        	
        );
        
        classListBox.addChangeHandler(new ChangeHandler()
        {
                @Override
                public void onChange(ChangeEvent event)
                {
                        for(int i=0;i<listOfTimetables.size();i++)
                        {
                        	//remove the whitespace from the strings being checked, for some reason a number of the arraylist entries have extra whitespace 
                        	//which means the timetable will never match
                        	if(classListBox.getItemText(classListBox.getSelectedIndex()).replace(" ", "").contains(listOfTimetables.get(i).getTitle().replace(" ", "")))
                        	{
                        		
                        		System.out.println(listOfTimetables.get(i).getTitle());
                        		
                        		String value = listOfTimetables.get(i).getValue();
                		        value =changeURL(value);

                				greetingService.displayTimetable(value, new AsyncCallback<ArrayList<SingleClass>>() 
                				{

        							public void onFailure(Throwable caught) 
        							{
                    					System.out.println("fail");
        								// Show the RPC error message to the user
        							}

        							@Override
        							public void onSuccess(ArrayList<SingleClass> result) 
        							{
        								if(!result.isEmpty())
        								{
        									System.out.println("result is not empty");
        								
        									for(int i =0; i<result.size();i++)
        									{
        										if(result.get(i).getDay().equals("monday"))
        										{
        											monClasses.add(result.get(i));
        										}
        										else if(result.get(i).getDay().equals("tuesday"))
        										{
        											tuesClasses.add(result.get(i));
        										}
        										else if(result.get(i).getDay().equals("wednesday"))
        										{
        											wedClasses.add(result.get(i));
        										}
        										else if(result.get(i).getDay().equals("thursday"))
        										{
        											thurClasses.add(result.get(i));
        										}       										
        										else if(result.get(i).getDay().equals("friday"))
        										{
        											friClasses.add(result.get(i));
        										} 
        										
        									}
        									
        									if(table!=null)
        									{
	        									table.setRowCount(0, false);
	        									RootPanel.get().remove(table);
	        									RootPanel.get("timetableDisplayDiv").clear();
        									}
        									
    										table = new CellTable<SingleClass>();

    								        TextColumn<SingleClass> dayColumn = new TextColumn<SingleClass>() 
    								        {
    											@Override
    											public String getValue(SingleClass sc) 
    											{
    												return sc.getDay();
    											}
    											
    								          };
    										
    								        TextColumn<SingleClass> titleColumn = new TextColumn<SingleClass>() 
    								        {
    											@Override
    											public String getValue(SingleClass sc) 
    											{
    												return sc.getClassTitle();
    											}
    											
    								          };
      								        TextColumn<SingleClass> startColumn = new TextColumn<SingleClass>() 
    								        {
    											@Override
    											public String getValue(SingleClass sc) 
    											{
    												return sc.getStartTime();
    											}
    											
    								          };
      								        TextColumn<SingleClass> endColumn = new TextColumn<SingleClass>() 
    								        {
    											@Override
    											public String getValue(SingleClass sc) 
    											{
    												return sc.getEndTime();
    											}    						   											   											
        								      };    
        								   TextColumn<SingleClass> lecturerColumn = new TextColumn<SingleClass>() 
    								        {
    											@Override
    											public String getValue(SingleClass sc) 
    											{
    												return sc.getLecturer();
    											}
    											
    								          };  

           								   TextColumn<SingleClass> roomColumn = new TextColumn<SingleClass>() 
       								        {
       											@Override
       											public String getValue(SingleClass sc) 
       											{
       												return sc.getRoomNo();
       											}
       											
       								          }; 
       								          
    								          ListDataProvider<SingleClass> dataProvider = new ListDataProvider<SingleClass>();
    								          dataProvider.addDataDisplay(table);
    								          List<SingleClass> list = dataProvider.getList();
    								          for (SingleClass sc : monClasses) {
    								            list.add(sc);
    								          }
    								          for (SingleClass sc : tuesClasses) {
      								            list.add(sc);
      								          }
    								          for (SingleClass sc : wedClasses) {
        								            list.add(sc);
        								          }
    								          for (SingleClass sc : thurClasses) {
        								            list.add(sc);
        								          }
    								          for (SingleClass sc : friClasses) {
        								            list.add(sc);
        								          }
    								          table.addColumn(dayColumn, "Day");
    								          table.addColumn(titleColumn, "Title");
    								          table.addColumn(startColumn, "Start");
    								          table.addColumn(endColumn, "End");
    								          table.addColumn(lecturerColumn, "lecturer");
    								          table.addColumn(roomColumn, "Room");
    								          table.setVisibleRange(0, 30);
    								          RootPanel.get("timetableDisplayDiv").add(table);       									
        								}
        								result.clear();
        								monClasses.clear();
        								tuesClasses.clear();
        								wedClasses.clear();
        								thurClasses.clear();
        								friClasses.clear();
        							}
        						});
        			}
                        	
                        };
                }
        });	

	}

		
//used for generating a section of the timetable URL
  public static String changeURL(String s)
  {
		String g="";
	  	for (int i=0; i<s.length(); i++) 
	  	{
	  		  String c = s.substring(i,i+1);
	
	  		  if(c.equals("_"))
	  		  {
	  			  g=g+"5F";
	  		  }
	  		  else if(c.equals("%"))
	  		  {
	  			  g=g+"%25";
	  		  }
	  		  else
	  		  {
	  			  g=g+c;
	  		  }
	  	}
	  	return g;
  }
  
	private void parseMessage(String xml) 
	{
	  try {
		Document messageDom = XMLParser.parse(xml);
	
		XMLParser.removeWhitespace(messageDom);
		Element root = messageDom.getDocumentElement();
	
		NodeList URLs = (NodeList) root.getChildNodes();
		Element channel = (Element)URLs.item(0);
		
		NodeList items = (NodeList) channel.getChildNodes();
		ArrayList<Element> elements = new ArrayList<Element>();
	
		String html = "<ol>";
		System.out.println("items : " + items.getLength());
		for(int i = 0; i < items.getLength()-4; i++)
		{	
			
			String title = messageDom.getElementsByTagName("title").item(i).getFirstChild().getNodeValue();
		    String link = messageDom.getElementsByTagName("link").item(i).getFirstChild().getNodeValue();

			NewsEntry n = new NewsEntry(title, link);
			newsEntries.add(n);
			
		}
		for(int i=0;i<newsEntries.size();i++)
		{
		html += "<li><a href=\"" + newsEntries.get(i).getLink() + "\">" + newsEntries.get(i).getTitle()+ "</a></li>";
		}
		html += "</ol>";
		delHtml.setHTML(html);
		RootPanel.get("xmlDiv").add(delHtml);
		
	  } 
	  catch (DOMException e) 
	  {
		   Window.alert("Could not parse XML document.");
	  }
	}
	
	private void parseJson(String json)
	{
		JSONArray jsonArray=(JSONArray) JSONParser.parseStrict(json);;
		
		for(int i=0;i<10;i++)
		{
			JSONObject entry = (JSONObject) jsonArray.get(i);
			JSONString created_at = (JSONString)entry.get("created_at");
            JSONString text = (JSONString)entry.get("text");
            Label l1 = new Label();
            Label l2 = new Label();
            l1.setText(created_at.stringValue());
            l2.setText(text.stringValue());

    		RootPanel.get("jsonDiv").add(l1);
    		RootPanel.get("jsonDiv").add(l2);
        }

		
	}

}

