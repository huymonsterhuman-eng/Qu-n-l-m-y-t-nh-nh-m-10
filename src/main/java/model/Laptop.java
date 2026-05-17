package model;

import java.util.Objects;

/**
 *
 * @author huymo
 */
public class Laptop extends MayTinh {
    private double kichThuocMan;
    private String dungLuongPin;
    
    public Laptop(){
        
    }
    
    // Constructor không cần truyền loaimay — tự điền "Laptop"
    public Laptop(double kichThuocMan, String dungLuongPin, String maMay, String tenMay, int soLuong, double gia, String tenCpu, String ram, String xuatXu, String cardManHinh, String Rom, int trangThai) {
        this(kichThuocMan, dungLuongPin, maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, Rom, trangThai, "Laptop");
    }

    public Laptop(double kichThuocMan, String dungLuongPin,String maMay, String tenMay, int soLuong, double gia, String tenCpu, String ram, String xuatXu, String cardManHinh, String Rom,int trangThai, String loaimay){
        super( maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, Rom, trangThai, loaimay);
        this.kichThuocMan = kichThuocMan;
        this.dungLuongPin = dungLuongPin;
    }

    public double getKichThuocMan() {
        return kichThuocMan;
    }

    public void setKichThuocMan(double kichThuocMan) {
        this.kichThuocMan = kichThuocMan;
    }

    public String getDungLuongPin() {
        return dungLuongPin;
    }

    public void setDungLuongPin(String dungLuongPin) {
        this.dungLuongPin = dungLuongPin;
    }

    @Override
    public String toString() {
        return super.toString() + "-->Laptop{" + "kichThuocMan=" + kichThuocMan + ", dungLuongPin=" + dungLuongPin + '}';
    }

    
           
    
}
