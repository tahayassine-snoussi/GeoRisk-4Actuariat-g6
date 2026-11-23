from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    model_config = SettingsConfigDict(env_file=".env", extra="ignore")

    app_name: str = "risk-engine-service"
    port: int = 8085

    db_host: str = "localhost"
    db_port: int = 5432
    db_name: str = "riskengine"
    db_user: str = "postgres"
    db_password: str = "postgres"

    eureka_url: str = "http://localhost:8761/eureka"
    instance_ip: str = ""

    weather_api_url: str = ""
    weather_api_key: str = ""

    @property
    def database_url(self) -> str:
        return (
            f"postgresql+psycopg2://{self.db_user}:{self.db_password}"
            f"@{self.db_host}:{self.db_port}/{self.db_name}"
        )


settings = Settings()
