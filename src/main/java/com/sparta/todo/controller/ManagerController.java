package com.sparta.todo.controller;

import com.sparta.todo.dto.ManagerRequest;
import com.sparta.todo.dto.ManagerResponse;
import com.sparta.todo.service.ManagerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ManagerController {
    private final ManagerService managerService;

    @PostMapping("/api/v1/managers")
    public ResponseEntity<ManagerResponse> createManager(@RequestBody @Valid ManagerRequest request) {
        ManagerResponse response = managerService.createManager(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/managers/{managerId}")
    public ResponseEntity<ManagerResponse> getManager(@PathVariable long managerId) {
        ManagerResponse response = managerService.getManager(managerId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/v1/managers")
    public ResponseEntity<List<ManagerResponse>> getManagers() {
        List<ManagerResponse> response = managerService.getManagers();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/api/v1/managers/{managerId}")
    public ResponseEntity<ManagerResponse> updateManager(
            @PathVariable long managerId,
            @RequestBody ManagerRequest request
    ) {
        ManagerResponse response = managerService.updateManager(managerId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/api/v1/managers/{managerId}")
    public void deleteManager(
            @PathVariable long managerId
    ) {
        managerService.deleteManager(managerId);
    }
}