
package DAO;

import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import model.PhieuNhap;

/**
 *
 * @author huymo
 */
public class DaoPhieuNhap implements DAOInterface<PhieuNhap> {
    
    public static DaoPhieuNhap getInstance() {
        return new DaoPhieuNhap();
    }

    @Override
    public int insert(PhieuNhap t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "INSERT INTO PhieuNhap (maPhieu, thoiGianTao, nguoiTao, maNhaCungCap, tongTien, trangThai) VALUES (?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setTimestamp(2, t.getThoiGianTao());
            pst.setString(3, t.getNguoiTao());
            pst.setString(4, t.getNhaCungCap());
            pst.setDouble(5, t.getTongTien());
            pst.setString(6, t.getTrangThai());
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int update(PhieuNhap t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "UPDATE PhieuNhap SET maPhieu=?, thoiGianTao=?, nguoiTao=?, maNhaCungCap=?, tongTien = ? WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setTimestamp(2, t.getThoiGianTao());
            pst.setString(3, t.getNguoiTao());
            pst.setString(4, t.getNhaCungCap());
            pst.setDouble(5, t.getTongTien());
            pst.setString(6, t.getMaPhieu());
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
    public int delete(PhieuNhap t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "DELETE FROM PhieuNhap WHERE maPhieu=?";
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
    public ArrayList<PhieuNhap> selectAll() {
        ArrayList<PhieuNhap> ketQua = new ArrayList<PhieuNhap>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM PhieuNhap ORDER BY thoiGianTao DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                Timestamp thoiGianTao = rs.getTimestamp("thoiGianTao");
                String nguoiTao = rs.getString("nguoiTao");
                String maNhaCungCap = rs.getString("maNhaCungCap");
                double tongTien = rs.getDouble("tongTien");
                String trangThai = rs.getString("trangThai");
                PhieuNhap p = new PhieuNhap(maNhaCungCap, maPhieu, thoiGianTao, nguoiTao, tongTien, trangThai);
                ketQua.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public PhieuNhap selectById(String t) {
        PhieuNhap ketQua = null;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM PhieuNhap WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                Timestamp thoiGianTao = rs.getTimestamp("thoiGianTao");
                String nguoiTao = rs.getString("nguoiTao");
                String maNhaCungCap = rs.getString("maNhaCungCap");
                double tongTien = rs.getDouble("tongTien");
                String trangThai = rs.getString("trangThai");
                ketQua = new PhieuNhap(maNhaCungCap, maPhieu, thoiGianTao, nguoiTao, tongTien, trangThai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    public ArrayList<PhieuNhap> selectallbyaccount(String acc) {
        ArrayList<PhieuNhap> ketQua = new ArrayList<PhieuNhap>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM PhieuNhap WHERE nguoiTao = ? ORDER BY thoiGianTao DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, acc);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                Timestamp thoiGianTao = rs.getTimestamp("thoiGianTao");
                String nguoiTao = rs.getString("nguoiTao");
                String maNhaCungCap = rs.getString("maNhaCungCap");
                double tongTien = rs.getDouble("tongTien");
                String trangThai = rs.getString("trangThai");
                PhieuNhap pn = new PhieuNhap(maNhaCungCap, maPhieu, thoiGianTao, nguoiTao, tongTien, trangThai);
                ketQua.add(pn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    // Cập nhật trạng thái — dùng connection riêng (cho thao tác đơn lẻ như Hủy)
    public int updateTrangThai(String maPhieu, String trangThai) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "UPDATE PhieuNhap SET trangThai=? WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, trangThai);
            pst.setString(2, maPhieu);
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    // Cập nhật trạng thái — dùng connection dùng chung (trong transaction với update soLuong)
    public int updateTrangThai(Connection con, String maPhieu, String trangThai) throws Exception {
        String sql = "UPDATE PhieuNhap SET trangThai=? WHERE maPhieu=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, trangThai);
        pst.setString(2, maPhieu);
        return pst.executeUpdate();
    }

}
