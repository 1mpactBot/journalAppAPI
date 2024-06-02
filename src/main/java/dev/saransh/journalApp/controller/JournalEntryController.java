//package dev.saransh.journalApp.controller;
//
//import dev.saransh.journalApp.entity.JournalEntry;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
////@RestController
//@RequestMapping("/journal")
//public class JournalEntryController {
//    private Map<String, JournalEntry> journalEntries = new HashMap<>();
//
//    @GetMapping
//    public List<JournalEntry> getALl(){ //path/journal GET
//        return new ArrayList<>(journalEntries.values());
//    }
//
//    @PostMapping
//    public boolean createEntry(@RequestBody JournalEntry entry){ //path/journal POST
//        journalEntries.put(entry.getId(),entry);
//        return true;
//    }
//
//    @GetMapping("/{id}")
//    public JournalEntry getEntryById(@PathVariable long id){
//        if(journalEntries.containsKey(id)){
//            return journalEntries.get(id);
//        }else{
//            return new JournalEntry();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public JournalEntry deleteEntryById(@PathVariable long id){
//        if(journalEntries.containsKey(id)){
//            return journalEntries.remove(id);
//        }else{
//            return new JournalEntry();
//        }
//    }
//}
