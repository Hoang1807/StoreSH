package Models;
import DAO.CTPhieuNhapDAO;
import java.util.List;
public class PhieuNhap {
    private String maPhieuNhap, ngayNhap, ghiChu, maNV, maNCC, maKho, tinhTrang;
    public PhieuNhap() {}
    public PhieuNhap(String maPhieuNhap, String ngayNhap, String ghiChu, String maNV, String maNCC, String maKho, String tinhTrang) {
        this.maPhieuNhap = maPhieuNhap;
        this.ngayNhap = ngayNhap;
        this.ghiChu = ghiChu;
        this.maNV = maNV;
        this.maNCC = maNCC;
        this.maKho = maKho;
        this.tinhTrang = tinhTrang;
    }
    public String getMaPhieuNhap() {return maPhieuNhap;}
    public String getNgayNhap() {return ngayNhap;}
    public String getGhiChu() {return ghiChu;}
    public String getMaNV() {return maNV;}
    public String getMaNCC() {return maNCC;}
    public String getMaKho() {return maKho;}
    public String getTinhTrang() {return tinhTrang;}

    public void setMaPhieuNhap(String maPhieuNhap) {this.maPhieuNhap = maPhieuNhap;}
    public void setTinhTrang(String tinhTrang) {this.tinhTrang = tinhTrang;}
    public void setNgayNhap(String ngayNhap) {this.ngayNhap = ngayNhap;}
    public void setGhiChu(String ghiChu) {this.ghiChu = ghiChu;}
    public void setMaNV(String maNV) {this.maNV = maNV;}
    public void setMaNCC(String maNCC) {this.maNCC = maNCC;}
    public void setMaKho(String maKho) {this.maKho = maKho;}

    public Double getTongTien() {
        CTPhieuNhapDAO CTpn = new CTPhieuNhapDAO();
        Double tongTien = 0.0;
        List<CTPhieuNhap> listCTPN = CTpn.selectCTPNbyMaPN(maPhieuNhap);
        for (CTPhieuNhap ct : listCTPN) {
            tongTien += ct.getTongTien();
        }
        return tongTien;
    }
}
