package io.assignment4.controller


import io.assignment4.domain.User
import io.assignment4.service.UserService
import io.assignment4.utils.UserRegistration
import io.assignment4.errorHandling.UserCreationException
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule


import javax.validation.Valid

@Controller("/")
class MainController {
    UserService userService

    MainController(UserService userService) {
        this.userService = userService
    }

    HTTPSingleResponse fail(int code, String message) {
        return new HTTPSingleResponse(success: false, errorCodes: [code], errorMessages: [message])
    }

    @Secured(SecurityRule.IS_ANONYMOUS)
    @Post("/register")
    HTTPSingleResponse  registerUser(@Body @Valid UserRegistration cmd) {

        User user
        try{
            user = userService.createAndSaveUser(cmd.getFirstName(), cmd.getLastName(),
                                                 cmd.getEmail(), cmd.getPassword(), cmd.getRole())

        } catch (UserCreationException ex) {
            return fail(ex.getCode(),ex.toString())
        }

        HTTPSingleResponse response = new HTTPSingleResponse(
                success: true,
                data: [
                        message: "User saved",
                        fname: user.firstName,
                        lname: user.lastName,
                        email: user.email,
                        id: user.id.toString()
                ]
        )
        return response
    }
}
