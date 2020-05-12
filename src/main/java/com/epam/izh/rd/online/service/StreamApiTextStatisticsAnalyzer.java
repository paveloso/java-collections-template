package com.epam.izh.rd.online.service;

import com.epam.izh.rd.online.helper.Direction;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.Collections.*;

/**
 * Данный класс обязан использовать StreamApi из функционала Java 8. Функциональность должна быть идентична
 * {@link SimpleTextStatisticsAnalyzer}.
 */
public class StreamApiTextStatisticsAnalyzer implements TextStatisticsAnalyzer {
    @Override
    public int countSumLengthOfWords(String text) {
        return getWords(text).stream().mapToInt(String::length).sum();
    }

    @Override
    public int countNumberOfWords(String text) {
        return (int) getWords(text).stream().count();
    }

    @Override
    public int countNumberOfUniqueWords(String text) {
        return (int) getWords(text).stream().distinct().count();
    }

    @Override
    public List<String> getWords(String text) {
        return Pattern.compile("[.,/!?\"\\s-]").splitAsStream(text).filter(word -> !word.isEmpty()).collect(Collectors.toList());
    }

    @Override
    public Set<String> getUniqueWords(String text) {
        return getWords(text).stream().distinct().collect(Collectors.toSet());
    }

    @Override
    public Map<String, Integer> countNumberOfWordsRepetitions(String text) {
        Map<String, Integer> wordsRepetitions = new HashMap<>();
        getWords(text).stream().forEach(word -> {
            if (wordsRepetitions.containsKey(word)) {
                wordsRepetitions.put(word, wordsRepetitions.get(word) + 1);
            } else {
                wordsRepetitions.put(word, 1);
            }
        });
        return wordsRepetitions;
    }

    @Override
    public List<String> sortWordsByLength(String text, Direction direction) {
        return getWords(text).stream().sorted(new StringLengthSorter(direction)).collect(Collectors.toList());
    }

    class StringLengthSorter implements Comparator<String> {

        Direction direction;

        public StringLengthSorter(Direction direction) {
            this.direction = direction;
        }

        @Override
        public int compare(String s1, String s2) {
            if (direction.equals(Direction.ASC)) {
                return s1.length() - s2.length();
            } else {
                return s2.length() - s1.length();
            }
        }
    }
}
