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


@Epic("Update user")
public class UserUpdateTest {
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
  @DisplayName("Update user by authorization")
  public void updateUserByAuthorization() {
    response = UserStepsApi.createUser(user);
    accessToken = response.extract().path("accessToken");
    response = UserStepsApi.loginUser(user, accessToken);
    response = UserStepsApi.updateUserByAuthorization(UserGeneratorData.getRandomUser(), accessToken);
    userStepsChecks.CheckUpdateUserByAuthorization(response);
  }

  @Test
  @DisplayName("Update user without authorization")
  public void updateUserWithoutAuthorization() {
    response = UserStepsApi.createUser(user);
    accessToken = response.extract().path("accessToken");
    response = UserStepsApi.updateUserWithoutAuthorization(UserGeneratorData.getRandomUser());
    userStepsChecks.CheckUpdateUserWithoutAuthorization(response);
  }
}