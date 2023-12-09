package shop.tests;

import org.junit.jupiter.api.Test;
import shop.dao.CartDAO;
import shop.dao.OrderDAO;
import shop.dao.ProductDAO;
import shop.dao.UserDAO;
import shop.exceptions.*;
import shop.models.Order;
import shop.models.StorefrontFacade;
import shop.models.User;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

public class StorefrontFacadeTest {

    private StorefrontFacade storefrontFacade;
    private ProductDAO productDAOMock;
    private CartDAO cartDAOMock;
    private OrderDAO orderDAOMock;
    private UserDAO userDAOMock;

    @BeforeEach
    public void setUp() {
        // Ensure that dependencies are reset between each test
        productDAOMock = mock(ProductDAO.class);
        cartDAOMock = mock(CartDAO.class);
        orderDAOMock = mock(OrderDAO.class);
        userDAOMock = mock(UserDAO.class);
        storefrontFacade = new StorefrontFacade(productDAOMock, cartDAOMock, orderDAOMock, userDAOMock);
    }

    @Test
    public void setOrderOwner_Successful() {
        // Input values
        int orderId = 1;
        int userId = 1;

        // Mock passing data: User corresponding to userId, Order corresponding to orderId that has no owner
        when(orderDAOMock.getOrder(orderId)).thenReturn(new Order(orderId, null));
        when(userDAOMock.getUserFromId(userId)).thenReturn(new User(userId));

        // Check that no error was thrown
        assertDoesNotThrow(() -> storefrontFacade.setOrderOwner(orderId, userId));

        // Check that OrderDAO.setOrderOwner was invoked
        verify(orderDAOMock).setOrderOwner(orderId, userId);

        // Mock other passing data: Order corresponding to orderId that has an owner without a passcode
        reset(orderDAOMock);
        when(orderDAOMock.getOrder(orderId)).thenReturn(new Order(orderId, new User(2, null)));

        // 2nd run: Check that no error was thrown
        assertDoesNotThrow(() -> storefrontFacade.setOrderOwner(orderId, userId));

        // 2nd run: Check that OrderDAO.setOrderOwner was invoked
        verify(orderDAOMock).setOrderOwner(orderId, userId);
    }

    @Test
    public void setOrderOwner_UserDoesNotExist() {
        // Arrange
        int orderId = 1;
        int userId = 1;

        when(userDAOMock.getUserFromId(userId)).thenReturn(null);
        when(orderDAOMock.getOrder(orderId)).thenReturn(new Order(orderId));

        // User corresponding to userId = null -> user does not exist
        assertThrows(UserDoesNotExistException.class, () -> storefrontFacade.setOrderOwner(orderId, userId));
    }

    @Test
    public void setOrderOwner_OrderDoesNotExist() {
        int orderId = 1;
        int userId = 1;

        when(userDAOMock.getUserFromId(userId)).thenReturn(new User(userId));
        when(orderDAOMock.getOrder(orderId)).thenReturn(null);

        // Order corresponding to orderId = null -> order does not exist
        assertThrows(OrderDoesNotExistException.class, () -> storefrontFacade.setOrderOwner(orderId, userId));
    }

    @Test
    public void setOrderOwner_OrderAlreadyClaimed() {
        int orderId = 1;
        int userId = 1;

        when(userDAOMock.getUserFromId(userId)).thenReturn(new User(userId));
        when(orderDAOMock.getOrder(orderId)).thenReturn(new Order(orderId, new User(2, "x")));

        // Order corresponding to orderId has an owner with a passcode
        assertThrows(OrderAlreadyClaimedException.class, () -> storefrontFacade.setOrderOwner(orderId, userId));
    }

    @Test
    public void setPasscode_Successful() {
        String passcode = "test";

        when(userDAOMock.passcodeExists(passcode)).thenReturn(false);

        assertDoesNotThrow(() -> storefrontFacade.setPasscode(passcode));
        verify(userDAOMock).createUser(false, passcode);
    }

    @Test
    public void setPasscode_PasscodeInvalidException() {
        String passcode = "inv";

        when(userDAOMock.passcodeExists(passcode)).thenReturn(false);

        assertThrows(PasscodeInvalidException.class, () -> storefrontFacade.setPasscode(passcode));
        verify(userDAOMock, never()).createUser(false, passcode);
    }

    @Test
    public void setPasscode_PasscodeExistsException() {
        String passcode = "test";

        when(userDAOMock.passcodeExists(passcode)).thenReturn(true);

        assertThrows(PasscodeExistsException.class, () -> storefrontFacade.setPasscode(passcode));
        verify(userDAOMock, never()).createUser(false, passcode);
    }


}

