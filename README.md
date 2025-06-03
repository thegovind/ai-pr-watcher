### PR Analytics: Volume vs Success Rate (auto‑updated)

View the [interactive dashboard](https://aavetis.github.io/ai-pr-watcher/) for these statistics.

## Data sources

Explore the GitHub search queries used:

- **All Copilot PRs**: [is:pr head:copilot/](https://github.com/search?q=is:pr+head:copilot/&type=pullrequests)
- **Merged Copilot PRs**: [is:pr head:copilot/ is:merged](https://github.com/search?q=is:pr+head:copilot/+is:merged&type=pullrequests)
- **All Codex PRs**: [is:pr head:codex/](https://github.com/search?q=is:pr+head:codex/&type=pullrequests)
- **Merged Codex PRs**: [is:pr head:codex/ is:merged](https://github.com/search?q=is:pr+head:codex/+is:merged&type=pullrequests)

---

![chart](chart.png)

## Database Schema Implementation

This repository now includes a PostgreSQL database schema for banking Account entities, converted from COBOL VSAM data structure patterns.

### Database Setup

1. **Install dependencies:**
   ```bash
   pip install -r requirements.txt
   ```

2. **Configure database connection:**
   ```bash
   cp .env.example .env
   # Edit .env with your PostgreSQL connection details
   ```

3. **Run migrations:**
   ```bash
   alembic upgrade head
   ```

4. **Test the implementation:**
   ```bash
   python example_usage.py
   ```

### Account Entity Schema

The Account table includes the following fields converted from COBOL VSAM structure:

- `account_number`: VARCHAR(20) - Primary key, unique identifier
- `hashed_pin`: VARCHAR(255) - Securely hashed PIN using bcrypt
- `balance`: DECIMAL(15,2) - Account balance with precision for currency
- `created_at`: TIMESTAMP - Record creation timestamp
- `updated_at`: TIMESTAMP - Last modification timestamp

### Features

- **Secure PIN handling**: Uses bcrypt for PIN hashing and verification
- **Precision arithmetic**: DECIMAL type prevents floating-point errors
- **Database migrations**: Alembic for schema version control
- **CRUD operations**: Complete set of account management functions
- **Indexing**: Optimized queries with appropriate database indexes

### COBOL VSAM to PostgreSQL Conversion

This implementation follows standard patterns for converting COBOL VSAM file structures to relational databases:

- Fixed-length COBOL fields → VARCHAR with appropriate lengths
- COBOL COMP-3 packed decimal → PostgreSQL DECIMAL
- VSAM key fields → Primary keys with indexes
- COBOL date/time → PostgreSQL TIMESTAMP with timezone support

## Current Statistics

| Project | Total PRs | Merged PRs | Merge Rate |
| ------- | --------- | ---------- | ---------- |
| Copilot | 11,091 | 3,372 | 30.40% |
| Codex   | 75,643 | 62,973 | 83.25% |
| Devin   | 27,225 | 16,620 | 61.05% |
