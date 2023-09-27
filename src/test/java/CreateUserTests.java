import models.CreateUserModel;
import org.junit.jupiter.api.Test;
import services.GoRestService;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

int id;
public class CreateUserTests {

    @Test (Priority = 1)
    public void Users_CreateUsers_Success(){

        final CreateUserModel createUserModel = new CreateUserModel("NandiniRamesh", "Female", "test@test.com", "Active");
        id = GoRestService.createUser(createUserModel)
                .then()
                .statusCode(201)
                .body("id", notNullValue(),
                     "name", equalTo(createUserModel.getName()),
                     "gender", equalTo(createUserModel.getGender()),
                     "email", equalTo(createUserModel.getEmail()),
                     "status", equalTo(createUserModel.getStatus()));
                .jsonPath().getInt("id")
    }

    @Test
    public void unableToCreateExistingUser(){

        final CreateUserModel createUserModel = new CreateUserModel("NandiniRamesh", "Female", "test@test.com", "Active");
        id = GoRestService.createUser(createUserModel)
                .then()
                .statusCode(422)
                .body("field", hasItem("email"),
                "message", hasItem("has already been taken") )
    }


    @Test
    public void incorrectURI(){

        final CreateUserModel createUserModel = new CreateUserModel("NandiniRamesh", "Female", "test@test.com", "Active");
        id = GoRestService.createUser(createUserModel)
                .then()
                .statusCode(404)
    }

 @Test
    public void incorrectAccessToken(){

        final CreateUserModel createUserModel = new CreateUserModel("NandiniRamesh", "Female", "test@test.com", "Active");
        id = GoRestService.incorrectAccessToken(createUserModel)
                .then()
                .statusCode(401)
                 .body("message", hasItem("Invalid token") )
    }

    @Test
    public void missingAccessToken(){

        final CreateUserModel createUserModel = new CreateUserModel("NandiniRamesh", "Female", "test@test.com", "Active");
        id = GoRestService.missingAccessToken(createUserModel)
                .then()
                .statusCode(401)
                 .body("message", hasItem("Authentication failed") )
    }


// same test can be repeated with every mandaotry field missing like email / gender/ status
    @Test
    public void unableToCreateUserWithMissingFields(){

        final CreateUserModel createUserModel = new CreateUserModel("NandiniRamesh", "Female", "", "Active");
        id = GoRestService.createUser(createUserModel)
                .then()
                .statusCode(422)
                .body(hasItem(
                    allOf(
                         hasEntry("field", "email"),
                         hasEntry("message", "can't be blank")
                    ))
    )
    }

    @Test (Priority=2,dependsOnMethods = {"Users_CreateUsers_Success"})
    public void Users_UpdateUsers_Success(){

        final UpdateUserModel UpdateUserModel = new UpdateUserModel(id, "Nandini Ramesh", "female", "qatest@test.com", "Active");
        GoRestService.updateUser(updateUserModel)
                .then()
                .statusCode(200)
                .body("id", equalTo(id),
                     "name", equalTo(createUserModel.getName()),
                     "gender", equalTo(createUserModel.getGender()),
                     "email", equalTo(createUserModel.getEmail()),
                     "status", equalTo(createUserModel.getStatus()));
    }

    @Test (Priority=3,dependsOnMethods = {"Users_CreateUsers_Success"})
    public void Users_Fewattributes_Success(){

        final UpdateFewAttributeUserModel UpdateFewAttributeUserModel = new UpdateFewAttributeUserModel(id, "Nandini Mane");
        GoRestService.updateFewAttributesUser(UpdateFewAttributeUserModel)
                .then()
                .statusCode(200)
                .body("id", equalTo(id),
                     "name", equalTo(createUserModel.getName()),
                     "$", hasKey("gender")
                     "$", hasKey("email")
                     "$", hasKey("status"));
    }
}


