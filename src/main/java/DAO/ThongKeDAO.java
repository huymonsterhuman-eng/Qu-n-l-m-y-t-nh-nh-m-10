package DAO;

import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import model.Thongke;

/**
 * DAO thống kê số lượng nhập/xuất theo từng sản phẩm.
 */
public class ThongKeDAO {

    public static ThongKeDAO getInstance() {
        return new ThongKeDAO();
    }

    /**
     * Thống kê tổng số lượng nhập và xuất cho tất cả sản phẩm (toàn thời gian).
     */
    public ArrayList<Thongke> getThongKe() {
        ArrayList<Thongke> result = new ArrayList<>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql =
                "SELECT m.maMay, m.tenMay, " +
                "COALESCE(SUM(ctn.soLuong), 0) AS slNhap, " +
                "COALESCE(SUM(ctx.soLuong), 0) AS slXuat " +
                "FROM maytinh m " +
                "LEFT JOIN ChiTietPhieuNhap ctn ON m.maMay = ctn.maMay " +
                "LEFT JOIN ChiTietPhieuXuat ctx ON m.maMay = ctx.maMay " +
                "WHERE m.trangThai = 1 " +
                "GROUP BY m.maMay, m.tenMay " +
                "ORDER BY m.tenMay";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                result.add(new Thongke(
                    rs.getString("maMay"),
                    rs.getString("tenMay"),
                    rs.getInt("slNhap"),
                    rs.getInt("slXuat")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return result;
    }

    /**
     * Thống kê số lượng nhập/xuất trong khoảng thời gian [from, to].
     */
    public ArrayList<Thongke> getThongKe(Date from, Date to) {
        ArrayList<Thongke> result = new ArrayList<>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql =
                "SELECT m.maMay, m.tenMay, " +
                "COALESCE(SUM(CASE WHEN pn.thoiGianTao BETWEEN ? AND ? THEN ctn.soLuong ELSE 0 END), 0) AS slNhap, " +
                "COALESCE(SUM(CASE WHEN px.thoiGianTao BETWEEN ? AND ? THEN ctx.soLuong ELSE 0 END), 0) AS slXuat " +
                "FROM maytinh m " +
                "LEFT JOIN ChiTietPhieuNhap ctn ON m.maMay = ctn.maMay " +
                "LEFT JOIN PhieuNhap pn ON ctn.maPhieu = pn.maPhieu " +
                "LEFT JOIN ChiTietPhieuXuat ctx ON m.maMay = ctx.maMay " +
                "LEFT JOIN PhieuXuat px ON ctx.maPhieu = px.maPhieu " +
                "WHERE m.trangThai = 1 " +
                "GROUP BY m.maMay, m.tenMay " +
                "ORDER BY m.tenMay";
            PreparedStatement pst = con.prepareStatement(sql);
            Timestamp fromTs = new Timestamp(from.getTime());
            Timestamp toTs = new Timestamp(to.getTime());
            pst.setTimestamp(1, fromTs);
            pst.setTimestamp(2, toTs);
            pst.setTimestamp(3, fromTs);
            pst.setTimestamp(4, toTs);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                result.add(new Thongke(
                    rs.getString("maMay"),
                    rs.getString("tenMay"),
                    rs.getInt("slNhap"),
                    rs.getInt("slXuat")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return result;
    }
}
