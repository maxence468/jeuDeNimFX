package org.example.jeudenimfx;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

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
    int[] joueur = {1,2};

    //hashmap pour associer les noms avec les numeros des joueurs
    Map<String, String> joueurs = new HashMap<String,String>();
    String nom1;
    String nom2;

    //nombre de tour dans le jeu
    int nbTour = 0;
    //nombre max de retrait par tour
    int MAX_RETRAIT = 3;
    //savoir si la partie est finie
    boolean partieFinie = false;
    //recuperer une police personalisée
    Font font = Font.loadFont(getClass().getResourceAsStream("/Monocraft.ttf"), 17);


    @Override
    public void start(Stage stage) throws Exception {
        //Affichage du tour
        Label inviteDeSaisie = new Label();
        inviteDeSaisie.setFont(font);

        //Champ input
        TextField reponse = new TextField();

        //boutton validation de la saisie
        Button btnValider = new Button("retirer");

        //Affichage des allumettes restantes
        Label lblStatutPile  = new Label("| | | | | | | | | |");
        lblStatutPile.setFont(Font.font("Stencil",  FontWeight.BOLD, 40 ));
        lblStatutPile.setTextFill(Color.BROWN);

        //Affiche le resultat de chaque tour
        Label lblFeedback  = new Label();
        lblFeedback.setFont(font);
        lblFeedback.setTextFill(Color.GREY);

        //Bouton pour rejouer
        Button btnRejouer = new Button("Rejouer");

        //image de l'icon
        Image icon = new Image("/nim.png");

        Image image = new Image("/image.png");
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);

        //champs pour choisir le nom des joueurs
        TextField choixNom1 = new TextField();
        //on met un placeholder
        choixNom1.setPromptText("Nom du joueur 1");
        TextField choixNom2 = new TextField();
        choixNom2.setPromptText("Nom du joueur 2");

        Button btnLancer = new Button("Lancer la partie");


        //boite Colonne verticale avec 8 d'espace
        VBox layout = new VBox(8);
        //Ajout des composants dans la boite verticale
        layout.getChildren().addAll(choixNom1, choixNom2, btnLancer, imageView, inviteDeSaisie,lblFeedback,reponse,btnValider,lblStatutPile,btnRejouer);
        btnRejouer.setVisible(false);
        inviteDeSaisie.setVisible(false);
        reponse.setVisible(false);
        btnValider.setVisible(false);

        //alignement de tous les elements au centre
        layout.setAlignment(CENTER);

        // 2. Création de la Scene (attachement du nœud racine + dimensions)
        Scene scene = new Scene(layout, 800, 600);


        // 3. Attachement de la Scene au Stage
        stage.setScene(scene);
        //mettre une icone
        stage.getIcons().add(icon);
        //mettre le titre de la fenetre
        stage.setTitle("Jeu de Nim");
        //Affichage de la scene de theatre
        stage.show();

        btnLancer.setOnAction(e->{
            //on met le nom associé à son numero de joueur
            joueurs.put("1", choixNom1.getText());
            joueurs.put("2", choixNom2.getText());
            //on met visible la suite du jeu
            inviteDeSaisie.setVisible(true);
            reponse.setVisible(true);
            btnValider.setVisible(true);
            //on enleve le choix du nom et le bouton
            choixNom1.setVisible(false);
            choixNom2.setVisible(false);
            btnLancer.setVisible(false);

            //on met le texte de depart avec le nom du joueur 1
            inviteDeSaisie.setText("Joueur "+ joueurs.get(Integer.toString(joueur[nbTour])) + " Combien d'allumette veux-tu enlever ?");
        });

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
                    reponse.clear();
                }
                catch(Exception _) {
                }
            }

            //Verification du choix
            if(choix <= 0 || choix > MAX_RETRAIT){
                lblFeedback.setText("Il faut choisir un nombre entre 1 et 3");
                try {
                    wait(500);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
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
                lblFeedback.setText("Le joueur "+ joueurs.get(Integer.toString(joueur[nbTour % 2])) +" enleve "+ choix +" allumettes");
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
                inviteDeSaisie.setText("Joueur "+ joueurs.get(Integer.toString(joueur[nbTour % 2])) +" Combien d'allumette veux-tu enlever ?");

                //Condition de victoire
                if(taillePileActuelle == 1){
                    //On affiche le message de victoire avec le joueur
                    lblFeedback.setText("Le joueur "+ joueurs.get(Integer.toString(joueur[(nbTour + 1) % 2])) +" gagne !");
                    partieFinie = true;
                }
                if(taillePileActuelle == 0){
                    //On affiche le message de victoire avec le joueur
                    lblFeedback.setText("Le joueur "+ joueurs.get(Integer.toString(joueur[nbTour % 2])) +" gagne !");
                    partieFinie = true;
                }
                //quand on rentre dans une des conditions de victoires
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
            inviteDeSaisie.setText("Nouvelle partie ! \nJoueur "+ joueurs.get(Integer.toString(joueur[nbTour % 2])) +" Combien d'allumette veux-tu enlever ?");
            lblStatutPile.setText("| | | | | | | | | |");
            lblFeedback.setText("");
        });
    }
}
