import com.exceptions.CustomException;
import com.http.store.HttpClientStore;
import com.model.*;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

public class Main {
    private static final String ADD_PET = "https://petstore.swagger.io/v2/pet/";
    private static final String GET_PET_BY_ID = "https://petstore.swagger.io/v2/pet/";
    private static final String GET_PET_BY_STATUS = "https://petstore.swagger.io/v2/pet/findByStatus";
    private static final String ADD_ORDER = "https://petstore.swagger.io/v2/store/order/";

    public static void main(String[] args) throws IOException, InterruptedException, CustomException {
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
        System.out.println(Clock.systemUTC().instant());

//        Pet test = HttpClientPet.sendPost(ADD_PET, summer);
//        System.out.println("POST new PET: " + test);
//
//        final Pet testFind = HttpClientPet.sendGet(GET_PET_BY_ID, 2L);
//        System.out.println("GET Pet by id: " + testFind);
//
//        Path path = Paths.get("src/main/resources/images/123.png");
//        //Util.printPath(path);
//        ApiResponse testImage = HttpClientPet.sendPostImage(ADD_PET, 2L, path);
//        System.out.println(testImage);
//
//        Pet testUpdate = HttpClientPet.sendPut(ADD_PET, summerUpd);
//
//        System.out.println(testUpdate);
//
//        System.out.println(Util.getQueryURI(GET_PET_BY_STATUS, new String[]{"asdas"}));
//
//        List<Pet> petsTest = HttpClientPet.sendGetWithListOfResults(GET_PET_BY_STATUS, new String[]{"sold"});
//        System.out.println(petsTest.size());
//
//        ApiResponse testUpdatePet = HttpClientPet.sendUpdate(GET_PET_BY_ID, 2L, "Bula", "test");
//        System.out.println(testUpdatePet);
//
//        ApiResponse testDelete = HttpClientPet.sendDelete(GET_PET_BY_ID, 2L);
//        System.out.println(testDelete);

        Order testOrder = HttpClientStore.sendPost(ADD_ORDER, order);
        System.out.println(testOrder);

        Order testGet = HttpClientStore.sendGet(ADD_ORDER, 3L);
        System.out.println(testGet);

        ApiResponse testDeleteOrder = HttpClientStore.sendDelete(ADD_ORDER, 22313L);
        System.out.println(testDeleteOrder);
    }

}
