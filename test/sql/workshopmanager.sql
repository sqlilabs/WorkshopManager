-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Sam 16 Février 2013 à 23:32
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
(8, 1),
(9, 1),
(9, 2);

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
  `charterAgree` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `USER`
--

INSERT INTO `USER` (`id`, `email`, `first_name`, `last_name`, `picture`, `role`, `charterAgree`) VALUES
(1, 'yannick.chartois@gmail.com', 'Yannick', 'Chartois', 'https://lh5.googleusercontent.com/-8rFmjE_z2do/AAAAAAAAAAI/AAAAAAAAAGM/kdGz_0hCplQ/photo.jpg', 'admin', 1),
(2, 'yannig.corp@gmail.com', 'yannig', 'yannig', 'https://lh5.googleusercontent.com/-_10nuInSTVs/AAAAAAAAAAI/AAAAAAAAAAA/bgePzMZAQ78/photo.jpg', NULL, 0);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=11 ;

--
-- Contenu de la table `WORKSHOP`
--

INSERT INTO `WORKSHOP` (`id`, `description`, `image`, `subject`, `author_id`, `workshopSession_id`) VALUES
(5, 'Caching data is a typical optimization in modern applications, and so Play provides a global cache. An important point about the cache is that it behaves just like a cache should: the data you just stored may just go missing.\r\n\r\nFor any data stored in the cache, a regeneration strategy needs to be put in place in case the data goes missing. This philosophy is one of the fundamentals behind Play, and is different from Java EE, where the session is expected to retain values throughout its lifetime.\r\n\r\nThe default implementation of the cache API uses EHCache. You can also provide your own implementation via a plugin.\r\n', 'images/workshops/play.png', 'sdfgsgsdg', 1, 1),
(6, 'That''s fine, and even great, but why should images get all the glory? Shouldn''t other elements inside the document be able to carry a tooltip as well, such as a text link or form element? Wouldn''t that be pretty cool, not be mention crucial in guiding devises with limited capabilities such as PDAs navigate your site and its various elements? Well, starting in HTML 4, the concept of the "alt" attribute for images have been expanded to all elements on the page.\r\n', 'images/workshops/play.png', 'zerazerazre', 1, 2),
(8, 'Caching data is a typical optimization in modern applications, and so Play provides a global cache. An important point about the cache is that it behaves just like a cache should: the data you just stored may just go missing.\r\n\r\nFor any data stored in the cache, a regeneration strategy needs to be put in place in case the data goes missing. This philosophy is one of the fundamentals behind Play, and is different from Java EE, where the session is expected to retain values throughout its lifetime.\r\n\r\nThe default implementation of the cache API uses EHCache. You can also provide your own implementation via a plugin.', 'images/workshops/play.png', 'The Play cache API', 1, 3),
(9, 'Bootstrap was made to not only look and behave great in the latest desktop browsers (as well as IE7!), but in tablet and smartphone browsers via responsive CSS as well.', 'images/workshops/default.png', 'Un super workshop tout nouveau', 1, NULL),
(10, '<p>JavaScript (quelques fois abrégé JS) est un langage de programmation de scripts principalement utilisé dans les pages web interactives mais aussi côté serveur1. </p>\r\n\r\n<p>C''est un langage orienté objet à prototype, c''est-à-dire que les bases du langage et ses principales interfaces sont fournies par des objets qui ne sont pas des instances de classes, mais qui sont chacun équipés de constructeurs permettant de créer leurs propriétés, et notamment une propriété de prototypage qui permet d''en créer des objets héritiers personnalisés.</p>\r\n\r\n<p>Le langage a été créé en 1995 par Brendan Eich (Brendan Eich étant membre du conseil d''administration de la fondation Mozilla) pour le compte de Netscape Communications Corporation. Le langage, actuellement à la version 1.8.2 est une implémentation de la 3e version de la norme ECMA-262 qui intègre également des éléments inspirés du langage Python. La version 1.8.5 du langage est prévue pour intégrer la 5e version du standard ECMA2.</p>', 'images/workshops/play.png', 'Javascript 2', 1, NULL);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `WORKSHOP_SESSION`
--

INSERT INTO `WORKSHOP_SESSION` (`id`, `doodle_url`, `location`, `nextPlay`, `speaker_id`) VALUES
(1, 'http://www.doodle.com/tr4gqnpedqxaxbeb', 'ssgsdfgsdg', '2013-01-30 23:00:00', 1),
(2, 'http://twitter.github.com/bootstrap/scaffolding.html', 'fsdfgsdfgsd', '2013-01-16 23:00:00', 1),
(3, 'http://localhost:9000/workshops/planifier/8', 'fsdgsdfgdsg', '2013-02-20 23:00:00', 1);

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
(8, 1),
(9, 2),
(10, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
