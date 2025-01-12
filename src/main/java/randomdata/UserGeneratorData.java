package randomdata;

import io.qameta.allure.Allure;
import org.apache.commons.lang3.RandomStringUtils;
import pojo.User;

public class UserGeneratorData {
  public static User getRandomUser() {
    String name = "Jora" + RandomStringUtils.randomAlphabetic(6);
    String email = name.toLowerCase() + "@mail.ru";
    String password = "YS" + RandomStringUtils.randomAlphabetic(9);

    Allure.addAttachment("Email : ", email);
    Allure.addAttachment("Password : ", password);
    Allure.addAttachment("Name : ", name);

    return new User(email, password, name);
  }
}
