package com.example.hackyeah2019.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.hackyeah2019.MainActivity;
import com.example.hackyeah2019.R;

public class DashboardFragment extends Fragment {

    private DashboardViewModel dashboardViewModel;



    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        while(MainActivity.stringList.size() >= 100) {
            MainActivity.stringList.remove(0) ;
        }
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final ListView listView = root.findViewById(R.id.resultListView);
        //dashboardViewModel.getText().observe(this, s -> textView.setText(s));
        ArrayAdapter adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,
                MainActivity.stringList);
        listView.setAdapter(adapter);
        return root;
    }

}