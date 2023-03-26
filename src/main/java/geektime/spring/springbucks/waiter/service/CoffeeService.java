package geektime.spring.springbucks.waiter.service;

import geektime.spring.springbucks.waiter.model.Coffee;
import geektime.spring.springbucks.waiter.repository.CoffeeCrudRepository;
import geektime.spring.springbucks.waiter.repository.CoffeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.joda.money.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@CacheConfig(cacheNames = "CoffeeCache")
public class CoffeeService {
    @Autowired
    private CoffeeRepository coffeeRepository;
    @Autowired
    private CoffeeCrudRepository coffeeCrudRepository;

    public Coffee saveCoffee(String name, Money price) {
        Coffee coffee = coffeeRepository.save(Coffee.builder().name(name).price(price).build());
        return coffee;
    }

    @Cacheable
    public List<Coffee> getAllCoffee() {
        return coffeeRepository.findAll(Sort.by("id"));
    }

    public Coffee getCoffee(Long id) {
//        return coffeeRepository.findById(id).get();
        return coffeeRepository.getOne(id);
    }

    public Coffee getCoffee(String name) {
        return coffeeRepository.findByName(name);
    }

    public List<Coffee> getCoffeeByName(List<String> names) {
        return coffeeRepository.findByNameInOrderById(names);
    }

    public void deleteAllCoffee() {
        coffeeRepository.deleteAll();
    }

    public List<Coffee> getAllById(Iterable iterable) {
        return coffeeRepository.findAllById(iterable);
    }

    public List<Coffee> getCoffeeByPage(int page,int size) {
        //int page = 0; //page:当前页的索引。注意索引都是从 0 开始的。
        //int size = 3;// size:每页显示 3 条数据
        PageRequest pageable= new PageRequest(page, size);

        Page<Coffee> pageCoffee = coffeeCrudRepository.findAll(pageable);
        List<Coffee> coffeeList = pageCoffee.getContent();
        return coffeeList;
    }

}
