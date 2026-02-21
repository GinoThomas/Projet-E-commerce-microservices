package com.example.catalogue_service.model;

public class Produit {
    private Long id;
    private String nom;
    private String description;
    private Double prix;
    private Integer stock;
    private String imageUrl;

    public Produit() {}

    public Produit(Long id, String nom, String description, Double prix, Integer stock, String imageUrl) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.prix = prix;
        this.stock = stock;
        this.imageUrl = imageUrl;
    }

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }
    
    public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}