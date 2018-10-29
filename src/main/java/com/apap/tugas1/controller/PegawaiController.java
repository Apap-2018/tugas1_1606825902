package com.apap.tugas1.controller;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.Instansi;
import com.apap.tugas1.model.Jabatan;
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
	/*
	 * Request Mapping untuk fitur-1: Melihat Data Pegawai
	 */
	@RequestMapping(value = "/pegawai/view", method=RequestMethod.GET)
	private String viewPegawai(@RequestParam(value="NIP") String NIP, Model model) {
		Pegawai pegawai = pegawaiService.getPegawaiByNIP(NIP);
		String Gaji = pegawaiService.getGajiPegawai(pegawai.getJabatan(), pegawai);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("jabatan", pegawai.getJabatan());
		model.addAttribute("gaji", Gaji);
		return "view-pegawai";
	}
	
	/*
	 * Request Mapping untuk fitur-2: Menambah Pegawai
	 */
	@RequestMapping(value="/pegawai/tambah", method=RequestMethod.GET)
	private String tambah(Model model) {
		Pegawai pegawai = new Pegawai();
		Jabatan jabatan = new Jabatan();
		List<Jabatan> listJabatanPegawai = new ArrayList<Jabatan>();
		listJabatanPegawai.add(jabatan);
		pegawai.setJabatan(listJabatanPegawai);
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("listProvinsi", provinsiService.getAll());
		model.addAttribute("listJabatan", jabatanService.getlistJabatan());
		return "addPegawai";
	}
	
	
	@RequestMapping(value="/pegawai/tambah",params= {"addRow"}, method=RequestMethod.POST)
	private String addJabatanPegawai(@ModelAttribute Pegawai pegawai, BindingResult bindingResult, Model model) {
		if(pegawai.getJabatan()==null) {
			pegawai.setJabatan(new ArrayList<Jabatan>());
		}
		pegawai.getJabatan().add(new Jabatan());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", instansiService.getListInstansi());
		model.addAttribute("listProvinsi", provinsiService.getAll());
		model.addAttribute("listJabatan", jabatanService.getlistJabatan());
		return "addPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", params= {"pegawaiSubmit"}, method=RequestMethod.POST)
    public String submitAddPegawai(@ModelAttribute Pegawai pegawai,Model model) {
		pegawaiService.addPegawai(pegawai);
		model.addAttribute("kata", "Pegawai");
		return "berhasilTambah";
    }
	
	@RequestMapping(value="/pegawai/tambah",params= {"deleteRow"}, method=RequestMethod.POST)
	private String deleteRowJabatan(@ModelAttribute Pegawai pegawai,BindingResult bindingResult, Model model, HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("deleteRow"));
	    pegawai.getJabatan().remove(rowId.intValue());
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("instansi", instansiService.getListInstansi());
		model.addAttribute("listProvinsi", provinsiService.getAll());
		model.addAttribute("listJabatan", jabatanService.getlistJabatan());
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
	
	@RequestMapping(value = "/pegawai/ubah", method = RequestMethod.GET)
	private String ubahPegawaiView(@RequestParam(value="nip") String nip, Model model) {
		Pegawai pegawai = pegawaiService.getPegawaiByNIP(nip);
        model.addAttribute("listJabatan", jabatanService.getlistJabatan());
	    model.addAttribute("listInstansi", instansiService.getListInstansi());
	    model.addAttribute("listProvinsi", provinsiService.getAll());
	    model.addAttribute("provinsiSelected", pegawai.getInstansi().getProvinsi());
	    model.addAttribute("instansiSelected", pegawai.getInstansi());
		model.addAttribute("pegawai", pegawai);
		return "updatePegawai";
	}
    
    @RequestMapping(value = "/pegawai/ubah", params= {"newRowUpdate"}, method=RequestMethod.POST)
	private String newRowUpdate(@ModelAttribute Pegawai pegawai, BindingResult bindingResult, Model model) {
		if (pegawai.getJabatan()== null) {
			pegawai.setJabatan(new ArrayList<Jabatan>());
		}
    	pegawai.getJabatan().add(new Jabatan());
		model.addAttribute("pegawai", pegawai);
        model.addAttribute("listJabatan", jabatanService.getlistJabatan());
	    model.addAttribute("listInstansi", instansiService.getListInstansi());
	    model.addAttribute("listProvinsi", provinsiService.getAll());
	    model.addAttribute("provinsiSelected", pegawai.getInstansi().getProvinsi());
	    model.addAttribute("instansiSelected", pegawai.getInstansi());
		return "updatePegawai";
	}
    
    @RequestMapping(value="/pegawai/ubah",params= {"delRowUpdate"}, method=RequestMethod.POST)
	private String delRowUpdate(@ModelAttribute Pegawai pegawai,BindingResult bindingResult, Model model, HttpServletRequest req) {
		final Integer rowId = Integer.valueOf(req.getParameter("delRowUpdate"));
	    pegawai.getJabatan().remove(rowId.intValue());
	    model.addAttribute("pegawai", pegawai);
        model.addAttribute("listJabatan", jabatanService.getlistJabatan());
	    model.addAttribute("listInstansi", instansiService.getListInstansi());
	    model.addAttribute("listProvinsi", provinsiService.getAll());
	    model.addAttribute("provinsiSelected", pegawai.getInstansi().getProvinsi());
	    model.addAttribute("instansiSelected", pegawai.getInstansi());
		return "updatePegawai";
	}
   
    @RequestMapping(value="/pegawai/ubah",params= {"submitUpdate"}, method=RequestMethod.POST)
    public String submitUbahPegawai(@ModelAttribute Pegawai pegawai,Model model) {
		pegawaiService.updatePegawai(pegawai);
		model.addAttribute("kata", "Pegawai");
        return "update";
    }
    
    /*
     * RequestMapping untuk fitur-10: Menampilkan Pegawai Tertua dan Termuda di Instansi
     */
	@RequestMapping(value="/pegawai/termuda-tertua", method=RequestMethod.GET)
	private String lihatPegawaiTer(@RequestParam(value="idInstansi") Long idInstansi, Model model) {
		Instansi instansi = instansiService.getInstansiById(idInstansi);
		List<Pegawai> terpilih = pegawaiService.getPegawaiByInstansi(instansi);
		Map<String, Pegawai> hasilPencarian = instansiService.getPegawaiMudaTua(terpilih);
		model.addAttribute("PegawaiTermuda", hasilPencarian.get("PegawaiTermuda"));
		model.addAttribute("PegawaiTertua" , hasilPencarian.get("PegawaiTertua"));
		return "view-young-old";
	}
	
	/*
	 * Request Mapping untuk fitur-4 : Mencari data pegawai berdasarkan filter tertentu
	 */
	
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
			else {
				List<Pegawai> targetPegawai = pegawaiService.cariPegawaiByFilter(id_provinsi, id_instansi, id_jabatan);
				model.addAttribute("hasil", targetPegawai);
			}
			return "cari-pegawai";
	}
}
