package com.iyushchuk.tictactoe.controllers;

import com.iyushchuk.tictactoe.common.dto.IDto;
import com.iyushchuk.tictactoe.common.exceptions.ApplicationException;
import com.iyushchuk.tictactoe.services.CrudService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class CrudController<DTO extends IDto, ID> {

    protected final CrudService<DTO, ID> crudService;

    public CrudController(CrudService<DTO, ID> crudService) {
        this.crudService = crudService;
    }

    @GetMapping
    public List<DTO> getAll() {
        return crudService.getAll();
    }

    @GetMapping("/{id}")
    public DTO getById(@PathVariable(value = "id") ID id) throws ApplicationException {
        return crudService.getById(id);
    }

    @PostMapping
    public DTO create(@RequestBody DTO request) throws ApplicationException {
        return crudService.create(request);
    }

    @PutMapping("/{id}")
    public DTO update(@PathVariable(value = "id") ID id, @RequestBody DTO request) throws ApplicationException {
        return crudService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") ID id) throws ApplicationException {
        crudService.delete(id);
    }
}
