package com.eurekakids.euraka1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.eurekakids.com.eurekakids.db.DatabaseHandler;
import com.eurekakids.db.datamodel.Assessment;

/**
 * Created by Kirubanand on 13/09/2015.
 */

public class math_fragment extends Fragment{
	public static math_fragment newInstance(int student_id) {
		math_fragment f = new math_fragment();
		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("id", student_id);
		f.setArguments(args);
		return f;
	}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.math_fragment,container,false);

		ViewGroup viewGroup = (ViewGroup) v;
		ViewGroup linear = (ViewGroup) viewGroup.getChildAt(0);
		int view_count = linear.getChildCount();

		Bundle args = getArguments();
		int child_id = args.getInt("id", 0);
		int skill = 13;
		for (int i = 0; i < view_count; i++) {
			View view = linear.getChildAt(i);
			DatabaseHandler db = new DatabaseHandler(getActivity());
			Assessment ass = db.getAssessmentById(child_id, skill);
			int t = ass.getIsCompleted();
			if(t==0){
				((Switch) view).setChecked(false);
			}else{
				((Switch) view).setChecked(true);
			}
			skill++;
		}

		return v;
    }
}
