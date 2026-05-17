/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author huymo
 */
import database.JDBCUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.NhaCungCap;
public class DaoNhaCungCap implements DAOInterface<NhaCungCap>{

    public static DaoNhaCungCap getInstance() {
        return new DaoNhaCungCap();
    }
     
    @Override
    public int insert(NhaCungCap t) {
        int ketQua = 0;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "INSERT INTO NhaCungCap (maNhaCungCap, tenNhaCungCap, Sdt, diaChi) VALUES (?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaNhaCungCap());
            pst.setString(2, t.getTenNhaCungCap());
            pst.setString(3, t.getSdt());
            pst.setString(4, t.getDiaChi());
            ketQua = pst.executeUpdate();
            
        } catch (Exception e) {           
            JOptionPane.showMessageDialog(null, "Không thêm được nhà cung cấp " + t.getMaNhaCungCap(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int update(NhaCungCap t) {
        int ketQua = 0;
        Connection con = null;

        try {
            con = JDBCUtil.getConnection();
            String sql = "UPDATE NhaCungCap SET maNhaCungCap=?, tenNhaCungCap=?, Sdt=?, diaChi=? WHERE maNhaCungCap=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaNhaCungCap());
            pst.setString(2, t.getTenNhaCungCap());
            pst.setString(3, t.getSdt());
            pst.setString(4, t.getDiaChi());
            pst.setString(5, t.getMaNhaCungCap());
            ketQua = pst.executeUpdate();
            
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại nhà cung cấp" + t.getMaNhaCungCap(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally{
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public int delete(NhaCungCap t) {
        int ketQua = 0;
        Connection con = null;

        try {
            con = JDBCUtil.getConnection();
            String sql = "DELETE FROM NhaCungCap WHERE maNhaCungCap=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t.getMaNhaCungCap());
            ketQua = pst.executeUpdate();            
        } catch (Exception e) {
            // TODO: handle exception
            JOptionPane.showMessageDialog(null, "Xóa thất bại nhà cung cấp" + t.getMaNhaCungCap(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally{
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public ArrayList<NhaCungCap> selectAll() {
        ArrayList<NhaCungCap> ketQua = new ArrayList<NhaCungCap>();
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM NhaCungCap";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maNhaCungCap = rs.getString("maNhaCungCap");
                String tenNhaCungCap = rs.getString("tenNhaCungCap");
                String sdt = rs.getString("Sdt");
                String diaChi = rs.getString("diaChi");
                NhaCungCap ncc = new NhaCungCap(maNhaCungCap, tenNhaCungCap, sdt, diaChi);
                ketQua.add(ncc);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            JDBCUtil.closeConnection(con);
        }
        return ketQua;
    }

    @Override
    public NhaCungCap selectById(String t) {
        NhaCungCap ketQua = null;
        Connection con = null;
        try {
            con = JDBCUtil.getConnection();
            String sql = "SELECT * FROM NhaCungCap WHERE maNhaCungCap=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, t);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                String maNhaCungCap = rs.getString("maNhaCungCap");
                String tenNhaCungCap = rs.getString("tenNhaCungCap");
                String sdt = rs.getString("Sdt");
                String diaChi = rs.getString("diaChi");
                ketQua = new NhaCungCap(maNhaCungCap, tenNhaCungCap, sdt, diaChi);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally{
            JDBCUtil.closeConnection(con);       
        }
        return ketQua;
    }
       
}
