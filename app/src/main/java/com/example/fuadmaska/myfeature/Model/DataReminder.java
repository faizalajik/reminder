package com.example.fuadmaska.myfeature.Model;

public class DataReminder {
    String category, total, tanggal, waktu, note;

    public DataReminder() {

    }

    public DataReminder(String category, String total, String tanggal, String waktu, String note) {
        this.category = category;
        this.total = total;
        this.tanggal = tanggal;
        this.waktu = waktu;
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
