-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 20, 2023 at 11:00 PM
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
CREATE DATABASE IF NOT EXISTS `supermarket` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE `supermarket`;

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
CREATE TABLE `employees` (
  `employeeId` int(11) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `position` varchar(255) NOT NULL,
  `department` int(11) NOT NULL,
  `contactInfo` varchar(255) NOT NULL,
  `hourlyRate` int(11) NOT NULL,
  `commentary` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='Thriving Coders - Supermarkt Project';

-- --------------------------------------------------------

--
-- Table structure for table `employees_assessments`
--

DROP TABLE IF EXISTS `employees_assessments`;
CREATE TABLE `employees_assessments` (
  `assessmentId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `assessmentDate` date NOT NULL,
  `performanceRating` int(11) NOT NULL,
  `salesAnalysis` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employees_permissions`
--

DROP TABLE IF EXISTS `employees_permissions`;
CREATE TABLE `employees_permissions` (
  `accessId` int(11) NOT NULL,
  `employeeId` int(11) DEFAULT NULL,
  `login` varchar(50) DEFAULT NULL,
  `employeeInternalNumber` int(11) DEFAULT NULL,
  `passwordHash` varchar(255) DEFAULT NULL,
  `accessRights` longtext DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employees_schedules`
--

DROP TABLE IF EXISTS `employees_schedules`;
CREATE TABLE `employees_schedules` (
  `scheduleId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `shiftDate` date NOT NULL,
  `shiftType` int(11) NOT NULL,
  `workStart` datetime NOT NULL,
  `workEnd` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employees_sicks`
--

DROP TABLE IF EXISTS `employees_sicks`;
CREATE TABLE `employees_sicks` (
  `sickLeaveId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `medicalCertificate` mediumblob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employees_vacations`
--

DROP TABLE IF EXISTS `employees_vacations`;
CREATE TABLE `employees_vacations` (
  `vacationId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `reason` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`employeeId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `employees_assessments`
--
ALTER TABLE `employees_assessments`
  ADD PRIMARY KEY (`assessmentId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `employees_permissions`
--
ALTER TABLE `employees_permissions`
  ADD PRIMARY KEY (`accessId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `employees_schedules`
--
ALTER TABLE `employees_schedules`
  ADD PRIMARY KEY (`scheduleId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `employees_sicks`
--
ALTER TABLE `employees_sicks`
  ADD PRIMARY KEY (`sickLeaveId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indexes for table `employees_vacations`
--
ALTER TABLE `employees_vacations`
  ADD PRIMARY KEY (`vacationId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `employeeId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employees_assessments`
--
ALTER TABLE `employees_assessments`
  MODIFY `assessmentId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employees_permissions`
--
ALTER TABLE `employees_permissions`
  MODIFY `accessId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employees_schedules`
--
ALTER TABLE `employees_schedules`
  MODIFY `scheduleId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employees_sicks`
--
ALTER TABLE `employees_sicks`
  MODIFY `sickLeaveId` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employees_vacations`
--
ALTER TABLE `employees_vacations`
  MODIFY `vacationId` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `employees_assessments`
--
ALTER TABLE `employees_assessments`
  ADD CONSTRAINT `employees_assessments_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`employeeId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `employees_permissions`
--
ALTER TABLE `employees_permissions`
  ADD CONSTRAINT `employees_permissions_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`employeeId`);

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
