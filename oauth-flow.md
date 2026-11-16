# Laboratory Work No. 11

## OAuth 2.1 and OIDC Flow Diagram

```mermaid
sequenceDiagram
    participant C as Client
    participant A as Authorization Server
    participant R as Resource Server

    C->>C: Generate PKCE verifier and challenge
    C->>A: Authorization request + state + code_challenge
    A->>A: Authenticate user and obtain consent
    A-->>C: Authorization code
    C->>A: Authorization code + code_verifier
    A-->>C: Access token (and ID Token for OIDC)
    C->>R: GET /invoices/1 with access token
    R->>R: Validate token, scope, and ABAC policy
    R-->>C: Invoice data if authorized
```

**Figure 1 - OAuth 2.1 Authorization Code Flow with PKCE**

### Explanation

The client starts authorization using PKCE.
The authorization server authenticates the user and issues tokens.
The client presents the access token to the resource server.
The resource server validates the token and checks authorization before returning protected data.