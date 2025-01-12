package order;

import api.ordersteps.OrderStepsApi;
import api.ordersteps.OrderStepsChecks;
import api.usersteps.UserStepsApi;
import api.usersteps.UserStepsChecks;
import io.qameta.allure.Epic;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Ingredients;
import pojo.Order;
import pojo.User;
import randomdata.UserGeneratorData;

import java.util.ArrayList;
import java.util.List;

@Epic("Create order")
public class OrderCreateTest {
  private ValidatableResponse response;
  private User user;
  private Order order;
  private UserStepsApi userStepsApi;
  private UserStepsChecks userStepsChecks;
  private OrderStepsApi orderStepsApi;
  private OrderStepsChecks orderStepsChecks;
  private List<String> ingredient;
  private String accessToken;

  @Before
  public void setUp() {
    user = UserGeneratorData.getRandomUser();
    userStepsApi = new UserStepsApi();
    userStepsChecks = new UserStepsChecks();
    orderStepsApi = new OrderStepsApi();
    orderStepsChecks = new OrderStepsChecks();
    ingredient = new ArrayList<>();
    order = new Order(ingredient);
  }

  @After
  public void tearDown() {
    if (accessToken != null) {
      response = UserStepsApi.deleteUser(StringUtils.substringAfter(accessToken, " "));
      userStepsChecks.checkUserDeleteByValidCredentials(response);
    }
  }

  @Test
  @DisplayName("Create order by authorization")
  public void orderCreateByAuthorization() {
    Ingredients ingredients = orderStepsApi.getAllIngredientsAsList();
    orderStepsApi.addIngredientToTheList(ingredients,ingredient);
    response = UserStepsApi.createUser(user);
    accessToken = response.extract().path("accessToken");
    response = UserStepsApi.loginUser(user, accessToken);
    response = OrderStepsApi.createOrderByAuthorization(order, accessToken);
    orderStepsChecks.checkOrderCreateSuccessWithAuthorization(response);
  }

  @Test
  @DisplayName("Create order without authorization")
  public void orderCreateWithoutAuthorization() {
    Ingredients ingredients = orderStepsApi.getAllIngredientsAsList();
    orderStepsApi.addIngredientToTheList(ingredients,ingredient);
    response = OrderStepsApi.createOrderWithoutAuthorization(order);
    orderStepsChecks.checkOrderCreateSuccessWithoutAuthorization(response);
  }

  @Test
  @DisplayName("Create order without authorization and ingredients")
  public void orderCreateWithoutAuthorizationAndIngredients() {
    response = OrderStepsApi.createOrderWithoutAuthorization(order);
    orderStepsChecks.checkOrderCreateWithoutIngredients(response);
  }

  @Test
  @DisplayName("Create order without ingredients with authorization")
  public void orderCreateWithoutIngredientsWithAuthorization() {
    response = UserStepsApi.createUser(user);
    accessToken = response.extract().path("accessToken");
    response = OrderStepsApi.createOrderByAuthorization(order, accessToken);
    orderStepsChecks.checkOrderCreateWithoutIngredients(response);
  }

  @Test
  @DisplayName("Create order without authorization and with incorrect ingredient's hash")
  public void orderCreateWithoutAuthorizationWithWrongHash() {
    Ingredients ingredients = orderStepsApi.getAllIngredientsAsList();
    orderStepsApi.addIngredientToTheListWithWrongHash(ingredients,ingredient);
    response = OrderStepsApi.createOrderWithoutAuthorization(order);
    orderStepsChecks.checkOrderCreateWithWrongHashIngredient(response);
  }

  @Test
  @DisplayName("Create order with authorization and with incorrect ingredient's hash")
  public void orderCreateWithAuthorizationWithWrongHash() {
    Ingredients ingredients = orderStepsApi.getAllIngredientsAsList();
    orderStepsApi.addIngredientToTheListWithWrongHash(ingredients,ingredient);
    response = UserStepsApi.createUser(user);
    accessToken = response.extract().path("accessToken");
    response = OrderStepsApi.createOrderByAuthorization(order, accessToken);
    orderStepsChecks.checkOrderCreateWithWrongHashIngredient(response);
  }

}
