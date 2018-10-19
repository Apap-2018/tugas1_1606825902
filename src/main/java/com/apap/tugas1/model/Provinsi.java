package com.apap.tugas1.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="provinsi")
public class Provinsi {
	
	@Id
	@Size(max = 10)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	@Size(max = 255)
	@Column(name = "nama", nullable=false)
	private String nama;
	
	@NotNull
	@Column(name = "presentase_tunjangan", nullable=false)
	private Double tunjangan;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public Double getTunjangan() {
		return tunjangan;
	}

	public void setTunjangan(Double tunjangan) {
		this.tunjangan = tunjangan;
	}
}
