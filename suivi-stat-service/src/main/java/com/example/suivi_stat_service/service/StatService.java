package com.example.suivi_stat_service.service;

import com.example.suivi_stat_service.model.Transaction;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatService {
    private final List<Transaction> transactions = new ArrayList<>();

    public void ajouterTransaction(Transaction transaction) {
        transactions.add(transaction);
        System.out.println("Transaction enregistrée: " + transaction.getType() + " - " + transaction.getMontant() + "€");
    }

    public List<Transaction> obtenirToutesTransactions() {
        return new ArrayList<>(transactions);
    }

    public List<Transaction> obtenirTransactionsParUser(Long userId) {
        return transactions.stream()
                .filter(t -> t.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public Map<String, Object> obtenirStatistiques() {
        Map<String, Object> stats = new HashMap<>();
        
        long nbTransactions = transactions.size();
        long nbAchatsReussis = transactions.stream()
                .filter(t -> "ACHAT".equals(t.getType()) && "SUCCESS".equals(t.getStatut()))
                .count();
        long nbAchatsEchoues = transactions.stream()
                .filter(t -> "ACHAT".equals(t.getType()) && "FAILED".equals(t.getStatut()))
                .count();
        
        double montantTotalVentes = transactions.stream()
                .filter(t -> "ACHAT".equals(t.getType()) && "SUCCESS".equals(t.getStatut()))
                .mapToDouble(Transaction::getMontant)
                .sum();
        
        double montantTotalCredits = transactions.stream()
                .filter(t -> "CREDIT".equals(t.getType()))
                .mapToDouble(Transaction::getMontant)
                .sum();

        stats.put("nombreTransactions", nbTransactions);
        stats.put("nombreAchatsReussis", nbAchatsReussis);
        stats.put("nombreAchatsEchoues", nbAchatsEchoues);
        stats.put("montantTotalVentes", montantTotalVentes);
        stats.put("montantTotalCredits", montantTotalCredits);
        
        return stats;
    }
}