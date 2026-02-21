package com.example.panier_service.service;

import com.example.panier_service.model.Panier;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class PanierService {
    private final Map<Long, Panier> paniers = new HashMap<>();

    public Panier obtenirPanier(Long userId) {
        return paniers.computeIfAbsent(userId, Panier::new);
    }

    public Panier ajouterItem(Long userId, Long produitId, String nomProduit, Double prix, Integer quantite, String imageUrl) {
        Panier panier = obtenirPanier(userId);
        
        Optional<com.example.panier_service.model.ItemPanier> existingItem = panier.getItems().stream()
                .filter(item -> item.getProduitId().equals(produitId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantite(existingItem.get().getQuantite() + quantite);
        } else {
            panier.getItems().add(new com.example.panier_service.model.ItemPanier(produitId, nomProduit, prix, quantite, imageUrl));
        }
        
        panier.calculerTotal();
        return panier;
    }

    public Panier retirerItem(Long userId, Long produitId) {
        Panier panier = obtenirPanier(userId);
        panier.getItems().removeIf(item -> item.getProduitId().equals(produitId));
        panier.calculerTotal();
        return panier;
    }

    public Panier modifierQuantite(Long userId, Long produitId, Integer quantite) {
        Panier panier = obtenirPanier(userId);
        panier.getItems().stream()
                .filter(item -> item.getProduitId().equals(produitId))
                .findFirst()
                .ifPresent(item -> item.setQuantite(quantite));
        panier.calculerTotal();
        return panier;
    }

    public void viderPanier(Long userId) {
        Panier panier = obtenirPanier(userId);
        panier.getItems().clear();
        panier.setTotal(0.0);
    }
}
