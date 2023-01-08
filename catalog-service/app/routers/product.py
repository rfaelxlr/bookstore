from fastapi import APIRouter, Depends, HTTPException, Path, Query
from models.schema.productResponse import ProductResponse
from repository import productRepository
from database.database_local import SessionLocal
from sqlalchemy.orm import Session
from models.schema.productResponse import Data
from models.schema.createProduct import CreateProduct


router = APIRouter(prefix="/products", tags=["products"])


def get_db():
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()


@router.get("")
async def get_products(page: int = 1, limit: int = 10, db: Session = Depends(get_db), keyword: str = Query(None,min_length=1)):
    if keyword is not None and len(keyword) > 0:
        result = await productRepository.get_products_by_keyword(db=db, page=page, limit= limit, keyword=keyword)
        return ProductResponse(totalElements=result.total_record, totalPages=result.total_pages, 
                           currentPage=result.page_number, data=[Data(p) for p in result.content])
        
    result = await productRepository.get_products(db, page, limit)
    return ProductResponse(totalElements=result.total_record, totalPages=result.total_pages, 
                           currentPage=result.page_number, data=[Data(p) for p in result.content])

@router.get("/{code}")
async def get_product_by_code(code: str = Path(..., alias = "code"), db: Session = Depends(get_db)):
    result = await productRepository.get_product_by_code(db=db, code=code)
    
    if result is None:
        raise HTTPException(status_code=404, detail=f"Product with code {code} not found")
    
    return Data(result)

@router.post("")
async def create_product(form: CreateProduct,db: Session = Depends(get_db)):
    return await productRepository.create(form=form, db=db)
    