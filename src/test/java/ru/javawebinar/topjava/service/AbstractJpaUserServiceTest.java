package ru.javawebinar.topjava.service;

import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.repository.datajpa.DataJpaUserRepositoryImpl;

/**
 * GKislin
 * 07.04.2015.
 */
abstract public class AbstractJpaUserServiceTest extends AbstractUserServiceTest {
    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private JpaUtil jpaUtil;

    @After
    public void tearDown() throws Exception {
        super.tearDown();
        jpaUtil.clear2ndLevelHibernateCache();
        DataJpaUserRepositoryImpl.isTest = false;
    }

    @Before
    public void setUp() {
        DataJpaUserRepositoryImpl.isTest = true;
    }
}
