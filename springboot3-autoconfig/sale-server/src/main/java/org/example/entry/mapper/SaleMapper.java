package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.entity.Sale;

@Mapper()
public interface SaleMapper {
    Sale getSale();
}
