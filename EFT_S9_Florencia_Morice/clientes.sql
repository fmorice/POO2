-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 13-10-2025 a las 00:04:43
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `sistema_computec`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

CREATE TABLE `clientes` (
  `id` int(11) NOT NULL,
  `rut` varchar(12) NOT NULL,
  `nombre_completo` varchar(100) NOT NULL,
  `direccion` varchar(200) NOT NULL,
  `comuna` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `telefono` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `clientes`
--

INSERT INTO `clientes` (`id`, `rut`, `nombre_completo`, `direccion`, `comuna`, `email`, `telefono`) VALUES
(1, '12.345.678-9', 'Juan Pérez González', 'Av. Libertador 1234', 'Santiago', 'juan.perez@email.com', '+56912345678'),
(2, '98.765.432-1', 'María González López', 'Calle Principal 567', 'Providencia', 'maria.gonzalez@email.com', '+56987654321'),
(3, '18.234.567-K', 'Carlos Ramírez Silva', 'Pasaje Los Olivos 89', 'Las Condes', 'carlos.ramirez@email.com', '+56923456789'),
(5, '11.111.111-1', 'Flore', 'lejos 1212', 'San miguel', 'flore@gmaill.com', '+56912121212'),
(7, '16941532-3', 'edi vedder', 'lejos 1212', 'san miguel', 'edi@correo.com', '+5699344533'),
(8, '18654234-7', 'edi veder', 'lejos1', 'macul', 'correoq@correo.com', '+5692346545'),
(9, '83.458.632-0', 'flo juanita', 'muy lejos 5456', 'macul', 'jana@email.com', '+5699874632'),
(10, '75.205.021-K', 'juanita perez', 'muy lejos 567', 'macul', 'juani@email.com', '+5693457645');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `rut` (`rut`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `clientes`
--
ALTER TABLE `clientes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
