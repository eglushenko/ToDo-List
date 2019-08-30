package com.todolist.ToDo.List.enums;

import java.util.stream.Stream;

public enum TaskStatusEnum {
    CLOSE("close"),
    NEW("new");

    private String typeOfStatus;

    TaskStatusEnum(String typeOfStatus){
        this.typeOfStatus = typeOfStatus;
    }

    public String getTypeOfStatus() {
        return typeOfStatus;
    }

    public static Stream<TaskStatusEnum> stream() {
        return Stream.of(TaskStatusEnum.values());
    }
}
