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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Epic("Get user")
public class UserGetTest {
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
  @DisplayName("Get user by valid credentials")
  public void userGetByValidCredentials() {
    accessToken = response.extract().path("accessToken");
    response = UserStepsApi.getUser(accessToken);
    userStepsChecks.checkUserGetByValidCredentials(response);
    String email = response.extract().path("user.email");
    String name = response.extract().path("user.name");


    assertThat("Email not equal", email, equalTo(user.getEmail()));
    assertThat("Name not equal", name, equalTo(user.getName()));
  }
}
