package com.lambdaschool.todos.services;


import com.lambdaschool.todos.models.Todos;
import com.lambdaschool.todos.models.User;
import com.lambdaschool.todos.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "todosService")
public class TodosServiceImpl implements TodosService {

    @Autowired
    TodoRepository todorepo;

    @Autowired
    UserService userService;

    @Override
    public void markComplete(long todoid) {
        Todos updateTodos = todorepo.findById(todoid)
                .orElseThrow(() -> new EntityNotFoundException("Todo " + todoid + " was not found."));
        updateTodos.setCompleted(true);

    }

    @Transactional
    @Override
    public Todos save(long userid, Todos todos) {
        User currentUser = userService.findUserById(userid);
        Todos newTodos = new Todos(currentUser, todos.getDescription());
        todorepo.save(newTodos);
        return newTodos;
    }

}