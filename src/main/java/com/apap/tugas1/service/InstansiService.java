package com.apap.tugas1.service;

import java.util.List;
import java.util.Map;

import com.apap.tugas1.model.Instansi;
import com.apap.tugas1.model.Pegawai;

public interface InstansiService {
	
	Instansi getInstansiById(Long id);
	List<Instansi> getListInstansi();
	Map<String, Pegawai> getPegawaiMudaTua(List<Pegawai> pegawaiInstansi);
	
}
