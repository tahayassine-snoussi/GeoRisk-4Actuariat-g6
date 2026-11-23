# risk-engine-service (MS5)

Zones, météo, score de risque, alertes. Python, FastAPI, PostgreSQL. Port 8085.

## Run

```bash
python -m venv .venv
source .venv/bin/activate        # Windows: .venv\Scripts\activate
pip install -r requirements-dev.txt
cp .env.example .env             # then adjust values
uvicorn app.main:app --host 0.0.0.0 --port 8085 --reload
```

- Swagger: http://localhost:8085/docs
- Health: http://localhost:8085/health
- Registers in Eureka (`EUREKA_URL`) as `RISK-ENGINE-SERVICE`

## Tests

```bash
pytest
```

## Structure

- `app/main.py` entry point (DB init + Eureka register/deregister)
- `app/config.py` settings from `.env`
- `app/database.py` engine, session, Base
- `app/models.py` SQLAlchemy tables
- `app/schemas.py` Pydantic schemas
- `app/routers/` API routes
- `app/services/` business logic (score, weather, alerts)
- `app/clients/` external API calls (weather)
- `migrations/` Alembic (later)
