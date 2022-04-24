CREATE DATABASE  IF NOT EXISTS `veterinaria_elrefugio`;
USE `veterinaria_elrefugio`;

--
-- Table structure for table `mascotas`
--	PRIMARY KEY (`mascota_id`)
--	ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;
--	NOT NULL AUTO_INCREMENT
--  ENGINE=InnoDB DEFAULT CHARSET=latin1;
-----------------------------------------------------------------------
DROP TABLE IF EXISTS `Cliente`;
CREATE TABLE `Cliente` (
  `cliente_id` Int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(20),
  `Apellido_Paterno` varchar(20),
  `Apellido_Materno` varchar(20),
  `Correo` varchar(45),
  `Telefono` varchar(20),
  `Direccion` varchar(150),
  `estatus` char,
  PRIMARY KEY (`cliente_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


-----------------------------------------------------------------------
DROP TABLE IF EXISTS `mascota`;
CREATE TABLE `Mascota` (
  `mascota_id` int NOT NULL AUTO_INCREMENT,
  `cliente_id` int,
  `nombre` varchar(20),
  `especie` varchar(20),
  `raza` varchar(20),
  `color` varchar(20),
  `nacimiento` date,
  `sexo` boolean,
  `esterilizado` boolean,
  `estatus` char,
  PRIMARY KEY (`mascota_id`),
  FOREIGN KEY (`cliente_id`) REFERENCES `Cliente`(`cliente_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


-----------------------------------------------------------------------
CREATE TABLE `Consulta` (
  `consulta_id` int NOT NULL AUTO_INCREMENT,
  `mascota_id` int,
  `Enfermedad` varchar(20),
  `Fecha` date,
  PRIMARY KEY (`consulta_id`),
  FOREIGN KEY (`mascota_id`) REFERENCES `Mascota`(`mascota_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


-----------------------------------------------------------------------
CREATE TABLE `Vacuna` (
  `Id_vacuna` int NOT NULL AUTO_INCREMENT,
  `mascota_id` int,
  `fecha` date,
  `enfermedad` varchar(20),
  `siguiente` date,
  `marca` varchar(20),
  PRIMARY KEY (`Id_vacuna`),
  FOREIGN KEY (`mascota_id`) REFERENCES `Mascota`(`mascota_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


-----------------------------------------------------------------------
CREATE TABLE `Peso` (
  `Id_peso` int NOT NULL AUTO_INCREMENT,
  `mascota_id` int,
  `fecha` date,
  `peso` int,
  PRIMARY KEY (`Id_peso`),
  FOREIGN KEY (`mascota_id`) REFERENCES `Mascota`(`mascota_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


-----------------------------------------------------------------------
CREATE TABLE `Guarderia` (
  `guarderia_id` int NOT NULL AUTO_INCREMENT,
  `mascota_id` int,
  `fecha_llegada` date,
  `fecha_salida` date,
  `cubiculo` int,
  PRIMARY KEY (`guarderia_id`),
  FOREIGN KEY (`mascota_id`) REFERENCES `Mascota`(mascota_id)
)ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;


-----------------------------------------------------------------------
CREATE TABLE `Hospitalizacion` (
  `hospitalizacion_id` int NOT NULL AUTO_INCREMENT,
  `consulta_id` int,
  `Fecha_llegada` date,
  `Fecha_salida` date,
  `Cubiculo` int,
  PRIMARY KEY (`hospitalizacion_id`),
  FOREIGN KEY (`consulta_id`) REFERENCES `Consulta`(`consulta_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

