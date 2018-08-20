//package com.example.fuadmaska.myfeature.TdkDiGunakan;
//
//import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.example.fuadmaska.myfeature.AddReminder;
//import com.example.fuadmaska.myfeature.Model.DataReminder;
//import com.example.fuadmaska.myfeature.R;
//
//public class DetailRemainderActivity extends AppCompatActivity {
//
//    TextView tvKategori,tvTanggal,tvTotalPermi,tvWaktu,tvNote ;
//    Button btnEdit ;
//
//    int posisiEdit = 0 ;
//
//    DataReminder reminder ;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_detail_remainder);
//        initKomponen();
//        getDataDetail();
//        btnEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(DetailRemainderActivity.this, AddReminder.class) ;
//                Bundle bundle = new Bundle() ;
//                bundle.putSerializable("data",reminder);
//                bundle.putInt("posisi",posisiEdit);
//                intent.putExtras(bundle);
//                AddReminder.status = "update" ;
//                AddReminder.statusDetail = "update" ;
//                startActivityForResult(intent,100);
//            }
//        });
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 100 && resultCode == RESULT_OK) {
//            Bundle bundle = data.getExtras();
//            reminder = (DataReminder) bundle.getSerializable("perbaruiData");
//            setLayoutData();
//
//        }
//    }
//    private void initKomponen(){
//        tvKategori = findViewById(R.id.tv_detail_kategori) ;
//        tvTotalPermi = findViewById(R.id.tv_detail_premi) ;
//        tvTanggal = findViewById(R.id.tv_detail_tanggal) ;
//        tvWaktu = findViewById(R.id.tv_detail_waktu) ;
//        tvNote = findViewById(R.id.tv_detail_note) ;
//
//        btnEdit = findViewById(R.id.btn_edit);
//    }
//
//    private void getDataDetail(){
//        Bundle bundle = getIntent().getExtras();
//        reminder = (DataReminder) bundle.getSerializable("data");
//        setLayoutData();
//        posisiEdit = bundle.getInt("posisi") ;
//    }
//
//    public void setLayoutData(){
//        tvKategori.setText(reminder.getCategory());
//        tvTanggal.setText(reminder.getTanggal());
//        tvWaktu.setText(reminder.getWaktu());
//        tvTotalPermi.setText(reminder.getTotal());
//        tvNote.setText(reminder.getNote());
//    }
//}
