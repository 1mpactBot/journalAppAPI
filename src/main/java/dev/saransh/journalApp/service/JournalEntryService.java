package dev.saransh.journalApp.service;

import dev.saransh.journalApp.entity.JournalEntry;
import dev.saransh.journalApp.entity.User;
import dev.saransh.journalApp.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {
    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;


    @Transactional // Executes this method as a transaction
    public void saveEntry(JournalEntry journalEntry, String username) {
        User user = userService.findByUserName(username);
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved = journalEntryRepo.save(journalEntry);
        user.getJournalEntries().add(saved);
        userService.saveUser(user);
    }

    public void saveEntry(JournalEntry journalEntry) {
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepo.findAll();
    }

    public Optional<JournalEntry> getById(ObjectId id) {
        return journalEntryRepo.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String username) {
        boolean removed = false;
        User user = userService.findByUserName(username);
        removed = user.getJournalEntries().removeIf(x -> x.getId().equals(id)); //Deleting journal ref ID from user
        if (removed) {
            userService.saveUser(user);
            journalEntryRepo.deleteById(id);
        }
        return removed;
    }
}
