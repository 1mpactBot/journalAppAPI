package dev.saransh.journalApp.controller;

import dev.saransh.journalApp.entity.JournalEntry;
import dev.saransh.journalApp.entity.User;
import dev.saransh.journalApp.service.JournalEntryService;
import dev.saransh.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    private JournalEntryService journalService;

    @Autowired
    private UserService userService;

    @GetMapping("{username}")
    public ResponseEntity<?> getALlJournalEntriesOfUser(@PathVariable String username) { //path/journal GET
        User user = userService.findByUserName(username);
        List<JournalEntry> all = user.getJournalEntries();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{username}")
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry entry, @PathVariable String username) { //path/journal POST
        try {
            journalService.saveEntry(entry, username);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalService.getById(id);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{username}/{id}")
    public ResponseEntity<?> deleteEntryById(@PathVariable String username, @PathVariable ObjectId id) {
        try {
            journalService.deleteById(id, username);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("{username}/{id}")
    public ResponseEntity<?> updateEntryById(@PathVariable String username, @PathVariable ObjectId id, @RequestBody JournalEntry newEntry) {
        JournalEntry oldEntry = journalService.getById(id).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().isEmpty() ? newEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent(newEntry.getContent() != null && !newEntry.getContent().isEmpty() ? newEntry.getContent() : oldEntry.getContent());
            journalService.saveEntry(oldEntry);
            return new ResponseEntity<JournalEntry>(oldEntry, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
