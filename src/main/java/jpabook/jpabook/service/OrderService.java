package jpabook.jpabook.service;

import java.util.List;
import java.util.Optional;

import jpabook.jpabook.domain.*;
import jpabook.jpabook.repository.ItemRepository;
import jpabook.jpabook.repository.MemberRepository;
import jpabook.jpabook.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OrderService {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    ItemRepository itemRepository;

    /**주문**/
    public Long order(Long memberId, Long itemId, int count) {

        //엔티티 조회
        //Member member = memberRepository.findOne(memberId);
        //Member member = memberRepository.getOne(memberId);
        //optional 사용은 NPE 방어를 위한 코드를 쉽게 작성할수 있음
//        Optional<Member> member = memberRepository.findById(memberId);
//        if(!member.isPresent()) {
//            throw new IllegalStateException("해당 회원이 없습니다");
//        }
        //Optional 객체 접근시 member.get()

        //람다식으로 작성
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원이 없습니다."));

        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 없습니다"));

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStauts(DeliveryStatus.READY);

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    /**주문취소**/
    public void cancelOrder(Long orderId) {

        //주문 엔티티 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException());
        //주문 취소
        order.cancel();

    }

    /**주문 검색**/
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll();
    }
}
