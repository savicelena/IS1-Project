CREATE DATABASE  IF NOT EXISTS `opstipodaci1` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `opstipodaci1`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: opstipodaci1
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
  `IDMes` int NOT NULL,
  PRIMARY KEY (`IDKor`),
  KEY `FK_IDMes_Korisnik_idx` (`IDMes`),
  CONSTRAINT `FK_IDMes_Korisnik` FOREIGN KEY (`IDMes`) REFERENCES `mesto` (`IDMes`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'Joakim','joakim@gmail.com','2002','M',13),(2,'Charity','charity@gmail.com','1970','Z',19),(3,'Isidora','isidora@gmail.com','1996','Z',16),(4,'Svea','svea@gmail.com','1980','Z',9),(5,'Morten','morten@gmail.com','1966','M',11),(6,'Vojin','vojinetf@gmail.com','2002','M',2),(7,'Dorotea','dorotea@gmail.com','2005','Z',13),(8,'Andreas','andreas@gmail.com','1991','M',14),(9,'Ilija','ilija@gmail.com','1981','Z',2),(10,'Elias','elias@gmail.com','1999','M',7),(11,'Petar','petar@gmail.com','2002','M',2),(12,'Elena','elena5@gmail.com','2002','Z',1),(13,'Alan Al','alan55@gmail.com','1995','M',12),(14,'Alaz','alaz@yahoo.com','1988','M',17),(15,'Jan','jan@gmail.com','1999','M',30),(16,'Vaggelis','vaggelis@gmail.com','2004','M',20);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesto`
--

DROP TABLE IF EXISTS `mesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesto` (
  `IDMes` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`IDMes`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesto`
--

LOCK TABLES `mesto` WRITE;
/*!40000 ALTER TABLE `mesto` DISABLE KEYS */;
INSERT INTO `mesto` VALUES (1,'Krusevac'),(2,'Beograd'),(3,'Budimpesta'),(4,'Ljubljana'),(5,'Bern'),(6,'Berlin'),(7,'Prag'),(8,'Oslo'),(9,'Stokholm'),(10,'Helsinki'),(11,'Kopenhagen'),(12,'Pariz'),(13,'Madrid'),(14,'Lisabon'),(15,'Rim'),(16,'Moskva'),(17,'Vilnjus'),(18,'Riga'),(19,'London'),(20,'Atina'),(21,'Nis'),(22,'Zagreb'),(23,'Subotica'),(24,'Ahen'),(25,'Venecija'),(26,'Liverpul'),(27,'Barselona'),(28,'Pirot'),(29,'Gornji Milanovac'),(30,'Celje');
/*!40000 ALTER TABLE `mesto` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-02-12 13:45:08
