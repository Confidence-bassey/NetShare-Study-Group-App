package com.zealmobile.studygroup.core.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.zealmobile.studygroup.core.models.enums.GroupStatus;

import lombok.Data;

@Entity
@Table(name = "groups")
@Data
public class Group {

	
	private int id;
	private String name;
	private String owner;
	private Date createDate;
	private GroupStatus status;
	
}
