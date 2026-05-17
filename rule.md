# Quy định coding và quy trình làm việc

**Rule 1**: Không đọc tất cả file — chỉ đọc những file thực sự cần thiết cho nhiệm vụ hiện tại.

**Rule 2**: Thiếu thông tin thì hỏi, không tự suy đoán và làm tiếp.

**Rule 3 — Quy ước đặt tên**
- Biến/field trong `model` và `DAO`: camelCase theo đúng tên cột trong DB (`maMay`, `tenMay`, `soLuong`)
- Tên class và method: PascalCase / camelCase tiếng Anh
- Nhãn giao diện (text của JLabel, JButton, JTable header): tiếng Việt có dấu

**Rule 4 — Xử lý kết nối DB**
- Luôn truy cập DAO qua singleton: `DaoXxx.getInstance()`
- Mọi `Connection`, `Statement`, `ResultSet` phải được đóng trong `finally` hoặc try-with-resources
- Không tự mở connection bên ngoài `JDBCUtil`

**Rule 5 — Cập nhật tài liệu sau khi hoàn thành**
- Sau mỗi batch/phase xong: đánh dấu ✅ và ghi chú vào `Progress.md`
- Nếu thêm dependency mới vào `pom.xml`: cập nhật mục "Công nghệ sử dụng" trong `CLAUDE.md`

**Rule 6 — Xử lý lỗi**
- Không nuốt exception im lặng (`catch (Exception e) { }`)
- View layer: dùng `JOptionPane.showMessageDialog` để thông báo lỗi cho người dùng
- DAO layer: chỉ ném exception lên, không hiển thị UI dialog

**Rule 7 — Phạm vi chỉnh sửa**
- Không sửa khối `initComponents()` trong các file View (do NetBeans tự sinh, sẽ bị ghi đè)
- Không sửa `DAO/*.java` và `model/*.java` trừ khi được yêu cầu rõ ràng
- Chỉ thêm code vào event handler và helper method trong View
