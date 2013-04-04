-- phpMyAdmin SQL Dump
-- version 3.3.7deb7
-- http://www.phpmyadmin.net
--
-- Serveur: localhost
-- Généré le : Lun 25 Mars 2013 à 08:49
-- Version du serveur: 5.1.61
-- Version de PHP: 5.3.3-7+squeeze8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `workshopmanager`
--

-- --------------------------------------------------------

--
-- Structure de la table `USER`
--

CREATE TABLE IF NOT EXISTS `USER` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `charterAgree` tinyint(1) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT 'standard',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Contenu de la table `USER`
--

INSERT INTO `USER` (`id`, `charterAgree`, `email`, `first_name`, `last_name`, `picture`, `role`) VALUES
(1, 1, 'yannick.chartois@gmail.com', 'Yannick', 'Chartois', 'https://lh5.googleusercontent.com/-8rFmjE_z2do/AAAAAAAAAAI/AAAAAAAAAGM/kdGz_0hCplQ/photo.jpg', 'admin'),
(2, 1, 'vdubois@sqli.com', 'Vincent', 'Dubois', '/assets/images/avatar-default.png', 'standard'),
(3, 1, 'rgoyard@sqli.com', 'Remi', 'Goyard', '/assets/images/avatar-default.png', 'admin'),
(4, 1, 'fgracia@sqli.com', 'Frédéric', 'Gracia', '/assets/images/avatar-default.png', 'standard'),
(7, 1, 'cachavezley@sqli.com', 'Christian Alonso', 'Chavez Ley', '/assets/images/avatar-default.png', 'admin'),
(9, 1, 'mdiezgarcia@sqli.com', 'Miguel', 'Diez Garcia', '/assets/images/avatar-default.png', 'admin');
