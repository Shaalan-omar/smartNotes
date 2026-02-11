# 🚀 Milestone 3 – What I Learned (Spring Boot + JPA)

---

# 1️⃣ Understanding JPA Persistence

- `save()` on a `JpaRepository` triggers Hibernate to generate SQL.
- Verified persistence using:
    - `noteRepository.count()`
    - Hibernate SQL logs
- Learned that database truth ≠ API output.
- Persistence layer and serialization layer are separate concerns.

---

# 2️⃣ Diagnosing DB vs Serialization Issues

## Problem
- `count()` showed records exist.
- API returned empty or `{}` objects.

## Root Cause
- DTO serialization issue (no getters).

## Lesson
- Always confirm DB layer separately before assuming persistence is broken.

---

# 3️⃣ Debugging Hibernate Logs

## Observed Logs

```sql
insert into notes (...)
select count(*) from notes
select ... from notes
```

## What I Learned
- Hibernate logs reveal:
    - Real table names
    - Insert vs select operations
    - Relationship loading
- SQL logs are critical for debugging persistence issues.

---

# 4️⃣ DTO ↔ Entity Separation

## What I Implemented
- `Note` and `User` as JPA entities.
- `NoteResponse`, `UserResponse`, and `CreateNoteRequest` as DTOs.
- Mapping logic inside the service layer.

```java
private NoteResponse toResponse(Note note)
```

## What I Learned
- Entities are for database persistence.
- DTOs are for API communication.
- Controllers should never expose entities directly.
- The service layer is responsible for mapping between Entity and DTO.

---

# 5️⃣ Understanding Jackson (JSON Serialization)

## Error Encountered

```
HttpMessageConversionException
Cannot construct instance of CreateNoteRequest
```

## Root Cause
- The DTO used `@Builder` without a default constructor.
- Jackson could not instantiate the object during deserialization.

## Fix Applied
- Added `@NoArgsConstructor`.
- Ensured request DTOs have setters.
- Ensured response DTOs have getters.

## Key Lesson
- Request DTOs require:
    - A no-args constructor
    - Setters
- Response DTOs require:
    - Getters

---

# 6️⃣ Fixing Infinite Recursion (Bidirectional Relationships)

## Error Encountered

```
Document nesting depth (501) exceeds maximum
```

## Root Cause
Bidirectional relationship:
- `User` → `List<Note>`
- `Note` → `User`

Returning entities directly caused infinite JSON nesting.

## Fix Applied
- Returned DTOs instead of entities.
- Controlled serialization explicitly in the service layer.

## Key Lesson
- Serialization must be explicitly controlled.
- Bidirectional relationships require careful API design.

---

# 7️⃣ Fixing Generics Bound Errors

## Error Encountered

```
Inferred type 'S' should extend User
```

## Root Cause
- Attempted to call repository `save()` with a DTO instead of an entity.

## Fix Applied
- Converted DTO → Entity before saving.
- Ensured repositories operate strictly on entities.

## Key Lesson
- Repositories belong to the persistence layer.
- DTO ↔ Entity mapping must occur in the service layer.

---

# 8️⃣ Fixing Optional Misuse

## Error Encountered

```
Required type: User
Provided: Optional<User>
```

## Root Cause
- Attempted to assign `Optional<User>` directly to a `User` variable.

## Fix Applied

```java
userRepository.findById(id)
    .orElseThrow(...)
```

## Key Lesson
- Always unwrap `Optional` properly.
- Use `orElseThrow()` for clean exception handling.

---

# 9️⃣ Fixing Table Naming Mismatches

## Issue Observed
- Hibernate inserted into `note`.
- Later selected from `notes`.

## Root Cause
Implicit table naming caused inconsistency.

## Fix Applied

```java
@Table(name = "notes")
```

## Key Lesson
- Always define explicit table names in entities.
- Avoid relying on implicit naming strategies.

---

# 🔟 Fixing Lombok Side Effects

## Issues Encountered
- `@Builder` broke JSON deserialization.
- Missing getters caused empty `{}` JSON responses.

## Fix Applied
- Added `@Getter` or `@Data` for response DTOs.
- Avoided `@Builder` for request DTOs.

## Key Lesson
- Lombok reduces boilerplate but must be used carefully.
- Always verify how generated code interacts with frameworks like Jackson.
