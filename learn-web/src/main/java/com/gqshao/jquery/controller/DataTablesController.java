package com.gqshao.jquery.controller;

import com.google.common.collect.Lists;
import com.gqshao.jquery.entity.DataTablesDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/datatables")
public class DataTablesController {

    @RequestMapping("/test1")
    public String test1() {
        return "/datatables/test1";
    }

    @RequestMapping("/test2")
    public String test2() {
        return "/datatables/test2";
    }

    @RequestMapping("list")
    public List<DataTablesDTO> list() {
        List<DataTablesDTO> dtos = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            DataTablesDTO dataTablesDto = new DataTablesDTO();
            dataTablesDto.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            dataTablesDto.setName("中文很长的中文名字中文超长的名字" + i);
            dataTablesDto.setRemark("中文很长的中文名字中文超长的名字,中文很长的中文名字中文超长的名字123123123123,中文很长的中文名字中文超长的名字asdlfasjkdfa,中文很长的中文名字中文超长的名字ef,中文很长的中文名字中文超长的名字");
            dtos.add(dataTablesDto);
        }
        return dtos;
    }
}
