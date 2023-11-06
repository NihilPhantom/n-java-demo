package org.example.sale.controller;

import jakarta.annotation.Resource;

import org.example.sale.entity.Sale;
import org.example.sale.service.SaleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SaleController {

    @Resource
    SaleService saleService;

    @GetMapping("sale")
    @ResponseBody
    Sale test(){
        return saleService.getSale();
    }
}