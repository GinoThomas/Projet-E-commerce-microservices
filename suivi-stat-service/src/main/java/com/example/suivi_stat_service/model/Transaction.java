package com.example.suivi_stat_service.model;

public class Transaction {
    private Long id;
    private Long userId;
    private Double montant;
    private String type; // ACHAT ou CREDIT
    private String statut; // SUCCESS ou FAILED
    private String timestamp;
    private String details;

    public Transaction() {}

    public Transaction(Long id, Long userId, Double montant, String type, String statut, String timestamp, String details) {
        this.id = id;
        this.userId = userId;
        this.montant = montant;
        this.type = type;
        this.statut = statut;
        this.timestamp = timestamp;
        this.details = details;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }
    
    public String getTimestamp() { return timestamp; }
    public void setTimestamp(String timestamp) { this.timestamp = timestamp; }
    
    public String getDetails() { return details; }
    public void setDetails(String details) { this.details = details; }
}