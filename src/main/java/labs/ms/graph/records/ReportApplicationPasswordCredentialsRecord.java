/**
 * Lab MS Graph
 *
 *  @author antonio.caccamo
 *  @date 26 apr 2024
 */


package labs.ms.graph.records;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @auhtor antonio.caccamo on 2025-10-17 @ 11:54
 */
public record ReportApplicationPasswordCredentialsRecord(
    String appId,
    String appDisplayName,
    String displayName,
    UUID keyId,
    OffsetDateTime startDateTime,
    OffsetDateTime endDateTime,
    Boolean expired) {}
