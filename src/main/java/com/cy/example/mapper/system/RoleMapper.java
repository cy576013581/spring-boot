package com.cy.example.mapper.system;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.example.model.Page;
import com.cy.example.entity.system.SysRoleEntity;
import com.cy.example.mapper.SuperMapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface RoleMapper extends SuperMapper<SysRoleEntity> {

	int searchAllCount(@Param("role") SysRoleEntity role);

	List<SysRoleEntity> searchAll(
			@Param("role") SysRoleEntity role,
			@Param("page") Page page);
}
