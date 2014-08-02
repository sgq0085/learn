package com.gqshao.myapp.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gqshao.myapp.demo.entity.PU1;

/**
 * repository包下的接口指向数据源2
 */
public interface PU1Repository extends JpaRepository<PU1, Integer> {

}
