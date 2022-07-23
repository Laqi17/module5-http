import com.exceptions.CustomException;
import com.http.store.HttpClientStore;
import com.model.*;

import java.io.IOException;
import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

public class Main {

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

        Order testOrder = HttpClientStore.sendPost(ADD_ORDER, order);
        System.out.println(testOrder);

        Order testGet = HttpClientStore.sendGet(ADD_ORDER, 3L);
        System.out.println(testGet);

        ApiResponse testDeleteOrder = HttpClientStore.sendDelete(ADD_ORDER, 22313L);
        System.out.println(testDeleteOrder);
    }

}
