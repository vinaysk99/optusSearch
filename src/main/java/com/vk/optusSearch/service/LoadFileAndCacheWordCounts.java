package com.vk.optusSearch.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
@Slf4j
public class LoadFileAndCacheWordCounts {

    private Map<String, Integer> wordCounts;

    public Map<String, Integer> getWordCounts() {
        return wordCounts;
    }

    private String removeSpecialChars(String wordWithSplChars) {
        String word = wordWithSplChars.toLowerCase();
        word = word.replaceAll("\\W", "");
        return word;
    }

    @PostConstruct
    public void readFileAndStoreWordCounts() {
        String file = "src/main/resources/paragraph1.txt";
        wordCounts = new HashMap<>();
        try (Scanner scanner = new Scanner(new File(file))) {
            while(scanner.hasNext()) {
                String next = scanner.next();
                String[] array = next.split(" ");
                for (String s : array) {
                    String wordToAdd = removeSpecialChars(s);
                    if (wordCounts.containsKey(wordToAdd)) {
                        Integer count = wordCounts.get(wordToAdd);
                        wordCounts.put(wordToAdd, ++count);
                    } else {
                        wordCounts.put(wordToAdd, 1);
                    }

                }
            }
        } catch (Exception e) {
            log.error("Failed to read paragraph1.txt");
        }
    }

    public Integer getCountForWord(final String word) {
        return wordCounts.getOrDefault(word.toLowerCase(), 0);
    }
}
