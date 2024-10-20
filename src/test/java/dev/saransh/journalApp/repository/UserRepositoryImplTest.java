package dev.saransh.journalApp.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepositoryImplTest {
    @Autowired
    UserRepositoryImpl repo;

    @Test
    public void testGetUsers(){
    repo.getUsersForSA();
    }
}
