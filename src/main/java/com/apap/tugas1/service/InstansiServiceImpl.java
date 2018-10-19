package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.Instansi;
import com.apap.tugas1.repository.InstansiDB;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	
	@Autowired
	private InstansiDB instansiDB;
	
	@Override
	public Instansi getInstansiById(Long id) {
		// TODO Auto-generated method stub
		return instansiDB.getOne(id);
	}

	@Override
	public List<Instansi> getListInstansi() {
		// TODO Auto-generated method stub
		return instansiDB.findAll();
	}

	

}
