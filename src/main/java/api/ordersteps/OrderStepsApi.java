package api.ordersteps;

import api.RestApi;
import constants.EndPoints;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import pojo.Ingredients;
import pojo.Order;

import java.util.List;

import static io.restassured.RestAssured.given;
import static randomdata.RandomData.RANDOM_HASH;

public class OrderStepsApi extends RestApi {

  @Step("Send GET request to /api/ingredients")
  public static ValidatableResponse getAllIngredients() {
    return given()
            .spec(getBaseSpec())
            .log().all()
            .get(EndPoints.INGREDIENTS_DATA_API)
            .then()
            .log().all();
  }

  @Step("Get data about ingredients as list")
  public Ingredients getAllIngredientsAsList() {
    return given()
            .spec(getBaseSpec())
            .log().all()
            .get(EndPoints.INGREDIENTS_DATA_API)
            .body()
            .as(Ingredients.class);
  }

  @Step("Add ingredient to the list")
  public void addIngredientToTheList(Ingredients ingredients, List<String> ingredient) {
    ingredient.add(ingredients.getData().get(1).get_id());
    ingredient.add(ingredients.getData().get(2).get_id());
    ingredient.add(ingredients.getData().get(3).get_id());
    ingredient.add(ingredients.getData().get(4).get_id());
    ingredient.add(ingredients.getData().get(5).get_id());
  }

  @Step("Add ingredient to the list with wrong hash")
  public void addIngredientToTheListWithWrongHash(Ingredients ingredients, List<String> ingredient) {
    ingredient.add(ingredients.getData().get(0).get_id().hashCode() + RANDOM_HASH);
    ingredient.add(ingredients.getData().get(1).get_id().hashCode() + RANDOM_HASH);
    ingredient.add(ingredients.getData().get(2).get_id().hashCode() + RANDOM_HASH);
  }

  @Step("Send GET request to /api/orders")
  public static ValidatableResponse getOrdersByAuthorization(String accessToken) {
    return given()
            .spec(getBaseSpec())
            .header("Authorization", accessToken)
            .log().all()
            .get(EndPoints.ORDER_API)
            .then()
            .log().all();
  }

  @Step("Send GET request to /api/orders")
  public static ValidatableResponse getOrdersWithoutAuthorization() {
    return given()
            .spec(getBaseSpec())
            .log().all()
            .get(EndPoints.ORDER_API)
            .then()
            .log().all();
  }

  @Step("Send GET request to /api/orders/all")
  public static ValidatableResponse getAllOrders() {
    return given()
            .spec(getBaseSpec())
            .log().all()
            .get(EndPoints.ORDER_API + "all")
            .then()
            .log().all();
  }

  @Step("Send POST request to /api/orders")
  public static ValidatableResponse createOrderByAuthorization(Order order, String accessToken) {
    return given()
            .spec(getBaseSpec())
            .header("Authorization", accessToken)
            .body(order)
            .log().all()
            .post(EndPoints.ORDER_API)
            .then()
            .log().all();
  }

  @Step("Send POST request to /api/orders")
  public static ValidatableResponse createOrderWithoutAuthorization(Order order) {
    return given()
            .spec(getBaseSpec())
            .body(order)
            .log().all()
            .post(EndPoints.ORDER_API)
            .then()
            .log().all();
  }
}