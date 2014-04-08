/**
 * 
 */
package com.jdev.domain.dao;

import java.util.Date;

import com.jdev.domain.domain.Title;

/**
 * @author Aleh
 * 
 */
public class TitleDaoTest extends AbstractWriteDaoTest<Title> {

    @Override
    Title createEntity() {
        Title title = new Title("Title");
        title.setDateTime(new Date());
        return title;
    }

    @Override
    Title createUpdateEntity() {
        Title title = new Title("TitleNew");
        title.setDateTime(new Date());
        return title;

    }
}
