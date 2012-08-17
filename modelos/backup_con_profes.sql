CREATE DATABASE  IF NOT EXISTS `sociul` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `sociul`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: sociul
-- ------------------------------------------------------
-- Server version	5.5.27

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
-- Table structure for table `play_evolutions`
--

DROP TABLE IF EXISTS `play_evolutions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `play_evolutions` (
  `id` int(11) NOT NULL,
  `hash` varchar(255) NOT NULL,
  `applied_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `apply_script` text,
  `revert_script` text,
  `state` varchar(255) DEFAULT NULL,
  `last_problem` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `play_evolutions`
--

LOCK TABLES `play_evolutions` WRITE;
/*!40000 ALTER TABLE `play_evolutions` DISABLE KEYS */;
INSERT INTO `play_evolutions` VALUES (1,'9be80d34be2dbb787b059f7016a5f74a8d98ec08','2012-08-17 21:39:54','create table Carrera (\nid                        bigint auto_increment not null,\nnombre                    varchar(255),\nfecha_registro            datetime,\nfacultad_id               bigint,\nuniversidad_id            bigint,\nconstraint pk_Carrera primary key (id))\n;\n\ncreate table Carrera_Curso (\ncarrera_id                bigint,\ncurso_id                  bigint)\n;\n\ncreate table Curso (\nid                        bigint auto_increment not null,\ncodigo                    varchar(255),\nnombre                    varchar(255),\nconstraint uq_Curso_codigo unique (codigo),\nconstraint pk_Curso primary key (id))\n;\n\ncreate table Curso_Usuario (\nseccion                   integer,\nusuario_id                bigint,\ncurso_id                  bigint)\n;\n\ncreate table Enlace (\nid                        bigint auto_increment not null,\nurl                       varchar(255),\ndescripcion               varchar(255),\nimagen                    varchar(255),\ntitulo                    varchar(255),\nemisor_id                 bigint,\ncurso_id                  bigint,\nconstraint pk_Enlace primary key (id))\n;\n\ncreate table Facultad (\nid                        bigint auto_increment not null,\nnombre                    varchar(255),\nfecha_registro            datetime,\nuniversidad_id            bigint,\nconstraint pk_Facultad primary key (id))\n;\n\ncreate table Mensaje (\nid                        bigint auto_increment not null,\ntitulo                    varchar(255),\ncontenido                 varchar(255),\ntipo                      integer,\nfecha                     datetime,\nmensaje_id                bigint,\nemisor_id                 bigint,\ncurso_id                  bigint,\nfacultad_id               bigint,\nuniversidad_id            bigint,\ncarrera_id                bigint,\nconstraint pk_Mensaje primary key (id))\n;\n\ncreate table Mensaje_Receptor (\nmensaje_id                bigint,\nreceptor_id               bigint)\n;\n\ncreate table Universidad (\nid                        bigint auto_increment not null,\nnombre                    varchar(255),\nfecha_registro            datetime,\nconstraint pk_Universidad primary key (id))\n;\n\ncreate table Usuario (\nid                        bigint auto_increment not null,\nusername                  varchar(255) not null,\npassword                  varchar(255) not null,\nnombres                   varchar(255),\napellidos                 varchar(255),\nemail                     varchar(255),\nprivilegio                Integer default \'0\' not null,\nrol                       Integer default \'0\' not null,\nuniversidad_id            bigint,\ncarrera_id                bigint,\nfacultad_id               bigint,\nconstraint uq_Usuario_username unique (username),\nconstraint pk_Usuario primary key (id))\n;\n\nalter table Carrera add constraint fk_Carrera_facultad_1 foreign key (facultad_id) references Facultad (id) on delete restrict on update restrict;\ncreate index ix_Carrera_facultad_1 on Carrera (facultad_id);\nalter table Carrera add constraint fk_Carrera_universidad_2 foreign key (universidad_id) references Universidad (id) on delete restrict on update restrict;\ncreate index ix_Carrera_universidad_2 on Carrera (universidad_id);\nalter table Carrera_Curso add constraint fk_Carrera_Curso_carrera_3 foreign key (carrera_id) references Carrera (id) on delete restrict on update restrict;\ncreate index ix_Carrera_Curso_carrera_3 on Carrera_Curso (carrera_id);\nalter table Carrera_Curso add constraint fk_Carrera_Curso_curso_4 foreign key (curso_id) references Curso (id) on delete restrict on update restrict;\ncreate index ix_Carrera_Curso_curso_4 on Carrera_Curso (curso_id);\nalter table Curso_Usuario add constraint fk_Curso_Usuario_usuario_5 foreign key (usuario_id) references Usuario (id) on delete restrict on update restrict;\ncreate index ix_Curso_Usuario_usuario_5 on Curso_Usuario (usuario_id);\nalter table Curso_Usuario add constraint fk_Curso_Usuario_curso_6 foreign key (curso_id) references Curso (id) on delete restrict on update restrict;\ncreate index ix_Curso_Usuario_curso_6 on Curso_Usuario (curso_id);\nalter table Enlace add constraint fk_Enlace_emisor_7 foreign key (emisor_id) references Usuario (id) on delete restrict on update restrict;\ncreate index ix_Enlace_emisor_7 on Enlace (emisor_id);\nalter table Enlace add constraint fk_Enlace_curso_8 foreign key (curso_id) references Curso (id) on delete restrict on update restrict;\ncreate index ix_Enlace_curso_8 on Enlace (curso_id);\nalter table Facultad add constraint fk_Facultad_universidad_9 foreign key (universidad_id) references Universidad (id) on delete restrict on update restrict;\ncreate index ix_Facultad_universidad_9 on Facultad (universidad_id);\nalter table Mensaje add constraint fk_Mensaje_mensaje_10 foreign key (mensaje_id) references Mensaje (id) on delete restrict on update restrict;\ncreate index ix_Mensaje_mensaje_10 on Mensaje (mensaje_id);\nalter table Mensaje add constraint fk_Mensaje_emisor_11 foreign key (emisor_id) references Usuario (id) on delete restrict on update restrict;\ncreate index ix_Mensaje_emisor_11 on Mensaje (emisor_id);\nalter table Mensaje add constraint fk_Mensaje_curso_12 foreign key (curso_id) references Curso (id) on delete restrict on update restrict;\ncreate index ix_Mensaje_curso_12 on Mensaje (curso_id);\nalter table Mensaje add constraint fk_Mensaje_facultad_13 foreign key (facultad_id) references Facultad (id) on delete restrict on update restrict;\ncreate index ix_Mensaje_facultad_13 on Mensaje (facultad_id);\nalter table Mensaje add constraint fk_Mensaje_universidad_14 foreign key (universidad_id) references Universidad (id) on delete restrict on update restrict;\ncreate index ix_Mensaje_universidad_14 on Mensaje (universidad_id);\nalter table Mensaje add constraint fk_Mensaje_carrera_15 foreign key (carrera_id) references Carrera (id) on delete restrict on update restrict;\ncreate index ix_Mensaje_carrera_15 on Mensaje (carrera_id);\nalter table Mensaje_Receptor add constraint fk_Mensaje_Receptor_mensaje_16 foreign key (mensaje_id) references Mensaje (id) on delete restrict on update restrict;\ncreate index ix_Mensaje_Receptor_mensaje_16 on Mensaje_Receptor (mensaje_id);\nalter table Mensaje_Receptor add constraint fk_Mensaje_Receptor_receptor_17 foreign key (receptor_id) references Usuario (id) on delete restrict on update restrict;\ncreate index ix_Mensaje_Receptor_receptor_17 on Mensaje_Receptor (receptor_id);\nalter table Usuario add constraint fk_Usuario_universidad_18 foreign key (universidad_id) references Universidad (id) on delete restrict on update restrict;\ncreate index ix_Usuario_universidad_18 on Usuario (universidad_id);\nalter table Usuario add constraint fk_Usuario_carrera_19 foreign key (carrera_id) references Carrera (id) on delete restrict on update restrict;\ncreate index ix_Usuario_carrera_19 on Usuario (carrera_id);\nalter table Usuario add constraint fk_Usuario_facultad_20 foreign key (facultad_id) references Facultad (id) on delete restrict on update restrict;\ncreate index ix_Usuario_facultad_20 on Usuario (facultad_id);','SET FOREIGN_KEY_CHECKS=0;\n\ndrop table Carrera;\n\ndrop table Carrera_Curso;\n\ndrop table Curso;\n\ndrop table Curso_Usuario;\n\ndrop table Enlace;\n\ndrop table Facultad;\n\ndrop table Mensaje;\n\ndrop table Mensaje_Receptor;\n\ndrop table Universidad;\n\ndrop table Usuario;\n\nSET FOREIGN_KEY_CHECKS=1;','applied','');
/*!40000 ALTER TABLE `play_evolutions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso_usuario`
--

DROP TABLE IF EXISTS `curso_usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curso_usuario` (
  `seccion` int(11) DEFAULT NULL,
  `usuario_id` bigint(20) DEFAULT NULL,
  `curso_id` bigint(20) DEFAULT NULL,
  KEY `ix_Curso_Usuario_usuario_5` (`usuario_id`),
  KEY `ix_Curso_Usuario_curso_6` (`curso_id`),
  CONSTRAINT `fk_Curso_Usuario_curso_6` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Curso_Usuario_usuario_5` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso_usuario`
--

LOCK TABLES `curso_usuario` WRITE;
/*!40000 ALTER TABLE `curso_usuario` DISABLE KEYS */;
INSERT INTO `curso_usuario` VALUES (804,3,1),(804,2,1),(702,5,2),(702,2,2),(702,6,3),(702,2,3),(802,4,4),(802,2,4),(802,7,5),(802,2,5);
/*!40000 ALTER TABLE `curso_usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `enlace`
--

DROP TABLE IF EXISTS `enlace`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `enlace` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  `emisor_id` bigint(20) DEFAULT NULL,
  `curso_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_Enlace_emisor_7` (`emisor_id`),
  KEY `ix_Enlace_curso_8` (`curso_id`),
  CONSTRAINT `fk_Enlace_curso_8` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Enlace_emisor_7` FOREIGN KEY (`emisor_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `enlace`
--

LOCK TABLES `enlace` WRITE;
/*!40000 ALTER TABLE `enlace` DISABLE KEYS */;
/*!40000 ALTER TABLE `enlace` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `usuario` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `nombres` varchar(255) DEFAULT NULL,
  `apellidos` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `privilegio` int(11) NOT NULL DEFAULT '0',
  `rol` int(11) NOT NULL DEFAULT '0',
  `universidad_id` bigint(20) DEFAULT NULL,
  `carrera_id` bigint(20) DEFAULT NULL,
  `facultad_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_Usuario_username` (`username`),
  KEY `ix_Usuario_universidad_18` (`universidad_id`),
  KEY `ix_Usuario_carrera_19` (`carrera_id`),
  KEY `ix_Usuario_facultad_20` (`facultad_id`),
  CONSTRAINT `fk_Usuario_facultad_20` FOREIGN KEY (`facultad_id`) REFERENCES `facultad` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_carrera_19` FOREIGN KEY (`carrera_id`) REFERENCES `carrera` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Usuario_universidad_18` FOREIGN KEY (`universidad_id`) REFERENCES `universidad` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'admin','123','Administrador','Ulima',NULL,3,3,NULL,NULL,NULL),(2,'20082219','DFYAPL','Daniel Mauricio','Flores Sánchez','20082219@aloe.ulima.edu.pe',0,0,1,NULL,1),(3,'rcheca','rcheca','Rocio del Pilar','Checa Fernandez','rcheca@ulima.edu.pe',1,1,1,NULL,1),(4,'jmiranda','jmiranda','Jorge Victor','Miranda Pacheco','jmiranda@ulima.edu.pe',1,1,1,NULL,1),(5,'gromero','gromero','George Edwin','Romero Velazco','gromero@ulima.edu.pe',1,1,1,NULL,1),(6,'cmtorres','cmtorres','Carlos Martín','Torres Paredes','cmtorres@ulima.edu.pe',1,1,1,NULL,1),(7,'jfespino','jfespino','José Francisco','Espinoza Matos','jfespino@ulima.edu.pe',1,1,1,NULL,1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `curso`
--

DROP TABLE IF EXISTS `curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `curso` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `codigo` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_Curso_codigo` (`codigo`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `curso`
--

LOCK TABLES `curso` WRITE;
/*!40000 ALTER TABLE `curso` DISABLE KEYS */;
INSERT INTO `curso` VALUES (1,'1327','INGENIERIA DE SOFTWARE II'),(2,'1424','REDES INALÁMBRICAS'),(3,'1425','SEGURIDAD DE REDES'),(4,'1516','PRODUCTOS DE SOFTWARE PARA LA GESTION'),(5,'2697','MARKETING');
/*!40000 ALTER TABLE `curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensaje_receptor`
--

DROP TABLE IF EXISTS `mensaje_receptor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensaje_receptor` (
  `mensaje_id` bigint(20) DEFAULT NULL,
  `receptor_id` bigint(20) DEFAULT NULL,
  KEY `ix_Mensaje_Receptor_mensaje_16` (`mensaje_id`),
  KEY `ix_Mensaje_Receptor_receptor_17` (`receptor_id`),
  CONSTRAINT `fk_Mensaje_Receptor_receptor_17` FOREIGN KEY (`receptor_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Mensaje_Receptor_mensaje_16` FOREIGN KEY (`mensaje_id`) REFERENCES `mensaje` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensaje_receptor`
--

LOCK TABLES `mensaje_receptor` WRITE;
/*!40000 ALTER TABLE `mensaje_receptor` DISABLE KEYS */;
INSERT INTO `mensaje_receptor` VALUES (NULL,3),(2,3);
/*!40000 ALTER TABLE `mensaje_receptor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `facultad`
--

DROP TABLE IF EXISTS `facultad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `facultad` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `universidad_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_Facultad_universidad_9` (`universidad_id`),
  CONSTRAINT `fk_Facultad_universidad_9` FOREIGN KEY (`universidad_id`) REFERENCES `universidad` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `facultad`
--

LOCK TABLES `facultad` WRITE;
/*!40000 ALTER TABLE `facultad` DISABLE KEYS */;
INSERT INTO `facultad` VALUES (1,'FACULTAD DE INGENIERÍA DE SISTEMAS',NULL,1);
/*!40000 ALTER TABLE `facultad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrera`
--

DROP TABLE IF EXISTS `carrera`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carrera` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  `facultad_id` bigint(20) DEFAULT NULL,
  `universidad_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_Carrera_facultad_1` (`facultad_id`),
  KEY `ix_Carrera_universidad_2` (`universidad_id`),
  CONSTRAINT `fk_Carrera_universidad_2` FOREIGN KEY (`universidad_id`) REFERENCES `universidad` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Carrera_facultad_1` FOREIGN KEY (`facultad_id`) REFERENCES `facultad` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrera`
--

LOCK TABLES `carrera` WRITE;
/*!40000 ALTER TABLE `carrera` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrera` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `carrera_curso`
--

DROP TABLE IF EXISTS `carrera_curso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `carrera_curso` (
  `carrera_id` bigint(20) DEFAULT NULL,
  `curso_id` bigint(20) DEFAULT NULL,
  KEY `ix_Carrera_Curso_carrera_3` (`carrera_id`),
  KEY `ix_Carrera_Curso_curso_4` (`curso_id`),
  CONSTRAINT `fk_Carrera_Curso_curso_4` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Carrera_Curso_carrera_3` FOREIGN KEY (`carrera_id`) REFERENCES `carrera` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `carrera_curso`
--

LOCK TABLES `carrera_curso` WRITE;
/*!40000 ALTER TABLE `carrera_curso` DISABLE KEYS */;
/*!40000 ALTER TABLE `carrera_curso` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `universidad`
--

DROP TABLE IF EXISTS `universidad`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `universidad` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(255) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `universidad`
--

LOCK TABLES `universidad` WRITE;
/*!40000 ALTER TABLE `universidad` DISABLE KEYS */;
INSERT INTO `universidad` VALUES (1,'UNIVERSIDAD DE LIMA',NULL);
/*!40000 ALTER TABLE `universidad` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mensaje`
--

DROP TABLE IF EXISTS `mensaje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mensaje` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(255) DEFAULT NULL,
  `contenido` varchar(255) DEFAULT NULL,
  `tipo` int(11) DEFAULT NULL,
  `fecha` datetime DEFAULT NULL,
  `mensaje_id` bigint(20) DEFAULT NULL,
  `emisor_id` bigint(20) DEFAULT NULL,
  `curso_id` bigint(20) DEFAULT NULL,
  `facultad_id` bigint(20) DEFAULT NULL,
  `universidad_id` bigint(20) DEFAULT NULL,
  `carrera_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `ix_Mensaje_mensaje_10` (`mensaje_id`),
  KEY `ix_Mensaje_emisor_11` (`emisor_id`),
  KEY `ix_Mensaje_curso_12` (`curso_id`),
  KEY `ix_Mensaje_facultad_13` (`facultad_id`),
  KEY `ix_Mensaje_universidad_14` (`universidad_id`),
  KEY `ix_Mensaje_carrera_15` (`carrera_id`),
  CONSTRAINT `fk_Mensaje_carrera_15` FOREIGN KEY (`carrera_id`) REFERENCES `carrera` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Mensaje_curso_12` FOREIGN KEY (`curso_id`) REFERENCES `curso` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Mensaje_emisor_11` FOREIGN KEY (`emisor_id`) REFERENCES `usuario` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Mensaje_facultad_13` FOREIGN KEY (`facultad_id`) REFERENCES `facultad` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Mensaje_mensaje_10` FOREIGN KEY (`mensaje_id`) REFERENCES `mensaje` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_Mensaje_universidad_14` FOREIGN KEY (`universidad_id`) REFERENCES `universidad` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mensaje`
--

LOCK TABLES `mensaje` WRITE;
/*!40000 ALTER TABLE `mensaje` DISABLE KEYS */;
INSERT INTO `mensaje` VALUES (1,'\"Sobre el desarrollo del proyecto\"','\"\"',NULL,NULL,NULL,2,NULL,NULL,NULL,NULL),(2,'\"Sobre el desarrollo del proyecto\"','\"Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy ...\"',NULL,NULL,NULL,2,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `mensaje` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-08-17 17:45:36
