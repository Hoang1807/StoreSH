package Models;
import java.io.File;
public class NhanVien {

    private String maNV, hoTen, ngaySinh, sdt, email, maCV, diaChi, ngayVaoLam, matKhau;
    private boolean gioiTinh;
    private File hinh;

    public NhanVien() {}
    public NhanVien(String maNV, String hoTen, boolean gioiTinh, String ngaySinh, String sdt, String email, File hinh, String maCV, String diaChi, String ngayVaoLam, String matKhau) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.sdt = sdt;
        this.email = email;
        this.hinh = hinh;
        this.maCV = maCV;
        this.diaChi = diaChi;
        this.ngayVaoLam = ngayVaoLam;
        this.matKhau = matKhau;
    } 
    @Override
    public String toString() {return getTenCV();}

    public String getMaNV() {return maNV;}
    public String getHoTen() {return hoTen;}
    public boolean isGioiTinh() {return gioiTinh;}
    public String getNgaySinh() {return ngaySinh;}
    public String getSdt() {return sdt;}
    public String getEmail() {return email;}
    public File getHinh() {return hinh;}
    public String getMaCV() {return maCV;}
    public String getDiaChi() {return diaChi;}
    public String getNgayVaoLam() { return ngayVaoLam;}
    public String getMatKhau() {return matKhau;}

    public void setMaNV(String maNV) {this.maNV = maNV;}
    public void setHoTen(String hoTen) {this.hoTen = hoTen;}
    public void setGioiTinh(boolean gioiTinh) {this.gioiTinh = gioiTinh;}
    public void setNgaySinh(String ngaySinh) {this.ngaySinh = ngaySinh;}
    public void setSdt(String sdt) {this.sdt = sdt;}
    public void setEmail(String email) {this.email = email;}
    public void setHinh(File hinh) {this.hinh = hinh;}
    public void setMaCV(String maCV) {this.maCV = maCV;}
    public void setDiaChi(String diaChi) {this.diaChi = diaChi;}
    public void setNgayVaoLam(String ngayVaoLam) {this.ngayVaoLam = ngayVaoLam;}
    public void setMatKhau(String matKhau) {this.matKhau = matKhau;}

    public String getTenCV() {
        if (maCV.equals("1")) {
            return "Chủ cửa hàng";
        } else if (maCV.equals("2")) {
            return "Quản lý";
        } else if (maCV.equals("3")) {
            return "Nhân viên kho";
        } else if (maCV.equals("4")) {
            return "Nhân viên bán hàng";
        } else if (maCV.equals("5")) {
            return "Kế toán";
        }
        return null;
    }
}
