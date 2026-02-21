package com.example.paiement_service.service;

import com.example.paiement_service.model.Compte;
//import com.example.paiement_service.model.Transaction;
// Doit-t-on vraiment mettre la Transaction public !?
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PaiementService {
    private final Map<Long, Compte> comptes = new HashMap<>();
    private final AtomicLong transactionIdGenerator = new AtomicLong(1);

    @Autowired
    private RestTemplate restTemplate;

    public Compte obtenirCompte(Long userId) {
        return comptes.computeIfAbsent(userId, id -> new Compte(id, 1000.0)); // 1000€ de départ
    }

    public Compte crediterCompte(Long userId, Double montant) {
        Compte compte = obtenirCompte(userId);
        compte.crediter(montant);
        
        // Notifier suivi-stat-service
        notifierTransaction(userId, montant, "CREDIT", "SUCCESS", "Ajout de fonds");
        
        return compte;
    }

    public Map<String, Object> effectuerPaiement(Long userId, Double montant, String details) {
        Map<String, Object> resultat = new HashMap<>();
        Compte compte = obtenirCompte(userId);

        if (compte.debiter(montant)) {
            resultat.put("success", true);
            resultat.put("message", "Paiement effectué avec succès");
            resultat.put("nouveauSolde", compte.getSolde());
            
            // Notifier suivi-stat-service
            notifierTransaction(userId, montant, "ACHAT", "SUCCESS", details);
        } else {
            resultat.put("success", false);
            resultat.put("message", "Solde insuffisant");
            resultat.put("soldeActuel", compte.getSolde());
            
            // Notifier suivi-stat-service
            notifierTransaction(userId, montant, "ACHAT", "FAILED", details + " - Solde insuffisant");
        }

        return resultat;
    }

    private void notifierTransaction(Long userId, Double montant, String type, String statut, String details) {
        try {
            //URL Local :
            //String url = "http://localhost:8084/api/stats/transaction";

            //URL Docker :
            String url = "http://service-stat:8084/api/stats/transaction";
            
            Map<String, Object> transaction = new HashMap<>();
            transaction.put("id", transactionIdGenerator.getAndIncrement());
            transaction.put("userId", userId);
            transaction.put("montant", montant);
            transaction.put("type", type);
            transaction.put("statut", statut);
            transaction.put("timestamp", LocalDateTime.now().toString());
            transaction.put("details", details);
            
            restTemplate.postForObject(url, transaction, String.class);
        } catch (Exception e) {
            System.err.println("Erreur lors de la notification au service stats: " + e.getMessage());
        }
    }
}