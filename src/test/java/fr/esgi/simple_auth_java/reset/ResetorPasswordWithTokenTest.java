package fr.esgi.simple_auth_java.reset;

import fr.esgi.simple_auth_java.Manager;
import fr.esgi.simple_auth_java.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.mock;
import static org.assertj.core.api.Assertions.*;
/**
 * Created by Yohan FAIRFORT on 08/06/2017.
 */
public class ResetorPasswordWithTokenTest {
    @Mock
    User testUser;
    Manager testManager = new Manager();

    Resetor resetPasswordWithToken = mock(ResetorPasswordWithOldPwd.class);

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        Mockito.reset(testUser);
    }

    @Test(expected = ResetException.class)
    public void should_fail_because_wrong_token()
    {
        testManager.reset(testUser, resetPasswordWithToken);
    }

    @Test
    public void should_succeed()
    {
        String oldPassword = testUser.getPassword();
        testManager.reset(testUser, resetPasswordWithToken);

        assertThat(oldPassword).isNotEqualTo(testUser.getPassword());
    }
}
