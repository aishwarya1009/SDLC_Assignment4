package io.assignment4.security

import io.assignment4.domain.User
import io.assignment4.service.UserService
import io.micronaut.http.HttpRequest
import io.micronaut.scheduling.TaskExecutors
import io.micronaut.security.authentication.*
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher

import javax.annotation.Nullable
import javax.inject.Named
import javax.inject.Singleton
import java.util.concurrent.ExecutorService

@Singleton
class DelegatedUserPasswordAuthProvider implements AuthenticationProvider {

    protected UserService userService
    protected PasswordEncoder passwordEncoder
    protected Scheduler scheduler

    DelegatedUserPasswordAuthProvider(UserService userService,
                                      PasswordEncoder passwordEncoder,
                                      @Named(TaskExecutors.IO) ExecutorService executorService) {
        this.userService = userService
        this.passwordEncoder = passwordEncoder
        this.scheduler = Schedulers.from(executorService)
    }

    @Override
    Publisher<AuthenticationResponse> authenticate(@Nullable HttpRequest<?> httpRequest, AuthenticationRequest<?, ?> authenticationRequest) {
        Flowable.create({ emitter ->
            User user = userService.findUserByEmail(authenticationRequest.identity)
            Optional<AuthenticationFailed> authenticationFailed = validate(user, authenticationRequest)
            if (authenticationFailed.isPresent()) {
                emitter.onError(new AuthenticationException(authenticationFailed.get()))
            } else {
                emitter.onNext(createSuccessfulAuthenticationResponse(authenticationRequest, user))
            }
            emitter.onComplete()
        }, BackpressureStrategy.ERROR)
                .subscribeOn(scheduler)

    }

    protected Optional<AuthenticationFailed> validate(User user, AuthenticationRequest authenticationRequest) {

        AuthenticationFailed authenticationFailed = null
        if (user == null) {
            authenticationFailed = new AuthenticationFailed(AuthenticationFailureReason.USER_NOT_FOUND)

        } else if (!passwordEncoder.matches(authenticationRequest.getSecret().toString(), user.getPassword())) {
            authenticationFailed = new AuthenticationFailed(AuthenticationFailureReason.CREDENTIALS_DO_NOT_MATCH)
        }
        Optional.ofNullable(authenticationFailed)
    }

    protected AuthenticationResponse createSuccessfulAuthenticationResponse(AuthenticationRequest authenticationRequest, User user) {
        List<String> roles = new ArrayList<>([user.role])
        new DelegatedUserDetails(user, roles, "SAMPLE DATA")
    }
}