from fastapi.testclient import TestClient

from app.main import app

# no "with" block -> lifespan (DB + Eureka) is not started
client = TestClient(app)


def test_health():
    response = client.get("/health")
    assert response.status_code == 200
    assert response.json() == {"status": "UP"}
