package com.example.suivi_stat_service.controller;

import com.example.suivi_stat_service.model.Transaction;
import com.example.suivi_stat_service.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
@CrossOrigin(origins = "*")
public class StatController {

    @Autowired
    private StatService statService;

    @PostMapping("/transaction")
    public void enregistrerTransaction(@RequestBody Transaction transaction) {
        statService.ajouterTransaction(transaction);
    }

    @GetMapping("/transactions")
    public List<Transaction> obtenirToutesTransactions() {
        return statService.obtenirToutesTransactions();
    }

    @GetMapping("/transactions/{userId}")
    public List<Transaction> obtenirTransactionsParUser(@PathVariable Long userId) {
        return statService.obtenirTransactionsParUser(userId);
    }

    @GetMapping("/statistiques")
    public Map<String, Object> obtenirStatistiques() {
        return statService.obtenirStatistiques();
    }
}