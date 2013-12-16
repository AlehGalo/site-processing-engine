package com.sswf.desti.extractor.info;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.sswf.desti.text.Token;
import com.sswf.desti.text.Tokenizer;

/**
 * @author Alexey Grigorev
 */
public class DateTimeUtils {

    private static final Tokenizer TOKENIZER = new Tokenizer();

    private static final String TIME_REGEX = "((1[012]|\\d)\\s*(:[0-5]\\d\\s*)?(am|pm))|"
            + "((2[0123]|1\\d|0?\\d):[0-5]\\d)";
    private static final Pattern TIME_PATTERN = Pattern.compile(TIME_REGEX, Pattern.CASE_INSENSITIVE);

    public static boolean isTime(String date) {
        Matcher matcher = TIME_PATTERN.matcher(date);
        return matcher.matches();
    }

    public static List<Token> findTime(String text) {
        Matcher matcher = TIME_PATTERN.matcher(text);

        List<Token> res = new ArrayList<>();
        while (matcher.find()) {
            int from = matcher.start();
            int to = matcher.end();
            res.add(new Token(text, from, to, text.substring(from, to)));
        }

        return res;
    }

    public static List<Token> findDays(String text) {
        List<Token> tokens = TOKENIZER.tokenize(text);
        List<Token> days = filterDays(tokens);
        return days;
    }

    public static List<Token> filterDays(List<Token> tokens) {
        List<Token> result = new ArrayList<>();
        for (Token token : tokens) {
            String stringToken = token.getToken();
            if (token.isNotPunctuation() && Days.isDay(stringToken)) {
                result.add(token);
            }
        }
        return result;
    }

    public static List<Token> findTimetableKeywords(String text) {
        List<Token> allTokens = TOKENIZER.tokenize(text);

        List<Token> days = filterDays(allTokens);
        List<Token> words = filterInterestingWords(allTokens);
        List<Token> time = findTime(text);

        return Lists.newArrayList(Iterables.concat(days, words, time));
    }

    private static List<Token> filterInterestingWords(List<Token> tokens) {
        List<Token> result = new ArrayList<>();
        for (Token token : tokens) {
            String tokenString = token.getToken().toLowerCase(Locale.US);
            if (INTERESTING_WORDS.contains(tokenString)) {
                result.add(token);
            }
        }
        return result;
    }

    private static final Set<String> INTERESTING_WORDS = ImmutableSet.of("closed", "serving", "breakfast",
            "from", "at", "to", "dinner", "daily", "midnight", "hours", "start", "starts", "til", "until", "lunch");

}
