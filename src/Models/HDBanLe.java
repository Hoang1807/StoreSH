package Models;
public class HDBanLe {
    private String maHD, ngayLap, sdtKH, maGG, maNV;
    private boolean trangThai;
    private int diemSD;
    private double tienKhuyenMai;

    public boolean isTrangThai() {return trangThai;}
    public void setDiemSD(int diemSD) {this.diemSD = diemSD;}
    public void setMaNV(String maNV) {this.maNV = maNV;}
    public void setMaHD(String maHD) {this.maHD = maHD;}
    public void setNgayLap(String ngayLap) {this.ngayLap = ngayLap;}
    public void setSdtKH(String sdtKH) {this.sdtKH = sdtKH;}
    public void setMaGG(String maGG) {this.maGG = maGG;}
    public void setTienKhuyenMai(Double tienKhuyenMai) {this.tienKhuyenMai = tienKhuyenMai;}
    public void setTrangThai(boolean trangThai) {this.trangThai = trangThai;}
    
    public String getMaHD() {return maHD;}
    public int getDiemSD() {return diemSD;}
    public String getNgayLap() {return ngayLap;}
    public String getSdtKH() {return sdtKH;}
    public String getMaGG() {return maGG;}
    public String getMaNV() {return maNV;}
    public Double getTienKhuyenMai() {return tienKhuyenMai;}
}
