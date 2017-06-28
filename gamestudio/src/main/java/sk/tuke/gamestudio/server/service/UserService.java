package sk.tuke.gamestudio.server.service;

import sk.tuke.gamestudio.server.entity.User;

public interface UserService {
    User login(String username, String passwd) throws UserException;

    User register(String username, String passwd) throws UserException;
}
