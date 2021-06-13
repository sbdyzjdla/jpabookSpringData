package jpabook.jpabook.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javafx.beans.binding.BooleanExpression;
import jpabook.jpabook.domain.Order;
import jpabook.jpabook.domain.OrderSearch;
import jpabook.jpabook.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;
import org.thymeleaf.util.StringUtils;

import java.util.List;

//static import 사용시
import static jpabook.jpabook.domain.QOrder.order;

@Repository
public class OrderRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory queryFactory;

    public OrderRepositorySupport(JPAQueryFactory queryFactory) {
        super(Order.class);
        this.queryFactory = queryFactory;
    }

    public List<Order> findAll(OrderSearch orderSearch) {
        return queryFactory.selectFrom(order)
                .join(order.member)
                .where(order.status.eq(orderSearch.getOrderStatus())
                        .and(order.member.name.eq(orderSearch.getMemberName())))
                .fetch();
    }


}

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