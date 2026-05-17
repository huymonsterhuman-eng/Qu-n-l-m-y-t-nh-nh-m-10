package controller;

import model.Account;
import java.util.ArrayList;

/**
 * Tìm kiếm và lọc danh sách tài khoản theo từ khóa và trường được chọn.
 * Dùng trong AccountForm, ThongKeForm khi người dùng nhập vào ô tìm kiếm.
 */
public class SearchAccount {

    /**
     * Lọc danh sách tài khoản theo từ khóa và trường lựa chọn.
     *
     * @param list    danh sách gốc lấy từ DaoAccount.selectAll()
     * @param keyword từ khóa tìm kiếm (không phân biệt hoa thường)
     * @param field   trường muốn tìm: "Tất cả", "Tên đăng nhập", "Họ tên", "Email"
     * @return danh sách đã lọc
     */
    public static ArrayList<Account> search(ArrayList<Account> list, String keyword, String field) {
        ArrayList<Account> result = new ArrayList<>();

        // Nếu từ khóa rỗng, trả về toàn bộ danh sách
        if (keyword == null || keyword.trim().isEmpty()) {
            return new ArrayList<>(list);
        }

        String kw = keyword.trim().toLowerCase();

        for (Account a : list) {
            boolean matched = false;

            switch (field) {
                case "Tên đăng nhập":
                    matched = a.getUser() != null && a.getUser().toLowerCase().contains(kw);
                    break;
                case "Họ tên":
                    matched = a.getFullname() != null && a.getFullname().toLowerCase().contains(kw);
                    break;
                case "Email":
                    matched = a.getEmail() != null && a.getEmail().toLowerCase().contains(kw);
                    break;
                default:
                    // "Tất cả": tìm trong username, họ tên, email
                    matched = (a.getUser() != null && a.getUser().toLowerCase().contains(kw))
                            || (a.getFullname() != null && a.getFullname().toLowerCase().contains(kw))
                            || (a.getEmail() != null && a.getEmail().toLowerCase().contains(kw));
                    break;
            }

            if (matched) {
                result.add(a);
            }
        }

        return result;
    }
}
