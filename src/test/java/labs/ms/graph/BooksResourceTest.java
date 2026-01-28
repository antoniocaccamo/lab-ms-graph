/**
 * Lab MS Graph
 *
 *  @author antonio.caccamo
 *  @date 26 apr 2024
 */


package labs.ms.graph;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import java.security.Principal;
import labs.ms.graph.services.MsGraphClientService;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class BooksResourceTest {

  @InjectMock JsonWebToken accessToken;

  @InjectMock SecurityIdentity securityIdentity;

  @InjectMock MsGraphClientService msGraphClientService;

  private static final String TEST_USER = "test-user";

  @BeforeEach
  public void setup() {
    Principal principal = mock(Principal.class);
    when(principal.getName()).thenReturn(TEST_USER);
    when(securityIdentity.getPrincipal()).thenReturn(principal);
    when(accessToken.getRawToken()).thenReturn("dummy-token");
  }

  @Test
  public void testGetBooksAuthenticated() {
    given()
        .auth()
        .oauth2("dummy-token")
        .when()
        .get("/api/books")
        .then()
        .statusCode(200)
        .contentType(ContentType.JSON)
        .body("size()", is(2))
        .body("[0].author", is("io"))
        .body("[0].title", is("alla grande"))
        .body("[0].id", is(1979))
        .body("[1].author", is("anna"))
        .body("[1].title", is("con me"))
        .body("[1].id", is(1980));

    verify(securityIdentity).getPrincipal();
  }

  @Test
  public void testGetBooksUnauthenticated() {
    given().when().get("/api/books").then().statusCode(401);
  }

  @Test
  public void testSecurityIdentityLogging() {
    given().auth().oauth2("dummy-token").when().get("/api/books").then().statusCode(200);

    verify(securityIdentity, times(1)).getPrincipal();
    verify(securityIdentity.getPrincipal(), times(1)).getName();
  }
}
