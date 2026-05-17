/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import model.MayTinh;
import database.JDBCUtil;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Laptop;
import model.PC;
/**
 *
 * @author huymo
 */
public class Daomaytinh implements DAOInterface<MayTinh>{
    public static Daomaytinh getInstance(){
        return new Daomaytinh();
    }

    @Override
    public int insert(MayTinh t) {
        int ketqua = 0;
        Connection con = null;
        try{
            con  = JDBCUtil.getConnection();
            String sql = "Insert into maytinh(maMay, tenMay, soLuong, tenCpu, ram, cardManHinh, gia, mainBoard, congSuatNguon, loaiMay, rom, kichThuocMan, dungLuongPin, xuatXu, trangThai) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaMay());
            pst.setString(2, t.getTenMay());
            pst.setInt(3, t.getSoLuong());
            pst.setString(4, t.getTenCpu());
            pst.setString(5, t.getRam());
            pst.setString(6, t.getCardManHinh());
            pst.setDouble(7, t.getGia());
            pst.setString(11, t.getRom());
            pst.setString(14, t.getXuatXu());
            pst.setInt(15, t.getTrangThai());
            
            if(t instanceof PC pc){
                pst.setString(8,pc.getMainBoard());
                pst.setInt(9, pc.getCongSuatNguon());
                pst.setString(10, "PC");
                pst.setNull(12,java.sql.Types.DOUBLE);
                pst.setNull(13,java.sql.Types.VARCHAR );
            } else if(t instanceof Laptop lap){
                pst.setNull(8, java.sql.Types.VARCHAR);
                pst.setNull(9, java.sql.Types.INTEGER);
                pst.setString(10, "Laptop");
                pst.setDouble(12, lap.getKichThuocMan());
                pst.setString(13, lap.getDungLuongPin());
            }
            ketqua = pst.executeUpdate();
            
        } catch (SQLException e){
            e.printStackTrace();
        } finally{
            JDBCUtil.closeConnection(con);
        }
        return ketqua;

    }

    @Override
    public int update(MayTinh t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "UPDATE maytinh SET tenMay=?, soLuong=?, tenCpu=?, ram=?, cardManHinh=?, gia=?, mainBoard=?, congSuatNguon=?, loaiMay=?, rom=?, kichThuocMan=?, dungLuongPin=?, xuatXu=?, trangThai=? WHERE maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            
            pst.setString(1, t.getTenMay());
            pst.setInt(2, t.getSoLuong());
            pst.setString(3, t.getTenCpu());
            pst.setString(4, t.getRam());
            pst.setString(5, t.getCardManHinh());
            pst.setDouble(6, t.getGia());
            pst.setString(10, t.getRom());
            pst.setString(13, t.getXuatXu());
            pst.setInt(14, t.getTrangThai());
            pst.setString(15, t.getMaMay()); // Điều kiện WHERE

            if (t instanceof PC) {
                PC pc = (PC) t;
                pst.setString(7, pc.getMainBoard());
                pst.setInt(8, pc.getCongSuatNguon());
                pst.setString(9, "PC");
                pst.setNull(11, java.sql.Types.DOUBLE);
                pst.setNull(12, java.sql.Types.VARCHAR);
            } else if (t instanceof Laptop) {
                Laptop lap = (Laptop) t;
                pst.setNull(7, java.sql.Types.VARCHAR);
                pst.setNull(8, java.sql.Types.INTEGER);
                pst.setString(9, "Laptop");
                pst.setDouble(11, lap.getKichThuocMan());
                pst.setString(12, lap.getDungLuongPin());
            }

            ketQua = pst.executeUpdate();
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{            
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int delete(MayTinh t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "DELETE FROM maytinh WHERE maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaMay());
            ketQua = pst.executeUpdate();            
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public ArrayList<MayTinh> selectAll() {
        ArrayList<MayTinh> ketQua = new ArrayList<>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM maytinh";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            
            while (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String cardManHinh = rs.getString("cardManHinh");
                double gia = rs.getDouble("gia");
                String rom = rs.getString("rom");
                String xuatXu = rs.getString("xuatXu");
                int trangThai = rs.getInt("trangThai");
                String loaiMay = rs.getString("loaiMay");

                // Nếu loaiMay bị NULL (do lỗi insert cũ), suy từ prefix maMay
                if (loaiMay == null || loaiMay.isBlank()) {
                    loaiMay = maMay.startsWith("LP") ? "Laptop" : "PC";
                }

                // Phân loại để tạo đối tượng tương ứng
                if ("Laptop".equalsIgnoreCase(loaiMay)) {
                    double kichThuocMan = rs.getDouble("kichThuocMan");
                    String dungLuongPin = rs.getString("dungLuongPin");

                    Laptop lap = new Laptop(kichThuocMan, dungLuongPin, maMay, tenMay, soLuong,gia, tenCpu, ram,xuatXu, cardManHinh, rom, trangThai, loaiMay);
                    ketQua.add(lap);
                } else if ("PC".equalsIgnoreCase(loaiMay)) {
                    String mainBoard = rs.getString("mainBoard");
                    int congSuatNguon = rs.getInt("congSuatNguon");

                    PC pc = new PC(mainBoard, congSuatNguon, maMay, tenMay, soLuong,gia, tenCpu, ram,xuatXu, cardManHinh, rom, trangThai, loaiMay);
                    ketQua.add(pc);
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public MayTinh selectById(String t) {
        MayTinh ketQua = null;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM MayTinh WHERE maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                String maMay = rs.getString("maMay");
                String tenMay = rs.getString("tenMay");
                int soLuong = rs.getInt("soLuong");
                String tenCpu = rs.getString("tenCpu");
                String ram = rs.getString("ram");
                String cardManHinh = rs.getString("cardManHinh");
                double gia = rs.getDouble("gia");
                String rom = rs.getString("rom");
                String xuatXu = rs.getString("xuatXu");
                int trangThai = rs.getInt("trangThai");
                String loaiMay = rs.getString("loaiMay");

                if (loaiMay == null || loaiMay.isBlank()) {
                    loaiMay = maMay.startsWith("LP") ? "Laptop" : "PC";
                }

                if ("Laptop".equalsIgnoreCase(loaiMay)) {
                    double kichThuocMan = rs.getDouble("kichThuocMan");
                    String dungLuongPin = rs.getString("dungLuongPin");
                    ketQua = new Laptop(kichThuocMan, dungLuongPin, maMay, tenMay, soLuong,gia, tenCpu, ram,xuatXu, cardManHinh, rom, trangThai, loaiMay);
                } else if ("PC".equalsIgnoreCase(loaiMay)) {
                    String mainBoard = rs.getString("mainBoard");
                    int congSuatNguon = rs.getInt("congSuatNguon");
                    ketQua = new PC(mainBoard, congSuatNguon, maMay, tenMay, soLuong,gia, tenCpu, ram,xuatXu, cardManHinh, rom, trangThai, loaiMay);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{                                   
            JDBCUtil.closeConnection(con);                               
    }
        return ketQua;
    }

    // Trả về danh sách sản phẩm đang hoạt động (trangThai=1), dùng trong form nhập/xuất hàng
    public ArrayList<MayTinh> selectAllExist() {
        ArrayList<MayTinh> result = new ArrayList<>();
        for (MayTinh m : selectAll()) {
            if (m.getTrangThai() == 1) {
                result.add(m);
            }
        }
        return result;
    }

    // Cập nhật riêng số lượng tồn kho của một sản phẩm, dùng khi tạo/sửa phiếu nhập/xuất
    public int updateSoLuong(String maMay, int soLuong) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "UPDATE maytinh SET soLuong=? WHERE maMay=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, soLuong);
            pst.setString(2, maMay);
            ketQua = pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    // Cộng/trừ tồn kho trong transaction dùng chung connection (delta>0: cộng, delta<0: trừ)
    public int updateSoLuong(Connection con, String maMay, int delta) throws Exception {
        String sql = "UPDATE MayTinh SET soLuong = soLuong + ? WHERE maMay = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, delta);
        pst.setString(2, maMay);
        return pst.executeUpdate();
    }

    // Tính tồn kho khả dụng = soLuong thực tế - tổng số lượng đang bị giữ bởi phiếu xuất "Chờ xử lý"
    public int getSoLuongKhaDung(String maMay) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            // Chỉ trừ số lượng của phiếu "Chờ xử lý" (px.maPhieu IS NOT NULL sau LEFT JOIN có filter trạng thái)
            String sql = "SELECT mt.soLuong - COALESCE(" +
                         "SUM(CASE WHEN px.maPhieu IS NOT NULL THEN ct.soLuong ELSE 0 END), 0) AS soLuongKhaDung " +
                         "FROM MayTinh mt " +
                         "LEFT JOIN ChiTietPhieuXuat ct ON mt.maMay = ct.maMay " +
                         "LEFT JOIN PhieuXuat px ON ct.maPhieu = px.maPhieu AND px.trangThai = 'Chờ xử lý' " +
                         "WHERE mt.maMay = ? " +
                         "GROUP BY mt.soLuong";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, maMay);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                ketQua = rs.getInt("soLuongKhaDung");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

}
