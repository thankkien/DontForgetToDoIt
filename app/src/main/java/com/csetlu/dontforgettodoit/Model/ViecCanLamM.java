package com.csetlu.dontforgettodoit.Model;

public class ViecCanLamM {
    private int maCV;
    private int trangThai;
    private String congViec;
    private String ngay;
    private String thoiGian;

    public ViecCanLamM() {
    }

    public int layMaCV() {
        return maCV;
    }

    public void ganMaCV(int maCV) {
        this.maCV = maCV;
    }

    public int layTT() {
        return trangThai;
    }

    public void ganTT(int trangThai) {
        this.trangThai = trangThai;
    }

    public String layCV() {
        return congViec;
    }

    public void ganCV(String congViec) {
        this.congViec = congViec;
    }

    public String layNgay() {
        return ngay;
    }

    public void ganNgay(String ngay) {
        this.ngay = ngay;
    }

    public String layThoiGian() {
        return thoiGian;
    }

    public void ganThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

}
