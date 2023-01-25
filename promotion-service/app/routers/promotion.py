from fastapi import APIRouter, Depends, HTTPException, Path, Query
from models.promotionSchema import PromotionDTO
from database.database_local import SessionLocal
from repository import promotionRepository
from sqlalchemy.orm import Session

router = APIRouter(prefix="/promotions", tags=["promotions"])


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@router.get("")
async def get_promotions_with_set_productCodes(product_codes: str = Query(None), db: Session = Depends(get_db)):
    if product_codes is None:
        return {"message": "product_codes query parameter is required"}

    product_codes = product_codes.split(",")

    return await promotionRepository.get_promotions_with_set_productCodes(product_code=product_codes, db=db)


@router.get("/{product_code}")
async def get_promotion_by_product_code(product_code: str = Path(..., alias="product_code"), db: Session = Depends(get_db)):
    result = await promotionRepository.get_promotion_by_product_code(product_code=product_code, db=db)
    if result is None:
        raise HTTPException(
            status_code=404, detail=f"Promotion for Product with code {product_code} not found")
    return result


@router.put("")
async def create_update_promotion(form: PromotionDTO , db: Session = Depends(get_db)):
    return await promotionRepository.create_update_promotion(form = form , db=db)

@router.delete("/{product_code}")
async def delete_promotion_by_product_code(product_code: str = Path(..., alias="product_code"), db: Session = Depends(get_db)):
    await promotionRepository.delete_promotion_by_product_code(product_code=product_code, db=db)
    return True
