package api.ordersteps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.CoreMatchers;

import static constants.AnswersWhenChecksDone.ORDER_GET_OR_USER_UPDATE_MESSAGE_UNAUTHORIZED;
import static constants.AnswersWhenChecksDone.ORDER_MESSAGE_BAD_REQUEST;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;

public class OrderStepsChecks {

  @Step("Check status code 200 and body response - success:true when we create order with authorization")
  public void checkOrderCreateSuccessWithAuthorization(ValidatableResponse response) {
    response
            .statusCode(SC_OK)
            .and().assertThat().body("success", CoreMatchers.equalTo(true))
            .and().assertThat().body("order.number", notNullValue())
            .and().assertThat().body("order._id", notNullValue());
  }

  @Step("Check status code 200 and body response - success:true when we create order without authorization")
  public void checkOrderCreateSuccessWithoutAuthorization(ValidatableResponse response) {
    response
            .statusCode(SC_OK)
            .and().assertThat().body("success", CoreMatchers.equalTo(true))
            .and().assertThat().body("order.number", notNullValue());
  }

  @Step("Check status code 400 and body response - success:false when we create order without ingredients")
  public void checkOrderCreateWithoutIngredients(ValidatableResponse response) {
    response
            .statusCode(SC_BAD_REQUEST)
            .and().assertThat().body("message", CoreMatchers.equalTo(ORDER_MESSAGE_BAD_REQUEST))
            .and().assertThat().body("success", CoreMatchers.equalTo(false));
  }


  @Step("Check status code 500 when we create order without authorization and change hash ingredient")
  public void CheckOrderCreateWithWrongHashIngredient(ValidatableResponse response) {
    response
            .statusCode(SC_INTERNAL_SERVER_ERROR);
  }

  @Step("Check status code 200 and body response - success:true when we get all ingredients of order")
  public void checkWhenWeGetAllIngredientsOfOrder(ValidatableResponse response) {
    response
            .statusCode(SC_OK).log().all()
            .and().assertThat().body("success", CoreMatchers.equalTo(true));
  }

  @Step("Check status code 200 and body response - success:true when get all orders")
  public void checkWhenWeGetAllOrders(ValidatableResponse response) {
    response
            .statusCode(SC_OK).log().all()
            .and().assertThat().body("success", CoreMatchers.equalTo(true));
  }

  @Step("Check status code 401 and body response when we get order without user authorization")
  public void checkWhenWeGetOrdersWithoutUserAuthorization(ValidatableResponse response) {
    response
            .statusCode(SC_UNAUTHORIZED).log().all()
            .and().assertThat().body("success", CoreMatchers.equalTo(false))
            .and().assertThat().body("message", CoreMatchers.equalTo(ORDER_GET_OR_USER_UPDATE_MESSAGE_UNAUTHORIZED)).log().all();
  }

}
