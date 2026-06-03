-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th5 21, 2026 lúc 05:38 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `quanlimaytinh`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `account`
--

CREATE TABLE `account` (
  `fullName` varchar(50) DEFAULT NULL,
  `userName` varchar(50) NOT NULL,
  `password` varchar(60) DEFAULT NULL,
  `role` varchar(50) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `account`
--

INSERT INTO `account` (`fullName`, `userName`, `password`, `role`, `status`, `email`) VALUES
('Admin', 'admin', '$2a$12$Zj6NkRCSywQvG88JyYg8y.ythcrqXJh8Ot6NRSKrwyOBSSQKssds6', 'Admin', 1, 'huymonsterhuman@gmail.com'),
('Hoàng Gia Bảo', 'bobo', '$2a$10$iMUMTCBsPAr6SoUhECeFHuiZY/wvZRyvlxnn2olokWxdLRhr0rjqW', 'Nhân viên xuất', 1, 'hgiabao2k3@gmail.com'),
('huyluong', 'huymonster', '$2a$10$En12bh//4WLXo2g8v6YrLu2/gDVvOKJ4HtaE.Q7SmEeC7btj9cn2a', 'Nhân viên nhập', 1, 'huymonoa@gmail.com'),
('Trần Nhật Sinh', 'sinhsinh1122', '$2a$10$FMT8aXAPS/pTOSECKaq55OKHcjZ8WrwiSXPjb1XOV8SNgGXQZFKZG', 'Nhân viên nhập', 1, 'transinh342@gmail.com'),
('Nguyễn Thiên Ân', 'thienan', '$2a$10$rTUz6UecHu1wTK6VJa1mPu3SA2vpZu3gA3vHuYh7IZU3mJ625S75S', 'Quản lý kho', 1, 'a11611112003@gmail.com');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietphieunhap`
--

CREATE TABLE `chitietphieunhap` (
  `maPhieu` varchar(50) NOT NULL,
  `maMay` varchar(50) NOT NULL,
  `soLuong` int(11) DEFAULT NULL,
  `donGia` double DEFAULT NULL,
  `soLuongConLai` int(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietphieunhap`
--

INSERT INTO `chitietphieunhap` (`maPhieu`, `maMay`, `soLuong`, `donGia`, `soLuongConLai`) VALUES
('DAU_KY', 'LP10', 40, 0, 40),
('DAU_KY', 'LP12', 23, 0, 23),
('DAU_KY', 'LP13', 26, 0, 26),
('DAU_KY', 'LP14', 10, 0, 10),
('DAU_KY', 'LP15', 28, 0, 28),
('DAU_KY', 'LP16', 62, 0, 62),
('DAU_KY', 'LP17', 22, 0, 22),
('DAU_KY', 'LP18', 23, 0, 23),
('DAU_KY', 'LP19', 19, 0, 19),
('DAU_KY', 'LP20', 37, 0, 37),
('DAU_KY', 'LP21', 18, 0, 18),
('DAU_KY', 'LP22', 21, 0, 21),
('DAU_KY', 'LP23', 42, 0, 39),
('DAU_KY', 'LP24', 34, 0, 34),
('DAU_KY', 'LP25', 53, 0, 53),
('DAU_KY', 'LP3', 81, 0, 81),
('DAU_KY', 'LP4', 118, 0, 118),
('DAU_KY', 'LP5', 11, 0, 11),
('DAU_KY', 'LP6', 90, 0, 87),
('DAU_KY', 'LP7', 19, 0, 19),
('DAU_KY', 'LP8', 60, 0, 60),
('DAU_KY', 'LP9', 25, 0, 25),
('DAU_KY', 'PC06', 19, 0, 19),
('DAU_KY', 'PC1', 26, 0, 26),
('DAU_KY', 'PC2', 30, 0, 30),
('DAU_KY', 'PC3', 22, 0, 22),
('DAU_KY', 'PC30', 15, 0, 15),
('DAU_KY', 'PC4', 71, 0, 71),
('DAU_KY', 'PC5', 39, 0, 39),
('DAU_KY', 'PC7', 34, 0, 34),
('PN10', 'LP16', 1, 22990000, 0),
('PN10', 'LP22', 1, 23490000, 0),
('PN10', 'LP9', 4, 16490000, 0),
('PN11', 'LP17', 1, 23190000, 0),
('PN11', 'LP25', 1, 18390000, 0),
('PN11', 'PC1', 8, 7090000, 0),
('PN14', 'LP19', 1, 19490000, 0),
('PN14', 'LP20', 1, 20790000, 0),
('PN14', 'LP4', 1, 10690000, 0),
('PN15', 'LP14', 1, 22490000, 0),
('PN15', 'LP6', 1, 17490000, 0),
('PN16', 'LP17', 1, 23190000, 0),
('PN16', 'LP5', 1, 19290000, 0),
('PN16', 'PC06', 1, 9690000, 0),
('PN17', 'LP19', 1, 19490000, 0),
('PN17', 'LP4', 1, 10690000, 0),
('PN18', 'LP15', 1, 25190000, 0),
('PN18', 'LP5', 1, 19290000, 0),
('PN18', 'LP6', 1, 17490000, 0),
('PN18', 'PC06', 1, 9690000, 0),
('PN18', 'PC1', 1, 7090000, 0),
('PN19', 'LP18', 1, 24990000, 0),
('PN19', 'LP24', 1, 21490000, 0),
('PN19', 'LP4', 1, 10690000, 0),
('PN19', 'PC06', 1, 9690000, 0),
('PN2', 'LP20', 1, 20790000, 0),
('PN2', 'LP21', 1, 25990000, 0),
('PN2', 'LP23', 5, 15690000, 0),
('PN20', 'LP16', 1, 22990000, 0),
('PN20', 'LP25', 10, 18390000, 0),
('PN20', 'LP5', 1, 19290000, 0),
('PN20', 'PC1', 1, 7090000, 0),
('PN21', 'LP16', 45, 22990000, 0),
('PN21', 'LP8', 25, 18390000, 0),
('PN22', 'LP10', 1, 23490000, 0),
('PN22', 'LP15', 1, 25190000, 0),
('PN22', 'LP22', 1, 23490000, 0),
('PN22', 'LP6', 1, 17490000, 0),
('PN23', 'LP19', 2, 19490000, 0),
('PN23', 'LP5', 2, 19290000, 0),
('PN23', 'LP7', 2, 17490000, 0),
('PN23', 'PC30', 3, 25000000, 0),
('PN24', 'PC3', 3, 8990000, 0),
('PN24', 'PC7', 23, 116990000, 0),
('PN25', 'LP14', 1, 22490000, 0),
('PN25', 'PC7', 3, 11200000, 0),
('PN26', 'LP15', 2, 25190000, 0),
('PN26', 'LP21', 2, 25990000, 0),
('PN28', 'LP15', 2, 25190000, 0),
('PN28', 'LP24', 1, 21490000, 0),
('PN28', 'PC7', 7, 11200000, 0),
('PN29', 'LP20', 1, 20790000, 0),
('PN29', 'LP4', 1, 10690000, 0),
('PN3', 'LP15', 1, 25190000, 0),
('PN3', 'LP22', 1, 23490000, 0),
('PN3', 'LP25', 1, 18390000, 0),
('PN3', 'LP4', 2, 10690000, 0),
('PN30', 'LP16', 1, 22990000, 0),
('PN30', 'LP24', 1, 21490000, 0),
('PN32', 'LP18', 1, 24990000, 0),
('PN32', 'LP19', 1, 19490000, 0),
('PN32', 'LP5', 1, 19290000, 0),
('PN32', 'LP9', 1, 16490000, 0),
('PN32', 'PC2', 1, 8290000, 0),
('PN32', 'PC7', 1, 11200000, 0),
('PN33', 'LP14', 1, 22490000, 0),
('PN33', 'LP22', 1, 23490000, 0),
('PN33', 'LP3', 1, 15000000, 0),
('PN33', 'PC1', 1, 7090000, 0),
('PN33', 'PC30', 1, 25000000, 0),
('PN34', 'LP20', 17, 20790000, 0),
('PN34', 'LP25', 7, 18390000, 0),
('PN34', 'LP6', 20, 17490000, 0),
('PN34', 'LP8', 1, 18390000, 0),
('PN34', 'PC2', 2, 8290000, 0),
('PN35', 'LP22', 1, 23490000, 0),
('PN35', 'LP24', 1, 21490000, 0),
('PN35', 'LP4', 1, 10690000, 0),
('PN35', 'LP8', 1, 18390000, 0),
('PN35', 'PC5', 1, 9190000, 0),
('PN36', 'LP14', 7, 22490000, 0),
('PN36', 'LP19', 20, 19490000, 0),
('PN37', 'LP17', 1, 23190000, 0),
('PN37', 'LP19', 1, 19490000, 0),
('PN37', 'LP4', 1, 10690000, 0),
('PN37', 'LP8', 3, 18390000, 0),
('PN38', 'LP13', 10, 9990000, 0),
('PN38', 'LP23', 20, 15690000, 0),
('PN38', 'LP6', 30, 17490000, 0),
('PN39', 'PC1', 10, 7090000, 0),
('PN39', 'PC30', 10, 25000000, 0),
('PN39', 'PC5', 6, 9190000, 0),
('PN40', 'LP27', 5, 800000, 0),
('PN41', 'LP10', 4, 23490000, 0),
('PN42', 'LP27', 5, 800000, 0),
('PN43', 'LP27', 50, 800000, 0),
('PN44', 'LP24', 5, 21490000, 5),
('PN44', 'LP27', 5, 800000, 1),
('PN44', 'PC30', 5, 25000000, 5),
('PN45', 'LP27', 10, 800000, 10),
('PN45', 'LP3', 2, 15000000, 2),
('PN46', 'LP13', 10, 9990000, 10),
('PN47', 'LP5', 20, 19290000, 0),
('PN48', 'LP5', 10, 19290000, 0),
('PN5', 'LP4', 3, 10690000, 0),
('PN6', 'LP17', 1, 23190000, 0),
('PN6', 'LP3', 1, 15000000, 0),
('PN7', 'LP15', 1, 25190000, 0),
('PN7', 'LP25', 1, 18390000, 0),
('PN7', 'LP8', 5, 18390000, 0),
('PN8', 'LP19', 1, 19490000, 0),
('PN8', 'LP4', 1, 10690000, 0),
('PN8', 'LP9', 1, 16490000, 0),
('PN9', 'LP21', 1, 25990000, 0),
('PN9', 'LP6', 1, 17490000, 0);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitietphieuxuat`
--

CREATE TABLE `chitietphieuxuat` (
  `maPhieu` varchar(50) NOT NULL,
  `maMay` varchar(50) NOT NULL,
  `soLuong` int(11) DEFAULT NULL,
  `donGia` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `chitietphieuxuat`
--

INSERT INTO `chitietphieuxuat` (`maPhieu`, `maMay`, `soLuong`, `donGia`) VALUES
('PX1', 'LP10', 1, 23490000),
('PX1', 'LP19', 13, 19490000),
('PX1', 'LP3', 1, 15000000),
('PX10', 'LP20', 1, 20790000),
('PX10', 'LP4', 1, 10690000),
('PX10', 'LP8', 1, 18390000),
('PX10', 'PC06', 1, 9690000),
('PX10', 'PC2', 1, 8290000),
('PX12', 'LP20', 1, 20790000),
('PX12', 'LP6', 1, 17490000),
('PX12', 'PC1', 1, 7090000),
('PX13', 'LP18', 1, 24990000),
('PX13', 'LP5', 2, 19290000),
('PX13', 'LP6', 1, 17490000),
('PX13', 'PC1', 4, 7090000),
('PX14', 'LP20', 1, 20790000),
('PX14', 'LP24', 1, 21490000),
('PX14', 'LP8', 1, 18390000),
('PX14', 'PC06', 1, 9690000),
('PX14', 'PC2', 1, 8290000),
('PX15', 'LP17', 1, 23190000),
('PX15', 'LP20', 1, 20790000),
('PX15', 'LP5', 1, 19290000),
('PX15', 'LP9', 1, 16490000),
('PX15', 'PC1', 1, 7090000),
('PX16', 'LP14', 4, 22490000),
('PX16', 'LP20', 1, 20790000),
('PX16', 'LP21', 1, 25990000),
('PX17', 'LP21', 2, 25990000),
('PX18', 'LP16', 5, 22990000),
('PX18', 'LP8', 2, 18390000),
('PX19', 'LP18', 1, 24990000),
('PX19', 'LP23', 1, 15690000),
('PX19', 'PC06', 1, 9690000),
('PX19', 'PC3', 1, 8990000),
('PX2', 'LP21', 1, 25990000),
('PX2', 'LP6', 2, 17490000),
('PX2', 'PC06', 1, 9690000),
('PX20', 'LP6', 2, 17490000),
('PX20', 'LP9', 1, 16490000),
('PX20', 'PC06', 1, 9690000),
('PX20', 'PC3', 2, 8990000),
('PX21', 'LP23', 1, 15690000),
('PX21', 'LP7', 1, 17490000),
('PX21', 'PC06', 2, 9690000),
('PX21', 'PC2', 1, 8290000),
('PX22', 'LP5', 1, 19290000),
('PX22', 'LP9', 1, 16490000),
('PX23', 'LP23', 1, 15690000),
('PX23', 'PC06', 1, 9690000),
('PX23', 'PC1', 1, 7090000),
('PX23', 'PC3', 1, 8990000),
('PX24', 'LP19', 1, 19490000),
('PX24', 'LP8', 1, 18390000),
('PX24', 'LP9', 1, 16490000),
('PX24', 'PC3', 1, 8990000),
('PX25', 'LP19', 1, 19490000),
('PX25', 'LP7', 2, 17490000),
('PX25', 'PC1', 1, 7090000),
('PX26', 'LP17', 1, 23190000),
('PX26', 'LP19', 1, 19490000),
('PX26', 'LP22', 1, 23490000),
('PX26', 'LP3', 1, 15000000),
('PX26', 'LP5', 1, 19290000),
('PX26', 'LP6', 1, 17490000),
('PX27', 'LP13', 3, 9990000),
('PX28', 'LP27', 2, 800000),
('PX28', 'LP3', 1, 15000000),
('PX29', 'LP27', 3, 800000),
('PX3', 'LP22', 1, 23490000),
('PX3', 'LP4', 1, 10690000),
('PX3', 'LP8', 1, 18390000),
('PX30', 'LP27', 3, 800000),
('PX31', 'LP27', 2, 800000),
('PX32', 'LP23', 3, 15690000),
('PX32', 'LP27', 4, 800000),
('PX32', 'LP6', 3, 17490000),
('PX33', 'LP5', 5, 19290000),
('PX4', 'LP17', 1, 23190000),
('PX4', 'LP6', 1, 17490000),
('PX4', 'LP7', 1, 17490000),
('PX5', 'LP16', 1, 22990000),
('PX5', 'LP5', 1, 19290000),
('PX5', 'LP7', 1, 17490000),
('PX5', 'LP9', 1, 16490000),
('PX5', 'PC3', 1, 8990000),
('PX6', 'LP12', 1, 13090000),
('PX6', 'LP13', 1, 9990000),
('PX6', 'LP15', 1, 25190000),
('PX6', 'LP17', 1, 23190000),
('PX7', 'LP21', 1, 25990000),
('PX7', 'LP5', 1, 19290000),
('PX7', 'PC1', 1, 7090000),
('PX8', 'LP24', 1, 21490000),
('PX8', 'LP3', 1, 15000000),
('PX8', 'LP5', 1, 19290000),
('PX8', 'LP8', 1, 18390000),
('PX8', 'PC1', 1, 7090000),
('PX9', 'LP21', 1, 25990000),
('PX9', 'LP22', 1, 23490000),
('PX9', 'LP4', 1, 10690000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `maytinh`
--

CREATE TABLE `maytinh` (
  `maMay` varchar(50) NOT NULL,
  `tenMay` varchar(100) DEFAULT NULL,
  `soLuong` int(11) NOT NULL DEFAULT 0,
  `tenCpu` varchar(50) NOT NULL DEFAULT '0',
  `ram` varchar(50) NOT NULL DEFAULT '0',
  `cardManHInh` varchar(50) DEFAULT NULL,
  `gia` double NOT NULL DEFAULT 0,
  `mainBoard` varchar(50) DEFAULT NULL,
  `congSuatNguon` int(11) DEFAULT NULL,
  `loaiMay` varchar(50) DEFAULT NULL,
  `rom` varchar(50) DEFAULT NULL,
  `kichThuocMan` double DEFAULT NULL,
  `dungLuongPin` varchar(50) DEFAULT NULL,
  `xuatXu` varchar(50) DEFAULT NULL,
  `trangThai` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `maytinh`
--

INSERT INTO `maytinh` (`maMay`, `tenMay`, `soLuong`, `tenCpu`, `ram`, `cardManHInh`, `gia`, `mainBoard`, `congSuatNguon`, `loaiMay`, `rom`, `kichThuocMan`, `dungLuongPin`, `xuatXu`, `trangThai`) VALUES
('LP10', 'Laptop Lenovo IdeaPad Gaming 3', 40, 'Intel Core i5 12500H', '16 GB', 'NVIDIA GeForce RTX 3050', 23490000, NULL, NULL, 'Laptop', '512 GB', 15.6, '4', 'Trung Quốc', 1),
('LP12', 'Laptop MSI Modern 14 B11MOU-1028VN', 23, 'Intel Core i3 115G4', '8 GB', 'Intel UHD Graphics', 13090000, NULL, NULL, 'Laptop', '256 GB', 14, '3', 'Trung Quốc', 1),
('LP13', 'Laptop HP 15s-fq2663TU', 36, 'Intel Core i3 1115G4', '4 GB', 'Intel UHD Graphics', 9990000, NULL, NULL, 'Laptop', '256 GB', 15.6, '3 Cell', 'Trung Quốc', 1),
('LP14', 'Laptop Lenovo IdeaPad 5 Pro 16IAH7', 10, 'Intel Core i5 12500H', '16 GB', 'Intel Iris Xe Graphics', 22490000, NULL, NULL, 'Laptop', '512 GB', 16, '4 Cell', 'Trung Quốc', 1),
('LP15', 'Laptop Lenovo IdeaPad 5 Pro 16IAH7', 28, 'Intel Core i7 12700H', '16 GB', 'Intel Iris Xe Graphics', 25190000, NULL, NULL, 'Laptop', '512 GB', 16, '75 Wh', 'Trung Quốc', 1),
('LP16', 'Laptop Acer Nitro Gaming AN515-57-54MV', 62, 'Intel Core i5 11400H', '8', 'NVIDIA GeForce RTX 3050', 22990000, NULL, NULL, 'Laptop', '512 GB', 15.6, '4 Cell ', 'Trung Quốc', 1),
('LP17', 'Laptop MSI Gaming Katana GF66 12UCK-815VN', 22, 'Intel Core i5 12450H', '8 GB', 'Intel UHD Graphics', 23190000, NULL, NULL, 'Laptop', '512 GB', 15.6, '53.5 Wh', 'Trung Quốc', 1),
('LP18', 'Laptop Asus TUF Gaming FX517ZC-HN077W', 23, 'Intel Core i5 12450H', '8 GB', 'NVIDIA GeForce RTX 3050', 24990000, NULL, NULL, 'Laptop', '512 GB', 15.6, '4 Cell', 'Trung Quốc', 1),
('LP19', 'Laptop HP Gaming Victus 16-e0175AX', 19, 'AMD Ryzen 5 5600H', '8 GB', 'NVIDIA GeForce RTX 3050 Ti', 19490000, NULL, NULL, 'Laptop', '512 GB', 16.1, '4 Cell', 'Trung Quốc', 1),
('LP20', 'Laptop MSI GF63 Thin 11UC-444VN', 37, 'Intel Core i5 11400H', '8 GB', 'NVIDIA GeForce RTX 3050', 20790000, NULL, NULL, 'Laptop', '512 GB', 15.6, '3 Cell', 'Trung Quốc', 1),
('LP21', 'Laptop Asus TUF Gaming FX517ZE-HN045W', 18, 'Intel Core i5 12450H', '8 GB', 'NVIDIA GeForce RTX 3050 Ti', 25990000, NULL, NULL, 'Laptop', '512 GB', 15.6, '4 Cell', 'Trung Quốc', 1),
('LP22', 'Laptop Lenovo Yoga Slim 7 Pro 14IHU5O', 21, 'Intel Core i5 11300H', '16 GB', 'Intel Iris Xe Graphics', 23490000, NULL, NULL, 'Laptop', '512 GB', 14, '4 Cell ', 'Trung Quốc', 1),
('LP23', 'Laptop Gigabyte U4 UD-50VN823SO', 39, 'Intel Core i5 1155G7', '16 GB', 'Intel Iris Xe Graphics', 15690000, NULL, NULL, 'Laptop', '512 GB', 14, '36 Wh', 'Trung Quốc', 1),
('LP24', 'Laptop Dell Vostro V5410 i5', 39, 'Intel Core i5 11320H', '8 GB', 'Intel Iris Xe Graphics', 21490000, NULL, NULL, 'Laptop', '512 GB', 14, '4 Cell', 'Trung Quốc', 1),
('LP25', 'Laptop MSI Gaming GF63 Thin 11SC-666VN', 53, 'Intel Core i5 11400H', '8 GB', 'NVIDIA GeForce GTX 1650', 18390000, NULL, NULL, 'Laptop', '512 GB', 15.6, '3 Cell', 'Trung Quốc', 1),
('LP27', 'test', 11, 'AMD', '16GB', 'NVDIA', 800000, NULL, NULL, 'Laptop', '512GB', 7.8, '5000', 'tq', 1),
('LP3', 'Lenovo ThinkPad E14', 83, 'Intel Core i5 11G352', '8GB', 'OnBoard', 15000000, NULL, NULL, 'Laptop', '521GB', 14, '45Wh', 'Trung Quốc', 1),
('LP4', 'Lenovo Ideapad 3 15ITL6', 118, 'Intel Core i3 1115G4', '8GB', 'Onboard', 10690000, NULL, NULL, 'Laptop', '512GB', 15.6, '35Wh', 'Trung Quốc', 1),
('LP5', 'Gigabyte Gaming G5 GD', 11, 'Intel Core i5 11400H', '16GB', 'NVIDIA GeForce RTX 3050 4GB', 19290000, NULL, NULL, 'Laptop', '512GB', 15.6, '50Wh', 'Trung Quốc', 1),
('LP6', 'MSI Gaming GF63 Thin 11SC-1090VN', 87, 'Intel Core i5 11400H', '8GB', 'NVIDIA GeForce GTX 1650 4GB', 17490000, NULL, NULL, 'Laptop', '512GB', 15.6, '50Wh', 'Trung Quốc', 1),
('LP7', 'Laptop Asus TUF Gaming FX506LHB-HN188W', 19, 'Intel Core i5 10300H', '8 GB', 'NVIDIA GeForce GTX 1650', 17490000, NULL, NULL, 'Laptop', '512 GB', 15.6, '3 Cell', 'Trung Quốc', 1),
('LP8', 'Laptop MSI Gaming GF63 Thin 11SC-1090VN', 60, 'Intel Core i5 11400H', '8 GB', 'NVIDIA GeForce GTX 1650 4GB', 18390000, NULL, NULL, 'Laptop', '512 GB', 15.6, '3 Cell', 'Trung Quốc', 1),
('LP9', 'Laptop Asus TUF Gaming FA506IHRB-HN019W', 25, 'AMD Ryzen 5 4600H', '8 GB', 'NVIDIA GeForce GTX 1650', 16490000, NULL, NULL, 'Laptop', '512 GB', 12, '3 cell', 'Trung Quốc', 1),
('PC06', 'PC E-Power Office 08', 19, 'Intel Core i5 11400', '16 GB', 'Intel UHD Graphics 730', 9690000, 'Intel H510', 9690000, 'PC', '240 GB', NULL, NULL, 'Việt Nam', 1),
('PC1', 'PC E-Power Office 04', 26, 'Intel Core i3 10105', '8GB', 'Intel HD Graphics 630', 7090000, 'Lacer', 5000, 'PC', '240GB', NULL, NULL, 'Việt Nam', 1),
('PC2', 'PC E-Power Office 05', 30, 'Intel Core i5 10400', '8 GB', 'Intel UHD Graphics 630', 8290000, 'Intel H510', 300, 'PC', '8 GB', NULL, NULL, 'Việt Nam', 1),
('PC3', 'PC E-Power Office 07', 22, 'Intel Core i5 11400', '8 GB', 'Intel UHD Graphics 730', 8990000, 'Intel H510', 8990000, 'PC', '240 GB', NULL, NULL, 'Việt Nam', 1),
('PC30', 'ASUS Vivobook', 20, 'Ryzen 7 5800HF', '16GB', 'GTX 3060', 25000000, 'LAcer', 5000, 'PC', '512GB', NULL, NULL, 'Việt Nam', 1),
('PC4', 'PC Gaming E-Power G1650', 71, 'Intel Core i3 10100F', '8 GB', 'Intel UHD Graphics 730', 11990000, 'Intel H510', 300, 'PC', '240 GB', NULL, NULL, 'Việt Nam', 1),
('PC5', 'PC E-Power Office 06', 39, 'Intel Core i5 10400', '16 GB', 'Intel HD Graphics 630', 9190000, 'Intel H510', 200, 'PC', '240 GB', NULL, NULL, 'Việt Nam', 1),
('PC7', 'PC Acer Aspire AS-XC780 DT.B8ASV.006', 34, ' Intel Core i5-7400', '4GB', ' Intel HD Graphics 630 / GeForce GT 720 2GB', 11200000, 'Intel H510', 300, 'PC', '1TB', NULL, NULL, 'Việt Nam', 1);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhacungcap`
--

CREATE TABLE `nhacungcap` (
  `maNhaCungCap` varchar(50) NOT NULL,
  `tenNhaCungCap` varchar(50) DEFAULT NULL,
  `Sdt` varchar(50) DEFAULT NULL,
  `diaChi` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `nhacungcap`
--

INSERT INTO `nhacungcap` (`maNhaCungCap`, `tenNhaCungCap`, `Sdt`, `diaChi`) VALUES
('ANPHAT', 'Công Ty TNHH Điều Khiển Tự Động An Phát', '02835109735', '86/21 Phan Tây Hồ, P. 7, Q. Phú Nhuận TP. Hồ Chí Minh'),
('CODO', 'Công Ty TNHH Thương Mại Dịch Vụ Hoàng Cố Đô', '02838115345', '622/16/5 Cộng Hòa, Phường 13, Quận Tân Bình, TP HCM		'),
('FPT', 'Công Ty Cổ Phần Bán Lẻ Kỹ Thuật Số FPT', '02873023456', '261 - 263 Khánh Hội, P2, Q4, TP. Hồ Chí Minh'),
('HACOM', 'Công ty Cổ phần đầu tư công nghệ HACOM', '1900 1903', 'Số 129 - 131, phố Lê Thanh Nghị, Phường Đồng Tâm, Quận Hai Bà Trưng, Hà Nội'),
('HOANGPHAT', 'Công Ty TNHH Thương Mại Hoàng Phát Hải Phòng', '02253250888', 'Số 4, Lô 2A Lê Hồng Phong, Ngô Quyền, Tp. Hải Phòng'),
('PHONGVU', 'Công ty cổ phần dịch vụ - thương mại Phong Vũ', '0967567567', 'Tầng 5, Số 117-119-121 Nguyễn Du, Phường Bến Thành, Quận 1, Thành Phố Hồ Chí Minh'),
('TGDĐ', 'Công ty cổ phần Thế Giới Di Động', '028 38125960', '128 Trần Quang Khải, P. Tân Định, Q.1, TP.Hồ Chí Minh'),
('VIETSTARS', 'Công ty CP Công nghệ Thương mại Dịch vụ Vietstars', '090 469 0212', ' Số 109 Lê Thanh Nghị  TP Hải dương');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieunhap`
--

CREATE TABLE `phieunhap` (
  `maPhieu` varchar(50) NOT NULL,
  `thoiGianTao` timestamp NULL DEFAULT NULL,
  `nguoiTao` varchar(50) DEFAULT NULL,
  `maNhaCungCap` varchar(50) DEFAULT NULL,
  `tongTien` double NOT NULL,
  `trangThai` varchar(20) NOT NULL DEFAULT 'Chờ xử lý'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieunhap`
--

INSERT INTO `phieunhap` (`maPhieu`, `thoiGianTao`, `nguoiTao`, `maNhaCungCap`, `tongTien`, `trangThai`) VALUES
('DAU_KY', '1999-12-31 17:00:00', NULL, NULL, 0, 'Đã hoàn thành'),
('PN10', '2022-12-07 18:04:19', 'admin', 'HOANGPHAT', 112440000, 'Đã hoàn thành'),
('PN11', '2022-12-07 18:48:21', 'admin', 'FPT', 98300000, 'Đã hoàn thành'),
('PN14', '2022-12-08 12:28:57', 'admin', 'FPT', 50970000, 'Đã hoàn thành'),
('PN15', '2022-12-08 12:36:25', 'admin', 'FPT', 39980000, 'Đã hoàn thành'),
('PN16', '2022-12-08 16:30:48', 'admin', 'HOANGPHAT', 52170000, 'Đã hoàn thành'),
('PN17', '2022-12-09 14:29:43', 'admin', 'FPT', 30180000, 'Đã hoàn thành'),
('PN18', '2022-12-09 17:08:19', 'admin', 'FPT', 78750000, 'Đã hoàn thành'),
('PN19', '2022-12-12 07:09:21', 'admin', 'PHONGVU', 66860000, 'Đã hoàn thành'),
('PN2', '2026-03-11 03:20:12', 'admin', 'FPT', 125230000, 'Đã hoàn thành'),
('PN20', '2022-12-13 19:46:37', 'admin', 'PHONGVU', 233270000, 'Đã hoàn thành'),
('PN21', '2022-12-14 11:54:21', 'admin', 'PHONGVU', 1536180000, 'Đã hoàn thành'),
('PN22', '2022-12-14 12:32:09', 'admin', 'FPT', 89660000, 'Đã hoàn thành'),
('PN23', '2026-03-11 03:21:43', 'admin', 'FPT', 187540000, 'Đã hoàn thành'),
('PN24', '2026-03-11 03:21:02', 'admin', 'FPT', 2717740000, 'Đã hoàn thành'),
('PN25', '2026-03-11 03:20:41', 'admin', 'ANPHAT', 56090000, 'Đã hoàn thành'),
('PN26', '2026-03-11 03:20:23', 'admin', 'ANPHAT', 102360000, 'Đã hoàn thành'),
('PN28', '2026-03-11 03:19:45', 'admin', 'ANPHAT', 150270000, 'Đã hoàn thành'),
('PN29', '2026-03-11 03:18:40', 'admin', 'CODO', 31480000, 'Đã hoàn thành'),
('PN3', '2022-12-03 03:58:18', 'admin', 'FPT', 88450000, 'Đã hoàn thành'),
('PN30', '2026-03-11 03:19:16', 'admin', 'ANPHAT', 44480000, 'Đã hoàn thành'),
('PN32', '2026-03-11 03:19:06', 'admin', 'HOANGPHAT', 99750000, 'Đã hoàn thành'),
('PN33', '2026-03-11 03:17:38', 'sinhsinh1122', 'VIETSTARS', 93070000, 'Đã hoàn thành'),
('PN34', '2026-03-11 03:17:21', 'admin', 'HOANGPHAT', 866930000, 'Đã hoàn thành'),
('PN35', '2026-03-11 03:17:11', 'admin', 'HOANGPHAT', 83250000, 'Đã hoàn thành'),
('PN36', '2026-03-11 03:16:53', 'admin', 'ANPHAT', 547230000, 'Đã hoàn thành'),
('PN37', '2026-03-11 03:16:34', 'admin', 'ANPHAT', 108540000, 'Đã hoàn thành'),
('PN38', '2026-03-11 03:11:52', 'admin', 'ANPHAT', 938400000, 'Đã hoàn thành'),
('PN39', '2026-03-11 03:03:05', 'admin', 'ANPHAT', 376040000, 'Đã hoàn thành'),
('PN40', '2026-04-25 15:51:07', 'admin', 'ANPHAT', 4000000, 'Đã hoàn thành'),
('PN41', '2026-04-25 16:27:50', 'admin', 'ANPHAT', 93960000, 'Đã hoàn thành'),
('PN42', '2026-04-26 02:59:56', 'admin', 'ANPHAT', 4000000, 'Đã hoàn thành'),
('PN43', '2026-04-26 03:02:41', 'admin', 'ANPHAT', 40000000, 'Đã hủy'),
('PN44', '2026-04-26 03:55:40', 'admin', 'ANPHAT', 236450000, 'Đã hoàn thành'),
('PN45', '2026-04-26 03:55:55', 'admin', 'ANPHAT', 38000000, 'Đã hoàn thành'),
('PN46', '2026-05-19 04:17:44', 'huymonster', 'ANPHAT', 99900000, 'Đã hoàn thành'),
('PN47', '2026-05-19 04:19:16', 'huymonster', 'ANPHAT', 385800000, 'Đã hủy'),
('PN48', '2026-05-19 04:20:30', 'huymonster', 'ANPHAT', 192900000, 'Chờ xử lý'),
('PN5', '2022-12-06 17:51:02', 'admin', 'FPT', 32070000, 'Đã hoàn thành'),
('PN6', '2022-12-06 18:50:32', 'admin', 'FPT', 38190000, 'Đã hoàn thành'),
('PN7', '2022-12-06 18:59:43', 'admin', 'PHONGVU', 135530000, 'Đã hoàn thành'),
('PN8', '2022-12-06 19:15:01', 'admin', 'FPT', 46670000, 'Đã hoàn thành'),
('PN9', '2022-12-06 19:20:44', 'admin', 'FPT', 43480000, 'Đã hoàn thành');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phieuxuat`
--

CREATE TABLE `phieuxuat` (
  `maPhieu` varchar(50) NOT NULL,
  `thoiGianTao` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `nguoiTao` varchar(50) NOT NULL,
  `tongTien` double NOT NULL,
  `trangThai` varchar(20) NOT NULL DEFAULT 'Chờ xử lý'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phieuxuat`
--

INSERT INTO `phieuxuat` (`maPhieu`, `thoiGianTao`, `nguoiTao`, `tongTien`, `trangThai`) VALUES
('PX1', '2026-04-25 14:54:34', 'admin', 291860000, 'Đã hoàn thành'),
('PX10', '2026-04-25 14:54:34', 'admin', 57160000, 'Đã hoàn thành'),
('PX12', '2026-04-25 14:54:34', 'admin', 45370000, 'Đã hoàn thành'),
('PX13', '2026-04-25 14:54:34', 'admin', 109420000, 'Đã hoàn thành'),
('PX14', '2026-04-25 14:54:34', 'admin', 78650000, 'Đã hoàn thành'),
('PX15', '2026-04-25 14:54:34', 'admin', 86850000, 'Đã hoàn thành'),
('PX16', '2026-04-25 14:54:34', 'admin', 136740000, 'Đã hoàn thành'),
('PX17', '2026-04-25 14:54:34', 'admin', 51980000, 'Đã hoàn thành'),
('PX18', '2026-04-25 14:54:34', 'admin', 151730000, 'Đã hoàn thành'),
('PX19', '2026-04-25 14:54:34', 'Admin', 59360000, 'Đã hoàn thành'),
('PX2', '2026-04-25 14:54:34', 'admin', 70660000, 'Đã hoàn thành'),
('PX20', '2026-04-25 14:54:34', 'Admin', 79140000, 'Đã hoàn thành'),
('PX21', '2026-04-25 14:54:34', 'Admin', 60850000, 'Đã hoàn thành'),
('PX22', '2026-04-25 14:54:34', 'Admin', 35780000, 'Đã hoàn thành'),
('PX23', '2026-04-25 14:54:34', 'Admin', 41460000, 'Đã hoàn thành'),
('PX24', '2026-04-25 14:54:34', 'Admin', 63360000, 'Đã hoàn thành'),
('PX25', '2026-04-25 14:54:34', 'Admin', 92550000, 'Đã hoàn thành'),
('PX26', '2026-04-25 14:54:34', 'admin', 117950000, 'Đã hoàn thành'),
('PX27', '2026-04-25 14:54:34', 'Admin', 29970000, 'Đã hoàn thành'),
('PX28', '2026-04-25 15:53:07', 'Admin', 16600000, 'Đã hoàn thành'),
('PX29', '2026-04-25 15:54:02', 'Admin', 2400000, 'Đã hoàn thành'),
('PX3', '2026-04-25 14:54:34', 'admin', 89350000, 'Đã hoàn thành'),
('PX30', '2026-04-26 03:01:04', 'Admin', 2400000, 'Đã hoàn thành'),
('PX31', '2026-04-26 03:01:00', 'Admin', 1600000, 'Đã hoàn thành'),
('PX32', '2026-04-26 03:57:21', 'Admin', 102740000, 'Đã hoàn thành'),
('PX33', '2026-05-19 04:21:47', 'bobo', 96450000, 'Đã hủy'),
('PX4', '2026-04-25 14:54:34', 'admin', 58170000, 'Đã hoàn thành'),
('PX5', '2026-04-25 14:54:34', 'admin', 85250000, 'Đã hoàn thành'),
('PX6', '2026-04-25 14:54:34', 'admin', 71460000, 'Đã hoàn thành'),
('PX7', '2026-04-25 14:54:34', 'admin', 52370000, 'Đã hoàn thành'),
('PX8', '2026-04-25 14:54:34', 'admin', 39880000, 'Đã hoàn thành'),
('PX9', '2026-04-25 14:54:34', 'admin', 36680000, 'Đã hoàn thành');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`userName`) USING BTREE;

--
-- Chỉ mục cho bảng `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD PRIMARY KEY (`maPhieu`,`maMay`),
  ADD KEY `FK_ChiTietPhieuNhap_MayTinh` (`maMay`);

--
-- Chỉ mục cho bảng `chitietphieuxuat`
--
ALTER TABLE `chitietphieuxuat`
  ADD PRIMARY KEY (`maPhieu`,`maMay`),
  ADD KEY `FK_ChiTietPhieuXuat_MayTinh` (`maMay`);

--
-- Chỉ mục cho bảng `maytinh`
--
ALTER TABLE `maytinh`
  ADD PRIMARY KEY (`maMay`);

--
-- Chỉ mục cho bảng `nhacungcap`
--
ALTER TABLE `nhacungcap`
  ADD PRIMARY KEY (`maNhaCungCap`);

--
-- Chỉ mục cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD PRIMARY KEY (`maPhieu`),
  ADD KEY `FK_PhieuNhap_NhaCungCap` (`maNhaCungCap`),
  ADD KEY `FK_PhieuNhap_Account` (`nguoiTao`);

--
-- Chỉ mục cho bảng `phieuxuat`
--
ALTER TABLE `phieuxuat`
  ADD PRIMARY KEY (`maPhieu`),
  ADD KEY `FK_PhieuXuat_Account` (`nguoiTao`);

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chitietphieunhap`
--
ALTER TABLE `chitietphieunhap`
  ADD CONSTRAINT `FK_ChiTietPhieuNhap_MayTinh` FOREIGN KEY (`maMay`) REFERENCES `maytinh` (`maMay`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_ChiTietPhieuNhap_PhieuNhap` FOREIGN KEY (`maPhieu`) REFERENCES `phieunhap` (`maPhieu`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `chitietphieuxuat`
--
ALTER TABLE `chitietphieuxuat`
  ADD CONSTRAINT `FK_ChiTietPhieuXuat_MayTinh` FOREIGN KEY (`maMay`) REFERENCES `maytinh` (`maMay`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_ChiTietPhieuXuat_PhieuXuat` FOREIGN KEY (`maPhieu`) REFERENCES `phieuxuat` (`maPhieu`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `phieunhap`
--
ALTER TABLE `phieunhap`
  ADD CONSTRAINT `FK_PhieuNhap_Account` FOREIGN KEY (`nguoiTao`) REFERENCES `account` (`userName`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_PhieuNhap_NhaCungCap` FOREIGN KEY (`maNhaCungCap`) REFERENCES `nhacungcap` (`maNhaCungCap`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Các ràng buộc cho bảng `phieuxuat`
--
ALTER TABLE `phieuxuat`
  ADD CONSTRAINT `FK_PhieuXuat_Account` FOREIGN KEY (`nguoiTao`) REFERENCES `account` (`userName`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
