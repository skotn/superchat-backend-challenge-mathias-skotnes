package skotnapplications.superchatbackendchallenge;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import skotnapplications.superchatbackendchallenge.model.Contact;
import skotnapplications.superchatbackendchallenge.model.Message;
import skotnapplications.superchatbackendchallenge.repository.ContactRepository;
import skotnapplications.superchatbackendchallenge.repository.MessageRepository;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = {SuperIntegrationTest.Initializer.class})
public class SuperIntegrationTest {

    @Value("http://localhost:${local.server.port}")
    String baseUrl;

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    MessageRepository messageRepository;

    @Test
    public void repository_is_not_null() {
        contactRepository.save(new Contact("test", "test", 2L));
        Iterable<Contact> contacts = contactRepository.findAll();
        assertNotNull(contacts);
    }

    @Test
    public void save_message_adds_to_repo() throws JSONException {
        String testMessage = "TESTMESSAGE";
        JSONObject message = new JSONObject()
                .put("sender","1")
                .put("receiver","2")
                .put("content", testMessage);

        given()
                .body(message.toString())
                .contentType("application/json")
                .post(baseUrl + "/messages")
                .then()
                .assertThat()
                .statusCode(201);

        Iterable<Message> messages = messageRepository.findAll();
        assert(messages.iterator().next().getContent()).equals(testMessage);

    }

    @Test
    public void testGet_returns_200() {
        when().
                get(baseUrl + "/contacts").
                then()
                .statusCode(200);
    }

    @Test
    public void contextLoads() {
    }

    @ClassRule
    public static PostgreSQLContainer postgres = new PostgreSQLContainer("postgres")
            .withDatabaseName("postgres")
            .withUsername("integrationUser")
            .withPassword("testPass");


    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgres.getJdbcUrl(),
                    "spring.datasource.username=" + postgres.getUsername(),
                    "spring.datasource.password=" + postgres.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }
}
