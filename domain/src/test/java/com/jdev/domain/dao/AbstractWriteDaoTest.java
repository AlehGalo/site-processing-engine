/**
 * 
 */
package com.jdev.domain.dao;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.jdev.domain.entity.IIdentifiable;

/**
 * @author Aleh Super class for dao tests with annotations.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:com/jdev/domain/data/daoSpringApplicationContext.xml" })
@TransactionConfiguration
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public abstract class AbstractWriteDaoTest<T extends IIdentifiable> {

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
     * 
     */
    @Autowired
    IReadDao<T> daoReadService;

    /**
     * Init all context. Warm up the JPA engine.
     */
    @Test
    public void aWarmingUpJPA() {
        daoReadService.countAll();
    }

    /**
     * Test insertion of the record in db.
     */
    @Test
    // @Timed(millis = 2000)
    public void testInsert() {
        T mainEntity = createEntity();
        long beforeSaveCounter = daoReadService.countAll();
        daoService.save(mainEntity);
        long afterSaveCounter = daoReadService.countAll();
        Assert.assertEquals("Records number is not correct. It should be increased by 1",
                beforeSaveCounter, afterSaveCounter - 1);
        T databaseEntity = daoReadService.get(mainEntity.getId());
        Assert.assertTrue("Entity was not correct",
                ReflectionUtils.compareObjects(databaseEntity, mainEntity));
    }

    /**
     * Test update of the record in db.
     */
    @Test
    // @Timed(millis = 3000)
    public void testUpdate() {
        T mainEntity = createEntity();
        daoService.save(mainEntity);
        T updateEntity = createUpdateEntity();
        ReflectionUtils.copyValuesExceptGetId(updateEntity, mainEntity);
        T foundEntity = daoReadService.get(mainEntity.getId());
        Assert.assertTrue(ReflectionUtils.compareObjects(foundEntity, updateEntity));
    }

    /**
     * Test deletion of the record in db.
     */
    @Test
    // @Timed(millis = 2000)
    public void testDelete() {
        T mainEntity = createEntity();
        daoService.save(mainEntity);
        long beforeDeleteCounter = daoReadService.countAll();
        daoService.delete(mainEntity);
        long afterDeleteCounter = daoReadService.countAll();
        Assert.assertEquals("Records number is not correct. It should be reduced by 1.",
                beforeDeleteCounter, afterDeleteCounter + 1);
        T databaseEntity = daoReadService.get(mainEntity.getId());
        Assert.assertNull("Entity was found in db. But should not.", databaseEntity);
    }

    @Test
    // @Timed(millis = 2000)
    public void testCleanInsert() {
        daoService.save(createEntity());
    }
}
