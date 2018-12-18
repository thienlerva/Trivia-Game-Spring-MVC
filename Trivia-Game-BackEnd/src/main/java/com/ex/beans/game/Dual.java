package com.ex.beans.game;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;


@Component
@Entity //registers class as entity in DB
@Table(name="DUAL")
public class Dual {

	@Id
	public String Dual;
	public String getDual() {
		return "Dual";
	}
	
	
	public Dual(){
	
	}
	
}
