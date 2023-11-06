package org.example.sale.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.sale.entity.Sale;

@Mapper
public interface SaleMapper {
    Sale getSale();
}
