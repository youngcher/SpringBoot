package com.ezen.demo.message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MsgRepository extends JpaRepository<Message, Integer>
{

}
