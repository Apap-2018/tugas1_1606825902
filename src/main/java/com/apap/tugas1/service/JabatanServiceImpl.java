package com.apap.tugas1.service;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.Jabatan;
import com.apap.tugas1.model.Jabatan_pegawai;
import com.apap.tugas1.repository.JabatanDB;
import com.apap.tugas1.repository.Jabatan_pegawaiDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	
	@Autowired
	private JabatanDB jabatanDB;
	
	@Autowired
	private Jabatan_pegawaiDB jabatan_pegawaiDB;
	
	@Override
	public void addJabatan(Jabatan baru) {
		// TODO Auto-generated method stub
		jabatanDB.save(baru);
		
	}

	@Override
	public List<Jabatan> getlistJabatan() {
		// TODO Auto-generated method stub
		return jabatanDB.findAll();
	}

	@Override
	public Jabatan getJabatanById(Long id) {
		// TODO Auto-generated method stub
		return jabatanDB.getJabatanByid(id);
	}

	@Override
	public void UpdateJabatan(Long id, Jabatan baru) {
		// TODO Auto-generated method stub
		Jabatan updateJabatan = getJabatanById(id);
		updateJabatan.setNama(baru.getNama());
		updateJabatan.setDeskripsi(baru.getDeskripsi());
		updateJabatan.setGajiPokok(baru.getGajiPokok());
		jabatanDB.save(updateJabatan);
		
	}

	@Override
	public Boolean deleteJabatan(Long id) {
		// TODO Auto-generated method stu
		boolean con= false;
		List<Jabatan_pegawai> jabatan = jabatan_pegawaiDB.findByJabatan(getJabatanById(id));
		if(jabatan.size()==0) {
			jabatanDB.deleteById(id);
			con = true;
		}
		return con;
	}

	@Override
	public Integer sebaranPegawai(Jabatan Jabatan) {
		// TODO Auto-generated method stub
		return jabatan_pegawaiDB.findByJabatan(Jabatan).size();
	}

	@Override
	public List<Jabatan_pegawai> findJabatanAll() {
		// TODO Auto-generated method stub
		return jabatan_pegawaiDB.findAll();
	}

	@Override
	public List<Jabatan_pegawai> findJabatan(Jabatan jabatan) {
		// TODO Auto-generated method stub
		return jabatan_pegawaiDB.findByJabatan(jabatan);
	}

	@Override
	public String getGajiJabatan(Jabatan jabatan) {
		// TODO Auto-generated method stub
		double gaji = jabatan.getGajiPokok();
		DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
		DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
		
		formatRp.setCurrencySymbol("Rp. ");
		formatRp.setMonetaryDecimalSeparator('.');
		formatRp.setGroupingSeparator('.');
		
		kursIndonesia.setDecimalFormatSymbols(formatRp);
		return kursIndonesia.format(gaji);
		
	}

}
