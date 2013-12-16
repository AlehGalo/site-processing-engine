package com.sswf.desti.extractor.info;

import static org.junit.Assert.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class MenuExtractorTest {

    @Test
    public void testProcess() {
        Pattern pattern = Pattern.compile("\\$\\s+\\d+.?\\d*");
        String test = "abc $ 14.5 per one";

        Matcher matcher = pattern.matcher(test);

        boolean find = matcher.find();
        assertTrue(find);
        
        System.out.println(matcher.group());

    }

}
