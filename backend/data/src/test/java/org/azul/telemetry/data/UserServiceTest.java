package org.azul.telemetry.data;

import org.azul.telemetry.data.config.DataConfiguration;
import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * Currently disabled. See README.
 */
@Disabled
@DataJpaTest
@ContextConfiguration(classes = DataConfiguration.class)
public class UserServiceTest {
//    @Autowired
//    private RoleService roleService;
//    @Autowired
//    private UserService userService;
//
//    @BeforeEach
//    public void initDb() {
////        roleService.saveRole(new Role(Role.ADMIN_ROLE_NAME));
////        roleService.saveRole(new Role("ROLE_USER"));
////        userService.saveUser(new User("admin", "admin", Set.of(new Role(Role.ADMIN_ROLE_NAME))));
////        userService.saveUser(new User("test", "test", Set.of(new Role("ROLE_USER"))));
//    }
//
//    @Test
//    public void testSave() {
////        assertTrue(userService.saveUser(
////            new User("test2", "test2", Set.of(new Role("ROLE_USER")))
////        ));
////        assertFalse(userService.saveUser(
////            new User("test", "test", Set.of(new Role("ROLE_USER")))
////        ));
////        assertFalse(userService.saveUser(
////            new User("test3", "test3", Set.of(new Role("ROLE_NOT_EXISTS")))
////        ));
//    }
//
//    @Test
//    public void testLoad() {
////        var user = userService.getByName("admin");
////        assertNotNull(user);
////        var notExists = userService.getByName("test2");
////        assertNull(notExists);
////
////        assertEquals(user.getName(), "admin");
////        assertTrue(user.getAuthorities().contains(new Role(Role.ADMIN_ROLE_NAME)));
//    }
//
//    @Test
//    public void testNull() {
////        assertThrows(Exception.class, () -> userService.saveUser(
////            new User(null, "valid")
////        ));
////        assertThrows(Exception.class, () -> userService.saveUser(
////            new User("valid", null)
////        ));
//    }
}
