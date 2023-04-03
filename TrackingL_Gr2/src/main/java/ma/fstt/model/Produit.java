package ma.fstt.model;

public class Produit {

        private int id_produit ;
        private String nom ;
        private String categorie ;
        private int prix;

        public Produit() {
        }

        public Produit(int id_produit, String nom, String categorie, int prix) {
            this.id_produit = id_produit;
            this.nom = nom;
            this.categorie = categorie;
            this.prix = prix;
        }

        public int getId_produit() {
            return id_produit;
        }

        public void setId_produit(int id_produit) {
            this.id_produit = id_produit;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getCategorie() {
            return categorie;
        }

        public void setCategorie(String categorie) {
            this.categorie = categorie;
        }

        public int getPrix(){return prix;}

        public void setPrix(int prix){this.prix=prix;}


        @Override
        public String toString() {
            return id_produit + " ; "+nom + " ; " +
                   categorie + " ; "+ prix;
        }


}
