package com.chatboard.commandparser;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class ParserUtilsTest {
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(ParserUtils.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @Test
    public void parser() {
        assertArrayEquals(ParserUtils.parser(">command GET"), new Object[] {new Mode("GET")});
        assertArrayEquals(ParserUtils.parser(">command 1"), new Object[] {1});
        assertArrayEquals(ParserUtils.parser(">command \"asd\""), new Object[] {"asd"});
        assertArrayEquals(ParserUtils.parser(">command \"asd qwe\""), new Object[] {"asd qwe"});
        assertArrayEquals(ParserUtils.parser(">command \"asd\\\" qwe\""), new Object[] {"asd\" qwe"});
        assertArrayEquals(ParserUtils.parser(">command \"asd\\nqwe\""), new Object[] {"asd\nqwe"});
        assertArrayEquals(ParserUtils.parser(">command \"asd\\qwe\""), new Object[] {"asd\\qwe"});
    }
}
