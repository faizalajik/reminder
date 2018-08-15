package com.example.fuadmaska.myfeature.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.fuadmaska.myfeature.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomRecyclerFragment extends Fragment {


    public CustomRecyclerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_custom_recycler, container, false);
        Bundle bundle = getArguments();
        String categoryInsurance = bundle.getString("insucateg");
        String jumlahTotal = bundle.getString("jmltotprem");
        String tanggalBayar = bundle.getString("dateprem");
        String waktuBayar = bundle.getString("timeprem");
        String catatanBayar = bundle.getString("noteprem");

        TextView caIns = view.findViewById(R.id.listinsu);
        TextView daIns = view.findViewById(R.id.listdate);
        caIns.setText(categoryInsurance);
        daIns.setText(tanggalBayar);
        return view;
    }

}
