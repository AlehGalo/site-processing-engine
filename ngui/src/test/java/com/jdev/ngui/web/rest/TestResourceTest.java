package com.jdev.ngui.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.jdev.ngui.Application;
import com.jdev.ngui.domain.Test;
import com.jdev.ngui.repository.TestRepository;


/**
 * Test class for the TestResource REST controller.
 *
 * @see TestResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class })
@ActiveProfiles("dev")
public class TestResourceTest {
    
    private static final Long DEFAULT_ID = new Long(1L);

    private static final LocalDate DEFAULT_SAMPLE_DATE_ATTR = new LocalDate(0L);

    private static final LocalDate UPD_SAMPLE_DATE_ATTR = new LocalDate();

    private static final String DEFAULT_SAMPLE_TEXT_ATTR = "sampleTextAttribute";

    private static final String UPD_SAMPLE_TEXT_ATTR = "sampleTextAttributeUpt";

    @Inject
    private TestRepository testRepository;

    private MockMvc restTestMockMvc;
    
    private Test test;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TestResource testResource = new TestResource();
        ReflectionTestUtils.setField(testResource, "testRepository", testRepository);

        this.restTestMockMvc = MockMvcBuilders.standaloneSetup(testResource).build();

        test = new Test();
        test.setId(DEFAULT_ID);
    	test.setSampleDateAttribute(DEFAULT_SAMPLE_DATE_ATTR);
    	test.setSampleTextAttribute(DEFAULT_SAMPLE_TEXT_ATTR);
    }

    @Test
    public void testCRUDTest() throws Exception {

    	// Create Test
    	restTestMockMvc.perform(post("/app/rest/tests")
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test)))
                .andExpect(status().isOk());

    	// Read Test
    	restTestMockMvc.perform(get("/app/rest/tests/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID.intValue()))
    			.andExpect(jsonPath("$.sampleDateAttribute").value(DEFAULT_SAMPLE_DATE_ATTR.toString()))
    			.andExpect(jsonPath("$.sampleTextAttribute").value(DEFAULT_SAMPLE_TEXT_ATTR));

    	// Update Test
    	test.setSampleDateAttribute(UPD_SAMPLE_DATE_ATTR);
    	test.setSampleTextAttribute(UPD_SAMPLE_TEXT_ATTR);
  
    	restTestMockMvc.perform(post("/app/rest/tests")
    			.contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(test)))
                .andExpect(status().isOk());

    	// Read updated Test
    	restTestMockMvc.perform(get("/app/rest/tests/{id}", DEFAULT_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(DEFAULT_ID.intValue()))
    			.andExpect(jsonPath("$.sampleDateAttribute").value(UPD_SAMPLE_DATE_ATTR.toString()))
    			.andExpect(jsonPath("$.sampleTextAttribute").value(UPD_SAMPLE_TEXT_ATTR));

    	// Delete Test
    	restTestMockMvc.perform(delete("/app/rest/tests/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

    	// Read nonexisting Test
    	restTestMockMvc.perform(get("/app/rest/tests/{id}", DEFAULT_ID)
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());

    }
}
