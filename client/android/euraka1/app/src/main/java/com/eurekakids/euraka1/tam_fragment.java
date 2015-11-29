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

import com.eurekakids.db.datamodel.Assessment;

/**
 * Created by Admin on 04-06-2015.
 */
public class tam_fragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,@Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tam_fragment,container,false);


        return v;
    }
}