package com.apap.tugas1.service;
import java.util.List;

import com.apap.tugas1.model.Provinsi;
public interface ProvinsiService {
	List<Provinsi> getAll();
	Provinsi getProvinsiById(Long id);
}
