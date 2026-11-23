import logging
from contextlib import asynccontextmanager

from fastapi import FastAPI

from app import eureka
from app.config import settings
from app.database import Base, engine
from app.routers import health, zones
# from app.routers import risk, weather, alerts   # include when implemented

logger = logging.getLogger("uvicorn.error")


@asynccontextmanager
async def lifespan(app: FastAPI):
    try:
        Base.metadata.create_all(bind=engine)  # dev only; switch to Alembic later
    except Exception as exc:
        logger.warning("Database not available, continuing without it: %s", exc.__class__.__name__)
    await eureka.register()
    yield
    await eureka.deregister()


app = FastAPI(title=settings.app_name, lifespan=lifespan)

app.include_router(health.router)
app.include_router(zones.router)
# app.include_router(risk.router)
# app.include_router(weather.router)
# app.include_router(alerts.router)