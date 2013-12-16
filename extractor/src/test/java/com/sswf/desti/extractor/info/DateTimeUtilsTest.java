package com.sswf.desti.extractor.info;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;
import com.sswf.desti.text.Token;

/**
 * @author Alexey Grigorev
 */
public class DateTimeUtilsTest {

    @Test
    public void isTime() {
        assertTrue(DateTimeUtils.isTime("4 pm"));
        assertTrue(DateTimeUtils.isTime("4 PM"));
        assertTrue(DateTimeUtils.isTime("1 am"));
        assertTrue(DateTimeUtils.isTime("1 AM"));
        assertTrue(DateTimeUtils.isTime("1:30 am"));
        assertTrue(DateTimeUtils.isTime("1:59 am"));
        assertTrue(DateTimeUtils.isTime("1am"));
        assertTrue(DateTimeUtils.isTime("1\tam"));
        assertFalse(DateTimeUtils.isTime("13 am"));
        assertFalse(DateTimeUtils.isTime("13 pm"));
        assertFalse(DateTimeUtils.isTime("24 pm"));

        assertTrue(DateTimeUtils.isTime("1:00"));
        assertTrue(DateTimeUtils.isTime("01:00"));
        assertTrue(DateTimeUtils.isTime("00:00"));
        assertTrue(DateTimeUtils.isTime("01:59"));
        assertFalse(DateTimeUtils.isTime("01:60"));
        assertFalse(DateTimeUtils.isTime("24:00"));
    }

    @Test
    public void findTime() {
        String text = "the store is open from 6:00 am to 10:00 pm";
        List<Token> matches = DateTimeUtils.findTime(text);

        String sixAm = "6:00 am";
        String tenPm = "10:00 pm";
        int expectedPos1 = text.indexOf(sixAm);
        int expectedPos2 = text.indexOf(tenPm);

        Token match1 = new Token(text, expectedPos1, expectedPos1 + sixAm.length(), sixAm);
        Token match2 = new Token(text, expectedPos2, expectedPos2 + tenPm.length(), tenPm);

        assertEquals(Arrays.asList(match1, match2), matches);
    }

    @Test
    public void findDays() {
        String text = "on Sundays and Mondays the store is closed";

        String sunday = "Sundays";
        int sundayPos = text.indexOf(sunday);
        String monday = "Mondays";
        int mondayPos = text.indexOf(monday);

        Token match1 = new Token(text, sundayPos, sundayPos + sunday.length(), sunday);
        Token match2 = new Token(text, mondayPos, mondayPos + monday.length(), monday);

        List<Token> matches = DateTimeUtils.findDays(text);
        assertEquals(Arrays.asList(match1, match2), matches);
    }

    @Test
    public void test_detectedKeywords() {
        String text = "Monday - Lunch 11:30AM til 2:30PM - Dinner 5:30PM til 10PM, "
                + "Tuesday- Lunch 11:30AM til 2:30PM - Dinner 5:30PM til 10PM, "
                + "Wednesday - Lunch 11:30AM til 2:30PM - Dinner 5:30PM til 10PM, "
                + "Thursday - Lunch 11:30AM til 2:30PM - Dinner 5:30PM til 10PM, "
                + "Friday - Lunch 11:30AM til 2:30PM - Dinner 5:30PM til 10PM, "
                + "Saturday - Closed for Lunch - Dinner 5:30PM til 10PM, " + "Sunday - Closed";

        List<Token> tokens = DateTimeUtils.findTimetableKeywords(text);
        Set<String> keywords = extractTokens(tokens);

        Set<String> expected = ImmutableSet.of("monday", "lunch", "11:30am", "til", "2:30pm", "dinner",
                "5:30pm", "10pm", "tuesday", "wednesday", "thursday", "friday", "saturday", "closed",
                "sunday");

        assertEquals(expected, keywords);
    }

    private static Set<String> extractTokens(List<Token> tokens) {
        Set<String> keywords = new HashSet<>();
        for (Token t : tokens) {
            keywords.add(t.getToken().toLowerCase(Locale.US));
        }
        return keywords;
    }
}
