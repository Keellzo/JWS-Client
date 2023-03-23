import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class PetClientTest {
    private PetClient petClient;

    @BeforeEach
    void setUp() throws IOException {
        petClient = new PetClient("localhost", 8080);
    }

    @AfterEach
    void tearDown() throws IOException {
        petClient.close();
    }

    @Test
    void testGetAllPets() throws IOException {
        String response = petClient.getAllPets();
        assertNotNull(response, "The response should not be null");


    }

    @Test
    void testGetPetsBySpecies() throws IOException {
        String species = "dog";
        String response = petClient.getPetsBySpecies(species);
        assertNotNull(response, "The response should not be null");


    }

    @Test
    void testPostPet() throws IOException {
        String name = "Max";
        String species = "dog";
        String breed = "Labrador";
        int age = 3;
        String gender = "male";

        String response = petClient.postPet(name, species, breed, age, gender);
        assertNotNull(response, "The response should not be null");


    }
}
