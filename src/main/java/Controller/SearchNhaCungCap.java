package controller;

import model.NhaCungCap;
import java.util.ArrayList;

/**
 * Tìm kiếm và lọc danh sách nhà cung cấp theo từ khóa và trường được chọn.
 * Dùng trong NhaCungCapForm khi người dùng nhập vào ô tìm kiếm.
 */
public class SearchNhaCungCap {

    /**
     * Lọc danh sách nhà cung cấp theo từ khóa và trường lựa chọn.
     *
     * @param list    danh sách gốc lấy từ DaoNhaCungCap.selectAll()
     * @param keyword từ khóa tìm kiếm (không phân biệt hoa thường)
     * @param field   trường muốn tìm: "Tất cả", "Mã NCC", "Tên NCC", "SĐT"
     * @return danh sách đã lọc
     */
    public static ArrayList<NhaCungCap> search(ArrayList<NhaCungCap> list, String keyword, String field) {
        ArrayList<NhaCungCap> result = new ArrayList<>();

        // Nếu từ khóa rỗng, trả về toàn bộ danh sách
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(list);
        }

        String kw = keyword.trim().toLowerCase();

        for (NhaCungCap n : list) {
            boolean matched = false;

            switch (field) {
                case "Mã NCC":
                    matched = n.getMaNhaCungCap() != null && n.getMaNhaCungCap().toLowerCase().contains(kw);
                    break;
                case "Tên NCC":
                    matched = n.getTenNhaCungCap() != null && n.getTenNhaCungCap().toLowerCase().contains(kw);
                    break;
                case "SĐT":
                    matched = n.getSdt() != null && n.getSdt().toLowerCase().contains(kw);
                    break;
                default:
                    // "Tất cả": tìm trong mã, tên, SĐT, địa chỉ
                    matched = (n.getMaNhaCungCap() != null && n.getMaNhaCungCap().toLowerCase().contains(kw))
                            || (n.getTenNhaCungCap() != null && n.getTenNhaCungCap().toLowerCase().contains(kw))
                            || (n.getSdt() != null && n.getSdt().toLowerCase().contains(kw))
                            || (n.getDiaChi() != null && n.getDiaChi().toLowerCase().contains(kw));
                    break;
            }

            if (matched) {
                result.add(n);
            }
        }

        return result;
    }
}
