package com.example.fuadmaska.myfeature;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.fuadmaska.myfeature.Fragment.ReminderListFragment;
import com.example.fuadmaska.myfeature.Model.DataReminder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class AddReminder extends AppCompatActivity {
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TimePickerDialog timePickerDialog;
    private Spinner spinnerAdd;
    private Button btnUpdate;
    private EditText Editdate,Edittotal,Edittime,Editnote;
    private String date, time, note, total;
    private ArrayList<DataReminder> data;
    private String[] jenisAsuransi = {
            "Health Insurance",
            "Car Insurance",
            "Pension Insurance",
            "Life Insurance"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);
        loaddata();
        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_add);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        spinnerAdd=(Spinner)findViewById(R.id.selectinsu);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, jenisAsuransi);
        spinnerAdd.setAdapter(adapter);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);

        Editdate = (EditText) findViewById(R.id.edit_date);
        Edittotal = (EditText) findViewById(R.id.edit_total);
        Edittime = (EditText)findViewById(R.id.edit_time);
        btnUpdate = (Button)findViewById(R.id.button_update);
        Editnote = (EditText) findViewById( R.id.edit_note);
        Edittotal.addTextChangedListener(new NumberTextWatcher(Edittotal, "#,##.00"));
        Editdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
        Edittime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDialog();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = spinnerAdd.getSelectedItem().toString();
                String total = Edittotal.getText().toString();
                String tanggal = Editdate.getText().toString();
                String waktu = Edittime.getText().toString();
                String note = Editnote.getText().toString();

                    if (total.isEmpty()) {
                        Edittotal.setError("The Amount must be filled");
                        Edittotal.requestFocus();
                    } else if (tanggal.isEmpty()) {
                        Editdate.setError("Date must be filled");
                        Editdate.requestFocus();
                    } else if (waktu.isEmpty()) {
                        Edittime.setError("Time must be filled");
                        Edittime.requestFocus();
                    } else if (note.isEmpty()) {
                        Editnote.setError("Note must be filled");
                        Editnote.requestFocus();
                    }else {
                        ReminderListFragment rlf = new ReminderListFragment();
                        data.add(new DataReminder(category, total, tanggal, waktu, note));
                        save();
                        Toast.makeText(getApplicationContext(), "Data Disimpan di Internal", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddReminder.this, activityList.class);
                        startActivity(intent);

//                        FragmentManager fragmentManager = getSupportFragmentManager();
//                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                        fragmentTransaction.replace(R.id.container_remin,rlf);
//                        fragmentTransaction.commit();

                    }
            }
        });

    }

    private void showDateDialog(){
        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                Editdate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }
    private void showTimeDialog() {

        /**
         * Calendar untuk mendapatkan waktu saat ini
         */
        Calendar calendar = Calendar.getInstance();

        /**
         * Initialize TimePicker Dialog
         */
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                /**
                 * Method ini dipanggil saat kita selesai memilih waktu di DatePicker
                 */
                Edittime.setText(+hourOfDay+":"+minute);
            }
        },
                /**
                 * Tampilkan jam saat ini ketika TimePicker pertama kali dibuka
                 */
                calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE),

                /**
                 * Cek apakah format waktu menggunakan 24-hour format
                 */
                DateFormat.is24HourFormat(this));

        timePickerDialog.show();
    }
    public void save (){
        SharedPreferences sharedPreferences = getSharedPreferences("datasave", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        editor.putString("datalist",json);
        editor.apply();

    }
    private void loaddata() {
        SharedPreferences sharedPreferences = getSharedPreferences("datasave", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("datalist", null);
        Type type = new TypeToken<ArrayList<DataReminder>>() {}.getType();
        data = gson.fromJson(json, type);

        if (data == null) {
            data = new ArrayList<>();
        }
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
}
