/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.ChiTietPhieu;

/**
 *
 * @author huymo
 */
public class DaoChiTietPhieuNhap implements DAOInterface<ChiTietPhieu> {
    
    public static DaoChiTietPhieuNhap getInstance(){
        return new DaoChiTietPhieuNhap();
    }

    @Override
    public int insert(ChiTietPhieu t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "INSERT INTO ChiTietPhieuNhap (maPhieu, maMay, soLuong, donGia) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setString(2, t.getMaMay());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            ketQua = pst.executeUpdate();
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally{
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int update(ChiTietPhieu t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "UPDATE ChiTietPhieuNhap SET maPhieu=?, maMay=?, soLuong=?, donGia = ?  WHERE maPhieu=? AND maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setString(2, t.getMaMay());
            pst.setInt(3, t.getSoLuong());
            pst.setDouble(4, t.getDonGia());
            pst.setString(5, t.getMaPhieu());
            pst.setString(6, t.getMaMay());
            ketQua = pst.executeUpdate();
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally{
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int delete(ChiTietPhieu t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "DELETE FROM ChiTietPhieuNhap WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            ketQua = pst.executeUpdate();
            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally{
            JDBCUtil.closeConnection(con);
        }
        return ketQua;    
    }

    @Override
    public ArrayList<ChiTietPhieu> selectAll() {
        ArrayList<ChiTietPhieu> ketQua = new ArrayList<ChiTietPhieu>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM ChiTietPhieuNhap";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                String maMay = rs.getString("maMay");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                ChiTietPhieu ctp = new ChiTietPhieu(maPhieu, maMay, soLuong, donGia);
                ketQua.add(ctp);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally{
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    public ArrayList<ChiTietPhieu> SelectBymaphieu (String id){
        
        ArrayList<ChiTietPhieu> ketqua = new ArrayList<>();
        Connection con = null;
        
        try{
            con = JDBCUtil.getConnection();
            String sql = "Select * from ChiTietPhieuNhap where maPhieu = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, id);
            ResultSet rs = pst.executeQuery();
            while(rs.next()){
                String maPhieu = rs.getString("maPhieu");
                String maMay = rs.getString("maMay");
                int soLuong = rs.getInt("soLuong");
                double donGia = rs.getDouble("donGia");
                ChiTietPhieu ctp = new ChiTietPhieu(maPhieu, maMay, soLuong, donGia);
                ketqua.add(ctp);
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally{
            JDBCUtil.closeConnection(con);
        }
        return ketqua;
    }

    @Override
    public ChiTietPhieu selectById(String t) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // Lấy danh sách lô nhập của 1 sản phẩm, sắp xếp theo ngày nhập tăng dần — dùng cho TonKhoForm
    public ArrayList<ChiTietPhieu> selectLoHangByMaMay(String maMay) {
        ArrayList<ChiTietPhieu> ketQua = new ArrayList<>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT ct.maPhieu, pn.thoiGianTao, ct.soLuong, ct.soLuongConLai " +
                         "FROM ChiTietPhieuNhap ct " +
                         "JOIN PhieuNhap pn ON ct.maPhieu = pn.maPhieu " +
                         "WHERE ct.maMay = ? AND pn.trangThai = 'Đã hoàn thành' " +
                         "ORDER BY pn.thoiGianTao ASC";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maMay);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                ChiTietPhieu ct = new ChiTietPhieu(
                    rs.getString("maPhieu"), maMay,
                    rs.getInt("soLuong"), 0
                );
                ct.setSoLuongConLai(rs.getInt("soLuongConLai"));
                // thoiGianTao được đặt vào donGia (dùng tạm làm timestamp ms) để truyền sang dialog
                // → thực ra dialog đọc thẳng từ ResultSet nên method riêng bên dưới mới dùng
                ketQua.add(ct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    // Lấy danh sách lô nhập kèm ngày nhập để hiển thị trong dialog — trả về Object[][] cho JTable
    public Object[][] selectLoHangTableData(String maMay) {
        ArrayList<Object[]> rows = new ArrayList<>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT ct.maPhieu, pn.thoiGianTao, ct.soLuong, ct.soLuongConLai " +
                         "FROM ChiTietPhieuNhap ct " +
                         "JOIN PhieuNhap pn ON ct.maPhieu = pn.maPhieu " +
                         "WHERE ct.maMay = ? AND pn.trangThai = 'Đã hoàn thành' " +
                         "ORDER BY pn.thoiGianTao ASC";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maMay);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                String ngayNhap = "DAU_KY".equals(maPhieu) ? "-"
                        : (rs.getTimestamp("thoiGianTao") != null
                           ? new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm")
                                .format(rs.getTimestamp("thoiGianTao"))
                           : "-");
                String tenPhieu = "DAU_KY".equals(maPhieu) ? "(Đầu kỳ)" : maPhieu;
                rows.add(new Object[]{tenPhieu, ngayNhap, rs.getInt("soLuong"), rs.getInt("soLuongConLai")});
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return rows.toArray(new Object[0][]);
    }

    // Kích hoạt lô khi hoàn thành phiếu nhập: soLuongConLai = soLuong
    public int activateLoHang(Connection con, String maPhieu) throws Exception {
        String sql = "UPDATE ChiTietPhieuNhap SET soLuongConLai = soLuong WHERE maPhieu = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, maPhieu);
        return pst.executeUpdate();
    }

    // Trừ FIFO: lấy từ lô cũ nhất trước, cập nhật soLuongConLai trong cùng transaction
    public void deductFIFO(Connection con, String maMay, int soLuongCanTru) throws Exception {
        String sqlSelect = "SELECT ct.maPhieu, ct.soLuongConLai " +
                           "FROM ChiTietPhieuNhap ct " +
                           "JOIN PhieuNhap pn ON ct.maPhieu = pn.maPhieu " +
                           "WHERE ct.maMay = ? AND ct.soLuongConLai > 0 " +
                           "ORDER BY pn.thoiGianTao ASC";
        String sqlUpdate = "UPDATE ChiTietPhieuNhap SET soLuongConLai = soLuongConLai - ? " +
                           "WHERE maPhieu = ? AND maMay = ?";
        PreparedStatement pstSelect = con.prepareStatement(sqlSelect);
        pstSelect.setString(1, maMay);
        ResultSet rs = pstSelect.executeQuery();

        int conLai = soLuongCanTru;
        while (rs.next() && conLai > 0) {
            int soLuongConLaiLo = rs.getInt("soLuongConLai");
            String maPhieuLo = rs.getString("maPhieu");
            int tru = Math.min(soLuongConLaiLo, conLai);
            PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate);
            pstUpdate.setInt(1, tru);
            pstUpdate.setString(2, maPhieuLo);
            pstUpdate.setString(3, maMay);
            pstUpdate.executeUpdate();
            conLai -= tru;
        }
    }


}
