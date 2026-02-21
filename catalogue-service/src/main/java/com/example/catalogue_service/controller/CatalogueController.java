package com.example.catalogue_service.controller;

import com.example.catalogue_service.model.Produit;
import com.example.catalogue_service.service.CatalogueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogue")
@CrossOrigin(origins = "*")
public class CatalogueController {

    @Autowired
    private CatalogueService catalogueService;

    @GetMapping("/produits")
    public List<Produit> obtenirTousProduits() {
        return catalogueService.obtenirTousProduits();
    }

    @GetMapping("/produits/{id}")
    public ResponseEntity<Produit> obtenirProduit(@PathVariable Long id) {
        return catalogueService.obtenirProduitParId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/produits")
    public Produit ajouterProduit(@RequestBody Produit produit) {
        return catalogueService.ajouterProduit(produit);
    }

    @PutMapping("/produits/{id}/diminuer-stock")
    public ResponseEntity<Boolean> diminuerStock(@PathVariable Long id, @RequestParam Integer quantite) {
        boolean success = catalogueService.diminuerStock(id, quantite);
        return ResponseEntity.ok(success);
    }

    @PutMapping("/produits/{id}/augmenter-stock")
    public Produit augmenterStock(@PathVariable Long id, @RequestParam Integer quantite) {
        return catalogueService.augmenterStock(id, quantite);
    }
}