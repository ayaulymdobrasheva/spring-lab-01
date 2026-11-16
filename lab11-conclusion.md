# Laboratory Work No. 11

## Conclusion

In this laboratory work, I studied token-based and federated security mechanisms, including JSON Web Tokens (JWT), OAuth 2.1, OpenID Connect (OIDC), Role-Based Access Control (RBAC), and Attribute-Based Access Control (ABAC).

The JWT exercise demonstrated that a token consists of three parts: a header, a payload, and a signature. I learned that decoding a JWT does not prove its authenticity. A resource server must verify the cryptographic signature and validate claims such as issuer, audience, expiration time, and token purpose.

The OAuth 2.1 exercise helped me understand the Authorization Code flow with PKCE. PKCE protects against unauthorized use of intercepted authorization codes, while the state parameter helps prevent CSRF attacks.

RBAC is a simple and effective authorization model based on predefined roles and permissions. It is useful when responsibilities are stable and clearly defined. However, RBAC does not easily account for dynamic access conditions.

ABAC provides more flexibility by evaluating user attributes, resource properties, and environmental conditions. For example, access to university financial documents can depend on department, classification, and device security.

In conclusion, secure applications should combine reliable token validation with appropriate authorization policies. Signature verification, claim validation, least-privilege permissions, and secure audit logging are essential for protecting sensitive information.