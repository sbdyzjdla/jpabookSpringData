package jpabook.jpabook.repository;

import jpabook.jpabook.domain.Order;
import jpabook.jpabook.domain.OrderSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    //jpa 명세(specification)를 이용한 검색 기능 구현



    //예전 검색로직
//    public List<Order> findAll (OrderSearch orderSearch) {
//        //JPQL
//        String jpql = "select o from Order o join o.member m";
//        boolean isFirstCondition = true;
//
//        //주문 상태 검색
//        if(orderSearch.getOrderStatus() != null) {
//            if(isFirstCondition) {
//                jpql += " where";
//                isFirstCondition = false;
//            } else {
//                jpql += " and";
//            }
//            jpql += " o.status = :status";
//        }
//
//        //회원 이름 검색
//        if(StringUtils.hasText(orderSearch.getMemberName())) {
//            if(isFirstCondition) {
//                jpql += " where";
//            } else {
//                jpql +=  " and";
//            }
//            jpql += " m.name like :name";
//        }
//
//        TypedQuery<Order> query = em.createQuery(jpql, Order.class)
//                .setMaxResults(1000);  //최대 1000건
//
//        if(orderSearch.getOrderStatus() != null) {
//            query = query.setParameter("status", orderSearch.getOrderStatus());
//        }
//
//        if(StringUtils.hasText(orderSearch.getMemberName())) {
//            query = query.setParameter("name", orderSearch.getMemberName());
//        }
//
//        return query.getResultList();
//    }
}
