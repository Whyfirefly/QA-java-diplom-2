package user;

import api.usersteps.UserStepsApi;
import api.usersteps.UserStepsChecks;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.User;
import randomdata.UserGeneratorData;

public class UserCreateTest {
  public static String RANDOM_EMAIL = RandomStringUtils.randomAlphabetic(4);
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
      userStepsChecks.checkUserDeleteByValidCredentials(response);
    }
  }

  @Test
  @DisplayName("User create by valid credentials")
  public void userCreateByValidCredentials() {
    response = UserStepsApi.createUser(user);
    userStepsChecks.checkUserCreateByValidCredentialsSuccessful(response);
  }

  @Test
  @DisplayName("User create with empty email")
  public void userCreateIsEmptyEmail() {
    user.setEmail(null);
    response = UserStepsApi.createUser(user);
    userStepsChecks.checkUserCreateWithEmptyField(response);
    accessToken = response.extract().path("accessToken");
  }

  @Test
  @DisplayName("User create with empty password")
  public void userCreateIsEmptyPassword() {
    user.setPassword(null);
    response = UserStepsApi.createUser(user);
    userStepsChecks.checkUserCreateWithEmptyField(response);
  }

  @Test
  @DisplayName("User create with empty name")
  public void userCreateIsEmptyName() {
    user.setName(null);
    response = UserStepsApi.createUser(user);
    userStepsChecks.checkUserCreateWithEmptyField(response);
  }

  @Test
  @DisplayName("Repeated request by create user")
  public void repeatedRequestByCreateUser() {
    UserStepsApi.createUser(user);
    response = UserStepsApi.createUser(user);
    userStepsChecks.checkRepeatedRequestByCreateUser(response);
    accessToken = response.extract().path("accessToken");
  }
}
