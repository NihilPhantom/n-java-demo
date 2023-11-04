package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.config.PageCut;
import org.example.entity.Table1;
import org.example.pojo.PaginationData;

import java.util.List;

@Mapper
public interface TestMapper {
    @Select("SELECT * FROM table1")
    List<Table1> getAll();

    @Select("SELECT * FROM table1")
    List<Table1> getPart1(PaginationData<String> paginationData);

    // 这里的第二个参数没有在sql 中使用，仅仅用于测试
    @Select("SELECT * FROM table1")
    List<Table1> getPart2(PaginationData<String> paginationData, Integer p);

}
