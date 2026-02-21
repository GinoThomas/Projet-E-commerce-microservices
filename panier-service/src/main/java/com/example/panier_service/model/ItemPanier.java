package com.example.panier_service.model;

// Classe ItemPanier
public class ItemPanier {
    private Long produitId;
    private String nomProduit;
    private Double prix;
    private Integer quantite;
    private String imageUrl;

    public ItemPanier() {}

    public ItemPanier(Long produitId, String nomProduit, Double prix, Integer quantite, String imageUrl) {
        this.produitId = produitId;
        this.nomProduit = nomProduit;
        this.prix = prix;
        this.quantite = quantite;
        this.imageUrl = imageUrl;
    }

    public Long getProduitId() { return produitId; }
    public void setProduitId(Long produitId) { this.produitId = produitId; }
    
    public String getNomProduit() { return nomProduit; }
    public void setNomProduit(String nomProduit) { this.nomProduit = nomProduit; }
    
    public Double getPrix() { return prix; }
    public void setPrix(Double prix) { this.prix = prix; }
    
    public Integer getQuantite() { return quantite; }
    public void setQuantite(Integer quantite) { this.quantite = quantite; }
    
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
