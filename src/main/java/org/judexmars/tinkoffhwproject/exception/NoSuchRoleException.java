package org.judexmars.tinkoffhwproject.exception;

public class NoSuchRoleException extends BaseNotFoundException {


    public NoSuchRoleException(Object... args) {
        super("exception.no_such_role", args);
    }
}
