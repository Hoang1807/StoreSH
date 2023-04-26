package Models;
public class KhachHang {
    private String SDT, tenKH, gmail, matkhau, diaChi;
    private int diem;
    public KhachHang() {}
    public KhachHang(String SDT, String tenKH, int diem, String gmail, String matkhau) {
        this.SDT = SDT;
        this.tenKH = tenKH;
        this.diem = diem;
        this.gmail = gmail;
        this.matkhau = matkhau;
    }
    
    public String getDiaChi() {return diaChi;}
    public String getSDT() {return SDT;}
    public String getTenKH() {return tenKH;}
    public int getDiem() {return diem;}
    public String getGmail() {return gmail;}
    public String getMatkhau() {return matkhau;}

    public void setSDT(String SDT) {this.SDT = SDT;}
    public void setMatkhau(String matkhau) {this.matkhau = matkhau;}
    public void setTenKH(String tenKH) {this.tenKH = tenKH;}
    public void setDiem(int diem) {this.diem = diem;}
    public void setGmail(String gmail) {this.gmail = gmail;}
    public void setDiaChi(String diaChi) {this.diaChi = diaChi;}    
}
