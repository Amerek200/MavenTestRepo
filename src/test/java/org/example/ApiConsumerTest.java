package org.example;

import org.example.api.ApiClient;
import org.example.api.ApiConsumer;
import org.example.api.AuthService;
import org.example.api.AuthToken;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ApiConsumerTest {
    @Mock AuthService authService;
    @Mock ApiClient apiClient;
    @InjectMocks ApiConsumer apiConsumer;
    private AutoCloseable mocks;

    @BeforeEach
    void setup() { mocks = MockitoAnnotations.openMocks(this); }

    @AfterEach
    void cleanup() throws Exception{ mocks.close(); }

    @Test
    void registerNewTokenTest() {
        AuthToken newToken = new AuthToken("newToken", System.currentTimeMillis()+30000);
        when(authService.register()).thenReturn(newToken);
        when(apiClient.getData("newToken")).thenReturn("OK");

        String res = apiConsumer.getData();
        //res = apiConsumer.getData(); would break verify since it default to one expected method call.
        verify(authService).register();
        verify(apiClient).getData("newToken");
        verify(authService, never()).renew(anyString());
        assertEquals("OK", res);
    }

    @Test
    void renewWhenNearExpiryTest() {
        AuthToken oldToken = new AuthToken("oldToken", System.currentTimeMillis() + 5000);
        apiConsumer.setToken(oldToken);
        AuthToken renewed = new AuthToken("renewed", System.currentTimeMillis() + 60000);
        when(authService.renew(oldToken.getToken())).thenReturn(renewed);
        when(apiClient.getData(renewed.getToken())).thenReturn("OK");

        String res = apiConsumer.getData();

        verify(authService).renew(oldToken.getToken());
        verify(apiClient).getData(renewed.getToken());
        assertEquals("OK", res);
    }

    @Test
    void useExistingWhenValidTest() {
        AuthToken token = new AuthToken("okToken", System.currentTimeMillis()+ 45000 );
        apiConsumer.setToken(token);
        when(apiClient.getData(token.getToken())).thenReturn("OK");

        String res = apiConsumer.getData();
        verify(authService, never()).register();
        verify(authService, never()).renew(anyString());
        verify(apiClient).getData(token.getToken());
        assertEquals("OK", res);
    }

}
