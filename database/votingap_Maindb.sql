-- phpMyAdmin SQL Dump
-- version 4.0.10.7
-- http://www.phpmyadmin.net
--
-- Host: localhost:3306
-- Generation Time: Sep 08, 2017 at 11:06 AM
-- Server version: 10.0.27-MariaDB-cll-lve
-- PHP Version: 5.4.31

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `votingap_Maindb`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `email` text NOT NULL,
  `password` text NOT NULL,
  `user` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=20 ;



--
-- Table structure for table `confirmschedule`
--

CREATE TABLE IF NOT EXISTS `confirmschedule` (
  `MeetingID` int(11) DEFAULT NULL,
  `Name` text,
  `employee` text,
  `Confirm` text,
  `meetingname` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



--
-- Table structure for table `Meetingschedules`
--

CREATE TABLE IF NOT EXISTS `Meetingschedules` (
  `MeetingID` int(11) NOT NULL AUTO_INCREMENT,
  `time` text,
  `date` text,
  `name` text,
  `Description` text,
  PRIMARY KEY (`MeetingID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;


-- --------------------------------------------------------

--
-- Table structure for table `question`
--

CREATE TABLE IF NOT EXISTS `question` (
  `QuestionID` int(11) NOT NULL AUTO_INCREMENT,
  `question` text,
  `Voteoption` text,
  `state` text,
  PRIMARY KEY (`QuestionID`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=62 ;


--
-- Table structure for table `VOTES`
--

CREATE TABLE IF NOT EXISTS `VOTES` (
  `surveyid` int(11) DEFAULT NULL,
  `employee` text,
  `Voted` text,
  `name` text
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


