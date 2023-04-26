/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import DAO.GGSanPhamDAO;
public class SanPham {
    private String maSP, tenSP, maKe, ngayHH, ngaySX, donVi, qrCode, maNCC, maLoai;
    private Double giaBan, giaNhap;
    private int slTrenKe, SLTonKho;
    private boolean trangThai;
    private byte[] Hinh;

    public SanPham() {}
    public boolean isTrangThai() {return trangThai;}

    public void setTrangThai(boolean trangThai) {this.trangThai = trangThai;}
    public void setHinh(byte[] Hinh) {this.Hinh = Hinh;}
    public void setGiaNhap(Double giaNhap) {this.giaNhap = giaNhap;}
    public void setMaNCC(String maNCC) {this.maNCC = maNCC;}
    public void setMaLoai(String maLoai) {this.maLoai = maLoai;}
    public void setMaSP(String maSP) {this.maSP = maSP;}
    public void setMaKe(String maKe) {this.maKe = maKe;}
    public void setTenSP(String tenSP) {this.tenSP = tenSP;}
    public void setGiaBan(Double giaBan) {this.giaBan = giaBan;}
    public void setSlTrenKe(int slTrenKe) {this.slTrenKe = slTrenKe;}
    public void setNgaySX(String ngaySX) {this.ngaySX = ngaySX;}
    public void setNgayHH(String ngayHH) {this.ngayHH = ngayHH;}
    public void setDonVi(String donVi) {this.donVi = donVi;}
    public void setSLTonKho(int SLTonKho) {this.SLTonKho = SLTonKho;}
    public void setQrCode(String qrCode) {this.qrCode = qrCode;}   
    
    public byte[] getHinh() {return Hinh;}
    public Double getGiaNhap() {return giaNhap;}
    public String getMaNCC() {return maNCC;}
    public String getMaLoai() {return maLoai;}
    public String getMaSP() {return maSP;}
    public String getTenSP() {return tenSP;}
    public String getMaKe() {return maKe;}
    public Double getGiaBan() {return giaBan;}
    public int getSlTrenKe() {return slTrenKe;}
    public String getNgaySX() {return ngaySX;}
    public String getNgayHH() {return ngayHH;}
    public String getDonVi() {return donVi;}
    public int getSLTonKho() {return SLTonKho;}
    public String getQrCode() {return qrCode;}  
    
    @Override
    public String toString() {return "( " + maSP + " )" + tenSP;}

    public Double getGiaGiam() {
        GGSanPhamDAO ggSP = new GGSanPhamDAO();
        GGSanPham gg = ggSP.selectByCheckHieuLuc(this.maSP);
        if (gg == null) {
            return this.giaBan;
        } else {
            return this.giaBan - gg.getGiaGiam();
        }
    }
}
