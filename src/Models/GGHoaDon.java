package Models;
public class GGHoaDon {
    private String maGG,ngayBD,ngayKT,maNV;
    private Double mucGG,hoaDonAD;
    private boolean hinhThuc;
    private int soLuong;
    
    public GGHoaDon() {}
    public GGHoaDon(String maGG, int soLuong, String ngayBD, String ngayKT, Double mucGG, boolean hinhThuc, Double hoaDonAD, String maNV) {
        this.maGG = maGG;
        this.soLuong = soLuong;
        this.ngayBD = ngayBD;
        this.ngayKT = ngayKT;
        this.mucGG = mucGG;
        this.hinhThuc = hinhThuc;
        this.hoaDonAD = hoaDonAD;
        this.maNV = maNV;
    }
    
    public boolean isHinhThuc() {return hinhThuc;}
    public String getMaGG() {return maGG;}
    public Integer getSoLuong() {return soLuong;}
    public String getNgayBD() {return ngayBD;}
    public String getNgayKT() {return ngayKT;}
    public Double getMucGG() {return mucGG;}
    public Double getHoaDonAD() {return hoaDonAD;}
    public String getMaNV() {return maNV;}

    public void setMaGG(String maGG) {this.maGG = maGG;}
    public void setMaNV(String maNV) {this.maNV = maNV;} 
    public void setSoLuong(Integer soLuong) {this.soLuong = soLuong;}
    public void setNgayBD(String ngayBD) {this.ngayBD = ngayBD;}
    public void setNgayKT(String ngayKT) {this.ngayKT = ngayKT;}
    public void setMucGG(Double mucGG) {this.mucGG = mucGG;}
    public void setHinhThuc(boolean hinhThuc) {this.hinhThuc = hinhThuc;}
    public void setHoaDonAD(Double hoaDonAD) {this.hoaDonAD = hoaDonAD;}
}
