-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               11.3.2-MariaDB - mariadb.org binary distribution
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for quanlydienthoai
CREATE DATABASE IF NOT EXISTS `quanlydienthoai` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;
USE `quanlydienthoai`;

-- Dumping structure for table quanlydienthoai.dienthoai
CREATE TABLE IF NOT EXISTS `dienthoai` (
  `maDT` int(11) NOT NULL AUTO_INCREMENT,
  `tenDT` varchar(50) DEFAULT NULL,
  `namSanXuat` int(11) DEFAULT NULL,
  `cauHinh` varchar(50) DEFAULT NULL,
  `maNCC` int(11) DEFAULT NULL,
  `hinhAnh` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`maDT`),
  KEY `FK_dienthoai_nhacungcap` (`maNCC`),
  CONSTRAINT `FK_dienthoai_nhacungcap` FOREIGN KEY (`maNCC`) REFERENCES `nhacungcap` (`maNCC`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table quanlydienthoai.dienthoai: ~3 rows (approximately)
INSERT INTO `dienthoai` (`maDT`, `tenDT`, `namSanXuat`, `cauHinh`, `maNCC`, `hinhAnh`) VALUES
	(3, 'NokiaLumia', 2023, 'V', 1, 'nokialumia.jpeg'),
	(5, 'HTC', 2023, 'V', 1, 'HTC.jpeg'),
	(6, 'Nokia', 2002, 'A', 1, 'nokialumia.jpeg');

-- Dumping structure for table quanlydienthoai.nhacungcap
CREATE TABLE IF NOT EXISTS `nhacungcap` (
  `maNCC` int(11) NOT NULL AUTO_INCREMENT,
  `tenNhaCC` varchar(50) NOT NULL DEFAULT '',
  `diaChi` varchar(50) NOT NULL DEFAULT '',
  `soDienThoai` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`maNCC`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Dumping data for table quanlydienthoai.nhacungcap: ~2 rows (approximately)
INSERT INTO `nhacungcap` (`maNCC`, `tenNhaCC`, `diaChi`, `soDienThoai`) VALUES
	(1, 'Nhà cung cấp a', 'A', '03768495720'),
	(2, 'Nhà cung cấp B', 'B', '03967581232');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
