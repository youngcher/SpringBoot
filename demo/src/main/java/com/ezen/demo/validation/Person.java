package com.ezen.demo.validation;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="person")
public class Person 
{
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO) // JPA에서 자동증가 지원
  private Long num;

  @NotEmpty(message="이름은 필수 입력항목입니다")
  @Size(min=2, message="{Person.Name.Size}") //ValidationMessages.properties   // [min=x, max=y]
  private String name;

  @NotEmpty
  @Email
  private String email;

  @NotNull
  @Min(value=20)
  private Integer age;
}