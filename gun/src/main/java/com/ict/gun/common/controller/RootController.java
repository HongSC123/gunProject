package com.ict.gun.common.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


/**
 *  작성자 : 김정선
 *  추가 목적 : 새로고침 안되는 에러 해결
 *  작성일 : 2023. 11. 29
 *  connect-history-api-fallback : SPA 에서 index.html
 *  이 계속 읽히는 문제 해결을 위한 ErrorController 상속
 *  컨트롤러 작성
 * */

@Controller
public class RootController implements ErrorController {
    private final String ERROR_PATH = "/error";

    @GetMapping(ERROR_PATH)
    public String redirectRoot(){
        return "forward:/index.html";
    }
    public String getERROR_PATH() {
        return null;
    }
}
