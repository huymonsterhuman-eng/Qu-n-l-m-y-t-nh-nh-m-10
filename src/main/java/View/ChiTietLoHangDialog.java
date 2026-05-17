package view;

import DAO.DaoChiTietPhieuNhap;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Dialog hiển thị danh sách lô hàng của 1 sản phẩm trong TonKhoForm.
 * Cột: Mã phiếu nhập | Ngày nhập | SL gốc | Còn lại
 */
public class ChiTietLoHangDialog extends JDialog {

    private JTable tblLoHang;
    private DefaultTableModel tblModel;
    private JLabel lblTongConLai;
    private final String maMay;

    public ChiTietLoHangDialog(JFrame owner, String maMay, String tenMay) {
        super(owner, "Chi tiết lô hàng — " + tenMay + " (" + maMay + ")", true);
        this.maMay = maMay;
        initComponents();
        loadData();
        setLocationRelativeTo(owner);
    }

    private void initComponents() {
        setPreferredSize(new Dimension(700, 420));
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        getContentPane().setLayout(new BorderLayout(10, 10));

        // ── Tiêu đề ──────────────────────────────────────────────
        JLabel lblTitle = new JLabel("Danh sách lô hàng nhập kho");
        lblTitle.setFont(new Font("SF Pro Display", Font.BOLD, 16));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(12, 16, 4, 0));
        getContentPane().add(lblTitle, BorderLayout.NORTH);

        // ── Bảng lô hàng ─────────────────────────────────────────
        String[] cols = {"Mã phiếu nhập", "Ngày nhập", "SL gốc", "Còn lại"};
        tblModel = new DefaultTableModel(cols, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tblLoHang = new JTable(tblModel);
        tblLoHang.setRowHeight(26);
        tblLoHang.setFont(new Font("SF Pro Display", Font.PLAIN, 13));
        tblLoHang.getTableHeader().setFont(new Font("SF Pro Display", Font.BOLD, 13));
        tblLoHang.getTableHeader().setBackground(new Color(230, 230, 230));
        tblLoHang.setDefaultEditor(Object.class, null);

        tblLoHang.getColumnModel().getColumn(0).setPreferredWidth(180);
        tblLoHang.getColumnModel().getColumn(1).setPreferredWidth(160);
        tblLoHang.getColumnModel().getColumn(2).setPreferredWidth(80);
        tblLoHang.getColumnModel().getColumn(3).setPreferredWidth(80);

        JScrollPane scrollPane = new JScrollPane(tblLoHang);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // ── Thanh dưới: tổng còn lại + nút đóng ──────────────────
        JPanel pnBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 16, 10));
        pnBottom.setBackground(Color.WHITE);

        lblTongConLai = new JLabel("Tổng còn lại: 0");
        lblTongConLai.setFont(new Font("SF Pro Display", Font.BOLD, 13));
        pnBottom.add(lblTongConLai);

        JButton btnDong = new JButton("Đóng");
        btnDong.setPreferredSize(new Dimension(90, 32));
        btnDong.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDong.addActionListener(e -> dispose());
        pnBottom.add(btnDong);

        getContentPane().add(pnBottom, BorderLayout.SOUTH);

        pack();
    }

    private void loadData() {
        tblModel.setRowCount(0);
        Object[][] data = DaoChiTietPhieuNhap.getInstance().selectLoHangTableData(maMay);
        int tongConLai = 0;
        for (Object[] row : data) {
            tblModel.addRow(row);
            tongConLai += (int) row[3]; // cột "Còn lại"
        }
        lblTongConLai.setText("Tổng còn lại: " + tongConLai);
    }
}
