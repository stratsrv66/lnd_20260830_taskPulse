User
- id
- email (unique)
- username (unique)
- passwordHash
- roles
- createdAt

RefreshToken
- id
- tokenHash (unique)
- userId
- familyId
- expiresAt (TTL)
- createdAt
- revokedAt
- replacedByHash

Team
- id
- name
- createdAt

TeamMember
- id
- userId
- teamId
- role
- joinedAt

Project
- id
- teamId
- name
- description
- createdAt

Task
- id
- projectId
- title
- description
- statusId
- assignedTo
- createdAt
- updatedAt

TaskStatus
- id
- projectId
- name
- position

InviteLink
- id
- teamId
- token
- createdBy
- expiresAt
- maxUses