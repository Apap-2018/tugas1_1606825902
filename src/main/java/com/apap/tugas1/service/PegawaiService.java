package com.apap.tugas1.service;

import com.apap.tugas1.model.Pegawai;

import java.util.List;

import com.apap.tugas1.model.Instansi;
import com.apap.tugas1.model.Jabatan_pegawai;

public interface PegawaiService {
	Pegawai getPegawaiById(Long id);
	Pegawai getPegawaiByNIP(String NIP);
	List<Jabatan_pegawai> getJabatanByPegawai(Pegawai pegawai);
	List<Pegawai> getPegawaiByInstansi(Instansi a);
}
