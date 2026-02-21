package com.example.panier_service.controller;

import com.example.panier_service.model.Panier;
import com.example.panier_service.service.PanierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/panier")
@CrossOrigin(origins = "*")
public class PanierController {

    @Autowired
    private PanierService panierService;

    @GetMapping("/{userId}")
    public Panier obtenirPanier(@PathVariable Long userId) {
        return panierService.obtenirPanier(userId);
    }

    @PostMapping("/{userId}/ajouter")
    public Panier ajouterItem(@PathVariable Long userId, @RequestBody Map<String, Object> item) {
        Long produitId = Long.valueOf(item.get("produitId").toString());
        String nomProduit = item.get("nomProduit").toString();
        Double prix = Double.valueOf(item.get("prix").toString());
        Integer quantite = Integer.valueOf(item.get("quantite").toString());
        String imageUrl = item.get("imageUrl").toString();
        
        return panierService.ajouterItem(userId, produitId, nomProduit, prix, quantite, imageUrl);
    }

    @DeleteMapping("/{userId}/retirer/{produitId}")
    public Panier retirerItem(@PathVariable Long userId, @PathVariable Long produitId) {
        return panierService.retirerItem(userId, produitId);
    }

    @PutMapping("/{userId}/modifier/{produitId}")
    public Panier modifierQuantite(@PathVariable Long userId, @PathVariable Long produitId, @RequestParam Integer quantite) {
        return panierService.modifierQuantite(userId, produitId, quantite);
    }

    @DeleteMapping("/{userId}/vider")
    public void viderPanier(@PathVariable Long userId) {
        panierService.viderPanier(userId);
    }
}