package com.example.catalogue_service.service;

import com.example.catalogue_service.model.Produit;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class CatalogueService {
    private final Map<Long, Produit> produits = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @PostConstruct
    public void init() {
        // Initialisation des produits
        ajouterProduit(new Produit(null, "Laptop Dell XPS 13", "Ultrabook performant 13 pouces", 1299.99, 15, "/images/produit1.jpg"));
        ajouterProduit(new Produit(null, "iPhone 15 Pro", "Smartphone Apple dernière génération", 1199.99, 25, "/images/produit2.jpg"));
        ajouterProduit(new Produit(null, "Samsung Galaxy S24", "Smartphone Android haut de gamme", 999.99, 30, "/images/produit3.jpg"));
        ajouterProduit(new Produit(null, "Sony WH-1000XM5", "Casque à réduction de bruit", 399.99, 40, "/images/produit4.jpg"));
        ajouterProduit(new Produit(null, "iPad Air", "Tablette Apple 10.9 pouces", 699.99, 20, "/images/produit5.jpg"));
        ajouterProduit(new Produit(null, "MacBook Pro 14", "Ordinateur portable professionnel", 2199.99, 10, "/images/produit6.jpg"));
        ajouterProduit(new Produit(null, "AirPods Pro", "Écouteurs sans fil avec ANC", 279.99, 50, "/images/produit7.jpg"));
        ajouterProduit(new Produit(null, "Nintendo Switch", "Console de jeu portable", 299.99, 35, "/images/produit8.jpg"));
    }

    public List<Produit> obtenirTousProduits() {
        return new ArrayList<>(produits.values());
    }

    public Optional<Produit> obtenirProduitParId(Long id) {
        return Optional.ofNullable(produits.get(id));
    }

    public Produit ajouterProduit(Produit produit) {
        if (produit.getId() == null) {
            produit.setId(idGenerator.getAndIncrement());
        }
        produits.put(produit.getId(), produit);
        return produit;
    }

    public boolean diminuerStock(Long produitId, Integer quantite) {
        Produit produit = produits.get(produitId);
        if (produit != null && produit.getStock() >= quantite) {
            produit.setStock(produit.getStock() - quantite);
            return true;
        }
        return false;
    }

    public Produit trouverProduit(Long id) {
        return produits.get(id);
    }

    public Produit augmenterStock(Long id, Integer quantite) {
        Produit produit = produits.get(id);
        if (produit == null) {
            throw new RuntimeException("Produit non trouvé");
        }
        produit.setStock(produit.getStock() + quantite);
        return produit;
    }
}