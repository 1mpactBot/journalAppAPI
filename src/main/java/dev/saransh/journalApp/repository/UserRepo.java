package dev.saransh.journalApp.repository;

import dev.saransh.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepo extends MongoRepository<User, ObjectId> {
    User findByUserName(String username);

    void deleteByUsername(String username);
}
