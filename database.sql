-- Script to create the database and tables for the project.

CREATE DATABASE IF NOT EXISTS `openlibraryhub`;

USE `openlibraryhub`;

DROP TABLE IF EXISTS `books`;

CREATE TABLE IF NOT EXISTS `books` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `title` VARCHAR(255) NOT NULL,
    `author` VARCHAR(255) NOT NULL,
    `section` VARCHAR(255) NOT NULL,
    `pages` INT UNSIGNED NOT NULL,
    `year` INT UNSIGNED NOT NULL,
    `stock` INT UNSIGNED NOT NULL
);

DROP TABLE IF EXISTS `loans`;
DROP TABLE IF EXISTS `students`;
DROP TABLE IF EXISTS `classes`;

CREATE TABLE IF NOT EXISTS `classes` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS `students` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `name` VARCHAR(255) NOT NULL,
    `class_id` INT UNSIGNED NOT NULL,
    FOREIGN KEY (`class_id`) REFERENCES `classes`(`id`)
);

CREATE TABLE IF NOT EXISTS `loans` (
    `id` INT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    `student_id` INT UNSIGNED NOT NULL,
    `book_id` INT UNSIGNED NOT NULL,
    `loan_date` DATE NOT NULL,
    `return_date` DATE NOT NULL,
    FOREIGN KEY (`student_id`) REFERENCES `students`(`id`),
    FOREIGN KEY (`book_id`) REFERENCES `books`(`id`)
);
