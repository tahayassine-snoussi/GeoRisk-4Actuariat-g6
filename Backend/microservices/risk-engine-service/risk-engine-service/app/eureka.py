import py_eureka_client.eureka_client as eureka_client

from app.config import settings


async def register() -> None:
    ip = settings.instance_ip or "127.0.0.1"
    await eureka_client.init_async(
        eureka_server=settings.eureka_url,
        app_name=settings.app_name,
        instance_ip=ip,
        instance_host=ip,
        instance_port=settings.port,
        instance_id=f"{ip}:{settings.app_name}:{settings.port}",
        health_check_url=f"http://{ip}:{settings.port}/health",
        status_page_url=f"http://{ip}:{settings.port}/docs",
    )


async def deregister() -> None:
    await eureka_client.stop_async()