package com.chatboard.commandparser;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ParserUtilsTest {
    
    @Test
    public void parser() {
        assertArrayEquals(ParserUtils.parser(">command GET"),               new Object[] {new Mode("GET")});
        assertArrayEquals(ParserUtils.parser(">command 1"),                 new Object[] {1});
        assertArrayEquals(ParserUtils.parser(">command \"asd\""),           new Object[] {"asd"});
        assertArrayEquals(ParserUtils.parser(">command \"asd qwe\""),       new Object[] {"asd qwe"});
        assertArrayEquals(ParserUtils.parser(">command \"asd\\\" qwe\""),   new Object[] {"asd\" qwe"});
        assertArrayEquals(ParserUtils.parser(">command \"asd\\nqwe\""),     new Object[] {"asd\nqwe"});
        assertArrayEquals(ParserUtils.parser(">command \"asd\\qwe\""),      new Object[] {"asd\\qwe"});
        assertArrayEquals(ParserUtils.parser(">command"),                   new Object[] {});
        assertArrayEquals(ParserUtils.parser(">command "),                  new Object[] {});
        assertArrayEquals(ParserUtils.parser(">command   "),                new Object[] {});
        assertArrayEquals(ParserUtils.parser(">command  1   3"),            new Object[] {1, 3});
        assertArrayEquals(ParserUtils.parser(">command 1   3"),             new Object[] {1, 3});
        assertArrayEquals(ParserUtils.parser(">command  1   3  "),          new Object[] {1, 3});
        
    }
}
