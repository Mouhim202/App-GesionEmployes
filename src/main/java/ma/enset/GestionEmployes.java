package ma.enset;
import java.util.Scanner;
import java.util.Arrays;
import java.util.Comparator;


public class GestionEmployes {
    private static final int MAX_EMPLOYES = 50;
    private static Employe[] employes = new Employe[MAX_EMPLOYES];
    private static int nombreEmployes = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choix;

        do {
            printMenu();
            System.out.print("Choisissez une option : ");
            choix = scanner.nextInt();
            scanner.nextLine();

            switch (choix) {
                case 1:
                    ajouterEmploye(scanner);
                    break;
                case 2:
                    modifierEmploye(scanner);
                    break;
                case 3:
                    supprimerEmploye(scanner);
                    break;
                case 4:
                    afficherEmployes();
                    break;
                case 5:
                    rechercherEmploye(scanner);
                    break;
                case 6:
                    calculerMasseSalariale();
                    break;
                case 7:
                    System.out.print("Souhaitez-vous trier par salaire croissant (entrez 1) ou décroissant (entrez 2) ? ");
                    int choixTri = scanner.nextInt();
                    boolean ordreCroissant = (choixTri == 1);
                    trierEmployesParSalaire(ordreCroissant);
                    break;
                case 8:
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Veuillez choisir une option valide.");
            }

            if (choix != 8) {
                System.out.println("\nRetour au menu principal...\n");
            }

        } while (choix != 8);

        scanner.close();
    }
    // Méthode pour afficher le menu
    private static void printMenu() {
        System.out.println("\n=== Menu Gestion des Employés ===");
        System.out.println("1. Ajouter un employé");
        System.out.println("2. Modifier un employé");
        System.out.println("3. Supprimer un employé");
        System.out.println("4. Afficher tous les employés");
        System.out.println("5. Rechercher un employé");
        System.out.println("6. Calculer la masse salariale");
        System.out.println("7. Trier les employés");
        System.out.println("8. Quitter");
    }

    //Vérifier si l'ID Existe
    private static boolean idExiste(int id) {
        for (int i = 0; i < nombreEmployes; i++) {
            if (employes[i].getId() == id) {
                return true;
            }
        }
        return false;
    }
    // Méthode pour ajouter un employé
    private static void ajouterEmploye(Scanner scanner) {
        if (nombreEmployes >= MAX_EMPLOYES) {
            System.out.println("Le tableau des employés est plein !");
            return;
        }
        int id;
        while (true) {
            System.out.print("Entrez l'ID : ");
            id = scanner.nextInt();
            scanner.nextLine();
            if (idExiste(id)) {
                System.out.println("Cet ID est déjà utilisé. Veuillez entrer un autre ID .");
            } else {
                break;
            }
        }

        System.out.print("Entrez le nom : ");
        String nom = scanner.nextLine();

        System.out.print("Entrez le poste : ");
        String poste = scanner.nextLine();

        System.out.print("Entrez le salaire : ");
        double salaire = scanner.nextDouble();

        // Ajout de l'employé
        employes[nombreEmployes] = new Employe(id, nom, poste, salaire);
        nombreEmployes++;
        System.out.println("Employé ajouté avec succès !");
    }


    // Méthode pour modifier un employé
    //1.Entrer un ID d'employé et de modifier ses informations
    private static void modifierEmploye(Scanner scanner) {
        if (nombreEmployes == 0) {
            System.out.println("Aucun employé enregistré.");
            return;
        }
        //boolean employeExiste;
        int id;
        // Boucle pour redemander un ID tant qu'il est invalide
        while (true) {
            System.out.print("Entrez l'ID de l'employé à modifier : ");
            id = scanner.nextInt();
            scanner.nextLine();


            if (employeExiste(id)) {
                break;
            } else {
                System.out.println("Aucun employé trouvé avec l'ID " + id + ". Veuillez entrer un ID valide.");
            }
        }

        System.out.print("Nouveau nom : ");
        String nouveauNom = scanner.nextLine();

        System.out.print("Nouveau poste : ");
        String nouveauPoste = scanner.nextLine();

        System.out.print("Nouveau salaire : ");
        double nouveauSalaire = scanner.nextDouble();

        modifierEmploye(id, nouveauNom, nouveauPoste, nouveauSalaire);
    }
    //2.Modifie les informations d’un employé  dans le tableau employes
    private static void modifierEmploye(int id, String nouveauNom, String nouveauPoste, double nouveauSalaire) {
        for (int i = 0; i < nombreEmployes; i++) {
            if (employes[i].getId() == id) {
                employes[i].setNom(nouveauNom);
                employes[i].setPoste(nouveauPoste);
                employes[i].setSalaire(nouveauSalaire);
                System.out.println("\nEmployé modifié avec succès !");
                System.out.println("Nouvelles informations : " + employes[i]);
                return;
            }
        }
        System.out.println("Aucun employé trouvé avec l'ID " + id + ".");
    }

    // Méthode pour supprimer un employé
    //1.gérer l'entrée utilisateur
    private static void supprimerEmploye(Scanner scanner) {
        if (nombreEmployes == 0) {
            System.out.println("Aucun employé enregistré.");
            return;
        }

        int id;
        while (true) {
            System.out.print("Entrez l'ID de l'employé à supprimer : ");
            id = scanner.nextInt();
            scanner.nextLine();

            if (employeExiste(id)) {
                break;
            } else {
                System.out.println("Aucun employé trouvé avec l'ID " + id + ". Veuillez entrer un ID valide.");
            }
        }
        supprimerEmploye(id);
    }
    //2.méthode supprime un employé
    private static void supprimerEmploye(int id) {
        for (int i = 0; i < nombreEmployes; i++) {
            if (employes[i].getId() == id) {
                for (int j = i; j < nombreEmployes - 1; j++) {
                    employes[j] = employes[j + 1];
                }
                employes[nombreEmployes - 1] = null;
                nombreEmployes--;
                System.out.println("Employé avec ID " + id + " supprimé avec succès.");
                return;
            }
        }
        System.out.println("Erreur : Aucun employé trouvé avec l'ID " + id + ".");
    }

    //vérifier si un employé avec un ID donné existe dans le tableau employes.
    private static boolean employeExiste(int id) {
        for (int i = 0; i < nombreEmployes; i++) {
            if (employes[i].getId() == id) {
                return true;
            }
        }
        return false;
    }

    // Méthode pour afficher tous les employés
    private static void afficherEmployes() {
        if (nombreEmployes == 0) {
            System.out.println("Aucun employé enregistré.");
            return;
        }

        System.out.println("\n===Liste des Employés===");
        System.out.println("---------------------------------------------------");
        System.out.printf("| %-5s | %-20s | %-15s | %-10s |\n", "ID", "Nom", "Poste", "Salaire");
        System.out.println("---------------------------------------------------");

        for (int i = 0; i < nombreEmployes; i++) {
            Employe e = employes[i];
            System.out.printf("| %-5d | %-20s | %-15s | %-10.2f |\n",
                    e.getId(), e.getNom(), e.getPoste(), e.getSalaire());
        }
        System.out.println("---------------------------------------------------");
    }

    // Méthode pour chercher un employé
    private static void rechercherEmploye(String critere) {
        boolean trouve = false;

        System.out.println("\n=== Résultats de la recherche pour : " + critere + " ===");

        for (int i = 0; i < nombreEmployes; i++) {
            Employe e = employes[i];

            // Vérifier si le nom ou le poste contient le critère
            if (e.getNom().toLowerCase().contains(critere.toLowerCase()) ||
                    e.getPoste().toLowerCase().contains(critere.toLowerCase())) {

                System.out.println("ID : " + e.getId() +
                        " | Nom : " + e.getNom() +
                        " | Poste : " + e.getPoste() +
                        " | Salaire : " + e.getSalaire() + " ");
                trouve = true;
            }
        }

        if (!trouve) {
            System.out.println("Aucun employé trouvé avec le critère : " + critere);
        }
    }

    private static void rechercherEmploye(Scanner scanner) {
        System.out.print("Entrez un nom ou un poste à rechercher : ");
        String critere = scanner.nextLine();
        rechercherEmploye(critere);
    }

    // Méthode pour calculer la Masse Salariale des employés
    private static void calculerMasseSalariale() {
        if (nombreEmployes == 0) {
            System.out.println("Aucun employé enregistré.");
            return;
        }

        double masseSalariale = 0;

        for (int i = 0; i < nombreEmployes; i++) {
            masseSalariale += employes[i].getSalaire();
        }

        System.out.printf("La masse salariale totale est : %.2f\n", masseSalariale);
    }

    // Méthode pour trier les employés par salaire
    private static void trierEmployesParSalaire(boolean ordreCroissant) {
        if (nombreEmployes == 0) {
            System.out.println("Aucun employé enregistré.");
            return;
        }

        // Tri des employés par salaire
        Arrays.sort(employes, 0, nombreEmployes, new Comparator<Employe>() {
            @Override
            public int compare(Employe e1, Employe e2) {
                // Comparaison par salaire
                if (ordreCroissant) {
                    return Double.compare(e1.getSalaire(), e2.getSalaire());
                } else {
                    return Double.compare(e2.getSalaire(), e1.getSalaire());
                }
            }
        });

        // Affichage des employés triés
        afficherEmployes();
    }

}