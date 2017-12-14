CREATE DATABASE  IF NOT EXISTS `autosupply` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `autosupply`;
-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: autosupply
-- ------------------------------------------------------
-- Server version	5.7.19-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `account_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `contact_number` varchar(45) NOT NULL,
  `total_visits` int(11) NOT NULL,
  `debt` decimal(19,4) DEFAULT NULL,
  `debt_limit` decimal(19,4) DEFAULT NULL,
  PRIMARY KEY (`account_id`),
  UNIQUE KEY `account_id_UNIQUE` (`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'jarod','makati','12310312',6,10455.0000,20000.0000),(2,'tim','manila','09173352933',4,50000.0000,50000.0000),(3,'luigi','luigi','123',0,0.0000,1.0000),(4,'gab','las pinas','1234',0,0.0000,1.0000);
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `daily_income`
--

DROP TABLE IF EXISTS `daily_income`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `daily_income` (
  `daily_income_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `total_amount` decimal(19,4) NOT NULL,
  PRIMARY KEY (`daily_income_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `daily_income`
--

LOCK TABLES `daily_income` WRITE;
/*!40000 ALTER TABLE `daily_income` DISABLE KEYS */;
INSERT INTO `daily_income` VALUES (1,4,'2017-12-12',1540.0000);
/*!40000 ALTER TABLE `daily_income` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items`
--

DROP TABLE IF EXISTS `items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items` (
  `item_code` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `description` varchar(45) NOT NULL,
  `category` varchar(45) NOT NULL,
  `manufacturer` varchar(45) NOT NULL,
  `supplier_code` varchar(45) NOT NULL,
  `stock` int(11) NOT NULL,
  `date_purchase` date NOT NULL,
  `price_supplier` decimal(19,4) NOT NULL,
  `price_customer` decimal(19,4) NOT NULL,
  PRIMARY KEY (`item_code`),
  UNIQUE KEY `item_code_UNIQUE` (`item_code`),
  UNIQUE KEY `supplier_code_UNIQUE` (`supplier_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items`
--

LOCK TABLES `items` WRITE;
/*!40000 ALTER TABLE `items` DISABLE KEYS */;
INSERT INTO `items` VALUES ('0','white wheel','xdddd','Wheel','Toyota','123',74,'1998-11-10',20.0000,12.0000),('1','black tinted window','they hatin','Window','Honda','0',28,'2017-11-10',20.0000,25.0000);
/*!40000 ALTER TABLE `items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `items_log`
--

DROP TABLE IF EXISTS `items_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `items_log` (
  `sale_id` int(11) NOT NULL AUTO_INCREMENT,
  `item_code` varchar(45) NOT NULL,
  `type` varchar(45) NOT NULL,
  `transaction_id` int(11) NOT NULL,
  `quantity_sold` int(11) NOT NULL,
  `original_price` decimal(19,4) NOT NULL,
  `price_sold` decimal(19,4) NOT NULL,
  PRIMARY KEY (`sale_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `items_log`
--

LOCK TABLES `items_log` WRITE;
/*!40000 ALTER TABLE `items_log` DISABLE KEYS */;
INSERT INTO `items_log` VALUES (35,'0','sold',0,1,25.0000,20.0000),(36,'0','returned',-1,20,500.0000,500.0000),(37,'1','returned',-1,12,300.0000,300.0000),(38,'0','returned',-1,12,300.0000,300.0000),(39,'0','returned',-1,12,300.0000,300.0000),(40,'1','returned',-1,13,325.0000,325.0000),(41,'0','returned',-1,23,575.0000,575.0000),(42,'0','sold',1,1,25.0000,25.0000),(43,'0','sold',2,1,25.0000,25.0000),(44,'0','sold',3,1,25.0000,25.0000);
/*!40000 ALTER TABLE `items_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchase_orders`
--

DROP TABLE IF EXISTS `purchase_orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchase_orders` (
  `order_id` int(11) NOT NULL,
  `supplier_code` varchar(45) NOT NULL,
  `total_price` decimal(19,4) NOT NULL,
  `date_ordered` date NOT NULL,
  `is_pending` int(4) NOT NULL,
  `date_received` date DEFAULT NULL,
  PRIMARY KEY (`order_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchase_orders`
--

LOCK TABLES `purchase_orders` WRITE;
/*!40000 ALTER TABLE `purchase_orders` DISABLE KEYS */;
/*!40000 ALTER TABLE `purchase_orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service_log`
--

DROP TABLE IF EXISTS `service_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `service_log` (
  `service_log_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_id` int(11) NOT NULL,
  `worker_id` int(11) NOT NULL,
  `transaction_id` int(11) NOT NULL,
  PRIMARY KEY (`service_log_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service_log`
--

LOCK TABLES `service_log` WRITE;
/*!40000 ALTER TABLE `service_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `service_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `services` (
  `service_id` int(11) NOT NULL AUTO_INCREMENT,
  `service_name` varchar(45) NOT NULL,
  `price` decimal(19,4) NOT NULL,
  PRIMARY KEY (`service_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `services`
--

LOCK TABLES `services` WRITE;
/*!40000 ALTER TABLE `services` DISABLE KEYS */;
INSERT INTO `services` VALUES (1,'Air pumping',20.0000),(2,'xd',10.0000);
/*!40000 ALTER TABLE `services` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suppliers` (
  `supplier_code` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `contact_person` varchar(45) NOT NULL,
  `contact_number` varchar(45) NOT NULL,
  `tax_id` varchar(45) NOT NULL,
  PRIMARY KEY (`supplier_code`),
  UNIQUE KEY `tax_id_UNIQUE` (`tax_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES ('123','123','123','123','123'),('tim_pogi','timpogi','timothy','0912323123','xdddd');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transactions` (
  `transaction_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `transaction_type` varchar(45) NOT NULL,
  `is_loan` int(4) NOT NULL,
  `date_sold` date NOT NULL,
  `total_price` decimal(19,4) NOT NULL,
  PRIMARY KEY (`transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (0,4,'retail',1,'2017-12-12',20.0000),(1,4,'retail',0,'2017-12-13',25.0000),(2,4,'retail',0,'2017-12-13',25.0000),(3,4,'retail',0,'2017-12-13',25.0000),(4,4,'retail',1,'2017-12-14',50.0000),(5,4,'whole',0,'2017-12-14',20.0000),(6,4,'retail',0,'2016-11-10',25.0000),(7,4,'whole',1,'2017-12-15',100.0000),(8,4,'retail',0,'2017-12-10',25.0000),(9,4,'whole',1,'2017-11-10',50.0000),(10,4,'retail',0,'2017-07-10',25.0000),(11,4,'whole',1,'2017-04-01',69.0000),(12,4,'retail',0,'2016-12-24',50.0000);
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `user_level` varchar(45) NOT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Angelo','angelo','123456','2'),(4,'Anj','anj','1234','1');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workers`
--

DROP TABLE IF EXISTS `workers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workers` (
  `worker_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `salary` decimal(19,4) NOT NULL,
  PRIMARY KEY (`worker_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workers`
--

LOCK TABLES `workers` WRITE;
/*!40000 ALTER TABLE `workers` DISABLE KEYS */;
INSERT INTO `workers` VALUES (1,'jack',20.0000),(2,'col',40.0000);
/*!40000 ALTER TABLE `workers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-15  1:09:09
