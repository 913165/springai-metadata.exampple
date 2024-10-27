
## Running PostgreSQL with pgvector in Docker


To quickly set up a PostgreSQL instance with the `pgvector` extension using docker , use the following Docker command:

```bash
docker run -it --rm --name postgres \
  -p 5432:5432 \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  pgvector/pgvector:0.7.4-pg16
```

### Enable Required Extensions
```sql
CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
```

### Create Vector Store Table
```sql
CREATE TABLE IF NOT EXISTS vector_store (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    content text,
    metadata json,
    embedding vector(1536)
);
```

### Create HNSW Index
```sql
CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);
```

### Field Descriptions:
- id: Unique identifier using UUID v4
- content: Text content of the document
- metadata: JSON field for storing additional document metadata
- embedding: Vector field for storing 1536-dimensional embeddings
- HNSW: Hierarchical Navigable Small World index for efficient similarity searches#
