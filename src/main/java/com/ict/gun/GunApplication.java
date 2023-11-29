package com.ict.gun;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class GunApplication {
	/**
	 *  작성자 : 김정선
	 *  작성 내용 : Spring Security Login 창 안뜨게 하기
	 *  작성 날짜 : 2023. 11. 27
	 * */
	public static void main(String[] args) {
		SpringApplication.run(GunApplication.class, args);
	}

}
