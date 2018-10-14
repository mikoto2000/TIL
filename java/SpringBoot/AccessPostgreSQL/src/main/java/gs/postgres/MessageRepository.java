package gs.postgres;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * MessageRepository
 */
public interface MessageRepository extends JpaRepository<Message, Long> {
}

