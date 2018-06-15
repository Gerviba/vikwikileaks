package hu.ortosch.vikwikileaks;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import hu.ortosch.vikwikileaks.web.ControllerUtil;

public class ControllerUtilTest {

    ControllerUtil util = new ControllerUtil();
    
    @Test
    public void testGetExtention() {
        assertEquals("", util.getExtention(""));
        assertEquals("", util.getExtention("."));
        assertEquals("JPG", util.getExtention(".jpg"));
        assertEquals("PNG", util.getExtention("test.png"));
        assertEquals("TESTPNG", util.getExtention("testpng"));
        assertEquals("ZIP", util.getExtention("TeSt.ZiP"));
    }
    
}
