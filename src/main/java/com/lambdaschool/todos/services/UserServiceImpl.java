package com.lambdaschool.todos.services;

import com.lambdaschool.todos.models.Todo;
import com.lambdaschool.todos.models.User;
import com.lambdaschool.todos.repository.UserRepository;
import com.lambdaschool.todos.views.UserNameCountTodos;
import com.lambdaschool.todos.views.UserTodoCount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements the Userservice Interface
 */
@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService
{
    /**
     * Connects this service to the User table.
     */
    @Autowired
    private UserRepository userrepos;

    /**
     * Connects this service to the auditing service in order to get current user name
     */
    @Autowired
    private UserAuditing userAuditing;

    public User findUserById(long id) throws EntityNotFoundException
    {
        return userrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User id " + id + " not found!"));
    }

    @Override
    public List<User> findAll()
    {
        List<User> list = new ArrayList<>();
        /*
         * findAll returns an iterator set.
         * iterate over the iterator set and add each element to an array list.
         */
        userrepos.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Transactional
    @Override
    public void delete(long id)
    {
        userrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User id " + id + " not found!"));
        userrepos.deleteById(id);
    }

    @Transactional
    @Override
    public User save(User user) {
        User newUser = new User();

        if (user.getUserid() != 0) {
            userrepos.findById(user.getUserid())
                    .orElseThrow(() -> new EntityNotFoundException("User " + user.getUserid() + " was not found."));
            newUser.setUserid(user.getUserid());
        }

        newUser.setUsername(user.getUsername()
                .toLowerCase());
        newUser.setPassword(user.getPassword());
        newUser.setPrimaryemail(user.getPrimaryemail()
                .toLowerCase());


        newUser.getTodos().clear();
        for (Todo t : user.getTodos()) {
            Todo newTodo = new Todo(newUser,t.getDescription());
            newUser.getTodos().add(newTodo);
        }

        return userrepos.save(newUser);
    }

    @Override
    public List<UserNameCountTodos> getCountUserTodos()
    {
        return null;
    }

    @Override
    public List<UserTodoCount> getUserTodoCount() {
        List<UserTodoCount> list = userrepos.getUserTodoCount();
        return list;
    }
}