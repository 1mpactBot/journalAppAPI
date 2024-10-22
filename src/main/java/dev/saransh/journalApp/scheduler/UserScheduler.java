package dev.saransh.journalApp.scheduler;

import dev.saransh.journalApp.entity.JournalEntry;
import dev.saransh.journalApp.entity.User;
import dev.saransh.journalApp.enums.Sentiment;
import dev.saransh.journalApp.repository.UserRepositoryImpl;
import dev.saransh.journalApp.service.EmailService;
import dev.saransh.journalApp.service.SentimentAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserScheduler {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

    @Scheduled(cron = "0 0 9 ? * SUN *")
    public void fetchUsersAndSendSaMail() {
        List<User> users = userRepository.getUsersForSA();
        for (User user : users) {
            List<JournalEntry> journalEntries = user.getJournalEntries();
            List<Sentiment> filteredEntries = journalEntries.stream().filter(x -> x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x -> x.getSentiment()).collect(Collectors.toList());
            Map<Sentiment, Integer> counts = new HashMap<>();
            for (Sentiment sentiment : filteredEntries) {
                if (sentiment != null) {
                    counts.put(sentiment, counts.getOrDefault(sentiment, 0) + 1);
                }
            }
            Sentiment mostFrequentSentiment = null;
            int maxCnt = 0;
            for (Map.Entry<Sentiment, Integer> entry : counts.entrySet()) {
                if (entry.getValue() > maxCnt) {
                    maxCnt = entry.getValue();
                    mostFrequentSentiment = entry.getKey();
                }
            }
            if (mostFrequentSentiment != null) {
                emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days", mostFrequentSentiment.toString());
            }
        }
    }
}
