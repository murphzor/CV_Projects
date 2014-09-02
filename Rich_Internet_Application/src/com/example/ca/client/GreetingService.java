package com.example.ca.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingService extends RemoteService {
	String greetServer(String name) throws IllegalArgumentException;
	ArrayList<TimetableClass> getTimetables() throws Exception;
	ArrayList<SingleClass> displayTimetable(String value) throws Exception;
	String getRssEntries() throws Exception;
	String getJson() throws Exception;
}
