package com.apap.tugas1.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.Instansi;
import com.apap.tugas1.model.Jabatan;
import com.apap.tugas1.model.Jabatan_pegawai;
import com.apap.tugas1.model.Pegawai;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;
import com.apap.tugas1.model.Provinsi;

@Controller
public class PegawaiController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@Autowired
	private InstansiService instansiService;
	
	@RequestMapping("/")
	private String home(Model model) {
		List<Jabatan> listJabatan = jabatanService.getlistJabatan();
		List<Instansi> listInstansi = instansiService.getListInstansi();
		model.addAttribute("listJabatan", listJabatan);
		model.addAttribute("listInstansi", listInstansi);
		return "home";
	}
	
	@RequestMapping(value = "/pegawai/view", method=RequestMethod.GET)
	private String viewPegawai(@RequestParam(value="NIP") String NIP, Model model) {
		Pegawai pegawai = pegawaiService.getPegawaiByNIP(NIP);
		double gajiPokok=0;
		double gaji;
		List<Jabatan_pegawai> jabatan = pegawaiService.getJabatanByPegawai(pegawai);
		for(Jabatan_pegawai jb : jabatan) {
			gajiPokok=Math.max(gajiPokok, jb.getJabatan().getGajiPokok());
		}
		gaji =gajiPokok+(gajiPokok*(pegawai.getInstansi().getProvinsi().getTunjangan()/100));
		String fixGaji = String.format("%.2f",gaji);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatan", jabatan);
		model.addAttribute("gaji", fixGaji);
		return "view-pegawai";
	}
	
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.GET)
	private String tambah(Model model) {
		Pegawai 
		model.addAttribute("pegawai", new Pegawai());
		model.
		List<Provinsi> list = provinsiService.getAll();
		model.addAttribute("listProvinsi", list);
		return "addPegawai";
	}
	
	@RequestMapping(value= "/instansi/get", method = RequestMethod.GET)
	public @ResponseBody List<Instansi> getInstansi(@RequestParam("provinsi") long id, Model model) {
    	List<Instansi> Instansi = instansiService.getListInstansi();
    	List<Instansi> instansiProvinsi = new ArrayList<Instansi>();
    	for (Instansi i: Instansi) {
    		if (i.getProvinsi().getId()==id) {
    			instansiProvinsi.add(i);
    		}
    	}
    	return instansiProvinsi;
	}
	@RequestMapping(value="/pegawai/termuda-tertua", method=RequestMethod.GET)
	private String lihatPegawaiTer(@RequestParam(value="idInstansi") Long idInstansi, Model model) {
		Instansi ins = instansiService.getInstansiById(idInstansi);
		List<Pegawai> terpilih = pegawaiService.getPegawaiByInstansi(ins);
		Pegawai pegawaiMuda = new Pegawai();
		Pegawai pegawaiTua = new Pegawai();
		Date muda = terpilih.get(0).getTanggalLahir();
		Date tua = terpilih.get(0).getTanggalLahir();
		for(Pegawai e: terpilih) {
			if(e.getTanggalLahir().after(muda)||e.getTanggalLahir().equals(muda)) {
				muda=e.getTanggalLahir();
				pegawaiMuda=e;
			}
		}
		for(Pegawai e: terpilih) {
			if(e.getTanggalLahir().before(tua)||e.getTanggalLahir().equals(tua)) {
				tua=e.getTanggalLahir();
				pegawaiTua=e;
			}
		}
		List<Jabatan_pegawai> jabatanmuda = pegawaiService.getJabatanByPegawai(pegawaiMuda);
		List<Jabatan_pegawai> jabatantua = pegawaiService.getJabatanByPegawai(pegawaiTua);
		model.addAttribute("pegawaimuda", pegawaiMuda);
		model.addAttribute("pegawaitua", pegawaiTua);
		model.addAttribute("jabatanmuda", jabatanmuda);
		model.addAttribute("jabatantua", jabatantua);
		return "view-young-old";
	}
	
	@RequestMapping(value="/pegawai/cari", method=RequestMethod.GET)
	private String cariPegawai(@RequestParam(value="idProvinsi", required=false, defaultValue="0") Long id_provinsi,
			@RequestParam(value="idInstansi", required=false, defaultValue ="0") Long id_instansi,
			@RequestParam(value="idJabatan", required=false, defaultValue ="0") Long id_jabatan, Model model) {
			List<Provinsi> listProvinsi = provinsiService.getAll();
			List<Instansi> listInstansi = instansiService.getListInstansi();
			List<Jabatan> listJabatan = jabatanService.getlistJabatan();
			model.addAttribute("listProvinsi", listProvinsi);
			model.addAttribute("listInstansi", listInstansi);
			model.addAttribute("listJabatan", listJabatan);
			if(id_provinsi==0&&id_instansi==0&&id_jabatan==0) {
				return "cari-pegawai";
			}
			else if(id_provinsi!=0&&id_instansi==0&&id_jabatan==0) {
				Provinsi provinsiTerpilih;
				List<Pegawai> listPegawai;
				List<Jabatan_pegawai> jabatan;
				Map<Pegawai, List<Jabatan_pegawai>> dataJabatan = new HashMap<Pegawai, List<Jabatan_pegawai>>();
				List<Pegawai> targetPegawai = new ArrayList<Pegawai>();
				provinsiTerpilih = provinsiService.getProvinsiById(id_provinsi);
				for(Instansi e: listInstansi) {
					if(e.getProvinsi().getNama().equalsIgnoreCase(provinsiTerpilih.getNama())) {
						listPegawai=pegawaiService.getPegawaiByInstansi(e);
						for(Pegawai a:listPegawai) {
							jabatan = pegawaiService.getJabatanByPegawai(a);
							dataJabatan.put(a, jabatan);
							targetPegawai.add(a);
						}
					}
				}
				model.addAttribute("dataJabatan", dataJabatan);
				model.addAttribute("hasil", targetPegawai);
			}
			else if(id_provinsi==0&&id_instansi!=0&&id_jabatan==0) {
				Instansi instansiTerpilih = instansiService.getInstansiById(id_instansi);
				Map<Pegawai, List<Jabatan_pegawai>> dataJabatan = new HashMap<Pegawai, List<Jabatan_pegawai>>();
				List<Pegawai> listPegawai=pegawaiService.getPegawaiByInstansi(instansiTerpilih);
				List<Pegawai> targetPegawai = new ArrayList<Pegawai>();
				List<Jabatan_pegawai> jabatan;
				listPegawai=pegawaiService.getPegawaiByInstansi(instansiTerpilih);
				for(Pegawai a:listPegawai) {
					jabatan = pegawaiService.getJabatanByPegawai(a);
					dataJabatan.put(a, jabatan);
					targetPegawai.add(a);
				}
				model.addAttribute("hasil", targetPegawai);
				model.addAttribute("dataJabatan", dataJabatan);
			}
			else if(id_provinsi==0&&id_instansi==0&&id_jabatan!=0) {
				Jabatan jabatanTerpilih;
				List<Pegawai> targetPegawai = new ArrayList<Pegawai>();
				List<Jabatan_pegawai> listJabatanPegawai = jabatanService.findJabatanAll();
				Map<Pegawai, List<Jabatan_pegawai>> dataJabatan = new HashMap<Pegawai, List<Jabatan_pegawai>>();
				List<Jabatan_pegawai> jabatan;
				jabatanTerpilih = jabatanService.getJabatanById(id_jabatan);
				listJabatanPegawai= jabatanService.findJabatan(jabatanTerpilih);
				for(Jabatan_pegawai a :listJabatanPegawai) {
					jabatan = pegawaiService.getJabatanByPegawai(a.getPegawai());
					dataJabatan.put(a.getPegawai(), jabatan);
					targetPegawai.add(a.getPegawai());
				}
				model.addAttribute("hasil", targetPegawai);
				model.addAttribute("dataJabatan", dataJabatan);
			}
			else if(id_provinsi!=0&&id_instansi!=0&&id_jabatan==0) {
				Provinsi provinsiTerpilih;
				Instansi instansiTerpilih;
				List<Pegawai> listPegawai;
				List<Pegawai> targetPegawai = new ArrayList<Pegawai>();
				provinsiTerpilih = provinsiService.getProvinsiById(id_provinsi);
				instansiTerpilih = instansiService.getInstansiById(id_instansi);
				Map<Pegawai, List<Jabatan_pegawai>> dataJabatan = new HashMap<Pegawai, List<Jabatan_pegawai>>();
				List<Jabatan_pegawai> jabatan;
				if(instansiTerpilih.getProvinsi().getId().equals(provinsiTerpilih.getId())) {
					listPegawai=pegawaiService.getPegawaiByInstansi(instansiTerpilih);
					for(Pegawai a:listPegawai) {
						jabatan = pegawaiService.getJabatanByPegawai(a);
						dataJabatan.put(a, jabatan);
						targetPegawai.add(a);
					}
				}
				model.addAttribute("hasil", targetPegawai);
				model.addAttribute("dataJabatan", dataJabatan);
			}
			else if(id_provinsi!=0&&id_instansi==0&&id_jabatan!=0) {
				Provinsi provinsiTerpilih;
				Jabatan jabatanTerpilih;
				List<Pegawai> targetPegawai = new ArrayList<Pegawai>();
				List<Jabatan_pegawai> listJabatanPegawai = jabatanService.findJabatanAll();
				provinsiTerpilih = provinsiService.getProvinsiById(id_provinsi);
				jabatanTerpilih = jabatanService.getJabatanById(id_jabatan);
				listJabatanPegawai= jabatanService.findJabatan(jabatanTerpilih);
				Map<Pegawai, List<Jabatan_pegawai>> dataJabatan = new HashMap<Pegawai, List<Jabatan_pegawai>>();
				List<Jabatan_pegawai> jabatan;
				for(Jabatan_pegawai a :listJabatanPegawai) {
					if(a.getPegawai().getInstansi().getProvinsi().equals(provinsiTerpilih)) {
						jabatan = pegawaiService.getJabatanByPegawai(a.getPegawai());
						dataJabatan.put(a.getPegawai(), jabatan);
						targetPegawai.add(a.getPegawai());
					}
				}
				model.addAttribute("hasil", targetPegawai);
				model.addAttribute("dataJabatan", dataJabatan);
			}
			else if(id_provinsi==0&&id_instansi!=0&&id_jabatan!=0) {
				Instansi instansiTerpilih;
				Jabatan jabatanTerpilih;
				List<Pegawai> targetPegawai = new ArrayList<Pegawai>();
				List<Jabatan_pegawai> listJabatanPegawai = jabatanService.findJabatanAll();
				Map<Pegawai, List<Jabatan_pegawai>> dataJabatan = new HashMap<Pegawai, List<Jabatan_pegawai>>();
				List<Jabatan_pegawai> jabatan;
				instansiTerpilih = instansiService.getInstansiById(id_instansi);
				jabatanTerpilih = jabatanService.getJabatanById(id_jabatan);
				listJabatanPegawai= jabatanService.findJabatan(jabatanTerpilih);
				for(Jabatan_pegawai a: listJabatanPegawai) {
					if(a.getPegawai().getInstansi().equals(instansiTerpilih)) {
						jabatan = pegawaiService.getJabatanByPegawai(a.getPegawai());
						dataJabatan.put(a.getPegawai(), jabatan);
						targetPegawai.add(a.getPegawai());
					}
				}
				model.addAttribute("hasil", targetPegawai);
				model.addAttribute("dataJabatan", dataJabatan);
			}
			else if(id_provinsi!=0&&id_instansi!=0&&id_jabatan!=0) {
				Provinsi provinsiTerpilih;
				Instansi instansiTerpilih;
				Jabatan jabatanTerpilih;
				List<Pegawai> targetPegawai = new ArrayList<Pegawai>();
				List<Jabatan_pegawai> listJabatanPegawai = jabatanService.findJabatanAll();
				Map<Pegawai, List<Jabatan_pegawai>> dataJabatan = new HashMap<Pegawai, List<Jabatan_pegawai>>();
				List<Jabatan_pegawai> jabatan;
				provinsiTerpilih = provinsiService.getProvinsiById(id_provinsi);
				instansiTerpilih = instansiService.getInstansiById(id_instansi);
				jabatanTerpilih = jabatanService.getJabatanById(id_jabatan);
				listJabatanPegawai= jabatanService.findJabatan(jabatanTerpilih);
				for(Jabatan_pegawai a: listJabatanPegawai) {
					if(a.getPegawai().getInstansi().equals(instansiTerpilih)&&a.getPegawai().getInstansi().getProvinsi().equals(provinsiTerpilih)) {
						targetPegawai.add(a.getPegawai());
						jabatan = pegawaiService.getJabatanByPegawai(a.getPegawai());
						dataJabatan.put(a.getPegawai(), jabatan);
					}
				}
				model.addAttribute("hasil", targetPegawai);
				model.addAttribute("dataJabatan", dataJabatan);
			}
			return "cari-pegawai";
	}
}
