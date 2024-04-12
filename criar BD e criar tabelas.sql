CREATE DATABASE tca_bd;
CREATE TABLE `tb_produtos` (
  `codigoP` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(100) NOT NULL,
  `Preco` double NOT NULL,
  PRIMARY KEY (`codigoP`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `tb_compra` (
  `data_hora` varchar(60) NOT NULL,
  `valor_total_produtos` double NOT NULL,
  `taxa_entrega` double NOT NULL,
  `valor_total_compra` double NOT NULL,
  `forma_pagamento` varchar(50) NOT NULL,
  PRIMARY KEY (`data_hora`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci

CREATE TABLE `tb_itens_compra` (
  `codigo_produto` int NOT NULL,
  `nome_produto` varchar(100) NOT NULL,
  `quantidade` int NOT NULL,
  `preco_unitario` double NOT NULL,
  `subtotal` double NOT NULL,
  PRIMARY KEY (`codigo_produto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci