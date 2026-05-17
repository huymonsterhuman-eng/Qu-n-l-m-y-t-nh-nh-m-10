/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package view;

import controller.ConvertDate;
import controller.SearchAccount;
import DAO.DaoAccount;
import DAO.Daomaytinh;
import DAO.DaoNhaCungCap;
import DAO.DaoPhieuNhap;
import DAO.DaoPhieuXuat;
import DAO.ThongKeDAO;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicInternalFrameUI;
import javax.swing.table.DefaultTableModel;
import model.Account;
import model.Phieu;
import model.PhieuNhap;
import model.Phieuxuat;
import model.Thongke;

/**
 *
 * @author Robot
 */
public class ThongKeForm extends javax.swing.JInternalFrame {

    private DefaultTableModel tblModel;
    DecimalFormat formatter = new DecimalFormat("###,###,###");
    SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/YYYY HH:mm");

    public DecimalFormat getFormatter() {
        return formatter;
    }

    public SimpleDateFormat getFormatDate() {
        return formatDate;
    }

    public ThongKeForm() {
        initComponents();
        BasicInternalFrameUI ui = (BasicInternalFrameUI) this.getUI();
        ui.setNorthPane(null);
        jDateChooserFrom.setDateFormatString("dd/MM/yyyy");
        jDateChooserTo.setDateFormatString("dd/MM/yyyy");
        jDateChooserFromPr.setDateFormatString("dd/MM/yyyy");
        jDateChooserToPr.setDateFormatString("dd/MM/yyyy");
        txtQuantityProduct.setText(Integer.toString(Daomaytinh.getInstance().selectAllExist().size()));
        txtQuantityNcc.setText(Integer.toString(DaoNhaCungCap.getInstance().selectAll().size()));
        txtQuantityUser.setText(Integer.toString(DaoAccount.getInstance().selectAll().size()));
        //
        initTable();
        loadDataToTable();
        changeTextFind();
        //
        loadDataToTableAcc(DaoAccount.getInstance().selectAll());
        tblAccount.setDefaultEditor(Object.class, null);
        tblThongKeProduct.setDefaultEditor(Object.class, null);
        tblPhieuNhap.setDefaultEditor(Object.class, null);
        //
        setupProductTabLayout();
        loadDataToTableThongKeProduct(ThongKeDAO.getInstance().getThongKe());
        initChartPanel();
        addExportButtons();
        addDoanhSoPanel();
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                resizeContent();
            }
        });
    }

    private void resizeContent() {
        int w = getContentPane().getWidth();
        int h = getContentPane().getHeight();
        if (w < 100 || h < 100) return;

        // Header luôn 130px, theo chiều rộng form
        jPanel8.setBounds(0, 0, w, 130);

        // Tab panel lấp đầy phần còn lại
        int tabH = h - 130;
        jTabbedPane1.setBounds(0, 130, w, tabH);

        // Bảng sản phẩm: trừ tab header (~30px) + filter row (~86px) + các gap (~50px)
        int tableH = tabH - 166;
        if (tableH > 100) {
            jScrollPane4.setPreferredSize(
                new java.awt.Dimension(jScrollPane4.getPreferredSize().width, tableH));
            jPanel15.revalidate();
        }
        adjustColumnWidths();
    }

    public final void initTable() {
        tblModel = new DefaultTableModel();
        String[] headerTbl = new String[]{"Mã phiếu nhập", "Người tạo", "Thời gian tạo", "Tổng tiền"};
        tblModel.setColumnIdentifiers(headerTbl);
        tblPhieuNhap.setModel(tblModel);
        tblPhieuNhap.getColumnModel().getColumn(0).setPreferredWidth(5);
    }

    private void loadDataToTable() {
        try {
            ArrayList<PhieuNhap> allPhieu = DaoPhieuNhap.getInstance().selectAll();
            tblModel.setRowCount(0);
            for (int i = 0; i < allPhieu.size(); i++) {
                tblModel.addRow(new Object[]{
                    allPhieu.get(i).getMaPhieu(), allPhieu.get(i).getNguoiTao(), formatDate.format(allPhieu.get(i).getThoiGianTao()), formatter.format(allPhieu.get(i).getTongTien()) + "đ"
                });
            }
        } catch (Exception e) {
        }
    }

    private void loadDataToTableSearch(ArrayList<Phieu> result) {
        try {
            tblModel.setRowCount(0);
            for (int i = 0; i < result.size(); i++) {
                tblModel.addRow(new Object[]{
                    result.get(i).getMaPhieu(), result.get(i).getNguoiTao(), formatDate.format(result.get(i).getThoiGianTao()), formatter.format(result.get(i).getTongTien()) + "đ"
                });
            }
        } catch (Exception e) {
        }
    }

    public ArrayList<Phieu> searchTatCa(String text) {
        ArrayList<Phieu> result = new ArrayList<>();
        ArrayList<Phieu> nhap = new ArrayList<>(DaoPhieuNhap.getInstance().selectAll());
        ArrayList<Phieu> xuat = new ArrayList<>(DaoPhieuXuat.getInstance().selectAll());
        for (var phieu : nhap) {
            if (phieu.getMaPhieu().toLowerCase().contains(text.toLowerCase())
                    || phieu.getNguoiTao().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }
        }
        for (var phieu : xuat) {
            if (phieu.getMaPhieu().toLowerCase().contains(text.toLowerCase())
                    || phieu.getNguoiTao().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }
        }
        return result;
    }

    public ArrayList<Phieu> searchPhieuNhap(String text) {
        ArrayList<Phieu> result = new ArrayList<>();
        ArrayList<PhieuNhap> armt = DaoPhieuNhap.getInstance().selectAll();
        for (var phieu : armt) {
            if (phieu.getMaPhieu().toLowerCase().contains(text.toLowerCase())
                    || phieu.getNguoiTao().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }
        }
        return result;
    }

    public ArrayList<Phieu> searchPhieuXuat(String text) {
        ArrayList<Phieu> result = new ArrayList<>();
        ArrayList<Phieuxuat> armt = DaoPhieuXuat.getInstance().selectAll();
        for (var phieu : armt) {
            if (phieu.getMaPhieu().toLowerCase().contains(text.toLowerCase())
                    || phieu.getNguoiTao().toLowerCase().contains(text.toLowerCase())) {
                result.add(phieu);
            }
        }
        return result;
    }

    public void changeTextFind() {
        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                /* do nothing */
                if (jTextFieldSearch.getText().length() == 0) {
                    loadDataToTable();
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {

            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel12 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        txtNamePr = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblThongKeProduct = new javax.swing.JTable();
        jPanel13 = new javax.swing.JPanel();
        jDateChooserFromPr = new com.toedter.calendar.JDateChooser();
        jDateChooserToPr = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnResetThongKePr = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        btnDetail = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jComboBoxLuaChon = new javax.swing.JComboBox<>();
        jTextFieldSearch = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPhieuNhap = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jDateChooserFrom = new com.toedter.calendar.JDateChooser();
        jDateChooserTo = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        giaTu = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        giaDen = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        soLuong = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        tongTien = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jComboBoxLuaChon1 = new javax.swing.JComboBox<>();
        jTextFieldSearch1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAccount = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        txtQuantityProduct = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txtQuantityNcc = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txtQuantityUser = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setBorder(null);
        setPreferredSize(new java.awt.Dimension(1180, 770));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1180, 770));

        jPanel15.setBackground(new java.awt.Color(255, 255, 255));

        jPanel16.setBackground(new java.awt.Color(255, 255, 255));
        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        jPanel16.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtNamePr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNamePrKeyReleased(evt);
            }
        });
        jPanel16.add(txtNamePr, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 350, 40));

        tblThongKeProduct.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã máy", "Tên máy", "Số lượng nhập", "Số lượng xuất"
            }
        ));
        tblThongKeProduct.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThongKeProductMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblThongKeProduct);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));
        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo ngày"));
        jPanel13.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDateChooserFromPr.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserFromPr.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserFromPrPropertyChange(evt);
            }
        });
        jDateChooserFromPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooserFromPrKeyReleased(evt);
            }
        });
        jPanel13.add(jDateChooserFromPr, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, 170, -1));

        jDateChooserToPr.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserToPr.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserToPrPropertyChange(evt);
            }
        });
        jDateChooserToPr.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooserToPrKeyReleased(evt);
            }
        });
        jPanel13.add(jDateChooserToPr, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 40, 170, -1));

        jLabel6.setText("Đến");
        jPanel13.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 40, 40, 20));

        jLabel8.setText("Từ");
        jPanel13.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 20, 20));

        btnResetThongKePr.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        btnResetThongKePr.setText("Làm mới");
        btnResetThongKePr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetThongKePrActionPerformed(evt);
            }
        });
        jPanel13.add(btnResetThongKePr, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 30, 140, 40));

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 1162, Short.MAX_VALUE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 463, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(53, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(510, 510, 510))
        );

        jTabbedPane1.addTab("Sản phẩm", jPanel12);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jToolBar1.setBackground(new java.awt.Color(255, 255, 255));
        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder("Chức năng"));
        jToolBar1.setRollover(true);

        btnDetail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_eye_40px.png"))); // NOI18N
        btnDetail.setText("Xem chi tiết");
        btnDetail.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDetail.setFocusable(false);
        btnDetail.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDetail.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDetail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDetailActionPerformed(evt);
            }
        });
        jToolBar1.add(btnDetail);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxLuaChon.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "Phiếu Nhập", "Phiếu Xuất" }));
        jComboBoxLuaChon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLuaChonActionPerformed(evt);
            }
        });
        jComboBoxLuaChon.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBoxLuaChonPropertyChange(evt);
            }
        });
        jPanel3.add(jComboBoxLuaChon, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 210, 40));

        jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearchKeyReleased(evt);
            }
        });
        jPanel3.add(jTextFieldSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 310, 40));

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        jButton7.setText("Làm mới");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 30, 140, 40));

        tblPhieuNhap.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblPhieuNhap);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo ngày"));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDateChooserFrom.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserFrom.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserFromPropertyChange(evt);
            }
        });
        jDateChooserFrom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooserFromKeyReleased(evt);
            }
        });
        jPanel4.add(jDateChooserFrom, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, 170, -1));

        jDateChooserTo.setBackground(new java.awt.Color(255, 255, 255));
        jDateChooserTo.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooserToPropertyChange(evt);
            }
        });
        jDateChooserTo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jDateChooserToKeyReleased(evt);
            }
        });
        jPanel4.add(jDateChooserTo, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 30, 170, -1));

        jLabel1.setText("Đến");
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 30, 40, 20));

        jLabel5.setText("Từ");
        jPanel4.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 20, 20));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo giá"));

        jLabel3.setText("Từ");

        giaTu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giaTuActionPerformed(evt);
            }
        });
        giaTu.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                giaTuKeyReleased(evt);
            }
        });

        jLabel4.setText("Đến");

        giaDen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                giaDenActionPerformed(evt);
            }
        });
        giaDen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                giaDenKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(giaTu, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jLabel4)
                .addGap(28, 28, 28)
                .addComponent(giaDen, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(giaTu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(giaDen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel2.setText("TỔNG TIỀN");

        soLuong.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        soLuong.setText("0");

        jLabel7.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        jLabel7.setText("TỔNG PHIẾU ");

        tongTien.setFont(new java.awt.Font("SF Pro Display", 1, 18)); // NOI18N
        tongTien.setForeground(new java.awt.Color(255, 0, 51));
        tongTien.setText("0");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(soLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(13, 13, 13)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                    .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(203, 203, 203))
        );

        jTabbedPane1.addTab("Phiếu", jPanel1);

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jComboBoxLuaChon1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tất cả", "FullName", "UserName", "Role" }));
        jComboBoxLuaChon1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxLuaChon1ActionPerformed(evt);
            }
        });
        jComboBoxLuaChon1.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jComboBoxLuaChon1PropertyChange(evt);
            }
        });
        jPanel7.add(jComboBoxLuaChon1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 210, 40));

        jTextFieldSearch1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldSearch1KeyReleased(evt);
            }
        });
        jPanel7.add(jTextFieldSearch1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 30, 320, 40));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8_reset_25px_1.png"))); // NOI18N
        jButton1.setText("Làm mới");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 30, 160, 40));

        tblAccount.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null}
            },
            new String [] {
                "Họ và tên", "Email", "Tên người dùng", "Vai trò", "Tình trạng"
            }
        ));
        tblAccount.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAccountMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblAccount);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1163, Short.MAX_VALUE))
                .addContainerGap(11, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(149, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(510, 510, 510))
        );

        jTabbedPane1.addTab("Tài khoản", jPanel5);

        getContentPane().add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 130, -1, 620));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel9.setBackground(new java.awt.Color(255, 204, 0));

        txtQuantityProduct.setFont(new java.awt.Font("SF Pro Display", 1, 36)); // NOI18N
        txtQuantityProduct.setForeground(new java.awt.Color(255, 255, 255));
        txtQuantityProduct.setText("100");

        jLabel10.setFont(new java.awt.Font("SF Pro Display", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Sản phẩm trong kho");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-monitor-80.png"))); // NOI18N

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtQuantityProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(73, 73, 73))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(12, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel12)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txtQuantityProduct, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)))
                .addGap(10, 10, 10))
        );

        jPanel10.setBackground(new java.awt.Color(255, 102, 0));

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-supplier-80.png"))); // NOI18N

        txtQuantityNcc.setFont(new java.awt.Font("SF Pro Display", 1, 36)); // NOI18N
        txtQuantityNcc.setForeground(new java.awt.Color(255, 255, 255));
        txtQuantityNcc.setText("100");

        jLabel14.setFont(new java.awt.Font("SF Pro Display", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Nhà cung cấp ");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel13)
                .addGap(43, 43, 43)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtQuantityNcc, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txtQuantityNcc, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)))
                .addGap(10, 10, 10))
        );

        jPanel11.setBackground(new java.awt.Color(0, 204, 204));

        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icons8-account-80.png"))); // NOI18N

        txtQuantityUser.setFont(new java.awt.Font("SF Pro Display", 1, 36)); // NOI18N
        txtQuantityUser.setForeground(new java.awt.Color(255, 255, 255));
        txtQuantityUser.setText("100");

        jLabel17.setFont(new java.awt.Font("SF Pro Display", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Tài khoản người dùng");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtQuantityUser, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addGap(73, 73, 73))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel11Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel15)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(txtQuantityUser, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel17)))
                .addGap(10, 10, 10))
        );

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1190, 130));

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        loadDataToTable();
        jComboBoxLuaChon.setSelectedIndex(0);
        jTextFieldSearch.setText("");
        jDateChooserFrom.setCalendar(null);
        jDateChooserTo.setCalendar(null);
        giaDen.setText("");
        giaTu.setText("");
    }//GEN-LAST:event_jButton7ActionPerformed

    private void giaDenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giaDenActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_giaDenActionPerformed

    private void jComboBoxLuaChonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLuaChonActionPerformed
        try {
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_jComboBoxLuaChonActionPerformed

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jTextFieldSearchKeyReleased

    private void giaTuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_giaTuActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_giaTuActionPerformed

    private void giaTuKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giaTuKeyReleased
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_giaTuKeyReleased

    private void jDateChooserFromKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooserFromKeyReleased
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jDateChooserFromKeyReleased

    private void jDateChooserToKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooserToKeyReleased
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jDateChooserToKeyReleased

    private void giaDenKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_giaDenKeyReleased
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_giaDenKeyReleased

    private void jDateChooserFromPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserFromPropertyChange
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jDateChooserFromPropertyChange

    private void jDateChooserToPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserToPropertyChange
        try {
            // TODO add your handling code here:
            searchAllRepect();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jDateChooserToPropertyChange

    private void btnDetailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDetailActionPerformed
        // TODO add your handling code here:
        if (tblPhieuNhap.getSelectedRow() == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu !");
        } else {
            CTThongKe a = new CTThongKe(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
            a.setVisible(true);
        }
    }//GEN-LAST:event_btnDetailActionPerformed

    private void jComboBoxLuaChonPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBoxLuaChonPropertyChange


    }//GEN-LAST:event_jComboBoxLuaChonPropertyChange

    private void jComboBoxLuaChon1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxLuaChon1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxLuaChon1ActionPerformed

    private void jComboBoxLuaChon1PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jComboBoxLuaChon1PropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxLuaChon1PropertyChange

    private void jTextFieldSearch1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearch1KeyReleased
        // TODO add your handling code here:
        String luachon = (String) jComboBoxLuaChon1.getSelectedItem();
        String searchContent = jTextFieldSearch1.getText();
        ArrayList<Account> allAccounts = DaoAccount.getInstance().selectAll();
        ArrayList<Account> result;
        // ComboBox dùng tên tiếng Anh, map sang field của SearchAccount
        if ("Role".equals(luachon)) {
            result = new ArrayList<>();
            for (Account a : allAccounts) {
                if (a.getRole() != null && a.getRole().toLowerCase().contains(searchContent.toLowerCase())) {
                    result.add(a);
                }
            }
        } else {
            String field;
            switch (luachon) {
                case "FullName":  field = "Họ tên";        break;
                case "UserName":  field = "Tên đăng nhập"; break;
                default:          field = "Tất cả";         break;
            }
            result = SearchAccount.search(allAccounts, searchContent, field);
        }
        loadDataToTableAcc(result);
    }//GEN-LAST:event_jTextFieldSearch1KeyReleased

    private void tblAccountMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAccountMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            DefaultTableModel tblAcc = (DefaultTableModel) tblAccount.getModel();
            if (tblAccount.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản");
            } else {
                CTThongKeAcc tk;
                tk = new CTThongKeAcc(this, (JFrame) javax.swing.SwingUtilities.getWindowAncestor(this), rootPaneCheckingEnabled);
                tk.setVisible(true);
            }
        }
    }//GEN-LAST:event_tblAccountMouseClicked

    private void txtNamePrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNamePrKeyReleased
        try {
            // TODO add your handling code here:
            filterThongKeSanPham();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_txtNamePrKeyReleased

    private void tblThongKeProductMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThongKeProductMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_tblThongKeProductMouseClicked

    private void jDateChooserFromPrPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserFromPrPropertyChange
        try {
            // TODO add your handling code here:
            filterThongKeSanPham();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jDateChooserFromPrPropertyChange

    private void jDateChooserFromPrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooserFromPrKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jDateChooserFromPrKeyReleased

    private void jDateChooserToPrPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooserToPrPropertyChange
        try {
            // TODO add your handling code here:
            filterThongKeSanPham();
        } catch (ParseException ex) {
            Logger.getLogger(ThongKeForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jDateChooserToPrPropertyChange

    private void jDateChooserToPrKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jDateChooserToPrKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jDateChooserToPrKeyReleased

    private void btnResetThongKePrActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetThongKePrActionPerformed
        // TODO add your handling code here:
        txtNamePr.setText("");
        jDateChooserFromPr.setCalendar(null);
        jDateChooserToPr.setCalendar(null);
        loadDataToTableThongKeProduct(ThongKeDAO.getInstance().getThongKe());
    }//GEN-LAST:event_btnResetThongKePrActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jComboBoxLuaChon1.setSelectedIndex(0);
        jTextFieldSearch1.setText("");
        loadDataToTableAcc(DaoAccount.getInstance().selectAll());
    }//GEN-LAST:event_jButton1ActionPerformed

    public Phieu getPhieuNhapSelect() {
        int i_row = tblPhieuNhap.getSelectedRow();
        Phieu pn = DaoPhieuNhap.getInstance().selectById(tblModel.getValueAt(i_row, 0).toString());
        return pn;
    }

    public Phieu findPhieu() {
        int i_row = tblPhieuNhap.getSelectedRow();
        String text = tblModel.getValueAt(i_row, 0).toString();
        for (Phieu phieu : DaoPhieuNhap.getInstance().selectAll()) {
            if (phieu.getMaPhieu().equals(text)) return phieu;
        }
        for (Phieu phieu : DaoPhieuXuat.getInstance().selectAll()) {
            if (phieu.getMaPhieu().equals(text)) return phieu;
        }
        return null;
    }

    public boolean checkDate(Date dateTest, Date star, Date end) {
        return dateTest.getTime() >= star.getTime() && dateTest.getTime() <= end.getTime();
    }

    public ArrayList<PhieuNhap> searchDate() {
        ArrayList<PhieuNhap> result = new ArrayList<PhieuNhap>();
        Date from = jDateChooserFrom.getDate();
        Date to = jDateChooserTo.getDate();
        ArrayList<PhieuNhap> armt = DaoPhieuNhap.getInstance().selectAll();
        for (var phieu : armt) {
            if (checkDate(phieu.getThoiGianTao(), from, to)) {
                result.add(phieu);
            }

        }
        return result;
    }

    public void loadDataToTableAcc(ArrayList<Account> acc) {
        try {
            DefaultTableModel tblModelAcc = (DefaultTableModel) tblAccount.getModel();
            tblModelAcc.setRowCount(0);
            for (Account i : acc) {
                tblModelAcc.addRow(new Object[]{
                    i.getFullname(), i.getEmail(), i.getUser(), i.getRole(), i.getStatus() == 1 ? "Hoạt động" : "Đã khóa"
                });
            }
        } catch (Exception e) {
        }
    }

    public String getMaAcc() {
        return tblAccount.getValueAt(tblAccount.getSelectedRow(), 2).toString();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnResetThongKePr;
    private javax.swing.JTextField giaDen;
    private javax.swing.JTextField giaTu;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBoxLuaChon;
    private javax.swing.JComboBox<String> jComboBoxLuaChon1;
    private com.toedter.calendar.JDateChooser jDateChooserFrom;
    private com.toedter.calendar.JDateChooser jDateChooserFromPr;
    private com.toedter.calendar.JDateChooser jDateChooserTo;
    private com.toedter.calendar.JDateChooser jDateChooserToPr;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextFieldSearch1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel soLuong;
    private javax.swing.JTable tblAccount;
    private javax.swing.JTable tblPhieuNhap;
    private javax.swing.JTable tblThongKeProduct;
    private javax.swing.JLabel tongTien;
    private javax.swing.JTextField txtNamePr;
    private javax.swing.JLabel txtQuantityNcc;
    private javax.swing.JLabel txtQuantityProduct;
    private javax.swing.JLabel txtQuantityUser;
    // End of variables declaration//GEN-END:variables

    private org.jfree.chart.JFreeChart barChart;
    private org.jfree.chart.JFreeChart lineChart;
    private com.toedter.calendar.JDateChooser jDateChooserChartFrom = new com.toedter.calendar.JDateChooser();
    private com.toedter.calendar.JDateChooser jDateChooserChartTo   = new com.toedter.calendar.JDateChooser();

    public void searchAllRepect() throws ParseException {
        String luaChon = jComboBoxLuaChon.getSelectedItem().toString();
        String content = jTextFieldSearch.getText();
        ArrayList<Phieu> result = null;

        switch (luaChon) {
            case "Tất cả":
                result = searchTatCa(content);
                break;
            case "Phiếu Nhập":
                result = searchPhieuNhap(content);
                break;
            case "Phiếu Xuất":
                result = searchPhieuXuat(content);
                break;
        }

        Iterator<Phieu> itr = result.iterator();
        if (jDateChooserFrom.getDate() != null || jDateChooserTo.getDate() != null) {
            Date from;
            Date to;
            if (jDateChooserFrom.getDate() != null && jDateChooserTo.getDate() == null) {
                from = ConvertDate.changeFrom(jDateChooserFrom.getDate());
                to = ConvertDate.changeTo(new Date());
                while (itr.hasNext()) {
                    Phieu phieu = itr.next();
                    if (!checkDate(phieu.getThoiGianTao(), from, to)) {
                        itr.remove();
                    }
                }
            } else if (jDateChooserTo.getDate() != null && jDateChooserFrom.getDate() == null) {
                String sDate1 = "01/01/2022";
                from = ConvertDate.changeFrom(new SimpleDateFormat("dd/MM/yyyy").parse(sDate1));
                to = ConvertDate.changeTo(jDateChooserTo.getDate());
                while (itr.hasNext()) {
                    Phieu phieu = itr.next();
                    if (!checkDate(phieu.getThoiGianTao(), from, to)) {
                        itr.remove();
                    }
                }
            } else {
                from = ConvertDate.changeFrom(jDateChooserFrom.getDate());
                to = ConvertDate.changeTo(jDateChooserTo.getDate());
                while (itr.hasNext()) {
                    Phieu phieu = itr.next();
                    if (!checkDate(phieu.getThoiGianTao(), from, to)) {
                        itr.remove();
                    }
                }
            }
        }

        ArrayList<Phieu> result1 = new ArrayList<>();
        if (giaTu.getText().length() > 0 || giaDen.getText().length() > 0) {
            double a;
            double b;

            if (giaTu.getText().length() > 0 && giaDen.getText().length() == 0) {
                a = Double.parseDouble(giaTu.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTongTien() >= a) {
                        result1.add(result.get(i));
                    }
                }
            } else if (giaTu.getText().length() == 0 && giaDen.getText().length() > 0) {;
                b = Double.parseDouble(giaDen.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTongTien() <= b) {
                        result1.add(result.get(i));
                    }
                }
            } else if (giaTu.getText().length() > 0 && giaDen.getText().length() > 0) {
                a = Double.parseDouble(giaTu.getText());
                b = Double.parseDouble(giaDen.getText());
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getTongTien() >= a && result.get(i).getTongTien() <= b) {
                        result1.add(result.get(i));
                    }
                }
            }
        }
        // Cập nhật nhãn theo loại phiếu đang xem
        if (luaChon.equals("Phiếu Nhập")) {
            jLabel2.setText("TỔNG TIỀN NHẬP");
        } else if (luaChon.equals("Phiếu Xuất")) {
            jLabel2.setText("TỔNG TIỀN XUẤT");
        } else {
            jLabel2.setText("TIỀN NHẬP | XUẤT");
        }

        ArrayList<Phieu> displayList = (giaTu.getText().length() > 0 || giaDen.getText().length() > 0) ? result1 : result;
        loadDataToTableSearch(displayList);
        soLuong.setText(displayList.size() + "");

        if (luaChon.equals("Tất cả")) {
            double sumNhap = 0, sumXuat = 0;
            for (Phieu p : displayList) {
                if (p instanceof PhieuNhap) sumNhap += p.getTongTien();
                else sumXuat += p.getTongTien();
            }
            tongTien.setText(formatter.format(sumNhap) + "đ | " + formatter.format(sumXuat) + "đ");
        } else {
            double sum = 0;
            for (Phieu p : displayList) sum += p.getTongTien();
            tongTien.setText(formatter.format(sum) + "đ");
        }
    }

    public void filterThongKeSanPham() throws ParseException {
        ArrayList<Thongke> thongKe = new ArrayList<>();
        if (jDateChooserFromPr.getDate() != null || jDateChooserToPr.getDate() != null) {
            Date from = new Date();
            Date to = new Date();
            if (jDateChooserFromPr.getDate() != null && jDateChooserToPr.getDate() == null) {
                from = ConvertDate.changeFrom(jDateChooserFromPr.getDate());
                to = ConvertDate.changeTo(new Date());
            } else if (jDateChooserToPr.getDate() != null && jDateChooserFromPr.getDate() == null) {
                String sDate1 = "01/01/2022";
                from = ConvertDate.changeFrom(new SimpleDateFormat("dd/MM/yyyy").parse(sDate1));
                to = ConvertDate.changeTo(jDateChooserToPr.getDate());
            } else {
                from = ConvertDate.changeFrom(jDateChooserFromPr.getDate());
                to = ConvertDate.changeTo(jDateChooserToPr.getDate());
                if (from.getTime() > to.getTime()) {
                    JOptionPane.showMessageDialog(this, "Thời gian không hợp lệ !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    jDateChooserFromPr.setCalendar(null);
                    jDateChooserToPr.setCalendar(null);
                }
            }
            thongKe = ThongKeDAO.getInstance().getThongKe(from, to);

        } else {
            thongKe = ThongKeDAO.getInstance().getThongKe();
        }
        if (!txtNamePr.getText().equals("")) {
            thongKe = searchTenSanPhamThongKe(thongKe, txtNamePr.getText());
        }
        loadDataToTableThongKeProduct(thongKe);
    }

    private void loadDataToTableThongKeProduct(ArrayList<Thongke> thongKe) {
        try {
            DefaultTableModel tblModelAcc = (DefaultTableModel) tblThongKeProduct.getModel();
            tblModelAcc.setColumnIdentifiers(new String[]{"STT", "Mã máy", "Tên máy", "Số lượng nhập", "Số lượng xuất", "Tồn kho"});
            tblModelAcc.setRowCount(0);
            for (int i = 0; i < thongKe.size(); i++) {
                int tonKho = thongKe.get(i).getSlNhap() - thongKe.get(i).getSlXuat();
                tblModelAcc.addRow(new Object[]{
                    (i + 1), thongKe.get(i).getMaMay(), thongKe.get(i).getTenMay(),
                    thongKe.get(i).getSlNhap(), thongKe.get(i).getSlXuat(), tonKho
                });
            }
            applyTonKhoRenderer();
            javax.swing.SwingUtilities.invokeLater(this::adjustColumnWidths);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyTonKhoRenderer() {
        javax.swing.table.TableCellRenderer renderer = new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    javax.swing.JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                java.awt.Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                if (!isSelected) {
                    Object tonKhoVal = table.getValueAt(row, 5);
                    if (tonKhoVal instanceof Integer) {
                        int tonKho = (Integer) tonKhoVal;
                        if (tonKho <= 0) {
                            c.setBackground(new java.awt.Color(255, 153, 153));
                        } else if (tonKho <= 5) {
                            c.setBackground(new java.awt.Color(255, 230, 128));
                        } else {
                            c.setBackground(java.awt.Color.WHITE);
                        }
                    } else {
                        c.setBackground(java.awt.Color.WHITE);
                    }
                }
                return c;
            }
        };
        for (int i = 0; i < tblThongKeProduct.getColumnCount(); i++) {
            tblThongKeProduct.getColumnModel().getColumn(i).setCellRenderer(renderer);
        }
    }

    private ArrayList<Thongke> searchTenSanPhamThongKe(ArrayList<Thongke> arr, String name) {
        ArrayList<Thongke> result = new ArrayList<>();
        for (Thongke i : arr) {
            if (i.getMaMay().toLowerCase().contains(name.toLowerCase()) || i.getTenMay().toLowerCase().contains(name.toLowerCase())) {
                result.add(i);
            }
        }
        return result;
    }

    private void setupProductTabLayout() {
        // Fix jPanel16 (Tìm kiếm): thay AbsoluteLayout → BorderLayout để txtNamePr kéo giãn
        jPanel16.removeAll();
        jPanel16.setLayout(new java.awt.BorderLayout());
        jPanel16.setBorder(javax.swing.BorderFactory.createCompoundBorder(
                javax.swing.BorderFactory.createTitledBorder("Tìm kiếm"),
                javax.swing.BorderFactory.createEmptyBorder(2, 6, 6, 6)));
        jPanel16.add(txtNamePr, java.awt.BorderLayout.CENTER);

        // Fix jPanel13 (Lọc theo ngày): thay AbsoluteLayout → FlowLayout để các component xếp ngang linh hoạt
        jPanel13.removeAll();
        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 6));
        jDateChooserFromPr.setPreferredSize(new java.awt.Dimension(170, 30));
        jDateChooserToPr.setPreferredSize(new java.awt.Dimension(170, 30));
        jPanel13.add(jLabel8);
        jPanel13.add(jDateChooserFromPr);
        jPanel13.add(jLabel6);
        jPanel13.add(jDateChooserToPr);
        jPanel13.add(btnResetThongKePr);

        // Filter row: [jPanel16 Tìm kiếm WEST] [jPanel13 Lọc ngày CENTER]
        javax.swing.JPanel filterRow = new javax.swing.JPanel(new java.awt.BorderLayout(8, 0));
        filterRow.setBackground(java.awt.Color.WHITE);
        filterRow.add(jPanel16, java.awt.BorderLayout.WEST);
        filterRow.add(jPanel13, java.awt.BorderLayout.CENTER);

        // Export row: nút Xuất Excel căn phải
        javax.swing.JButton btnExportSP = new javax.swing.JButton("Xuất Excel");
        btnExportSP.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportSP.addActionListener(e -> exportTableToExcel(tblThongKeProduct, "ThongKeSanPham"));
        javax.swing.JPanel exportRow = new javax.swing.JPanel(
                new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 6, 2));
        exportRow.setBackground(java.awt.Color.WHITE);
        exportRow.add(btnExportSP);

        // NORTH: filterRow + exportRow
        javax.swing.JPanel northPanel = new javax.swing.JPanel(new java.awt.BorderLayout(0, 4));
        northPanel.setBackground(java.awt.Color.WHITE);
        northPanel.add(filterRow, java.awt.BorderLayout.CENTER);
        northPanel.add(exportRow, java.awt.BorderLayout.SOUTH);

        // jPanel15 đổi sang BorderLayout: northPanel ở NORTH, jScrollPane4 lấp đầy CENTER
        jPanel15.setLayout(new java.awt.BorderLayout(0, 6));
        jPanel15.setBorder(javax.swing.BorderFactory.createEmptyBorder(6, 6, 6, 6));
        jPanel15.add(northPanel, java.awt.BorderLayout.NORTH);
        jPanel15.add(jScrollPane4, java.awt.BorderLayout.CENTER);
    }

    private void adjustColumnWidths() {
        int viewportW = jScrollPane4.getViewport().getWidth();
        if (viewportW <= 0) return;
        int fixed = 50 + 100 + 130 + 130 + 90; // STT + Mã máy + Nhập + Xuất + Tồn kho
        int tenMayW = Math.max(180, viewportW - fixed);
        tblThongKeProduct.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        javax.swing.table.TableColumnModel cm = tblThongKeProduct.getColumnModel();
        cm.getColumn(0).setPreferredWidth(50);
        cm.getColumn(1).setPreferredWidth(100);
        cm.getColumn(2).setPreferredWidth(tenMayW);
        cm.getColumn(3).setPreferredWidth(130);
        cm.getColumn(4).setPreferredWidth(130);
        cm.getColumn(5).setPreferredWidth(90);
    }

    private void addDoanhSoPanel() {
        // Tính tổng tiền phiếu xuất trong tháng hiện tại
        java.util.Calendar cal = java.util.Calendar.getInstance();
        int thang = cal.get(java.util.Calendar.MONTH);
        int nam = cal.get(java.util.Calendar.YEAR);
        double tongXuatThang = 0;
        for (Phieuxuat p : DaoPhieuXuat.getInstance().selectAll()) {
            java.util.Calendar pCal = java.util.Calendar.getInstance();
            pCal.setTimeInMillis(p.getThoiGianTao().getTime());
            if (pCal.get(java.util.Calendar.MONTH) == thang
                    && pCal.get(java.util.Calendar.YEAR) == nam) {
                tongXuatThang += p.getTongTien();
            }
        }

        // Panel thứ 4 — màu xanh lá
        javax.swing.JPanel panel4 = new javax.swing.JPanel(new java.awt.BorderLayout(0, 2));
        panel4.setBackground(new java.awt.Color(0x27, 0xAE, 0x60));
        panel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 20, 12, 20));

        javax.swing.JLabel lblTitle = new javax.swing.JLabel(
                "Doanh thu tháng " + (thang + 1) + "/" + nam);
        lblTitle.setForeground(java.awt.Color.WHITE);
        lblTitle.setFont(new java.awt.Font("SF Pro Display", java.awt.Font.PLAIN, 15));

        javax.swing.JLabel lblAmount = new javax.swing.JLabel(
                formatter.format(tongXuatThang) + "đ");
        lblAmount.setForeground(java.awt.Color.WHITE);
        lblAmount.setFont(new java.awt.Font("SF Pro Display", java.awt.Font.BOLD, 26));

        javax.swing.JLabel lblSub = new javax.swing.JLabel("(Tổng phiếu xuất)");
        lblSub.setForeground(new java.awt.Color(180, 255, 180));
        lblSub.setFont(new java.awt.Font("SF Pro Display", java.awt.Font.PLAIN, 12));

        javax.swing.JPanel textPanel = new javax.swing.JPanel(new java.awt.GridLayout(3, 1, 0, 2));
        textPanel.setOpaque(false);
        textPanel.add(lblTitle);
        textPanel.add(lblAmount);
        textPanel.add(lblSub);

        panel4.add(textPanel, java.awt.BorderLayout.CENTER);

        // Đổi layout jPanel8 sang GridLayout(1,4) — 3 panel cũ giữ nguyên thứ tự
        jPanel8.setLayout(new java.awt.GridLayout(1, 4, 8, 0));
        jPanel8.add(panel4);
        jPanel8.revalidate();
        jPanel8.repaint();
    }

    private void addExportButtons() {
        // Nút Xuất Excel cho tab Tài khoản (đặt sau jButton1 Làm mới tại 620,30)
        javax.swing.JButton btnExportAcc = new javax.swing.JButton("Xuất Excel");
        btnExportAcc.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExportAcc.addActionListener(e -> exportTableToExcel(tblAccount, "ThongKeTaiKhoan"));
        jPanel7.add(btnExportAcc, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 30, 130, 40));
    }

    private void exportTableToExcel(javax.swing.JTable table, String defaultName) {
        try {
            javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
            chooser.setSelectedFile(new java.io.File(defaultName));
            if (chooser.showSaveDialog(this) != javax.swing.JFileChooser.APPROVE_OPTION) return;

            java.io.File saveFile = new java.io.File(chooser.getSelectedFile().toString() + ".xlsx");
            org.apache.poi.xssf.usermodel.XSSFWorkbook wb = new org.apache.poi.xssf.usermodel.XSSFWorkbook();
            org.apache.poi.ss.usermodel.Sheet sheet = wb.createSheet("Data");

            org.apache.poi.ss.usermodel.Row headerRow = sheet.createRow(0);
            for (int i = 0; i < table.getColumnCount(); i++) {
                headerRow.createCell(i).setCellValue(table.getColumnName(i));
            }
            for (int r = 0; r < table.getRowCount(); r++) {
                org.apache.poi.ss.usermodel.Row row = sheet.createRow(r + 1);
                for (int c = 0; c < table.getColumnCount(); c++) {
                    Object val = table.getValueAt(r, c);
                    row.createCell(c).setCellValue(val != null ? val.toString() : "");
                }
            }

            try (java.io.FileOutputStream out = new java.io.FileOutputStream(saveFile)) {
                wb.write(out);
            }
            wb.close();
            JOptionPane.showMessageDialog(this, "Xuất Excel thành công!");
            java.awt.Desktop.getDesktop().open(saveFile);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Xuất Excel thất bại: " + ex.getMessage(),
                "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initChartPanel() {
        jDateChooserChartFrom.setDateFormatString("dd/MM/yyyy");
        jDateChooserChartTo.setDateFormatString("dd/MM/yyyy");
        jDateChooserChartFrom.setPreferredSize(new java.awt.Dimension(150, 30));
        jDateChooserChartTo.setPreferredSize(new java.awt.Dimension(150, 30));
        jDateChooserChartFrom.addPropertyChangeListener("date", e -> refreshCharts());
        jDateChooserChartTo.addPropertyChangeListener("date", e -> refreshCharts());

        javax.swing.JButton btnResetChart = new javax.swing.JButton("Làm mới");
        btnResetChart.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnResetChart.addActionListener(e -> {
            jDateChooserChartFrom.setCalendar(null);
            jDateChooserChartTo.setCalendar(null);
        });

        javax.swing.JPanel filterRow = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 6));
        filterRow.setBackground(java.awt.Color.WHITE);
        filterRow.setBorder(javax.swing.BorderFactory.createTitledBorder("Lọc theo thời gian"));
        filterRow.add(new javax.swing.JLabel("Từ:"));
        filterRow.add(jDateChooserChartFrom);
        filterRow.add(new javax.swing.JLabel("Đến:"));
        filterRow.add(jDateChooserChartTo);
        filterRow.add(btnResetChart);

        javax.swing.JPanel chartRow = new javax.swing.JPanel(new java.awt.GridLayout(1, 2, 12, 0));
        chartRow.setBackground(java.awt.Color.WHITE);
        chartRow.add(createBarChartPanel());
        chartRow.add(createLineChartPanel());

        javax.swing.JPanel chartTab = new javax.swing.JPanel(new java.awt.BorderLayout(0, 8));
        chartTab.setBackground(java.awt.Color.WHITE);
        chartTab.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));
        chartTab.add(filterRow, java.awt.BorderLayout.NORTH);
        chartTab.add(chartRow, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Biểu đồ", chartTab);
    }

    private org.jfree.data.category.DefaultCategoryDataset buildBarDataset(java.util.Date from, java.util.Date to) {
        ArrayList<Thongke> data = (from != null && to != null)
            ? ThongKeDAO.getInstance().getThongKe(from, to)
            : ThongKeDAO.getInstance().getThongKe();
        data.sort((a, b) -> (b.getSlNhap() + b.getSlXuat()) - (a.getSlNhap() + a.getSlXuat()));
        org.jfree.data.category.DefaultCategoryDataset dataset = new org.jfree.data.category.DefaultCategoryDataset();
        int limit = Math.min(10, data.size());
        for (int i = 0; i < limit; i++) {
            String name = data.get(i).getTenMay();
            if (name.length() > 12) name = name.substring(0, 12) + "…";
            dataset.addValue(data.get(i).getSlNhap(), "Nhập", name);
            dataset.addValue(data.get(i).getSlXuat(), "Xuất", name);
        }
        return dataset;
    }

    private org.jfree.chart.ChartPanel createBarChartPanel() {
        this.barChart = org.jfree.chart.ChartFactory.createBarChart(
            "Top 10 sản phẩm nhập/xuất", null, "Số lượng",
            buildBarDataset(null, null),
            org.jfree.chart.plot.PlotOrientation.VERTICAL, true, true, false);
        barChart.setBackgroundPaint(java.awt.Color.WHITE);
        org.jfree.chart.plot.CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(java.awt.Color.WHITE);
        plot.setRangeGridlinePaint(new java.awt.Color(220, 220, 220));
        plot.getDomainAxis().setCategoryLabelPositions(
            org.jfree.chart.axis.CategoryLabelPositions.UP_45);
        org.jfree.chart.renderer.category.BarRenderer renderer =
            (org.jfree.chart.renderer.category.BarRenderer) plot.getRenderer();
        renderer.setSeriesPaint(0, new java.awt.Color(0x44, 0x72, 0xC4));
        renderer.setSeriesPaint(1, new java.awt.Color(0xED, 0x7D, 0x31));
        renderer.setDrawBarOutline(false);
        renderer.setShadowVisible(false);
        return new org.jfree.chart.ChartPanel(barChart);
    }

    private org.jfree.data.category.DefaultCategoryDataset buildLineDataset(java.util.Date from, java.util.Date to) {
        org.jfree.data.category.DefaultCategoryDataset dataset = new org.jfree.data.category.DefaultCategoryDataset();
        java.text.SimpleDateFormat monthFmt = new java.text.SimpleDateFormat("MM/yyyy");
        java.util.Map<String, Integer> nhapByMonth = new java.util.TreeMap<>();
        java.util.Map<String, Integer> xuatByMonth = new java.util.TreeMap<>();
        for (PhieuNhap p : DaoPhieuNhap.getInstance().selectAll()) {
            if (from != null && to != null && !checkDate(p.getThoiGianTao(), from, to)) continue;
            nhapByMonth.merge(monthFmt.format(p.getThoiGianTao()), 1, Integer::sum);
        }
        for (Phieuxuat p : DaoPhieuXuat.getInstance().selectAll()) {
            if (from != null && to != null && !checkDate(p.getThoiGianTao(), from, to)) continue;
            xuatByMonth.merge(monthFmt.format(p.getThoiGianTao()), 1, Integer::sum);
        }
        java.util.Set<String> allMonths = new java.util.TreeSet<>();
        allMonths.addAll(nhapByMonth.keySet());
        allMonths.addAll(xuatByMonth.keySet());
        for (String month : allMonths) {
            dataset.addValue(nhapByMonth.getOrDefault(month, 0), "Phiếu nhập", month);
            dataset.addValue(xuatByMonth.getOrDefault(month, 0), "Phiếu xuất", month);
        }
        return dataset;
    }

    private org.jfree.chart.ChartPanel createLineChartPanel() {
        this.lineChart = org.jfree.chart.ChartFactory.createLineChart(
            "Số phiếu theo tháng", null, "Số phiếu",
            buildLineDataset(null, null),
            org.jfree.chart.plot.PlotOrientation.VERTICAL, true, true, false);
        lineChart.setBackgroundPaint(java.awt.Color.WHITE);
        org.jfree.chart.plot.CategoryPlot plot = lineChart.getCategoryPlot();
        plot.setBackgroundPaint(java.awt.Color.WHITE);
        plot.setRangeGridlinePaint(new java.awt.Color(220, 220, 220));
        plot.getDomainAxis().setCategoryLabelPositions(
            org.jfree.chart.axis.CategoryLabelPositions.UP_45);
        org.jfree.chart.renderer.category.LineAndShapeRenderer renderer =
            (org.jfree.chart.renderer.category.LineAndShapeRenderer) plot.getRenderer();
        renderer.setDefaultShapesVisible(true);
        renderer.setSeriesPaint(0, new java.awt.Color(0x44, 0x72, 0xC4));
        renderer.setSeriesPaint(1, new java.awt.Color(0xED, 0x7D, 0x31));
        return new org.jfree.chart.ChartPanel(lineChart);
    }

    private void refreshCharts() {
        try {
            java.util.Date from = null, to = null;
            if (jDateChooserChartFrom.getDate() != null && jDateChooserChartTo.getDate() != null) {
                from = ConvertDate.changeFrom(jDateChooserChartFrom.getDate());
                to   = ConvertDate.changeTo(jDateChooserChartTo.getDate());
                if (from.getTime() > to.getTime()) {
                    JOptionPane.showMessageDialog(this, "Thời gian không hợp lệ !", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                    jDateChooserChartFrom.setCalendar(null);
                    jDateChooserChartTo.setCalendar(null);
                    return;
                }
            } else if (jDateChooserChartFrom.getDate() != null) {
                from = ConvertDate.changeFrom(jDateChooserChartFrom.getDate());
                to   = ConvertDate.changeTo(new java.util.Date());
            } else if (jDateChooserChartTo.getDate() != null) {
                from = ConvertDate.changeFrom(new java.util.Date(0));
                to   = ConvertDate.changeTo(jDateChooserChartTo.getDate());
            }
            ((org.jfree.chart.plot.CategoryPlot) barChart.getPlot()).setDataset(buildBarDataset(from, to));
            ((org.jfree.chart.plot.CategoryPlot) lineChart.getPlot()).setDataset(buildLineDataset(from, to));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
