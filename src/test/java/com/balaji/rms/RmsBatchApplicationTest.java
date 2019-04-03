package com.balaji.rms;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
public class RmsBatchApplicationTest {

    Logger logger = LoggerFactory.getLogger(RmsBatchApplicationTest.class);

    @Test
    public void testContextLoads() throws Exception {
        logger.info("Context Loads are tested successfully");
    }

}
