package model;

import java.util.Objects;

/**
 *
 * @author huymo
 */
public class ChiTietPhieu {
    private String maPhieu;
    private String maMay;
    private int soLuong;
    private double donGia;
    private int soLuongConLai; // số lượng còn lại trong lô nhập; mặc định 0 cho phiếu xuất

    public ChiTietPhieu(String maPhieu, String maMay, int soLuong, double donGia) {
        this.maPhieu = maPhieu;
        this.maMay = maMay;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public ChiTietPhieu() {
    }
    
    
    
    public String getMaPhieu() {
        return maPhieu;
    }

    public void setMaPhieu(String maPhieu) {
        this.maPhieu = maPhieu;
    }

    public String getMaMay() {
        return maMay;
    }

    public void setMaMay(String maMay) {
        this.maMay = maMay;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public int getSoLuongConLai() {
        return soLuongConLai;
    }

    public void setSoLuongConLai(int soLuongConLai) {
        this.soLuongConLai = soLuongConLai;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.maPhieu);
        hash = 61 * hash + Objects.hashCode(this.maMay);
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
        final ChiTietPhieu other = (ChiTietPhieu) obj;
        if (!Objects.equals(this.maPhieu, other.maPhieu)) {
            return false;
        }
        return Objects.equals(this.maMay, other.maMay);
    }

    @Override
    public String toString() {
        return "ChiTietPhieu{" + "maPhieu=" + maPhieu + ", maMay=" + maMay + ", soLuong=" + soLuong + ", donGia=" + donGia + '}';
    }
    
    
}
