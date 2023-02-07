package server.team_a.todayhouse.config;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestInfo {
    private String remoteIp;
    private String sessionId;
    private LocalDateTime loginTime;
}
