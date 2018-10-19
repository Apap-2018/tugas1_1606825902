package com.apap.tugas1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.Jabatan;
import com.apap.tugas1.model.Jabatan_pegawai;
import com.apap.tugas1.model.Pegawai;

@Repository
public interface Jabatan_pegawaiDB extends JpaRepository<Jabatan_pegawai, Long> {
	
	List<Jabatan_pegawai> findByPegawai(Pegawai pegawai);
	List<Jabatan_pegawai> findByJabatan(Jabatan jabatan);
	List<Jabatan_pegawai> findAll();
}
