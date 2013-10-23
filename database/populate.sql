INSERT INTO `USER` (`id`, `first_name`, `last_name`, `email`, `picture`, `role`, `charterAgree`) VALUES
(2, 'Yannick', 'Chartois', 'ychartois@test.com', 'https://lh5.googleusercontent.com/-8rFmjE_z2do/AAAAAAAAAAI/AAAAAAAAAGM/kdGz_0hCplQ/photo.jpg', 'admin', 1),
(3, 'Remi', 'Goyard', 'rgoyard@test.com', 'http://www.gravatar.com/avatar/6b9b1cd5a64efe296f2e7135af88cdf5', 'admin', 1),
(4, 'Christian Alonso', 'Chavez Ley', 'cachavezley@test.com', 'http://farm3.staticflickr.com/2248/2282734669_a7f431e660_o.jpg', 'admin', 1),
(5, 'Miguel', 'Diez Garcia', 'mdiezgarcia@test.com', 'https://www.gravatar.com/avatar/9abf70f2c2c58bcd0446aeed713d1767?s=128&d=identicon&r=PG', 'admin', 1),
(6, 'Sylvie', 'Dupont', 'sdupont@test.com', 'https://www.gravatar.com/avatar/cd8bf68b7a6006e602285a1622ca6ed1?s=128&d=identicon&r=PG', 'standard', 1),
(7, 'Marie', 'Dupont', 'mdupont@test.com', 'https://www.gravatar.com/avatar/3e6e3edf3968e015b34228ba17afcbf0?s=128&d=identicon&r=PG&f=1', 'standard', 1),
(8, 'Greg', 'Dupont', 'gdupont@test.com', 'https://www.gravatar.com/avatar/427e2bfe1897620b552cab93c274cd0d?s=128&d=identicon&r=PG', 'standard', 1);

INSERT INTO `RESSOURCE` (`id`, `ws_support_file`, `ws_support_link`) VALUES
(1, 'ressources/[2013-02] - Les Certifications Java /Workshop - Design Pattern JEE.pptx', 'http://www.avaje.org/static/javadoc/pub/com/avaje/ebean/Filter.html');


INSERT INTO `WORKSHOP` (`id`, `subject`, `summary`, `description`, `image`, `author_id`, `creation_date`, `workshop_ressources_id`)  VALUES
(1, 'Test-Driven Developpement', 'Tests de Dev, Tests Non-Régression, Behavior Driven-Developpement, Data Driven Testing', 'Un petit aperçu de la TDD', 'images/workshops/sTEyF.jpg', 2, '2013-04-16 21:42:21', 1),
(2, 'les Design Pattern Java EE', 'Ceux que vous utilisez tous les jours: Service Locator, MVC, Business Delegate, DAO, Transfert Object, Facade,...', 'Le but est d''avoir une définition rapide de ces designs qui sont la plupart du temps déjà implémentés dans les frameworks: à quoi il servent, leurs avantages,... \r\nBref un tour d''horizon pour comprendre les architectures JEE de base.', 'images/workshops/J2EE_Design_Patterns.jpg', 2, '2013-04-16 21:42:21', NULL),
(3, 'Comprendre la Virtualisation', 'Présentation générale du principe, intérêts, avantages, risques et inconvénients.', 'Cas concrets d''utilisation sur les différents plateaux de l''agence, conseils d''utilisation et d''optimisation.\r\nPrésentation rapide des serveurs de virtualisation de l''agence.', 'images/workshops/virtualbox.png', 3, '2013-04-16 21:42:21', NULL),
(4, 'Au retour de la Devoxx', 'En avril 2012 j''étais à Devoxx France !\r\nVoici un retour sur la conférence et un hyper résumé des conférences vues, au programme : HTML 5 / Javascript / Mobilité / Node.js / Java / Guava / Cloud / Play 2 / DevOps et d''autres choses bien intéressantes !', '', 'images/workshops/logo_devoxx_france_big.jpg', 7, '2013-04-16 21:42:21', NULL),
(5, 'Les Certifications Java ', 'C''est quoi? Ça m''apporte quoi? Ça coute combien? Quelles sont les démarches? Ça se prépare comment? En combien de temps? Est-ce que je serai immensément riche après le(s) avoir passé(es)?\r\nToutes les questions que vous vous posez sur les certifications Java ^^', '', 'images/workshops/duke_cert_kab_V3a.jpg', 2, '2013-04-16 21:42:21', NULL),
(6, 'Découverte de l''agité avec le framework SCRUM', 'Le workshop a pour objectif de : \r\n- Découvrir les principes fondamentaux de Scrum\r\n- Connaître quelques bonnes pratiques éprouvées dans la conduite de méthodes agiles', '', 'images/workshops/Certified ScrumMaster Basic.jpg', 4, '2013-04-16 21:42:21', NULL),
(7, 'Apache', 'Une présentation de HTTP, du serveur apache, virtual hosts et règles de réécriture .\r\nDes exemples et un peu de théorie ', '', 'images/workshops/apache.jpg', 3, '2013-04-16 21:42:21', NULL),
(8, 'Struts', 'Venez découvrir une présentation complète du framework Struts où seront abordés les bases comme les utilisations avancées : \r\ncontrôleurs dynamiques, validation dynamique côté client et serveur, système de templating... ', '', 'images/workshops/struts.gif', 6, '2013-04-16 21:42:21', NULL),
(9, 'Pimp my Tests', 'Vous avez envie de faire des tests unitaires mais vous trouvez JUnit trop contraignant? Venez voir comment :', '- Tester indépendamment vos couches (DAO, Services, Web)\r\n- Utiliser des assertions parlantes\r\nau lieu de "s''assurer de l’égalité (ce que j''attends, résultat)"\r\nutiliser "s''assurer que ce que j''obtiens et égal à ce que j''attends"\r\n- Tester des cas limites (tester unitairement une méthode static privée dans une classe finale, par exemple)\r\n- Principes de DDT (Data Driven Testing)\r\nUtiliser le même test sur plusieurs jeux de données de façon rapide et simple', 'images/workshops/junit-logo.gif', 4, '2013-04-16 21:42:21', NULL),
(10, 'Retour du JUG Summer Camp', 'Une présentation des différentes conférences auxquelles nous avons assisté dans le cadre du JUG Summer Camp.', '', 'images/workshops/summercamp7.png', 8, '2013-04-16 21:42:21', NULL);


INSERT INTO `WORKSHOP_SESSION` (`id`, `location`, `limite_place`, `next_play`, `speaker_id`, `workshop_id`) VALUES
(1, 'A location somewhere', 10, '2013-04-30 00:00:00', 4, 1),
(2, 'A location somewhere',10, '2013-04-25 00:00:00', 2, 2),
(3, 'Auditorium A',10, '2020-02-20 00:00:00', 3, 7),
(4, 'A location somewhere',0, '2020-02-02 00:00:00', 6, 3),
(5, 'A location somewhere',10, '2020-12-02 00:00:00', 1, 3),
(6, 'A location somewhere',0, '2013-10-02 00:00:00', 2, 5),
(7, 'A location somewhere',10, '2020-04-26 00:00:00', 4, 1),
(8, 'A location somewhere',10, '2020-04-28 00:00:00', 4, 1),
(9, 'A location somewhere',0, '2013-04-26 00:00:00', 4, 1);

INSERT INTO `COMMENT` (`id`, `author_id`, `creation_date`, `comment`, `workshop_id`) VALUES
(1, 2, '2013-04-09 08:27:13', 'Comment calculer la position et la normale dans le vertex shader - deuxième partie : les performances, une tra... http://bit.ly/16L51Nl ', 1),
(2, 3, '2013-04-10 08:27:13', 'The pin tumbler lock is a lock mechanism that uses pins of varying lengths to prevent the lock from opening without the correct key. ', 1),
(3, 4, '2013-04-10 08:28:13', 'In functional programming, a monad is a structure that represents computations defined as sequences of steps.', 1),
(4, 5, '2013-04-10 08:28:13', 'A timestamp value represents a single point in time. This can be serialized using a subset of the ISO8601 format and the formats proposed by the W3C note on datetime. In addition, a more relaxed format is also supported for enhanced readability, using white space separation.', 1),
(5, 3, '2013-04-09 08:27:30', 'Lingual ("true SQL for Cascading and Apache Hadoop") is now public on github. https://github.com/Cascading/lingual … (via @julianhyde)', 5),
(6, 4, '2013-04-09 08:27:46', 'Koken: a free PHP CMS designed for photographers, designers and creatives to showcase their work - http://koken.me ', 2);


INSERT INTO `WORKSHOP_SPEAKERS` (`workshop_id`, `user_id`) VALUES
(4, 4),
(4, 5),
(6, 8),
(8, 7),
(8, 3),
(9, 5),
(10, 2),
(10, 3);

INSERT INTO `ACTION` (`id`, `author_id`, `creation_date`, `title`, `description`) VALUES
(1,2,'2013-10-11 12:04:56','New Workshop !','test'),
(2,3,'2013-10-12 12:04:56','New session','test'),
(3,2,'2013-10-13 12:04:56','New Workshop !','test'),
(4,4,'2013-10-14 12:04:56','New session','test'),
(5,5,'2013-10-15 12:04:56','New Workshop !','test'),
(6,5,'2013-10-16 12:04:56','Session modified','test'),
(7,7,'2013-10-17 12:04:56','Session modified','test'),
(8,8,'2013-10-18 12:04:56','Workshop deleted','test');

INSERT INTO `POTENTIAL_PARTICIPANTS` (`workshop_id`, `user_id`) VALUES
(4, 2),
(4, 3),
(4, 6),
(4, 7),
(4, 8),
(6, 3),
(8, 2),
(8, 6),
(8, 7),
(8, 8),
(9, 3),
(9, 4),
(9, 7),
(10, 4),
(10, 5),
(10, 6),
(10, 7),
(10, 8);

INSERT INTO `PARTICIPANTS` (`workshop_id`, `user_id`) VALUES
(1, 4),
(1, 3),
(1, 6),
(1, 8),
(1, 7),
(3, 4),
(3, 5),
(3, 6),
(3, 7),
(3, 8),
(2, 3),
(2, 2),
(2, 6),
(2, 7),
(2, 8),
(3, 3),
(9, 6),
(9, 7),
(5, 4),
(5, 5),
(5, 6),
(5, 7),
(5, 8);