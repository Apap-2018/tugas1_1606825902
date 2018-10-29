package com.apap.tugas1.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.Instansi;
import com.apap.tugas1.model.Jabatan;
import com.apap.tugas1.model.Pegawai;
import com.apap.tugas1.model.Provinsi;
import com.apap.tugas1.repository.InstansiDB;
import com.apap.tugas1.repository.JabatanDB;
import com.apap.tugas1.repository.PegawaiDB;
import com.apap.tugas1.repository.ProvinsiDB;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/*
 * PilotServiceImpl
 */
@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	@Autowired
	private PegawaiDB pegawaiDB;
	
	@Autowired
	private JabatanDB jabatanDB;
	
	@Autowired
	private ProvinsiDB provinsiDB;
	
	@Autowired
	private InstansiDB instansiDB;
	
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
	public List<Pegawai> getPegawaiByInstansi(Instansi a) {
		// TODO Auto-generated method stub
		return pegawaiDB.findByInstansi(a);
	}

	@Override
	public void addPegawai(Pegawai pegawai) {
		pegawai.setNip(this.nipGenerator(pegawai));
		pegawaiDB.save(pegawai);
		
	}
	public String nipGenerator (Pegawai pegawai) {
		String generatedNip;
		String kodeUrutanLahirMasuk;
		String kodeTglLahir = pegawai.getTanggalLahir().toString();
		kodeTglLahir = kodeTglLahir.substring(8) + kodeTglLahir.substring(5, 7) + kodeTglLahir.substring(2, 4);	
			List<Pegawai> list = pegawaiDB.findByTahunMasukAndTanggalLahir(pegawai.getTahunMasuk(), pegawai.getTanggalLahir());
			list.add(pegawai);
			kodeUrutanLahirMasuk = Integer.toString(list.size());
			if (Integer.parseInt(kodeUrutanLahirMasuk) < 10) {
				kodeUrutanLahirMasuk = "0" + kodeUrutanLahirMasuk;
			}
			generatedNip = Long.toString(pegawai.getInstansi().getId()) + kodeTglLahir + pegawai.getTahunMasuk() + kodeUrutanLahirMasuk;
			return generatedNip;
		}

	@Override
	public void updatePegawai(Pegawai pegawai) {
		Pegawai oldPegawai =pegawaiDB.getOne(pegawai.getId());
		oldPegawai.setNip(this.nipGenerator(pegawai));
		oldPegawai.setNama(pegawai.getNama());
		oldPegawai.setInstansi(pegawai.getInstansi());
		oldPegawai.setJabatan(pegawai.getJabatan());
		oldPegawai.setTahunMasuk(pegawai.getTahunMasuk());
		oldPegawai.setTanggalLahir(pegawai.getTanggalLahir());
		oldPegawai.setTempatLahir(pegawai.getTempatLahir());
		
	}

	@Override
	public String getGajiPegawai(List<Jabatan> jabatan, Pegawai pegawai) {
		// TODO Auto-generated method stub
		double gajiPokok=0;
		double gaji;
		for(Jabatan getJabatan : jabatan) {
			gajiPokok=Math.max(gajiPokok, getJabatan.getGajiPokok());
		}
		gaji =gajiPokok+(gajiPokok*(pegawai.getInstansi().getProvinsi().getTunjangan()/100));
		DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
		DecimalFormatSymbols formatRp = new DecimalFormatSymbols();
		
		formatRp.setCurrencySymbol("Rp. ");
		formatRp.setMonetaryDecimalSeparator('.');
		formatRp.setGroupingSeparator('.');
		
		kursIndonesia.setDecimalFormatSymbols(formatRp);
		
		return kursIndonesia.format(gaji);
	}

	@Override
	public List<Pegawai> cariPegawaiByFilter(Long id_provinsi, Long id_instansi, Long id_jabatan) {
		// TODO Auto-generated method stub
		List<Pegawai> hasilPencarian = new ArrayList<Pegawai>();
		List<Pegawai> semuaPegawai = pegawaiDB.findAll();
		if(id_provinsi!=0&&id_instansi==0&&id_jabatan==0) {
			Provinsi provinsi = provinsiDB.findById(id_provinsi).get();
			for(Pegawai pegawai:semuaPegawai) {
				if(pegawai.getInstansi().getProvinsi().equals(provinsi)) {
					hasilPencarian.add(pegawai);
				}
			}
		}
		else if(id_provinsi==0&&id_instansi!=0&&id_jabatan==0) {
			hasilPencarian = this.getPegawaiByInstansi(instansiDB.getOne(id_instansi));
		}
		else if(id_provinsi==0&&id_instansi==0&&id_jabatan!=0) {
			List<Jabatan> jabatan = new ArrayList<Jabatan>();
			jabatan.add(jabatanDB.getJabatanByid(id_jabatan));
			hasilPencarian = pegawaiDB.findByJabatanIn(jabatan);
		}
		else if(id_provinsi!=0&&id_instansi!=0&&id_jabatan==0) {
			Instansi instansiTerpilih = instansiDB.findByProvinsiAndId(provinsiDB.findById(id_provinsi).get(),id_instansi);
			hasilPencarian = this.getPegawaiByInstansi(instansiTerpilih);
		}
		else if(id_provinsi!=0&&id_instansi==0&&id_jabatan!=0) {
			List<Instansi> instansi = instansiDB.findByProvinsi(provinsiDB.findById(id_provinsi).get());
			List<Jabatan> listJabatan = new ArrayList<Jabatan>();
			listJabatan.add(jabatanDB.getJabatanByid(id_jabatan));
			for(Instansi a: instansi) {
				for(Pegawai b: pegawaiDB.findByInstansiAndJabatanIn(a, listJabatan)) {
					hasilPencarian.add(b);
				}
			}
		}
		else if(id_provinsi==0&&id_instansi!=0&&id_jabatan!=0) {
			List<Jabatan> listJabatan = new ArrayList<Jabatan>();
			listJabatan.add(jabatanDB.getJabatanByid(id_jabatan));
			hasilPencarian = pegawaiDB.findByInstansiAndJabatanIn(instansiDB.findById(id_instansi).get(), listJabatan);
		}
		else if(id_provinsi!=0&&id_instansi!=0&&id_jabatan!=0) {
			List<Jabatan> listJabatan = new ArrayList<Jabatan>();
			listJabatan.add(jabatanDB.getJabatanByid(id_jabatan));
			hasilPencarian = pegawaiDB.findByInstansiAndJabatanIn(instansiDB.findById(id_instansi).get(), listJabatan);
		}
		return hasilPencarian;
	}
	
}
