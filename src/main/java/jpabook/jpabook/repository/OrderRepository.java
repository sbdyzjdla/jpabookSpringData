package jpabook.jpabook.repository;

import jpabook.jpabook.domain.Order;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OrderRepository {

    @PersistenceContext
    EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        em.find(Order.class, id);
    }

    public List<Order> findAll (OrderSearch orderSearch) {...}
}
