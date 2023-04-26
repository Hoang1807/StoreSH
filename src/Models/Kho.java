package Models;
public class Kho {
    private String maKho, tenKho, diaChi;
    public Kho() {}
    public Kho(String maKho, String tenKho, String diaChi) {
        this.maKho = maKho;
        this.tenKho = tenKho;
        this.diaChi = diaChi;
    }
    @Override
    public String toString() {return tenKho;}
    
    public String getMaKho() {return maKho;}
    public String getTenKho() {return tenKho;}
    public String getDiaChi() {return diaChi;}

    public void setMaKho(String maKho) {this.maKho = maKho;}
    public void setTenKho(String tenKho) {this.tenKho = tenKho;}
    public void setDiaChi(String diaChi) {this.diaChi = diaChi;} 
}
