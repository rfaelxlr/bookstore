from pydantic import BaseModel
from typing import Optional
from models.entity.product import Product

import uuid


class Data (BaseModel):
    external_id: Optional[uuid.UUID]
    code: Optional[str]
    name: Optional[str]
    description: Optional[str]
    img_url: Optional[str]
    price: Optional[float]
    discount: Optional[float]
    salesPrice: Optional[float]

    def __init__(self, p: Product):
        discount = (p.discount) if p.discount else 0
        salesPrice= (p.price - discount) if p.price else 0
        super().__init__(
            external_id=p.external_id,
            code = p.code,
            name = p.name,
            description = p.description,
            img_url = p.image_url,
            price = p.price,
            discount = discount,
            salesPrice = salesPrice)
            


class PaginetedProductResponse (BaseModel):
    totalElements: int
    totalPages: int
    currentPage: int
    data: list[Data]
