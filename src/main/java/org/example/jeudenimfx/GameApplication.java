package org.example.jeudenimfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static javafx.geometry.Pos.*;


public class GameApplication extends Application {
    //declaration variable
    //nombre d'allumette initial
    int PILE_INITIALE  = 10;
    //taille de la pille pour chaque tour
    int taillePileActuelle = PILE_INITIALE;
    //declaration d'une variable String pour l'affichage
    String allumetteString = "";
    //choix du joueur
    int choix;
    //tableau contenant les joueurs
    int joueur[] = {1,2};
    //nombre de tour dans le jeu
    int nbTour = 0;
    //nombre max de retrait par tour
    int MAX_RETRAIT = 3;
    //savoir si la partie est finie
    boolean partieFinie = false;

    @Override
    public void start(Stage stage) throws Exception {
        //Affichage du tour
        Label inviteDeSaisie = new Label("Joueur"+ joueur[nbTour] + " Combien d'allumette veux-tu enlever ?");
        //Champ input
        TextField reponse = new TextField();
        //boutton validation de la saisie
        Button btnValider = new Button("retirer");
        //Affichage des allumettes restantes
        Label lblStatutPile  = new Label("| | | | | | | | | |");
        //Affiche le resultat de chaque tour
        Label lblFeedback  = new Label();
        //Bouton pour rejouer
        Button btnRejouer = new Button("Rejouer");

        //boite Colonne verticale avec 8 d'espace
        VBox layout = new VBox(8);
        //Ajout des composants dans la boite verticale
        layout.getChildren().addAll(inviteDeSaisie,reponse,btnValider,lblStatutPile,lblFeedback,btnRejouer);
        btnRejouer.setVisible(false);

        //alignement de tous les elements au centre
        layout.setAlignment(CENTER);

        // 2. Création de la Scene (attachement du nœud racine + dimensions)
        Scene scene = new Scene(layout, 400, 400);


        // 3. Attachement de la Scene au Stage
        stage.setScene(scene);

        //Affichage de la scene de theatre
        stage.show();

        btnValider.setOnAction( e -> {
            //on verifie que le joueur a rempli le textField
            if(reponse.getText().isEmpty()){
                choix = 0;
            }
            //le joueur a rentré quelque chose
            else{
                //on recupere le choix du joueur et le converti en int
                try {
                    choix = Integer.parseInt(reponse.getText());
                }
                catch(Exception ex) {
                    System.out.println("pas un nombre");
                    lblFeedback.setText("Il faut choisir un NOMBRE entre 1 et 3");
                }
            }

            //Verification du choix
            if(choix <= 0 || choix > MAX_RETRAIT){
                lblFeedback.setText("Il faut choisir un nombre entre 1 et 3");
            }
            else if(choix > taillePileActuelle){
                lblFeedback.setText("le nombre choisi ne doit pas depasser le nombre d'allumettes restant");
            }
            //le choix est valide
            else{
                //on retire le nombre choisi
                taillePileActuelle -= choix;
                //affichage du choix
                lblFeedback.setText("Le joueur "+ joueur[nbTour % 2] +" enleve "+ choix +" allumettes");
                //on remet le choix à 0
                choix = 0;


                //affichage du nombre d'allumette
                //on remet la chaine de caractere vide
                allumetteString = "";
                //on ajoute le nombre d'allumette dans la chaine
                for(int i = 0; i < taillePileActuelle; i++ ){
                    allumetteString += "| ";
                }
                //on met le texte dans le label
                lblStatutPile.setText(allumetteString);

                //on vide le champ de reponse
                reponse.setText("");

                //+1 tour
                nbTour ++;

                //On affiche le numero du joueur qui doit jouer
                inviteDeSaisie.setText("Joueur "+ joueur[nbTour % 2] +" Combien d'allumette veux-tu enlever ?");

                //Condition de victoire
                if(taillePileActuelle == 1){
                    //On affiche le message de victoire avec le joueur
                    lblFeedback.setText("Le joueur "+ joueur[(nbTour + 1) % 2] +" gagne !");
                    partieFinie = true;
                }
                if(taillePileActuelle == 0){
                    //On affiche le message de victoire avec le joueur
                    lblFeedback.setText("Le joueur "+ joueur[(nbTour) % 2] +" gagne !");
                    partieFinie = true;
                }
                if(partieFinie){
                    //affichage du message de fin de partie
                    inviteDeSaisie.setText("FIN DE LA PARTIE !");
                    //on retire de la scene les elements inutiles
                    btnValider.setVisible(false);
                    reponse.setVisible(false);
                    lblStatutPile.setVisible(false);
                    //on ajoute le bouton pour rejouer
                    btnRejouer.setVisible(true);

                }
            }
        });

        btnRejouer.setOnAction( e -> {
            partieFinie = false;

            //on remet visible dans la scene les elements
            btnValider.setVisible(true);
            reponse.setVisible(true);
            lblStatutPile.setVisible(true);

            //on enleve le bouton pour rejouer
            btnRejouer.setVisible(false);

            //on remet tout d'origine
            taillePileActuelle = 10;
            nbTour = 0;
            inviteDeSaisie.setText("Nouvelle partie ! \nJoueur "+ joueur[nbTour % 2] +" Combien d'allumette veux-tu enlever ?");
            lblStatutPile.setText("| | | | | | | | | |");
            lblFeedback.setText("");
        });
    }
}
