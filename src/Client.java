import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            Socket socket = new Socket("localhost", 8080);

            InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

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

                switch (option) {
                    case 1 -> {
                        // GET all pets
                        String getRequest = "GET / HTTP/1.1\r\n";
                        getRequest += "Connection: keep-alive\r\n"; // Add this line
                        getRequest += "\r\n";
                        bufferedWriter.write(getRequest);
                        bufferedWriter.flush();
                    }
                    case 2 -> {
                        // GET pets by species
                        System.out.println("Enter species:");
                        String species = scanner.nextLine();
                        String getRequest = "GET /pets?species=" + species + " HTTP/1.1\r\n";
                        getRequest += "Connection: keep-alive\r\n"; // Add this line
                        getRequest += "\r\n";
                        bufferedWriter.write(getRequest);
                        bufferedWriter.flush();
                    }
                    case 3 -> {
                        // POST pets
                        System.out.println("Enter pet name:");
                        String name = scanner.nextLine();
                        System.out.println("Enter pet species:");
                        String species = scanner.nextLine();
                        System.out.println("Enter pet breed:");
                        String breed = scanner.nextLine();
                        System.out.println("Enter pet age:");
                        int age = scanner.nextInt();
                        scanner.nextLine(); // Consume newline character
                        System.out.println("Enter pet gender:");
                        String gender = scanner.nextLine();

                        String jsonInput = String.format("{\"name\": \"%s\", \"species\": \"%s\", \"breed\": \"%s\", \"age\": %d, \"gender\": \"%s\"}", name, species, breed, age, gender);

                        String postRequest = "POST / HTTP/1.1\r\n";
                        postRequest += "Content-Type: application/json\r\n";
                        postRequest += "Content-Length: " + jsonInput.length() + "\r\n";
                        postRequest += "Connection: keep-alive\r\n"; // Change this line to "Connection: keep-alive"
                        postRequest += "\r\n";
                        postRequest += jsonInput;
                        bufferedWriter.write(postRequest);
                        bufferedWriter.flush();
                    }
                    default -> System.out.println("Invalid option");
                }

                // Read and display the response
                String line;
                StringBuilder response = new StringBuilder();

                int contentLength = -1;
                while ((line = bufferedReader.readLine()) != null) {
                    if (line.startsWith("Content-Length:")) {
                        contentLength = Integer.parseInt(line.substring("Content-Length:".length()).trim());
                    }

                    if (line.isEmpty()) {
                        break;
                    }
                }

                if (contentLength > 0) {
                    char[] contentBuffer = new char[contentLength];
                    bufferedReader.read(contentBuffer);
                    response.append(new String(contentBuffer));
                }
                System.out.println(response);

                // Clear the response StringBuilder for the next iteration
                response.setLength(0);
            }

            bufferedReader.close();
            bufferedWriter.close();
            inputStreamReader.close();
            outputStreamWriter.close();
            socket.close();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}