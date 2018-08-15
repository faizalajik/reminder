package com.example.fuadmaska.myfeature.Model;

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

import com.example.fuadmaska.myfeature.AddReminder;
import com.example.fuadmaska.myfeature.DetailRemainderActivity;
import com.example.fuadmaska.myfeature.Fragment.ReminderListFragment;
import com.example.fuadmaska.myfeature.R;
import com.example.fuadmaska.myfeature.TdkDiGunakan.ReminderAddFragment;
import com.google.gson.Gson;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class AdapterReminder extends RecyclerView.Adapter<AdapterReminder.ViewHolder>{

    private ArrayList<DataReminder> dataList;
    private Context context;


    public AdapterReminder(Context context) {
        this.context = context;

    }
    public AdapterReminder(Context context,ArrayList<DataReminder> dataList){
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
    public void onBindViewHolder(@NonNull AdapterReminder.ViewHolder holder,final int position) {
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
                                notifyItemRangeChanged(position,dataList.size());
                                save();
                            }
                        }).setNegativeButton("No",null).show();

            }
        });
        holder.tvEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AddReminder.class) ;
                kirimData(position,intent);
                AddReminder.status = "update" ;
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailRemainderActivity.class) ;
                kirimData(position,intent);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTanggal,tvCategory, del;
        Button tvEdit;
        CardView cardView;
        public ViewHolder(View itemView) {
            super(itemView);
            tvEdit = itemView.findViewById(R.id.listedit) ;
            tvCategory = (TextView)itemView.findViewById(R.id.listinsu);
            tvTanggal = (TextView)itemView.findViewById(R.id.listdate);
            del = (TextView) itemView.findViewById(R.id.listdelete);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
    public void save () {
        SharedPreferences sharedPreferences = context.getSharedPreferences("datasave", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(dataList);
        editor.putString("datalist", json).commit();
        editor.apply();
    }

    private void kirimData(int position, Intent intent){
        Bundle bundle = new Bundle() ;
        bundle.putSerializable("data",dataList.get(position));
        bundle.putInt("posisi",position);
        intent.putExtras(bundle);
    }
}
