package harmony.communityservice.common.event.dto.produce;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Builder;

@JsonInclude(Include.NON_NULL)
@Builder(toBuilder = true)
public record ExternalEvent() {
}
