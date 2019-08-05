package com.tensquare.recruit.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.recruit.pojo.Enterprise;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface EnterpriseDao extends JpaRepository<Enterprise,String>,JpaSpecificationExecutor<Enterprise>{
	//面向对象的查询语句jphl。jphl类似于hql。hql是hibernate内部面向对象的查询语句
    public List<Enterprise> findByIshot(String ishot);
}
