package DAO;
import Utils.JDBCHelper;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;
import java.util.ArrayList;
public class ThongKeDAO {
    public List<Object[]>thongKeDT(){
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query("select DISTINCT(CONVERT(DATE,NGAYLAP,108)),SUM(CT.SL * CT.GIABAN) from HDBANLE HD\n" +
                    "JOIN CTHDBANLE CT ON CT.MAHDBANLE =  HD.MAHDBANLE\n" +
                    "WHERE NGAYLAP <= GETDATE() AND NGAYLAP > DATEADD(DAY, -5,GETDATE())\n" +
                    "GROUP BY CONVERT(DATE,NGAYLAP,108)");
            while (rs.next()) {                
                Object[] a ={rs.getDate(1),rs.getDouble(2)}; 
                list.add(a);
            }
            return list;
        } catch (SQLException ex) {
            Logger.getLogger(ThongKeDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
