package Models;
public class ChucVu {
    private String maCV,tenCV;

    public ChucVu() {}
    public ChucVu(String maCV, String tenCV) {
        this.maCV = maCV;
        this.tenCV = tenCV;
    }
    @Override
    public String toString() {return tenCV;}
    public String getMaCV() {return maCV;}
    public String getTenCV() {return tenCV;}

    public void setMaCV(String maCV) {this.maCV = maCV;}
    public void setTenCV(String tenCV) {this.tenCV = tenCV;}   
}
