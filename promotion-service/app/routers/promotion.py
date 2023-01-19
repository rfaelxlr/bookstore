from fastapi import APIRouter,Depends, Query
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
async def get_promotions_with_set_productCodes(product_codes: str = Query(None), db: Session = Depends(get_db) ):
    if product_codes is None:
        return {"message": "product_codes query parameter is required"}
        
    product_codes = product_codes.split(",")
    
    return await promotionRepository.get_promotions_with_set_productCodes(product_code=product_codes, db = db) 