package com.ax.model;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MainMapper {
    StudentDto studentVo2Dto(StudentVo vo);
}

