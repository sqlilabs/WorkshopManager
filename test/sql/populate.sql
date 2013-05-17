INSERT INTO `USER` (`id`, `first_name`, `last_name`, `email`, `picture`, `role`, `charterAgree`) VALUES
(1, 'Yannick', 'CHARTOIS', 'ychartois@sqli.com', '/assets/images/avatar-default.png', 'admin', 1),
(10, 'Jérôme', 'Mivielle', 'jemivielle@sqli.com', '/assets/images/avatar-default.png', 'admin', 1),
(2, 'Vincent', 'Dubois', 'vdubois@sqli.com', '/assets/images/avatar-default.png', 'standard', 1),
(3, 'Remi', 'Goyard', 'rgoyard@sqli.com', '/assets/images/avatar-default.png', 'admin', 1),
(4, 'Frédéric', 'Gracia', 'fgracia@sqli.com', '/assets/images/avatar-default.png', 'admin', 1),
(7, 'Christian Alonso', 'Chavez Ley', 'cachavezley@sqli.com', '/assets/images/avatar-default.png', 'admin', 1),
(9, 'Miguel', 'Diez Garcia', 'mdiezgarcia@sqli.com', '/assets/images/avatar-default.png', 'admin', 1),
(11, 'Vincent', 'SIMON', 'vsimon@sqli.com', '/assets/images/avatar-default.png', 'standard', 1),
(12, 'Julien', 'Albert', 'jalbert@sqli.com', '/assets/images/avatar-default.png', 'admin', 1),
(13, 'Abdessamed', 'Chadouli', 'achadouli@sqli.com', '/assets/images/avatar-default.png', 'admin', 1),
(14, 'Mathieu', 'Hicauber', 'mhicauber@sqli.com', '/assets/images/avatar-default.png', 'admin', 1),
(22, 'Christophe', 'Opoix', 'copoix@sqli.com', '/assets/images/avatar-default.png', 'admin', 1),
(16, 'Vincent', 'DUPA', 'vdupa@sqli.com', '/assets/images/avatar-default.png', 'standard', 1),
(17, 'Stéphane', 'Valadier', 'svaladier@sqli.com', '/assets/images/avatar-default.png', 'admin', 1),
(18, 'Olivier', 'Guillerm', 'oguillerm@sqli.com', '/assets/images/avatar-default.png', 'standard', 1),
(19, 'Christophe', 'Frezier', 'cfrezier@sqli.com', '/assets/images/avatar-default.png', 'admin', 1);

INSERT INTO `RESSOURCE` (`id`, `ws_support_file`, `ws_support_link`) VALUES
(1, 'ressources/[2013-02] - Les Certifications Java /Workshop - Design Pattern JEE.pptx', 'http://www.avaje.org/static/javadoc/pub/com/avaje/ebean/Filter.html');


INSERT INTO `WORKSHOP` (`id`, `subject`, `summary`, `description`, `image`, `author_id`, `creation_date`, `workshop_ressources_id`)  VALUES
(1, 'Test-Driven Developpement', 'Tests de Dev, Tests Non-Régression, Behavior Driven-Developpement, Data Driven Testing', 'Un petit aperçu de la TDD', 'images/workshops/sTEyF.jpg', 1, '2013-04-16 21:42:21', NULL),
(2, 'les Design Pattern Java EE', 'Ceux que vous utilisez tous les jours: Service Locator, MVC, Business Delegate, DAO, Transfert Object, Facade,...', 'Le but est d''avoir une définition rapide de ces designs qui sont la plupart du temps déjà implémentés dans les frameworks: à quoi il servent, leurs avantages,... \r\nBref un tour d''horizon pour comprendre les architectures JEE de base.', 'images/workshops/J2EE_Design_Patterns.jpg', 1, '2013-04-16 21:42:21', NULL),
(3, 'Comprendre la Virtualisation', 'Présentation générale du principe, intérêts, avantages, risques et inconvénients.', 'Cas concrets d''utilisation sur les différents plateaux de l''agence, conseils d''utilisation et d''optimisation.\r\nPrésentation rapide des serveurs de virtualisation de l''agence.', 'images/workshops/virtualbox.png', 1, '2013-04-16 21:42:21', NULL),
(4, 'Au retour de la Devoxx', 'En avril 2012 j''étais à Devoxx France !\r\nVoici un retour sur la conférence et un hyper résumé des conférences vues, au programme : HTML 5 / Javascript / Mobilité / Node.js / Java / Guava / Cloud / Play 2 / DevOps et d''autres choses bien intéressantes !', '', 'images/workshops/logo_devoxx_france_big.jpg', 1, '2013-04-16 21:42:21', NULL),
(5, 'Les Certifications Java ', 'C''est quoi? Ça m''apporte quoi? Ça coute combien? Quelles sont les démarches? Ça se prépare comment? En combien de temps? Est-ce que je serai immensément riche après le(s) avoir passé(es)?\r\nToutes les questions que vous vous posez sur les certifications Java ^^', '', 'images/workshops/duke_cert_kab_V3a.jpg', 1, '2013-04-16 21:42:21', 1),
(6, 'Découverte de l''agité avec le framework SCRUM', 'Le workshop a pour objectif de : \r\n- Découvrir les principes fondamentaux de Scrum\r\n- Connaître quelques bonnes pratiques éprouvées dans la conduite de méthodes agiles', '', 'images/workshops/Certified ScrumMaster Basic.jpg', 1, '2013-04-16 21:42:21', NULL),
(7, 'Apache', 'Une présentation de HTTP, du serveur apache, virtual hosts et règles de réécriture .\r\nDes exemples et un peu de théorie ', '', 'images/workshops/apache.jpg', 1, '2013-04-16 21:42:21', NULL),
(8, 'Struts', 'Venez découvrir une présentation complète du framework Struts où seront abordés les bases comme les utilisations avancées : \r\ncontrôleurs dynamiques, validation dynamique côté client et serveur, système de templating... ', '', 'images/workshops/struts.gif', 1, '2013-04-16 21:42:21', NULL),
(9, 'Pimp my Tests', 'Vous avez envie de faire des tests unitaires mais vous trouvez JUnit trop contraignant? Venez voir comment :', '- Tester indépendamment vos couches (DAO, Services, Web)\r\n- Utiliser des assertions parlantes\r\nau lieu de "s''assurer de l’égalité (ce que j''attends, résultat)"\r\nutiliser "s''assurer que ce que j''obtiens et égal à ce que j''attends"\r\n- Tester des cas limites (tester unitairement une méthode static privée dans une classe finale, par exemple)\r\n- Principes de DDT (Data Driven Testing)\r\nUtiliser le même test sur plusieurs jeux de données de façon rapide et simple', 'images/workshops/junit-logo.gif', 1, '2013-04-16 21:42:21', NULL),
(10, 'Retour du JUG Summer Camp', 'Une présentation des différentes conférences auxquelles nous avons assisté dans le cadre du JUG Summer Camp.', '', 'images/workshops/summercamp7.png', 1, '2013-04-16 21:42:21', NULL),
(11, 'les Portlets', 'Vous avez déjà entendu parler de "portlets" mais vous ne savez pas très bien de quoi il s''agit ???\r\nVous en avez déjà fait mais vous ne savez pas comment ça peut arriver à marcher "ce truc bizarre" ???\r\nVous n''avez jamais en entendu parler mais vous êtes curieux ???', 'Je vous invite à me joindre dans ce workshop pour une présentation sommaire de tout qui tourne au tour du monde des portails, portlets, JSR 168 & 286, solutions déjà utilisés dans certains de nos projets pour LaBanquePostale ou Cofinoga, etc... ', 'images/workshops/portlet.jpg', 9, '2013-04-16 21:42:21', NULL),
(12, 'Javascript première partie', 'Les bases du langage et des trucs à savoir pour améliorer vos scripts. \r\nLa syntaxe, les "easter eggs" du langages, et un peu de DOM ... ', '', 'images/workshops/javascript.png', 1, '2013-04-16 21:42:21', NULL),
(13, 'Linux', 'Présentation générale de Linux & Unix, de leur histoire, leurs différences, leurs distributions, leur intérêt tant pour la production que pour une utilisation personnelle, etc. Commandes utiles à connaître et conseils d''utilisation.', 'Initiation au contrôle d''utilisation des ressources d''un serveur (processus, espace disque, etc.). ', 'images/workshops/Tux.svg.png', 1, '2013-04-16 21:42:21', NULL),
(14, 'Java Puzzlers, Part 1', 'Présentation des quelques Puzzles Java créés par Mr Joshua Bloch pour approfondir dans les subtilités du langage. \r\n\r\nP.S. Il y aura de bonbons pour les réponses correctes aux puzzles ;-)', '', 'images/workshops/java_logo.png', 1, '2013-04-16 21:42:21', NULL),
(15, 'Les architectures REST et Jersey', 'Ce workshop se propose d’exposer les grandes lignes d’une architecture REST, et fait un tour rapide du framework Jersey permettant d’exposer (facilement) des services REST en Java.', 'Plan :\r\nREST\r\n\r\n5 principes d’une architecture REST\r\nSpécification d’une ressource REST (exemple)\r\n\r\nJersey\r\nLes frameworks Java du marché\r\nMise en place d’un projet avec Jersey\r\nContrôleur\r\nFiltres\r\nGestion des erreurs\r\nGestion des appels Ajax Cross-Domain avec CORS\r\nFormat des donnnées\r\nInjections d’objets personnalisés\r\nFiltres utiles\r\nTUAs', 'images/workshops/Jersey.png', 1, '2013-04-16 21:42:21', NULL),
(16, 'Design Pattern GoF - Part1', 'Présentation de 5 patterns GoF de comportement: Strategy, State, Visitor, Iterator, Observer.\r\n\r\nL''idée est d''avoir une fiche signalitique par Design: définition, représentation UML, et un exemple d''application avec son code Java. ', '', 'images/workshops/101bc9335f208403ae77673a21027a64.jpg', 1, '2013-04-16 21:42:21', NULL),
(17, 'Gestion de mémoire sur Java', 'Explication des pointers, allocation de mémoire statique / dynamique, les leaks de mémoire, fonctionnement du garbage collector', 'http://www.stickyminds.com/BetterSoftware/magazine.asp?fn=cifea&id=99\r\n\r\nhttp://www.javaworld.com/javaworld/javaqa/1999-08/04-qa-leaks.html\r\n\r\nhttp://java.dzone.com/articles/java-performance-tuning\r\n\r\nhttps://booster-sqli.bluekiwi.net/space/in/Kiosk_Java_JEE_and_co./notebook/note?id=24409#comment-24435', 'images/workshops/java_logo.png', 1, '2013-04-16 21:42:21', NULL),
(18, 'Structures des données', 'List, Array, Map, Hashing, Tree, Stack, Concurrency où pourquoi Java nous offre plus que ArrayList et HashMap :)', '', 'images/workshops/java_logo.png', 1, '2013-04-16 21:42:21', NULL),
(19, 'Compilateurs', 'Différences entre les analyseurs lexique, syntactique et semantique\r\n\r\nDifférences entre erreurs de compilation / exécution. Langages compilés / interpretés\r\n\r\nDSLs (DomainSpecificLanguage)', '', 'images/workshops/0201100886.01.LZZZZZZZ.jpg', 1, '2013-04-16 21:42:21', NULL),
(20, 'Paradigmes de Programmation', 'Qu''est-ce que c''est un paradigme de programmation? Quelles sont les conséquences / implications de choisir un paradigme ou un autre?\r\n\r\nImperatif / Declaratif\r\nOO, Logique, Fonctionnel', '', 'images/workshops/programmer.jpg', 1, '2013-04-16 21:42:21', NULL),
(21, 'Concurrence / Parallélisme / Répartie', 'Aujourd''hui tous les PC et même smartphone sont multi coeurs, je trouve dommage que dans les fait on en utilise un seul par appli. ', 'Il y aurait-il une bonne âme pour éclaircir ces notions en JAVA pour définir dans quel cas on utilise l''un l''autre (Retour de performance etc. Problème que cela soulève etc etc...) ?\r\n\r\nhttp://www.javaworld.com/javaworld/jw-06-2012/120618-jw-archive-under-the-hood-threads-and-shared-data.html?source=IFWNLE_jw_2012-06-19\r\nhttp://www.javaworld.com/javaworld/jw-06-2012/120626-modern-threading.html', 'images/workshops/concurrency-in-practise.jpg', 1, '2013-04-16 21:42:21', NULL),
(22, 'Les solutions de stockage', 'Description des principales solutions de stockage. Une partie concernant ce qu''il est possible/préconisé de mettre en place dans une entreprise, et une partie à titre personnel, afin que vous puissiez savoir ce qu''il peut être intéressant d''utiliser chez soi.', 'Par la même occasion, l''architecture de stockage de l''agence de Bordeaux vous sera présentée.', 'images/workshops/63812_a-scientist-performs-maintenance-in-the-cern-lhc-computing-grid-center-in-geneva.jpg', 1, '2013-04-16 21:42:21', NULL),
(23, 'Utilisation optimale de Gmail', 'Tout ce qu''il est possible de faire (à ma connaissance) et qui vous facilitera la vie sur Gmail. Libellés, filtres, plusieurs boites aux lettres, labs, méthodes de recherche rapide', 'Mais aussi la présentation de Google Drive ainsi que les configurations possibles de vos mails/agendas/contacts sur votre smartphone.', 'images/workshops/Gmail-cloud.png',1, '2013-04-16 21:42:21', NULL),
(24, 'Initiation aux Expressions Régulières', 'Présenter ce que sont les expressions régulières (de leur petit nom "RegExp"), à quoi elles servent, et terminer avec une poignée d''exemple de manipulation de regexp, et quelques exercices type TP commun.', 'Je prévois quelque chose d''assez rapide (séance de 30 min environ), condensée et surtout imagée.', 'images/workshops/fts-regex-front_1_.jpg', 1, '2013-04-16 21:42:21', NULL),
(25, 'Twitter Bootstrap', 'Qu''est-ce que c''est? Comment ça fonctionne? Quels sont les avantages? inconvénients? Délai de prise en main?', '', 'images/workshops/illus_bootstrap.jpg', 1, '2013-04-16 21:42:21', NULL),
(26, 'Développeur connecté :  organiser sa veille technologique ', 'Devant la multitude d''informations disponibles (RSS, forum, vidéos présentations, etc), comment organiser sa veille techno ? ', '', 'images/workshops/3602683294_4c9c688bbc-300x300.jpg', 1, '2013-04-16 21:42:21', NULL);


INSERT INTO `WORKSHOP_SESSION` (`id`, `location`, `limite_place`, `next_play`, `speaker_id`, `workshop_id`) VALUES
(1, 'Smith Haut Lafitte', 10, '2013-04-30 00:00:00', 7, 1),
(2, 'Smith Haut Lafitte',10, '2013-04-25 00:00:00', 1, 2),
(3, 'test',10, '2013-02-20 00:00:00', 4, 7),
(4, 'Smith Haut Lafitte',10, '2013-02-02 00:00:00', 1, 3),
(5, 'Smith Haut Lafitte',10, '2013-12-02 00:00:00', 2, 4),
(6, 'Smith Haut Lafitte',10, '2013-10-02 00:00:00', 7, 5),
(7, 'Smith Haut Lafitte',10, '2013-04-26 00:00:00', 10, 6);

INSERT INTO `COMMENT` (`id`, `author_id`, `creation_date`, `comment`, `workshop_id`) VALUES
(1, 1, '2013-04-09 08:27:13', 'omment calculer la position et la normale dans le vertex shader - deuxième partie : les performances, une tra... http://bit.ly/16L51Nl ', 5),
(2, 1, '2013-04-09 08:27:30', 'Lingual ("true SQL for Cascading and Apache Hadoop") is now public on github. https://github.com/Cascading/lingual … (via @julianhyde)', 3),
(3, 1, '2013-04-09 08:27:46', 'Koken: a free PHP CMS designed for photographers, designers and creatives to showcase their work - http://koken.me ', 3);


INSERT INTO `WORKSHOP_SPEAKERS` (`workshop_id`, `user_id`) VALUES
(2, 1),
(4, 13),
(4, 14),
(5, 1),
(6, 13),
(6, 16),
(6, 22),
(7, 22),
(11, 9),
(12, 13),
(12, 22),
(13, 4),
(13, 16),
(16, 1),
(16, 22),
(19, 22),
(24, 13),
(25, 13),
(26, 13);

INSERT INTO `POTENTIAL_PARTICIPANTS` (`workshop_id`, `user_id`) VALUES
(4, 1),
(4, 2),
(4, 14),
(4, 16),
(4, 19),
(4, 22),
(7, 1),
(7, 16),
(7, 18),
(7, 22),
(10, 16),
(11, 16),
(12, 16),
(12, 18),
(13, 18),
(14, 4),
(16, 16),
(19, 22),
(25, 2),
(25, 19);