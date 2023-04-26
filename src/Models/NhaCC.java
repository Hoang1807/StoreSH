package Models;
public class NhaCC {
    private String maNCC, tenNCC, sdt, diaChi;
    private boolean trangThai;
    
    public NhaCC() {}
    public NhaCC(String maNCC, String tenNCC, String sdt, String diaChi, boolean trangThai) {
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
    }
    
    @Override
    public String toString() {return tenNCC;}
    public boolean isTrangThai() {return trangThai;}

    public String getMaNCC() {return maNCC;}
    public String getTenNCC() {return tenNCC;}
    public String getSdt() {return sdt;}
    public String getDiaChi() {return diaChi;}

    public void setMaNCC(String maNCC) {this.maNCC = maNCC;}
    public void setTenNCC(String tenNCC) {this.tenNCC = tenNCC;}
    public void setSdt(String sdt) {this.sdt = sdt;}
    public void setDiaChi(String diaChi) {this.diaChi = diaChi;}
    public void setTrangThai(boolean trangThai) {this.trangThai = trangThai;}
}
