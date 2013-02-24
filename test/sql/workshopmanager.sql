-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Dim 24 Février 2013 à 18:46
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
-- Structure de la table `COMMENT`
--

CREATE TABLE IF NOT EXISTS `COMMENT` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `creation_date` date NOT NULL,
  `comment` varchar(500) NOT NULL,
  `author_id` bigint(20) NOT NULL,
  `workshop_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=6 ;

--
-- Contenu de la table `COMMENT`
--

INSERT INTO `COMMENT` (`id`, `creation_date`, `comment`, `author_id`, `workshop_id`) VALUES
(1, '2013-02-17', 'test', 1, 5),
(2, '2013-02-17', 'un autre super commentaire', 1, 6),
(3, '2013-02-17', 'une autre commentaire !!!!', 2, 5),
(4, '2013-02-17', 'L’entité n’a pas été insérée en Base de données et donc elle est restée en dehors du champ d’Hibernate. Certes, vous l’avez ajoutée à la liste des B de A qui, elle, est attachée. La belle affaire ! C’est B qui est propriétaire du lien. Lorsque A est sauvegardé, la collection des B est purement et simplement – et silencieusement :-( - ignorée par Hibernate, qui aurait parfaitement pu se rendre compte de l’erreur mais qui ne le fait pas !', 2, 5),
(5, '2013-02-22', 'fgxhcxfghghdfhdh', 1, 8);

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
(11, 1);

-- --------------------------------------------------------

--
-- Structure de la table `RESSOURCE`
--

CREATE TABLE IF NOT EXISTS `RESSOURCE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ws_support_file` varchar(200) DEFAULT NULL,
  `ws_support_link` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Contenu de la table `RESSOURCE`
--

INSERT INTO `RESSOURCE` (`id`, `ws_support_file`, `ws_support_link`) VALUES
(1, NULL, 'http://stackoverflow.com/questions/4674265/what-is-causing-a-hibernate-sql-query-exception'),
(2, NULL, NULL),
(3, NULL, NULL),
(4, 'public/ressources/Projet_Analyse_fonctionnelle.pdf', NULL),
(5, 'ressources/[2013-02] - The Play cache API/phpvsjava.pdf', 'http://www.iconarchive.com/show/sleek-xp-basic-icons-by-deleket/Files-icon.html'),
(6, NULL, 'http://twitter.github.com/bootstrap/base-css.html#buttons'),
(7, 'public/ressources/ressource_CI1.pdf', NULL);

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
  `role` varchar(255) DEFAULT 'standard',
  `charterAgree` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `USER`
--

INSERT INTO `USER` (`id`, `email`, `first_name`, `last_name`, `picture`, `role`, `charterAgree`) VALUES
(1, 'yannick.chartois@gmail.com', 'Yannick', 'Chartois', 'https://lh5.googleusercontent.com/-8rFmjE_z2do/AAAAAAAAAAI/AAAAAAAAAGM/kdGz_0hCplQ/photo.jpg', 'admin', 1),
(2, 'yannig.corp@gmail.com', 'yannig', 'yannig', 'https://lh5.googleusercontent.com/-_10nuInSTVs/AAAAAAAAAAI/AAAAAAAAAAA/bgePzMZAQ78/photo.jpg', 'standard', 1);

-- --------------------------------------------------------

--
-- Structure de la table `WORKSHOP`
--

CREATE TABLE IF NOT EXISTS `WORKSHOP` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `summary` varchar(300) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `workshopSession_id` bigint(20) DEFAULT NULL,
  `workshopRessources_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=14 ;

--
-- Contenu de la table `WORKSHOP`
--

INSERT INTO `WORKSHOP` (`id`, `summary`, `description`, `image`, `subject`, `author_id`, `workshopSession_id`, `workshopRessources_id`) VALUES
(5, 'Ce deuxième cas de figure est très traitre. Il arrive fréquemment lorsqu’une pièce de code ajoute une nouvelle entité B à la liste des entités B de A. Hélas, vous avez oublié d’appeler', 'Caching data is a typical optimization in modern applications, and so Play provides a global cache. An important point about the cache is that it behaves just like a cache should: the data you just stored may just go missing.\r\n\r\nFor any data stored in the cache, a regeneration strategy needs to be put in place in case the data goes missing. This philosophy is one of the fundamentals behind Play, and is different from Java EE, where the session is expected to retain values throughout its lifetime.\r\n\r\nThe default implementation of the cache API uses EHCache. You can also provide your own implementation via a plugin.\r\n', 'images/workshops/play.png', 'sdfgsgsdg', 1, 1, 6),
(6, 'Ce deuxième cas de figure est très traitre. Il arrive fréquemment lorsqu’une pièce de code ajoute une nouvelle entité B à la liste des entités B de A. Hélas, vous avez oublié d’appeler', 'That''s fine, and even great, but why should images get all the glory? Shouldn''t other elements inside the document be able to carry a tooltip as well, such as a text link or form element? Wouldn''t that be pretty cool, not be mention crucial in guiding devises with limited capabilities such as PDAs navigate your site and its various elements? Well, starting in HTML 4, the concept of the "alt" attribute for images have been expanded to all elements on the page.\r\n', 'images/workshops/play.png', 'zerazerazre', 1, 2, 7),
(8, 'Ce deuxième cas de figure est très traitre. Il arrive fréquemment lorsqu’une pièce de code ajoute une nouvelle entité B à la liste des entités B de A. Hélas, vous avez oublié d’appeler', 'Caching data is a typical optimization in modern applications, and so Play provides a global cache. An important point about the cache is that it behaves just like a cache should: the data you just stored may just go missing.\r\n\r\nFor any data stored in the cache, a regeneration strategy needs to be put in place in case the data goes missing. This philosophy is one of the fundamentals behind Play, and is different from Java EE, where the session is expected to retain values throughout its lifetime.\r\n\r\nThe default implementation of the cache API uses EHCache. You can also provide your own implementation via a plugin.', 'images/workshops/play.png', 'The Play cache API', 1, 3, 5),
(9, 'Ce deuxième cas de figure est très traitre. Il arrive fréquemment lorsqu’une pièce de code ajoute une nouvelle entité B à la liste des entités B de A. Hélas, vous avez oublié d’appeler', 'Bootstrap was made to not only look and behave great in the latest desktop browsers (as well as IE7!), but in tablet and smartphone browsers via responsive CSS as well.', 'images/workshops/default.png', 'Un super workshop tout nouveau', 1, 7, NULL),
(10, 'JavaScript (quelques fois abrégé JS) est un langage de programmation de scripts principalement utilisé dans les pages web interactives mais aussi côté serveur1.', '<p>C''est un langage orienté objet à prototype, c''est-à-dire que les bases du langage et ses principales interfaces sont fournies par des objets qui ne sont pas des instances de classes, mais qui sont chacun équipés de constructeurs permettant de créer leurs propriétés, et notamment une propriété de prototypage qui permet d''en créer des objets héritiers personnalisés.</p>\r\n\r\n<p>Le langage a été créé en 1995 par Brendan Eich (Brendan Eich étant membre du conseil d''administration de la fondation Mozilla) pour le compte de Netscape Communications Corporation. Le langage, actuellement à la version 1.8.2 est une implémentation de la 3e version de la norme ECMA-262 qui intègre également des éléments inspirés du langage Python. La version 1.8.5 du langage est prévue pour intégrer la 5e version du standard ECMA2.</p>', 'images/workshops/play.png', 'Javascript 2', 1, NULL, NULL),
(11, 'Ce deuxième cas de figure est très traitre. Il arrive fréquemment lorsqu’une pièce de code ajoute une nouvelle entité B à la liste des entités B de A. Hélas, vous avez oublié d’appeler', 'Copyright © 1998-2012 Caucho Technology, Inc. All rights reserved. Resin ® is a registered trademark. Quercustm, and Hessiantm are trademarks of Caucho Technology.\r\n\r\nCloud-optimized Resin Server is a Java EE certified Java Application Server, and Web Server, and Distributed Cache Server (Memcached).\r\nLeading companies worldwide with demand for reliability and high performance web applications including SalesForce.com, CNET, DZone and many more are powered by Resin.', 'images/workshops/default.png', 'fgdhdghgf', 1, NULL, NULL),
(12, 'En informatique, et plus particulièrement en développement logiciel, un patron de conception (en anglais : « design pattern ») est un arrangement caractéristique de modules, reconnu comme bonne pratique en réponse à un problème de conception d''un logiciel. ', '<p>Un patron de conception est issu de l''expérience des concepteurs de logiciels2. Il décrit sous forme de diagrammes un arrangement récurrent de rôles et d''actions joués par des modules d''un logiciel, et le nom du patron sert de vocabulaire commun entre le concepteur et le programmeur3. D''une manière analogue à un patron de couture, le patron de conception décrit les grandes lignes d''une solution, qui peuvent ensuite être modifiées et adaptées en fonction des besoins4.</p>\r\n<p>Les patrons de conception décrivent des procédés de conception généraux et permettent en conséquence de capitaliser l''expérience appliquée à la conception de logiciel. Ils ont une influence sur l''architecture logicielle d''un système informatique.</p>', 'images/workshops/default.png', 'Design Pattern GoF', 1, NULL, NULL),
(13, 'REST (REpresentational State Transfer) est un style d’architecture pour les systèmes hypermédia distribués, créé par Roy Fielding en 2000 dans le chapitre 5 de sa thèse de doctorat1.', 'REST n’est pas un protocole (tel que HTTP) ou un format. Ce style d''architecture est particulièrement bien adapté au World Wide Web mais n''est pas dépendant du Web. Les contraintes, telles que définies par Roy Fielding, peuvent s''appliquer à d''autres protocoles d''application que HTTP.', 'images/workshops/Jersey.png', 'REST API', 1, NULL, NULL);

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=8 ;

--
-- Contenu de la table `WORKSHOP_SESSION`
--

INSERT INTO `WORKSHOP_SESSION` (`id`, `doodle_url`, `location`, `nextPlay`, `speaker_id`) VALUES
(1, 'http://www.doodle.com/tr4gqnpedqxaxbeb', 'ssgsdfgsdg', '2013-01-30 23:00:00', 1),
(2, 'http://twitter.github.com/bootstrap/scaffolding.html', 'fsdfgsdfgsd', '2013-01-16 23:00:00', 1),
(3, 'http://localhost:9000/workshops/planifier/8', 'fsdgsdfgdsg', '2013-02-20 23:00:00', 1),
(7, 'http://sqdfqfds@sqdqs.com', 'test', '2013-02-27 23:00:00', 1);

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
