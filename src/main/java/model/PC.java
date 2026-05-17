package model;

import java.util.Objects;

/**
 *
 * @author huymo
 */
public class PC extends MayTinh{
    private String mainBoard;
    private int congSuatNguon;
    

    public PC() {
    }

    public PC(String mainBoard, int congSuatNguon) {
        this.mainBoard = mainBoard;
        this.congSuatNguon = congSuatNguon;
    }
    
    // Constructor không cần truyền loaimay — tự điền "PC"
    public PC(String mainBoard, int congSuatNguon, String maMay, String tenMay, int soLuong, double gia, String tenCpu, String ram, String xuatXu, String cardManHinh, String Rom, int trangThai) {
        this(mainBoard, congSuatNguon, maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, Rom, trangThai, "PC");
    }

    public PC(String mainBoard, int congSuatNguon, String maMay, String tenMay, int soLuong, double gia, String tenCpu, String ram, String xuatXu, String cardManHinh, String Rom, int trangThai, String loaimay) {
        super(maMay, tenMay, soLuong, gia, tenCpu, ram, xuatXu, cardManHinh, Rom,trangThai, loaimay);
        this.mainBoard = mainBoard;
        this.congSuatNguon = congSuatNguon;
    }

    public String getMainBoard() {
        return mainBoard;
    }

    public void setMainBoard(String mainBoard) {
        this.mainBoard = mainBoard;
    }

    public int getCongSuatNguon() {
        return congSuatNguon;
    }

    public void setCongSuatNguon(int congSuatNguon) {
        this.congSuatNguon = congSuatNguon;
    }
    @Override
    public String toString() {
        return super.toString() + "--> PC{" + "mainBoard=" + mainBoard + ", congSuatNguon=" + congSuatNguon + '}';
    }
    
}
