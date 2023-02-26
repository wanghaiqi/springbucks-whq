package geektime.spring.springbucks.waiter.repository;

import geektime.spring.springbucks.waiter.model.Coffee;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CoffeeCrudRepository extends PagingAndSortingRepository<Coffee, Long> {

}
