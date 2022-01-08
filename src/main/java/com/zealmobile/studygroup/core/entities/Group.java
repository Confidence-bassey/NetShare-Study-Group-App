package com.zealmobile.studygroup.core.entities;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.zealmobile.studygroup.core.models.enums.GroupStatus;

import lombok.Data;

@Entity
@Table(name = "groups")
@Data
public class Group {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	
	private String name;

	@Column(name="owner_id")
	private Long ownerId;

	@Column(name="created_date")
	private Date createDate;

	@Column(name="status")
	private GroupStatus status;

	@ManyToOne()
	@JoinColumn(name="owner_id", referencedColumnName="id", insertable=false, updatable=false)
	private UserAccount Owner;

	@OneToMany()
	private Set<GroupMember> groupMembers;
}
