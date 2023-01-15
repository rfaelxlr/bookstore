from typing import TypeVar, Generic, List
from pydantic.generics import GenericModel


T = TypeVar('T')

class PageResponse(GenericModel, Generic[T]):
    page_number: int
    page_size: int
    total_pages: int
    total_record: int
    content: List[T]