-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : mar. 04 avr. 2023 à 00:45
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `glovo`
--

-- --------------------------------------------------------

--
-- Structure de la table `commande`
--

CREATE TABLE `commande` (
  `id_commande` int(255) NOT NULL,
  `date_commande` varchar(50) NOT NULL,
  `statut` varchar(80) NOT NULL,
  `distance` double NOT NULL,
  `id_livreur` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `commande`
--

INSERT INTO `commande` (`id_commande`, `date_commande`, `statut`, `distance`, `id_livreur`) VALUES
(0, '09-01-2021', 'livrée', 2500, 10),
(1, '29-02-2022', 'livrée', 1580, 85),
(2, '24-05-2023', 'livrée', 1450, 5),
(3, '21-01-2021', 'livrée', 2650, 10),
(7, '24-05-2023', 'En attente de livraison', 5850, 100),
(8, '15-02-2023', 'livrée', 1520, 85),
(10, '02-04-2023', 'livrée', 2560, 1),
(56, '03-04-2022', 'En attente de livraison', 1520, 1);

-- --------------------------------------------------------

--
-- Structure de la table `ligne_commande`
--

CREATE TABLE `ligne_commande` (
  `id` int(11) NOT NULL,
  `id_commande` int(255) NOT NULL,
  `id_produit` int(255) NOT NULL,
  `quantite` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `ligne_commande`
--

INSERT INTO `ligne_commande` (`id`, `id_commande`, `id_produit`, `quantite`) VALUES
(2, 2, 1, 3),
(4, 3, 2, 5),
(5, 3, 3, 2),
(6, 7, 2, 5),
(8, 8, 0, 10),
(9, 3, 2, 7),
(10, 56, 3, 4),
(11, 56, 6, 2);

-- --------------------------------------------------------

--
-- Structure de la table `livreur`
--

CREATE TABLE `livreur` (
  `id_livreur` int(255) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `livreur`
--

INSERT INTO `livreur` (`id_livreur`, `nom`, `telephone`) VALUES
(0, 'Ahmed', '0612345678'),
(1, 'Ayoub', '0619345867'),
(2, 'Khalid', '0625384975'),
(5, 'livreur 3', '0680258900'),
(10, 'Zaid', '0659786200'),
(11, 'Hakim', '0698571426'),
(13, 'Mouhamed', '0654231879'),
(25, 'Yassine', '0689512686'),
(85, 'Asaad', '0615487956'),
(92, 'livreur 6', '0625987452'),
(100, 'Jabir', '0615893625');

-- --------------------------------------------------------

--
-- Structure de la table `produit`
--

CREATE TABLE `produit` (
  `id_produit` int(255) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `categorie` varchar(100) NOT NULL,
  `prix` int(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `produit`
--

INSERT INTO `produit` (`id_produit`, `nom`, `categorie`, `prix`) VALUES
(0, 'pomme', 'fruit', 15),
(1, 'banane', 'fruit', 10),
(3, 'tacos', 'food', 35),
(5, 'huile', 'alementation', 25),
(6, 'l\'emonade', 'food', 10);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `commande`
--
ALTER TABLE `commande`
  ADD PRIMARY KEY (`id_commande`),
  ADD KEY `id_livreur` (`id_livreur`);

--
-- Index pour la table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_commande` (`id_commande`,`id_produit`) USING BTREE;

--
-- Index pour la table `livreur`
--
ALTER TABLE `livreur`
  ADD PRIMARY KEY (`id_livreur`);

--
-- Index pour la table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id_produit`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `ligne_commande`
--
ALTER TABLE `ligne_commande`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
