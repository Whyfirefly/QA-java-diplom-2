package user;

import api.usersteps.UserStepsApi;
import api.usersteps.UserStepsChecks;
import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.User;
import randomdata.UserGeneratorData;


@Epic("Logout user")
public class UserLogoutTest {
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
    response = UserStepsApi.createUser(user);
  }

  @After
  public void tearDown() {
    if (accessToken != null) {
      response = UserStepsApi.deleteUser(StringUtils.substringAfter(accessToken, " "));
      userStepsChecks.checkUserDeleteByValidCredentials(response);
    }
  }

  @Test
  @DisplayName("User logout by valid credentials")
  public void userLogoutByValidCredentials() {
    accessToken = response.extract().path("accessToken");
    response = UserStepsApi.loginUser(user, accessToken);
    String refreshToken = response.extract().path("refreshToken");
    refreshToken = "{\"token\":\"" + refreshToken + "\"}";
    response = UserStepsApi.logoutUser(refreshToken);
    userStepsChecks.checkUserLogoutByValidCredentials(response);
  }
}

