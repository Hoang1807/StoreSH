package Models;
public class PhieuNhapKho {
    private String maPN,maNCC,maKho,maNV,ngayNhap,ghiChu,maSP;
    private Double giaNhap;
    private int SL;

    public PhieuNhapKho() {}
    public PhieuNhapKho(String maPN, String maNCC, String maKho, String maNV, String ngayNhap, String ghiChu, String maSP, Double giaNhap, int SL) {
        this.maPN = maPN;
        this.maNCC = maNCC;
        this.maKho = maKho;
        this.maNV = maNV;
        this.ngayNhap = ngayNhap;
        this.ghiChu = ghiChu;
        this.maSP = maSP;
        this.giaNhap = giaNhap;
        this.SL = SL;
    }

    public String getMaPN() {return maPN;}
    public String getMaNCC() {return maNCC;}
    public String getMaKho() {return maKho;}
    public String getMaNV() {return maNV;}
    public String getNgayNhap() {return ngayNhap;}
    public String getGhiChu() {return ghiChu;}
    public String getMaSP() {return maSP;}
    public Double getGiaNhap() {return giaNhap;}
    public int getSL() {return SL;}

    public void setMaPN(String maPN) {this.maPN = maPN;}
    public void setGhiChu(String ghiChu) {this.ghiChu = ghiChu;}
    public void setMaNCC(String maNCC) {this.maNCC = maNCC;}
    public void setMaKho(String maKho) {this.maKho = maKho;}
    public void setMaNV(String maNV) {this.maNV = maNV;}
    public void setNgayNhap(String ngayNhap) {this.ngayNhap = ngayNhap;}
    public void setMaSP(String maSP) {this.maSP = maSP;}
    public void setGiaNhap(Double giaNhap) {this.giaNhap = giaNhap;}
    public void setSL(int SL) {this.SL = SL;}
}
