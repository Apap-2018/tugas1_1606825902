package com.apap.tugas1.service;

import java.util.List;

import com.apap.tugas1.model.Jabatan;
import com.apap.tugas1.model.Jabatan_pegawai;

public interface JabatanService {
	
	void addJabatan(Jabatan baru);
	List<Jabatan> getlistJabatan();
	Jabatan getJabatanById(Long id);
	void UpdateJabatan(Long id, Jabatan baru);
	Boolean deleteJabatan(Long id);
	Integer sebaranPegawai(Jabatan Jabatan);
	List<Jabatan_pegawai> findJabatanAll();
	List<Jabatan_pegawai> findJabatan(Jabatan jabatan);
	String getGajiJabatan(Jabatan jabatan);
}
