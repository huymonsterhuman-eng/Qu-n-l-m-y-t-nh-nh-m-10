package model;
import java.util.Objects;
/**
 *
 * @author huymo
 */
public class Thongke {
    private String maMay;
    private String tenMay;
    private int slNhap;
    private int slXuat;

    public Thongke(){
        
    }
    public Thongke(String maMay, String tenMay, int slNhap, int slXuat) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.slNhap = slNhap;
        this.slXuat = slXuat;
    }

    public String getMaMay() {
        return maMay;
    }

    public void setMaMay(String maMay) {
        this.maMay = maMay;
    }

    public String getTenMay() {
        return tenMay;
    }

    public void setTenMay(String tenMay) {
        this.tenMay = tenMay;
    }

    public int getSlNhap() {
        return slNhap;
    }

    public void setSlNhap(int slNhap) {
        this.slNhap = slNhap;
    }

    public int getSlXuat() {
        return slXuat;
    }

    public void setSlXuat(int slXuat) {
        this.slXuat = slXuat;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.maMay);
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
        final Thongke other = (Thongke) obj;
        return Objects.equals(this.maMay, other.maMay);
    }

    @Override
    public String toString() {
        return "Thongke{" + "maMay=" + maMay + ", tenMay=" + tenMay + ", slNhap=" + slNhap + ", slXuat=" + slXuat + '}';
    }
     
}
