-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 08, 2023 at 09:12 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `supermarket`
--

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
CREATE TABLE IF NOT EXISTS `employees` (
  `employeeId` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `position` varchar(255) NOT NULL,
  `department` int(11) NOT NULL,
  `contactInfo` varchar(255) NOT NULL,
  `hourlyRate` int(11) NOT NULL,
  `commentary` varchar(255) NOT NULL,
  PRIMARY KEY (`employeeId`),
  KEY `employeeId` (`employeeId`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Thriving Coders - Supermarkt Project';


-- --------------------------------------------------------

--
-- Table structure for table `employees_assessments`
--

DROP TABLE IF EXISTS `employees_assessments`;
CREATE TABLE IF NOT EXISTS `employees_assessments` (
  `assessmentId` int(11) NOT NULL AUTO_INCREMENT,
  `employeeId` int(11) NOT NULL,
  `assessmentDate` date NOT NULL,
  `performanceRating` int(11) NOT NULL,
  `salesAnalysis` int(11) NOT NULL,
  PRIMARY KEY (`assessmentId`),
  KEY `employeeId` (`employeeId`)
) ENGINE=InnoDB AUTO_INCREMENT=131 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


-- --------------------------------------------------------

--
-- Table structure for table `employees_schedules`
--

DROP TABLE IF EXISTS `employees_schedules`;
CREATE TABLE IF NOT EXISTS `employees_schedules` (
  `scheduleId` int(11) NOT NULL AUTO_INCREMENT,
  `employeeId` int(11) NOT NULL,
  `shiftDate` date NOT NULL,
  `shiftType` int(11) NOT NULL,
  `workStart` date NOT NULL,
  `workEnd` date NOT NULL,
  PRIMARY KEY (`scheduleId`),
  KEY `employeeId` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employees_sicks`
--

DROP TABLE IF EXISTS `employees_sicks`;
CREATE TABLE IF NOT EXISTS `employees_sicks` (
  `sickLeaveId` int(11) NOT NULL AUTO_INCREMENT,
  `employeeId` int(11) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `medicalCertificate` mediumblob NOT NULL,
  PRIMARY KEY (`sickLeaveId`),
  KEY `employeeId` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employees_vacations`
--

DROP TABLE IF EXISTS `employees_vacations`;
CREATE TABLE IF NOT EXISTS `employees_vacations` (
  `vacationId` int(11) NOT NULL AUTO_INCREMENT,
  `employeeId` int(11) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `reason` text NOT NULL,
  PRIMARY KEY (`vacationId`),
  KEY `employeeId` (`employeeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employees_assessments`
--
ALTER TABLE `employees_assessments`
  ADD CONSTRAINT `employees_assessments_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`employeeId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `employees_schedules`
--
ALTER TABLE `employees_schedules`
  ADD CONSTRAINT `employees_schedules_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`employeeId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `employees_sicks`
--
ALTER TABLE `employees_sicks`
  ADD CONSTRAINT `employees_sicks_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`employeeId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `employees_vacations`
--
ALTER TABLE `employees_vacations`
  ADD CONSTRAINT `employees_vacations_ibfk_1` FOREIGN KEY (`vacationId`) REFERENCES `employees` (`employeeId`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
