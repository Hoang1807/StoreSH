package Models;
public class CTHDDatHang {
    private String maHD,maSP;
    private int SL;
    private Double giaBan;

    public String getMaHD() {return maHD;}

    public void setMaHD(String maHD) {this.maHD = maHD;}
    public void setMaSP(String maSP) {this.maSP = maSP;}
    public void setSL(int SL) {this.SL = SL;}
    public void setGiaBan(Double giaBan) {this.giaBan = giaBan;}

    public String getMaSP() {return maSP;}
    public Double getTongTien(){return SL * giaBan;}
    public int getSL() {return SL;}
    public Double getGiaBan() {return giaBan;}
}
