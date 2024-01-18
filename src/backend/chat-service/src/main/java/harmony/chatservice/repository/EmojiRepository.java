package harmony.chatservice.repository;

import harmony.chatservice.domain.Emoji;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmojiRepository extends MongoRepository<Emoji, Long> {

}