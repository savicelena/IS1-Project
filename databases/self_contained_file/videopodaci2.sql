CREATE DATABASE  IF NOT EXISTS `videopodaci2` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `videopodaci2`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: videopodaci2
-- ------------------------------------------------------
-- Server version	8.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `IDKat` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`IDKat`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES (1,'Naucna fantastika'),(2,'Triler'),(3,'Drama'),(4,'Komedija'),(5,'Akcija'),(6,'Fantazija'),(7,'Dokumentarni'),(8,'Misterija'),(9,'Krimi'),(10,'Romantika'),(11,'Istorija sa elementima fikcije'),(12,'Istorija'),(13,'Animirani');
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `IDKor` int NOT NULL AUTO_INCREMENT,
  `Ime` varchar(45) NOT NULL,
  `Email` varchar(45) DEFAULT NULL,
  `Godiste` varchar(45) DEFAULT NULL,
  `Pol` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`IDKor`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'Joakim','joakim@gmail.com','2002','M'),(2,'Charity','charity@gmail.com','1970','Z'),(3,'Isidora','isidora@gmail.com','1996','Z'),(4,'Svea','svea@gmail.com','1980','Z'),(5,'Morten','morten@gmail.com','1966','M'),(6,'Vojin','vojinetf@gmail.com','2002','M'),(7,'Dorotea','dorotea@gmail.com','2005','Z'),(8,'Andreas','andreas@gmail.com','1991','M'),(9,'Ilija','ilija@gmail.com','1981','Z'),(10,'Elias','elias@gmail.com','1999','M'),(11,'Petar','petar@gmail.com','2002','M'),(12,'Elena','elena5@gmail.com','2002','Z'),(13,'Alan Al','alan55@gmail.com','1995','M'),(14,'Alaz','alaz@yahoo.com','1988','M'),(15,'Jan','jan@gmail.com','1999','M'),(16,'Vaggelis','vaggelis@gmail.com','2004','M');
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video`
--

DROP TABLE IF EXISTS `video`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video` (
  `IDVid` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  `Trajanje` int NOT NULL,
  `DatumVremePost` datetime NOT NULL,
  `IDKor` int NOT NULL,
  PRIMARY KEY (`IDVid`),
  KEY `FK_IDKor_Video_idx` (`IDKor`),
  CONSTRAINT `FK_IDKor_Video` FOREIGN KEY (`IDKor`) REFERENCES `korisnik` (`IDKor`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video`
--

LOCK TABLES `video` WRITE;
/*!40000 ALTER TABLE `video` DISABLE KEYS */;
INSERT INTO `video` VALUES (1,'Gospodar Prstenova Povratak Kralja',183,'2022-10-25 10:34:15',1),(2,'Avatar',175,'2023-12-27 19:15:00',10),(3,'Parazit',155,'2023-05-12 20:00:00',4),(4,'Prozor u dvoriste',95,'1995-10-22 15:44:19',2),(5,'Kum',191,'1999-03-12 13:15:00',8),(6,'Matriks',115,'2015-01-15 03:03:03',6),(23,'Psiho',93,'2024-02-12 13:40:24',1);
/*!40000 ALTER TABLE `video` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `video_pripada`
--

DROP TABLE IF EXISTS `video_pripada`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `video_pripada` (
  `IDVid` int NOT NULL,
  `IDKat` int NOT NULL,
  PRIMARY KEY (`IDVid`,`IDKat`),
  KEY `FK_IDKat_Pripada_idx` (`IDKat`),
  CONSTRAINT `FK_IDKat_Pripada` FOREIGN KEY (`IDKat`) REFERENCES `kategorija` (`IDKat`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_IDVid_Pripada` FOREIGN KEY (`IDVid`) REFERENCES `video` (`IDVid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `video_pripada`
--

LOCK TABLES `video_pripada` WRITE;
/*!40000 ALTER TABLE `video_pripada` DISABLE KEYS */;
INSERT INTO `video_pripada` VALUES (2,1),(6,1),(3,2),(4,2),(4,3),(5,3),(3,4),(1,6),(5,9);
/*!40000 ALTER TABLE `video_pripada` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-12 13:45:48
