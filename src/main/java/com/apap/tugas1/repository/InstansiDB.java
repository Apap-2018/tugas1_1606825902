package com.apap.tugas1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apap.tugas1.model.Instansi;
import com.apap.tugas1.model.Provinsi;

public interface InstansiDB extends JpaRepository<Instansi, Long>{
	List<Instansi> findByProvinsi(Provinsi provinsi);
	Instansi findByProvinsiAndId(Provinsi provinsi, Long id);
}
