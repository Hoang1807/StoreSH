/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Models.NhanVien;
import Utils.JDBCHelper;
import Utils.XDate;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.*;

/**
 *
 * @author TIEN SY
 */
public class NhanVienDAO extends CuaHangDAO<NhanVien, String> {

    public static byte[] luuAnhTam;

    @Override
    public void insert(NhanVien entity) {
        if (entity.getHinh() == null) {
            JDBCHelper.update("EXEC SP_INSERT_NVNOIMAGE ?,?,?,?,?,?,?,?,?,?,?", entity.getMaNV(), entity.getHoTen(), entity.isGioiTinh(), entity.getNgaySinh(), entity.getSdt(),
                    entity.getEmail(), entity.getHinh(), entity.getMaCV(), entity.getDiaChi(), entity.getNgayVaoLam(), entity.getMatKhau());
        } else {
            try {
                FileInputStream fis = new FileInputStream(entity.getHinh());
                JDBCHelper.update("INSERT INTO NHANVIEN VALUES  (?,?,?,?,?,?,?,?,?,?,?)", entity.getMaNV(), entity.getHoTen(), entity.isGioiTinh(), entity.getNgaySinh(), entity.getSdt(),
                        entity.getEmail(), fis, entity.getMaCV(), entity.getDiaChi(), entity.getNgayVaoLam(), entity.getMatKhau());
            } catch (FileNotFoundException ex) {
                Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void update(NhanVien entity) {
        if (entity.getHinh() == null) {
            try {
//            FileInputStream fis = new FileInputStream(entity.getHinh());
                JDBCHelper.update("UPDATE NHANVIEN SET HOTEN = ?, GIOITINH = ?,NGAYSINH = ?,"
                        + "SDT = ?, EMAIL = ?,HINH = ?,MACV = ?,DIACHI = ?,NGAYVAOLAM = ?,MATKHAU = ? WHERE MANV = ?",
                        entity.getHoTen(), entity.isGioiTinh(), entity.getNgaySinh(), entity.getSdt(),
                        entity.getEmail(), luuAnhTam, entity.getMaCV(), entity.getDiaChi(), entity.getNgayVaoLam(), entity.getMatKhau(), entity.getMaNV());
            } catch (Exception ex) {
                Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            try {
            FileInputStream fis = new FileInputStream(entity.getHinh());
                JDBCHelper.update("UPDATE NHANVIEN SET HOTEN = ?, GIOITINH = ?,NGAYSINH = ?,"
                        + "SDT = ?, EMAIL = ?,HINH = ?,MACV = ?,DIACHI = ?,NGAYVAOLAM = ?,MATKHAU = ? WHERE MANV = ?",
                        entity.getHoTen(), entity.isGioiTinh(), entity.getNgaySinh(), entity.getSdt(),
                        entity.getEmail(),fis, entity.getMaCV(), entity.getDiaChi(), entity.getNgayVaoLam(), entity.getMatKhau(), entity.getMaNV());
            } catch (Exception ex) {
                Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public void delete(String id) {
        JDBCHelper.update("delete from NHANVIEN WHERE MANV = ?", id);
    }

    @Override
    public NhanVien selectbyID(String id) {
        try {
            ResultSet rs = JDBCHelper.query("select * from nhanvien where manv =?", id);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setHoTen(rs.getString(2));
                nv.setGioiTinh(rs.getBoolean(3));
                nv.setNgaySinh(XDate.toString(rs.getDate(4), "dd-MM-yyyy"));
                nv.setSdt(rs.getString(5));
                nv.setEmail(rs.getString(6));
                luuAnhTam = rs.getBytes(7);
                nv.setMaCV(rs.getString(8));
                nv.setDiaChi(rs.getString(9));
                nv.setNgayVaoLam(XDate.toString(rs.getDate(10), "dd-MM-yyyy"));
                nv.setMatKhau(rs.getString(11));
                return nv;
            }
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<NhanVien> selectAll() {
        List<NhanVien> listNV = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query("SELECT * FROM NHANVIEN");
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNV(rs.getString(1));
                nv.setHoTen(rs.getString(2));
                nv.setGioiTinh(rs.getBoolean(3));
                nv.setNgaySinh(XDate.toString(rs.getDate(4), "dd-MM-yyyy"));
                nv.setSdt(rs.getString(5));
                nv.setEmail(rs.getString(6));
                nv.setMaCV(rs.getString(8));
                nv.setDiaChi(rs.getString(9));
                nv.setNgayVaoLam(XDate.toString(rs.getDate(10), "dd-MM-yyyy"));
                nv.setMatKhau(rs.getString(11));
                listNV.add(nv);
            }
            return listNV;
        } catch (SQLException ex) {
            Logger.getLogger(NhanVienDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public List<NhanVien> selectSQL(String sql, Object... args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
