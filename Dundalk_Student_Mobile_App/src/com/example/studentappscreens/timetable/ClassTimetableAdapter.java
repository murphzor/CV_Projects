package com.example.studentappscreens.timetable;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.studentappscreens.R;

public class ClassTimetableAdapter extends BaseAdapter
{
		private LayoutInflater mInflater = null;
		private ArrayList<SingleClass> timetableList;

		private final class ViewHolder {
			TextView startTextView;
			TextView endTextView;
			TextView titleTextView;
			TextView typeTextView;
			TextView lecturerTextView;
			TextView roomTextView;
		}

		private ViewHolder mHolder = null;

		public ClassTimetableAdapter(Context context) 
		{
			mContext = context;
			mInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		@Override
		public int getCount() {
		return timetableList.size();
		}

		@Override
		public Object getItem(int position) {
		return position;
		}
		@Override
		public long getItemId(int position) {
		return position;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null) {
		mHolder = new ViewHolder();
		convertView = mInflater.inflate(R.layout.list_row, null);           
		convertView.setTag(mHolder);
		} else {
		mHolder = (ViewHolder)convertView.getTag(); 
		}

		mHolder.startTextView (TextView)convertView.findViewById(R.id.starttime);
		mHolder.startTextView.setText(timetableList.get(position).getStartTime());
		
		mHolder.endTextView = (TextView)convertView.findViewById(R.id.endtime);
		mHolder.endTextView.setText(timetableList.get(position).getEndTime());
		
		mHolder.titleTextView (TextView)convertView.findViewById(R.id.classname);
		mHolder.titleTextView.setText(timetableList.get(position).getClassTitle());
				
		mHolder.typeTextView (TextView)convertView.findViewById(R.id.type);
		mHolder.typeTextView.setText(timetableList.get(position).getType());
		
		mHolder.lecturerTextView = (TextView)convertView.findViewById(R.id.lecturer);
		mHolder.lecturerTextView.setText(timetableList.get(position).getLecturer());
		
		mHolder.roomTextView = (TextView)convertView.findViewById(R.id.room);
		mHolder.roomTextView.setText(timetableList.get(position).getRoomNo());

		return convertView;
		
	}
}
