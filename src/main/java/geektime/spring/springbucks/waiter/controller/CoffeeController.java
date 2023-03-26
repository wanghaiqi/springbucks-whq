package geektime.spring.springbucks.waiter.controller;

import geektime.spring.springbucks.waiter.controller.request.NewCoffeeRequest;
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
@RequestMapping("/coffee")
@Slf4j
public class CoffeeController {
    @Autowired
    private CoffeeService coffeeService;

    @Autowired
    public SpringBeanTest springBeanTest;

    @PostMapping(path = "/testSpringBean")
    public void testSpringBean() {
        springBeanTest.springBeanTest();
    }


    @PostMapping(path = "/addForm", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public Coffee addCoffee(@Valid NewCoffeeRequest newCoffee) throws Exception {
        Coffee coffee = coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
        //测试异常回滚
        if(null != coffee){
            throw new Exception("异常回滚,不保存数据");
        }
        return coffee;
    }

    @GetMapping(path = "/deleteAll")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public void deleteAllCoffee() throws Exception {
        coffeeService.deleteAllCoffee();
    }


    @PostMapping(path = "/addJson", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Coffee addJsonCoffeeWithoutBindingResult(@Valid @RequestBody NewCoffeeRequest newCoffee) {
        return coffeeService.saveCoffee(newCoffee.getName(), newCoffee.getPrice());
    }

    @PostMapping(path = "/addBatch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<Coffee> batchAddCoffee(@RequestParam("file") MultipartFile file) {
        List<Coffee> coffees = new ArrayList<>();
        if (!file.isEmpty()) {
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(
                        new InputStreamReader(file.getInputStream()));
                String str;
                while ((str = reader.readLine()) != null) {
                    String[] arr = StringUtils.split(str, " ");
                    if (arr != null && arr.length == 2) {
                        coffees.add(coffeeService.saveCoffee(arr[0],
                                Money.of(CurrencyUnit.of("CNY"),
                                        NumberUtils.createBigDecimal(arr[1]))));
                    }
                }
            } catch (IOException e) {
                log.error("exception", e);
            } finally {
                IOUtils.closeQuietly(reader);
            }
        }
        return coffees;
    }

    //缓存查询
    @PostMapping(path = "/getAll")
    public List<Coffee> getAll() {
        return coffeeService.getAllCoffee();
    }

    //分页查询
    @PostMapping(path = "/getCoffeeByPage", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public List<Coffee> getCoffeeByPage(int page,int size) {
        return coffeeService.getCoffeeByPage(page,size);
    }

    @PostMapping(path = "/getAllById")
    public List<Coffee> getAllById() {
        ArrayList<Long> arrayList = new ArrayList<>();
        arrayList.add(new Long(1));
        arrayList.add(new Long(2));

        Iterator<Long> iterator = arrayList.iterator();
        Iterable iterable = convert(iterator);

        return coffeeService.getAllById(iterable);
    }

    private Iterable convert(Iterator<Long> iterator) {
        return new Iterable() {
            @Override
            public Iterator iterator() {
                return iterator;
            }
        };
    }

    @PostMapping("/getById/{id}")
    public Coffee getById(@PathVariable Long id) {
        Coffee coffee = coffeeService.getCoffee(id);
        log.info("Coffee {}:", coffee);
        return coffee;
    }

    @PostMapping(path = "/getByName", params = "name")
    public Coffee getByName(@RequestParam String name) {
        return coffeeService.getCoffee(name);
    }
}
