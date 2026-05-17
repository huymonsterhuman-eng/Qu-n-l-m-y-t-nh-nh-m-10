# Log thay đổi dự án

> **Quy tắc:** File này chỉ được đọc khi có sự cho phép của người dùng.

---

## [2026-04-25] Session 1 — Sửa lỗi insert/display sản phẩm

### Những gì đã làm

**Lỗi 1 — `Daomaytinh.insert()` Laptop branch sai vị trí parameter:**
- SQL INSERT có 15 `?`; nhánh Laptop gán sai: pos 9 thay vì 10 cho `loaiMay`, bỏ qua pos 12/13 (`kichThuocMan`/`dungLuongPin`), đồng thời ghi đè `gia` (pos 7) thành NULL
- Kết quả: `PreparedStatement` ném `SQLException` → DAO bắt im lặng → insert thất bại hoàn toàn
- **Sửa:** căn đúng pos 8=mainBoard(NULL), 9=congSuatNguon(NULL), 10="Laptop", 12=kichThuocMan, 13=dungLuongPin; bỏ dòng `setNull(7)` sai

**Lỗi 2 — `AddProduct.java` không kiểm tra return value:**
- `insert()` trả về 0 khi thất bại nhưng View luôn show "Thêm thành công" và dispose dialog
- **Sửa:** lưu kết quả vào `ketquaLT`/`ketquaPC`, chỉ dispose + reload khi `> 0`

**Lỗi 3 — `selectAll()` bỏ sót record:**
- Dùng `equals()` case-sensitive → record có loaiMay casing khác bị drop im lặng
- **Sửa:** `equalsIgnoreCase()` + inference từ prefix maMay khi loaiMay NULL (do bug insert cũ)

---

## [2026-04-25] Session 2 — Sửa xóa sản phẩm & đồng bộ selectById

### Những gì đã làm

**Lỗi 4 — `selectById()` không đồng bộ với `selectAll()`:**
- `selectAll()` đã dùng `equalsIgnoreCase` + NULL inference, nhưng `selectById()` vẫn dùng `equals()` thuần
- Hậu quả: sản phẩm hiện trong bảng (qua selectAll) nhưng getMayTinhSelect() trả về null → NPE khi edit/delete
- **Sửa:** đồng bộ `equalsIgnoreCase` + NULL inference vào `selectById()`

**Lỗi 5 — Xóa sản phẩm chỉ ẩn khỏi bảng, không xóa DB:**
- Code cũ dùng soft delete (`setTrangThai(0)` + `update()`); user kỳ vọng hard delete
- **Sửa:** đổi sang `Daomaytinh.delete()` (DELETE FROM); nếu FK violation → trả về 0 → báo lỗi "Sản phẩm đang dùng trong phiếu nhập/xuất"

---

## [2026-04-25] Session 3 — Business rules & trạng thái kinh doanh

### Những gì đã làm

**Business rule: Không xóa sản phẩm khi soLuong > 0**
- `xoaMayTinhSelect()`: kiểm tra `soLuong > 0` trước confirm dialog → báo "còn X cái trong kho"

**Tính năng: Cột Trạng thái + nút Đổi trạng thái**
- `ProductForm.initTable()`: thêm cột "Trạng thái" (thứ 9)
- `loadDataToTable()` + `loadDataToTableSearch()`: bỏ filter `trangThai==1`, hiện TẤT CẢ sản phẩm, thêm text "Đang kinh doanh" / "Ngừng kinh doanh"
- `searchFn()`: tìm trên ALL sản phẩm; "Đã xóa" filter = ngừng KD; thêm case "Trạng thái" lọc theo text
- `initToggleButton()`: thêm nút "Đổi trạng thái" vào `jToolBar1` sau `initComponents()`, icon `icons8-update-left-rotation-40.png`
- `toggleTrangThaiSelect()`: toggle `trangThai` (1↔0) → confirm → `update()` → reload
- `checkRole()`: disable `btnToggleStatus` cho Nhân viên nhập/xuất
- `jComboBoxLuaChon.addItem("Trạng thái")`: thêm mục filter sau khi table khởi tạo xong

**`UpdateProduct.java`: Giữ nguyên trangThai khi sửa sản phẩm**
- Trước: hardcode `trangThai = 1` khi update → sửa sản phẩm tự reset về Đang KD
- **Sửa:** thêm field `trangThaiProduct`, đọc từ product trong constructor, dùng khi tạo Laptop/PC object để update

**`selectAllExist()` đã lọc `trangThai==1` → sản phẩm Ngừng KD tự ẩn khỏi phiếu nhập/xuất ✅ (không cần sửa)**

---

## Bài học rút ra

1. **PreparedStatement INSERT ≠ UPDATE về thứ tự cột** — mỗi SQL có mapping riêng, phải đối chiếu kỹ từng vị trí `?`
2. **DAO bắt exception im lặng → View PHẢI kiểm tra return value** — nếu DAO chỉ `e.printStackTrace()` và trả về 0, View không được bỏ qua giá trị trả về
3. **`selectAll()` và `selectById()` phải đồng bộ logic** — nếu một cái có `equalsIgnoreCase` + NULL inference, cái kia cũng phải có, nếu không sản phẩm hiện trong bảng nhưng không thao tác được
4. **NetBeans `initComponents()` không được sửa** — thêm button/combobox item bằng code trong constructor SAU `initComponents()`; `JToolBar.add()` và `JComboBox.addItem()` hoạt động tốt theo cách này
5. **Soft delete vs hard delete cần cân nhắc FK constraints** — hệ thống có `ChiTietPhieuNhap` và `ChiTietPhieuXuat` tham chiếu `maMay` → hard delete đúng thiết kế, dùng FK violation để báo lỗi thay vì kiểm tra thủ công
6. **`trangThai` có thể tái dụng** — cùng một cột DB phục vụ được cả "trạng thái kinh doanh" lẫn cơ chế lọc phiếu nhập/xuất mà không cần thêm cột mới
