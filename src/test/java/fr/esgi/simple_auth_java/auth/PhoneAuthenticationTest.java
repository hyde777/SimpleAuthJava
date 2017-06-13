package fr.esgi.simple_auth_java.auth;

import fr.esgi.simple_auth_java.User;
import fr.esgi.simple_auth_java.password_encrypt.PasswordEncryptDisable;
import fr.esgi.simple_auth_java.password_encrypt.PasswordEncryptSHA256;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PhoneAuthenticationTest {
    private List<User> users = Arrays.asList(
            new User("foo.bar@email.com", "Foo", "Bar",
                    new PasswordEncryptDisable(), "azerty"),
            new User("test@email.com", "Testin", "Unity",
                    new PasswordEncryptSHA256(), "azerty")
    );
    private PhoneMessageSender sender = new PhoneMessageSender("0102030405");

    @Test
    public void should_sign_with_email() throws AuthenticationException {
        HashMap<String, Object> args = new HashMap<>();
        args.put("email", "foo.bar@email.com");
        args.put("message", sender.getCode());
        args.put("phone_sender", sender);
        PhoneAuthentication auth = new PhoneAuthentication(args, users);

        assertThat(users.get(0)).isEqualTo(auth.signIn());
    }

    @Test
    public void shoud_raise_error_if_bad_code() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("email", "foo.bar@email.com");
        args.put("message", "bad code");
        args.put("phone_sender", sender);
        PhoneAuthentication auth = new PhoneAuthentication(args, users);

        assertThatThrownBy(auth::signIn).isInstanceOf(AuthenticationException.class);
    }


    @Test
    public void shoud_raise_error_if_no_users_email() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("email", "bla@bla.com");
        args.put("message", sender.getCode());
        args.put("phone_sender", sender);
        PhoneAuthentication auth = new PhoneAuthentication(args, users);

        assertThatThrownBy(auth::signIn).isInstanceOf(AuthenticationException.class);
    }

    @Test
    public void shoud_raise_error_if_no_users() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("email", "foo.bar@email.com");
        args.put("message", sender.getCode());
        args.put("phone_sender", sender);
        PhoneAuthentication auth = new PhoneAuthentication(args, new ArrayList<>());

        assertThatThrownBy(auth::signIn).isInstanceOf(AuthenticationException.class);
    }

    @Test
    public void shoud_raise_error_if_no_args() {
        HashMap<String, Object> args = new HashMap<>();
        PhoneAuthentication auth = new PhoneAuthentication(args, users);

        assertThatThrownBy(auth::signIn).isInstanceOf(AuthenticationException.class);
    }

    @Test
    public void shoud_raise_error_if_no_sender() {
        HashMap<String, Object> args = new HashMap<>();
        args.put("email", "foo.bar@email.com");
        args.put("message", sender.getCode());
        PhoneAuthentication auth = new PhoneAuthentication(args, users);

        assertThatThrownBy(auth::signIn).isInstanceOf(AuthenticationException.class);
    }
}
