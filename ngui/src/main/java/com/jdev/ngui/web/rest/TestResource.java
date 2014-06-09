package com.jdev.ngui.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jdev.ngui.domain.Test;
import com.jdev.ngui.repository.TestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * REST controller for managing Test.
 */
@RestController
@RequestMapping("/app")
public class TestResource {

    private final Logger log = LoggerFactory.getLogger(TestResource.class);

    @Inject
    private TestRepository testRepository;

    /**
     * POST  /rest/tests -> Create a new test.
     */
    @RequestMapping(value = "/rest/tests",
            method = RequestMethod.POST,
            produces = "application/json")
    @Timed
    public void create(@RequestBody Test test) {
        log.debug("REST request to save Test : {}", test);
        testRepository.save(test);
    }

    /**
     * GET  /rest/tests -> get all the tests.
     */
    @RequestMapping(value = "/rest/tests",
            method = RequestMethod.GET,
            produces = "application/json")
    @Timed
    public List<Test> getAll() {
        log.debug("REST request to get all Tests");
        return testRepository.findAll();
    }

    /**
     * GET  /rest/tests/:id -> get the "id" test.
     */
    @RequestMapping(value = "/rest/tests/{id}",
            method = RequestMethod.GET,
            produces = "application/json")
    @Timed
    public ResponseEntity<Test> get(@PathVariable Long id, HttpServletResponse response) {
        log.debug("REST request to get Test : {}", id);
        Test test = testRepository.findOne(id);
        if (test == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(test, HttpStatus.OK);
    }

    /**
     * DELETE  /rest/tests/:id -> delete the "id" test.
     */
    @RequestMapping(value = "/rest/tests/{id}",
            method = RequestMethod.DELETE,
            produces = "application/json")
    @Timed
    public void delete(@PathVariable Long id) {
        log.debug("REST request to delete Test : {}", id);
        testRepository.delete(id);
    }
}
