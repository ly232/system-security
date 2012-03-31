-- MySQL dump 10.13  Distrib 5.5.15, for Win64 (x86)
--
-- Host: localhost    Database: entnetdb_v3
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
  `loc_id` varbinary(100) NOT NULL,
  `user_id` varbinary(100) NOT NULL,
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
INSERT INTO `currloc` VALUES ('������s����y��',' \0��J����L.^�'),('������s����y��','�U�h�	v@�5��T'),('�5��ݗ\04�vf�)','p�=\0��G�kۦ?�V'),('׻��a����H�/�2�\r','b(������cV��\'��');
/*!40000 ALTER TABLE `currloc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `did` varbinary(100) NOT NULL,
  `dname` varbinary(100) DEFAULT NULL,
  `dhead_uid` varbinary(100) DEFAULT NULL,
  PRIMARY KEY (`did`),
  KEY `dhead_uid` (`dhead_uid`),
  CONSTRAINT `` FOREIGN KEY (`dhead_uid`) REFERENCES `user` (`user_id`) ON DELETE SET NULL ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES ('������s����y��','�x^R\\d��v�� ','�U�h�	v@�5��T'),('�5��ݗ\04�vf�)','�Z�0V�$BB?K','b(������cV��\'��'),('׻��a����H�/�2�\r','���H�GoQw.�	\"�[f',' \0��J����L.^�');
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friend`
--

DROP TABLE IF EXISTS `friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `friend` (
  `user1` varbinary(100) NOT NULL,
  `user2` varbinary(100) NOT NULL,
  `message` varbinary(500) NOT NULL,
  `msg_id` varbinary(100) NOT NULL,
  PRIMARY KEY (`msg_id`,`user1`,`user2`),
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
  `loc_id` varbinary(100) NOT NULL,
  `loc_name` varbinary(500) DEFAULT NULL,
  PRIMARY KEY (`loc_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES ('������s����y��','>�O�O���L��ܔ'),('�5��ݗ\04�vf�)','��iuH̪�X������'),('׻��a����H�/�2�\r','.�-�l�5��L�^���');
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manage`
--

DROP TABLE IF EXISTS `manage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manage` (
  `manager_id` varbinary(100) NOT NULL,
  `worker_id` varbinary(100) NOT NULL,
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
INSERT INTO `manage` VALUES ('�U�h�	v@�5��T','p�=\0��G�kۦ?�V');
/*!40000 ALTER TABLE `manage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `postworkmessage`
--

DROP TABLE IF EXISTS `postworkmessage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `postworkmessage` (
  `msg_id` varbinary(100) NOT NULL,
  `did` varbinary(100) NOT NULL,
  `msg_content` varbinary(500) NOT NULL,
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
INSERT INTO `postworkmessage` VALUES ('������s����y��','������s����y��','%�G������������.<�Q֩\"Q��k`�d�');
/*!40000 ALTER TABLE `postworkmessage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project`
--

DROP TABLE IF EXISTS `project`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project` (
  `proj_id` varbinary(100) NOT NULL,
  `proj_name` varbinary(100) DEFAULT NULL,
  PRIMARY KEY (`proj_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project`
--

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;
INSERT INTO `project` VALUES ('������s����y��','`��ߧ#*�e\'W%�'),('�5��ݗ\04�vf�)','Z�v�}�x7J����\nC�'),('׻��a����H�/�2�\r','e������]3���\'ݙ\'');
/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `user_id` varbinary(100) NOT NULL,
  `user_pwd` varbinary(100) DEFAULT NULL,
  `contact_info` varbinary(500) DEFAULT NULL,
  `role_id` varbinary(100) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (' \0��J����L.^�','Ռ���S�N��m<�]','�7s�����yA\\ܪx�','׻��a����H�/�2�\r'),('b(������cV��\'��','Ռ���S�N��m<�]','D;�z0x�ѕ��U','�5��ݗ\04�vf�)'),('p�=\0��G�kۦ?�V','Ռ���S�N��m<�]','|]�pܬ谵�氱e0','�5��ݗ\04�vf�)'),('�U�h�	v@�5��T','Ռ���S�N��m<�]','�?|�}0����h53?�','������s����y��');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workat`
--

DROP TABLE IF EXISTS `workat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workat` (
  `userID_workat` varbinary(100) NOT NULL,
  `deptID_workat` varbinary(100) DEFAULT NULL,
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
INSERT INTO `workat` VALUES ('p�=\0��G�kۦ?�V','������s����y��'),('�U�h�	v@�5��T','������s����y��'),('b(������cV��\'��','�5��ݗ\04�vf�)'),(' \0��J����L.^�','׻��a����H�/�2�\r');
/*!40000 ALTER TABLE `workat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workon`
--

DROP TABLE IF EXISTS `workon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `workon` (
  `uid` varbinary(100) NOT NULL,
  `pid` varbinary(100) DEFAULT NULL,
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
INSERT INTO `workon` VALUES ('p�=\0��G�kۦ?�V','������s����y��'),('�U�h�	v@�5��T','������s����y��'),('b(������cV��\'��','�5��ݗ\04�vf�)'),(' \0��J����L.^�','׻��a����H�/�2�\r');
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

-- Dump completed on 2012-03-31  5:03:44
