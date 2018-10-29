package com.apap.tugas1.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tugas1.model.Jabatan;
import com.apap.tugas1.service.JabatanService;


@Controller
public class JabatanController {
	
	@Autowired
	private JabatanService jabatanService;
	
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.GET)
	private String tambah(Model model) {
		List<Integer> gaji = new ArrayList<Integer>();
		for (int awal=500000; awal<=75000000; awal+=1000000) {
			gaji.add(awal);
		}
		gaji.add(75000000);
		model.addAttribute("jabatan", new Jabatan());
		model.addAttribute("listGaji", gaji);
		return "addJabatan";
	}
	
	@RequestMapping(value="/jabatan/tambah", method=RequestMethod.POST)
	private String addPilotSubmit(@ModelAttribute Jabatan jabatan, Model model) {
		jabatanService.addJabatan(jabatan);
		model.addAttribute("kata", "Jabatan");
		return "berhasilTambah";
	}
	
	@RequestMapping(value = "/jabatan/view", method=RequestMethod.GET)
	private String viewJabatan(@RequestParam(value="id") Long id, Model model) {
		Jabatan jabatan = jabatanService.getJabatanById(id);
		model.addAttribute("gaji", jabatanService.getGajiJabatan(jabatan));
		model.addAttribute("jabatan", jabatan);
		return "view-jabatan";
	}
	
	@RequestMapping(value = "/jabatan/ubah", method=RequestMethod.GET)
	private String ubahJabatan(@RequestParam(value="id") Long id, Model model) {
		List<Integer> gaji = new ArrayList<Integer>();
		for (int awal=500000; awal<=75000000; awal+=1000000) {
			gaji.add(awal);
		}
		gaji.add(75000000);
		model.addAttribute("jabatanBaru", new Jabatan());
		Jabatan jabatan = jabatanService.getJabatanById(id);
		model.addAttribute("jabatanLama", jabatan);
		model.addAttribute("listGaji", gaji);
		model.addAttribute("gaji", jabatanService.getGajiJabatan(jabatan));
		return "ubah-jabatan";
	}
	
	@RequestMapping(value="/jabatan/ubah", method=RequestMethod.POST)
	public String ubahJabatanSubmit(@ModelAttribute Jabatan jabatanBaru, Model model) {
		Long id = jabatanBaru.getId();
		jabatanService.UpdateJabatan(id, jabatanBaru);
		model.addAttribute("kata", "Jabatan");
		return "update";
	}
	
	@RequestMapping(value="/jabatan/hapus/{id}", method=RequestMethod.GET)
	public String deletePilot(@PathVariable(value="id") Long id, Model model) {
		boolean hapus = jabatanService.deleteJabatan(id);
		model.addAttribute("kondisi", hapus);
		model.addAttribute("id", id);
		return "deletePage";
	}
	
	@RequestMapping(value="/jabatan/viewall", method=RequestMethod.GET)
	public String viewAll(Model model) {
		List<Jabatan> list = jabatanService.getlistJabatan();
		model.addAttribute("listJabatan", list);
		return "view-all";
	}
	
	@RequestMapping(value="/jabatan/sebaran", method=RequestMethod.GET)
	public String sebaranKaryawan(Model model) {
		List<Jabatan> list = jabatanService.getlistJabatan();
		Map<Jabatan, Integer> dataJabatan = new HashMap<Jabatan, Integer>();
		for(Jabatan e: list) {
			dataJabatan.put(e, jabatanService.sebaranPegawai(e));
		}
		model.addAttribute("detailJabatan", dataJabatan);
		return "sebaran-karyawan";
	}
}
