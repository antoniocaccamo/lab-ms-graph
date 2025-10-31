/**
 * Lab MS Graph
 *
 *  @author antonio.caccamo
 *  @date 26 apr 2024
 */


package labs.ms.graph.clients;

import com.azure.core.credential.TokenCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.graph.models.Application;
import com.microsoft.graph.models.ApplicationCollectionResponse;
import com.microsoft.graph.models.User;
import com.microsoft.graph.serviceclient.GraphServiceClient;
import jakarta.enterprise.context.RequestScoped;
import java.time.OffsetDateTime;
import java.util.*;
import java.util.stream.Collectors;
import labs.ms.graph.config.MsGraphClientConfig;
import labs.ms.graph.records.ReportApplicationPasswordCredentialsRecord;
import labs.ms.graph.records.UserRecord;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestScoped
public class MsGraphClient {

  // private final MsGraphClientConfig msGraphClientConfig;

  private final GraphServiceClient graphClient;

  public MsGraphClient(MsGraphClientConfig msGraphClientConfig) {

    // this.msGraphClientConfig = msGraphClientConfig;

    TokenCredential tokenCredential = null;

    tokenCredential =
        new ClientSecretCredentialBuilder()
            .tenantId(msGraphClientConfig.tenantId())
            .clientId(msGraphClientConfig.clientId())
            .clientSecret(msGraphClientConfig.clientSecret())
            .build();

    //    tokenCredential =
    //        new OnBehalfOfCredentialBuilder()
    //            .tenantId(msGraphClientConfig.tenantId())
    //            .clientId(msGraphClientConfig.clientId())
    //            .clientSecret(msGraphClientConfig.clientSecret())
    //            .userAssertion(accessToken.getRawToken())
    //            .build();

    this.graphClient =
        new GraphServiceClient(
            tokenCredential,
            msGraphClientConfig.scopes().stream().collect(Collectors.joining(" ")));
  }

  public Collection<ReportApplicationPasswordCredentialsRecord> reportApplicationRecords(
      OffsetDateTime now) {

    List<ReportApplicationPasswordCredentialsRecord> records = new LinkedList<>();

    try {
      ApplicationCollectionResponse applicationsPage = graphClient.applications().get();

      while (applicationsPage != null) {
        final List<Application> applications = applicationsPage.getValue();
        for (Application application : applications) {

          if (Objects.nonNull(application.getPasswordCredentials())) {

            records.addAll(
                application.getPasswordCredentials().stream()
                    .map(
                        pc ->
                            new ReportApplicationPasswordCredentialsRecord(
                                application.getAppId(),
                                application.getDisplayName(),
                                pc.getDisplayName(),
                                pc.getKeyId(),
                                pc.getStartDateTime(),
                                pc.getEndDateTime(),
                                pc.getEndDateTime().isBefore(now)))
                    .collect(Collectors.toList()));
          }
        }
        // Get the next page
        final String odataNextLink = applicationsPage.getOdataNextLink();
        if (odataNextLink == null || odataNextLink.isEmpty()) {
          break;
        } else {
          applicationsPage = graphClient.applications().withUrl(odataNextLink).get();
        }
      }

      return records;
    } catch (Exception e) {
      log.error("error occurred", e);
      throw e;
    }
  }

  public UserRecord getMe() {

    User user = graphClient.me().get();

    return new UserRecord(user.getMail(), user.getSurname(), user.getGivenName());
  }
}
