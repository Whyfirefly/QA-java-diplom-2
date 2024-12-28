package api.usersteps;

import api.RestApi;
import constants.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.User;

import static io.restassured.RestAssured.given;

public class UserStepsApi extends RestApi {

  @Step("Send GET request to /api/auth/user")
  public static ValidatableResponse getUser(String accessToken) {
    return given()
            .spec(getBaseSpec())
            .header("Authorization", accessToken)
            .log().all()
            .get(EndPoints.USER_AUTHENTICATION_API + "user")
            .then()
            .log().all();
  }

  @Step("Send POST request to /api/auth/register")
  public static ValidatableResponse createUser(User user) {
    return given()
            .spec(getBaseSpec())
            .body(user)
            .log().all()
            .post(EndPoints.USER_AUTHENTICATION_API + "register")
            .then()
            .log().all();
  }

  @Step("Send POST request to /api/auth/login")
  public static ValidatableResponse loginUser(User user, String accessToken) {
    return given()
            .spec(getBaseSpec())
            .auth().oauth2(accessToken)
            .body(user)
            .log().all()
            .post(EndPoints.USER_AUTHENTICATION_API + "login")
            .then()
            .log().all();
  }

  @Step("Send POST request to /api/auth/logout")
  public static ValidatableResponse logoutUser(String refreshToken) {
    return given()
            .spec(getBaseSpec())
            .body(refreshToken)
            .log().all()
            .post(EndPoints.USER_AUTHENTICATION_API + "logout")
            .then()
            .log().all();
  }

  @Step("Send DELETE request to /api/auth/user")
  public static ValidatableResponse deleteUser(String accessToken) {
    return given()
            .spec(getBaseSpec())
            .auth().oauth2(accessToken)
            .log().all()
            .delete(EndPoints.USER_DELETION)
            .then()
            .log().all();
  }

  @Step("Send PATCH request to /api/auth/user")
  public static ValidatableResponse updateUserByAuthorization(User user, String accessToken) {
    return given()
            .spec(getBaseSpec())
            .header("Authorization", accessToken)
            .body(user)
            .log().all()
            .patch(EndPoints.USER_AUTHENTICATION_API + "user")
            .then()
            .log().all();
  }

  @Step("Send PATCH request to /api/auth/user")
  public static ValidatableResponse updateUserWithoutAuthorization(User user) {
    return given()
            .spec(getBaseSpec())
            .body(user)
            .log().all()
            .patch(EndPoints.USER_AUTHENTICATION_API + "user")
            .then()
            .log().all();
  }
}

