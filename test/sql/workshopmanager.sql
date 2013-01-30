-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Mar 29 Janvier 2013 à 08:48
-- Version du serveur: 5.5.29
-- Version de PHP: 5.3.10-1ubuntu3.5

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `workshopmanager`
--

-- --------------------------------------------------------

--
-- Structure de la table `play_evolutions`
--

CREATE TABLE IF NOT EXISTS `play_evolutions` (
  `id` int(11) NOT NULL,
  `hash` varchar(255) NOT NULL,
  `applied_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `apply_script` text,
  `revert_script` text,
  `state` varchar(255) DEFAULT NULL,
  `last_problem` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `POTENTIAL_PARTICIPANTS`
--

CREATE TABLE IF NOT EXISTS `POTENTIAL_PARTICIPANTS` (
  `workshop_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`workshop_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `POTENTIAL_PARTICIPANTS`
--

INSERT INTO `POTENTIAL_PARTICIPANTS` (`workshop_id`, `user_id`) VALUES
(6, 1),
(8, 1);

-- --------------------------------------------------------

--
-- Structure de la table `USER`
--

CREATE TABLE IF NOT EXISTS `USER` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `picture` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Contenu de la table `USER`
--

INSERT INTO `USER` (`id`, `email`, `first_name`, `last_name`, `picture`, `role`) VALUES
(1, 'yannick.chartois@gmail.com', 'Yannick', 'Chartois', 'https://lh5.googleusercontent.com/-8rFmjE_z2do/AAAAAAAAAAI/AAAAAAAAAGM/kdGz_0hCplQ/photo.jpg', 'admin');

-- --------------------------------------------------------

--
-- Structure de la table `WORKSHOP`
--

CREATE TABLE IF NOT EXISTS `WORKSHOP` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `workshopSession_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=9 ;

--
-- Contenu de la table `WORKSHOP`
--

INSERT INTO `WORKSHOP` (`id`, `description`, `image`, `subject`, `author_id`, `workshopSession_id`) VALUES
(5, 'Caching data is a typical optimization in modern applications, and so Play provides a global cache. An important point about the cache is that it behaves just like a cache should: the data you just stored may just go missing.\r\n\r\nFor any data stored in the cache, a regeneration strategy needs to be put in place in case the data goes missing. This philosophy is one of the fundamentals behind Play, and is different from Java EE, where the session is expected to retain values throughout its lifetime.\r\n\r\nThe default implementation of the cache API uses EHCache. You can also provide your own implementation via a plugin.\r\n', NULL, 'sdfgsgsdg', 1, 1),
(6, 'That''s fine, and even great, but why should images get all the glory? Shouldn''t other elements inside the document be able to carry a tooltip as well, such as a text link or form element? Wouldn''t that be pretty cool, not be mention crucial in guiding devises with limited capabilities such as PDAs navigate your site and its various elements? Well, starting in HTML 4, the concept of the "alt" attribute for images have been expanded to all elements on the page.\r\n', 'images/workshops/Celtic_rond_chien.jpg', 'zerazerazre', 1, 2),
(8, 'Caching data is a typical optimization in modern applications, and so Play provides a global cache. An important point about the cache is that it behaves just like a cache should: the data you just stored may just go missing.\r\n\r\nFor any data stored in the cache, a regeneration strategy needs to be put in place in case the data goes missing. This philosophy is one of the fundamentals behind Play, and is different from Java EE, where the session is expected to retain values throughout its lifetime.\r\n\r\nThe default implementation of the cache API uses EHCache. You can also provide your own implementation via a plugin.', 'images/workshops/play.png', 'The Play cache API', 1, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `WORKSHOP_SESSION`
--

CREATE TABLE IF NOT EXISTS `WORKSHOP_SESSION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `doodle_url` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `nextPlay` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `speaker_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `WORKSHOP_SESSION`
--

INSERT INTO `WORKSHOP_SESSION` (`id`, `doodle_url`, `location`, `nextPlay`, `speaker_id`) VALUES
(1, 'http://www.doodle.com/tr4gqnpedqxaxbeb', 'ssgsdfgsdg', '2013-01-30 23:00:00', 1),
(2, 'http://twitter.github.com/bootstrap/scaffolding.html', 'fsdfgsdfgsd', '2013-01-16 23:00:00', 1);

-- --------------------------------------------------------

--
-- Structure de la table `WORKSHOP_SPEAKERS`
--

CREATE TABLE IF NOT EXISTS `WORKSHOP_SPEAKERS` (
  `workshop_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`workshop_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `WORKSHOP_SPEAKERS`
--

INSERT INTO `WORKSHOP_SPEAKERS` (`workshop_id`, `user_id`) VALUES
(5, 1),
(6, 1),
(8, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
