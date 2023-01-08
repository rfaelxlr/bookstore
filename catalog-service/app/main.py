from fastapi import FastAPI
from routers import product
from fastapi_pagination import add_pagination

app = FastAPI()

app.include_router(product.router)
add_pagination(app)

@app.get("/")
async def root():
    return {"message": "Hello World"}