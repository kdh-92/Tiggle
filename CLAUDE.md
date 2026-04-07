# Tiggle Project Rules

## File Naming
- **NEVER create files or directories that differ only in casing** from existing ones (e.g., `typeTag` vs `TypeTag`). macOS is case-insensitive and this causes persistent git conflicts.
- Directory names under `frontend/tiggle/src/components/atoms/` must use **PascalCase** (e.g., `TypeTag`, not `typeTag`).
- The `.githooks/pre-commit` hook blocks commits with case-duplicate paths. Run `git config core.hooksPath .githooks` after cloning.

## Architecture
- Backend: Spring Boot (Kotlin), port 8080
- Frontend: Vite + React + TypeScript
- DB: MySQL (port 3307)
- Messaging: Kafka
- Deployment: Docker Compose on Synology NAS, nginx reverse proxy

## Testing
- Unit tests: `./gradlew :tiggle:test` (excludes integration tests)
- Integration tests (requires Kafka): `./gradlew :tiggle:integrationTest`
- Controller tests use `@AutoConfigureMockMvc(addFilters = false)` to bypass Security filters

## Deployment
- Push to `main` triggers GitHub Actions → SSH deploy to NAS
- Frontend: build with `npm run build`, deploy to `/var/services/web/`
- Backend: Docker Compose rebuild
- nginx proxies: `/api/`, `/oauth2/`, `/login/oauth2/`, `/upload/` → backend:8080
