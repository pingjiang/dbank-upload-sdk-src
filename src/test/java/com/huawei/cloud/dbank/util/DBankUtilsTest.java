package com.huawei.cloud.dbank.util;

import junit.framework.TestCase;

public class DBankUtilsTest extends TestCase {

    public void testArrayToString() throws Exception {
        assertEquals("[\"url\", \"size\"]", DBankUtils.arrayToString(new String[]{"url", "size"}));
        // ["/Netdisk/直链/nsp.zip"]
        assertEquals("[\"/Netdisk/直链/nsp.zip\"]", DBankUtils.arrayToString(new String[]{"/Netdisk/直链/nsp.zip"}));
    }
}