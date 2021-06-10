package jpabook.jpabook.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jpabook.jpabook.domain.Order;
import jpabook.jpabook.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;



@RequiredArgsConstructor
@Repository
public class OrderRepositorySupport {

    private final JPAQueryFactory queryFactory;


//    public List<Order> findAll(OrderSearch orderSearch) {
//        return queryFactory.selectFrom(order)
//    }
}

//    public List<WorkGroup> findByWorkGroupNm(String name) {
//        return queryFactory.selectFrom(workGroup)
//                .where(workGroup.workGroupNm.containsIgnoreCase(name))
//                .fetch();
//    }

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