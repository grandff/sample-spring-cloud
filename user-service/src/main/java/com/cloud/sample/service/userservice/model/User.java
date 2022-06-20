package com.cloud.sample.service.userservice.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

@Entity(name="users")   // jpa에서 사용할 entity 이름
@Getter
@Setter
@ToString
public class User{
    @Id @GeneratedValue
    private Long id;
    private String username;
}