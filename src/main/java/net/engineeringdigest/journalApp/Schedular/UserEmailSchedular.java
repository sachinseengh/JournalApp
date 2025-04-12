package net.engineeringdigest.journalApp.Schedular;


import net.engineeringdigest.journalApp.entities.JournalEntry;
import net.engineeringdigest.journalApp.entities.User;
import net.engineeringdigest.journalApp.enums.Sentiment;
import net.engineeringdigest.journalApp.service.EmailService;
import net.engineeringdigest.journalApp.service.SentimentAnalysisService;
import net.engineeringdigest.journalApp.service.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserEmailSchedular {


    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepositoryImpl userRepositoryImpl;

    @Autowired
    private SentimentAnalysisService sentimentAnalysisService;

//    @Scheduled(cron="0 0 6 1/1 * ? ")
    public void fetchUserAndSendEmail(){
       List<User> users= userRepositoryImpl.searchForSA();
       for(User user:users){
           List<JournalEntry> entries = user.getEntries();

//           List<String> filteredEntries = entries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getContent()).collect(Collectors.toList());
//
//           String entry = String.join(" ",filteredEntries);
//
//           String sentiment = sentimentAnalysisService.getSentiment(entry);


           List<Sentiment> sentiments = entries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minus(7, ChronoUnit.DAYS))).map(x->x.getSentiment()).collect(Collectors.toList());

           Map<Sentiment, Integer> sentimentCounts = new HashMap<>();
           for (Sentiment sentiment :sentiments) {
               if (sentiment != null)
                   sentimentCounts.put(sentiment, sentimentCounts.getOrDefault(sentiment, 0) + 1);
           }
           Sentiment mostFrequentSentiment = null;
           int maxCount = 0;
           for (Map. Entry <Sentiment, Integer>entry: sentimentCounts.entrySet()) {
               if (entry.getValue() > maxCount) {
                   maxCount = entry.getValue();
                   mostFrequentSentiment = entry.getKey();
               }
           }
           if(mostFrequentSentiment !=null){
               emailService.sendMail("curiousjunker@gmail.com","Sentiment For Last Seven Days",mostFrequentSentiment.toString());
           }


       }
    }

}
