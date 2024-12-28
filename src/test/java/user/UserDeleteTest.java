package user;

import api.usersteps.UserStepsApi;
import api.usersteps.UserStepsChecks;
import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import pojo.User;
import randomdata.UserGeneratorData;

@Epic("Delete user")
public class UserDeleteTest {
  private ValidatableResponse response;
  private UserStepsApi userStepsApi;
  private UserStepsChecks userStepsChecks;
  private User user;

  @Before
  public void setUp() {
    user = UserGeneratorData.getRandomUser();
    userStepsApi = new UserStepsApi();
    userStepsChecks = new UserStepsChecks();
  }

  @Test
  @DisplayName("User delete by valid credentials")
  public void userDeleteByValidCredentials() {
    response = UserStepsApi.createUser(user);
    String accessToken = response.extract().path("accessToken");
    response = UserStepsApi.deleteUser(StringUtils.substringAfter(accessToken, " "));
    userStepsChecks.checkUserDeleteByValidCredentials(response);

  }
}
