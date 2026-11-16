# Laboratory Work No. 11

## Task A - Inspect a Sample JWT

### JWT Structure

A signed JWT consists of three parts:

1. Header
2. Payload
3. Signature

### Sample JWT Header

```json
{
  "alg": "RS256",
  "typ": "JWT",
  "kid": "training-key-01"
}
```

### Sample JWT Payload

```json
{
  "iss": "https://identity.example.edu",
  "sub": "user-1042",
  "aud": "https://api.example.edu",
  "iat": 1794301200,
  "exp": 1794304800,
  "scope": "invoices:read profile",
  "roles": ["finance-analyst"],
  "department": "finance"
}
```

### Answers

1. A signed JWT has three parts: Header, Payload, and Signature.

2. The issuer is identified by `iss`, and the subject by `sub`.

3. The intended audience is `https://api.example.edu`.

4. The scope claim contains `invoices:read` and `profile`.

5. The role is `finance-analyst`, while `department = finance` is an attribute.

6. The sample is unsigned and cannot be accepted as proof of identity or authorization.