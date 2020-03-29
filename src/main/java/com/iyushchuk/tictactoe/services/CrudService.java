package com.iyushchuk.tictactoe.services;

import com.iyushchuk.tictactoe.common.dto.IDto;
import com.iyushchuk.tictactoe.common.exceptions.ApplicationException;

import java.util.List;

public interface CrudService<DTO extends IDto, ID> {

    List<DTO> getAll();

    DTO getById(ID id) throws ApplicationException;

    DTO create(DTO dto) throws ApplicationException;

    DTO update(ID id, DTO dto) throws ApplicationException;

    void delete(ID id) throws ApplicationException;
}
