package DAO;
import Models.CTHDDatHang;
import Utils.JDBCHelper;
import java.util.List;
public class CTHDDatHangDAO {
    public void insert(String maHD, List<CTHDDatHang> ct) {
        for (CTHDDatHang ctHD : ct) {
            JDBCHelper.update("EXEC SP_CTHDDATHANG  ?,?,?,?", maHD, ctHD.getMaSP(), ctHD.getSL(), ctHD.getGiaBan());
        }
    }
}
