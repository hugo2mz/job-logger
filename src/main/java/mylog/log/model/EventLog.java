package mylog.log.model;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EventLog implements Serializable {
	
	private Long logId;

    private String message;

    private Timestamp occurredOn;

    private String jobLevels;

	private static final long serialVersionUID = 247537479332550926L;

}
