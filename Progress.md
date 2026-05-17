# Kế hoạch triển khai Controller + View Logic

## Context
Dự án quản lý kho máy tính đã có sẵn: Model, DAO, và UI (View copy từ dự án khác, chỉ giữ phần giao diện). Controller chưa có. Cần: (1) xây dựng Controller layer, (2) viết lại toàn bộ logic xử lý sự kiện trong View dựa trên DAO/Controller có sẵn.
Chú thích khi viết code để người dung hiểu

---

##Tiến độ hiện tại 
Phase 1: Controller Layer (package `controller`) : hoàn thành

Phase 2: View Logic Rewrite
✅ Batch 1: AddProduct, UpdateProduct
✅ Batch 2: DetailProduct, ProductForm
✅ Batch 3: CTPhieuNhap, CTPhieuXuat
✅ Batch 4: UpdatePhieuNhap, UpdatePhieuXuat
✅ Batch 5: TonKhoForm (Quanlikho), RecoverPassword
✅ Batch 6: CTThongKe, CTThongKeACC
✅ Batch 7: ThongKeForm

Phase 3: Cải tiến
✅ Batch 1 (Validate Input): AddProduct, UpdateProduct
   - Thêm trim() vào tất cả getText()
   - Thêm return sau mỗi catch để dừng khi lỗi parse
   - Check số âm/bằng 0: donGia, kichThuocMan, congSuatNguon, dungLuongPin
   - Đổi .equals("") → .isEmpty()
✅ Batch 2 (Trạng thái phiếu nhập/xuất): Vòng đời phiếu theo trạng thái
   - Thêm cột trangThai vào DB: PhieuNhap, PhieuXuat (varchar 20, default 'Chờ xử lý')
   - Migration: UPDATE bản ghi cũ → 'Đã hoàn thành'
   - model/Phieu.java: thêm hằng số CHO_XU_LY / HOAN_THANH / DA_HUY, field trangThai, getter/setter, overload constructor
   - model/PhieuNhap.java, model/Phieuxuat.java: thêm constructor nhận trangThai
   - database/JDBCUtil.java: thêm beginTransaction(), commitTransaction(), rollbackTransaction()
   - DAO/DaoPhieuNhap.java: cập nhật INSERT/SELECT thêm trangThai, thêm updateTrangThai() (2 overload)
   - DAO/DaoPhieuXuat.java: tương tự
   - DAO/Daomaytinh.java: thêm updateSoLuong(Connection, maMay, delta) và getSoLuongKhaDung(maMay)
   - View/NhapHangForm.java: bỏ update tồn kho ngay khi tạo phiếu
   - View/XuatHangForm.java: bỏ update tồn kho ngay, validate bằng soLuongKhaDung, hiển thị cột "Khả dụng"
   - View/PhieuNhapForm.java: thêm cột Trạng thái, nút Hoàn thành/Hủy, transaction khi hoàn thành, guard sửa/xóa
   - View/PhieuXuatForm.java: tương tự + validate soLuong thực tế đủ trước khi hoàn thành
✅ Batch 3 (Tài liệu hóa): Tạo business_rule.md — tổng hợp toàn bộ ràng buộc nghiệp vụ
⏳ Batch 4 (Cảnh báo tồn kho thấp): TonKhoForm — highlight màu khi soLuong = 0 hoặc ≤ 5
⏳ Batch 5 (Role-based Access): NhapKho, XuatKho, QuanLiKho — giới hạn menu theo role
⏳ Batch 6 (Email Recovery): SendEmailSMTP, pom.xml — gửi email OTP thực qua Gmail SMTP

Ghi chú sửa thêm trong quá trình:
- model/Laptop.java: thêm constructor 12 tham số (tự điền loaimay="Laptop")
- model/PC.java: thêm constructor 12 tham số (tự điền loaimay="PC")
- DAO/Daomaytinh.java: thêm selectAllExist(), updateSoLuong()


## Phase 1 — Controller Layer (package `controller`)

### 1. BCrypt.java
Wrap thư viện jBCrypt (đã có trong pom.xml).
```
hashPassword(String plain) → String
checkPassword(String plain, String hashed) → boolean
```

### 2. ConvertDate.java
Tiện ích chuyển đổi ngày tháng.
```
toSqlDate(java.util.Date) → java.sql.Date
toUtilDate(java.sql.Date) → java.util.Date
format(Timestamp ts) → String  // "dd/MM/yyyy HH:mm"
parseDate(String s) → java.util.Date
```

### 3. SearchProduct.java
Lọc `ArrayList<MayTinh>` theo keyword và field.
```
search(ArrayList<MayTinh> list, String keyword, String field) → ArrayList<MayTinh>
// field: "Tất cả", "Mã máy", "Tên máy", "Loại máy"
```

### 4. SearchAccount.java
Lọc `ArrayList<Account>` theo keyword và field.
```
search(ArrayList<Account> list, String keyword, String field) → ArrayList<Account>
// field: "Tất cả", "Tên đăng nhập", "Họ tên", "Email"
```

### 5. SearchNhaCungCap.java
Lọc `ArrayList<NhaCungCap>` theo keyword và field.
```
search(ArrayList<NhaCungCap> list, String keyword, String field) → ArrayList<NhaCungCap>
// field: "Tất cả", "Mã NCC", "Tên NCC", "SĐT"
```

### 6. WritePDF.java
Xuất dữ liệu JTable ra file PDF. Dùng **iText 5** (thêm vào pom.xml).
```
exportTable(JTable table, String title, String filePath) → void
```
Cần thêm dependency vào pom.xml:
```xml
<dependency>
  <groupId>com.itextpdf</groupId>
  <artifactId>itextpdf</artifactId>
  <version>5.5.13.3</version>
</dependency>
```

### 7. SendEmailSMTP.java — Placeholder
Tạm thời để class rỗng với TODO. Chức năng khôi phục mật khẩu sẽ làm sau.
```java
public class SendEmailSMTP {
    // TODO: implement SMTP email recovery
}
```

---

## Phase 2 — View Logic Rewrite

Giữ nguyên phần `initComponents()` (UI), chỉ viết lại event handlers và các method helper.  
Tất cả View đều dùng package `view`. Gọi DAO bằng singleton: `DaoAccount.getInstance()`, v.v.

### Thứ tự ưu tiên

#### 2.1 Login.java
- `JPaneLoginMouseClicked`: lấy user/pass → `DaoAccount.getInstance().selectById(user)` → `BCrypt.checkPassword()` → mở `Admin`
- `jLabel7MouseClicked`: mở `RecoverPassword`
- Xử lý Enter key trên cả 2 field

#### 2.2 Admin.java
- Constructor nhận `Account currentAcc`
- Mỗi menu panel MouseClicked: tạo JInternalFrame tương ứng, add vào `MainContent`, `setVisible(true)`
- `DangXuatMouseClicked`: đóng Admin, mở lại Login
- Hiển thị tên user lên `NameUser` label

#### 2.3 ProductForm.java
- Load: `Daomaytinh.getInstance().selectAll()` → đổ vào `tblSanPham` (DefaultTableModel)
- `btnAdd` → mở `AddProduct` dialog
- `btnEdit` → lấy row được chọn → mở `UpdateProduct`
- `btnDelete` → confirm → `Daomaytinh.getInstance().delete()`
- `btnDetail` → mở `DetailProduct`
- `jTextFieldSearchKeyReleased` + `jComboBoxLuaChon` → `SearchProduct.search()`
- Xuất Excel: Apache POI (đã có trong pom.xml)
- `jButton7` (Làm mới): reload table

#### 2.4 NhaCungCapForm.java
- Load: `DaoNhaCungCap.getInstance().selectAll()` → `tblNCC`
- `btnAdd` → `AddNhaCungCap`, `jButton5` → `UpdateNhaCungCap`, `jButton4` → delete
- Search: `SearchNhaCungCap.search()`

#### 2.5 AccountForm.java
- Load: `DaoAccount.getInstance().selectAll()` → `tblAccount`
- `btnAdd` → `AddAccount`, `btnEditAccount` → `UpdateAccount`
- `btnDeleteAccount` → confirm → `DaoAccount.getInstance().delete()`
- `btnEditAccount1` (reset pass) → generate random pass → `BCrypt.hashPassword()` → `DaoAccount.getInstance().update()`
- Search: `SearchAccount.search()`

#### 2.6 NhapHangForm.java
- Load danh sách sản phẩm từ `Daomaytinh` vào `tblSanPham`
- Load nhà cung cấp vào `cboNhaCungCap`
- Auto-generate `txtMaPhieu` (UUID ngắn), fill `txtNguoiTao` từ Account đăng nhập
- `addProduct`: chuyển sản phẩm được chọn từ `tblSanPham` sang `tblNhapHang`, tính tổng tiền
- `deleteProduct`: xóa khỏi `tblNhapHang`
- `btnNhapHang`: tạo `PhieuNhap` + list `ChiTietPhieu` → `DaoPhieuNhap.insert()` + `DaoChiTietPhieuNhap.insert()` cho từng dòng → cập nhật `soLuong` trong `Daomaytinh`
- Xuất PDF: `WritePDF.exportTable()`

#### 2.7 XuatHangForm.java
- Tương tự NhapHangForm nhưng dùng `DaoPhieuXuat` + `DaoChiTietPhieuXuat`
- Kiểm tra tồn kho trước khi xuất (soLuong đủ không)

#### 2.8 PhieuNhapForm.java
- Load: `DaoPhieuNhap.getInstance().selectAll()` → bảng
- Double-click row → mở `CTPhieuNhap` chi tiết

#### 2.9 PhieuXuatForm.java
- Tương tự, dùng `DaoPhieuXuat`

#### 2.10 TonKhoForm.java
- Load: `Daomaytinh.getInstance().selectAll()` → `tblSanPham`
- Search: `SearchProduct.search()`
- Xuất Excel (jButton6)

#### 2.11 ThongKeForm.java
- Tab Sản phẩm: load + filter theo tên, giá, ngày (dùng `ConvertDate`)
- Tab Phiếu: load `PhieuNhap` + `Phieuxuat`, filter theo ngày tháng + người tạo
- Tab Tài khoản: load `Account`, click row → mở `CTThongKeAcc`
- Hiển thị tổng số lượng sản phẩm/NCC/người dùng lên các label
- Xuất PDF: `WritePDF.exportTable()`

#### 2.12 Dialog forms
| Form | Chức năng |
|------|-----------|
| `AddProduct` | JComboBox chọn loại (Laptop/PC) → hiện/ẩn field tương ứng → insert |
| `UpdateProduct` | Nhận `MayTinh` → fill field → update |
| `DetailProduct` | Nhận `MayTinh` → hiển thị read-only |
| `AddNhaCungCap` | insert NhaCungCap |
| `UpdateNhaCungCap` | Nhận `NhaCungCap` → update |
| `AddAccount` | insert Account, hash password bằng BCrypt |
| `UpdateAccount` | Nhận `Account` → update |
| `ChangePassword` | oldPass check → BCrypt → updatePassword |
| `RecoverPassword` | Placeholder (email chưa làm) |
| `CTPhieuNhap` | Nhận `PhieuNhap` → load chi tiết từ DaoChiTietPhieuNhap, WritePDF |
| `CTPhieuXuat` | Tương tự với PhieuXuat |
| `CTThongKe` | Nhận data thống kê → hiển thị |
| `CTThongKeAcc` | Nhận Account → hiển thị phiếu của account đó |

---

## Files sẽ tạo mới
```
src/main/java/controller/BCrypt.java
src/main/java/controller/ConvertDate.java
src/main/java/controller/SearchProduct.java
src/main/java/controller/SearchAccount.java
src/main/java/controller/SearchNhaCungCap.java
src/main/java/controller/WritePDF.java
src/main/java/controller/SendEmailSMTP.java   (placeholder)
```

## Files sẽ sửa
```
pom.xml                              — thêm iText 5 dependency
src/main/java/View/Login.java        — viết lại event handlers
src/main/java/View/Admin.java        — viết lại navigation
(tất cả View/*.java còn lại)
```

## Files KHÔNG thay đổi
```
src/main/java/DAO/*.java             — giữ nguyên
src/main/java/model/*.java           — giữ nguyên
src/main/java/database/JDBCUtil.java — giữ nguyên
```

---

## Verification
1. `mvn compile` không còn lỗi compile
2. Chạy app → Login với account có sẵn trong DB → vào được Admin
3. CRUD từng module: thêm/sửa/xóa sản phẩm, NCC, tài khoản
4. Tạo phiếu nhập → kiểm tra số lượng tồn kho tăng
5. Tạo phiếu xuất → kiểm tra số lượng tồn kho giảm, cảnh báo nếu không đủ
6. Xuất PDF từ 1 phiếu nhập/xuất → file mở được
