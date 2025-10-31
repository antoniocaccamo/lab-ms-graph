/**
 * Lab MS Graph
 *
 *  @author antonio.caccamo
 *  @date 26 apr 2024
 */


package labs.ms.graph.providers;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import labs.ms.graph.clients.MsGraphClient;
import labs.ms.graph.config.MsGraphClientConfig;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Slf4j
@ApplicationScoped
public class MsGraphClientProvider {

  @Inject MsGraphClientConfig msGraphClientConfig;

  public MsGraphClient provideMsGraphClient(JsonWebToken accessToken) {
    MsGraphClient msGraphClient = new MsGraphClient(msGraphClientConfig);
    log.info("providing MsGraphClient [{}]", msGraphClient.hashCode());
    return msGraphClient;
  }
}
