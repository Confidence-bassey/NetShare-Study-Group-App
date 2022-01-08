package com.zealmobile.studygroup.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.zealmobile.studygroup.core.models.enums.GroupMembershipStatus;
import com.zealmobile.studygroup.core.models.enums.GroupMembershipType;

import lombok.Data;

@Entity
@Table(name="group_users")
@Data
public class GroupMember {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name="group_id")
    private int groupId;

    @Column(name="membership_type")
    private GroupMembershipType membershipType;

    @Column(name="membership_status")
    private GroupMembershipStatus membershipStatus;


    @ManyToOne()
    @JoinColumn(name="group_id", referencedColumnName="id", insertable=false, updatable=false)
    private Group Group;
    
}
