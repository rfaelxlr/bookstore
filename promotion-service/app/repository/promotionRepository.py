from fastapi import Query
from sqlalchemy.orm import Session
from sqlalchemy.sql import select
from models.promotionEntity import Promotion
from models.promotionSchema import PromotionResponse


async def get_promotions_with_set_productCodes(product_code :set, db: Session):
    query = select(Promotion.code, Promotion.discount).where((Promotion.active == True) & (Promotion.code.in_(product_code)))
    result =  db.execute(query).fetchall()
    promotions = [PromotionResponse(productCode=row[0], discount=row[1]) for row in result]
    return promotions
     
