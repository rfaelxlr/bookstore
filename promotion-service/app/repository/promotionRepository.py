from fastapi import HTTPException
from sqlalchemy.orm import Session
from sqlalchemy.sql import select
from models.promotionEntity import Promotion
from models.promotionSchema import PromotionDTO
from uuid import uuid4

async def get_promotions_with_set_productCodes(product_code :set, db: Session):
    query = select(Promotion.code, Promotion.discount).where((Promotion.active == True) & (Promotion.code.in_(product_code)))
    result =  db.execute(query).fetchall()
    promotions = [PromotionDTO(productCode=row[0], discount=row[1]) for row in result]
    return promotions

async def get_promotion_by_product_code(product_code: str, db:Session):
    query = select(Promotion.code, Promotion.discount).where((Promotion.active == True) & (Promotion.code == product_code))
    result =  db.execute(query).first()
    return result


async def create_update_promotion(form: PromotionDTO, db: Session):
    promotion = db.query(Promotion).filter((Promotion.code == form.productCode) & (Promotion.active == True)).first()

    if promotion:
        promotion.discount = form.discount
        db.commit()
        db.refresh(promotion)
        return PromotionDTO(productCode = promotion.code, discount = promotion.discount)
    
    promotion = Promotion(code = form.productCode, discount = form.discount, active = True, external_id = uuid4())
    db.add(promotion)
    try:
        db.commit()
        db.refresh(promotion)
        return PromotionDTO(productCode = promotion.code, discount = promotion.discount)
    except Exception:
        db.rollback
        raise
    
async def delete_promotion_by_product_code(product_code: str, db:Session):
    promotion = db.query(Promotion).filter((Promotion.code == product_code) & (Promotion.active == True)).first()
    if promotion is None:
        raise HTTPException(
            status_code=404, detail=f"Product with code {product_code} not found")
    promotion.active = False
    db.commit()
    db.refresh(promotion)