package com.apap.tugas1.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.Instansi;
import com.apap.tugas1.model.Pegawai;
import com.apap.tugas1.repository.InstansiDB;

@Service
@Transactional
public class InstansiServiceImpl implements InstansiService {
	
	@Autowired
	private InstansiDB instansiDB;
	
	@Override
	public Instansi getInstansiById(Long id) {
		// TODO Auto-generated method stub
		return instansiDB.getOne(id);
	}

	@Override
	public List<Instansi> getListInstansi() {
		// TODO Auto-generated method stub
		return instansiDB.findAll();
	}

	@Override
	public Map<String, Pegawai> getPegawaiMudaTua(List<Pegawai> pegawaiInstansi) {
		// TODO Auto-generated method stub
		Map<String, Pegawai> hasil = new HashMap<String, Pegawai>();
		Date termuda = pegawaiInstansi.get(0).getTanggalLahir();
		Date tertua = pegawaiInstansi.get(0).getTanggalLahir();
		for(Pegawai pegawai:pegawaiInstansi) {
			if(pegawai.getTanggalLahir().after(termuda)||pegawai.getTanggalLahir().equals(termuda)) {
				termuda = pegawai.getTanggalLahir();
				hasil.put("PegawaiTermuda", pegawai);
			}
		}
		for (Pegawai pegawai:pegawaiInstansi) {
			if(pegawai.getTanggalLahir().before(tertua)||pegawai.getTanggalLahir().equals(tertua)) {
				tertua = pegawai.getTanggalLahir();
				hasil.put("PegawaiTertua", pegawai);
			}
		}
		return hasil;
	}

	

}
