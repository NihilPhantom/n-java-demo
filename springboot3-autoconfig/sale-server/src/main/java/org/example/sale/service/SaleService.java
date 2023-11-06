package org.example.sale.service;

import jakarta.annotation.Resource;

import org.example.sale.entity.Sale;
import org.example.sale.mapper.SaleMapper;
import org.springframework.stereotype.Service;

@Service
public class SaleService {

    @Resource
    SaleMapper saleMapper;

    public Sale getSale(){
        return saleMapper.getSale();
    }
}
