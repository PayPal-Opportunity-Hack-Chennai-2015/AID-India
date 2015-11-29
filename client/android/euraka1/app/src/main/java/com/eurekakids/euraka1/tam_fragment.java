package com.eurekakids.euraka1;

/**
 * Created by Kirubanand on 12/09/2015.
 */
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.eurekakids.com.eurekakids.db.DatabaseHandler;
import com.eurekakids.db.datamodel.Assessment;
import com.eurekakids.db.datamodel.Student;

/**
 * Created by Admin on 04-06-2015.
 */
public class tam_fragment extends Fragment {

	private  Student student;

	public static tam_fragment newInstance(int student_id) {
		tam_fragment f = new tam_fragment();
		// Supply index input as an argument.
		Bundle args = new Bundle();
		args.putInt("id", student_id);
		f.setArguments(args);
		return f;
	}


    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tam_fragment,container,false);
		ViewGroup viewGroup = (ViewGroup) v;
		ViewGroup linear = (ViewGroup) viewGroup.getChildAt(0);
		int view_count = linear.getChildCount();

		Bundle args = getArguments();
		int child_id = args.getInt("id", 0);
		int skill = 1;
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