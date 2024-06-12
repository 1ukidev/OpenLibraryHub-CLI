-- Script to create the database and tables for the project.

CREATE DATABASE IF NOT EXISTS `openlibraryhub`;

USE `openlibraryhub`;

DROP TABLE IF EXISTS `books`;
CREATE TABLE IF NOT EXISTS `books` (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    section VARCHAR(255) NOT NULL,
    pages INT UNSIGNED NOT NULL,
    year INT UNSIGNED NOT NULL,
    stock INT UNSIGNED NOT NULL
)

CREATE TABLE IF NOT EXISTS `classes` (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL
)

CREATE TABLE IF NOT EXISTS `students` (
    id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(255) NOT NULL,
    class_id INT UNSIGNED NOT NULL,
    FOREIGN KEY (class_id) REFERENCES classes(id)
)