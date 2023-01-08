from database.database_local import Base
from sqlalchemy import Boolean, Column, Float, Integer, String
from sqlalchemy.dialects.postgresql import UUID
import uuid


class Product(Base):
    __tablename__ = "product"

    id = Column(Integer, primary_key=True, index=True)
    external_id = Column(UUID(as_uuid=True), unique=True,
                         default=uuid.uuid4, nullable=False)
    code = Column(String(10), nullable=False)
    name = Column(String(100), nullable=False)
    description = Column(String(200))
    img_url = Column(String(200), nullable = True)
    price = Column(Float, nullable=False)
    discount = Column(Float, nullable=True)
    active = Column(Boolean, default=True)
    removed = Column(Boolean, default=False)
    
    

