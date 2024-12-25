package user;

import api.user_steps.UserStepsApi;
import api.user_steps.UserStepsChecks;
import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.User;
import random_data.UserGeneratorData;

@Epic("Login user")
public class UserLoginTest {
  private ValidatableResponse response;
  private UserStepsApi userStepsApi;
  private UserStepsChecks userStepsChecks;
  private User user;
  private String accessToken;

  @Before
  public void setUp() {
    user = UserGeneratorData.getRandomUser();
    userStepsApi = new UserStepsApi();
    userStepsChecks = new UserStepsChecks();
  }

  @After
  public void tearDown() {
    if (accessToken != null) {
      response = UserStepsApi.deleteUser(StringUtils.substringAfter(accessToken, " "));
      userStepsChecks.CheckUserDeleteByValidCredentials(response);
    }
  }

  @Test
  @DisplayName("User login by valid credentials")
  public void userLoginByValidCredentials() {
    response = UserStepsApi.createUser(user);
    accessToken = response.extract().path("accessToken");
    response = UserStepsApi.loginUser(user, accessToken);
    userStepsChecks.CheckUserGetByValidCredentials(response);
  }

  @Test
  @DisplayName("User login with empty email")
  public void userLoginByEmptyEmail() {
    response = UserStepsApi.createUser(user);
    accessToken = response.extract().path("accessToken");
    user.setEmail(null);
    response = UserStepsApi.loginUser(user, accessToken);
    userStepsChecks.CheckUserLoginWithEmptyEmailOrPass(response);
  }

  @Test
  @DisplayName("User login with empty password")
  public void userLoginByEmptyPassword() {
    response = UserStepsApi.createUser(user);
    accessToken = response.extract().path("accessToken");
    user.setPassword(null);
    response = UserStepsApi.loginUser(user, accessToken);
    userStepsChecks.CheckUserLoginWithEmptyEmailOrPass(response);

  }
}
