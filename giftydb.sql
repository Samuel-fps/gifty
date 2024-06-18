-- MariaDB dump 10.19  Distrib 10.4.28-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: giftydb
-- ------------------------------------------------------
-- Server version	11.4.2-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Sequence structure for `verification_token_seq`
--

DROP SEQUENCE IF EXISTS `verification_token_seq`;
CREATE SEQUENCE `verification_token_seq` start with 1 minvalue 1 maxvalue 9223372036854775806 increment by 50 nocache nocycle ENGINE=InnoDB;
SELECT SETVAL(`verification_token_seq`, 1, 0);

--
-- Table structure for table `gift`
--

DROP TABLE IF EXISTS `gift`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gift` (
  `id` uuid NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` decimal(19,2) DEFAULT NULL,
  `state` enum('POR_COMPRAR','PENDIENTE_DE_RECIBIR','RECIBIDO') DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `person_id` uuid DEFAULT NULL,
  `gift_registry_id` uuid DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbcikfecfe0yt3eip0ocn4qn3f` (`person_id`),
  KEY `FKhtw86xyxht6aoi5m6oj6n1xdr` (`gift_registry_id`),
  CONSTRAINT `FKbcikfecfe0yt3eip0ocn4qn3f` FOREIGN KEY (`person_id`) REFERENCES `person` (`id`),
  CONSTRAINT `FKhtw86xyxht6aoi5m6oj6n1xdr` FOREIGN KEY (`gift_registry_id`) REFERENCES `gift_registry` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gift`
--

LOCK TABLES `gift` WRITE;
/*!40000 ALTER TABLE `gift` DISABLE KEYS */;
INSERT INTO `gift` VALUES ('d9e95efb-294e-4bf8-b463-95fd624c0280','Carcasa móvil',8.00,'POR_COMPRAR','','844a1f6e-4a02-46a1-ad47-5649bda3706c','b488131b-ce9b-4e20-b99d-a29bb8fbbf4f'),('4c2b2e68-241d-4284-9883-98c3a35780a7','Mesa',34.00,'PENDIENTE_DE_RECIBIR','www.muebles.com','a7b0d6bf-4acd-4da2-b96f-5e200f0e5960',NULL),('67d0e2f7-13f1-45ca-bf17-994005575e2e','PS5',400.00,'POR_COMPRAR','arwg','a7b0d6bf-4acd-4da2-b96f-5e200f0e5960',NULL),('78bfb9e2-7638-4380-a130-bb7c4eeefbd1','Teclado PC',45.00,'POR_COMPRAR','https://www.pccomponentes.com/?s_kwcid=AL!14405!3!289609551491!e!!g!!pc%20componentes&gad_source=1&gclid=Cj0KCQjwvb-zBhCmARIsAAfUI2vEC9fiE5jYZbEab4-RhaF4sThnLC7P97uu9ab7DcHHMpLR7v9JZ-AaAmjXEALw_wcB','a7b0d6bf-4acd-4da2-b96f-5e200f0e5960','30f4d799-10ab-4b22-895c-a5bdc4802783'),('217d245d-300e-4a94-b56f-e3d8ceb7540c','Silla roja',34.00,'POR_COMPRAR','','54177cfa-433d-4358-a147-884c0d2070b1','9d7b62a5-4e74-4d22-b479-edaee6bc806c'),('c6b4cba5-089b-43c7-b597-ff08d4e8e756','Vestido',40.00,'POR_COMPRAR','www.zalando.com','54177cfa-433d-4358-a147-884c0d2070b1','9d7b62a5-4e74-4d22-b479-edaee6bc806c');
/*!40000 ALTER TABLE `gift` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gift_registry`
--

DROP TABLE IF EXISTS `gift_registry`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `gift_registry` (
  `id` uuid NOT NULL,
  `name` varchar(255) NOT NULL,
  `state` enum('CERRADA','PENDIENTE') DEFAULT NULL,
  `total_price` decimal(19,2) DEFAULT NULL,
  `user_id` uuid DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKo5iu8ctmgna9l7rmyko5qkn5n` (`user_id`),
  CONSTRAINT `FKo5iu8ctmgna9l7rmyko5qkn5n` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gift_registry`
--

LOCK TABLES `gift_registry` WRITE;
/*!40000 ALTER TABLE `gift_registry` DISABLE KEYS */;
INSERT INTO `gift_registry` VALUES ('b488131b-ce9b-4e20-b99d-a29bb8fbbf4f','Reyes 2024','PENDIENTE',8.00,'c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('30f4d799-10ab-4b22-895c-a5bdc4802783','BBQ','PENDIENTE',45.00,'c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('9d7b62a5-4e74-4d22-b479-edaee6bc806c','Cumpleaños María','PENDIENTE',74.00,'c0677aa7-af86-4c97-9b24-6f8f70a03a02');
/*!40000 ALTER TABLE `gift_registry` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `person`
--

DROP TABLE IF EXISTS `person`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `person` (
  `id` uuid NOT NULL,
  `name` varchar(255) NOT NULL,
  `person_id` uuid DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKaxbylk4netgtufpiajise2o3k` (`person_id`),
  CONSTRAINT `FKaxbylk4netgtufpiajise2o3k` FOREIGN KEY (`person_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `person`
--

LOCK TABLES `person` WRITE;
/*!40000 ALTER TABLE `person` DISABLE KEYS */;
INSERT INTO `person` VALUES ('13fd7219-1c81-479d-8c86-4c2c59202790','Daniel','c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('844a1f6e-4a02-46a1-ad47-5649bda3706c','Jose','c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('a7b0d6bf-4acd-4da2-b96f-5e200f0e5960','Carlos','c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('bbf9ac52-2032-4e38-bed0-64d774947810','Daniel UNI','c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('256a3b41-bfc7-4764-a1c7-871a62cb3a0a','Rocio','c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('54177cfa-433d-4358-a147-884c0d2070b1','Paloma','c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('c1ac2e43-64e5-4c87-ab5f-8fd0497f9f83','Antonio','c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('9dd98d78-47cd-4502-a0de-97b747fa27ba','Marta','c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('f83ade56-a749-489a-a4b7-bd3a71db5f38','Pepe trabajo','c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('cef5d64c-42cb-487c-89de-de13f37c6e4c','Alvaro','c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('8fd0a1cd-404c-4276-9980-ee6931c567b6','Mario','c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('6f84e8a3-0119-40f3-a9be-f6d040d5ce94','Juanma','c0677aa7-af86-4c97-9b24-6f8f70a03a02'),('4f562e03-7356-4f9d-a94f-fd1bcced86ba','Peluquero','c0677aa7-af86-4c97-9b24-6f8f70a03a02');
/*!40000 ALTER TABLE `person` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` uuid NOT NULL,
  `email` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `enabled` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('c0677aa7-af86-4c97-9b24-6f8f70a03a02','samuel@gmail.com','Fernández Peinado','Samuel','$2a$10$27nikEdKMnj/k.TmExT0d.MHTLYYx360DRSt184s5Bnq.pSb7dfX6',NULL),('64777a72-ff32-4386-81d0-7b31d7dd139f','jorge@gmail.com','','jorge','$2a$10$g.aae4t7JiBCsd2QyZt0O.TH58i31hWWgdr28hgOP2xP09Z7lrWoG',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_roles` (
  `user_id` uuid NOT NULL,
  `roles` tinyint(4) DEFAULT NULL CHECK (`roles` between 0 and 1),
  KEY `FK55itppkw3i07do3h7qoclqd4k` (`user_id`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_roles`
--

LOCK TABLES `user_roles` WRITE;
/*!40000 ALTER TABLE `user_roles` DISABLE KEYS */;
INSERT INTO `user_roles` VALUES ('c0677aa7-af86-4c97-9b24-6f8f70a03a02',0),('64777a72-ff32-4386-81d0-7b31d7dd139f',0);
/*!40000 ALTER TABLE `user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `verification_token`
--

DROP TABLE IF EXISTS `verification_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `verification_token` (
  `id` bigint(20) NOT NULL,
  `expiry_date` date DEFAULT NULL,
  `token` varchar(255) DEFAULT NULL,
  `user_id` uuid NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_q6jibbenp7o9v6tq178xg88hg` (`user_id`),
  CONSTRAINT `FKrdn0mss276m9jdobfhhn2qogw` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `verification_token`
--

LOCK TABLES `verification_token` WRITE;
/*!40000 ALTER TABLE `verification_token` DISABLE KEYS */;
/*!40000 ALTER TABLE `verification_token` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-18 17:13:03
