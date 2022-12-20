from typing import List
from pydantic import BaseModel

class Data (BaseModel):
    id : str
    code : str
    name : str
    description : str
    img_url : str
    price : float
    discount: float
    salesPrice: float


class ProductResponse (BaseModel):
    totalElements : int
    totalPages : int
    currentPage: int
    data: List[Data]