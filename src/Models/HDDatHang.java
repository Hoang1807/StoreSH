package Models;
public class HDDatHang {
    private String maHD,ngayDat,diaChiGH,maGG,sdtKH,maNV;
    private int trangThai,diemSD;
    private Double tienKM;
    private boolean hinhThucTT;

    public boolean isHinhThucTT() {return hinhThucTT;}
    public void setMaHD(String maHD) {this.maHD = maHD;}
    public void setDiemSD(int diemSD) {this.diemSD = diemSD;}
    public void setTrangThai(int trangThai) {this.trangThai = trangThai;}
    public void setNgayDat(String ngayDat) {this.ngayDat = ngayDat;}
    public void setDiaChiGH(String diaChiGH) {this.diaChiGH = diaChiGH;}
    public void setMaGG(String maGG) {this.maGG = maGG;}
    public void setSdtKH(String sdtKH) {this.sdtKH = sdtKH;}
    public void setMaNV(String maNV) {this.maNV = maNV;}
    public void setHinhThucTT(boolean hinhThucTT) {this.hinhThucTT = hinhThucTT;}
    public void setTienKM(Double tienKM) {this.tienKM = tienKM;}

    public int getTrangThai() {return trangThai;}
    public String getNgayDat() {return ngayDat;}
    public String getDiaChiGH() {return diaChiGH;}
    public String getMaHD() {return maHD;}
    public String getMaGG() {return maGG;}
    public String getSdtKH() {return sdtKH;}
    public String getMaNV() {return maNV;}
    public Double getTienKM() {return tienKM;}
    public int getDiemSD() {return diemSD;}
}
