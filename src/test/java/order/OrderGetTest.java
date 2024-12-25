package order;

import api.order_steps.OrderStepsApi;
import api.order_steps.OrderStepsChecks;
import api.user_steps.UserStepsApi;
import api.user_steps.UserStepsChecks;
import io.qameta.allure.Epic;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pojo.Ingredients;
import pojo.Order;
import pojo.User;
import random_data.UserGeneratorData;

import java.util.ArrayList;
import java.util.List;


@Epic("Get order and ingredients")
public class OrderGetTest {
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
    Ingredients ingredients = orderStepsApi.getAllIngredientsAsList();
    orderStepsApi.addIngredientToTheList(ingredients,ingredient);
  }

  @After
  public void tearDown() {
    if (accessToken != null) {
      response = UserStepsApi.deleteUser(StringUtils.substringAfter(accessToken, " "));
      userStepsChecks.CheckUserDeleteByValidCredentials(response);
    }
  }

  @Test
  @DisplayName("Get all ingredients")
  public void getAllIngredients() {
    response = OrderStepsApi.getAllIngredients();
    orderStepsChecks.checkWhenWeGetAllIngredientsOfOrder(response);
  }

  @Test
  @DisplayName("Get all orders")
  public void getAllOrders() {
    response = OrderStepsApi.createOrderWithoutAuthorization(order);
    response = OrderStepsApi.getAllOrders();
    orderStepsChecks.checkWhenWeGetAllOrders(response);
  }

  @Test
  @DisplayName("Get order by authorization user")
  public void getOrderByAuthorizationUser() {
    response = UserStepsApi.createUser(user);
    accessToken = response.extract().path("accessToken");
    response = UserStepsApi.loginUser(user, accessToken);
    response = OrderStepsApi.createOrderByAuthorization(order, accessToken);
    response = OrderStepsApi.getOrdersByAuthorization(accessToken);
    orderStepsChecks.checkWhenWeGetAllOrders(response);
  }

  @Test
  @DisplayName("Get order without authorization user")
  public void getOrderWithoutAuthorizationUser() {
    response = OrderStepsApi.createOrderWithoutAuthorization(order);
    response = OrderStepsApi.getOrdersWithoutAuthorization();
    orderStepsChecks.checkWhenWeGetOrdersWithoutUserAuthorization(response);
  }

}
