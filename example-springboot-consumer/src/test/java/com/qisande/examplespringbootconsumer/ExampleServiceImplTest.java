package com.qisande.examplespringbootconsumer;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author qisan
 * @date 2024-10-18 18:17:16
 * @description:
 */
@SpringBootTest
public class ExampleServiceImplTest {

    @Resource
    private ExampleServiceImpl exampleService;

    @Test
    public void test() {
        exampleService.test();
    }
}