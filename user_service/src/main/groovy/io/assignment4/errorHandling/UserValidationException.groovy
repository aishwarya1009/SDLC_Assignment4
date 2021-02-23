package io.assignment4.errorHandling

class UserValidationException extends Exception{
    int code
    UserValidationException(int code, String message) {
            super(message)
            this.code = code
        }
    int getCode(){
       return code
    }
}
