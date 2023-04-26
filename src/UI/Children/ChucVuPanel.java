/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package UI.Children;

import DAO.ChucVuDAO;
import Models.ChucVu;
import Utils.MsgBox;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author TIEN SY
 */
public class ChucVuPanel extends javax.swing.JPanel {

    /**
     * Creates new form ChucVuPanel
     */
    public ChucVuPanel() {
        initComponents();
        fillTableCV();
    }
    ChucVuDAO chucVuDAO = new ChucVuDAO();
    private void addCV() {
        if (chucVuDAO.selectbyID(txtMaCV.getText()) == null) {
            if (tblListCV.getRowCount() > 0) {
                for (int i = 0; i < tblListCV.getRowCount(); i++) {
                    if (txtTenCV.getText().equalsIgnoreCase((String) tblListCV.getValueAt(i, 1))) {
                        MsgBox.alert(this, "Tên Chức Vụ Này Đã Tồn Tại Trên Hệ Thống");
                        return;
                    }
                }
            }
            ChucVu chucVu = new ChucVu(txtMaCV.getText(), txtTenCV.getText());
            chucVuDAO.insert(chucVu);
            clearFormCV();
            MsgBox.alert(this, "Thêm Chức Vụ Thành Công");
            fillTableCV();
        } else {
            MsgBox.alert(this, "Mã Chức Vụ Này Đã Tồn Tại Trên Hệ Thống");
        }
    }

    private void deleteCV() {
        chucVuDAO.delete(txtMaCV.getText());
        fillTableCV();
        clearFormCV();
    }

    private void updateCV() {
        chucVuDAO.update(new ChucVu(txtMaCV.getText(), txtTenCV.getText()));
        MsgBox.alert(null, "Cập Nhật Thành Công");
        fillTableCV();
    }

    private boolean validateCV() {
        if (txtMaCV.getText().isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập Mã Chức Vụ");
            txtMaCV.requestFocus();
            return false;
        }
        if (txtTenCV.getText().isEmpty()) {
            MsgBox.alert(this, "Vui lòng nhập Tên Chức Vụ");
            txtTenCV.requestFocus();
            return false;
        }
        return true;
    }

    private void clearFormCV() {
        txtMaCV.setText(null);
        txtTenCV.setText(null);
        btnXoa.setEnabled(false);
        btnCapNhat.setEnabled(false);
        btnThem.setEnabled(true);
        txtMaCV.setEnabled(true);
        txtMaCV.requestFocus();
//        indexSelected = -1;
    }

    private void fillTableCV() {
        DefaultTableModel model = (DefaultTableModel) tblListCV.getModel();
        model.setRowCount(0);
        for (ChucVu x : chucVuDAO.selectAll()) {
            Object row[] = {x.getMaCV(), x.getTenCV()};
            model.addRow(row);
        }
    }

    private void showDetailCV(int slr) {
        ChucVu chucVu = chucVuDAO.selectbyID((String) tblListCV.getValueAt(slr, 0));
        txtMaCV.setText(chucVu.getMaCV());
        txtTenCV.setText(chucVu.getTenCV());
        btnXoa.setEnabled(true);
        btnCapNhat.setEnabled(true);
        btnThem.setEnabled(false);
        txtMaCV.setEnabled(false);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblMakho = new javax.swing.JLabel();
        txtTenCV = new SWING_OTHER.MyTextField();
        lblTenkho = new javax.swing.JLabel();
        txtMaCV = new SWING_OTHER.MyTextField();
        jPanel2 = new javax.swing.JPanel();
        btnXoa = new SWING_OTHER.Button();
        btnThem = new SWING_OTHER.Button();
        btnCapNhat = new SWING_OTHER.Button();
        btnNew = new SWING_OTHER.Button();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblListCV = new javax.swing.JTable();

        setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblMakho.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblMakho.setForeground(new java.awt.Color(0, 102, 102));
        lblMakho.setText("Mã Chức Vụ");

        lblTenkho.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        lblTenkho.setForeground(new java.awt.Color(0, 102, 102));
        lblTenkho.setText("Tên Chức Vụ");

        jPanel2.setLayout(new java.awt.GridLayout(2, 2, 5, 5));

        btnXoa.setBackground(new java.awt.Color(0, 102, 102));
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xóa");
        btnXoa.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        jPanel2.add(btnXoa);

        btnThem.setBackground(new java.awt.Color(0, 102, 102));
        btnThem.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        jPanel2.add(btnThem);

        btnCapNhat.setBackground(new java.awt.Color(0, 102, 102));
        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setText("Cập Nhật");
        btnCapNhat.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        jPanel2.add(btnCapNhat);

        btnNew.setBackground(new java.awt.Color(0, 102, 102));
        btnNew.setForeground(new java.awt.Color(255, 255, 255));
        btnNew.setText("Làm Mới");
        btnNew.setFont(new java.awt.Font("SansSerif", 1, 14)); // NOI18N
        btnNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewActionPerformed(evt);
            }
        });
        jPanel2.add(btnNew);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblTenkho)
                            .addComponent(lblMakho))
                        .addGap(55, 55, 55)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtMaCV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTenCV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(72, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtMaCV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMakho))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTenCV, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTenkho))
                .addGap(50, 50, 50)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(131, Short.MAX_VALUE))
        );

        add(jPanel1);

        tblListCV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Mã Chức Vụ", "Tên Chức Vụ"
            }
        ));
        tblListCV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblListCVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblListCV);

        add(jScrollPane1);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewActionPerformed
    clearFormCV();
    }//GEN-LAST:event_btnNewActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
    updateCV();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
    deleteCV();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
       addCV();
    }//GEN-LAST:event_btnThemActionPerformed

    private void tblListCVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblListCVMouseClicked
        showDetailCV(tblListCV.getSelectedRow());
    }//GEN-LAST:event_tblListCVMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private SWING_OTHER.Button btnCapNhat;
    private SWING_OTHER.Button btnNew;
    private SWING_OTHER.Button btnThem;
    private SWING_OTHER.Button btnXoa;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblMakho;
    private javax.swing.JLabel lblTenkho;
    private javax.swing.JTable tblListCV;
    private SWING_OTHER.MyTextField txtMaCV;
    private SWING_OTHER.MyTextField txtTenCV;
    // End of variables declaration//GEN-END:variables
}
