package com.ycy.demo;

import junit.framework.TestCase;

/**
 * Unit test for simple App.
 */
public class FormatTest 
    extends TestCase
{



}
Configuration conf = new configuration();
conf.addResource("configuration-l.xml");
assertThat(conf.get("color"), ls("yellow"));


