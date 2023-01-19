from fastapi import FastAPI,status
from routers import promotion
from fastapi.responses import JSONResponse
from fastapi.exceptions import RequestValidationError
from fastapi.encoders import jsonable_encoder

app = FastAPI()

app.include_router(promotion.router)


@app.exception_handler(RequestValidationError)
async def validation_exception_handler(request, exc):
    return  JSONResponse(
        status_code=status.HTTP_400_BAD_REQUEST,
        content=jsonable_encoder({"message": "Request invalid!"}),
    )
@app.get("/")
async def root():
    return {"message": "Hello World"}