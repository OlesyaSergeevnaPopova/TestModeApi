package ru.netology.testMode.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Locale;

import static io.restassured.RestAssured.given;


public class DataGenerator {
    private static final Faker faker = new Faker(new Locale("en"));

    private DataGenerator() {

    }

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static void sendRequest(RegistrationInfo  user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static class Registration {
        private Registration() {

        }
        public static String RandomLogin() {
            return faker.name().username();
        }

        public static String RandomPassword() {
            return faker.internet().password();
        }

        public static RegistrationInfo getUser(String status) {
            RegistrationInfo user = new RegistrationInfo(RandomLogin(), RandomPassword(), status);
            sendRequest(user);
            return user;
        }

        public static RegistrationInfo shouldGetRegisteredUser(String status) {
            RegistrationInfo shouldGetRegisteredUser = getUser(status);
            sendRequest(shouldGetRegisteredUser);
            return shouldGetRegisteredUser;
        }

    }

}
