from pydantic import BaseModel, ConfigDict


class ZoneCreate(BaseModel):
    name: str
    latitude: float
    longitude: float


class ZoneOut(ZoneCreate):
    model_config = ConfigDict(from_attributes=True)

    id: int
    risk_score: float
