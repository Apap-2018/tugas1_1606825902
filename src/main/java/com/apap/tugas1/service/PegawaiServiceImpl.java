package com.apap.tugas1.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.Instansi;
import com.apap.tugas1.model.Jabatan_pegawai;
import com.apap.tugas1.model.Pegawai;
import com.apap.tugas1.repository.Jabatan_pegawaiDB;
import com.apap.tugas1.repository.PegawaiDB;


/*
 * PilotServiceImpl
 */
@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDB pegawaiDB;
	
	@Autowired
	private Jabatan_pegawaiDB jabatan_pegawaiDB;
	
	@Override
	public Pegawai getPegawaiById(Long id) {
		// TODO Auto-generated method stub
		return pegawaiDB.getOne(id);
	}

	@Override
	public Pegawai getPegawaiByNIP(String NIP) {
		// TODO Auto-generated method stub
		return pegawaiDB.findByNip(NIP);
	}

	@Override
	public List<Jabatan_pegawai> getJabatanByPegawai(Pegawai pegawai) {
		// TODO Auto-generated method stub
		return jabatan_pegawaiDB.findByPegawai(pegawai);
	}

	@Override
	public List<Pegawai> getPegawaiByInstansi(Instansi a) {
		// TODO Auto-generated method stub
		return pegawaiDB.findByInstansi(a);
	}
	
}
