package com.apap.tugas1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

import com.apap.tugas1.model.Instansi;
import com.apap.tugas1.model.Jabatan;
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
	List<Pegawai> findByTahunMasukAndTanggalLahir(String tahunMasuk, Date tanggalLahir);
	List<Pegawai> findByJabatanIn(List<Jabatan> jabatan);
	List<Pegawai> findByInstansiAndJabatanIn(Instansi instansi, List<Jabatan> jabatan);
}
