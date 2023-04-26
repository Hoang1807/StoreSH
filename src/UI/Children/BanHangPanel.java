/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Children;

import DAO.CTHDBanLeDAO;
import DAO.GGHoaDonDAO;
import DAO.GGSanPhamDAO;
import DAO.HDBanLeDAO;
import DAO.KeHangDAO;
import DAO.KhachHangDAO;
import DAO.LoaiHangDAO;
import DAO.SanPhamDAO;
import HELP.CellRenderer;
import Utils.XPDF;
import MODEL_SWING.CellEditorButton;
import MODEL_SWING.cellRendererButton;
import Models.CTHDBanLe;
import Models.GGHoaDon;
import Models.GGSanPham;
import Models.HDBanLe;
import Models.KhachHang;
import Models.LoaiHang;
import Models.SanPham;
import UI.Children.DaiLog.WebcamDiaLog;
import UI.MainJF;
import Utils.Auth;
import Utils.DDTienTe;
import Utils.MsgBox;
import Utils.XDate;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TIEN SY
 */
public class BanHangPanel extends javax.swing.JPanel {

    /**
     * Creates new form TinhTienPanel
     */
    public BanHangPanel() {
        initComponents();
        init();
    }
    SanPhamDAO spDAO = new SanPhamDAO();
    public static LoaiHangDAO lhDAO = new LoaiHangDAO();
    HDBanLeDAO hdBanLeDAO = new HDBanLeDAO();
    CTHDBanLeDAO ctHDBanLeDAO = new CTHDBanLeDAO();
    public static KeHangDAO khDAO = new KeHangDAO();
    KhachHangDAO khachHangDAO = new KhachHangDAO();
    List<CTHDBanLe> listCTHDBanLe = new ArrayList<>();
    GGHoaDonDAO ggHDDAO = new GGHoaDonDAO();
    MouseListener mouseListener = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getClickCount() == 2) {
                SanPham sp = spDAO.selectbyID((String) tblSPDangBan.getValueAt(tblSPDangBan.getSelectedRow(), 0));
                for (int i = 0; i < getListCTHDBanLe().size(); i++) {
                    if (getListCTHDBanLe().get(i).getMaSP().equals(sp.getMaSP())) {
                        if (sp.getSlTrenKe() <= listCTHDBanLe.get(i).getSoLuong()) {
                            MsgBox.alert(null, "Số lượng không đủ chỉ mua tối đa " + sp.getSlTrenKe() + " sản phẩm !");
                            return;
                        } else {
                            listCTHDBanLe.get(i).setSoLuong(listCTHDBanLe.get(i).getSoLuong() + 1);
                            tblCTHoaDon.setValueAt(listCTHDBanLe.get(i).getSoLuong(), i, 2);
                            tblCTHoaDon.setValueAt(DDTienTe.FormatVND(listCTHDBanLe.get(i).getTongTien()), i, 4);
                            txtTongCong.setText(DDTienTe.FormatVND(getTongTien()));
                            getThanhTien();
                            return;
                        }
                    }
                }
                if (sp != null) {
                    if (sp.getSlTrenKe() == 0) {
                        MsgBox.alert(null, "Sản phẩm tạm hết hàng");
                    } else {
                        CTHDBanLe ct = new CTHDBanLe();
                        ct.setMaSP(sp.getMaSP());
                        ct.setSoLuong(1);
                        ct.setGiaBan(sp.getGiaGiam());
                        ct.setMaHD(lblMaHD.getText());
                        addTableCTBanLe(ct);
                        listCTHDBanLe.add(ct);
                        txtTongCong.setText(DDTienTe.FormatVND(getTongTien()));
                        getThanhTien();
                    }
                } else {
                    MsgBox.alert(null, "Sản phẩm không tồn tại");
                }
            }
        }

    };

    public void init() {
        fillTableSPDangBan();
        setDateRunTime();
        checkButton(0);

        //tab3
        fillTableHDBanLe(null, null, null, XDate.toString(XDate.getDate(), "yyyy-MM-dd"));
    }

    public void checkButton(int check) {
        if (check == 0) {
            tblSPDangBan.setEnabled(false);
            btnTaoHD.setEnabled(true);
            txtTimMaSP.setEnabled(false);
            btnQuetBarcode.setEnabled(false);
            txtMaKH.setEnabled(false);
        txtMaKH.setEnabled(false);
            btnSDDIem.setEnabled(false);
            txtaddMaGG.setEnabled(false);
            btnApDungGG.setEnabled(false);
            btnHuyMagg.setEnabled(false);
            txtKhachTra.setEnabled(false);
            btnHuyHD.setEnabled(false);
            btnThanhToan.setEnabled(false);
            tblSPDangBan.setRowSelectionAllowed(false);
            tblSPDangBan.removeMouseListener(mouseListener);
        } else {
            tblSPDangBan.setRowSelectionAllowed(true);
            tblSPDangBan.setEnabled(true);
            btnTaoHD.setEnabled(false);
            txtTimMaSP.setEnabled(true);
            btnQuetBarcode.setEnabled(true);
            txtMaKH.setEnabled(true);
           txtMaKH.setEnabled(true);
            btnSDDIem.setEnabled(true);
            txtaddMaGG.setEnabled(true);
            btnApDungGG.setEnabled(true);
            txtKhachTra.setEnabled(true);
            btnHuyHD.setEnabled(true);
            btnThanhToan.setEnabled(true);
            tblSPDangBan.addMouseListener(mouseListener);
        }
    }

    public void setDateRunTime() {
//        new Thread() {
//            @Override
//            public void run() {
//                while (true) {
//
//                    txtNgayHienHanh.setText(XDate.toString(XDate.getDate(), "dd-MM-yyyy HH:mm:ss"));
//                }
//            }
//
//        }.start();
    }

    //BEGIN TAB 1 TÍNH TIỀN
    public static void fillTableSPDangBan() {
        DefaultTableModel model = (DefaultTableModel) tblSPDangBan.getModel();
        model.setRowCount(0);
        List<SanPham> listSP = khDAO.getlistSPTrenKe();
        if (listSP != null) {
            BufferedImage bfimage[] = new BufferedImage[listSP.size()];
            for (int i = 0; i < listSP.size(); i++) {
                ByteArrayInputStream byteArray = new ByteArrayInputStream(listSP.get(i).getHinh());
                try {
                    bfimage[i] = ImageIO.read(byteArray);
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                LoaiHang lh = lhDAO.selectbyID(listSP.get(i).getMaLoai());
                GGSanPhamDAO gg = new GGSanPhamDAO();
                GGSanPham sp = gg.selectByCheckHieuLuc(listSP.get(i).getMaSP());
                if (sp == null) {
                    Object row[] = {listSP.get(i).getMaSP(), listSP.get(i).getTenSP(), listSP.get(i).getSlTrenKe(), DDTienTe.FormatVND(listSP.get(i).getGiaBan()), null, listSP.get(i).getQrCode()};
                    model.addRow(row);
                } else {
                    Object row[] = {listSP.get(i).getMaSP(), listSP.get(i).getTenSP(), listSP.get(i).getSlTrenKe(), "<HTML><strike>" + DDTienTe.FormatVND(listSP.get(i).getGiaBan()) + "</strike>" + " - " + DDTienTe.FormatVND(listSP.get(i).getGiaGiam()), null, listSP.get(i).getQrCode()};
                    model.addRow(row);
                }
            }
            tblSPDangBan.getColumnModel().getColumn(4).setCellRenderer(new CellRenderer(bfimage));
        }
    }

    public List<CTHDBanLe> getListCTHDBanLe() {
        return listCTHDBanLe;
    }

    public void addTableCTBanLe(CTHDBanLe ct) {
        tblCTHoaDon.getColumnModel().getColumn(5).setCellRenderer(new cellRendererButton());
        tblCTHoaDon.getColumnModel().getColumn(5).setCellEditor(new CellEditorButton());
        DefaultTableModel model = (DefaultTableModel) tblCTHoaDon.getModel();
        SanPham sp = spDAO.selectbyID(ct.getMaSP());
        GGSanPhamDAO ggSPDAO = new GGSanPhamDAO();
        JButton btnXoa = new JButton("X");
        btnXoa.setBorder(null);
        btnXoa.setForeground(Color.red);
        btnXoa.setFont(new Font("tohama", Font.BOLD, 12));
        btnXoa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        if (ggSPDAO.selectByCheckHieuLuc(sp.getMaSP()) == null) {
            Object[] row = {ct.getMaSP(), sp.getTenSP(), ct.getSoLuong(), DDTienTe.FormatVND(ct.getGiaBan()), DDTienTe.FormatVND(ct.getTongTien()), btnXoa};
            model.addRow(row);
            getThanhTien();
        } else {
            Object[] row = {ct.getMaSP(), sp.getTenSP(), ct.getSoLuong(), "<HTML><strike>" + DDTienTe.FormatVND(sp.getGiaBan()) + "</strike>" + " - " + DDTienTe.FormatVND(sp.getGiaGiam()), DDTienTe.FormatVND(ct.getTongTien()), btnXoa};
            model.addRow(row);
            getThanhTien();
        }

    }

    public void addSPVaoCTHD() {
        SanPham sp = spDAO.selectbyID(txtTimMaSP.getText());
        for (int i = 0; i < getListCTHDBanLe().size(); i++) {
            if (getListCTHDBanLe().get(i).getMaSP().equals(sp.getMaSP())) {
                if (sp.getSlTrenKe() <= listCTHDBanLe.get(i).getSoLuong()) {
                    MsgBox.alert(this, "Số lượng không đủ chỉ mua tối đa " + sp.getSlTrenKe() + " sản phẩm !");
                    return;
                } else {
                    listCTHDBanLe.get(i).setSoLuong(listCTHDBanLe.get(i).getSoLuong() + 1);
                    tblCTHoaDon.setValueAt(listCTHDBanLe.get(i).getSoLuong(), i, 2);
                    tblCTHoaDon.setValueAt(DDTienTe.FormatVND(listCTHDBanLe.get(i).getTongTien()), i, 4);
                    txtTongCong.setText(DDTienTe.FormatVND(getTongTien()));
                    getThanhTien();
                    return;
                }
            }
        }
        if (sp != null) {
            if (sp.getSlTrenKe() == 0) {
                MsgBox.alert(this, "Sản phẩm tạm hết hàng");
            } else {
                CTHDBanLe ct = new CTHDBanLe();
                ct.setMaSP(sp.getMaSP());
                ct.setSoLuong(1);
                ct.setGiaBan(sp.getGiaGiam());
                ct.setMaHD(lblMaHD.getText());
                addTableCTBanLe(ct);
                listCTHDBanLe.add(ct);
                txtTongCong.setText(String.valueOf(getTongTien()));
                getThanhTien();
            }
        } else {
            MsgBox.alert(this, "Sản phẩm không tồn tại");
        }
    }

    public HDBanLe getFormHDBanLe() {
        HDBanLe hd = new HDBanLe();
        GGHoaDonDAO ggHDDAO = new GGHoaDonDAO();
        GGHoaDon ggHD = ggHDDAO.selectbyID(txtaddMaGG.getText());
        if (!btnApDungGG.isEnabled()) {
            if (ggHD == null) {
                hd.setMaGG(null);
            } else {
                hd.setMaGG(ggHD.getMaGG());
            }
        } else {
            hd.setMaHD(null);
        }

        hd.setDiemSD(Integer.parseInt(txtDiemSD.getText()));
        hd.setMaHD(lblMaHD.getText());
        hd.setNgayLap(XDate.toString(XDate.getDate(), "yyyy-MM-dd hh:mm:ss"));
        if (txtSDT.getText().trim().isEmpty()) {
            hd.setSdtKH(null);
        } else {
            hd.setSdtKH(txtMaKH.getText());
        }
        if (txtaddMaGG.getText().trim().isEmpty()) {
            hd.setMaGG(null);
        }
        hd.setTienKhuyenMai(Double.parseDouble(txtSoTienGiam.getText()));
        hd.setMaNV(Auth.user.getMaNV());
        return hd;
    }

    public void xoaSPTableCTHD() {
        int index = tblCTHoaDon.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblCTHoaDon.getModel();
        model.removeRow(index);
        listCTHDBanLe.remove(index);
        txtTongCong.setText(String.valueOf(getTongTien()));
        getThanhTien();
    }

    public void thayDoiSLTrucTiep() {

        int index = tblCTHoaDon.getSelectedRow();
        if (index != -1) {
            try {
                Object soLuongUpdate = tblCTHoaDon.getValueAt(index, 2);
                String soLuongString = String.valueOf(soLuongUpdate);
                int soLuongInt = Integer.parseInt(soLuongString);
                SanPham sp = spDAO.selectbyID((String) tblCTHoaDon.getValueAt(index, 0));
                if (soLuongInt > sp.getSlTrenKe()) {
                    MsgBox.alert(this, "Số lượng không đủ chỉ mua tối đa " + sp.getSlTrenKe() + " sản phẩm !");
                    tblCTHoaDon.setValueAt(listCTHDBanLe.get(index).getSoLuong(), index, 2);
                } else {
                    getListCTHDBanLe().get(index).setSoLuong(soLuongInt);
                    tblCTHoaDon.setValueAt(DDTienTe.FormatVND(getListCTHDBanLe().get(index).getTongTien()), index, 4);
                    txtTongCong.setText(DDTienTe.FormatVND(getTongTien()));
                    getThanhTien();
                }
            } catch (Exception e) {
//                MsgBox.alert(this, "Số lượng không được nhập chữ");
                if (tblCTHoaDon.getRowCount() > 0) {
                    tblCTHoaDon.setValueAt(listCTHDBanLe.get(index).getSoLuong(), index, 2);
                }

            }

        }
    }

    public Double getTongTien() {
        Double tongTien = 0.0;
        for (CTHDBanLe ct : listCTHDBanLe) {
            tongTien += ct.getTongTien();
        }
        return tongTien;
    }

    public void suDungMaGG() {
        GGHoaDon ggHD = ggHDDAO.selectbyIDandNgayAPDung(txtaddMaGG.getText());
        if (ggHD == null) {
            MsgBox.alert(this, "Mã giảm giá không tồn tại !");
        } else if (ggHD.getSoLuong() == 0) {
            MsgBox.alert(this, "Mã giảm giá đã hết !");
        } else {
            try {
                if (getTongTien() < ggHD.getHoaDonAD()) {
                    MsgBox.alert(this, "Mã khuyến mãi chỉ áp dụng cho hóa đơn trên " + ggHD.getHoaDonAD());
                    return;
                } else {
                    if (ggHD.isHinhThuc() == true) {
                        hdBanLeDAO.suDungMaGG(lblMaHD.getText(), ggHD.getMaGG());
                        txtSoTienGiam.setText(String.valueOf(ggHD.getMucGG()));
                        getThanhTien();
                    } else {
                        hdBanLeDAO.suDungMaGG(lblMaHD.getText(), ggHD.getMaGG());
                        double tienGG = getTongTien() * ggHD.getMucGG() / 100;
                        txtSoTienGiam.setText(String.valueOf(tienGG));
                        getThanhTien();
                    }
                }

                btnHuyMagg.setEnabled(true);
                btnApDungGG.setEnabled(false);
                txtaddMaGG.setEnabled(false);
            } catch (Exception e) {
                MsgBox.alert(this, "Áp dụng thất bại !");
            }

        }
    }

    public void huyMaGG() {
        try {
            hdBanLeDAO.huyMaGG(lblMaHD.getText(), txtaddMaGG.getText());
            MsgBox.alert(this, "Hủy thành công");
            txtaddMaGG.setText("");
            btnHuyMagg.setEnabled(false);
            btnApDungGG.setEnabled(true);
            txtaddMaGG.setEnabled(true);
            txtSoTienGiam.setText("0");
            getThanhTien();
        } catch (Exception e) {
            MsgBox.alert(this, "Hủy thất bại !");
        }
    }

    public void timKhachHang() {
        KhachHang kh = khachHangDAO.selectbyID(txtMaKH.getText());
        if (kh == null) {
            MsgBox.alert(this, "Khách hàng không tồn tại bạn có muốn tạo thẻ thành viên mới không ?");
        } else {
            txtTenKH.setText(kh.getTenKH());
            txtDiemKH.setText(String.valueOf(kh.getDiem()));
            txtSDT.setText(kh.getSDT());
            txtDiemSD.setText("0");
            getThanhTien();
        }
    }

    public void taoHoaDon() {
        try {
            String maHD = hdBanLeDAO.insertReturnMaHD();
            lblMaHD.setText(maHD);
            checkButton(1);
            MsgBox.alert(this, "Tạo hóa đơn thành công");
        } catch (Exception e) {
            MsgBox.alert(this, "Tạo thất bại !");
        }

    }

    public void suDungDiem() {
        KhachHang kh = khachHangDAO.selectbyID(txtSDT.getText());
        String diem = MsgBox.prompt(this, "Nhập số điểm muốn sử dụng");
        if (diem != null) {
            if (Integer.parseInt(diem) > kh.getDiem()) {
                MsgBox.alert(this, "Bạn không đủ điểm bạn chỉ sử dụng tối đa là " + kh.getDiem());
            } else {
                if (Integer.parseInt(diem) > getTongTien() * 10 / 100) {
                    MsgBox.alert(this, "Bạn chỉ được sử dụng tối đa " + getTongTien() * 10 / 100 + " dựa trên 10% giá trị đơn hàng !");
                } else {
                    txtDiemSD.setText(diem);
                    getThanhTien();
                }
            }
        }

    }

    public Double getThanhTien() {
        Double thanhTien = getTongTien() - Double.parseDouble(txtDiemSD.getText()) - Double.parseDouble(txtSoTienGiam.getText());
        txtThanhTien.setText(DDTienTe.FormatVND(thanhTien));
        return thanhTien;
    }

    public void claerFormTinhTien() {
        txtMaKH.setText("");
        txtTimMaSP.setText("");
        txtaddMaGG.setText("");
        lblMaHD.setText("");
        txtSDT.setText("");
        txtTenKH.setText("");
        txtDiemKH.setText("0");
        txtTongCong.setText("0");
        txtSoTienGiam.setText("0");
        txtDiemSD.setText("0");
        txtThanhTien.setText("0");
        txtKhachTra.setText("");
        txtTraLaiKhach.setText("0");
        listCTHDBanLe.clear();
        DefaultTableModel model = (DefaultTableModel) tblCTHoaDon.getModel();
        model.setRowCount(0);
    }

    public void huyHD() {
        hdBanLeDAO.deleteHD(lblMaHD.getText());
        claerFormTinhTien();
        checkButton(0);
    }

    public void thanhToan() {
        try {
            hdBanLeDAO.thanhToan(listCTHDBanLe, getFormHDBanLe());
            khachHangDAO.tichDiem(getThanhTien(), txtSDT.getText());
            String maHD = lblMaHD.getText();
            String khachTra = txtKhachTra.getText();
            claerFormTinhTien();
            System.out.println(maHD);
            checkButton(0);
            if (MsgBox.confrim(this, "Bạn có muốn xuất hóa đơn")) {
                JFileChooser jf = new JFileChooser();
                if (jf.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    XPDF xuatPDF = new XPDF();
                    xuatPDF.setPath(jf.getSelectedFile().getAbsolutePath());
                    HDBanLe hdBanLe = hdBanLeDAO.selectByID(maHD);
                    List<CTHDBanLe> listHDBanLe = ctHDBanLeDAO.selectCTHDbyMAHD(hdBanLe.getMaHD());
                    if ((hdBanLe.getTienKhuyenMai() + hdBanLe.getDiemSD()) > 0) {
                        xuatPDF.setKhuyenMai((float) (hdBanLe.getTienKhuyenMai() + hdBanLe.getDiemSD()));
                    }
                    xuatPDF.setKhachTra(Integer.parseInt(khachTra));
                    xuatPDF.createFilePDF(hdBanLe, listHDBanLe);
                }
            }
            MsgBox.alert(this, "Thanh toán thành công");
            khDAO.setListSPTrenKe(khDAO.selectAll());
            fillTableSPDangBan();
            fillTableHDBanLe(null, null, null, XDate.toString(XDate.getDate(), "yyyy-MM-dd"));

        } catch (Exception e) {
            System.out.println(e);
        }

    }
//END TAB1

    //BEGIN TAB3
    public void fillTableHDBanLe(String maHD, String ngayBD, String ngayKT, String theoNgay) {
        DefaultTableModel model = (DefaultTableModel) tblHDBanLe.getModel();
        model.setRowCount(0);
        try {
            List<HDBanLe> listHD = hdBanLeDAO.selectALbyMaAndNgay(maHD, ngayBD, ngayKT, theoNgay);
            for (HDBanLe hd : listHD) {
                Object row[] = {hd.getMaHD(), hd.getNgayLap(), hd.getSdtKH() == null ? "Khách vãng lai" : hd.getSdtKH(), DDTienTe.FormatVND(hdBanLeDAO.getTongTienHD(hd.getMaHD())),
                    hd.getMaNV(), hd.getMaGG(), hd.getDiemSD()};
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void filltableCTHDbanLe(String maHD) {
        DefaultTableModel model = (DefaultTableModel) tblCTHDBanLe.getModel();
        model.setRowCount(0);
        List<CTHDBanLe> listCTHD = ctHDBanLeDAO.selectCTHDbyMAHD(maHD);
        for (CTHDBanLe ct : listCTHD) {
            SanPham sp = spDAO.selectbyID(ct.getMaSP());
            Object[] row = {ct.getMaSP(), sp.getTenSP(), ct.getGiaBan(), ct.getSoLuong(), DDTienTe.FormatVND(ct.getTongTien())};
            model.addRow(row);
        }
    }

    public void timKiemHDTab3() {
        String maHD = Tab3txtTimKien.getText();
        HDBanLe hDBanLe = hdBanLeDAO.selectByID(maHD);
        if (hdBanLeDAO == null) {
            MsgBox.alert(this, "Không tồn tại hóa đơn");
            return;
        }
        if (maHD.trim().isEmpty()) {
            fillTableHDBanLe(null, null, null, null);
        } else {
            fillTableHDBanLe(maHD, null, null, null);
        }
    }

    public void locHDTab3() {
        if (tab3NgayBD.getDate() == null && tab3NgayBD.getDate() == null) {
            fillTableHDBanLe(null, null, null, null);
        } else if (tab3NgayBD.getDate() == null) {
            fillTableHDBanLe(null, null, XDate.toString(tab3NgayKT.getDate(), "yyyy-MM-dd"), null);
        } else {
            fillTableHDBanLe(null, XDate.toString(tab3NgayBD.getDate(), "yyyy-MM-dd"), null, null);
        }

    }

    //END TAB3
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCTHoaDon = new HELP.Table();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtaddMaGG = new javax.swing.JTextField();
        txtTongCong = new javax.swing.JLabel();
        txtSoTienGiam = new javax.swing.JLabel();
        txtThanhTien = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtKhachTra = new javax.swing.JTextField();
        txtTraLaiKhach = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        txtDiemKH = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        txtDiemSD = new javax.swing.JLabel();
        btnHuyHD = new javax.swing.JButton();
        btnTaoHD = new javax.swing.JButton();
        btnThanhToan = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btnApDungGG = new javax.swing.JButton();
        btnHuyMagg = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JLabel();
        btnSDDIem = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTimMaSP = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSPDangBan = new HELP.Table();
        jLabel23 = new javax.swing.JLabel();
        lblMaHD = new javax.swing.JLabel();
        btnQuetBarcode = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtNgayHienHanh = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField8 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblHDBanLe = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblCTHDBanLe = new javax.swing.JTable();
        Tab3txtTimKien = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        tab3NgayBD = new com.toedter.calendar.JDateChooser();
        tab3NgayKT = new com.toedter.calendar.JDateChooser();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Tab3cboTheoNgay = new javax.swing.JComboBox<>();

        jLabel4.setText("jLabel4");

        jTabbedPane1.setForeground(new java.awt.Color(102, 102, 102));
        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N

        tblCTHoaDon.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblCTHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên Sản Phẩm", "SL", "Đơn Giá", "Tổng Tiền", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCTHoaDon.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblCTHoaDon.setRowHeight(30);
        tblCTHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblCTHoaDonMouseReleased(evt);
            }
        });
        tblCTHoaDon.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                tblCTHoaDonPropertyChange(evt);
            }
        });
        jScrollPane1.setViewportView(tblCTHoaDon);
        if (tblCTHoaDon.getColumnModel().getColumnCount() > 0) {
            tblCTHoaDon.getColumnModel().getColumn(5).setMaxWidth(50);
        }

        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 153, 255));
        jLabel1.setText("THÀNH TIỀN");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 153, 255));
        jLabel2.setText("GIẢM GIÁ");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 153, 255));
        jLabel3.setText("TỔNG CỘNG");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setText("MÃ KH");

        txtMaKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaKHActionPerformed(evt);
            }
        });
        txtMaKH.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMaKHKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setText("KHUYẾN MÃI");

        txtaddMaGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtaddMaGGActionPerformed(evt);
            }
        });

        txtTongCong.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtTongCong.setText("0");

        txtSoTienGiam.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtSoTienGiam.setText("0");

        txtThanhTien.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtThanhTien.setText("0");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setText("KHÁCH TRẢ");

        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel13.setText("TRẢ LẠI KHÁCH");

        txtKhachTra.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtKhachTraKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtKhachTraKeyTyped(evt);
            }
        });

        txtTraLaiKhach.setEditable(false);
        txtTraLaiKhach.setText("0");
        txtTraLaiKhach.setEnabled(false);

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel17.setText("SĐT");

        txtDiemKH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        txtDiemKH.setForeground(new java.awt.Color(255, 0, 51));
        txtDiemKH.setText("0");

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 153, 255));
        jLabel20.setText("SỬ DỤNG ĐIỂM");

        txtDiemSD.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        txtDiemSD.setText("0");

        btnHuyHD.setBackground(new java.awt.Color(204, 0, 0));
        btnHuyHD.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnHuyHD.setForeground(new java.awt.Color(255, 255, 255));
        btnHuyHD.setText("F0- Hủy");
        btnHuyHD.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnHuyHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyHDActionPerformed(evt);
            }
        });

        btnTaoHD.setBackground(new java.awt.Color(0, 102, 102));
        btnTaoHD.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnTaoHD.setForeground(new java.awt.Color(255, 255, 255));
        btnTaoHD.setText("Tạo Hóa Đơn");
        btnTaoHD.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnTaoHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaoHDActionPerformed(evt);
            }
        });

        btnThanhToan.setBackground(new java.awt.Color(0, 102, 102));
        btnThanhToan.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnThanhToan.setText("F12 - Thanh Toán");
        btnThanhToan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThanhToanActionPerformed(evt);
            }
        });
        btnThanhToan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                btnThanhToanKeyTyped(evt);
            }
        });

        jPanel7.setLayout(new java.awt.GridLayout(0, 1));

        btnApDungGG.setText("Áp dụng");
        btnApDungGG.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnApDungGGActionPerformed(evt);
            }
        });
        jPanel7.add(btnApDungGG);

        btnHuyMagg.setText("Hủy mã ");
        btnHuyMagg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyMaggActionPerformed(evt);
            }
        });
        jPanel7.add(btnHuyMagg);

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel18.setText("Tên KH");

        txtTenKH.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtTenKH.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setText("Số điểm");

        txtSDT.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtSDT.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnSDDIem.setBackground(new java.awt.Color(153, 0, 255));
        btnSDDIem.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnSDDIem.setForeground(new java.awt.Color(255, 255, 255));
        btnSDDIem.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        btnSDDIem.setText("Sử dụng điểm");
        btnSDDIem.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnSDDIem.setOpaque(true);
        btnSDDIem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSDDIemMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(129, 129, 129)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtKhachTra, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTraLaiKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING))
                                    .addComponent(btnTaoHD, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtTongCong, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtSoTienGiam, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDiemSD, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtThanhTien, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(btnHuyHD, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(4, 4, 4))))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtaddMaGG, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8))
                                .addGap(18, 18, 18)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txtMaKH)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel18)
                            .addComponent(jLabel9))
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(txtDiemKH, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtSDT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTenKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addComponent(btnSDDIem, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(10, 10, 10)
                .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel18)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(btnSDDIem, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(txtDiemKH)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 13, Short.MAX_VALUE)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtaddMaGG, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtTongCong))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSoTienGiam))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(txtDiemSD))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtThanhTien))
                .addGap(51, 51, 51)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtKhachTra, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtTraLaiKhach, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(22, 22, 22)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHuyHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnTaoHD, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setText("Mã SP:");

        txtTimMaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTimMaSPActionPerformed(evt);
            }
        });
        txtTimMaSP.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtTimMaSPKeyTyped(evt);
            }
        });

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel22.setText("Mã HĐ: ");

        tblSPDangBan.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblSPDangBan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã SP", "Tên SP", "Số Lượng", "Giá bán", "Hình", "Barcode"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSPDangBan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        tblSPDangBan.setRowHeight(80);
        tblSPDangBan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tblSPDangBanMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblSPDangBan);
        if (tblSPDangBan.getColumnModel().getColumnCount() > 0) {
            tblSPDangBan.getColumnModel().getColumn(3).setMinWidth(100);
        }

        jLabel23.setBackground(new java.awt.Color(0, 102, 102));
        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Danh Sách Sản Phẩm Đang Bán");
        jLabel23.setOpaque(true);

        lblMaHD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblMaHD.setForeground(new java.awt.Color(255, 0, 51));
        lblMaHD.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnQuetBarcode.setText("Quét BarCode");
        btnQuetBarcode.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQuetBarcodeActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 0));
        jLabel6.setText("Thời Gian:");

        txtNgayHienHanh.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        txtNgayHienHanh.setText("12:00:11");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTimMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnQuetBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 895, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 895, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNgayHienHanh, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel7)
                        .addComponent(txtTimMaSP, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnQuetBarcode, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblMaHD, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtNgayHienHanh)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Tính Tiền", jPanel1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Mã SP", "Tên SP", "Số Lượg", "Tổng tiền"
            }
        ));
        jScrollPane4.setViewportView(jTable2);

        jLabel28.setText("DANH SÁCH ĐƠN HÀNG");

        jLabel29.setText("Chi Tiết Đơn");

        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel30.setText("MÃ ĐƠN ĐH");

        jLabel31.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel31.setText("HÌNH THỨC THANH TOÁN");

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel32.setText("ĐỊA CHỈ GIAO");

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel33.setText("NGÀY ĐẶT HÀNG");

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel34.setText("TRẠNG THÁI");

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel35.setText("SỐ ĐIỆN THOẠI KH");

        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane5.setViewportView(jTextArea1);

        jTextField8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField8ActionPerformed(evt);
            }
        });

        jRadioButton1.setText("Thanh Toàn Khi Nhận Hàng");

        jRadioButton2.setText("Đã Thanh Toán");

        jRadioButton3.setText("Chờ Giao Hàng");

        jRadioButton5.setText("Đã giao Hàng");
        jRadioButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButton5ActionPerformed(evt);
            }
        });

        jPanel5.setLayout(new java.awt.GridLayout(0, 1, 0, 6));

        jLabel36.setBackground(new java.awt.Color(0, 102, 204));
        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("GIAO HÀNG");
        jLabel36.setOpaque(true);
        jPanel5.add(jLabel36);

        jLabel37.setBackground(new java.awt.Color(255, 51, 51));
        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(255, 255, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("HỦY ĐƠN HÀNG");
        jLabel37.setOpaque(true);
        jPanel5.add(jLabel37);

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(0, 153, 255));
        jLabel38.setText("TỔNG CỘNG");

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel39.setText("100.000");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(0, 153, 255));
        jLabel40.setText("GIẢM GIÁ");

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel41.setText("0");

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(0, 153, 255));
        jLabel42.setText("SỬ DỤNG ĐIỂM");

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel43.setText("0");

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(0, 153, 255));
        jLabel44.setText("THÀNH TIỀN");

        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel45.setText("100.000");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel31, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jRadioButton2))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jRadioButton3)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButton5)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                            .addComponent(jTextField7, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(26, 26, 26)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel44, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel40, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel38, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel30)
                            .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel33)
                            .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel32)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel39))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel41))
                                .addGap(30, 30, 30)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                                    .addComponent(jLabel42)
                                    .addComponent(jLabel43)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(jLabel44)
                            .addComponent(jLabel45))))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel35)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel31)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(30, 30, 30)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabel34)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton5))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Mã Đơn ĐH", "Ngày đặt", "Tổng Tiền", "Hình Thức TT", "Nhân Viên Xử Lý", "Trạng Thái"
            }
        ));
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Đơn Đặt Hàng", jPanel2);

        tblHDBanLe.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblHDBanLe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "MÃ HD", "Ngày Lập", "Mã KH", "Tổng Tiền", "Thu Ngân", "Mã GG", "Điểm SD"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHDBanLe.setGridColor(new java.awt.Color(51, 51, 51));
        tblHDBanLe.setRowHeight(30);
        tblHDBanLe.setShowGrid(true);
        tblHDBanLe.setShowVerticalLines(false);
        tblHDBanLe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                tblHDBanLeMouseReleased(evt);
            }
        });
        jScrollPane6.setViewportView(tblHDBanLe);
        if (tblHDBanLe.getColumnModel().getColumnCount() > 0) {
            tblHDBanLe.getColumnModel().getColumn(0).setMaxWidth(50);
            tblHDBanLe.getColumnModel().getColumn(1).setMinWidth(110);
            tblHDBanLe.getColumnModel().getColumn(2).setMinWidth(100);
        }

        tblCTHDBanLe.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        tblCTHDBanLe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Sản Phẩm", "Tên Sản Phẩm", "Giá bán", "Số Lượng", "Tổng tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tblCTHDBanLe);

        Tab3txtTimKien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Tab3txtTimKienActionPerformed(evt);
            }
        });

        jLabel46.setBackground(new java.awt.Color(0, 153, 204));
        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("TÌM KIẾM");
        jLabel46.setOpaque(true);
        jLabel46.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel46MouseClicked(evt);
            }
        });

        jLabel47.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(0, 102, 102));
        jLabel47.setText("Ngày BĐ");

        jLabel48.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(0, 102, 102));
        jLabel48.setText("Ngày KT");

        jLabel49.setBackground(new java.awt.Color(0, 102, 102));
        jLabel49.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(255, 255, 255));
        jLabel49.setText("DANH SÁCH HÓA ĐƠN");
        jLabel49.setOpaque(true);

        jLabel50.setBackground(new java.awt.Color(0, 102, 102));
        jLabel50.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(255, 255, 255));
        jLabel50.setText("CHI TIẾT HÓA ĐƠN");
        jLabel50.setOpaque(true);

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 102, 102));
        jLabel10.setText("Mã HD");

        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 102, 102));
        jLabel14.setText("HÓA ĐƠN BÁN LẺ");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("LỌC");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Tab3cboTheoNgay.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        Tab3cboTheoNgay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Hôm nay", "1 Ngày trước", "2 Ngày trước" }));
        Tab3cboTheoNgay.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                Tab3cboTheoNgayItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 710, Short.MAX_VALUE)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(Tab3txtTimKien, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel47)
                                    .addComponent(tab3NgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel48)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(tab3NgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel50, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE))
                            .addComponent(Tab3cboTheoNgay, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel14)
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel48)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tab3NgayKT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel47)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tab3NgayBD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(Tab3cboTheoNgay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(4, 4, 4)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Tab3txtTimKien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel49, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel50, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jScrollPane7)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Hóa đơn bán lẻ", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jRadioButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButton5ActionPerformed

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField8ActionPerformed

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField7ActionPerformed

    private void btnTaoHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaoHDActionPerformed
        taoHoaDon();
    }//GEN-LAST:event_btnTaoHDActionPerformed

    private void txtaddMaGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtaddMaGGActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtaddMaGGActionPerformed

    private void txtMaKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaKHActionPerformed
      
    }//GEN-LAST:event_txtMaKHActionPerformed

    private void btnApDungGGActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnApDungGGActionPerformed
        suDungMaGG();
    }//GEN-LAST:event_btnApDungGGActionPerformed

    private void btnHuyMaggActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyMaggActionPerformed
        huyMaGG();
    }//GEN-LAST:event_btnHuyMaggActionPerformed

    private void btnSDDIemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSDDIemMouseClicked
        suDungDiem();
    }//GEN-LAST:event_btnSDDIemMouseClicked

    private void btnHuyHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyHDActionPerformed
        if (MsgBox.confrim(this, "Bạn thực sự muốn hủy hóa đơn")) {
            huyHD();
        }
    }//GEN-LAST:event_btnHuyHDActionPerformed

    private void btnThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThanhToanActionPerformed
        thanhToan();
    }//GEN-LAST:event_btnThanhToanActionPerformed

    private void txtKhachTraKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhachTraKeyReleased


    }//GEN-LAST:event_txtKhachTraKeyReleased

    private void tblSPDangBanMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblSPDangBanMousePressed
//        if (evt.getClickCount() == 2) {
//            SanPham sp = spDAO.selectbyID((String) tblSPDangBan.getValueAt(tblSPDangBan.getSelectedRow(), 0));
//            for (int i = 0; i < getListCTHDBanLe().size(); i++) {
//                if (getListCTHDBanLe().get(i).getMaSP().equals(sp.getMaSP())) {
//                    if (sp.getSlTrenKe() <= listCTHDBanLe.get(i).getSoLuong()) {
//                        MsgBox.alert(this, "Số lượng không đủ chỉ mua tối đa " + sp.getSlTrenKe() + " sản phẩm !");
//                        return;
//                    } else {
//                        listCTHDBanLe.get(i).setSoLuong(listCTHDBanLe.get(i).getSoLuong() + 1);
//                        tblCTHoaDon.setValueAt(listCTHDBanLe.get(i).getSoLuong(), i, 2);
//                        tblCTHoaDon.setValueAt(DDTienTe.FormatVND(listCTHDBanLe.get(i).getTongTien()), i, 4);
//                        txtTongCong.setText(DDTienTe.FormatVND(getTongTien()));
//                        getThanhTien();
//                        return;
//                    }
//                }
//            }
//            if (sp != null) {
//                if (sp.getSlTrenKe() == 0) {
//                    MsgBox.alert(this, "Sản phẩm tạm hết hàng");
//                } else {
//                    CTHDBanLe ct = new CTHDBanLe();
//                    ct.setMaSP(sp.getMaSP());
//                    ct.setSoLuong(1);
//                    ct.setGiaBan(sp.getGiaGiam());
//                    ct.setMaHD(lblMaHD.getText());
//                    addTableCTBanLe(ct);
//                    listCTHDBanLe.add(ct);
//                    txtTongCong.setText(DDTienTe.FormatVND(getTongTien()));
//                    getThanhTien();
//                }
//            } else {
//                MsgBox.alert(this, "Sản phẩm không tồn tại");
//            }
//        }

    }//GEN-LAST:event_tblSPDangBanMousePressed

    private void txtTimMaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTimMaSPActionPerformed

    }//GEN-LAST:event_txtTimMaSPActionPerformed

    private void txtTimMaSPKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimMaSPKeyTyped
        if (evt.getKeyChar() == 10) {
            addSPVaoCTHD();

        }
    }//GEN-LAST:event_txtTimMaSPKeyTyped

    private void btnQuetBarcodeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQuetBarcodeActionPerformed
        WebcamDiaLog webCamDL = new WebcamDiaLog(null, true);

        new Thread() {
            @Override
            public void run() {
                while (true) {

                    try {
                        Thread.sleep(10);
                        Boolean ck = false;
                        if (webCamDL.text != null) {
                            SanPham sp = spDAO.selectbyBarcode(webCamDL.text);
                            System.out.println(webCamDL.text);
                            if (sp != null) {
                                if (sp.getSlTrenKe() == 0) {
                                    MsgBox.alert(null, "Sản phẩm tạm hết hàng");
                                    webCamDL.text = null;
                                } else {
                                    for (int i = 0; i < getListCTHDBanLe().size(); i++) {
                                        if (getListCTHDBanLe().get(i).getMaSP().equals(sp.getMaSP())) {
                                            if (sp.getSlTrenKe() <= listCTHDBanLe.get(i).getSoLuong()) {
                                                MsgBox.alert(null, "Số lượng không đủ chỉ mua tối đa " + sp.getSlTrenKe() + " sản phẩm !");
                                                webCamDL.text = null;
                                                ck = true;
                                                break;
                                            } else {
                                                listCTHDBanLe.get(i).setSoLuong(listCTHDBanLe.get(i).getSoLuong() + 1);
                                                tblCTHoaDon.setValueAt(listCTHDBanLe.get(i).getSoLuong(), i, 2);
                                                tblCTHoaDon.setValueAt(DDTienTe.FormatVND(listCTHDBanLe.get(i).getTongTien()), i, 4);
                                                txtTongCong.setText(DDTienTe.FormatVND(getTongTien()));
                                                getThanhTien();
                                                webCamDL.text = null;
                                                ck = true;
                                                break;
                                            }
                                        }
                                    }
                                    if (ck != true) {
                                        CTHDBanLe ct = new CTHDBanLe();
                                        ct.setMaSP(sp.getMaSP());
                                        ct.setSoLuong(1);
                                        ct.setGiaBan(sp.getGiaGiam());
                                        ct.setMaHD(lblMaHD.getText());
                                        addTableCTBanLe(ct);
                                        listCTHDBanLe.add(ct);
                                        txtTongCong.setText(DDTienTe.FormatVND(getTongTien()));
                                        getThanhTien();
                                        webCamDL.text = null;
                                    }
                                }
                            } else {
                                MsgBox.alert(null, "Sản phẩm không tồn tại");
                                webCamDL.text = null;
                            }

                        }

                    } catch (Exception e) {
                    }
                }
            }

        }
                .start();
        webCamDL.setVisible(true);

    }//GEN-LAST:event_btnQuetBarcodeActionPerformed

    private void tblCTHoaDonPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_tblCTHoaDonPropertyChange
        if (evt.getNewValue() == null) {
            thayDoiSLTrucTiep();
        }
    }//GEN-LAST:event_tblCTHoaDonPropertyChange

    private void tblCTHoaDonMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCTHoaDonMouseReleased
        if (tblCTHoaDon.getSelectedColumn() == 5) {
            int index = tblCTHoaDon.getSelectedRow();
            getListCTHDBanLe().remove(index);
            txtTongCong.setText(String.valueOf(getTongTien()));
            getThanhTien();
        }
    }//GEN-LAST:event_tblCTHoaDonMouseReleased

    private void Tab3txtTimKienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Tab3txtTimKienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_Tab3txtTimKienActionPerformed

    private void jLabel46MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel46MouseClicked
        timKiemHDTab3();
    }//GEN-LAST:event_jLabel46MouseClicked

    private void tblHDBanLeMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDBanLeMouseReleased
        if (evt.getClickCount() == 2) {
            filltableCTHDbanLe((String) tblHDBanLe.getValueAt(tblHDBanLe.getSelectedRow(), 0));
        }
    }//GEN-LAST:event_tblHDBanLeMouseReleased

    private void txtKhachTraKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtKhachTraKeyTyped
        if (evt.getKeyChar() == 10) {
            Double tralai = Double.parseDouble(txtKhachTra.getText()) - getThanhTien();
            if (tralai < 0) {
                MsgBox.alert(this, "Khách trả không đủ tiền");
                return;
            } else {
                txtTraLaiKhach.setText(DDTienTe.FormatVND(tralai));
            }

        }
    }//GEN-LAST:event_txtKhachTraKeyTyped

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        locHDTab3();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void Tab3cboTheoNgayItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_Tab3cboTheoNgayItemStateChanged
        if (Tab3cboTheoNgay.getSelectedIndex() == 0) {
            fillTableHDBanLe(null, null, null, XDate.toString(XDate.getDate(), "yyyy-MM-dd"));
        } else if (Tab3cboTheoNgay.getSelectedIndex() == 1) {
            fillTableHDBanLe(null, null, null, XDate.toString(XDate.addDays(XDate.getDate(), -1), "yyyy-MM-dd"));
        } else {
            fillTableHDBanLe(null, null, null, XDate.toString(XDate.addDays(XDate.getDate(), -2), "yyyy-MM-dd"));
        }
    }//GEN-LAST:event_Tab3cboTheoNgayItemStateChanged

    private void txtMaKHKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMaKHKeyTyped
        if(evt.getKeyChar()==10){
            timKhachHang();
        }
    }//GEN-LAST:event_txtMaKHKeyTyped

    private void btnThanhToanKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_btnThanhToanKeyTyped

    }//GEN-LAST:event_btnThanhToanKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> Tab3cboTheoNgay;
    private javax.swing.JTextField Tab3txtTimKien;
    private javax.swing.JButton btnApDungGG;
    private javax.swing.JButton btnHuyHD;
    private javax.swing.JButton btnHuyMagg;
    private javax.swing.JButton btnQuetBarcode;
    private javax.swing.JLabel btnSDDIem;
    private javax.swing.JButton btnTaoHD;
    private javax.swing.JButton btnThanhToan;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    public static javax.swing.JLabel lblMaHD;
    private com.toedter.calendar.JDateChooser tab3NgayBD;
    private com.toedter.calendar.JDateChooser tab3NgayKT;
    private javax.swing.JTable tblCTHDBanLe;
    private HELP.Table tblCTHoaDon;
    private javax.swing.JTable tblHDBanLe;
    public static HELP.Table tblSPDangBan;
    private javax.swing.JLabel txtDiemKH;
    private javax.swing.JLabel txtDiemSD;
    private javax.swing.JTextField txtKhachTra;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JLabel txtNgayHienHanh;
    private javax.swing.JLabel txtSDT;
    private javax.swing.JLabel txtSoTienGiam;
    private javax.swing.JLabel txtTenKH;
    private javax.swing.JLabel txtThanhTien;
    private javax.swing.JTextField txtTimMaSP;
    private javax.swing.JLabel txtTongCong;
    private javax.swing.JTextField txtTraLaiKhach;
    private javax.swing.JTextField txtaddMaGG;
    // End of variables declaration//GEN-END:variables
}
