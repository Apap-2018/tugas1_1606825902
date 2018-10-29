package com.apap.tugas1.service;

import com.apap.tugas1.model.Pegawai;

import java.util.List;

import com.apap.tugas1.model.Instansi;
import com.apap.tugas1.model.Jabatan;

public interface PegawaiService {
	Pegawai getPegawaiById(Long id);
	Pegawai getPegawaiByNIP(String NIP);
	List<Pegawai> getPegawaiByInstansi(Instansi a);
	void addPegawai(Pegawai pegawai);
	void updatePegawai(Pegawai pegawai);
	String getGajiPegawai(List<Jabatan> jabatan, Pegawai pegawai);
	List<Pegawai> cariPegawaiByFilter(Long id_provinsi, Long id_instansi, Long id_jabatan );
}
