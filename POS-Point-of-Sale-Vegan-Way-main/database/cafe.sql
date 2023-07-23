-- phpMyAdmin SQL Dump
-- version 5.1.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 03, 2021 at 09:40 AM
-- Server version: 10.4.19-MariaDB
-- PHP Version: 7.4.20

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cafe`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `total_harga_transaksi` ()  BEGIN
SELECT 
SUM(tb_keranjang.jumlah*tb_keranjang.harga) AS total_harga
FROM tb_keranjang;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `id` int(11) NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id`, `username`, `password`) VALUES
(1, 'Admin', '12345'),
(2, 'admin', '12345'),
(3, 'owner', 'owner123');

-- --------------------------------------------------------

--
-- Table structure for table `tb_databarang`
--

CREATE TABLE `tb_databarang` (
  `kode_minuman` int(11) NOT NULL,
  `nama_minuman` varchar(35) NOT NULL,
  `harga` int(6) NOT NULL,
  `stok` int(3) NOT NULL,
  `tanggal` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_databarang`
--

INSERT INTO `tb_databarang` (`kode_minuman`, `nama_minuman`, `harga`, `stok`, `tanggal`) VALUES
(2023, 'Brown Sugar Boba', 15000, 50, '2021-07-02'),
(2024, 'Strawberry Milk', 15000, 50, '2021-07-02'),
(2025, 'Choco Milk ', 15000, 50, '2021-07-02'),
(2026, 'Matcha Milk ', 15000, 49, '2021-07-02'),
(2027, 'Lechy Milk ', 15000, 49, '2021-07-02');

-- --------------------------------------------------------

--
-- Table structure for table `tb_datapetugas`
--

CREATE TABLE `tb_datapetugas` (
  `id_petugas` int(11) NOT NULL,
  `nama_petugas` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `alamat` text NOT NULL,
  `tanggal_pendaftaran` date NOT NULL,
  `username` varchar(10) NOT NULL,
  `password` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_datapetugas`
--

INSERT INTO `tb_datapetugas` (`id_petugas`, `nama_petugas`, `email`, `alamat`, `tanggal_pendaftaran`, `username`, `password`) VALUES
(12, 'vivi', 'vivi@gmail.com', 'dringu plosok', '2021-07-02', 'vivi', '12345'),
(13, 'fiqri', 'fiqrim930@gmail.com', 'Tuban', '2021-07-02', 'fiqri', 'fiqri123');

-- --------------------------------------------------------

--
-- Table structure for table `tb_keranjang`
--

CREATE TABLE `tb_keranjang` (
  `id_transaksi` int(11) NOT NULL,
  `kode_minuman` int(11) NOT NULL,
  `nama_minuman` varchar(35) NOT NULL,
  `harga` int(6) NOT NULL,
  `jumlah` int(3) NOT NULL,
  `total_harga` int(6) NOT NULL,
  `tgl_transaksi` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tb_keranjang`
--

INSERT INTO `tb_keranjang` (`id_transaksi`, `kode_minuman`, `nama_minuman`, `harga`, `jumlah`, `total_harga`, `tgl_transaksi`) VALUES
(33, 2027, 'Lechy Milk ', 15000, 1, 15000, '2021-07-03'),
(34, 2026, 'Matcha Milk ', 15000, 1, 15000, '2021-07-03');

--
-- Triggers `tb_keranjang`
--
DELIMITER $$
CREATE TRIGGER `cancel` AFTER DELETE ON `tb_keranjang` FOR EACH ROW BEGIN
UPDATE tb_databarang SET
stok = stok + OLD.jumlah
WHERE kode_minuman = OLD.kode_minuman;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `cancel_2` AFTER DELETE ON `tb_keranjang` FOR EACH ROW BEGIN
DELETE FROM transaksi
WHERE kode_minuman = OLD.kode_minuman;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `stok_habis` AFTER INSERT ON `tb_keranjang` FOR EACH ROW BEGIN
DELETE FROM tb_databarang
WHERE stok = 0
AND
kode_minuman = NEW.kode_minuman;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `transaksi`
--

CREATE TABLE `transaksi` (
  `tgl_transaksi` date NOT NULL,
  `id_transaksi` int(11) NOT NULL,
  `kode_minuman` int(11) NOT NULL,
  `nama_minuman` varchar(35) NOT NULL,
  `harga` int(6) NOT NULL,
  `jumlah_barang` int(3) NOT NULL,
  `total_harga` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `transaksi`
--

INSERT INTO `transaksi` (`tgl_transaksi`, `id_transaksi`, `kode_minuman`, `nama_minuman`, `harga`, `jumlah_barang`, `total_harga`) VALUES
('2021-07-03', 33, 2027, 'Lechy Milk ', 15000, 1, 15000),
('2021-07-03', 34, 2026, 'Matcha Milk ', 15000, 1, 15000);

--
-- Triggers `transaksi`
--
DELIMITER $$
CREATE TRIGGER `keranjang` AFTER INSERT ON `transaksi` FOR EACH ROW BEGIN
INSERT INTO tb_keranjang SET
id_transaksi = NEW.id_transaksi,
kode_minuman = NEW.kode_minuman,
nama_minuman = NEW.nama_minuman,
harga = NEW.harga,
jumlah = NEW.jumlah_barang,
total_harga = NEW.total_harga,
tgl_transaksi = NEW.tgl_transaksi;
END
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `transaksi` AFTER INSERT ON `transaksi` FOR EACH ROW BEGIN
UPDATE tb_databarang SET
stok = stok - NEW.jumlah_barang
WHERE kode_minuman = NEW.kode_minuman;
END
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tb_databarang`
--
ALTER TABLE `tb_databarang`
  ADD PRIMARY KEY (`kode_minuman`);

--
-- Indexes for table `tb_datapetugas`
--
ALTER TABLE `tb_datapetugas`
  ADD PRIMARY KEY (`id_petugas`);

--
-- Indexes for table `tb_keranjang`
--
ALTER TABLE `tb_keranjang`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `kode_minuman` (`kode_minuman`);

--
-- Indexes for table `transaksi`
--
ALTER TABLE `transaksi`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `kode_minuman` (`kode_minuman`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tb_databarang`
--
ALTER TABLE `tb_databarang`
  MODIFY `kode_minuman` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2028;

--
-- AUTO_INCREMENT for table `tb_datapetugas`
--
ALTER TABLE `tb_datapetugas`
  MODIFY `id_petugas` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT for table `tb_keranjang`
--
ALTER TABLE `tb_keranjang`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `transaksi`
--
ALTER TABLE `transaksi`
  MODIFY `id_transaksi` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tb_keranjang`
--
ALTER TABLE `tb_keranjang`
  ADD CONSTRAINT `tb_keranjang_ibfk_1` FOREIGN KEY (`kode_minuman`) REFERENCES `tb_databarang` (`kode_minuman`) ON DELETE NO ACTION ON UPDATE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
