package io.assignment4.errorHandling

class UserNotFoundException extends Exception{
    int code
    UserNotFoundException(int code, String message) {
            super(message)
            this.code = code
    }

    int getCode(){
        return code
    }
}
