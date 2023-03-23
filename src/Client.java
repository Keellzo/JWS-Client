import java.io.IOException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = null;
        PetClient petClient = null;

        try {
            petClient = new PetClient("localhost", 8080);
            scanner = new Scanner(System.in);

            while (true) {
                System.out.println("--------------------------------------------");
                System.out.println("Choose an option:");
                System.out.println("1. GET all pets");
                System.out.println("2. GET pets by species");
                System.out.println("3. POST pets");
                System.out.println("4. Quit");
                System.out.println("--------------------------------------------");

                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline character

                if (option == 4) {
                    break;
                }

                String response;
                switch (option) {
                    case 1:
                        response = petClient.getAllPets();
                        System.out.println(response);
                        break;
                    case 2:
                        System.out.println("Enter species:");
                        String species = scanner.nextLine();
                        response = petClient.getPetsBySpecies(species);
                        System.out.println(response);
                        break;
                    case 3:
                        System.out.println("Enter pet name:");
                        String name = scanner.nextLine();
                        System.out.println("Enter pet species:");
                        species = scanner.nextLine();
                        System.out.println("Enter pet breed:");
                        String breed = scanner.nextLine();
                        System.out.println("Enter pet age:");
                        int age = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        System.out.println("Enter pet gender:");
                        String gender = scanner.nextLine();
                        response = petClient.postPet(name, species, breed, age, gender);
                        System.out.println(response);
                        break;
                    default:
                        System.out.println("Invalid option");
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
            if (petClient != null) {
                try {
                    petClient.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
    }
}
