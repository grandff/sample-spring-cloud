package com.cloud.sample.service.teamservice.domain;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.Builder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;

import com.cloud.sample.service.teamservice.domain.Team;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamMember{
    @Id @GeneratedValue
    private Long id;
    private Long userId;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="teamId")
    private Team team;
}