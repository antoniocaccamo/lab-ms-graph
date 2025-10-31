/**
 * Lab MS Graph
 *
 *  @author antonio.caccamo
 *  @date 26 apr 2024
 */


package labs.ms.graph.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import java.time.OffsetDateTime;
import java.util.Collection;
import labs.ms.graph.clients.MsGraphClient;
import labs.ms.graph.records.ReportApplicationPasswordCredentialsRecord;
import labs.ms.graph.records.UserRecord;
import org.eclipse.microprofile.jwt.JsonWebToken;

/**
 * @auhtor antonio.caccamo on 2025-10-17 @ 11:12
 */
@ApplicationScoped
public class MsGraphClientService {

  @Inject MsGraphClient msGraphClient;

  public Collection<ReportApplicationPasswordCredentialsRecord>
      getReportApplicationPasswordCredentialsRecords(JsonWebToken accessToken, OffsetDateTime now) {
    return msGraphClient.reportApplicationRecords(now);
  }

  public UserRecord getMe(JsonWebToken accessToken) {
    return msGraphClient.getMe();
  }
}
