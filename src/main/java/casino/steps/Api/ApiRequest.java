package casino.steps.Api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static casino.hooks.Hooks.confReader;
import static io.restassured.RestAssured.given;

public class ApiRequest {


    public static Response getToken(Method method, int statusCode, String baseAuth, String body) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(confReader.getValue("url.api") + "v2/oauth2/token")
                .log(LogDetail.ALL)
                .setContentType("application/json")
                .addHeader("Authorization", "Basic " + baseAuth)
                .setBody(body)
                .build();
        return doRequest(method, requestSpecification, statusCode);
    }

    public static Response registerOrLoginPlayer(Method method, int statusCode, String body, String bearer) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(confReader.getValue("url.api") + "v2/players")
                .log(LogDetail.ALL)
                .setContentType("application/json")
                .addHeader("Authorization", "Bearer " + bearer)
                .setBody(body)
                .build();
        return doRequest(method, requestSpecification, statusCode);
    }

    public static Response getProfile(Method method, int statusCode, String bearer, String id) {
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri(confReader.getValue("url.api") + "v2/players/" + id)
                .log(LogDetail.ALL)
                .addHeader("Authorization", "Bearer " + bearer)
                .build();
        return doRequest(method, requestSpecification, statusCode);
    }

    private static Response doRequest(Method method, RequestSpecification spec, int statusCode) {
        switch (method) {
            case GET: {
                return given().spec(spec).get().then().statusCode(statusCode).extract().response();
            }
            case POST: {
                return given().spec(spec).post().then().statusCode(statusCode).extract().response();
            }
            default: {
                throw new RuntimeException("Метод " + method + " не найден!");
            }
        }
    }
}
