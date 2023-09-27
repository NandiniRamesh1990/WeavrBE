package services;

import io.restassured.response.Response;
import models.CreateUserModel;


public class GoRestService extends BaseService {

    public static Response createUser(final CreateUserModel createUserModel){
        return defaultRequestSpecification()
                .body(createUserModel)
                .when()
                .post("/public/v1/users");
    }


    public static Response incorrectURI(final CreateUserModel createUserModel){
        return defaultRequestSpecification()
                .body(createUserModel)
                .when()
                .post("/public/v1/user");
    }

    public static Response incorrectAccessToken(final CreateUserModel createUserModel){
        return incorrectAccessTokenSpecification()
                .body(createUserModel)
                .when()
                .post("/public/v1/users");
    }

     public static Response missingAccessToken(final CreateUserModel createUserModel){
        return incorrectRequestSpecification()
                .body(createUserModel)
                .when()
                .post("/public/v1/users");
    }

        public static Response updateUser(final CreateUserModel updateUserModel){
        return defaultRequestSpecification()
                .body(updateUserModel)
                .when()
                .put(`/public/v2/users/${id}`);
    }

     public static Response updateFewAttributesUser(final CreateUserModel UpdateFewAttributeUserModel){
        return defaultRequestSpecification()
                .body(UpdateFewAttributeUserModel)
                .when()
                .patch(`/public/v2/users/${id}`);
    }
}
