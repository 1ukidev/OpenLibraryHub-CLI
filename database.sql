-- Script para criar o banco de dados e as tabelas necessárias.

CREATE DATABASE IF NOT EXISTS `openlibraryhub`;

USE `openlibraryhub`;

DROP TABLE IF EXISTS `books`;

CREATE TABLE IF NOT EXISTS `books` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `author` VARCHAR(255) NOT NULL,
    `section` VARCHAR(255) NOT NULL,
    `pages` INT NOT NULL,
    `year` INT NOT NULL,
    `stock` INT NOT NULL
);

DROP TABLE IF EXISTS `loans`;
DROP TABLE IF EXISTS `students`;
DROP TABLE IF EXISTS `classes`;

CREATE TABLE IF NOT EXISTS `classes` (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `students` (
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `class_id` INT NOT NULL,
    FOREIGN KEY (`class_id`) REFERENCES `classes`(`id`)
);

CREATE TABLE IF NOT EXISTS `loans` (
    `id` BIGINT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `student_id` INT NOT NULL,
    `book_id` INT NOT NULL,
    `loan_date` DATE NOT NULL,
    `return_date` DATE NOT NULL,
    FOREIGN KEY (`student_id`) REFERENCES `students`(`id`),
    FOREIGN KEY (`book_id`) REFERENCES `books`(`id`)
);
