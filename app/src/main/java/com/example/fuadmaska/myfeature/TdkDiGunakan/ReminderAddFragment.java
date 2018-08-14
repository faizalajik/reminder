package com.example.fuadmaska.myfeature.TdkDiGunakan;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.fuadmaska.myfeature.Fragment.ReminderListFragment;
import com.example.fuadmaska.myfeature.Model.DataReminder;
import com.example.fuadmaska.myfeature.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

public class ReminderAddFragment extends Fragment {
    Toolbar toolbar;
    EditText addedttot, addedtdate, addedttime, addedtnote;
    RadioButton setmonthly;
    Button saveaddrem;
    Spinner selins;
    private ArrayList<DataReminder> data;

    String[] jenisInsurance = {
            "Asuransi Jiwa",
            "Asuransi Kesehatan",
            "Asuransi Kendaraan",
            "Asuransi Rumah dan Property",
            "Asuransi Pendidikan",
            "Asuransi Bisnis",
            "Asuransi Umum",
            "Asuransi Kredit"};

    private int mYear, mMonth, mDay, mHour, mMinute;


    public ReminderAddFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_reminder_add, container, false);
        loaddata();
        addedttot = view.findViewById(R.id.edttotprem);
        addedttot.addTextChangedListener(new NumberTextWatcher(addedttot, "#,##.00"));
        addedtdate = view.findViewById(R.id.edtdateprem);
        addedtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int yy = calendar.get(Calendar.YEAR);
                int mm = calendar.get(Calendar.MONTH);
                int dd = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePicker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date = String.valueOf(year) + "-" + String.valueOf(monthOfYear)
                                + "-" + String.valueOf(dayOfMonth);
                        addedtdate.setText(date);

                    }
                }, yy, mm, dd);
                datePicker.show();
            }
        });
        addedttime = view.findViewById(R.id.edttimprem);
        addedttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);

                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        addedttime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        addedtnote = view.findViewById(R.id.edtnote);
        setmonthly = view.findViewById(R.id.setmonth);

        saveaddrem = view.findViewById(R.id.saveadd);
        saveaddrem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String insucateg = selins.getSelectedItem().toString();
                String jmltotprem = addedttot.getText().toString();
                String dateprem = addedtdate.getText().toString();
                String timeprem = addedttime.getText().toString();
                String noteprem = addedtnote.getText().toString();

                if (jmltotprem.isEmpty()) {
                    addedttot.setError("The Amount must be filled");
                    addedttot.requestFocus();
                } else if (dateprem.isEmpty()) {
                    addedtdate.setError("Date must be filled");
                    addedtdate.requestFocus();
                } else if (timeprem.isEmpty()) {
                    addedttime.setError("Time must be filled");
                    addedttime.requestFocus();
                } else if (noteprem.isEmpty()) {
                    addedtnote.setError("Note must be filled");
                    addedtnote.requestFocus();
                }else  {
                    ReminderListFragment rlf = new ReminderListFragment();
                    Bundle bkd = new Bundle();
                    bkd.putString("insucateg",insucateg);
                    bkd.putString("jmltotprem",jmltotprem);
                    bkd.putString("dateprem",dateprem);
                    bkd.putString("timeprem",timeprem);
                    bkd.putString("noteprem",noteprem);
                    data.add(new DataReminder(insucateg,jmltotprem,dateprem,timeprem,noteprem));
                    save();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.add_rem,rlf);
                    fragmentTransaction.commit();


                }






            }

        });
        selins = view.findViewById(R.id.selectinsu);

        final ArrayAdapter adapter = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, jenisInsurance);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selins.setAdapter(adapter);
//        selins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//                selins.setOnItemSelectedListener(this);
//                adapter.notifyDataSetChanged();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;

    }



    private class NumberTextWatcher implements TextWatcher {
        private final DecimalFormat df;
        private final DecimalFormat dfnd;
        private final EditText et;
        private boolean hasFractionalPart;
        private int trailingZeroCount;


        public NumberTextWatcher(EditText addedttot, String s) {
            df = new DecimalFormat(s);
            df.setDecimalSeparatorAlwaysShown(true);
            dfnd = new DecimalFormat("#,##.00");
            this.et = addedttot;
            hasFractionalPart = false;

        }


        @Override
        public void afterTextChanged(Editable editable) {
            et.removeTextChangedListener(this);

            if (editable != null && !editable.toString().isEmpty()) {
                try {
                    int inilen, endlen;
                    inilen = et.getText().length();
                    String v = editable.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "").replace("$", "");
                    Number n = df.parse(v);
                    int cp = et.getSelectionStart();
                    if (hasFractionalPart) {
                        StringBuilder trailingZeros = new StringBuilder();
                        while (trailingZeroCount-- > 0)
                            trailingZeros.append('0');
                        et.setText(df.format(n) + trailingZeros.toString());
                    } else {
                        et.setText(dfnd.format(n));
                    }
                    et.setText("Rp.".concat(et.getText().toString()));
                    endlen = et.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel < et.getText().length()) {
                        et.setSelection(sel);
                    } else if (trailingZeroCount > -1) {
                        et.setSelection(et.getText().length() - 3);
                    } else {
                        et.setSelection(et.getText().length());
                    }
                } catch (NumberFormatException | ParseException e) {
                    e.printStackTrace();


                }
            }
            et.addTextChangedListener(this);

        }

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }


        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            int index = charSequence.toString().indexOf(String.valueOf(df.getDecimalFormatSymbols().getDecimalSeparator()));
            trailingZeroCount = 0;
            if (index > -1) {
                for (index++; index < charSequence.length(); index++) {
                    if (charSequence.charAt(index) == '0')
                        trailingZeroCount++;
                    else {
                        trailingZeroCount = 0;
                    }
                }
                hasFractionalPart = true;
            } else {
                hasFractionalPart = false;
            }

        }


    }
    public void save (){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("datasave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString("datalist",json);
        editor.apply();

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


}