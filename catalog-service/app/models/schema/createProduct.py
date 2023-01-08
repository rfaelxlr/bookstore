from pydantic.generics import GenericModel

class CreateProduct(GenericModel):
    code: str
    name: str
    description: str
    image_url: str
    price: str
