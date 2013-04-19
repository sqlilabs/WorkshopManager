-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Mer 27 Février 2013 à 21:06
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
  `comment` varchar(255) DEFAULT NULL,
  `creation_date` datetime DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `workshop_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK63717A3FA7CD013E` (`author_id`),
  KEY `FK63717A3FAC90337E` (`workshop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
  PRIMARY KEY (`workshop_id`,`user_id`),
  KEY `FK8118E64147140EFE` (`user_id`),
  KEY `FK8118E641AC90337E` (`workshop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `RESSOURCE`
--

CREATE TABLE IF NOT EXISTS `RESSOURCE` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ws_support_file` varchar(255) DEFAULT NULL,
  `ws_support_link` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

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

-- --------------------------------------------------------

--
-- Structure de la table `WORKSHOP`
--

CREATE TABLE IF NOT EXISTS `WORKSHOP` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(1000) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `summary` varchar(300) DEFAULT NULL,
  `author_id` bigint(20) DEFAULT NULL,
  `workshopRessources_id` bigint(20) DEFAULT NULL,
  `workshopSession_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK30C7A187B1F406B7` (`workshopRessources_id`),
  KEY `FK30C7A187A7CD013E` (`author_id`),
  KEY `FK30C7A1873F837396` (`workshopSession_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=27 ;

--
-- Contenu de la table `WORKSHOP`
--

INSERT INTO `WORKSHOP` (`id`, `description`, `image`, `subject`, `summary`, `author_id`, `workshopRessources_id`, `workshopSession_id`) VALUES
(1, 'Un petit aperçu de la TDD', 'images/workshops/sTEyF.jpg', 'Test-Driven Developpement', 'Tests de Dev, Tests Non-Régression, Behavior Driven-Developpement, Data Driven Testing', 1, NULL, NULL),
(2, 'Le but est d''avoir une définition rapide de ces designs qui sont la plupart du temps déjà implémentés dans les frameworks: à quoi il servent, leurs avantages,... \r\nBref un tour d''horizon pour comprendre les architectures JEE de base.', 'images/workshops/J2EE_Design_Patterns.jpg', 'les Design Pattern Java EE', 'Ceux que vous utilisez tous les jours: Service Locator, MVC, Business Delegate, DAO, Transfert Object, Facade,...', 1, NULL, NULL),
(3, 'Cas concrets d''utilisation sur les différents plateaux de l''agence, conseils d''utilisation et d''optimisation.\r\nPrésentation rapide des serveurs de virtualisation de l''agence.', 'images/workshops/virtualbox.png', 'Comprendre la Virtualisation', 'Présentation générale du principe, intérêts, avantages, risques et inconvénients.', 1, NULL, NULL),
(4, '', 'images/workshops/logo_devoxx_france_big.jpg', 'Au retour de la Devoxx', 'En avril 2012 j''étais à Devoxx France !\r\nVoici un retour sur la conférence et un hyper résumé des conférences vues, au programme : HTML 5 / Javascript / Mobilité / Node.js / Java / Guava / Cloud / Play 2 / DevOps et d''autres choses bien intéressantes !', 1, NULL, NULL),
(5, '', 'images/workshops/duke_cert_kab_V3a.jpg', 'Les Certifications Java ', 'C''est quoi? Ça m''apporte quoi? Ça coute combien? Quelles sont les démarches? Ça se prépare comment? En combien de temps? Est-ce que je serai immensément riche après le(s) avoir passé(es)?\r\nToutes les questions que vous vous posez sur les certifications Java ^^', 1, NULL, NULL),
(6, '', 'images/workshops/Certified ScrumMaster Basic.jpg', 'Découverte de l''agité avec le framework SCRUM', 'Le workshop a pour objectif de : \r\n- Découvrir les principes fondamentaux de Scrum\r\n- Connaître quelques bonnes pratiques éprouvées dans la conduite de méthodes agiles', 1, NULL, NULL),
(7, '', 'images/workshops/apache.jpg', 'Apache', 'Une présentation de HTTP, du serveur apache, virtual hosts et règles de réécriture .\r\nDes exemples et un peu de théorie ', 1, NULL, NULL),
(8, '', 'images/workshops/struts.gif', 'Struts', 'Venez découvrir une présentation complète du framework Struts où seront abordés les bases comme les utilisations avancées : \r\ncontrôleurs dynamiques, validation dynamique côté client et serveur, système de templating... ', 1, NULL, NULL),
(9, '- Tester indépendamment vos couches (DAO, Services, Web)\r\n- Utiliser des assertions parlantes\r\nau lieu de "s''assurer de l’égalité (ce que j''attends, résultat)"\r\nutiliser "s''assurer que ce que j''obtiens et égal à ce que j''attends"\r\n- Tester des cas limites (tester unitairement une méthode static privée dans une classe finale, par exemple)\r\n- Principes de DDT (Data Driven Testing)\r\nUtiliser le même test sur plusieurs jeux de données de façon rapide et simple', 'images/workshops/junit-logo.gif', 'Pimp my Tests', 'Vous avez envie de faire des tests unitaires mais vous trouvez JUnit trop contraignant? Venez voir comment :', 1, NULL, NULL),
(10, '', 'images/workshops/summercamp7.png', 'Retour du JUG Summer Camp', 'Une présentation des différentes conférences auxquelles nous avons assisté dans le cadre du JUG Summer Camp.', 1, NULL, NULL),
(11, 'Je vous invite à me joindre dans ce workshop pour une présentation sommaire de tout qui tourne au tour du monde des portails, portlets, JSR 168 & 286, solutions déjà utilisés dans certains de nos projets pour LaBanquePostale ou Cofinoga, etc... ', 'images/workshops/java_logo.png', 'les Portlets', 'Vous avez déjà entendu parler de "portlets" mais vous ne savez pas très bien de quoi il s''agit ???\r\nVous en avez déjà fait mais vous ne savez pas comment ça peut arriver à marcher "ce truc bizarre" ???\r\nVous n''avez jamais en entendu parler mais vous êtes curieux ???', 1, NULL, NULL),
(12, '', 'images/workshops/javascript.png', 'Javascript première partie', 'Les bases du langage et des trucs à savoir pour améliorer vos scripts. \r\nLa syntaxe, les "easter eggs" du langages, et un peu de DOM ... ', 1, NULL, NULL),
(13, 'Initiation au contrôle d''utilisation des ressources d''un serveur (processus, espace disque, etc.). ', 'images/workshops/Tux.svg.png', 'Linux', 'Présentation générale de Linux & Unix, de leur histoire, leurs différences, leurs distributions, leur intérêt tant pour la production que pour une utilisation personnelle, etc. Commandes utiles à connaître et conseils d''utilisation.', 1, NULL, NULL),
(14, '', 'images/workshops/java_logo.png', 'Java Puzzlers, Part 1', 'Présentation des quelques Puzzles Java créés par Mr Joshua Bloch pour approfondir dans les subtilités du langage. \r\n\r\nP.S. Il y aura de bonbons pour les réponses correctes aux puzzles ;-)', 1, NULL, NULL),
(15, 'Plan :\r\nREST\r\n\r\n5 principes d’une architecture REST\r\nSpécification d’une ressource REST (exemple)\r\n\r\nJersey\r\nLes frameworks Java du marché\r\nMise en place d’un projet avec Jersey\r\nContrôleur\r\nFiltres\r\nGestion des erreurs\r\nGestion des appels Ajax Cross-Domain avec CORS\r\nFormat des donnnées\r\nInjections d’objets personnalisés\r\nFiltres utiles\r\nTUAs', 'images/workshops/Jersey.png', 'Les architectures REST et Jersey', 'Ce workshop se propose d’exposer les grandes lignes d’une architecture REST, et fait un tour rapide du framework Jersey permettant d’exposer (facilement) des services REST en Java.', 1, NULL, NULL),
(16, '', 'images/workshops/101bc9335f208403ae77673a21027a64.jpg', 'Design Pattern GoF - Part1', 'Présentation de 5 patterns GoF de comportement: Strategy, State, Visitor, Iterator, Observer.\r\n\r\nL''idée est d''avoir une fiche signalitique par Design: définition, représentation UML, et un exemple d''application avec son code Java. ', 1, NULL, NULL),
(17, 'http://www.stickyminds.com/BetterSoftware/magazine.asp?fn=cifea&id=99\r\n\r\nhttp://www.javaworld.com/javaworld/javaqa/1999-08/04-qa-leaks.html\r\n\r\nhttp://java.dzone.com/articles/java-performance-tuning\r\n\r\nhttps://booster-sqli.bluekiwi.net/space/in/Kiosk_Java_JEE_and_co./notebook/note?id=24409#comment-24435', 'images/workshops/java_logo.png', 'Gestion de mémoire sur Java', 'Explication des pointers, allocation de mémoire statique / dynamique, les leaks de mémoire, fonctionnement du garbage collector', 1, NULL, NULL),
(18, '', 'images/workshops/java_logo.png', 'Structures des données', 'List, Array, Map, Hashing, Tree, Stack, Concurrency où pourquoi Java nous offre plus que ArrayList et HashMap :)', 1, NULL, NULL),
(19, '', 'images/workshops/0201100886.01.LZZZZZZZ.jpg', 'Compilateurs', 'Différences entre les analyseurs lexique, syntactique et semantique\r\n\r\nDifférences entre erreurs de compilation / exécution. Langages compilés / interpretés\r\n\r\nDSLs (DomainSpecificLanguage)', 1, NULL, NULL),
(20, '', 'images/workshops/programmer.jpg', 'Paradigmes de Programmation', 'Qu''est-ce que c''est un paradigme de programmation? Quelles sont les conséquences / implications de choisir un paradigme ou un autre?\r\n\r\nImperatif / Declaratif\r\nOO, Logique, Fonctionnel', 1, NULL, NULL),
(21, 'Il y aurait-il une bonne âme pour éclaircir ces notions en JAVA pour définir dans quel cas on utilise l''un l''autre (Retour de performance etc. Problème que cela soulève etc etc...) ?\r\n\r\nhttp://www.javaworld.com/javaworld/jw-06-2012/120618-jw-archive-under-the-hood-threads-and-shared-data.html?source=IFWNLE_jw_2012-06-19\r\nhttp://www.javaworld.com/javaworld/jw-06-2012/120626-modern-threading.html', 'images/workshops/concurrency-in-practise.jpg', 'Concurrence / Parallélisme / Répartie', 'Aujourd''hui tous les PC et même smartphone sont multi coeurs, je trouve dommage que dans les fait on en utilise un seul par appli. ', 1, NULL, NULL),
(22, 'Par la même occasion, l''architecture de stockage de l''agence de Bordeaux vous sera présentée.', 'images/workshops/63812_a-scientist-performs-maintenance-in-the-cern-lhc-computing-grid-center-in-geneva.jpg', 'Les solutions de stockage', 'Description des principales solutions de stockage. Une partie concernant ce qu''il est possible/préconisé de mettre en place dans une entreprise, et une partie à titre personnel, afin que vous puissiez savoir ce qu''il peut être intéressant d''utiliser chez soi.', 1, NULL, NULL),
(23, 'Mais aussi la présentation de Google Drive ainsi que les configurations possibles de vos mails/agendas/contacts sur votre smartphone.', 'images/workshops/Gmail-cloud.png', 'Utilisation optimale de Gmail', 'Tout ce qu''il est possible de faire (à ma connaissance) et qui vous facilitera la vie sur Gmail. Libellés, filtres, plusieurs boites aux lettres, labs, méthodes de recherche rapide', 1, NULL, NULL),
(24, 'Je prévois quelque chose d''assez rapide (séance de 30 min environ), condensée et surtout imagée.', 'images/workshops/fts-regex-front_1_.jpg', 'Initiation aux Expressions Régulières', 'Présenter ce que sont les expressions régulières (de leur petit nom "RegExp"), à quoi elles servent, et terminer avec une poignée d''exemple de manipulation de regexp, et quelques exercices type TP commun.', 1, NULL, NULL),
(25, '', 'images/workshops/illus_bootstrap.jpg', 'Twitter Bootstrap', 'Qu''est-ce que c''est? Comment ça fonctionne? Quels sont les avantages? inconvénients? Délai de prise en main?', 1, NULL, NULL),
(26, '', 'images/workshops/3602683294_4c9c688bbc-300x300.jpg', 'Développeur connecté :  organiser sa veille technologique ', 'Devant la multitude d''informations disponibles (RSS, forum, vidéos présentations, etc), comment organiser sa veille techno ? ', 1, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `WORKSHOP_SESSION`
--

CREATE TABLE IF NOT EXISTS `WORKSHOP_SESSION` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `doodle_url` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `nextPlay` datetime DEFAULT NULL,
  `speaker_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKE158DFBEAE35BE4A` (`speaker_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `WORKSHOP_SPEAKERS`
--

CREATE TABLE IF NOT EXISTS `WORKSHOP_SPEAKERS` (
  `workshop_id` bigint(20) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`workshop_id`,`user_id`),
  KEY `FK76C6E80C47140EFE` (`user_id`),
  KEY `FK76C6E80CAC90337E` (`workshop_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `WORKSHOP_SPEAKERS`
--

INSERT INTO `WORKSHOP_SPEAKERS` (`workshop_id`, `user_id`) VALUES
(2, 1),
(5, 1),
(16, 1);

--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `COMMENT`
--
ALTER TABLE `COMMENT`
  ADD CONSTRAINT `FK63717A3FAC90337E` FOREIGN KEY (`workshop_id`) REFERENCES `WORKSHOP` (`id`),
  ADD CONSTRAINT `FK63717A3FA7CD013E` FOREIGN KEY (`author_id`) REFERENCES `USER` (`id`);

--
-- Contraintes pour la table `POTENTIAL_PARTICIPANTS`
--
ALTER TABLE `POTENTIAL_PARTICIPANTS`
  ADD CONSTRAINT `FK8118E641AC90337E` FOREIGN KEY (`workshop_id`) REFERENCES `WORKSHOP` (`id`),
  ADD CONSTRAINT `FK8118E64147140EFE` FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`);

--
-- Contraintes pour la table `WORKSHOP`
--
ALTER TABLE `WORKSHOP`
  ADD CONSTRAINT `FK30C7A1873F837396` FOREIGN KEY (`workshopSession_id`) REFERENCES `WORKSHOP_SESSION` (`id`),
  ADD CONSTRAINT `FK30C7A187A7CD013E` FOREIGN KEY (`author_id`) REFERENCES `USER` (`id`),
  ADD CONSTRAINT `FK30C7A187B1F406B7` FOREIGN KEY (`workshopRessources_id`) REFERENCES `RESSOURCE` (`id`);

--
-- Contraintes pour la table `WORKSHOP_SESSION`
--
ALTER TABLE `WORKSHOP_SESSION`
  ADD CONSTRAINT `FKE158DFBEAE35BE4A` FOREIGN KEY (`speaker_id`) REFERENCES `USER` (`id`);

--
-- Contraintes pour la table `WORKSHOP_SPEAKERS`
--
ALTER TABLE `WORKSHOP_SPEAKERS`
  ADD CONSTRAINT `FK76C6E80CAC90337E` FOREIGN KEY (`workshop_id`) REFERENCES `WORKSHOP` (`id`),
  ADD CONSTRAINT `FK76C6E80C47140EFE` FOREIGN KEY (`user_id`) REFERENCES `USER` (`id`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
