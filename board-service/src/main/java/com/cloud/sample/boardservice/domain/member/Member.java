package com.cloud.sample.boardservice.domain.member;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Getter
public class Member implements Serializable{
	private static final long serialVersionUID = 8328774953218952698L;
	
	@Id @GeneratedValue
    @Column(name="user_no")
    private Long id;

    @Column(name="user_id", nullable=false, unique=true)
    private String userId;
	
	@Column(name="user_name", nullable=false, length=10)
    private String userName;
}
