package io.assignment4.errorHandling

class UserCreationException extends Exception{
    int code
    UserCreationException(int code, String message) {
            super(message)
            this.code = code
        }
    int getCode(){
        return code
    }
}
