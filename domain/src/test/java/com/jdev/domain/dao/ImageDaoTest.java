package com.jdev.domain.dao;

import com.jdev.domain.domain.Image;

/**
 * 
 */

/**
 * @author Aleh
 * 
 */
public class ImageDaoTest extends AbstractWriteDaoTest<Image> {

    @Override
    Image createEntity() {
        return new Image("http://someurl.com", new byte[] { 0, 1, 0 });
    }

    @Override
    Image createUpdateEntity() {
        return new Image("http://somenewurl.com", new byte[] { 3, 1, 0 });
    }
}
