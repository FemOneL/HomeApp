CREATE SCHEMA `homeapp` ;
CREATE TABLE `homeapp`.`appliances` (
  `idappliances` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `model` varchar(45) NOT NULL,
  `company` varchar(45) NOT NULL,
  `power` int NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`idappliances`)
) 