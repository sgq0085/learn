package com.gqshao.myapp.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gqshao.myapp.demo.entity.PU1;

/**
 * dao包下的接口指向数据源1 
 */
public interface PU1Dao extends JpaRepository<PU1, Integer> {  
    
}  
