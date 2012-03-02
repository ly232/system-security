-- MySQL dump 10.13  Distrib 5.5.15, for Win64 (x86)
--
-- Host: localhost    Database: entnetdb_v2
-- ------------------------------------------------------
-- Server version	5.5.15

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
-- Table structure for table `currloc`
--

DROP TABLE IF EXISTS `currloc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `currloc` (
  `loc_id` int(11) NOT NULL,
  `user_id` varchar(20) NOT NULL,
  PRIMARY KEY (`user_id`),
  KEY `user_id` (`user_id`),
  KEY `loc_id` (`loc_id`),
  CONSTRAINT `loc_id` FOREIGN KEY (`loc_id`) REFERENCES `location` (`loc_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `currloc`
--

LOCK TABLES `currloc` WRITE;
/*!40000 ALTER TABLE `currloc` DISABLE KEYS */;
INSERT INTO `currloc` VALUES (1,'aa111'),(1,'bb222'),(1,'cc333'),(1,'cw597'),(1,'dd444'),(1,'ly232'),(2,'ee555'),(2,'sy482'),(3,'ff666'),(3,'tl529');
/*!40000 ALTER TABLE `currloc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `did` int(11) NOT NULL AUTO_INCREMENT,
  `dname` varchar(20) DEFAULT NULL,
  `dhead_uid` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`did`),
  KEY `dhead_uid` (`dhead_uid`),
  CONSTRAINT `` FOREIGN KEY (`dhead_uid`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'Boss','tl529'),(2,'IT','ly232'),(3,'HR','cw597'),(4,'Sales','sy482'),(5,'Root','root');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friend` (
  `user1` varchar(20) NOT NULL,
  `user2` varchar(20) NOT NULL,
  `message` varchar(100) NOT NULL,
  `msg_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user1`,`user2`),
  KEY `user1` (`user1`),
  KEY `user2` (`user2`),
  CONSTRAINT `user1` FOREIGN KEY (`user1`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user2` FOREIGN KEY (`user2`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friend`
--

LOCK TABLES `friend` WRITE;
/*!40000 ALTER TABLE `friend` DISABLE KEYS */;
/*!40000 ALTER TABLE `friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `loc_id` int(11) NOT NULL AUTO_INCREMENT,
  `loc_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`loc_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'New York'),(2,'Boston'),(3,'Los Angles');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manage`
--

DROP TABLE IF EXISTS `manage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manage` (
  `manager_id` varchar(20) NOT NULL,
  `worker_id` varchar(20) NOT NULL,
  PRIMARY KEY (`worker_id`),
  KEY `manager_id` (`manager_id`),
  KEY `worker_id` (`worker_id`),
  CONSTRAINT `manager_id` FOREIGN KEY (`manager_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `worker_id` FOREIGN KEY (`worker_id`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manage`
--

LOCK TABLES `manage` WRITE;
/*!40000 ALTER TABLE `manage` DISABLE KEYS */;
INSERT INTO `manage` VALUES ('cw597','cc333'),('cw597','dd444'),('ly232','aa111'),('ly232','bb222'),('sy482','ee555'),('sy482','ff666'),('tl529','cw597'),('tl529','ly232'),('tl529','sy482');
/*!40000 ALTER TABLE `manage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postworkmessage`
--

DROP TABLE IF EXISTS `postworkmessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `postworkmessage` (
  `msg_id` int(11) NOT NULL,
  `did` int(11) NOT NULL,
  `msg_content` varchar(100) NOT NULL,
  PRIMARY KEY (`msg_id`,`did`),
  KEY `did` (`did`),
  CONSTRAINT `did` FOREIGN KEY (`did`) REFERENCES `department` (`did`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `postworkmessage`
--

LOCK TABLES `postworkmessage` WRITE;
/*!40000 ALTER TABLE `postworkmessage` DISABLE KEYS */;
INSERT INTO `postworkmessage` VALUES (1,1,'Welcome to TCLS'),(2,1,'TCLS aims IPO next year'),(3,2,'NEW PROJECT: cloud computing'),(4,2,'NEW PROJECT: network infrastructure'),(5,3,'Starting intern hire this week!'),(6,3,'Targets 20 new full time hires this year'),(7,4,'Products shipping to Florida this week'),(8,4,'New contract signed with China');
/*!40000 ALTER TABLE `postworkmessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `proj_id` int(11) NOT NULL AUTO_INCREMENT,
  `proj_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`proj_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES (1,'cloud computing'),(2,'network infrastructure'),(3,'intern hire'),(4,'fulltime hire'),(5,'domestic sale'),(6,'international sale'),(7,'N/A');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` varchar(20) NOT NULL,
  `user_pwd` varchar(45) DEFAULT NULL,
  `contact_info` varchar(100) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('aa111','123','2127876462',3),('bb222','123','9178782940',3),('cc333','123','7189896264',3),('cw597','123','6461827462',2),('dd444','123','8982726462',3),('ee555','123','1726462721',3),('ff666','123','8981234214',3),('ly232','123','6467839182',2),('root','root','root',0),('sy482','123','2128482819',2),('tl529','123','9177274929',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workat`
--

DROP TABLE IF EXISTS `workat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workat` (
  `userID_workat` varchar(20) NOT NULL,
  `deptID_workat` int(11) DEFAULT NULL,
  PRIMARY KEY (`userID_workat`),
  KEY `userID_workat` (`userID_workat`),
  KEY `deptID_workat` (`deptID_workat`),
  CONSTRAINT `deptID_workat` FOREIGN KEY (`deptID_workat`) REFERENCES `department` (`did`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `userID_workat` FOREIGN KEY (`userID_workat`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workat`
--

LOCK TABLES `workat` WRITE;
/*!40000 ALTER TABLE `workat` DISABLE KEYS */;
INSERT INTO `workat` VALUES ('tl529',1),('aa111',2),('bb222',2),('ly232',2),('cc333',3),('cw597',3),('dd444',3),('ee555',4),('ff666',4),('sy482',4);
/*!40000 ALTER TABLE `workat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workon`
--

DROP TABLE IF EXISTS `workon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workon` (
  `uid` varchar(20) NOT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  KEY `uid` (`uid`),
  KEY `pid` (`pid`),
  CONSTRAINT `pid` FOREIGN KEY (`pid`) REFERENCES `project` (`proj_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `uid` FOREIGN KEY (`uid`) REFERENCES `user` (`user_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workon`
--

LOCK TABLES `workon` WRITE;
/*!40000 ALTER TABLE `workon` DISABLE KEYS */;
INSERT INTO `workon` VALUES ('aa111',1),('ly232',1),('bb222',2),('cc333',3),('cw597',3),('dd444',4),('ee555',5),('sy482',5),('ff666',6),('tl529',7);
/*!40000 ALTER TABLE `workon` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-03-02 18:25:15
