import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.IOException;

public class S {

    public static void main(String[] args) {
        // Définition des variables pour la commande
        String domain = "domain.pddl";
        String problem = "01.pddl";
        String lien = "./src/test/resources/benchmarks/pddl/";
        String lien2 = "ipc2000/blocks/strips-typed/";
        boolean mctsOuAsp = true;
        String mcts = "fr.uga.pddl4j.examples.mcts.MCTS";
        String aStar = "fr.uga.pddl4j.planners.ASP";

        // Construction de la commande en fonction de mctsOuAsp
        String command = "java -cp \"classes;lib/pddl4j-4.0.0.jar\" " +
                (mctsOuAsp ? mcts : aStar) + " " + lien + lien2 + domain + " " + lien + lien2 + "p0" + problem;

        try {
            // Utilisation de ProcessBuilder pour exécuter la commande dans PowerShell
            ProcessBuilder processBuilder = new ProcessBuilder("powershell", "-Command", command);
            System.out.println("Exécution de la commande: " + command);

            // Lancement de la commande
            Process process = processBuilder.start();

            // Récupération de la sortie de la commande
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            // Attendre que le processus se termine
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                System.out.println("La commande a été exécutée avec succès!");

                // Analyse de la sortie pour trouver "Total time" et la ligne précédant "Time spent"
                String[] lines = output.toString().split("\n");
                String totalTimeLine = null;
                String lineBeforeTimeSpent = null;

                for (int i = 0; i < lines.length; i++) {
                    if (lines[i].contains("Total time")) {
                        totalTimeLine = lines[i];
                    }
                    if (i > 0 && lines[i].contains("Time spent")) {
                        lineBeforeTimeSpent = lines[i - 1];
                    }
                }

                // Écriture dans le fichier stat.txt
                try (BufferedWriter writer = new BufferedWriter(new FileWriter("stat.txt"))) {
                    writer.write(mctsOuAsp ? "MCTS\n" : "A*\n");
                    writer.write("Domaine: " + domain + ", Problème: " + problem + "\n");
                    writer.write("Total time: " + (totalTimeLine != null ? totalTimeLine : "Non trouvé") + "\n");
                    writer.write("Nombre d'actions: " + (lineBeforeTimeSpent != null ? lineBeforeTimeSpent : "Non trouvé") + "\n");
                    System.out.println("Les statistiques ont été écrites dans stat.txt.");
                } catch (IOException e) {
                    System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
                }
                
            } else {
                System.out.println("Erreur lors de l'exécution de la commande, code de sortie : " + exitCode);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
