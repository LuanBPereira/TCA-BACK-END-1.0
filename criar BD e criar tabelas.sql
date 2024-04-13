CREATE DATABASE tca_bd;
CREATE TABLE `tb_produtos` (
  `codigoP` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(100) NOT NULL,
  `Preco` double NOT NULL,
  PRIMARY KEY (`codigoP`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE tb_compra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_hora DATETIME,
    valor_total_produtos DECIMAL(10, 2),
    taxa_entrega DECIMAL(10, 2),
    valor_total_compra DECIMAL(10, 2),
    forma_pagamento VARCHAR(50)
);

CREATE TABLE tb_itens_compra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    compra_id INT,
    codigo_produto INT,
    nome_produto VARCHAR(100),
    quantidade INT,
    preco_unitario DECIMAL(10, 2),
    subtotal DECIMAL(10, 2),
    FOREIGN KEY (compra_id) REFERENCES tb_compra(id)
);