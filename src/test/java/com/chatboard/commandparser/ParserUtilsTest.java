package com.chatboard.commandparser;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

public class ParserUtilsTest {
    
    @Test
    public void parser() {
        assertArrayEquals(new Object[] {new Mode("GET")},   ParserUtils.parser(">command GET"));
        assertArrayEquals(new Object[] {1},                 ParserUtils.parser(">command 1"));
        assertArrayEquals(new Object[] {"asd"},             ParserUtils.parser(">command \"asd\""));
        assertArrayEquals(new Object[] {"asd qwe"},         ParserUtils.parser(">command \"asd qwe\""));
        assertArrayEquals(new Object[] {"asd\" qwe"},       ParserUtils.parser(">command \"asd\\\" qwe\""));
        assertArrayEquals(new Object[] {"asd\nqwe"},        ParserUtils.parser(">command \"asd\\nqwe\""));
        assertArrayEquals(new Object[] {"asd\\qwe"},        ParserUtils.parser(">command \"asd\\qwe\""));
        assertArrayEquals(new Object[] {},                  ParserUtils.parser(">command"));
        assertArrayEquals(new Object[] {},                  ParserUtils.parser(">command "));
        assertArrayEquals(new Object[] {},                  ParserUtils.parser(">command   "));
        assertArrayEquals(new Object[] {1, 3},              ParserUtils.parser(">command  1   3"));
        assertArrayEquals(new Object[] {1, 3},              ParserUtils.parser(">command 1   3"));
        assertArrayEquals(new Object[] {1, 3},              ParserUtils.parser(">command  1   3  "));
    }
}
