/**
 * Lab MS Graph
 *
 *  @author antonio.caccamo
 *  @date 26 apr 2024
 */


package labs.ms.graph.resources;

import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * @auhtor antonio.caccamo on 2025-10-22 @ 17:20
 */
@Slf4j
@Authenticated
@Path("/api/books")
public class BooksResource {

  @Inject JsonWebToken accessToken;
  @Inject SecurityIdentity securityIdentity;

  private final List<BookRecord> books =
      Arrays.asList(
          new BookRecord("io", "alla grande", 1979), new BookRecord("anna", "con me", 1980));

  @GET
  @Produces("application/json")
  public List<BookRecord> getBooks() {

    log.info("call from {}", securityIdentity.getPrincipal().getName());
    return books;
  }

  private record BookRecord(String author, String title, Integer id) {}
}
