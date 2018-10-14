package gs.postgres;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Message
 */
@Entity
@SequenceGenerator(name = "message_id_seq", sequenceName = "message_id_seq", allocationSize = 1, initialValue = 1)
public class Message {
    @Id
    @GeneratedValue(generator="message_id_seq")
    private Long id;

    private String message;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return String.format("id:%d, message=%s", this.id, this.message);
    }
}
