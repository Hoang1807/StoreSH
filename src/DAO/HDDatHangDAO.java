package DAO;
import Models.HDDatHang;
import Utils.JDBCHelper;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

public class HDDatHangDAO {

    public String insertHDreturnMaHD(HDDatHang hd) {
        try {
            ResultSet rs = JDBCHelper.query("EXEC SP_THANHTOAN_HDDATHANG_RETURNMAHD ?,?,?,?,?,?,?,?", hd.getTrangThai(), hd.getNgayDat(), hd.getDiaChiGH(), hd.getMaGG(), hd.getSdtKH(), hd.isHinhThucTT(), hd.getTienKM(), hd.getDiemSD());
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            Logger.getLogger(HDDatHangDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
