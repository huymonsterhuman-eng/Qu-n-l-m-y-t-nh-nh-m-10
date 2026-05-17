package controller;

import model.MayTinh;
import java.util.ArrayList;

/**
 * Tìm kiếm và lọc danh sách sản phẩm (MayTinh) theo từ khóa và trường được chọn.
 * Dùng trong ProductForm, TonKhoForm khi người dùng nhập vào ô tìm kiếm.
 */
public class SearchProduct {

    /**
     * Lọc danh sách sản phẩm theo từ khóa và trường lựa chọn.
     *
     * @param list    danh sách gốc lấy từ Daomaytinh.selectAll()
     * @param keyword từ khóa tìm kiếm (không phân biệt hoa thường)
     * @param field   trường muốn tìm: "Tất cả", "Mã máy", "Tên máy", "Loại máy"
     * @return danh sách đã lọc (không làm thay đổi list gốc)
     */
    public static ArrayList<MayTinh> search(ArrayList<MayTinh> list, String keyword, String field) {
        ArrayList<MayTinh> result = new ArrayList<>();

        // Nếu từ khóa rỗng, trả về toàn bộ danh sách
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(list);
        }

        String kw = keyword.trim().toLowerCase();

        for (MayTinh m : list) {
            boolean matched = false;

            switch (field) {
                case "Mã máy":
                    // Tìm theo mã máy
                    matched = m.getMaMay() != null && m.getMaMay().toLowerCase().contains(kw);
                    break;
                case "Tên máy":
                    // Tìm theo tên máy
                    matched = m.getTenMay() != null && m.getTenMay().toLowerCase().contains(kw);
                    break;
                case "Loại máy":
                    // Tìm theo loại (Laptop, PC, ...)
                    matched = m.getLoaimay() != null && m.getLoaimay().toLowerCase().contains(kw);
                    break;
                default:
                    // "Tất cả": tìm trong mã, tên, loại
                    matched = (m.getMaMay() != null && m.getMaMay().toLowerCase().contains(kw))
                            || (m.getTenMay() != null && m.getTenMay().toLowerCase().contains(kw))
                            || (m.getLoaimay() != null && m.getLoaimay().toLowerCase().contains(kw));
                    break;
            }

            if (matched) {
                result.add(m);
            }
        }

        return result;
    }
}
