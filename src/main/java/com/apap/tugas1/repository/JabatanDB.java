package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.Jabatan;

public interface JabatanDB extends JpaRepository<Jabatan, Long>{
	Jabatan getJabatanByid(Long id);
}
