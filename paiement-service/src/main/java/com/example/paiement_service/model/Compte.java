package com.example.paiement_service.model;

// Classe Compte
public class Compte {
    private Long userId;
    private Double solde;

    public Compte() {
        this.solde = 0.0;
    }

    public Compte(Long userId, Double solde) {
        this.userId = userId;
        this.solde = solde;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Double getSolde() { return solde; }
    public void setSolde(Double solde) { this.solde = solde; }

    public void crediter(Double montant) {
        this.solde += montant;
    }

    public boolean debiter(Double montant) {
        if (this.solde >= montant) {
            this.solde -= montant;
            return true;
        }
        return false;
    }
}
