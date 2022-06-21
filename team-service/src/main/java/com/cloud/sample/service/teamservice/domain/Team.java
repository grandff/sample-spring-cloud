package com.cloud.sample.service.teamservice.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

@Entity
@Getter
@Setter
@ToString
@Builder
public class Team{
    @Id @GeneratedValue
    private Long id;
    private String name;
    private String address;
}