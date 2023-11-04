package org.example.controller;

import jakarta.annotation.Resource;
import org.example.entity.Table1;
import org.example.mapper.TestMapper;
import org.example.pojo.PaginationData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {

    @Resource
    TestMapper testMapper;

    @GetMapping("all")
    @ResponseBody
    List<Table1> getAll(){
        List<Table1> res = testMapper.getAll();
        return res;
    }

    @GetMapping("part1")
    @ResponseBody
    List<Table1> getPart1(){
        PaginationData<String> paginationData = new PaginationData<>();
        paginationData.setCurrentPage(1);
        paginationData.setPageSize(1);
        List<Table1> res = testMapper.getPart1(paginationData);
        return res;
    }

    @GetMapping("part2")
    @ResponseBody
    List<Table1> getPart2(){
        PaginationData<String> paginationData = new PaginationData<>();
        paginationData.setCurrentPage(1);
        paginationData.setPageSize(1);
        List<Table1> res = testMapper.getPart2(paginationData, 0);
        return res;
    }

}