package com.example.matome.controllerTests;

import com.example.matome.controller.shortestPathController;
import com.example.matome.service.DijkstrasAlgorithm;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(shortestPathController.class)
public class shortestPathIntegrationTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    DijkstrasAlgorithm dijkstrasAlgorithm;



}
