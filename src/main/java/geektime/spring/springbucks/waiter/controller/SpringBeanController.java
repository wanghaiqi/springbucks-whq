package geektime.spring.springbucks.waiter.controller;

import geektime.spring.springbucks.waiter.model.Coffee;
import geektime.spring.springbucks.waiter.service.CoffeeService;
import geektime.spring.springbucks.waiter.service.SpringBeanTest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/springBean")
@Slf4j
public class SpringBeanController {

    @Autowired
    public SpringBeanTest springBeanTest;

    @PostMapping(path = "/getAll")
    public void getAll() {
         springBeanTest.springBeanTest();
    }

}
