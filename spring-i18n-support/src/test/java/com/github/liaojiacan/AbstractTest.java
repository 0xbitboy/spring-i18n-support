package com.github.liaojiacan;

import com.github.liaojiacan.config.BaseConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author liaojiacan
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BaseConfig.class })
@Transactional
public abstract class AbstractTest {


}
