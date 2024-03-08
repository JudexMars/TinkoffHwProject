package org.judexmars.tinkoffhwproject.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.judexmars.tinkoffhwproject.model.MessageEntity;
import org.judexmars.tinkoffhwproject.model.PrivilegeEntity;
import org.judexmars.tinkoffhwproject.model.RoleEntity;
import org.judexmars.tinkoffhwproject.repository.MessageRepository;
import org.judexmars.tinkoffhwproject.repository.PrivilegeRepository;
import org.judexmars.tinkoffhwproject.repository.RoleRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataLoader implements ApplicationRunner {

    private final RoleRepository roleRepository;

    private final PrivilegeRepository privilegeRepository;

    private final MessageRepository messageRepository;

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        initPrivilegesAndRoles();
    }

    private void initPrivilegesAndRoles() {
        var auditOperation = createPrivilegeIfDoesntExist("AUDIT_OPERATION");
        var sendMessage = createPrivilegeIfDoesntExist("SEND_MESSAGE");
        var getMessage = createPrivilegeIfDoesntExist("GET_MESSAGE");
        var getAllMessages = createPrivilegeIfDoesntExist("GET_ALL_MESSAGES");
        var uploadImage = createPrivilegeIfDoesntExist("UPLOAD_IMAGE");
        var downloadImage = createPrivilegeIfDoesntExist("DOWNLOAD_IMAGE");

        createRoleIfDoesntExist("ROLE_USER", getMessage, sendMessage, uploadImage, downloadImage);
        createRoleIfDoesntExist("ROLE_ADMIN", auditOperation, getAllMessages);

        mockMessages();
    }

    private PrivilegeEntity createPrivilegeIfDoesntExist(String name) {
        return privilegeRepository.findByName(name).orElseGet(() -> {
            var privilege = new PrivilegeEntity();
            privilege.setName(name);
            return privilegeRepository.save(privilege);
        });
    }

    private RoleEntity createRoleIfDoesntExist(String name, PrivilegeEntity... privileges) {
        List<PrivilegeEntity> privilegeList = new ArrayList<>(List.of(privileges));

        var createdRole = roleRepository.findByName(name).orElseGet(() -> {
            var role = new RoleEntity();
            role.setName(name);
            role.setPrivileges(privilegeList);
            return roleRepository.save(role);
        });

        boolean arePrivilegesMatched = new HashSet<>(createdRole.getPrivileges()).containsAll(privilegeList);
        if (!arePrivilegesMatched) {
            createdRole.setPrivileges(privilegeList);
            return roleRepository.save(createdRole);
        }
        return createdRole;
    }

    private void mockMessages() {
        messageRepository.findById(1L).orElseGet(() ->
                messageRepository.save(MessageEntity.builder()
                .author("JudexMars").content("Default message 1")
                .build()));
        messageRepository.findById(2L).orElseGet(() ->
                messageRepository.save(MessageEntity.builder()
                .author("Johnny Guitar").content("Default message 2")
                .build()));
    }
}
