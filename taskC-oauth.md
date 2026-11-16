# Laboratory Work No. 11

## Task C - OAuth 2.1 and OpenID Connect

### Authorization Code Flow with PKCE

| Event in Assignment | Correct Order | Description |
|---|---|---|
| 7 | 5 | Client exchanges authorization code and code_verifier |
| 8 | 3 | User authenticates and grants consent |
| 9 | 1 | Client creates code_verifier and code_challenge |
| 10 | 6 | Authorization server returns tokens |
| 11 | 2 | Client redirects user to authorization endpoint |
| 12 | 4 | Authorization server redirects back with authorization code |

### Correct Sequence

1. Client creates code_verifier and code_challenge.
2. Client redirects user to the authorization endpoint.
3. User authenticates and grants consent.
4. Authorization server returns an authorization code.
5. Client exchanges the code with code_verifier.
6. Authorization server returns tokens.

### Short-Answer Questions

**1. What problem does PKCE help mitigate?**

PKCE prevents unauthorized redemption of intercepted authorization codes.

**2. What is the purpose of the state parameter?**

The state parameter correlates the authorization response with the original request and helps prevent CSRF attacks.

**3. What is the difference between an access token and an ID Token?**

An access token grants access to protected resources. An ID Token provides information about the authenticated user to the OIDC client.

**4. Which component issues and validates tokens?**

The authorization server issues tokens. The resource server validates access tokens and enforces API authorization decisions.

### Result

The correct sequence is 5, 3, 1, 6, 2, 4.