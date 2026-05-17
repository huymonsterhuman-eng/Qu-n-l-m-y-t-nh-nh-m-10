package model;
import java.sql.Timestamp;
import java.util.ArrayList;
/**
 *
 * @author huymo
 */
public class PhieuNhap extends Phieu {
    private String nhaCungCap;
    
    public PhieuNhap(){
        
    }
    
    public PhieuNhap(String nhaCungCap,String maphieu, Timestamp thoigiantao, String nguoitao,ArrayList<ChiTietPhieu> ds, double tongtien ){
        super(maphieu, thoigiantao, nguoitao, ds, tongtien);
        this.nhaCungCap = nhaCungCap;
    }

    public PhieuNhap(String nhaCungCap, String maphieu, Timestamp Thoigiantao, String nguoitao, double tongtien) {
        super(maphieu, Thoigiantao, nguoitao, tongtien);
        this.nhaCungCap = nhaCungCap;
    }

    public PhieuNhap(String nhaCungCap, String maphieu, Timestamp Thoigiantao, String nguoitao, double tongtien, String trangThai) {
        super(maphieu, Thoigiantao, nguoitao, tongtien, trangThai);
        this.nhaCungCap = nhaCungCap;
    }
    
    

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    @Override
    public String toString() {
        return super.toString() + "--> PhieuNhap{" + "nhaCungCap=" + nhaCungCap + '}';
    }
       
}
