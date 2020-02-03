-- phpMyAdmin SQL Dump
-- version 4.8.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3307
-- Generation Time: Jul 01, 2019 at 12:52 PM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `classroom`
--
CREATE DATABASE IF NOT EXISTS `classroom` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `classroom`;

-- --------------------------------------------------------

--
-- Table structure for table `classes`
--

DROP TABLE IF EXISTS `classes`;
CREATE TABLE IF NOT EXISTS `classes` (
  `room_number` int(11) NOT NULL AUTO_INCREMENT,
  `capacity` decimal(4,0) DEFAULT NULL,
  `department_dept_name` varchar(40) NOT NULL,
  PRIMARY KEY (`room_number`,`department_dept_name`),
  KEY `fk_classroom_department1_idx` (`department_dept_name`)
) ENGINE=InnoDB AUTO_INCREMENT=202 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `classes`
--

INSERT INTO `classes` (`room_number`, `capacity`, `department_dept_name`) VALUES
(101, '40', 'Computer Science'),
(109, '52', 'Computer Science'),
(201, '50', 'Computer Science');

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

DROP TABLE IF EXISTS `course`;
CREATE TABLE IF NOT EXISTS `course` (
  `course_id` varchar(8) NOT NULL,
  `title` varchar(50) DEFAULT NULL,
  `credits` decimal(2,0) DEFAULT NULL,
  `department_dept_name` varchar(20) NOT NULL,
  PRIMARY KEY (`course_id`),
  KEY `fk_course_department1_idx` (`department_dept_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`course_id`, `title`, `credits`, `department_dept_name`) VALUES
('CSC221', 'Data Structures', '4', 'Computer Science'),
('CSC291', 'Software Engineering Concepts', '3', 'Computer Science'),
('CSC371', 'Database Systems', '4', 'Computer Science'),
('HUM100', 'Introduction to Psychology', '3', 'Psychology'),
('MTH101', 'Calculus 1', '3', 'Mathametics'),
('MTH201', 'Statistics', '3', 'Mathametics'),
('PHY100', 'Mechanics and Thermodynamics', '4', 'Physics');

-- --------------------------------------------------------

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
CREATE TABLE IF NOT EXISTS `department` (
  `dept_name` varchar(40) NOT NULL,
  `building` varchar(25) DEFAULT NULL,
  `budget` int(15) DEFAULT NULL,
  PRIMARY KEY (`dept_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `department`
--

INSERT INTO `department` (`dept_name`, `building`, `budget`) VALUES
('Computer Science', 'Academic Block 2', 250000),
('Electrical Engineering', 'Academic Block ', 210000),
('Mathametics', 'Academic Block 1', 110000),
('Physics', 'Academic Block ', 110000),
('Psychology', 'Bio Science', 210000);

-- --------------------------------------------------------

--
-- Table structure for table `instructor`
--

DROP TABLE IF EXISTS `instructor`;
CREATE TABLE IF NOT EXISTS `instructor` (
  `instructor_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `salary` decimal(8,2) DEFAULT NULL,
  `department_dept_name` varchar(40) NOT NULL,
  PRIMARY KEY (`instructor_id`),
  KEY `fk_instructor_department1_idx` (`department_dept_name`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `instructor`
--

INSERT INTO `instructor` (`instructor_id`, `name`, `salary`, `department_dept_name`) VALUES
(1, 'Qasim Malik', '130000.00', 'Computer Science'),
(3, 'Asif Zia', '190000.00', 'Physics'),
(4, 'Inayat ur Rehman', '160000.00', 'Computer Science'),
(5, 'Tehseen Riaz Abbasi', '90000.00', 'Computer Science'),
(6, 'Qamar Hussain', '135000.00', 'Mathametics'),
(7, 'Shakila Raja', '85000.00', 'Psychology');

-- --------------------------------------------------------

--
-- Table structure for table `instructor_teaches_course`
--

DROP TABLE IF EXISTS `instructor_teaches_course`;
CREATE TABLE IF NOT EXISTS `instructor_teaches_course` (
  `instructor_instructor_id` int(11) NOT NULL,
  `course_course_id` varchar(8) NOT NULL,
  PRIMARY KEY (`course_course_id`,`instructor_instructor_id`) USING BTREE,
  UNIQUE KEY `instructor_instructor_id` (`instructor_instructor_id`),
  KEY `fk_instructor_has_course_course1_idx` (`course_course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `instructor_teaches_course`
--

INSERT INTO `instructor_teaches_course` (`instructor_instructor_id`, `course_course_id`) VALUES
(4, 'CSC221'),
(5, 'CSC291'),
(1, 'CSC371'),
(7, 'HUM100'),
(6, 'MTH101'),
(3, 'PHY100');

-- --------------------------------------------------------

--
-- Table structure for table `instructor_teaches_section`
--

DROP TABLE IF EXISTS `instructor_teaches_section`;
CREATE TABLE IF NOT EXISTS `instructor_teaches_section` (
  `instructor_instructor_id` int(11) NOT NULL,
  `section_sec_id` varchar(8) NOT NULL,
  `section_semester` int(11) NOT NULL,
  `section_program` varchar(10) NOT NULL,
  PRIMARY KEY (`instructor_instructor_id`,`section_sec_id`,`section_semester`,`section_program`),
  KEY `fk_instructor_has_section_section1_idx` (`section_sec_id`,`section_semester`,`section_program`,`instructor_instructor_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `instructor_teaches_section`
--

INSERT INTO `instructor_teaches_section` (`instructor_instructor_id`, `section_sec_id`, `section_semester`, `section_program`) VALUES
(1, 'A', 4, 'BCS'),
(1, 'B', 4, 'BCS'),
(3, 'A', 1, 'BPH'),
(4, 'A', 4, 'BCS'),
(4, 'B', 4, 'BCS'),
(5, 'A', 4, 'BCS'),
(6, 'B', 4, 'BCS'),
(7, 'A', 3, 'BPY');

-- --------------------------------------------------------

--
-- Table structure for table `section`
--

DROP TABLE IF EXISTS `section`;
CREATE TABLE IF NOT EXISTS `section` (
  `sec_id` varchar(8) NOT NULL,
  `semester` int(11) NOT NULL,
  `program` varchar(10) NOT NULL,
  `department_dept_name` varchar(40) NOT NULL,
  PRIMARY KEY (`sec_id`,`semester`,`program`),
  KEY `dept_name` (`department_dept_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `section`
--

INSERT INTO `section` (`sec_id`, `semester`, `program`, `department_dept_name`) VALUES
('A', 3, 'BSE', 'Computer Science'),
('A', 4, 'BCS', 'Computer Science'),
('B', 4, 'BCS', 'Computer Science'),
('A', 1, 'BPH', 'Physics'),
('A', 3, 'BPY', 'Psychology');

-- --------------------------------------------------------

--
-- Table structure for table `section_has_timeslot`
--

DROP TABLE IF EXISTS `section_has_timeslot`;
CREATE TABLE IF NOT EXISTS `section_has_timeslot` (
  `section_sec_id` varchar(8) NOT NULL,
  `section_semester` int(11) NOT NULL,
  `section_program` varchar(10) NOT NULL,
  `time_slot_time_slot_id` int(11) NOT NULL,
  `time_slot_day` varchar(12) NOT NULL,
  `course_course_id` varchar(8) NOT NULL,
  PRIMARY KEY (`section_sec_id`,`section_semester`,`section_program`,`time_slot_time_slot_id`,`time_slot_day`),
  KEY `fk_section_has_time_slot_time_slot1_idx` (`time_slot_time_slot_id`,`time_slot_day`),
  KEY `fk_section_has_time_slot_section1_idx` (`section_sec_id`,`section_semester`,`section_program`),
  KEY `course_id_foreign_key` (`course_course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `section_has_timeslot`
--

INSERT INTO `section_has_timeslot` (`section_sec_id`, `section_semester`, `section_program`, `time_slot_time_slot_id`, `time_slot_day`, `course_course_id`) VALUES
('B', 4, 'BCS', 1, 'Monday', 'CSC221'),
('B', 4, 'BCS', 2, 'Tuesday', 'CSC221'),
('A', 3, 'BSE', 2, 'Tuesday', 'CSC291'),
('A', 4, 'BCS', 1, 'Monday', 'CSC291'),
('A', 4, 'BCS', 1, 'Tuesday', 'CSC371'),
('B', 4, 'BCS', 2, 'Monday', 'CSC371'),
('A', 3, 'BPY', 2, 'Wednesday', 'HUM100'),
('B', 4, 'BCS', 4, 'Wednesday', 'MTH101'),
('A', 3, 'BSE', 3, 'Wednesday', 'MTH201'),
('A', 1, 'BPH', 2, 'Monday', 'PHY100'),
('A', 4, 'BCS', 2, 'Tuesday', 'PHY100');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `stu_reg` varchar(15) NOT NULL,
  `name` varchar(35) NOT NULL,
  `tot_cred` decimal(3,0) DEFAULT NULL,
  `department_dept_name` varchar(40) NOT NULL,
  `section_sec_id` varchar(8) NOT NULL,
  `section_semester` int(11) NOT NULL,
  `section_program` varchar(10) NOT NULL,
  PRIMARY KEY (`stu_reg`),
  KEY `fk_student_department1_idx` (`department_dept_name`),
  KEY `fk_student_section1_idx` (`section_sec_id`,`section_semester`,`section_program`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`stu_reg`, `name`, `tot_cred`, `department_dept_name`, `section_sec_id`, `section_semester`, `section_program`) VALUES
('FA17-BCS-007', 'Haroon Munir', '19', 'Computer Science', 'A', 4, 'BCS'),
('FA17-BCS-055', 'Muhammad Kashif', '20', 'Computer Science', 'B', 4, 'BCS'),
('FA17-BCS-058', 'Mutahhar bin Muzaffar', '20', 'Computer Science', 'B', 4, 'BCS'),
('FA17-BCS-088', 'Syed Muhammad Irtesam', '20', 'Computer Science', 'B', 4, 'BCS'),
('FA18-BSE-096', 'Uzair Qamar', '16', 'Computer Science', 'A', 3, 'BSE'),
('FA19-BPH-082', 'Sher Alam', '16', 'Physics', 'A', 1, 'BPH'),
('SP18-BPY-014', 'Raheem Hussain', '15', 'Psychology', 'A', 3, 'BPY');

-- --------------------------------------------------------

--
-- Table structure for table `takes`
--

DROP TABLE IF EXISTS `takes`;
CREATE TABLE IF NOT EXISTS `takes` (
  `stu_reg` varchar(15) NOT NULL,
  `course_course_id` varchar(8) NOT NULL,
  PRIMARY KEY (`stu_reg`,`course_course_id`),
  KEY `fk_student_has_course_course1_idx` (`course_course_id`),
  KEY `fk_student_has_course_student1_idx` (`stu_reg`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `takes`
--

INSERT INTO `takes` (`stu_reg`, `course_course_id`) VALUES
('FA17-BCS-007', 'CSC291'),
('FA17-BCS-055', 'CSC221'),
('FA17-BCS-058', 'CSC221'),
('FA17-BCS-058', 'CSC371'),
('FA17-BCS-088', 'CSC221'),
('FA17-BCS-088', 'CSC291'),
('FA18-BSE-096', 'CSC291'),
('FA19-BPH-082', 'PHY100'),
('SP18-BPY-014', 'HUM100');

-- --------------------------------------------------------

--
-- Table structure for table `time_slot`
--

DROP TABLE IF EXISTS `time_slot`;
CREATE TABLE IF NOT EXISTS `time_slot` (
  `time_slot_id` int(11) NOT NULL AUTO_INCREMENT,
  `day` varchar(12) NOT NULL,
  `start` varchar(7) NOT NULL,
  `end` varchar(7) NOT NULL,
  PRIMARY KEY (`time_slot_id`,`day`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `time_slot`
--

INSERT INTO `time_slot` (`time_slot_id`, `day`, `start`, `end`) VALUES
(1, 'Monday', '08:30', '10:00'),
(1, 'Tuesday', '08:30', '10:00'),
(1, 'Wednesday', '08:30', '10:00'),
(2, 'Monday', '10:00', '11:30'),
(2, 'Tuesday', '10:00', '11:30'),
(2, 'Wednesday', '10:00', '11:30'),
(3, 'Monday', '11:30', '01:00'),
(3, 'Tuesday', '11:30', '01:00'),
(3, 'Wednesday', '11:30', '01:00'),
(4, 'Monday', '01:00', '02:30'),
(4, 'Tuesday', '01:00', '02:30'),
(4, 'Wednesday', '01:00', '02:30');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `classes`
--
ALTER TABLE `classes`
  ADD CONSTRAINT `fk_classroom_department1` FOREIGN KEY (`department_dept_name`) REFERENCES `department` (`dept_name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `course`
--
ALTER TABLE `course`
  ADD CONSTRAINT `fk_course_department1` FOREIGN KEY (`department_dept_name`) REFERENCES `department` (`dept_name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `instructor`
--
ALTER TABLE `instructor`
  ADD CONSTRAINT `fk_instructor_department1` FOREIGN KEY (`department_dept_name`) REFERENCES `department` (`dept_name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `instructor_teaches_course`
--
ALTER TABLE `instructor_teaches_course`
  ADD CONSTRAINT `fk_instructor_has_course_course1` FOREIGN KEY (`course_course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_instructor_has_course_instructor1` FOREIGN KEY (`instructor_instructor_id`) REFERENCES `instructor` (`instructor_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `instructor_teaches_section`
--
ALTER TABLE `instructor_teaches_section`
  ADD CONSTRAINT `fk_instructor_has_section_instructor1` FOREIGN KEY (`instructor_instructor_id`) REFERENCES `instructor` (`instructor_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_instructor_has_section_section1` FOREIGN KEY (`section_sec_id`,`section_semester`,`section_program`) REFERENCES `section` (`sec_id`, `semester`, `program`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `section`
--
ALTER TABLE `section`
  ADD CONSTRAINT `dept_name` FOREIGN KEY (`department_dept_name`) REFERENCES `department` (`dept_name`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `section_has_timeslot`
--
ALTER TABLE `section_has_timeslot`
  ADD CONSTRAINT `course_id_foreign_key` FOREIGN KEY (`course_course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_section_has_time_slot_section1` FOREIGN KEY (`section_sec_id`,`section_semester`,`section_program`) REFERENCES `section` (`sec_id`, `semester`, `program`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_section_has_time_slot_time_slot1` FOREIGN KEY (`time_slot_time_slot_id`,`time_slot_day`) REFERENCES `time_slot` (`time_slot_id`, `day`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `student`
--
ALTER TABLE `student`
  ADD CONSTRAINT `fk_student_department1` FOREIGN KEY (`department_dept_name`) REFERENCES `department` (`dept_name`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_student_section1` FOREIGN KEY (`section_sec_id`,`section_semester`,`section_program`) REFERENCES `section` (`sec_id`, `semester`, `program`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `takes`
--
ALTER TABLE `takes`
  ADD CONSTRAINT `fk_student_has_course_course1` FOREIGN KEY (`course_course_id`) REFERENCES `course` (`course_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_student_has_course_student1` FOREIGN KEY (`stu_reg`) REFERENCES `student` (`stu_reg`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
