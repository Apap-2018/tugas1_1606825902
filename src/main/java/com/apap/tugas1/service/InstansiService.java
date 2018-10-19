package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.Instansi;

public interface InstansiService {
	
	Instansi getInstansiById(Long id);
	List<Instansi> getListInstansi();
}
