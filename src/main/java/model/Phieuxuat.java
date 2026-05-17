package model;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 *
 * @author huymo
 */
public class Phieuxuat extends Phieu {

    public Phieuxuat() {
        
    }
    
    public Phieuxuat(String maPhieu, Timestamp thoiGianTao, String nguoiTao, ArrayList<ChiTietPhieu> CTPhieu, double tongTien) {
        super(maPhieu, thoiGianTao, nguoiTao, CTPhieu, tongTien);
    }

    public Phieuxuat(String maphieu, Timestamp Thoigiantao, String nguoitao, double tongtien) {
        super(maphieu, Thoigiantao, nguoitao, tongtien);
    }

    public Phieuxuat(String maphieu, Timestamp Thoigiantao, String nguoitao, double tongtien, String trangThai) {
        super(maphieu, Thoigiantao, nguoitao, tongtien, trangThai);
    }
    
    
    
}
