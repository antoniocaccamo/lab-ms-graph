/**
 * Lab MS Graph
 *
 *  @author antonio.caccamo
 *  @date 26 apr 2024
 */


package labs.ms.graph.resources;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import java.time.OffsetDateTime;
import java.util.Collection;
import labs.ms.graph.records.ReportApplicationPasswordCredentialsRecord;
import labs.ms.graph.services.MsGraphClientService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Slf4j
@Authenticated
@Path("/reports")
public class ReportResource {

  @Inject JsonWebToken accessToken;
  @Inject SecurityIdentity securityIdentity;

  @Inject MsGraphClientService msGraphClientService;

  @CheckedTemplate
  public static class Templates {

    public static native TemplateInstance index();

    public static native TemplateInstance applications_passwordCredentials(
        OffsetDateTime now, Collection<ReportApplicationPasswordCredentialsRecord> records);
  }

  @GET
  @Path("/applications/passwordCredentials")
  @Produces("text/html")
  public TemplateInstance applications() {

    OffsetDateTime now = OffsetDateTime.now();

    return Templates.applications_passwordCredentials(
        now, msGraphClientService.getReportApplicationPasswordCredentialsRecords(accessToken, now));
  }

  @GET
  @Path("")
  public TemplateInstance index() {
    return Templates.index();
  }
}
