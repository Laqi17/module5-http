import com.http.HttpClientUtil;
import com.model.Category;
import com.model.Pet;
import com.model.Tag;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    private static final String ADD_PET = "https://petstore.swagger.io/v2/pet/";
    private static final String GET_PET_BY_ID = "https://petstore.swagger.io/v2/pet/";

    public static void main(String[] args) throws IOException, InterruptedException {
        Category cat = new Category(1, "cat");
        Pet summer = new Pet(2,
                cat,
                "Sammer",
                List.of("dsadas"),
                List.of(new Tag(1, "cat")),
                Pet.Status.AVAILABLE.getStatus());

        Pet test = HttpClientUtil.sendPost(URI.create(ADD_PET), summer);
        System.out.println("POST new PET: " + test);

        final Pet testFind = HttpClientUtil.sendGet(URI.create(GET_PET_BY_ID), 2);
        System.out.println("GET Pet by id: " + testFind);

        Path path = Paths.get("src/main/resources/images/123.png");
        //Util.printPath(path);
        String testImage = HttpClientUtil.sendPostImage(URI.create(ADD_PET), 2, path);
        System.out.println(testImage);


    }

}
