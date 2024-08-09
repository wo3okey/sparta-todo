package com.sparta.todo.service;

import com.sparta.todo.dto.ManagerRequest;
import com.sparta.todo.dto.ManagerResponse;
import com.sparta.todo.entity.Manager;
import com.sparta.todo.repository.ManagerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerService {
    private final ManagerRepository managerRepository;

    public ManagerResponse createManager(ManagerRequest request) {
        Manager entity = new Manager(request);
        return ManagerResponse.of(managerRepository.save(entity));
    }

    public Manager getManagerEntity(long managerId) {
        return managerRepository.findById(managerId).orElseThrow();
    }

    public ManagerResponse getManager(long managerId) {
        Manager entity = managerRepository.findById(managerId).orElseThrow();
        return ManagerResponse.of(entity);
    }

    public List<ManagerResponse> getManagers() {
        List<Manager> entities = managerRepository.search();
        return entities.stream()
                .map(ManagerResponse::of)
                .collect(Collectors.toList());
    }

    public ManagerResponse updateManager(long managerId, ManagerRequest request) {
        Manager entity = managerRepository.findById(managerId).orElseThrow();
        if (request.getName() != null) {
            entity.changeName(request.getName());
        }

        if (request.getEmail() != null) {
            entity.changeEmail(request.getEmail());
        }

        managerRepository.update(entity);

        Manager updatedEntity = managerRepository.findById(managerId).orElseThrow();
        return ManagerResponse.of(updatedEntity);
    }

    public void deleteManager(long managerId) {
        Manager entity = managerRepository.findById(managerId).orElseThrow();
        managerRepository.delete(entity.getId());
    }
}
