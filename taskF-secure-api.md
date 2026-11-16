# Laboratory Work No. 11

## Task F - Design a Secure API Access Decision

### Scenario

A university finance API provides the endpoint:

`GET /invoices/{id}`

The API must verify the access token and apply authorization rules before returning invoice information.

### Security Validation Checks

1. Verify the token's cryptographic signature using a trusted key.
2. Check that the signing algorithm is allowed.
3. Validate the issuer claim (`iss`).
4. Validate the audience claim (`aud`).
5. Check the expiration time (`exp`) and not-before time (`nbf`).
6. Confirm that the token is an access token, not an ID Token.
7. Verify that the token includes the `invoices:read` scope.
8. Apply the ABAC policy before returning the invoice.

### Required Claims and Attributes

| Claim or Attribute | Purpose |
|---|---|
| sub | Identifies the authenticated user |
| iss | Identifies the trusted issuer |
| aud | Identifies the intended API |
| exp | Specifies token expiration |
| scope | Must contain invoices:read |
| department | Identifies the user's department |
| resource.department | Identifies the invoice department |
| resource.classification | Defines invoice sensitivity |
| device_managed | Indicates whether the device is managed |

### ABAC Authorization Policy

Access is allowed only when:

1. The user's department matches the invoice department.
2. The invoice classification is `internal` or `public`.
3. The device is managed.

### Negative Test Cases

| Test Case | Expected Result |
|---|---|
| Expired access token | 401 Unauthorized |
| Valid token, wrong department | 403 Forbidden |

### Audit Logging

The API audit log should record:

- Subject identifier
- Requested action
- Resource identifier
- Authorization decision (ALLOW or DENY)
- Timestamp
- Policy version
- Reason for denial

The raw bearer token must never be stored in the audit log.

### Result

The university finance API uses JWT validation, scope checking, and ABAC policies to prevent unauthorized access to invoice information.