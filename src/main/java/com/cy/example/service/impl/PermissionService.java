package com.cy.example.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.cy.example.model.Page;
import com.cy.example.entity.system.SysPermissionEntity;
import com.cy.example.mapper.system.PermissionMapper;
import com.cy.example.service.IPermissionService;

@Service
public class PermissionService extends ServiceImpl<PermissionMapper, SysPermissionEntity>
	implements IPermissionService{
	
	@Autowired
	private PermissionMapper permissionMapper;
	

	public List<SysPermissionEntity> searchAll(SysPermissionEntity permission,
			Page page) {
		// TODO Auto-generated method stub
		return permissionMapper.searchAll(permission, page);
	}

	public int searchAllCount(SysPermissionEntity permission) {
		// TODO Auto-generated method stub
		return permissionMapper.searchAllCount(permission);
	}

	@Override
	public List<SysPermissionEntity> findAll() {
		return permissionMapper.findAll();
	}
}
