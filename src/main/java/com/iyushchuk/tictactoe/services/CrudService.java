package com.iyushchuk.tictactoe.services;

import com.iyushchuk.tictactoe.common.dto.IDto;
import com.iyushchuk.tictactoe.common.exceptions.ApplicationException;

import java.util.List;

public interface CrudService<Dto extends IDto, Id> {

    List<Dto> getAll();

    Dto getById(Id id) throws ApplicationException;

    Dto create(Dto dto) throws ApplicationException;

    Dto update(Id id, Dto dto) throws ApplicationException;

    void delete(Id id) throws ApplicationException;
}
