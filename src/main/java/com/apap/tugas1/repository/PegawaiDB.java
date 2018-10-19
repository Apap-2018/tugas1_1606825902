package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import com.apap.tugas1.model.Instansi;
import com.apap.tugas1.model.Pegawai;


/**
 * 
 * PilotDb
 *
 */
@Repository
public interface PegawaiDB extends JpaRepository<Pegawai, Long>{
	Pegawai findByNip(String nip);
	List<Pegawai> findByInstansi(Instansi instansi);

}
