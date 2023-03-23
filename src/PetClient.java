import java.io.*;
import java.net.Socket;

public class PetClient {
    private final BufferedReader bufferedReader;
    private final BufferedWriter bufferedWriter;

    public PetClient(String host, int port) throws IOException {
        Socket socket = new Socket(host, port);
        InputStreamReader inputStreamReader = new InputStreamReader(socket.getInputStream());
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        bufferedReader = new BufferedReader(inputStreamReader);
        bufferedWriter = new BufferedWriter(outputStreamWriter);
    }

    public String getAllPets() throws IOException {
        String getRequest = "GET / HTTP/1.1\r\n";
        getRequest += "Connection: keep-alive\r\n";
        getRequest += "\r\n";
        bufferedWriter.write(getRequest);
        bufferedWriter.flush();
        return readResponse();
    }

    public String getPetsBySpecies(String species) throws IOException {
        String getRequest = "GET /pets?species=" + species + " HTTP/1.1\r\n";
        getRequest += "Connection: keep-alive\r\n";
        getRequest += "\r\n";
        bufferedWriter.write(getRequest);
        bufferedWriter.flush();
        return readResponse();
    }

    public String postPet(String name, String species, String breed, int age, String gender) throws IOException {
        String jsonInput = String.format("{\"name\": \"%s\", \"species\": \"%s\", \"breed\": \"%s\", \"age\": %d, \"gender\": \"%s\"}", name, species, breed, age, gender);
        String postRequest = "POST / HTTP/1.1\r\n";
        postRequest += "Content-Type: application/json\r\n";
        postRequest += "Content-Length: " + jsonInput.length() + "\r\n";
        postRequest += "Connection: keep-alive\r\n";
        postRequest += "\r\n";
        postRequest += jsonInput;
        bufferedWriter.write(postRequest);
        bufferedWriter.flush();
        return readResponse();
    }

    private String readResponse() throws IOException {
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
        return response.toString();
    }

    public void close() throws IOException {
        bufferedReader.close();
        bufferedWriter.close();
    }
}
