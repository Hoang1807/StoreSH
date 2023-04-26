package Models;
public class GGSanPham {
    private String maSP,ngayBD,ngayKT,maNV;
    private Double giaGiam;

    public String getMaSP() {return maSP;}
    public String getNgayBD() {return ngayBD;}
    public String getNgayKT() {return ngayKT;}
    public String getMaNV() {return maNV;}
    public Double getGiaGiam() {return giaGiam;}

    public void setMaSP(String maSP) {this.maSP = maSP;}
    public void setGiaGiam(Double giaGiam) {this.giaGiam = giaGiam;}
    public void setNgayBD(String ngayBD) {this.ngayBD = ngayBD;}
    public void setNgayKT(String ngayKT) {this.ngayKT = ngayKT;}
    public void setMaNV(String maNV) {this.maNV = maNV;}
}
