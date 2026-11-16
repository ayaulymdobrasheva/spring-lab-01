# Laboratory Work No. 11

## Task E - ABAC Policy Exercise

### Attribute-Based Access Control (ABAC)

ABAC is an authorization model that makes access decisions based on attributes of the user, resource, and environment.

### ABAC Policy

Access to an invoice is allowed only if all conditions are satisfied:

1. The user's department matches the invoice department.
2. The invoice classification is internal or public.
3. The device is managed.

### Access Control Decisions

| Case | User Department | Invoice Department | Classification | Managed Device | Decision |
|---|---|---|---|---|---|
| 1 | finance | finance | internal | Yes | ALLOW |
| 2 | sales | finance | internal | Yes | DENY |
| 3 | finance | finance | confidential | Yes | DENY |
| 4 | finance | finance | public | No | DENY |

### Explanation

**Case 1:** ALLOW — all conditions are satisfied.

**Case 2:** DENY — the user's department does not match the invoice department.

**Case 3:** DENY — confidential classification is not allowed.

**Case 4:** DENY — the device is not managed.

### Additional Attribute

An additional attribute could be resource ownership.

The policy could require that the user owns the requested invoice before allowing access.

### Result

One request is ALLOW and three requests are DENY.