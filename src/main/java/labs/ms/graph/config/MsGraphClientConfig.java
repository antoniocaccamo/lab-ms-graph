/**
 * Lab MS Graph
 *
 *  @author antonio.caccamo
 *  @date 26 apr 2024
 */


package labs.ms.graph.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;
import java.util.List;

/**
 * @auhtor antonio.caccamo on 2025-10-17 @ 11:32
 */
@ConfigMapping(prefix = "ms.graph.client")
public interface MsGraphClientConfig {

  @WithName("tenant-id")
  String tenantId();

  @WithName("client-id")
  String clientId();

  @WithName("client-secret")
  String clientSecret();

  @WithName("scopes")
  List<String> scopes();
}
