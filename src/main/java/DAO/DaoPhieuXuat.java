package DAO;

import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Phieuxuat;
import java.sql.Timestamp;
import model.Phieu;
import model.PhieuNhap;
/**
 *
 * @author huymo
 */
public class DaoPhieuXuat implements DAOInterface<Phieuxuat>{
    
    static public DaoPhieuXuat getInstance(){
        return new DaoPhieuXuat();
    }

    @Override
    public int insert(Phieuxuat t) {
        int ketqua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "INSERT INTO PhieuXuat (maPhieu, thoiGianTao, nguoiTao, tongTien, trangThai) VALUES (?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setTimestamp(2, t.getThoiGianTao());
            pst.setString(3, t.getNguoiTao());
            pst.setDouble(4, t.getTongTien());
            pst.setString(5, t.getTrangThai());
            ketqua = pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketqua;
    }

    @Override
    public int update(Phieuxuat t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "UPDATE PhieuXuat SET maPhieu=?, thoiGianTao=?, nguoiTao=?, tongTien = ? WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            pst.setTimestamp(2, t.getThoiGianTao());
            pst.setString(3, t.getNguoiTao());
            pst.setDouble(4, t.getTongTien());
            pst.setString(5, t.getMaPhieu());
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
    public int delete(Phieuxuat t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "DELETE FROM PhieuXuat WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaPhieu());
            ketQua = pst.executeUpdate();
            JDBCUtil.closeConnection(con);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally{
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public ArrayList<Phieuxuat> selectAll() {
        ArrayList<Phieuxuat> ketQua = new ArrayList<Phieuxuat>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM PhieuXuat ORDER BY thoiGianTao DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                Timestamp thoiGianTao = rs.getTimestamp("thoiGianTao");
                String nguoiTao = rs.getString("nguoiTao");
                double tongTien = rs.getDouble("tongTien");
                String trangThai = rs.getString("trangThai");
                Phieuxuat p = new Phieuxuat(maPhieu, thoiGianTao, nguoiTao, tongTien, trangThai);
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
    public Phieuxuat selectById(String t) {
        Phieuxuat ketQua = null;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM PhieuXuat WHERE maPhieu=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                Timestamp thoiGianTao = rs.getTimestamp("thoiGianTao");
                String nguoiTao = rs.getString("nguoiTao");
                double tongTien = rs.getDouble("tongTien");
                String trangThai = rs.getString("trangThai");
                ketQua = new Phieuxuat(maPhieu, thoiGianTao, nguoiTao, tongTien, trangThai);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    public ArrayList<Phieuxuat> selectallbyaccount(String acc) {
        ArrayList<Phieuxuat> ketQua = new ArrayList<Phieuxuat>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM PhieuXuat WHERE nguoiTao = ? ORDER BY thoiGianTao DESC";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, acc);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maPhieu = rs.getString("maPhieu");
                Timestamp thoiGianTao = rs.getTimestamp("thoiGianTao");
                String nguoiTao = rs.getString("nguoiTao");
                double tongTien = rs.getDouble("tongTien");
                String trangThai = rs.getString("trangThai");
                Phieuxuat px = new Phieuxuat(maPhieu, thoiGianTao, nguoiTao, tongTien, trangThai);
                ketQua.add(px);
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
            String sql = "UPDATE PhieuXuat SET trangThai=? WHERE maPhieu=?";
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
        String sql = "UPDATE PhieuXuat SET trangThai=? WHERE maPhieu=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, trangThai);
        pst.setString(2, maPhieu);
        return pst.executeUpdate();
    }

}
