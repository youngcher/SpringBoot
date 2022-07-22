package com.ezen.demo.validation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_track")
public class RequestMenu 
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO) // JPA에서 자동증가 지원
  private Long num;
  
  @NotNull
  private String userid;
  
  @NotNull
  private java.sql.Timestamp reqtime;
  
  @NotNull
  private String svcname;
}
