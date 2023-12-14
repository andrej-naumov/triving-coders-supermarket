-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 14. Dez 2023 um 14:10
-- Server-Version: 10.4.32-MariaDB
-- PHP-Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `supermarket`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `employees`
--

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
-- Tabellenstruktur für Tabelle `employees_assessments`
--

CREATE TABLE `employees_assessments` (
  `assessmentId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `assessmentDate` date NOT NULL,
  `performanceRating` int(11) NOT NULL,
  `salesAnalysis` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `employees_schedules`
--

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
-- Tabellenstruktur für Tabelle `employees_sicks`
--

CREATE TABLE `employees_sicks` (
  `sickLeaveId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `medicalCertificate` mediumblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `employees_vacations`
--

CREATE TABLE `employees_vacations` (
  `vacationId` int(11) NOT NULL,
  `employeeId` int(11) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `reason` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`employeeId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indizes für die Tabelle `employees_assessments`
--
ALTER TABLE `employees_assessments`
  ADD PRIMARY KEY (`assessmentId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indizes für die Tabelle `employees_schedules`
--
ALTER TABLE `employees_schedules`
  ADD PRIMARY KEY (`scheduleId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indizes für die Tabelle `employees_sicks`
--
ALTER TABLE `employees_sicks`
  ADD PRIMARY KEY (`sickLeaveId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- Indizes für die Tabelle `employees_vacations`
--
ALTER TABLE `employees_vacations`
  ADD PRIMARY KEY (`vacationId`),
  ADD KEY `employeeId` (`employeeId`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `employees`
--
ALTER TABLE `employees`
  MODIFY `employeeId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=0;

--
-- AUTO_INCREMENT für Tabelle `employees_assessments`
--
ALTER TABLE `employees_assessments`
  MODIFY `assessmentId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=0;

--
-- AUTO_INCREMENT für Tabelle `employees_schedules`
--
ALTER TABLE `employees_schedules`
  MODIFY `scheduleId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=0;

--
-- AUTO_INCREMENT für Tabelle `employees_sicks`
--
ALTER TABLE `employees_sicks`
  MODIFY `sickLeaveId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=0;

--
-- AUTO_INCREMENT für Tabelle `employees_vacations`
--
ALTER TABLE `employees_vacations`
  MODIFY `vacationId` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=0;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `employees_assessments`
--
ALTER TABLE `employees_assessments`
  ADD CONSTRAINT `employees_assessments_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`employeeId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `employees_schedules`
--
ALTER TABLE `employees_schedules`
  ADD CONSTRAINT `employees_schedules_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`employeeId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `employees_sicks`
--
ALTER TABLE `employees_sicks`
  ADD CONSTRAINT `employees_sicks_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`employeeId`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `employees_vacations`
--
ALTER TABLE `employees_vacations`
  ADD CONSTRAINT `employees_vacations_ibfk_1` FOREIGN KEY (`employeeId`) REFERENCES `employees` (`employeeId`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
