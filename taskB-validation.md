# Laboratory Work No. 11

## Task B - Token Validation Checklist

| Validation | Scenario | Expected Result |
|---|---|---|
| Signature | Invalid signature | FAIL — Reject |
| Issuer (iss) | Incorrect issuer | FAIL — Reject |
| Audience (aud) | Token for another API | FAIL — Reject |
| Expiry (exp) | Expired token | FAIL — Reject |
| Scope | Missing invoices:read | FAIL — Deny |
| Token purpose | ID Token used as access token | FAIL — Reject |

## Explanation

Successful Base64URL decoding does not prove that a JWT is authentic.

Decoding only reveals the information stored in the header and payload.

An attacker may construct or modify these values.

A resource server must verify the cryptographic signature and validate the issuer, audience, expiration time, and token purpose before trusting the token.

## Result

All six negative validation cases are rejected or denied.