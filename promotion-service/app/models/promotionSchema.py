
from pydantic import BaseModel

class PromotionDTO (BaseModel):
    productCode: str
    discount: float
    