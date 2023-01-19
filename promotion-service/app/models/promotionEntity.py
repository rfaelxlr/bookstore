from database.database_local import Base
from sqlalchemy import Boolean, Column, Float, Integer, String
from sqlalchemy.dialects.postgresql import UUID
import uuid


class Promotion(Base):
    __tablename__ = "promotion"

    id = Column(Integer, primary_key=True, index=True)
    external_id = Column(UUID(as_uuid=True), unique=True,
                         default=uuid.uuid4, nullable=False)
    code = Column(String(10), nullable=False, unique = True)
    discount = Column(Float, nullable=True)
    active = Column(Boolean, default=True)
