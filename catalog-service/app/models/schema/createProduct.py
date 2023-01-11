from pydantic.generics import GenericModel
from typing import Optional

class CreateProduct(GenericModel):
    code: str
    name: str
    description: Optional[str]
    image_url: Optional[str]
    price: str
