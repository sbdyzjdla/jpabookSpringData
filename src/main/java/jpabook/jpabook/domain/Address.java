package jpabook.jpabook.domain;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() {}
    // 임베디드 타입 (@Embeddable)은 자바 기본 생성자를 public 또는 protected로 설정해야한다.
    // protected가 더 안전함

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
    //값 타입은 변경 불가능하게 설계햐야함
}
