package api.user_steps;

import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;

import static constants.AnswersWhenChecksDone.*;

import static org.apache.http.HttpStatus.*;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static org.hamcrest.Matchers.notNullValue;


public class UserStepsChecks {

  @Step("Check status code 200 and body response - success:true when we create user by valid credentials")
  public void CheckUserCreateByValidCredentialsSuccessful(ValidatableResponse response) {
    response
            .statusCode(SC_OK)
            .and().assertThat().body("success", CoreMatchers.equalTo(true));
  }

  @Step("Check status code 403 and body response - success:false when we create user with empty email/password/name")
  public void CheckUserCreateWithEmptyField(ValidatableResponse response) {
    response
            .statusCode(SC_FORBIDDEN)
            .and().assertThat().body("success", CoreMatchers.equalTo(false))
            .and().assertThat().body("message", CoreMatchers.equalTo(USER_CREATE_MESSAGE_EMPTY_FIELD));
  }

  @Step("Check status code 403 and body response - success:false when we repeat request by create user")
  public void CheckRepeatedRequestByCreateUser(ValidatableResponse response) {
    response
            .statusCode(SC_FORBIDDEN)
            .and().assertThat().body("success", CoreMatchers.equalTo(false))
            .and().assertThat().body("message", CoreMatchers.equalTo(USER_MESSAGE_ALREADY_EXIST));
  }

  @Step("Check status code 202 and body response - success:true when we delete user")
  public void CheckUserDeleteByValidCredentials(ValidatableResponse response) {
    response
            .statusCode(SC_ACCEPTED)
            .and().assertThat().body("success", CoreMatchers.equalTo(true))
            .and().assertThat().body("message", CoreMatchers.equalTo(USER_SUCCESSFULLY_REMOVED_MESSAGE));
  }

  @Step("Check status code 200 and body response - success:true when get user")
  public void CheckUserGetByValidCredentials(ValidatableResponse response) {
    response
            .statusCode(SC_OK)
            .and().assertThat().body("success", CoreMatchers.equalTo(true));
  }

  @Step("Check status code 200 and body response - success:true when user login ")
  @DisplayName("User login by valid credentials")
  public void CheckUserLoginByValidCredentialsSuccess(ValidatableResponse response) {
    response
            .statusCode(SC_OK)
            .and().assertThat().body("success", CoreMatchers.equalTo(true))
            .and().assertThat().body("accessToken", notNullValue());
  }

  @Step("Check status code 200 and body response - success:true when user logout ")
  public void CheckUserLogoutByValidCredentials(ValidatableResponse response) {
    response
            .statusCode(SC_OK)
            .and().assertThat().body("success", CoreMatchers.equalTo(true))
            .and().assertThat().body("message", CoreMatchers.equalTo(MESSAGE_LOGOUT_SUCCESS));
  }

  @Step("Check status code 401 and body response - success:false when user login without email or password ")
  public void CheckUserLoginWithEmptyEmailOrPass(ValidatableResponse response) {
    response
            .statusCode(SC_UNAUTHORIZED)
            .and().assertThat().body("success", CoreMatchers.equalTo(false))
            .and().assertThat().body("message", CoreMatchers.equalTo(LOGIN_MESSAGE_UNAUTHORIZED));
  }

  @Step("Check status code 200 and body response - success:true when we update user by authorization")
  public void CheckUpdateUserByAuthorization(ValidatableResponse response) {
    response
            .statusCode(SC_OK)
            .and().assertThat().body("success", CoreMatchers.equalTo(true));
  }

  @Step("Check status code 401 and body response - success:false when we update user without authorization")
  public void CheckUpdateUserWithoutAuthorization(ValidatableResponse response) {
    response
            .statusCode(SC_UNAUTHORIZED)
            .and().assertThat().body("success", CoreMatchers.equalTo(false))
            .and().assertThat().body("message", CoreMatchers.equalTo(ORDER_GET_OR_USER_UPDATE_MESSAGE_UNAUTHORIZED));
  }
}
