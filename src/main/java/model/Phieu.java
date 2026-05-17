package model;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Objects;
/**
 *
 * @author huymo
 */
public class Phieu {
    public static final String CHO_XU_LY  = "Chờ xử lý";
    public static final String HOAN_THANH = "Đã hoàn thành";
    public static final String DA_HUY     = "Đã hủy";

    private String maPhieu;
    private Timestamp thoiGianTao;
    private String nguoiTao;
    private ArrayList<ChiTietPhieu> CTPhieu;
    private double tongTien;
    private String trangThai;

    public Phieu(){
        this.CTPhieu = new ArrayList<>();
        this.trangThai = CHO_XU_LY;
    }
    public Phieu(String maphieu, Timestamp thoigiantao, String nguoitao, ArrayList<ChiTietPhieu> ds, double tongtien){
        this.maPhieu = maphieu;
        this.thoiGianTao = thoigiantao;
        this.nguoiTao = nguoitao;
        this.CTPhieu = ds;
        this.tongTien = tongtien;
        this.trangThai = CHO_XU_LY;
    }
    public Phieu(String maphieu, Timestamp thoigiantao, String nguoitao, ArrayList<ChiTietPhieu> ds, double tongtien, String trangthai){
        this.maPhieu = maphieu;
        this.thoiGianTao = thoigiantao;
        this.nguoiTao = nguoitao;
        this.CTPhieu = ds;
        this.tongTien = tongtien;
        this.trangThai = trangthai;
    }
    public Phieu(String maphieu, Timestamp Thoigiantao, String nguoitao, double tongtien){
        this.maPhieu = maphieu;
        this.thoiGianTao = Thoigiantao;
        this.nguoiTao = nguoitao;
        this.tongTien = tongtien;
        this.CTPhieu = new ArrayList<>();
        this.trangThai = CHO_XU_LY;
    }
    public Phieu(String maphieu, Timestamp Thoigiantao, String nguoitao, double tongtien, String trangthai){
        this.maPhieu = maphieu;
        this.thoiGianTao = Thoigiantao;
        this.nguoiTao = nguoitao;
        this.tongTien = tongtien;
        this.CTPhieu = new ArrayList<>();
        this.trangThai = trangthai;
    }

    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public Timestamp getThoiGianTao() {
        return thoiGianTao;
    }

    public void setThoiGianTao(Timestamp thoiGianTao) {
        this.thoiGianTao = thoiGianTao;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public ArrayList<ChiTietPhieu> getCTPhieu() {
        return CTPhieu;
    }

    public void setCTPhieu(ArrayList<ChiTietPhieu> CTPhieu) {
        this.CTPhieu = CTPhieu;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.maPhieu);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Phieu other = (Phieu) obj;
        return Objects.equals(this.maPhieu, other.maPhieu);
    }

    @Override
    public String toString() {
        return "Phieu{" + "maPhieu=" + maPhieu + ", thoiGianTao=" + thoiGianTao + ", nguoiTao=" + nguoiTao + ", CTPhieu=" + CTPhieu + ", tongTien=" + tongTien + ", trangThai=" + trangThai + '}';
    }
    
    
    
}
