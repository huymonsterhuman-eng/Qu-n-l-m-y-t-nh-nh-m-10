package model;

import java.util.Objects;

/**
 *
 * @author huymo
 */
public class MayTinh {
    private String maMay;
    private String tenMay;
    private int soLuong;
    private double gia;
    private String tenCpu;
    private String ram;
    private String xuatXu;
    private String cardManHinh;
    private String Rom;
    private int trangThai;
    private String loaimay;
    
    public MayTinh() {
        
    }
    
    public MayTinh(String maMay, String tenMay, int soLuong, double donGia, String boXuLi, String loaiMay, int trangThai){
        
    }

    public MayTinh(String maMay, String tenMay, int soLuong, double gia, String tenCpu, String ram, String xuatXu, String cardManHinh, String Rom,int trangThai, String loaimay) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.soLuong = soLuong;
        this.gia = gia;
        this.tenCpu = tenCpu;
        this.ram = ram;
        this.xuatXu = xuatXu;
        this.cardManHinh = cardManHinh;
        this.Rom = Rom;
        this.trangThai = trangThai;
        this.loaimay = loaimay;
    }
    
    public MayTinh(String maMay, String tenMay, int soLuong, double gia,String tenCpu, String ram,String Rom) {
        this.maMay = maMay;
        this.tenMay = tenMay;
        this.soLuong = soLuong;
        this.gia = gia;
        this.tenCpu = tenCpu;
        this.ram = ram;
        this.Rom = Rom;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getTenCpu() {
        return tenCpu;
    }

    public void setTenCpu(String tenCpu) {
        this.tenCpu = tenCpu;
    }

    public String getRam() {
        return ram;
    }

    public void setRam(String ram) {
        this.ram = ram;
    }

    public String getXuatXu() {
        return xuatXu;
    }

    public void setXuatXu(String xuatXu) {
        this.xuatXu = xuatXu;
    }

    public String getCardManHinh() {
        return cardManHinh;
    }

    public void setCardManHinh(String cardManHinh) {
        this.cardManHinh = cardManHinh;
    }

    public String getRom() {
        return Rom;
    }

    public void setRom(String Rom) {
        this.Rom = Rom;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getLoaimay() {
        return loaimay;
    }

    public void setLoaimay(String loaimay) {
        this.loaimay = loaimay;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.maMay);
        hash = 47 * hash + Objects.hashCode(this.tenMay);   
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
        final MayTinh other = (MayTinh) obj;
        if (!Objects.equals(this.maMay, other.maMay)) {
            return false;
        }
        return Objects.equals(this.tenMay, other.tenMay);
    }   

    @Override
    public String toString() {
        return "MayTinh{" + "maMay=" + maMay + ", tenMay=" + tenMay + ", soLuong=" + soLuong + ", gia=" + gia + ", tenCpu=" + tenCpu + ", ram=" + ram + ", xuatXu=" + xuatXu + ", cardManHinh=" + cardManHinh + ", Rom=" + Rom + ", trangThai=" + trangThai + ", loaimay=" + loaimay + '}';
    }     
}
