package com.iyushchuk.tictactoe.controllers;

import com.iyushchuk.tictactoe.common.dto.IDto;
import com.iyushchuk.tictactoe.common.exceptions.ApplicationException;
import com.iyushchuk.tictactoe.services.CrudService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class CrudController<Dto extends IDto, Id> {

    protected final CrudService<Dto, Id> crudService;

    public CrudController(CrudService<Dto, Id> crudService) {
        this.crudService = crudService;
    }

    @GetMapping
    public List<Dto> getAll() {
        return crudService.getAll();
    }

    @GetMapping("/{id}")
    public Dto getById(@PathVariable(value = "id") Id id) throws ApplicationException {
        return crudService.getById(id);
    }

    @PostMapping
    public Dto create(@RequestBody Dto request) throws ApplicationException {
        return crudService.create(request);
    }

    @PutMapping("/{id}")
    public Dto update(@PathVariable(value = "id") Id id, @RequestBody Dto request) throws ApplicationException {
        return crudService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") Id id) throws ApplicationException {
        crudService.delete(id);
    }
}
