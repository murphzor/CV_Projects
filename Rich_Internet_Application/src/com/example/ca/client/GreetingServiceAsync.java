package com.example.ca.client;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface GreetingServiceAsync {
	void greetServer(String input, AsyncCallback<String> callback)
			throws IllegalArgumentException;

	void getTimetables(AsyncCallback<ArrayList<TimetableClass>> asyncCallback);

	void displayTimetable(String value,
			AsyncCallback<ArrayList<SingleClass>> asyncCallback);

	void getRssEntries(AsyncCallback<String> asyncCallback);

	void getJson(AsyncCallback<String> callback);
}
