package com.example.paiement_service.controller;

import com.example.paiement_service.model.Compte;
import com.example.paiement_service.service.PaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/paiement")
@CrossOrigin(origins = "*")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;

    @GetMapping("/compte/{userId}")
    public Compte obtenirCompte(@PathVariable Long userId) {
        return paiementService.obtenirCompte(userId);
    }

    @PostMapping("/crediter/{userId}")
    public Compte crediterCompte(@PathVariable Long userId, @RequestParam Double montant) {
        return paiementService.crediterCompte(userId, montant);
    }

    @PostMapping("/payer")
    public Map<String, Object> effectuerPaiement(@RequestBody Map<String, Object> paiement) {
        Long userId = Long.valueOf(paiement.get("userId").toString());
        Double montant = Double.valueOf(paiement.get("montant").toString());
        String details = paiement.get("details").toString();
        
        return paiementService.effectuerPaiement(userId, montant, details);
    }
}