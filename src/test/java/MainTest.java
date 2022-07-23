import com.http.pet.HttpClientPet;
import com.model.*;
import com.util.Util;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Clock;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    private static final String ADD_PET = "https://petstore.swagger.io/v2/pet/";
    private static final String GET_PET_BY_ID = "https://petstore.swagger.io/v2/pet/";
    private static final String GET_PET_BY_STATUS = "https://petstore.swagger.io/v2/pet/findByStatus";

    @Test
    void pet() throws IOException, InterruptedException {
        Category cat = new Category(1L, "cat");
        Pet summer = new Pet(2L,
                cat,
                "Sammer",
                List.of("dsadas"),
                List.of(new Tag(1, "cat")),
                Pet.Status.AVAILABLE.getStatus());

        Pet summerUpd = new Pet(2L,
                cat,
                "Dendi",
                List.of("dsadas"),
                List.of(new Tag(1, "cat")),
                Pet.Status.AVAILABLE.getStatus());

        Order order = new Order(1L, 1L, 10, Clock.systemUTC().instant().toString(), "placed", true);
        assertInstanceOf(Order.class, order);

        //TODO: explain tests
        Pet test = HttpClientPet.sendPost(ADD_PET, summer);
        System.out.println("POST new PET: " + test);

        final Pet testFind = HttpClientPet.sendGet(GET_PET_BY_ID, 2L);
        System.out.println("GET Pet by id: " + testFind);

        Path path = Paths.get("src/main/resources/images/123.png");
        //Util.printPath(path);
        ApiResponse testImage = HttpClientPet.sendPostImage(ADD_PET, 2L, path);
        System.out.println(testImage);

        Pet testUpdate = HttpClientPet.sendPut(ADD_PET, summerUpd);

        System.out.println(testUpdate);

        System.out.println(Util.getQueryURI(GET_PET_BY_STATUS, new String[]{"asdas"}));

        List<Pet> petsTest = HttpClientPet.sendGetWithListOfResults(GET_PET_BY_STATUS, new String[]{"sold"});
        System.out.println(petsTest.size());

        ApiResponse testUpdatePet = HttpClientPet.sendUpdate(GET_PET_BY_ID, 2L, "Bula", "test");
        System.out.println(testUpdatePet);

        ApiResponse testDelete = HttpClientPet.sendDelete(GET_PET_BY_ID, 2L);
        System.out.println(testDelete);
    }
}
