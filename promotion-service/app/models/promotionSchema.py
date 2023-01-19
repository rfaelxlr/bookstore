
from pydantic import BaseModel

class PromotionResponse (BaseModel):
    productCode: str
    discount: float
    