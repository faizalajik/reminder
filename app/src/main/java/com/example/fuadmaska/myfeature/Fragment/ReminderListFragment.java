package com.example.fuadmaska.myfeature.Fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fuadmaska.myfeature.AddReminder;
import com.example.fuadmaska.myfeature.Model.AdapterReminder;
import com.example.fuadmaska.myfeature.Model.DataReminder;
import com.example.fuadmaska.myfeature.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReminderListFragment extends CustomRecyclerFragment  {
RecyclerView recycler;

    private RecyclerView rv;
    private ArrayList<DataReminder> data;
    TabLayout tabbawah;

    View view ;
    public ReminderListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_reminder_list, container, false);

        loaddata();
        rv = (RecyclerView) view.findViewById(R.id.recyclerlistadd);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddReminder.class);
                getActivity().startActivity(intent);
//                ReminderAddFragment rlf = new ReminderAddFragment();
//                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.replace(R.id.reminder_list,rlf);
//                fragmentTransaction.commit();
//                tabbawah = view.findViewById(R.id.Tabbawah);
//                tabbawah.setVisibility(View.GONE);

            }
        });
//        fab.getP
//        showNotification();
        return view;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(new AdapterReminder(this.getActivity(),data));
    }
    private void loaddata() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("datasave", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("datalist", null);
        Type type = new TypeToken<ArrayList<DataReminder>>() {}.getType();
        data = gson.fromJson(json, type);

        if (data == null) {
            data = new ArrayList<>();
        }
    }


    public static Fragment newInstance() {
        return new ReminderListFragment();
    }
}
