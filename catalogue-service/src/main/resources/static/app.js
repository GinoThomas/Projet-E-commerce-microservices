// Configuration des URLs
const API_CATALOGUE = 'http://localhost:8081/api/catalogue';
const API_PANIER = 'http://localhost:8082/api/panier';
const API_PAIEMENT = 'http://localhost:8083/api/paiement';
const API_STATS = 'http://localhost:8084/api/stats';
const API_AUTH = 'http://localhost:8085/api/auth';

// État global
let panier = null;
let solde = 0;
let userConnecte = null;

// Initialisation
document.addEventListener('DOMContentLoaded', async () => {
    const userStorage = localStorage.getItem('user');
    if (userStorage) {
        userConnecte = JSON.parse(userStorage);
        afficherUtilisateurConnecte(userConnecte);

        await chargerPanier();
        await chargerSolde();
        await chargerStatistiques();
    } else {
        ouvrirModalAuth();
    }

    await chargerCatalogue();   // pour être sûr d'avoir les bons boutons admin

    // Event listeners
    document.getElementById('btnVoirPanier').addEventListener('click', afficherPanier);
    document.getElementById('btnRetourCatalogue').addEventListener('click', afficherCatalogue);
    document.getElementById('lienRetourCatalogue').addEventListener('click', afficherCatalogue);
    document.getElementById('btnAjouterArgent').addEventListener('click', ouvrirModalArgent);
    document.getElementById('btnConfirmerArgent').addEventListener('click', ajouterArgent);
    document.getElementById('btnValiderAchat').addEventListener('click', validerAchat);
    document.getElementById('btnViderPanier').addEventListener('click', viderPanier);
    document.getElementById('btnAuth').addEventListener('click', ouvrirModalAuth);
    document.getElementById('closeAuth').addEventListener('click', fermerModalAuth);
    document.getElementById('btnConnexion').addEventListener('click', seConnecter);
    document.getElementById('btnCreerCompte').addEventListener('click', ouvrirModalCreerCompte);
    document.getElementById('closeSignup').addEventListener('click', fermerModalSignup);
    document.getElementById('btnSignup').addEventListener('click', creerCompte);

    // Modal
    const modal = document.getElementById('modalArgent');
    const span = document.getElementsByClassName('close')[0];
    span.onclick = () => modal.style.display = 'none';
    window.onclick = (e) => { if (e.target == modal) modal.style.display = 'none'; };
});


// -----------------------------------------------------
// ----- TOUT LE RESTE DU CODE (même que toi) ---------
// -----------------------------------------------------
// (Je garde ton code exact, juste corrigé au niveau du DOMContentLoaded)
// -----------------------------------------------------

// Charger le catalogue
async function chargerCatalogue() {
    try {
        const response = await fetch(`${API_CATALOGUE}/produits`);
        const produits = await response.json();
        afficherProduits(produits);
    } catch (error) {
        console.error('Erreur chargement catalogue:', error);
    }
}

// Afficher les produits
function afficherProduits(produits) {
    const container = document.getElementById('catalogueProduits');
    container.innerHTML = produits.map(p => `
        <div class="produit-card">
            <div class="image-wrapper">
  <img src="http://localhost:8081${p.imageUrl}" alt="${p.nom}">
</div>
            <h3>${p.nom}</h3>
            <p>${p.description}</p>
            <div class="prix">${p.prix.toFixed(2)}€</div>

            <div class="quantite-container">
                <button onclick="changerQuantite(${p.id}, -1)">−</button>
                <input 
                    type="number" 
                    id="quantite-${p.id}" 
                    value="1" 
                    min="1" 
                    max="${p.stock}">
                <button onclick="changerQuantite(${p.id}, 1)">+</button>
            </div>

            <button 
                class="btn-primary"
                onclick="ajouterAuPanier(${p.id}, '${p.nom}', ${p.prix}, '${p.imageUrl}')"
                ${p.stock === 0 ? 'disabled class="btn-disabled"' : ''}>
                ${p.stock === 0 ? 'Rupture de stock' : 'Ajouter au panier'}
            </button>

            ${isAdmin() ? `
                <div class="admin-stock">
                    <button onclick="changerStock(${p.id}, -1)">−</button>
                    <span>Stock: ${p.stock}</span>
                    <button onclick="changerStock(${p.id}, 1)">+</button>
                </div>
            ` : `
                <div class="stock">Stock: ${p.stock}</div>
            `}
        </div>
    `).join('');
}

async function changerStock(produitId, delta) {
    if (!isAdmin()) return;

    const endpoint = delta > 0 ? "augmenter-stock" : "diminuer-stock";
    const quantite = Math.abs(delta);

    try {
        await fetch(`${API_CATALOGUE}/produits/${produitId}/${endpoint}?quantite=${quantite}`, {
            method: 'PUT'
        });

        await chargerCatalogue();
    } catch (error) {
        console.error("Erreur mise à jour stock:", error);
    }
}

function changerQuantite(produitId, delta) {
    const input = document.getElementById(`quantite-${produitId}`);
    let valeur = parseInt(input.value) || 1;

    valeur += delta;

    if (valeur < 1) valeur = 1;
    if (input.max && valeur > parseInt(input.max)) {
        valeur = parseInt(input.max);
    }

    input.value = valeur;
}

// Ajouter au panier
async function ajouterAuPanier(produitId, nomProduit, prix, imageUrl) {
    const userId = requireUserId();
    if (!userId) return;
    const quantite = parseInt(
        document.getElementById(`quantite-${produitId}`).value
    ) || 1;

    try {
        const response = await fetch(`${API_PANIER}/${requireUserId()}/ajouter`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                produitId,
                nomProduit,
                prix,
                quantite,
                imageUrl
            })
        });

        panier = await response.json();
        mettreAJourCompteurPanier();
        alert(`Produit ajouté (${quantite}) au panier !`);
    } catch (error) {
        console.error('Erreur ajout panier:', error);
    }
}

// Charger le panier
async function chargerPanier() {
    try {
        const userId = requireUserId();
        if (!userId) return;

        const response = await fetch(`${API_PANIER}/${userId}`);
        panier = await response.json();
        mettreAJourCompteurPanier();
    } catch (error) {
        console.error('Erreur chargement panier:', error);
    }
}

// Mettre à jour le compteur du panier
function mettreAJourCompteurPanier() {
    const count = panier ? panier.items.length : 0;
    document.getElementById('panierCount').textContent = count;
}

// Afficher le panier
function afficherPanier() {
    document.getElementById('catalogueSection').style.display = 'none';
    document.getElementById('panierSection').style.display = 'block';
    afficherItemsPanier();
}

// Afficher les items du panier
function afficherItemsPanier() {
    const container = document.getElementById('panierItems');
    if (!panier || panier.items.length === 0) {
        container.innerHTML = '<p>Votre panier est vide</p>';
        document.getElementById('panierTotal').textContent = '0.00€';
        return;
    }
    
    container.innerHTML = panier.items.map(item => `
        <div class="panier-item">
            <div class="panier-item-info">
                <img src="http://localhost:8081${item.imageUrl}" alt="${item.nomProduit}">
                <div>
                    <h3>${item.nomProduit}</h3>
                    <p>${item.prix.toFixed(2)}€</p>
                </div>
            </div>
            <div class="panier-item-actions">
                <input type="number" value="${item.quantite}" min="1" 
                    onchange="modifierQuantite(${item.produitId}, this.value)">
                <button class="btn-danger" onclick="retirerDuPanier(${item.produitId})">Retirer</button>
            </div>
        </div>
    `).join('');
    
    document.getElementById('panierTotal').textContent = panier.total.toFixed(2) + '€';
}

// Modifier quantité
async function modifierQuantite(produitId, quantite) {
    try {
        const userId = requireUserId();
        if (!userId) return;
        const response = await fetch(`${API_PANIER}/${requireUserId()}/modifier/${produitId}?quantite=${quantite}`, {
            method: 'PUT'
        });
        panier = await response.json();
        afficherItemsPanier();
    } catch (error) {
        console.error('Erreur modification quantité:', error);
    }
}

// Retirer du panier
async function retirerDuPanier(produitId) {
    try {
        const userId = requireUserId();
        if (!userId) return;
        const response = await fetch(`${API_PANIER}/${requireUserId()}/retirer/${produitId}`, {
            method: 'DELETE'
        });
        panier = await response.json();
        mettreAJourCompteurPanier();
        afficherItemsPanier();
    } catch (error) {
        console.error('Erreur retrait panier:', error);
    }
}

// Vider le panier
async function viderPanier() {
    if (!confirm('Voulez-vous vraiment vider votre panier ?')) return;
    
    try {
        const userId = requireUserId();
        if (!userId) return;
        await fetch(`${API_PANIER}/${requireUserId()}/vider`, { method: 'DELETE' });
        panier = { userId: requireUserId(), items: [], total: 0 };
        mettreAJourCompteurPanier();
        afficherItemsPanier();
    } catch (error) {
        console.error('Erreur vidage panier:', error);
    }
}

// Afficher le catalogue
function afficherCatalogue() {
    document.getElementById('catalogueSection').style.display = 'block';
    document.getElementById('panierSection').style.display = 'none';
}

// Charger le solde
async function chargerSolde() {
    console.log("chargerSolde → userConnecte =", userConnecte);

    if (!userConnecte) return;

    const response = await fetch(
        `${API_PAIEMENT}/compte/${userConnecte.id}`
    );

    const compte = await response.json();
    solde = compte.solde;

    document.getElementById("solde").textContent =
        solde.toFixed(2) + "€";
}

// Ouvrir modal ajout argent
function ouvrirModalArgent() {
    document.getElementById('modalArgent').style.display = 'block';
}

// Ajouter de l'argent
async function ajouterArgent() {
    const userId = requireUserId();
    if (!userId) return;
    const montant = parseFloat(document.getElementById('montantArgent').value);
    if (!montant || montant <= 0) {
        alert('Montant invalide');
        return;
    }
    
    try {
        const response = await fetch(`${API_PAIEMENT}/crediter/${userId}?montant=${montant}`, {
            method: 'POST'
        });
        const compte = await response.json();
        solde = compte.solde;
        document.getElementById('solde').textContent = solde.toFixed(2) + '€';
        document.getElementById('modalArgent').style.display = 'none';
        document.getElementById('montantArgent').value = '';
        chargerStatistiques();
        alert(`${montant.toFixed(2)}€ ajouté avec succès !`);
    } catch (error) {
        console.error('Erreur ajout argent:', error);
    }
}

// Valider l'achat
async function validerAchat() {
    const userId = requireUserId();
    if (!userId) return;
    if (!panier || panier.items.length === 0) {
        alert('Votre panier est vide');
        return;
    }
    
    if (panier.total > solde) {
        alert(`Solde insuffisant ! Il vous manque ${(panier.total - solde).toFixed(2)}€`);
        return;
    }
    
    try {
        const details = panier.items.map(i => `${i.nomProduit} x${i.quantite}`).join(', ');
        const response = await fetch(`${API_PAIEMENT}/payer`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({userId: userConnecte.id, montant: panier.total, details})
        });
        
        const resultat = await response.json();
        
        if (resultat.success) {
            // Diminuer les stocks
            for (const item of panier.items) {
                await fetch(`${API_CATALOGUE}/produits/${item.produitId}/diminuer-stock?quantite=${item.quantite}`, {
                    method: 'PUT'
                });
            }
            
            alert('Achat validé avec succès !');
            await viderPanier();
            chargerSolde();
            chargerCatalogue();
            chargerStatistiques();
            afficherCatalogue();
        } else {
            alert(resultat.message);
        }
    } catch (error) {
        console.error('Erreur validation achat:', error);
        alert('Erreur lors de l\'achat');
    }
}

// Charger les statistiques
async function chargerStatistiques() {
    const userId = requireUserId();
    if (!userId) return;
    try {
        // Stats globales
        const statsResponse = await fetch(`${API_STATS}/statistiques`);
        const stats = await statsResponse.json();
        
        document.getElementById('statsContainer').innerHTML = `
            <div class="stat-card">
                <h4>Transactions totales</h4>
                <div class="stat-value">${stats.nombreTransactions}</div>
            </div>
            <div class="stat-card">
                <h4>Achats réussis</h4>
                <div class="stat-value transaction-success">${stats.nombreAchatsReussis}</div>
            </div>
            <div class="stat-card">
                <h4>Achats échoués</h4>
                <div class="stat-value transaction-failed">${stats.nombreAchatsEchoues}</div>
            </div>
            <div class="stat-card">
                <h4>Total des ventes</h4>
                <div class="stat-value">${stats.montantTotalVentes.toFixed(2)}€</div>
            </div>
        `;
        
        // Historique utilisateur
        const histoResponse = await fetch(`${API_STATS}/transactions/${userId}`);
        const transactions = await histoResponse.json();
        
        const container = document.getElementById('historiqueTransactions');
        if (transactions.length === 0) {
            container.innerHTML = '<p>Aucune transaction pour le moment</p>';
        } else {
            container.innerHTML = transactions.reverse().map(t => `
                <div class="transaction-item">
                    <div>
                        <strong class="${t.statut === 'SUCCESS' ? 'transaction-success' : 'transaction-failed'}">
                            ${t.type === 'ACHAT' ? '🛒' : '💰'} ${t.details}
                        </strong>
                        <div style="font-size: 0.85rem; color: #999;">${new Date(t.timestamp).toLocaleString()}</div>
                    </div>
                    <div style="font-weight: bold;">
                        ${t.type === 'CREDIT' ? '+' : '-'}${t.montant.toFixed(2)}€
                    </div>
                </div>
            `).join('');
        }
    } catch (error) {
        console.error('Erreur chargement stats:', error);
    }
}

function ouvrirModalAuth() {
    document.getElementById('modalAuth').style.display = 'block';
}

function fermerModalAuth() {
    document.getElementById('modalAuth').style.display = 'none';
    document.getElementById('authError').style.display = 'none';
}

async function seConnecter() {
    const email = document.getElementById('authEmail').value;
    const password = document.getElementById('authPassword').value;

    if (!email || !password) {
        alert('Veuillez remplir tous les champs');
        return;
    }

    try {
        const response = await fetch(`${API_AUTH}/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        if (!response.ok) {
            throw new Error('Erreur auth');
        }

        const data = await response.json();

        localStorage.setItem('token', data.token);
        localStorage.setItem('user', JSON.stringify(data.user));

        userConnecte = data.user;

        // ⚡️ RECHARGER LES DONNÉES DU COMPTE
        await chargerPanier();
        await chargerSolde();
        await chargerStatistiques();
        await chargerCatalogue();

        fermerModalAuth();
        afficherUtilisateurConnecte(userConnecte);

    } catch (error) {
        document.getElementById('authError').style.display = 'block';
    }
}

function afficherUtilisateurConnecte(user) {
    const btnAuth = document.getElementById('btnAuth');

    btnAuth.textContent = `👋 ${user.nom}`;
    btnAuth.classList.remove('btn-secondary');
    btnAuth.classList.add('btn-success');

    btnAuth.onclick = seDeconnecter;
}

function seDeconnecter() {
    userConnecte = null;
    panier = null;
    solde = 0;

    document.getElementById('panierItems').innerHTML = '<p>Veuillez vous connecter pour voir votre panier.</p>';
    document.getElementById('panierTotal').textContent = '0.00€';

    localStorage.removeItem('token');
    localStorage.removeItem('user');

    document.getElementById('solde').textContent = '0.00€';
    mettreAJourCompteurPanier();

    const btnAuth = document.getElementById('btnAuth');
    btnAuth.textContent = '👤 S’identifier';
    btnAuth.className = 'btn-secondary';
    btnAuth.onclick = ouvrirModalAuth;

    chargerCatalogue(); // recharge le catalogue sans admin
}

function ouvrirModalCreerCompte() {
    document.getElementById('modalSignup').style.display = 'block';
}

function fermerModalSignup() {
    document.getElementById('modalSignup').style.display = 'none';
    document.getElementById('signupError').style.display = 'none';
}

async function creerCompte() {
    const nom = document.getElementById('signupNom').value;
    const email = document.getElementById('signupEmail').value;
    const password = document.getElementById('signupPassword').value;

    if (!nom || !email || !password) {
        alert("Veuillez remplir tous les champs");
        return;
    }

    try {
        const response = await fetch(`${API_AUTH}/signup`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nom, email, password })
        });

        const data = await response.text();

        if (!response.ok) {
            console.error("Erreur signup:", response.status, data);
            document.getElementById('signupError').textContent = data;
            document.getElementById('signupError').style.display = 'block';
            return;
        }

        alert("Compte créé ! Vous pouvez maintenant vous connecter.");
        fermerModalSignup();
        fermerModalAuth();

    } catch (error) {
        console.error(error);
        document.getElementById('signupError').textContent = error.message;
        document.getElementById('signupError').style.display = 'block';
    }
}

function requireUserId() {
    if (!userConnecte || !userConnecte.id) {
        ouvrirModalAuth();
        return null;
    }
    return userConnecte.id;
}

function getUser() {
    return JSON.parse(localStorage.getItem('user'));
}

function getUserId() {
    const user = getUser();
    return user ? user.id : null;
}

function isAdmin() {
    const user = JSON.parse(localStorage.getItem('user'));
    return user && user.email === "admin@admin.com";
}
