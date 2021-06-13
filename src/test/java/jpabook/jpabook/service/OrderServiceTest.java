package jpabook.jpabook.service;

import jpabook.jpabook.domain.*;
import jpabook.jpabook.exception.NotEnoughStockException;
import jpabook.jpabook.repository.OrderRepository;
import jpabook.jpabook.repository.OrderRepositorySupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    OrderRepositorySupport orderRepositorySupport;

    @Test
    public void 상품주문() throws Exception {

        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격 재고
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException());


        assertEquals("상품 주문시 상내는 ORDER", OrderStatus.ORDER, getOrder.getStatus());
        assertEquals("주문한 상품 종류 슈가 정확해야 한다.", 1, getOrder.getOrderItems().size());
        assertEquals("주문 가격은 가격 * 수량이다.", 10000 * 2, getOrder.getTotalPrice());
        assertEquals("주문 수량만큼 재고가 줄어야 한다.",8, item.getStockQuantity());

    }

    @Test
    public void 주문취소() {

        //givne
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격 재고
        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException());

        assertEquals("주문 취소의 상태는 CANCEL 이다.", OrderStatus.CANCEL, getOrder.getStatus());
        assertEquals("주문이 취소된 상품의 그만큼 재고가 증가해야 한다.", 10 , item.getStockQuantity());

    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격 재고
        int orderCount = 11;

        //when
        orderService.order(member.getId(), item.getId(), orderCount);

        //then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    @Test
    public void 주문조회() {
        //given
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10); //이름, 가격 재고
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        OrderSearch orderSearch = new OrderSearch();

        //when
        orderSearch.setOrderStatus(OrderStatus.ORDER);
        orderSearch.setMemberName("회원1");
        List<Order> orderList = orderService.findOrders(orderSearch);

        //then
        assertThat(orderList.get(0).getStatus()).isEqualTo(OrderStatus.ORDER);
        assertThat(orderList.get(0).getOrderItems().get(0).getItem().getName()).isEqualTo("시골 JPA");
        assertThat(orderList.get(0).getMember().getName()).isEqualTo("회원1");

    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "강가", "123-123"));
        em.persist(member);
        return member;
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setStockQuantity(stockQuantity);
        book.setPrice(price);
        em.persist(book);
        return book;
    }

}
