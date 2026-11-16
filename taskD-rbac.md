# Laboratory Work No. 11

## Task D - RBAC Policy Exercise

### Role-Based Access Control (RBAC)

RBAC is an authorization model in which permissions are assigned to roles, and users receive permissions through their assigned roles.

### Role Permissions

| Role | Permissions |
|---|---|
| viewer | invoices:read |
| finance-analyst | invoices:read, reports:read |
| billing-admin | invoices:read, invoices:update, users:manage |

### Access Control Decisions

| User | Role | Requested Action | Decision | Reason |
|---|---|---|---|---|
| Alex | viewer | invoices:read | ALLOW | viewer has invoices:read |
| Alex | viewer | invoices:update | DENY | viewer lacks invoices:update |
| Sam | finance-analyst | reports:read | ALLOW | finance-analyst has reports:read |
| Riley | billing-admin | users:manage | ALLOW | billing-admin has users:manage |

### Advantage of RBAC

RBAC simplifies access management because administrators assign permissions to roles instead of configuring permissions separately for each user.

### Limitation of RBAC

RBAC is less flexible when authorization decisions depend on dynamic conditions such as device security, resource ownership, or the user's department.

### Result

Three requests are ALLOW and one request is DENY.