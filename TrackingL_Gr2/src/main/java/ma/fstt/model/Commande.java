package ma.fstt.model;

public class Commande {
      private int id_commande ;

        private String date_commande ;

        private String statut ;

        private double distance;

        private int id_livreur;

        public Commande() {
        }

        public Commande(int id_commande, String date_commande, String statut, double distance, int id_livreur) {
            this.id_commande= id_commande;
            this.date_commande= date_commande;
            this.statut = statut;
            this.distance= distance;
            this.id_livreur= id_livreur;
        }

        public int getId_commande() {
            return id_commande;
        }

        public void setId_commande(int id_commande) {
            this.id_commande = id_commande;
        }

        public String getDate_commande() {
            return date_commande;
        }

        public void setDate_commande(String date_commande) {
            this.date_commande = date_commande;
        }

        public String getStatut(){return statut;}

        public void setStatut(String statut){this.statut= statut;}
        public double getDistance() {
            return distance;
        }

        public void setDistance(double distance) {
            this.distance = distance;
        }

        public int getId_livreur() {
        return id_livreur;
    }

        public void setId_livreur(int id_livreur) {
        this.id_livreur = id_livreur;
    }



    @Override
        public String toString() {
            return id_commande+ " ; "+ date_commande+ " ; "+
        statut+ " ; "+distance;
        }


}

