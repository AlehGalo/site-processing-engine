/**
 * 
 */
package com.jdev.domain.dao;

import com.jdev.domain.domain.ScaledImage;

/**
 * @author Aleh
 * 
 */
public class ScaledDaoTest extends AbstractWriteDaoTest<ScaledImage> {

    @Override
    ScaledImage createEntity() {
        return new ScaledImage(123, new byte[] { 0, 1, 0 });
    }

    @Override
    ScaledImage createUpdateEntity() {
        return new ScaledImage(654, new byte[] { 0, 1, 98 });
    }

}
