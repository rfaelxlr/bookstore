from fastapi import APIRouter
from typing import List
from models.schema.productResponse import ProductResponse, Data
router = APIRouter(prefix="/products", tags=["products"])


@router.get("")
async def root():
    dado = [Data(id="teste", code="01", name="teste", description="testando",
                     img_url="", price=10.0, discount=1.0, salesPrice=2.0)]
    return ProductResponse(currentPage=1, totalElements=10, totalPages=1, data=dado)
