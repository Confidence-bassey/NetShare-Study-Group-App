package com.zealmobile.studygroup.services;

import java.util.Date;
import java.util.Optional;

import com.zealmobile.studygroup.core.entities.Group;
import com.zealmobile.studygroup.core.entities.GroupMember;
import com.zealmobile.studygroup.core.entities.UserAccount;
import com.zealmobile.studygroup.core.models.dtos.AddGroupMemberPayload;
import com.zealmobile.studygroup.core.models.dtos.CreateGroupResult;
import com.zealmobile.studygroup.core.models.dtos.GroupMembershipAddResult;
import com.zealmobile.studygroup.core.models.dtos.NewGroupPayload;
import com.zealmobile.studygroup.core.models.enums.AccountStatus;
import com.zealmobile.studygroup.core.models.enums.GroupMembershipStatus;
import com.zealmobile.studygroup.core.models.enums.GroupMembershipType;
import com.zealmobile.studygroup.core.models.enums.GroupStatus;
import com.zealmobile.studygroup.dao.respositories.GroupMembershipRepository;
import com.zealmobile.studygroup.dao.respositories.GroupRepository;
import com.zealmobile.studygroup.dao.respositories.UserRespository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

@Service
public class GroupService {

    private GroupMembershipRepository _groupMembershipRepository;
    private GroupRepository _groupRepository;
    private UserRespository _userAccountRepository;

    Logger logs = LoggerFactory.getLogger("GroupService.class");

    @Autowired
    public GroupService(GroupMembershipRepository groupMembershipRepository, UserRespository repo,
    GroupRepository groupRepository
    ) {
        this._groupMembershipRepository = groupMembershipRepository;
        this._userAccountRepository = repo;
        this._groupRepository = groupRepository;
    }
    public CreateGroupResult createGroup(NewGroupPayload groupInfo) {

        try{
            Optional<UserAccount> userAccount = _userAccountRepository.findById(groupInfo.getOwnerId());
            if(!userAccount.isPresent() || userAccount.get().getStatus() !=AccountStatus.Active)
                return new CreateGroupResult(false, String.format("User with Id %d was not found or has an inactive account", groupInfo.getOwnerId()));

            Group groupToCreate = new Group();
            groupToCreate.setOwnerId(groupInfo.getOwnerId());
            groupToCreate.setName(groupInfo.getName());

            boolean groupExistAlready = this._groupRepository.exists(Example.of(groupToCreate));
            if(groupExistAlready)
                return new CreateGroupResult(false, String.format("Group by name - %s was already created by %d", groupInfo.getName(), groupInfo.getOwnerId()));
            
            groupToCreate.setCreateDate(new Date());
            groupToCreate.setStatus(GroupStatus.Active);
            Group createdGroup = this._groupRepository.save(groupToCreate);

            addGroupMember(new AddGroupMemberPayload(userAccount.get().getId(), createdGroup.getId(), userAccount.get().getId(), GroupMembershipType.Admin));

            return new CreateGroupResult(true, "");

        }
        catch(Exception exception) {
            // Exception logged
            logs.info("group creation visited");
            throw exception;
        }

    }

    public boolean verifyIsGroupAdmin(long userId, int groupId) {
        try {
            Optional<GroupMember> groupMember = _groupMembershipRepository.findById(groupId);
            if(!groupMember.isPresent())
                return false;
            return groupMember.get().getMembershipType() == GroupMembershipType.Admin;
        }
        catch(Exception exception) {
            //Exception logged
            logs.info("GroupAdmin verification method visited");
            return false;
        }

    }

    public GroupMembershipAddResult addGroupMember(AddGroupMemberPayload groupMember) {
        //Todo: Confidence - check that member to add is a valid user account
        GroupMember memberTodAdd = new GroupMember();
        memberTodAdd.setGroupId(groupMember.getGroupId());
        memberTodAdd.setMembershipStatus(GroupMembershipStatus.Active);
        memberTodAdd.setMembershipType(groupMember.getMemberType());
        memberTodAdd.setUserId(groupMember.getUserId());

        _groupMembershipRepository.save(memberTodAdd);
        return new GroupMembershipAddResult(true, "");
    }
    
}
