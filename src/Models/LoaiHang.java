package Models;
public class LoaiHang {
    private String maLoai,tenLoai;
    public LoaiHang() {}
    public LoaiHang(String maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }
    @Override
    public String toString() {return tenLoai;}
    
    public String getMaLoai() {return maLoai;}
    public String getTenLoai() {return tenLoai;}

    public void setMaLoai(String maLoai) {this.maLoai = maLoai;}
    public void setTenLoai(String tenLoai) {this.tenLoai = tenLoai;}  
}
