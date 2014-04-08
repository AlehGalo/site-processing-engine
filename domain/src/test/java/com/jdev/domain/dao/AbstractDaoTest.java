/**
 * 
 */
package com.jdev.domain.dao;

import static com.jdev.domain.dao.ReflectionUtils.compareObjects;
import static com.jdev.domain.dao.ReflectionUtils.copyValuesExceptGetId;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Timed;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.jdev.domain.domain.IIdentifiable;

/**
 * @author Aleh Super class for dao tests with annotations.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:com/jdev/domain/data/daoSpringApplicationContext.xml" })
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class AbstractDaoTest<T extends IIdentifiable> {

    /**
     * @return entity of type T.
     */
    abstract T createEntity();

    /**
     * @return entity for the update.
     */
    abstract T createUpdateEntity();

    /**
     * Injected daoService.
     */
    @Autowired
    IWriteDao<T> daoService;

    /**
     * Init all context. Warm up the JPA engine.
     */
    @Test
    public void aWarmingUpJPA() {
        daoService.countAll();
    }

    /**
     * Test insertion of the record in db.
     */
    @Test
    @Timed(millis = 100)
    public void testInsert() {
        T mainEntity = createEntity();
        long beforeSaveCounter = daoService.countAll();
        daoService.save(mainEntity);
        long afterSaveCounter = daoService.countAll();
        Assert.assertEquals("Records number is not correct. It should be increased by 1.",
                beforeSaveCounter, afterSaveCounter - 1);
        T databaseEntity = daoService.find(mainEntity.getId());
        Assert.assertNotNull("Entity was not found in db.", databaseEntity);
    }

    /**
     * Test update of the record in db.
     */
    @Test
    @Timed(millis = 300)
    public void testUpdate() {
        T mainEntity = createEntity();
        daoService.save(mainEntity);
        T updateEntity = createUpdateEntity();
        copyValuesExceptGetId(updateEntity, mainEntity);
        T foundEntity = daoService.find(mainEntity.getId());
        Assert.assertTrue(compareObjects(foundEntity, updateEntity));
    }

    /**
     * Test deletion of the record in db.
     */
    @Test
    @Timed(millis = 100)
    public void testDelete() {
        T mainEntity = createEntity();
        daoService.save(mainEntity);
        long beforeDeleteCounter = daoService.countAll();
        daoService.delete(mainEntity);
        long afterDeleteCounter = daoService.countAll();
        Assert.assertEquals("Records number is not correct. It should be reduced by 1.",
                beforeDeleteCounter, afterDeleteCounter + 1);
        T databaseEntity = daoService.find(mainEntity.getId());
        Assert.assertNull("Entity was found in db. But should not.", databaseEntity);
    }

    @Test
    @Timed(millis = 100)
    public void testCleanInsert() {
        daoService.save(createEntity());
    }
}
