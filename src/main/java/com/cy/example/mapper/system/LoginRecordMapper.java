package com.cy.example.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.example.model.Page;
import com.cy.example.entity.system.LoginRecordEntity;
import com.cy.example.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LoginRecordMapper extends SuperMapper<LoginRecordEntity> {

	int searchAllCount(@Param("loginRecord") LoginRecordEntity loginRecord);

	List<LoginRecordEntity> searchAll(
			@Param("loginRecord") LoginRecordEntity loginRecord,
			@Param("page") Page page);
	
	public int recentLoginCount(String date);
}
