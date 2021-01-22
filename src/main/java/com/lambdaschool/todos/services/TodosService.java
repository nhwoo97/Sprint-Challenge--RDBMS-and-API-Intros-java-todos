package com.lambdaschool.todos.services;

import com.lambdaschool.todos.models.Todo;

public interface TodosService
{
    void markComplete(long todoid);

    Todo save(long userid, Todo todo);
}