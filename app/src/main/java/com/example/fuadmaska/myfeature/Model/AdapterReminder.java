package com.example.fuadmaska.myfeature.Model;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.fuadmaska.myfeature.AddReminder;
import com.example.fuadmaska.myfeature.AlarmReceiver;
import com.example.fuadmaska.myfeature.MainActivity;
import com.example.fuadmaska.myfeature.PopUpDetailReminder;
import com.example.fuadmaska.myfeature.R;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class AdapterReminder extends RecyclerView.Adapter<AdapterReminder.ViewHolder> {

    private ArrayList<DataReminder> dataList;
    private Context context;

    final static int RQS_1 = 1;

    public AdapterReminder(Context context) {
        this.context = context;

    }

    public AdapterReminder(Context context, ArrayList<DataReminder> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public AdapterReminder.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v;
        v = layoutInflater.inflate(R.layout.fragment_custom_recycler, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull AdapterReminder.ViewHolder holder, final int position) {
        holder.tvCategory.setText(dataList.get(position).getCategory());
        holder.tvTanggal.setText(dataList.get(position).getTanggal());
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Are you sure want to delete reminder?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dataList.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, dataList.size());
                                save();
                            }
                        }).setNegativeButton("No", null).show();

            }
        });
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddReminder.class);
                kirimData(position, intent);
                AddReminder.status = "update";
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // pakai pop up
//                showNotification();
                Intent intent = new Intent(context, PopUpDetailReminder.class);
                kirimData(position, intent);
                Dialog dialog = new PopUpDetailReminder(context, intent);
                dialog.show();


            }
        });

        //setAlarm(dataList.get(position).getTanggal(), dataList.get(position).getWaktu());


    }


    private void setAlarm(String tanggal, String waktu) {

        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String string = tanggal + " " + waktu ;


        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = format.parse(string);
            System.out.println(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        calendar.setTime(date);
        System.out.println(calendar.getTimeInMillis());

        //
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, RQS_1, intent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTanggal, tvCategory, del;
        Button tvEdit;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            tvEdit = itemView.findViewById(R.id.listedit);
            tvCategory = (TextView) itemView.findViewById(R.id.listinsu);
            tvTanggal = (TextView) itemView.findViewById(R.id.listdate);
            del = (TextView) itemView.findViewById(R.id.listdelete);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }

    public void save() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("datasave", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataList);
        editor.putString("datalist", json).commit();
        editor.apply();
    }

    private void kirimData(int position, Intent intent) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", dataList.get(position));
        bundle.putInt("posisi", position);
        intent.putExtras(bundle);
    }


}
