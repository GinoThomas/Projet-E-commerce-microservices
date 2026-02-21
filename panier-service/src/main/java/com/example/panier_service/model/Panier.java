package com.example.panier_service.model;

import java.util.ArrayList;
import java.util.List;

// Classe Panier
public class Panier {
    private Long userId;
    private List<ItemPanier> items;
    private Double total;

    public Panier() {
        this.items = new ArrayList<>();
        this.total = 0.0;
    }

    public Panier(Long userId) {
        this.userId = userId;
        this.items = new ArrayList<>();
        this.total = 0.0;
    }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    
    public List<ItemPanier> getItems() { return items; }
    public void setItems(List<ItemPanier> items) { this.items = items; }
    
    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public void calculerTotal() {
        this.total = items.stream()
                .mapToDouble(item -> item.getPrix() * item.getQuantite())
                .sum();
    }
}