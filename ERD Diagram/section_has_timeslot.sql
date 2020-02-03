-- MySQL Script generated by MySQL Workbench
-- Tue Jun 25 10:54:36 2019
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema classroom
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `classroom` ;

-- -----------------------------------------------------
-- Schema classroom
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `classroom` DEFAULT CHARACTER SET utf8 ;
USE `classroom` ;

-- -----------------------------------------------------
-- Table `classroom`.`section_has_timeslot`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `classroom`.`section_has_timeslot` ;

CREATE TABLE IF NOT EXISTS `classroom`.`section_has_timeslot` (
  `section_sec_id` VARCHAR(8) NOT NULL,
  `section_semester` INT NOT NULL,
  `section_program` VARCHAR(10) NOT NULL,
  `time_slot_time_slot_id` INT NOT NULL,
  `time_slot_day` VARCHAR(12) NOT NULL,
  `course_course_id` VARCHAR(8) NOT NULL,
  PRIMARY KEY (`section_sec_id`, `section_semester`, `section_program`, `time_slot_time_slot_id`, `time_slot_day`),
  INDEX `fk_section_has_time_slot_time_slot1_idx` (`time_slot_time_slot_id` ASC, `time_slot_day` ASC),
  INDEX `fk_section_has_time_slot_section1_idx` (`section_sec_id` ASC, `section_semester` ASC, `section_program` ASC),
  INDEX `fk_section_has_timeslot_course1_idx` (`course_course_id` ASC),
  CONSTRAINT `fk_section_has_time_slot_section1`
    FOREIGN KEY (`section_sec_id` , `section_semester` , `section_program`)
    REFERENCES `classroom`.`section` (`sec_id` , `semester` , `program`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_section_has_time_slot_time_slot1`
    FOREIGN KEY (`time_slot_time_slot_id` , `time_slot_day`)
    REFERENCES `classroom`.`time_slot` (`time_slot_id` , `day`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_section_has_timeslot_course1`
    FOREIGN KEY (`course_course_id`)
    REFERENCES `classroom`.`course` (`course_id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;