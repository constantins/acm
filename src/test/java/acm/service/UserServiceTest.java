package acm.service;

import acm.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})
public class UserServiceTest {

    private final String u1Name = "user1";
    private final Long u1Id = 1L;
    private final Long u2Id = 2L;

    @Autowired
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    public void testGetUserByName() {
        User u = userService.getUserByName(u1Name);
        assertNotNull(u.getId());
    }

    @Test
    public void testGetUserByID() {
        User u1 = userService.getUserByID(u1Id);
        assertNotNull(u1.getId());

        User u2 = userService.getUserByID(u2Id);
        assertNotNull(u2.getId());

        assertTrue(!u1.getUsername().equalsIgnoreCase(u2.getUsername()));
    }


}
