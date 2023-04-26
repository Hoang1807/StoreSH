package Models;
public class CTHDBanLe {
    private String maHD, maSP;
    private Double giaBan;
    private int soLuong;

    public void setMaSP(String maSP) {this.maSP = maSP;}
    public void setMaHD(String maHD) {this.maHD = maHD;}
    public void setGiaBan(Double giaBan) {this.giaBan = giaBan;}
    public void setSoLuong(int soLuong) {this.soLuong = soLuong;}

    public String getMaHD() {return maHD;}
    public Double getTongTien() {return soLuong * giaBan;}
    public String getMaSP() {return maSP;}
    public Double getGiaBan() {return giaBan;}
    public int getSoLuong() {return soLuong;}
}
