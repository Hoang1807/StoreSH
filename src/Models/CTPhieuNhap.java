package Models;
public class CTPhieuNhap {
    private String maPN, maSP, tenSP, ngaySX, ngayHH;
    private Integer sl;
    private Double giaNhap;

    public void setMaPN(String maPN) {this.maPN = maPN;}
    public void setMaSP(String maSP) {this.maSP = maSP;}
    public void setTenSP(String tenSP) {this.tenSP = tenSP;}
    public void setSl(Integer sl) {this.sl = sl;}
    public void setGiaNhap(Double giaNhap) {this.giaNhap = giaNhap;}
    public void setNgaySX(String ngaySX) {this.ngaySX = ngaySX;}
    public void setNgayHH(String ngayHH) {this.ngayHH = ngayHH;}

    public String getMaSP() {return maSP;}
    public Double getTongTien() {return this.sl * this.giaNhap;}
    public String getTenSP() {return tenSP;}
    public Integer getSl() {return sl;}
    public Double getGiaNhap() {return giaNhap;}
    public String getNgaySX() {return ngaySX;}
    public String getNgayHH() {return ngayHH;}
    public String getMaPN() {return maPN;}
}
