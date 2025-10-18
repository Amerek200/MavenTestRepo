package org.example.api;

public class ApiConsumer {

    private final AuthService authService;
    private final ApiClient apiClient;
    private AuthToken authToken;

    public ApiConsumer(AuthService authService, ApiClient apiClient) {
        this.authService = authService;
        this.apiClient = apiClient;
    }

    public String getData() {
        if (authToken == null || authToken.isExpired()) {
            authToken = authService.register();
        } else if (authToken.isNearExpiry()) {
            authToken = authService.renew(authToken.getToken());
        }
        return apiClient.getData(authToken.getToken());
    }

    public void setToken(AuthToken authToken) {
        this.authToken = authToken;
    }
}
